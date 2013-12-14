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
		$train_no = $this->input->get('train_no');

		if (!empty($train_no))
		{
			redirect('/mobile/stations/' . $train_no);
		}

		$data['is_ajax_request'] = $this->input->is_ajax_request();

		$my_trains = json_decode($this->input->cookie('my_trains', true));

		if (!empty($my_trains))
		{
			sort($my_trains);
			$data['my_trains'] = $my_trains;
		}

		$this->load->view('mobile_home', $data);
	}

	public function stations($train_no)
	{
		$train_data = $this->stationsfetcher->getStations($train_no);

		$data['is_ajax_request'] = $this->input->is_ajax_request();
		$data['train_no'] = $train_no;

		if (!empty($train_data['stations']))
		{
			$data['all_stations'] = $train_data['stations'];
			$data['current_station'] = end($train_data['stations']);
		}

		$this->output->set_header('Cache-Control: no-cache, must-revalidate');
		$this->output->set_header('Pragma: no-cache');
		$this->load->view('mobile_stations', $data);
	}
}
