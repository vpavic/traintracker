<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Vozni red uživo!</title>
	<link rel="stylesheet" href="//code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
</head>
<body>
	<div data-role="page" class="type-interior">
		<div data-role="header">
			<h1>Vozni red uživo!</h1>
		</div>
		<div data-role="content">
			<?php if (isset($current_station)): ?>
				<ul data-role="listview">
					<li data-role="list-divider">Trenutna pozicija vlaka</li>
					<li>
						<h3><?php echo $current_station['name']; ?></h3>
						<?php if ($current_station['arrival_time'] != ""): ?>
							<p>Dolazak: <strong><?php echo $current_station['arrival_time']; ?></strong> Kašnjenje: <strong><?php echo $current_station['arrival_delay']; ?></strong></p>
						<?php endif; ?>
						<?php if ($current_station['departure_time'] != ""): ?>
							<p>Odlazak: <strong><?php echo $current_station['departure_time']; ?></strong> Kašnjenje: <strong><?php echo $current_station['departure_delay']; ?></strong></p>
						<?php endif; ?>
					</li>
					<li>
						<h3>Potpuni pregled kretanja vlaka</h3>
						<ul>
							<?php foreach ($all_stations as $station): ?>
								<li>
									<h3><?php echo $station['name']; ?></h3>
									<?php if ($station['arrival_time'] != ""): ?>
										<p>Dolazak: <strong><?php echo $station['arrival_time']; ?></strong> Kašnjenje: <strong><?php echo $station['arrival_delay']; ?></strong></p>
									<?php endif; ?>
									<?php if ($station['departure_time'] != ""): ?>
										<p>Odlazak: <strong><?php echo $station['departure_time']; ?></strong> Kašnjenje: <strong><?php echo $station['departure_delay']; ?></strong></p>
									<?php endif; ?>
								</li>
							<?php endforeach; ?>
						</ul>
					</li>
				</ul>
			<?php else: ?>
				<div class="ui-body ui-body-d ui-corner-all">
					<p>Podaci o kretanju vlaka br. <?php echo $train_no; ?> trenutno nisu dostupni!</p>
				</div>
			<?php endif; ?>
			<p><a href="#" data-role="button" data-rel="back" data-icon="arrow-l">Povratak na pretragu</a></p>
		</div>
	</div>
	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="//code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
</body>
</html>
