<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class StationsFetcher
{
	public function getStations($trainNo)
	{
		if ($trainNo === 211 || $trainNo === 415)
		{
			$fetchers = array(new CroHtmlStationsFetcher(), new SloHtmlStationsFetcher());
		}
		else
		{
			$fetchers = array(new CroHtmlStationsFetcher());
		}

		foreach ($fetchers as $fetcher)
		{
			$data = $fetcher->getStations($trainNo);

			if (!empty($data['stations']))
			{
				break;
			}
		}

		return $data;
	}
}

abstract class AbstractHtmlStationsFetcher
{
	abstract protected function fetchHtml($trainNo);

	abstract protected function parseStations($input);

	abstract protected function parseNoOfWagons($input);

	public function getStations($trainNo)
	{
		$html = $this->fetchHtml($trainNo);

		$input = new DOMDocument();
		$input->preserveWhiteSpace = false;
		@$input->loadHTML($html);

		return array(
			'no_of_wagons' => $this->parseNoOfWagons($input),
			'stations' => $this->parseStations($input)
		);
	}
}

class CroHtmlStationsFetcher extends AbstractHtmlStationsFetcher
{
	protected function fetchHtml($trainNo)
	{
		$params = array(
			'VL' => $trainNo,
			'D1' => date('ymd'),
			'D2' => date('ymd'),
			'Category' => "hzinfo",
			'Service' => "PKVL",
			'SCREEN' => "2",
			'LANG' => "HR"
		);
		$uri = "http://vred.hzinfra.hr/hzinfo/Default.asp?" . http_build_query($params);
		$context = stream_context_create(array(
			'http' => array(
				'method' => "GET",
				'timeout' => 10
			)
		));

		$html = file_get_contents($uri, false, $context);

		$input = new DOMDocument();
		$input->preserveWhiteSpace = false;
		@$input->loadHTML($html);

		$link = $input->getElementsByTagName('a')->item(0);

		if (empty($link))
		{
			return null;
		}

		$uri = $link->getAttribute('href');

		return file_get_contents($uri, false, $context);
	}

	protected function parseNoOfWagons($input)
	{
		// TODO
		return 'n/a';
	}

	protected function parseStations($input)
	{
		$table = $input->getElementsByTagName('table')->item(1);
		$rows = ($table != null) ? $table->getElementsByTagName('tr') : array();

		$stations = array();

		foreach ($rows as $row)
		{
			$cols = $row->getElementsByTagName('td');

			if ($cols->length === 5)
			{
				$name = trim($cols->item(0)->nodeValue);
				$direction = trim($cols->item(1)->nodeValue);
				$time = trim($cols->item(3)->nodeValue);
				$delay = trim($cols->item(4)->nodeValue);

				if ($direction === "Dolazak")
				{
					$station = array(
						'name' => $name,
						'arrival_time' => $time,
						'arrival_delay' => ($delay == "") ? "-" : $delay,
						'departure_time' => "",
						'departure_delay' => ""
					);
				}
				else
				{
					$prev_station = end($stations);

					if ($prev_station !== false && $prev_station['name'] === $name)
					{
						$station = array_pop($stations);
					}
					else
					{
						$station = array(
							'name' => $name,
							'arrival_time' => "",
							'arrival_delay' => ""
						);
					}
					$station['departure_time'] = $time;
					$station['departure_delay'] = ($delay == "") ? "-" : $delay;
				}
				array_push($stations, $station);
			}
		}

		return $stations;
	}
}

class SloHtmlStationsFetcher extends AbstractHtmlStationsFetcher
{
	protected function fetchHtml($trainNo)
	{
		$params = array(
			'Category' => "E-zeleznice",
			'Service' => "w_zamude_web_2_1",
			'vlak' => str_pad($trainNo, 5, " ", STR_PAD_LEFT)
		);
		$uri = "http://ice.slo-zeleznice.si/CIDirect/default.asp?" . http_build_query($params);
		$context = stream_context_create(array(
			'http' => array(
				'method' => "GET",
				'timeout' => 10
			)
		));

		return file_get_contents($uri, false, $context);
	}

	protected function parseNoOfWagons($input)
	{
		// TODO
		return 'n/a';
	}

	protected function parseStations($input)
	{
		$table = $input->getElementsByTagName('table')->item(0);
		$rows = ($table != null) ? $table->getElementsByTagName('tr') : array();

		$stations = array();

		foreach ($rows as $row)
		{
			$cols = $row->getElementsByTagName('td');

			if ($cols->length === 7)
			{
				$name = trim($cols->item(0)->nodeValue);
				$arrival_time = trim($cols->item(2)->nodeValue);
				$arrival_delay = trim($cols->item(3)->nodeValue);
				$departure_time = trim($cols->item(5)->nodeValue);
				$departure_delay = trim($cols->item(6)->nodeValue);

				$arrival_available = ($arrival_time !== ".") && ($arrival_time != null);
				$departure_available = ($departure_time !== ".") && ($departure_time != null);

				if ($arrival_available || $departure_available)
				{
					$station = array(
						'name' => $name,
						'arrival_time' => "",
						'arrival_delay' => "",
						'departure_time' => "",
						'departure_delay' => ""
					);

					if ($arrival_available)
					{
						$station['arrival_time'] = $arrival_time;
						$station['arrival_delay'] = ($arrival_delay == "R") ? "-" : $arrival_delay;
					}

					if ($departure_available)
					{
						$station['departure_time'] = $departure_time;
						$station['departure_delay'] = ($departure_delay == "R") ? "-" : $departure_delay;
					}

					array_push($stations, $station);
				}
			}
		}

		return $stations;
	}
}

/* End of file StationsFetcher.php */
