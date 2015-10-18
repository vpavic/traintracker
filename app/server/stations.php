<?php

define('BASEPATH', true);

require_once 'StationsFetcher.php';

if ($_SERVER['REQUEST_METHOD'] != 'GET') {
	http_response_code(405);
	die;
}

$trainNo = isset($_GET['train-no']) ? $_GET['train-no'] : null;
$callback = isset($_GET['callback']) ? $_GET['callback'] : null;

if (!isset($trainNo) || empty($trainNo)) {
	http_response_code(400);
	die;
}

$stations = (new StationsFetcher)->getStations($trainNo);

$data = array(
	'generatedTime' => date('H:i:s'),
	'stations' => $stations
);

$json = json_encode($data, JSON_UNESCAPED_UNICODE);

header('Cache-Control: no-cache, must-revalidate');
header('Pragma: no-cache');

if (isset($callback)) {
	header('Content-Type: application/javascript;charset=UTF-8');
	echo "{$callback}($json)";
}
else {
	header('Content-Type: application/json;charset=UTF-8');
	echo $json;
}
