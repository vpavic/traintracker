<?php if (!$is_ajax_request): ?>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Vozni red uživo!</title>
	<link rel="stylesheet" href="//code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
</head>
<body>
<?php endif; ?>
	<div data-role="page">
		<div data-role="header">
			<h1>Vozni red uživo!</h1>
		</div>
		<div data-role="content">
			<form method="get">
				<input type="number" name="train_no" placeholder="Broj vlaka" autocomplete="off" data-clear-btn="true"/>
				<button type="submit">Traži!</button>
			</form>
			<?php if (!empty($my_trains)): ?>
				<ul data-role="listview" data-split-icon="delete" data-split-theme="d" data-inset="true">
					<li data-role="list-divider">Moji vlakovi</li>
					<?php foreach ($my_trains as $train): ?>
						<li>
							<a href="<?php echo site_url('/mobile/stations/' . $train);?>"><?php echo $train; ?></a>
							<a href="#" class="removeTrain" train-no="<?php echo $train; ?>">Obriši iz mojih vlakova</a>
						</li>
					<?php endforeach; ?>
				</ul>
				<?php foreach ($my_trains as $train): ?>
					<div data-role="popup" data-overlay-theme="a" class="ui-content" id="removeTrain<?php echo $train; ?>Popup">
						<p>Vlak <?php echo $train; ?> je uklonjen iz mojih vlakova</p>
					</div>
				<?php endforeach; ?>
			<?php endif; ?>
		</div>
	</div>
<?php if (!$is_ajax_request): ?>
	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
	<script src="//code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
	<script src="<?php echo base_url('/resources/jquery.cookie.js'); ?>"></script>
	<script src="<?php echo base_url('/resources/voznired.js'); ?>"></script>
</body>
</html>
<?php endif; ?>
