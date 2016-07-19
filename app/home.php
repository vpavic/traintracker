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
			<form class="form-inline" id="search">
				<div class="form-group">
					<label class="sr-only" for="trainNo">Broj vlaka</label>
					<div class="input-group">
						<input type="number" class="form-control" id="trainNo" name="trainNo" placeholder="Broj vlaka">
					</div>
				</div>
				<button type="submit" class="btn btn-primary" id="searchSubmit" disabled>Traži!</button>
			</form>
		</div>
	</div>

	<div class="container">
		<div class="panel panel-default" id="my-trains">
			<div class="panel-heading">
				<h3 class="panel-title">
					<span class="glyphicon glyphicon-list"></span>
					Moji vlakovi
				</h3>
			</div>
			<div class="panel-body collapse">
				<p class="text-center text-muted">Nemate spremljenih vlakova</p>
			</div>
			<ul class="list-group"></ul>
		</div>
	</div>

	<div class="container" id="data"></div>

	<div class="modal fade" id="shortcuts" tabindex="-1" role="dialog" aria-labelledby="headerShortcuts">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="headerShortcuts">Prečaci</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-2 text-center"><strong>/</strong> ili <strong>s</strong></div>
						<div class="col-md-8">Fokusiraj polje za pretragu</div>
					</div>
					<div class="row">
						<div class="col-md-2 text-center"><strong>e</strong></div>
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
	<script>
		var input = $('#trainNo');
		var submit = $('#searchSubmit');
		var form = $('#search')

		focusInput();
		generateMyTrainsList();

		Mousetrap.bind({
			'/': focusInput,
			's': focusInput,
			'e': toggleDetails,
			'r': submitSearch,
			'?': displayShortcuts
		});

		Mousetrap.bindGlobal('esc', blurInput);

		input.keyup(function() {
			var disabled = submit.is(':disabled');
			var empty = $(this).val().length == 0;

			if (!disabled && empty) {
				submit.attr('disabled', 'disabled');
			}
			else if (disabled && !empty) {
				submit.removeAttr('disabled');
			}
		});

		form.submit(function(event) {
			event.preventDefault();
			var trainNo = input.val();

			if (trainNo.length != 0) {
				$('#data').load(trainNo, function() {
					input.blur();

					if (containsTrain(trainNo)) {
						// TODO
					}
					else {
						var link = $('#add-my-train');
						link.append('<span class="glyphicon glyphicon-star"></span>');

						link.click(function(event) {
							var trainNo = String($(this).data('train-no'));
							event.preventDefault();
							addTrain(trainNo);
							generateMyTrainsList();
						});
					}
				});
			}
		});

		function focusInput() {
			input.focus();
		}

		function blurInput() {
			input.blur();
		}

		function toggleDetails() {
			$('#stations').collapse('toggle');
		}

		function submitSearch() {
			form.submit();
		}

		function displayShortcuts() {
			$('#shortcuts').modal('show');
		}

		function saveMyTrains(myTrains) {
			localStorage.setItem('my_trains', JSON.stringify(myTrains));
		}

		function loadMyTrains() {
			return JSON.parse(localStorage.getItem('my_trains')) || [];
		}

		function addTrain(trainNo) {
			var myTrains = loadMyTrains();

			if (myTrains.indexOf(trainNo) != -1) {
				return;
			}

			myTrains.push(trainNo);
			saveMyTrains(myTrains);
		}

		function removeTrain(trainNo) {
			var myTrains = loadMyTrains();
			var index = myTrains.indexOf(trainNo);

			if (index == -1) {
				return;
			}

			myTrains.splice(index);
			saveMyTrains(myTrains);
		}

		function containsTrain(trainNo) {
			var myTrains = loadMyTrains();
			return myTrains.indexOf(trainNo) != -1;
		}

		function generateMyTrainsList() {
			var panel = $('#my-trains');
			var body = panel.find('div.panel-body');
			var list = panel.find('ul.list-group');

			body.hide();
			list.empty();

			var myTrains = loadMyTrains();

			if (myTrains.length > 0) {
				myTrains.sort(function(a, b) {
					return a - b;
				});

				myTrains.forEach(function(train) {
					list.append(
						'<li class="list-group-item">' +
						'<a href="#" class="fetch-my-train" data-train-no="' + train + '">' + train + '</a>' +
						'<a href="#" class="remove-my-train pull-right" data-train-no="' + train + '">' +
						'<span class="glyphicon glyphicon-remove"></span>' +
						'</a>' +
						'</li>');
				});

				$('.fetch-my-train').click(function(event) {
					var trainNo = String($(this).data('train-no'));
					event.preventDefault();
					input.val(trainNo);
					input.keyup();
					form.submit();
				});

				$('.remove-my-train').click(function(event) {
					var trainNo = String($(this).data('train-no'));
					event.preventDefault();
					removeTrain(trainNo);
					generateMyTrainsList();
				});
			}
			else {
				body.show();
			}
		}
	</script>
</body>
</html>
