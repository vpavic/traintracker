<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Ic211 extends CI_Controller {

	public function __construct()
	{
		parent::__construct();
		$this->load->library('user_agent');
		$this->load->helper('url');
	}

	public function index()
	{
		if ($this->agent->is_mobile())
		{
			redirect('/mobile/stations?train_no=211');
		}
		else
		{
			$this->load->view('ic211');
		}
	}
}
