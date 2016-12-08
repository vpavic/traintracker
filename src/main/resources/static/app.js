var form = $('#search-form');
var input = $('#train-no-input');
var button = $('#submit-button');
var saved = $('#saved-trains');
var data = $('#train-data');
var saveLink;

focusInput();
generateMyTrainsList();

Mousetrap.bind({
	'/': focusInput,
	'h': toggleDetails,
	'r': submitSearch,
	'c': countrySelector,
	'?': displayShortcuts
});

Mousetrap.bindGlobal('esc', blurInput);

input.keyup(function() {
	var disabled = button.is(':disabled');
	var empty = $(this).val().length == 0;

	if (!disabled && empty) {
		button.attr('disabled', 'disabled');
	}
	else if (disabled && !empty) {
		button.removeAttr('disabled');
	}
});

form.submit(function(event) {
	event.preventDefault();
	var trainNo = input.val();

	if (trainNo.length != 0) {
		data.load('hr/' + trainNo, function() {
			$('#generated-time').text(messages['voyage.generated'].replace('{0}', new Date().toTimeString()));

			input.blur();
			saveLink = $('#save');

			if (containsTrain(trainNo)) {
				prepareNotSaved(saveLink);
			}
			else {
				prepareSaved(saveLink);
			}

			saveLink.click(function(event) {
				event.preventDefault();
				var trainNo = $(this).attr('data-train-no');

				if (containsTrain(trainNo)) {
					removeTrain(trainNo);
					prepareSaved(saveLink);
				}
				else {
					addTrain(trainNo);
					prepareNotSaved(saveLink);
				}

				generateMyTrainsList();
			});
		});
	}
});

function focusInput() {
	input.focus();
}

function blurInput() {
	input.blur();
}

function toggleDetails() {
	$('#stations').collapse('toggle');
}

function submitSearch() {
	form.submit();
}

function countrySelector() {
	$('#country').modal('show');
}

function displayShortcuts() {
	$('#shortcuts').modal('show');
}

function saveMyTrains(myTrains) {
	localStorage.setItem('my_trains', JSON.stringify(myTrains));
}

function loadMyTrains() {
	return JSON.parse(localStorage.getItem('my_trains')) || [];
}

function addTrain(trainNo) {
	var myTrains = loadMyTrains();

	if (myTrains.indexOf(trainNo) != -1) {
		return;
	}

	myTrains.push(trainNo);
	saveMyTrains(myTrains);
}

function removeTrain(trainNo) {
	var myTrains = loadMyTrains();
	var index = myTrains.indexOf(trainNo);

	if (index == -1) {
		return;
	}

	myTrains.splice(index);
	saveMyTrains(myTrains);
}

function containsTrain(trainNo) {
	var myTrains = loadMyTrains();
	return myTrains.indexOf(trainNo) != -1;
}

function generateMyTrainsList() {
	saved.empty();
	var myTrains = loadMyTrains();

	if (myTrains.length > 0) {
		myTrains.sort(function(a, b) {
			return a - b;
		});

		myTrains.forEach(function(train) {
			if (!saved.is(':empty')) {
				saved.append('&nbsp;')
			}
			saved.append('<button class="btn btn-primary btn-sm fetch-my-train" data-train-no="'
				+ train + '">' + train + '</button>');
		});

		$('.fetch-my-train').click(function(event) {
			var trainNo = $(this).attr('data-train-no');
			event.preventDefault();
			input.val(trainNo);
			input.keyup();
			form.submit();
		});

		$('.remove-my-train').click(function(event) {
			var trainNo = $(this).attr('data-train-no');
			event.preventDefault();
			removeTrain(trainNo);
			if (trainNo == saveLink.attr('data-train-no')) {
				prepareSaved(saveLink);
			}
			generateMyTrainsList();
		});
	}
	else {
		saved.append('<p class="text-left text-muted">' + messages['saved.empty'] + '</p>')
	}
}

function prepareNotSaved(link) {
	link.attr('title', messages['saved.remove'])
		.find('span.glyphicon')
		.removeClass('glyphicon-star-empty')
		.addClass('glyphicon-star');
}

function prepareSaved(link) {
	link.attr('title', messages['saved.add'])
		.find('span.glyphicon')
		.removeClass('glyphicon-star')
		.addClass('glyphicon-star-empty');
}
