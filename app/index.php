<?php

define('BASEPATH', true);

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
		$trainNo = $_GET['train-no'];
		if (empty($trainNo)) {
			http_response_code(400);
			die;
		}
		$controller->train($trainNo);
		break;
	default:
		http_response_code(404);
		die;
}
