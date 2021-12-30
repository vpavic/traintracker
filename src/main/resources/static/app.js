var form = $('#search-form');
var input = $('#train-no-input');
var button = $('#submit-button');

Mousetrap.bind('/', function () {
	input.focus();
});
Mousetrap.bind('h', function () {
	$('#stations').collapse('toggle');
});
Mousetrap.bind('r', function () {
	form.submit();
});
Mousetrap.bind('c', function () {
	$('#carrier').modal('show');
});
Mousetrap.bind('?', function () {
	$('#shortcuts').modal('show');
});
Mousetrap.bindGlobal('esc', function () {
	input.blur();
});

$(document).ready(function () {
	if (input.val()) {
		input.keyup();
	}
});

input.keyup(function () {
	var value = $(this).val();
	var empty = value.length == 0;
	var disabled = button.is(':disabled');

	if (!empty) {
		form.attr('action', value);
		if (disabled) {
			button.removeAttr('disabled');
		}
	}
	else if (!disabled) {
		form.removeAttr('action');
		button.attr('disabled', 'disabled');
	}
});
