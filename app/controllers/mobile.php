<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Mobile extends CI_Controller {

	public function __construct()
	{
		parent::__construct();
		$this->load->library('stationsFetcher');
		$this->load->helper('url');
		date_default_timezone_set('Europe/Zagreb');
	}

	public function index()
	{
		$this->load->view('mobile_home');
	}

	public function stations()
	{
		$train_no = intval($this->input->get('train_no'));
		$train_data = $this->stationsfetcher->getStations($train_no);

		if (!empty($train_data['stations']))
		{
			$data['all_stations'] = $train_data['stations'];
			$data['current_station'] = end($train_data['stations']);
		}
		else
		{
			$data['train_no'] = $train_no;
		}

		$this->output->set_header('Cache-Control: no-cache, must-revalidate');
		$this->output->set_header('Pragma: no-cache');
		$this->load->view('mobile_stations', $data);
	}
}
