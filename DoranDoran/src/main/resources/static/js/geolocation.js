document.addEventListener('DOMContentLoaded', function () {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            const latitude = position.coords.latitude;
            const longitude = position.coords.longitude;
            console.log(`위도: ${latitude}, 경도: ${longitude}`);
            sessionStorage.setItem('userLocation', JSON.stringify({ latitude, longitude }));
          },
          (error) => {
            console.error("위치정보를 가져올 수 없습니다.", error);
          }
        );
      } else {
        console.error("Geolocation API를 지원하지 않는 브라우저입니다.");
      }
    });