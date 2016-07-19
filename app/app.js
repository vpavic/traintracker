var input = $('#trainNo');
var submit = $('#searchSubmit');
var form = $('#search')

focusInput();
generateMyTrainsList();

Mousetrap.bind({
	'/': focusInput,
	's': focusInput,
	'e': toggleDetails,
	'r': submitSearch,
	'?': displayShortcuts
});

Mousetrap.bindGlobal('esc', blurInput);

input.keyup(function() {
	var disabled = submit.is(':disabled');
	var empty = $(this).val().length == 0;

	if (!disabled && empty) {
		submit.attr('disabled', 'disabled');
	}
	else if (disabled && !empty) {
		submit.removeAttr('disabled');
	}
});

form.submit(function(event) {
	event.preventDefault();
	var trainNo = input.val();

	if (trainNo.length != 0) {
		$('#data').load(trainNo, function() {
			input.blur();
			var link = $('#star');

			if (containsTrain(trainNo)) {
				star(link);
			}
			else {
				unstar(link);
			}

			link.click(function(event) {
				event.preventDefault();
				var trainNo = String($(this).data('train-no'));

				if (containsTrain(trainNo)) {
					removeTrain(trainNo);
					unstar(link);
				}
				else {
					addTrain(trainNo);
					star(link);
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
	var panel = $('#my-trains');
	var body = panel.find('div.panel-body');
	var list = panel.find('ul.list-group');

	body.hide();
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
				'<a href="#" class="remove-my-train pull-right" title="Ukloni iz mojih vlakova" data-train-no="' + train + '">' +
				'<span class="glyphicon glyphicon-remove"></span>' +
				'</a>' +
				'</li>');
		});

		$('.fetch-my-train').click(function(event) {
			var trainNo = String($(this).data('train-no'));
			event.preventDefault();
			input.val(trainNo);
			input.keyup();
			form.submit();
		});

		$('.remove-my-train').click(function(event) {
			var trainNo = String($(this).data('train-no'));
			event.preventDefault();
			removeTrain(trainNo);
			unstar($('#star'));
			generateMyTrainsList();
		});
	}
	else {
		body.show();
	}
}

function star(link) {
	link.attr('title', 'Ukloni iz mojih vlakova')
		.find('span.glyphicon')
		.removeClass('glyphicon-star-empty')
		.addClass('glyphicon-star');
}

function unstar(link) {
	link.attr('title', 'Dodaj u moje vlakove')
		.find('span.glyphicon')
		.removeClass('glyphicon-star')
		.addClass('glyphicon-star-empty');
}
