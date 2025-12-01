const slidesContainer = document.getElementById('slides');
const slides = Array.from(slidesContainer.children);
const totalOriginalSlides = slides.length; // 7개

const dotsContainer = document.getElementById('dots');

let currentSlide = 1; // 복제 고려, 1부터 시작 (첫 번째 원본 슬라이드)
let slideWidthPercent = 100 / (totalOriginalSlides + 2);

// 1. 앞뒤 슬라이드 복제
const firstClone = slides[0].cloneNode(true);
const lastClone = slides[slides.length - 1].cloneNode(true);
slidesContainer.appendChild(firstClone);
slidesContainer.insertBefore(lastClone, slidesContainer.firstChild);

// 2. 슬라이드 개수 및 너비 세팅
const totalSlidesWithClones = totalOriginalSlides + 2;
slidesContainer.style.width = `${totalSlidesWithClones * 100}%`;
for (let slide of slidesContainer.children) {
   slide.style.flex = `0 0 ${slideWidthPercent}%`;
}

// 3. dot 7개 생성 (원본 슬라이드 수 만큼)
for (let i = 0; i < totalOriginalSlides; i++) {
   const dot = document.createElement('div');
   dot.classList.add('dot');
   dot.addEventListener('click', () => {
      currentSlide = i + 1;
      updateSlidePosition();
      resetAutoSlide();
   });
   dotsContainer.appendChild(dot);
}

// 4. 슬라이드 위치 업데이트 함수
function updateSlidePosition(withTransition = true) {
   if (withTransition) {
      slidesContainer.style.transition = 'transform 0.4s ease-in-out';
   } else {
      slidesContainer.style.transition = 'none';
   }

   const translatePercent = currentSlide * slideWidthPercent;
   slidesContainer.style.transform = `translateX(-${translatePercent}%)`;
   updateDots();
}

// 5. dot 활성화 업데이트
function updateDots() {
   const dots = dotsContainer.children;
   for (let i = 0; i < dots.length; i++) {
      dots[i].classList.remove('active');
   }
   // 현재 슬라이드 (복제 슬라이드 제외) 기준 dot 활성화
   let dotIndex = currentSlide - 1;
   if (dotIndex < 0) dotIndex = totalOriginalSlides - 1;
   if (dotIndex >= totalOriginalSlides) dotIndex = 0;
   dots[dotIndex].classList.add('active');
}

// 6. 이전, 다음 슬라이드 함수
function nextSlide() {
   if (currentSlide >= totalSlidesWithClones - 1) return; // 복제 마지막 슬라이드 도달시 보정 처리됨
   currentSlide++;
   updateSlidePosition();
}

function prevSlide() {
   if (currentSlide <= 0) return; // 복제 첫 슬라이드 도달시 보정 처리됨
   currentSlide--;
   updateSlidePosition();
}

// 7. 트랜지션 끝난 후 무한 루프 보정
slidesContainer.addEventListener('transitionend', () => {
   if (currentSlide === totalSlidesWithClones - 1) {
      // 마지막 복제 슬라이드에 도달하면 첫 원본 슬라이드로 이동
      currentSlide = 1;
      updateSlidePosition(false);
   } else if (currentSlide === 0) {
      // 첫 복제 슬라이드에 도달하면 마지막 원본 슬라이드로 이동
      currentSlide = totalOriginalSlides;
      updateSlidePosition(false);
   }
});

// 8. 자동 슬라이드 (4초 간격)
let autoSlideInterval = setInterval(() => {
   nextSlide();
}, 4000);

function resetAutoSlide() {
   clearInterval(autoSlideInterval);
   autoSlideInterval = setInterval(() => {
      nextSlide();
   }, 4000);
}

// 9. 초기 위치 세팅
updateSlidePosition(false);

// 10. 버튼 클릭에 함수 연결 (html에 onclick 대신 이벤트 리스너 권장)
document.querySelector('.arrow.left').addEventListener('click', () => {
   prevSlide();
   resetAutoSlide();
});

document.querySelector('.arrow.right').addEventListener('click', () => {
   nextSlide();
   resetAutoSlide();
});