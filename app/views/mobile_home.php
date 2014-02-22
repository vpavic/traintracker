<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Vozni red uživo!</title>
	<link rel="stylesheet" href="//code.jquery.com/mobile/1.4.1/jquery.mobile-1.4.1.min.css">
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="//code.jquery.com/mobile/1.4.1/jquery.mobile-1.4.1.min.js"></script>
	<script src="<?php echo base_url('/resources/jquery.cookie.js'); ?>"></script>
	<script src="<?php echo base_url('/resources/voznired.js'); ?>"></script>
</head>
<body>
	<div data-role="page" id="home">
		<div data-role="header">
			<h1>Vozni red uživo!</h1>
		</div>
		<div data-role="content">
			<form method="get">
				<input type="number" placeholder="Broj vlaka" autocomplete="off" data-clear-btn="true"/>
				<button type="submit">Traži!</button>
			</form>
			<ul id="my-trains" style="display: none" data-role="listview" data-split-icon="delete" data-split-theme="d" data-inset="true">
				<li data-role="list-divider">Moji vlakovi</li>
			</ul>
		</div>
	</div>
</body>
</html>
