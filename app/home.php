<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link rel="icon" href="favicon.ico">

	<title>Vozni red uživo!</title>

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/css/ie10-viewport-bug-workaround.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/pace/1.0.2/themes/blue/pace-theme-flash.css">

	<style>
		body { padding-top: 70px; }
	</style>

	<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
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
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Korisno <span class="caret"></span></a>
						<ul class="dropdown-menu">
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
			<form class="form-inline" id="search">
				<div class="form-group">
					<label class="sr-only" for="trainNo">Broj vlaka</label>
					<div class="input-group">
						<input type="number" class="form-control" id="trainNo" name="trainNo" placeholder="Broj vlaka">
					</div>
				</div>
				<button type="submit" class="btn btn-primary">Traži!</button>
			</form>
		</div>
	</div>

	<div class="container" id="data"></div>

	<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/js/ie10-viewport-bug-workaround.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/pace/1.0.2/pace.min.js"></script>
	<script>
		var input = $('#trainNo');
		input.focus();

		$('#search').submit(function(event) {
			event.preventDefault();
			var trainNo = input.val();

			$('#data').load(trainNo, function() {
				input.select();
			});
		});
	</script>
</body>
</html>
