<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Home extends CI_Controller {

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
			redirect('/mobile');
		}
		else
		{
			$this->load->view('home');
		}
	}
}
