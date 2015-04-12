<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class StationsFetcher
{
	private static $eastToWestTrains = array(158, 210, 212, 414, 498);
	private static $westToEastTrains = array(159, 211, 213, 415, 499);

	public function getStations($trainNo)
	{
		if (in_array($trainNo, self::$eastToWestTrains))
		{
			$fetchers = array(new SloHtmlStationsFetcher(), new CroHtmlStationsFetcher());
		}
		elseif (in_array($trainNo, self::$westToEastTrains))
		{
			$fetchers = array(new CroHtmlStationsFetcher(), new SloHtmlStationsFetcher());
		}
		else
		{
			$fetchers = array(new CroHtmlStationsFetcher());
		}

		foreach ($fetchers as $fetcher)
		{
			$stations = $fetcher->getStations($trainNo);

			if (!empty($stations))
			{
				break;
			}
		}

		return isset($stations) ? $stations : array();
	}
}

abstract class AbstractHtmlStationsFetcher
{
	abstract protected function prepareUrl($trainNo);

	abstract protected function parseStations($input);

	public function getStations($trainNo)
	{
		$url = $this->prepareUrl($trainNo);
		$html = file_get_contents($url);

		return $this->parseStations($html);
	}
}

class CroHtmlStationsFetcher extends AbstractHtmlStationsFetcher
{
	protected function prepareUrl($trainNo)
	{
		$params = array(
			'VL' => $trainNo,
			'D1' => date('ymd'),
			'Category' => 'korisnici',
			'Service' => 'Pkvl',
			'SCREEN' => '2'
		);

		return 'http://vred.hzinfra.hr/hzinfo/Default.asp?' . http_build_query($params);
	}

	protected function parseStations($input)
	{
		$regex = '#<TD ALIGN=left BGCOLOR=.{7}><FONT FACE="Arial" SIZE=3>(?P<name>.*?)</TD>\r\n' .
				'<TD ALIGN=CENTER BGCOLOR=.{7}><FONT FACE="Arial" SIZE=3>(?P<direction>.*?)</TD>\r\n' .
				'<TD ALIGN=CENTER BGCOLOR=.{7}><FONT FACE="Arial" SIZE=3>.*?</TD>\r\n' .
				'<TD ALIGN=CENTER BGCOLOR=.{7}><FONT FACE="Arial" SIZE=3>(?P<time>.*?)</TD>\r\n' .
				'<TD ALIGN=CENTER BGCOLOR=.{7}><FONT FACE="Arial" SIZE=3>(?P<delay>.*?)</TD>#';

		preg_match_all($regex, $input, $matches);

		$stations = array();

		for ($i = 0; $i < count($matches['name']); $i++)
		{
			$name = iconv('Windows-1250', 'UTF-8', trim($matches['name'][$i]));
			$direction = trim($matches['direction'][$i]);
			$time = trim($matches['time'][$i]);
			$delay = trim($matches['delay'][$i]);

			if ($direction == 'Dolazak')
			{
				$station = array(
					'name' => $name,
					'arrivalTime' => $time
				);

				if ($delay != '<BR>')
				{
					$station['arrivalDelay'] = intval($delay);
				}
			}
			else
			{
				$prev_station = end($stations);

				if ($prev_station !== false && $prev_station['name'] == $name)
				{
					$station = array_pop($stations);
				}
				else
				{
					$station = array(
						'name' => $name
					);
				}

				$station['departureTime'] = $time;

				if ($delay != '<BR>')
				{
					$station['departureDelay'] = intval($delay);
				}
			}

			array_push($stations, $station);
		}

		return $stations;
	}
}

class SloHtmlStationsFetcher extends AbstractHtmlStationsFetcher
{
	protected function prepareUrl($trainNo)
	{
		$params = array(
			'Category' => 'E-zeleznice',
			'Service' => 'w_zamude_web_2_1',
			'vlak' => str_pad($trainNo, 5, ' ', STR_PAD_LEFT)
		);

		return 'http://ice.slo-zeleznice.si/CIDirect/default.asp?' . http_build_query($params);
	}

	protected function parseStations($input)
	{
		$regex = '#<tbody>\r\n' .
				'  <tr>\r\n' .
				'    <td>(?P<name>.*?)</td>\r\n' .
				'    <td>.*?</td>\r\n' .
				'    <td>(?P<arrival_time>.*?)</td>\r\n' .
				'    <td>(?P<arrival_delay>.*?)</td>\r\n' .
				'    <td>.*?</td>\r\n' .
				'    <td>(?P<departure_time>.*?)</td>\r\n' .
				'    <td>(?P<departure_delay>.*?)</td>\r\n' .
				'  </tr>#';

		preg_match_all($regex, $input, $matches);

		$stations = array();

		for ($i = 0; $i < count($matches['name']); $i++)
		{
			$name = iconv('Windows-1250', 'UTF-8', trim($matches['name'][$i]));
			$arrival_time = trim($matches['arrival_time'][$i]);
			$arrival_delay = trim($matches['arrival_delay'][$i]);
			$departure_time = trim($matches['departure_time'][$i]);
			$departure_delay = trim($matches['departure_delay'][$i]);

			$arrival_available = ($arrival_time != '.') && ($arrival_time != null);
			$departure_available = ($departure_time != '.') && ($departure_time != null);

			if ($arrival_available || $departure_available)
			{
				$station = array(
					'name' => $name
				);

				if ($arrival_available)
				{
					$station['arrivalTime'] = $arrival_time;

					if ($arrival_delay != 'R')
					{
						$station['arrivalDelay'] = intval($arrival_delay);
					}
				}

				if ($departure_available)
				{
					$station['departureTime'] = $departure_time;

					if ($departure_delay != 'R')
					{
						$station['departureDelay'] = intval($departure_delay);
					}
				}

				array_push($stations, $station);
			}
		}

		return $stations;
	}
}

/* End of file StationsFetcher.php */
