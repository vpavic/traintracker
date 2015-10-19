$(document).ready(function() {
	prepareContact();
	loadStations();
});

function prepareContact() {
	document.getElementById('contact').innerHTML="<n uers=\"znvygb:irqena@ubphvagrearg.pbz\" gnetrg=\"_oynax\">Xbagnxg</n>".replace(/[a-zA-Z]/g, function(c) {
		return String.fromCharCode((c <= "Z" ? 90 : 122) >= (c = c.charCodeAt(0) + 13) ? c : c - 26);
	});
}

function loadStations() {
	$('#data').html('<p class="noinfo"><img src="img/ajax-loader.gif" alt="indicator"/></p>');
	$('#timestamp').html('--:--:--');

	$.getJSON("/stations/211", function(data) {
		if (!$.isEmptyObject(data.stations)) {
			var table = $('<table id="stations"/>');
			var thead = $('<thead/>');
			var tbody = $('<tbody/>');

			table.append(thead);
			table.append(tbody);
			thead.append('<tr><th>Kolodvor</th><th>Dolazak</th><th>Kašnjenje</th><th>Odlazak</th><th>Kašnjenje</th></tr>');

			$.each(data.stations, function(i, station) {
				var tr = $('<tr/>');

				tr.append('<td align="left">' + station.name + '</td>');
				tr.append(station.arrivalTime ? '<td align="center">' + station.arrivalTime + '</td>' : '<td/>');
				tr.append(station.arrivalDelay ? '<td align="center">' + station.arrivalDelay + '</td>' : '<td/>');
				tr.append(station.departureTime ? '<td align="center">' + station.departureTime + '</td>' : '<td/>');
				tr.append(station.departureDelay ? '<td align="center">' + station.departureDelay + '</td>' : '<td/>');

				tbody.append(tr);
			});

			var dataDiv = $('#data');
			dataDiv.html(table);
			tbody.scrollTop(9999);
			dataDiv.scrollTop(9999);
		}
		else {
			$('#data').html('<p class="noinfo">Ne postoji podatak o kretanju vlaka.</p>');
		}

		$('#timestamp').html(data.generatedTime);
	});

	setTimeout(loadStations, 180000);
}
