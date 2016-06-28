<?php if (!empty($stations)): ?><table class="table">
	<tr>
		<th>Kolodvor</th>
		<th class="text-center">Dolazak</th>
		<th class="text-center">Kašnjenje</th>
		<th class="text-center">Odlazak</th>
		<th class="text-center">Kašnjenje</th>
	</tr><?php foreach ($stations as $station): ?>
	<tr>
		<td><?php echo $station['name'] ?></td>
		<td class="text-center"><?php echo !empty($station['arrivalTime']) ? $station['arrivalTime'] : ''; ?></td>
		<td class="text-center"><?php echo !empty($station['arrivalDelay']) ? $station['arrivalDelay'] : ''; ?></td>
		<td class="text-center"><?php echo !empty($station['departureTime']) ? $station['departureTime'] : ''; ?></td>
		<td class="text-center"><?php echo !empty($station['departureDelay']) ? $station['departureDelay'] : ''; ?></td>
	</tr>
<?php endforeach; ?></table>
<?php else: ?>
<div class="alert alert-warning" role="alert">Ne postoji podatak o kretanju vlaka <?php echo $trainNo; ?>.</div>
<?php endif; ?>
