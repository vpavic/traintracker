<!DOCTYPE html>
<html lang="hr">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link rel="icon" href="favicon.ico">

	<title>Vozni red uživo!</title>

	<link rel="stylesheet" href="https://cdn.jsdelivr.net/bootstrap/3.3.6/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/pace/1.0.2/themes/blue/pace-theme-flash.css">

	<style>
		body { padding-top: 70px; }
	</style>

	<!--[if lt IE 9]>
		<script src="https://cdn.jsdelivr.net/html5shiv/3.7.3/html5shiv.min.js"></script>
		<script src="https://cdn.jsdelivr.net/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="<?php echo strtok($_SERVER['REQUEST_URI'], '?'); ?>">Vozni red uživo!</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Pomoć <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="#" data-toggle="modal" data-target="#shortcuts">Prečaci</a></li>
							<li class="dropdown-header">Korisni linkovi</li>
							<li><a href="http://www.hzinfra.hr/radovi-na-pruzi02" target="_blank">HŽ - Radovi na pruzi</a></li>
							<li><a href="http://hr.wikipedia.org/wiki/%C5%BDeljezni%C4%8Dke_pruge_u_Hrvatskoj" target="_blank">Željezničke pruge u RH</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="jumbotron">
			<h3>Pretraga po broju vlaka</h3>
			<form class="form-inline" id="search-form">
				<div class="form-group">
					<label class="sr-only" for="train-no-input">Broj vlaka</label>
					<div class="input-group">
						<input type="number" class="form-control" id="train-no-input" placeholder="Broj vlaka">
					</div>
				</div>
				<button type="submit" class="btn btn-primary" id="submit-button" disabled>Traži!</button>
			</form>
		</div>
	</div>

	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading" role="tab" id="starred-trains-heading">
				<h3 class="panel-title">
					<span class="glyphicon glyphicon-list" aria-hidden="true"></span>
					<a role="button" data-toggle="collapse" data-parent="#accordion" href="#starred-trains" aria-expanded="false" aria-controls="starred-trains">
						Moji vlakovi
					</a>
				</h3>
			</div>
			<div id="starred-trains" class="panel-collapse collapse" role="tabpanel" aria-labelledby="starred-trains-heading">
				<div class="panel-body collapse" id="starred-trains-panel">
					<p class="text-center text-muted">Nemate spremljenih vlakova</p>
				</div>
				<ul class="list-group" id="starred-trains-list"></ul>
			</div>
		</div>
	</div>

	<div class="container" id="train-data"></div>

	<div class="modal fade" id="shortcuts" tabindex="-1" role="dialog" aria-labelledby="header-shortcuts">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="header-shortcuts">Prečaci</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-2 text-center"><strong>/</strong></div>
						<div class="col-md-8">Fokusiraj polje za pretragu</div>
					</div>
					<div class="row">
						<div class="col-md-2 text-center"><strong>s</strong></div>
						<div class="col-md-8">Prikaži/sakrij moje vlakove</div>
					</div>
					<div class="row">
						<div class="col-md-2 text-center"><strong>h</strong></div>
						<div class="col-md-8">Prikaži/sakrij potpuni pregled kretanja vlaka</div>
					</div>
					<div class="row">
						<div class="col-md-2 text-center"><strong>r</strong></div>
						<div class="col-md-8">Osvježi trenutnu pretragu</div>
					</div>
					<div class="row">
						<div class="col-md-2 text-center"><strong>?</strong></div>
						<div class="col-md-8">Prikaži prečace</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<script src="https://cdn.jsdelivr.net/pace/1.0.2/pace.min.js"></script>
	<script src="https://cdn.jsdelivr.net/mousetrap/1.6.0/mousetrap.min.js"></script>
	<script src="https://cdn.jsdelivr.net/mousetrap/1.6.0/plugins/global-bind/mousetrap-global-bind.min.js"></script>
	<script src="https://cdn.jsdelivr.net/mousetrap/1.6.0/plugins/bind-dictionary/mousetrap-bind-dictionary.min.js"></script>
	<script src="app.js"></script>
</body>
</html>