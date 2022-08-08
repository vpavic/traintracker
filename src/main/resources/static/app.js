let trainNoInput = document.getElementById("train-no-input");
let submitButton = document.getElementById("submit-button");

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
