<?php if (!empty($stations)): ?>
	<div class="panel panel-<?php if ($delay < 1) echo 'success'; elseif ($delay < 20) echo 'warning'; else echo 'danger'; ?>">
		<div class="panel-heading">
			<h3 class="panel-title">
				<span class="glyphicon glyphicon-info-sign"></span>
				Trenutna pozicija vlaka
				<a href="#" class="pull-right" id="star" data-train-no="<?php echo $trainNo; ?>">
					<span class="glyphicon"></span>
				</a>
			</h3>
		</div>
		<div class="table-responsive">
			<table class="table">
				<tr>
					<th>Kolodvor</th>
					<th class="text-center">Dolazak</th>
					<th class="text-center">Kašnjenje</th>
					<th class="text-center">Odlazak</th>
					<th class="text-center">Kašnjenje</th>
				</tr>
				<tr>
					<td><?php echo $currentStation['name'] ?></td>
					<td class="text-center"><?php echo !empty($currentStation['arrivalTime']) ? $currentStation['arrivalTime'] : ''; ?></td>
					<td class="text-center"><?php echo !empty($currentStation['arrivalDelay']) ? $currentStation['arrivalDelay'] : ''; ?></td>
					<td class="text-center"><?php echo !empty($currentStation['departureTime']) ? $currentStation['departureTime'] : ''; ?></td>
					<td class="text-center"><?php echo !empty($currentStation['departureDelay']) ? $currentStation['departureDelay'] : ''; ?></td>
				</tr>
			</table>
		</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading" role="tab" id="stations-heading">
			<h3 class="panel-title">
				<span class="glyphicon glyphicon-collapse-down" aria-hidden="true"></span>
				<a role="button" data-toggle="collapse" data-parent="#accordion" href="#stations" aria-expanded="false" aria-controls="stations">
					Potpuni pregled kretanja vlaka
				</a>
			</h3>
		</div>
		<div id="stations" class="panel-collapse collapse" role="tabpanel" aria-labelledby="stations-heading">
			<div class="table-responsive">
				<table class="table">
					<tr>
						<th>Kolodvor</th>
						<th class="text-center">Dolazak</th>
						<th class="text-center">Kašnjenje</th>
						<th class="text-center">Odlazak</th>
						<th class="text-center">Kašnjenje</th>
					</tr>
					<?php foreach ($stations as $station): ?>
						<tr>
							<td><?php echo $station['name'] ?></td>
							<td class="text-center"><?php echo !empty($station['arrivalTime']) ? $station['arrivalTime'] : ''; ?></td>
							<td class="text-center"><?php echo !empty($station['arrivalDelay']) ? $station['arrivalDelay'] : ''; ?></td>
							<td class="text-center"><?php echo !empty($station['departureTime']) ? $station['departureTime'] : ''; ?></td>
							<td class="text-center"><?php echo !empty($station['departureDelay']) ? $station['departureDelay'] : ''; ?></td>
						</tr>
					<?php endforeach; ?>
				</table>
			</div>
		</div>
	</div>
<?php else: ?>
	<div class="alert alert-warning" role="alert">Ne postoji podatak o kretanju vlaka <?php echo $trainNo; ?>.</div>
<?php endif; ?>

<p class="text-center text-muted"><small>Generirano u <?php echo date('H:i:s'); ?>.</small></p>
