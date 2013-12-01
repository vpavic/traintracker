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
		$sitePreference = $this->_get_requested_site_preference();

		if ($sitePreference != false)
		{
			$this->_save_site_preference($sitePreference);
		}
		else
		{
			$sitePreference = $this->_get_saved_site_preference();
		}

		if ($sitePreference == false)
		{
			$sitePreference = $this->_get_default_site_preference();
		}

		if ($sitePreference == 'desktop')
		{
			$this->load->view('home');
		}
		elseif ($sitePreference == 'mobile')
		{
			redirect('/mobile');
		}
	}

	private function _get_requested_site_preference()
	{
		$sitePreference = $this->input->get('site_preference');
		return $this->_validate_site_preference($sitePreference);
	}

	private function _get_saved_site_preference()
	{
		$sitePreference = $this->input->cookie('site_preference', true);
		return $this->_validate_site_preference($sitePreference);
	}

	private function _get_default_site_preference()
	{
		return $this->agent->is_mobile() ? 'mobile' : 'desktop';
	}

	private function _save_site_preference($value)
	{
		$cookie = array(
			'name' => 'site_preference',
			'value' => $value,
			'expire' => '31536000'
		);

		$this->input->set_cookie($cookie);
	}

	private function _validate_site_preference($value)
	{
		switch ($value) {
			case 'desktop':
			case 'mobile':
				return $value;
			default:
				return false;
		}
	}
}
