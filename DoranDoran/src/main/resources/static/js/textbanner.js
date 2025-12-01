window.addEventListener("DOMContentLoaded", e => {
	const textElement = document.querySelector('.word-slider');
	const texts = ["쉐어하우스", "홈스테이"];
	let currentTextIndex = 0;

	function changeText() {
		const currentSpan = textElement.children[currentTextIndex];
		const nextIndex = (currentTextIndex + 1) % texts.length;
		const nextSpan = textElement.children[nextIndex];

		currentTextIndex = nextIndex;

		currentSpan.style.opacity = 0;
		nextSpan.style.opacity = 1;
	}

	setInterval(changeText, 2000);
});