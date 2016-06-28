<?php if (!defined('BASEPATH')) exit('No direct script access allowed');

require_once 'StationsFetcher.php';

class Controller
{
	private $stationsFetcher;

	public function __construct()
	{
		$this->stationsFetcher = new StationsFetcher();
	}

	public function home()
	{
		require_once 'home.php';
	}

	public function train($trainNo)
	{
		$stations = $this->stationsFetcher->getStations($trainNo);

		header('Cache-Control: no-cache, must-revalidate');
		header('Pragma: no-cache');

		if (strpos(strtolower($_SERVER['HTTP_ACCEPT']), 'text/html') !== false ) {
			require_once 'train.php';
		}
		else {
			$data = array(
				'generatedTime' => date('H:i:s'),
				'stations' => $stations
			);

			$json = json_encode($data, JSON_UNESCAPED_UNICODE);

			$callback = !empty($_GET['callback']) ? $_GET['callback'] : null;

			if (isset($callback)) {
				header('Content-Type: application/javascript;charset=UTF-8');
				echo "{$callback}($json)";
			}
			else {
				header('Content-Type: application/json;charset=UTF-8');
				echo $json;
			}
		}
	}
}
