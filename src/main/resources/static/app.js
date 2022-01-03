let trainNoInput = document.getElementById("train-no-input");
let searchForm = document.getElementById("search-form");
let carrierModal = new bootstrap.Modal(document.getElementById("carrier"));
let shortcutsModal = new bootstrap.Modal(document.getElementById("shortcuts"));
let submitButton = document.getElementById("submit-button");

Mousetrap.bind("/", function () {
	trainNoInput.focus();
});
Mousetrap.bind("r", function () {
	searchForm.submit();
});
Mousetrap.bind("c", function () {
	carrierModal.show();
});
Mousetrap.bind("?", function () {
	shortcutsModal.show();
});
Mousetrap.bindGlobal("esc", function () {
	trainNoInput.blur();
});

document.onreadystatechange = function () {
	if (trainNoInput.value) {
		trainNoInput.select();
	}
};

trainNoInput.onkeyup = function () {
	if (this.value.length !== 0) {
		if (submitButton.disabled) {
			submitButton.removeAttribute("disabled");
		}
	}
	else if (!submitButton.disabled) {
		submitButton.setAttribute("disabled", "disabled");
	}
};
