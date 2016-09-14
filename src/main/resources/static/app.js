var form = $('#search-form');
var input = $('#train-no-input');
var button = $('#submit-button');
var starred = $('#starred-trains');
var panel = $('#starred-trains-panel');
var list = $('#starred-trains-list');
var data = $('#train-data');
var starLink;

focusInput();
generateMyTrainsList();

Mousetrap.bind({
	'/': focusInput,
	's': toggleStarred,
	'h': toggleDetails,
	'r': submitSearch,
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
			starLink = $('#star');

			if (containsTrain(trainNo)) {
				prepareUnstarred(starLink);
			}
			else {
				prepareStarred(starLink);
			}

			starLink.click(function(event) {
				event.preventDefault();
				var trainNo = $(this).attr('data-train-no');

				if (containsTrain(trainNo)) {
					removeTrain(trainNo);
					prepareStarred(starLink);
				}
				else {
					addTrain(trainNo);
					prepareUnstarred(starLink);
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

function toggleStarred() {
	starred.collapse('toggle');
}

function toggleDetails() {
	$('#stations').collapse('toggle');
}

function submitSearch() {
	form.submit();
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
	panel.hide();
	list.empty();

	var myTrains = loadMyTrains();

	if (myTrains.length > 0) {
		myTrains.sort(function(a, b) {
			return a - b;
		});

		myTrains.forEach(function(train) {
			list.append(
				'<li class="list-group-item">' +
				'<a href="#" class="fetch-my-train" data-train-no="' + train + '">' + train + '</a>' +
				'<a href="#" class="remove-my-train pull-right" title="' + messages['saved.remove'] + '" data-train-no="' + train + '">' +
				'<span class="glyphicon glyphicon-remove"></span>' +
				'</a>' +
				'</li>');
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
			if (trainNo == starLink.attr('data-train-no')) {
				prepareStarred(starLink);
			}
			generateMyTrainsList();
		});
	}
	else {
		panel.show();
	}
}

function prepareUnstarred(link) {
	link.attr('title', messages['saved.remove'])
		.find('span.glyphicon')
		.removeClass('glyphicon-star-empty')
		.addClass('glyphicon-star');
}

function prepareStarred(link) {
	link.attr('title', messages['saved.add'])
		.find('span.glyphicon')
		.removeClass('glyphicon-star')
		.addClass('glyphicon-star-empty');
}
