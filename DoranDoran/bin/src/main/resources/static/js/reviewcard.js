const track = document.querySelector('.review-track');
const card = document.querySelector('.review-card');
const prevBtn = document.querySelector('.prev-btn');
const nextBtn = document.querySelector('.next-btn');

let currentPosition = 0;
const cardWidth = card.offsetWidth + 20;

nextBtn.addEventListener('click', () => {
	const maxScroll = track.scrollWidth - track.parentElement.clientWidth;
	currentPosition = Math.min(currentPosition + cardWidth, maxScroll);
	track.style.transform = `translateX(-${currentPosition}px)`;
});

prevBtn.addEventListener('click', () => {
	currentPosition = Math.max(currentPosition - cardWidth, 0);
	track.style.transform = `translateX(-${currentPosition}px)`;
});

