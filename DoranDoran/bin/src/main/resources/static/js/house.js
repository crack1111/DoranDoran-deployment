window.addEventListener("load", () => {
	const addressInput = document.getElementById("houseAddress");

	addressInput.addEventListener("click", () => {
		new daum.Postcode({
			oncomplete: function(data) {
				const address = data.address;
				addressInput.value = address;
			}
		}).open();
	});
});
