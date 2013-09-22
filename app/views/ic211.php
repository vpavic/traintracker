<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>IC 211 uživo!</title>
	<link rel="stylesheet" type="text/css" href="/resources/ic211-style.css">
	<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="/resources/ic211-style-ie.css">
	<![endif]-->
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
			<p>Korisno:</p>
			<ul>
				<li><a href="http://voznired.hocuinternet.com/mobile" target="_blank">Vozni red uživo!</a></li>
				<li><a href="http://www.hzinfra.hr/radovi-na-pruzi02" target="_blank">HŽ Infrastruktura - Radovi na pruzi</a></li>
				<li><a href="http://hr.wikipedia.org/wiki/%C5%BDeljezni%C4%8Dke_pruge_u_Hrvatskoj" target="_blank">Željezničke pruge u Hrvatskoj</a></li>
			</ul>
		</div>
		<div class="notice">
			<p>Izvor podataka: <a href="http://www.slo-zeleznice.si/" target="_blank">Slovenske železnice d.o.o.</a> i <a href="http://www.hzpp.hr/" target="_blank">HŽ Putnički prijevoz d.o.o.</a></p>
			<p>Obrada i prikaz podataka: hocuinternet.com</p>
			<p>IC 211 uživo! | <?php echo safe_mailto('vedran@hocuinternet.com', 'Kontakt'); ?></p>
		</div>
	</div>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script type="text/javascript">
		//<![CDATA[
		$(document).ready(function() {
			loadStations();
		});

		function loadStations() {
			$('#data').html('<p class="noinfo"><img src="/resources/ajax-loader.gif" alt="indicator"/></p>');
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
</body>
</html>
