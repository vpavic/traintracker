<!DOCTYPE html>
<html>
<head>
	<title>IC 211 uživo!</title>
	<meta charset="utf-8">
	<link rel="shortcut icon" href="resources/favicon.ico">
	<link rel="stylesheet" type="text/css" href="resources/styles/ic211-style.css">
	<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="resources/styles/ic211-style-ie.css">
	<![endif]-->
	<script type="text/javascript" src="resources/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
	//<![CDATA[
	$(document).ready(function() {
		loadStations();
	});

	function loadStations() {
		$('#data').html('<p class="noinfo"><img src="resources/images/ajax-loader.gif" alt="indicator"/></p>');
		$('#timestamp').html('--:--');

		$.getJSON("stations/211", function(data) {
			if (!$.isEmptyObject(data.stations)) {
				var table = $('<table id="stations"/>');
				var thead = $('<thead/>');
				var tbody = $('<tbody/>');

				table.append(thead);
				table.append(tbody);
				thead.append('<tr><th>Kolodvor</th><th>Dolazak</th><th>Kašnjenje</th><th>Odlazak</th><th>Kašnjenje</th></tr>');

				$.each(data.stations, function(i, station) {
					tbody.append('<tr><td align="left">' + station.name + '</td><td align="center">' + station.arrival_time + '</td><td align="center">' + station.arrival_delay + '</td><td align="center">' + station.departure_time + '</td><td align="center">' + station.departure_delay + '</td></tr>');
				});

				$('#data').html(table);
				tbody.scrollTop(9999);
				$('#data').scrollTop(9999);
			}
			else {
				$('#data').html('<p class="noinfo">Ne postoji podatak o kretanju vlaka.</p>');
			}

			$('#timestamp').html(data.generated_time);
		});

		setTimeout(loadStations, 180000);
	}
	//]]>
	</script>
</head>
<body>
	<div id="container">
		<div class="subcontainer">
			<h1>IC 211 - Vozni red</h1>
			<div id="data">
				<noscript><p class="noinfo">Vaš preglednik ne podržava Javascript.</p></noscript>
			</div>
		</div>
		<div class="notice">
			<p>Generirano u <span id="timestamp"></span>. Podaci se osvježavaju svake 3 minute.</p>
		</div>
		<div class="spacer"></div>
		<div class="subcontainer">
			<p>Dodatak:</p>
			<ul>
				<li><a href="resources/images/railroads_in_croatia.gif">Karta željezničkih pruga u Hrvatskoj</a></li>
			</ul>
		</div>
		<div class="notice">
			<p>Izvor podataka: <a href="http://www.slo-zeleznice.si/" target="_blank">Slovenske železnice d.o.o.</a> i <a href="http://www.hzpp.hr/" target="_blank">HŽ Putnički prijevoz d.o.o.</a></p>
			<p>Obrada i prikaz podataka: hocuinternet.com</p>
			<p>IC 211 uživo! | <?php echo safe_mailto('vedran@hocuinternet.com', 'Kontakt'); ?></p>
		</div>
	</div>
</body>
</html>
