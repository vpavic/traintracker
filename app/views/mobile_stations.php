<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Vozni red uživo!</title>
	<link rel="stylesheet" href="//code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css">
	<script src="//code.jquery.com/jquery-1.11.2.min.js"></script>
	<script src="//code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
	<script src="//oss.maxcdn.com/jquery.cookie/1.4.1/jquery.cookie.min.js"></script>
	<script src="<?php echo base_url('/resources/voznired.js'); ?>"></script>
</head>
<body>
	<div data-role="page">
		<div data-role="header">
			<a href="<?php echo site_url('/mobile'); ?>" data-role="button" data-icon="home" data-iconpos="notext" data-theme="c" data-inline="true">Početna</a>
			<h1>Vozni red uživo!</h1>
			<a href="<?php echo current_url(); ?>" data-role="button" data-icon="refresh" data-iconpos="notext" data-theme="c" data-inline="true" data-ajax="false">Osvježi</a>
		</div>
		<div data-role="content">
			<?php if (isset($current_station)): ?>
				<ul data-role="listview" data-inset="true">
					<li data-role="list-divider">Trenutna pozicija vlaka</li>
					<li>
						<h3><?php echo $current_station['name']; ?></h3>
						<?php if (isset($current_station['arrival_time'])): ?>
							<p>Dolazak: <strong><?php echo $current_station['arrival_time']; ?></strong> Kašnjenje: <strong><?php echo isset($current_station['arrival_delay']) ? $current_station['arrival_delay'] : '-'; ?></strong></p>
						<?php endif; ?>
						<?php if (isset($current_station['departure_time'])): ?>
							<p>Odlazak: <strong><?php echo $current_station['departure_time']; ?></strong> Kašnjenje: <strong><?php echo isset($current_station['departure_delay']) ? $current_station['departure_delay'] : '-'; ?></strong></p>
						<?php endif; ?>
					</li>
				</ul>
				<div data-role="collapsible">
					<h2>Potpuni pregled kretanja vlaka</h2>
					<ul data-role="listview" data-theme="d" data-divider-theme="d">
						<?php foreach ($all_stations as $station): ?>
							<li>
								<h3><?php echo $station['name']; ?></h3>
								<?php if (isset($station['arrival_time'])): ?>
									<p>Dolazak: <strong><?php echo $station['arrival_time']; ?></strong> Kašnjenje: <strong><?php echo isset($station['arrival_delay']) ? $station['arrival_delay'] : '-'; ?></strong></p>
								<?php endif; ?>
								<?php if (isset($station['departure_time'])): ?>
									<p>Odlazak: <strong><?php echo $station['departure_time']; ?></strong> Kašnjenje: <strong><?php echo isset($station['departure_delay']) ? $station['departure_delay'] : '-'; ?></strong></p>
								<?php endif; ?>
							</li>
						<?php endforeach; ?>
					</ul>
				</div>
				<button class="saveTrain" data-train-no="<?php echo $train_no; ?>">Dodaj u moje vlakove</button>
				<div data-role="popup" data-overlay-theme="a" class="ui-content" id="saveTrain<?php echo $train_no; ?>Popup">
					<p>Vlak <?php echo $train_no; ?> je dodan u moje vlakove</p>
				</div>
			<?php else: ?>
				<div class="ui-body ui-body-d ui-corner-all">
					<p>Podaci o kretanju vlaka br. <?php echo $train_no; ?> trenutno nisu dostupni!</p>
				</div>
			<?php endif; ?>
		</div>
	</div>
</body>
</html>
