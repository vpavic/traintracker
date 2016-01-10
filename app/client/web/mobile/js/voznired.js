$(document).on('pageinit', function() {
	generateMyTrainsList();

	$('form').submit(function() {
		var trainNo = $('input').val();

		if (trainNo) {
			$(this).attr('action', 'mobile/stations/' + trainNo);
		}
		else {
			$(this).removeAttr('action');
		}
	});

	$('.saveTrain').click(function() {
		var trainNo = $(this).attr('data-train-no');

		if (!trainNo) {
			return;
		}

		trainNo = parseInt(trainNo, 10);

		var myTrains = loadMyTrains();

		if (myTrains.indexOf(trainNo) != -1) {
			return;
		}

		myTrains.push(trainNo);

		saveMyTrains(myTrains);

		$('#saveTrain' + trainNo + 'Popup').popup('open');

		generateMyTrainsList();
	});

	$('.removeTrain').click(function() {
		var trainNo = $(this).attr('data-train-no');

		if (!trainNo) {
			return;
		}

		trainNo = parseInt(trainNo, 10);

		var myTrains = loadMyTrains();

		var index = myTrains.indexOf(trainNo);

		if (index == -1) {
			return;
		}

		myTrains.splice(index);

		saveMyTrains(myTrains);

		generateMyTrainsList();
	});
});

function saveMyTrains(myTrains) {
	if (window.localStorage) {
		localStorage.setItem('my_trains', JSON.stringify(myTrains));
	}
	else {
		$.cookie('my_trains', JSON.stringify(myTrains), { expires: 365, path: '/' });
	}
}

function loadMyTrains() {
	var myTrains;

	if (window.localStorage) {
		myTrains = localStorage.getItem('my_trains');
	}
	else {
		myTrains = $.cookie('my_trains');
	}

	if (!myTrains) {
		return [];
	}

	return JSON.parse(myTrains);
}

function generateMyTrainsList() {
	var list = $('ul#my-trains');

	list.hide();
	$('li.my-train').remove();

	var myTrains = loadMyTrains();

	if (myTrains.length > 0) {
		myTrains.sort(function(a, b) {
			return a - b;
		});
		myTrains.forEach(function(train) {
			list.append($('<li class="my-train">' +
				'<a href="mobile/stations/' + train + '">' + train + '</a>' +
				'<a href="#" class="removeTrain" data-train-no="' + train + '">Obriši iz mojih vlakova</a>' +
				'</li>'));
		});

		list.listview('refresh');
		list.show();
	}
}
