const houseListContainer = document.querySelector('.house-list-container');
let isDown = false;
let startX;
let scrollLeft;

houseListContainer.addEventListener('mousedown', (e) => {
    isDown = true;
    startX = e.pageX - houseListContainer.offsetLeft;
    scrollLeft = houseListContainer.scrollLeft;
});

houseListContainer.addEventListener('mouseleave', () => {
    isDown = false;
});

houseListContainer.addEventListener('mouseup', () => {
    isDown = false;
});

houseListContainer.addEventListener('mousemove', (e) => {
    if (!isDown) return;
    e.preventDefault();
    const x = e.pageX - houseListContainer.offsetLeft;
    const walk = (x - startX) * 2; // 속도 조절
    houseListContainer.scrollLeft = scrollLeft - walk;
});
