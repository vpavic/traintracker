$(function() {
	$('body').on('click', '.saveTrain', function() {
		var trainNo = loadTrainNo();

		if (!trainNo) {
			return;
		}

		var myTrains = loadMyTrainsCookie();

		if (myTrains.indexOf(trainNo) != -1) {
			return;
		}

		myTrains.push(trainNo);

		saveMyTrainsCookie(myTrains);

		$('#saveTrain' + trainNo + 'Popup').popup('open');
	});

	$('body').on('click', '.removeTrain', function() {
		var trainNo = $(this).attr('train-no');

		if (!trainNo) {
			return;
		}

		var myTrains = loadMyTrainsCookie();

		var index = myTrains.indexOf(trainNo);

		if (index > -1) {
			myTrains.splice(index);
		}

		saveMyTrainsCookie(myTrains);

		$('#removeTrain' + trainNo + 'Popup').popup('open');
	});

	$('body').on('submit', 'form', function() {
		saveTrainNo($('input').val());
	});

	function saveTrainNo(trainNo) {
		localStorage.setItem('trainNo', trainNo);
	}

	function loadTrainNo() {
		return localStorage.getItem('trainNo');
	}

	function saveMyTrainsCookie(myTrains) {
		$.cookie('my_trains', JSON.stringify(myTrains), { expires: 365, path: '/' });
	}

	function loadMyTrainsCookie() {
		var myTrains = $.cookie('my_trains');

		if (myTrains) {
			return JSON.parse(myTrains);
		}
		else {
			return [];
		}
	}
})
