<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Stations extends CI_Controller {

	public function __construct()
	{
		parent::__construct();
		$this->load->library('stationsFetcher');
		date_default_timezone_set('Europe/Zagreb');
	}

	public function _remap($method)
	{
		$trainNo = intval($method);

		if ($trainNo > 0)
		{
			$this->_stations($trainNo);
		}
		else
		{
			show_error('400 Bad Request', 400);
		}
	}

	private function _stations($trainNo)
	{
		$stations = $this->stationsfetcher->getStations($trainNo);

		$data = array(
			'generatedTime' => date('H:i:s'),
			'stations' => $stations
		);

		$json = json_encode($data, JSON_UNESCAPED_UNICODE);

		$this->output->set_header('Cache-Control: no-cache, must-revalidate');
		$this->output->set_header('Pragma: no-cache');

		if ($this->input->get('callback'))
		{
			$this->output->set_content_type('application/javascript;charset=UTF-8');
			$this->output->set_output("{$this->input->get('callback')}($json)");
		}
		else
		{
			$this->output->set_content_type('application/json;charset=UTF-8');
			$this->output->set_output($json);
		}
	}
}
