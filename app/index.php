<?php

define('BASEPATH', true);

if (file_exists('lib')) {
	set_include_path('lib');
}

require_once 'Controller.php';

if ($_SERVER['REQUEST_METHOD'] != 'GET') {
	http_response_code(405);
	die;
}

$controller = new Controller();
$page = !empty($_GET['page']) ? $_GET['page'] : 'home';

switch ($page) {
	case 'home':
		$controller->home();
		break;
	case 'train':
		$country = $_GET['country'];
		$trainNo = $_GET['train-no'];
		if (empty($country) || empty($trainNo)) {
			http_response_code(400);
			die;
		}
		$controller->train($country, $trainNo);
		break;
	default:
		http_response_code(404);
		die;
}
