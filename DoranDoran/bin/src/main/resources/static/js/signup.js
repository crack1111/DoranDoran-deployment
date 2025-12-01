window.addEventListener("load", () => {
	const addressInput = document.getElementById("memberAddress");

	addressInput.addEventListener("click", e => {
		new daum.Postcode({
			oncomplete : function(data) {
				const address = data.address;
				addressInput.value = address;
				
				const nextValidSpan = addressInput.nextElementSibling;
					if (nextValidSpan && nextValidSpan.classList.contains("valid")) {
				    	nextValidSpan.textContent = "";
				        nextValidSpan.style.display = "none";
				    }
			}
		}).open();
	});
});
