if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      const lat = position.coords.latitude;
      const lon = position.coords.longitude;

      if (!window.location.search.includes('latitude')) {
        const url = new URL(window.location.href);
        url.searchParams.set('latitude', lat);
        url.searchParams.set('longitude', lon);
        window.location.href = url.toString();
      } else {
        // URL에서 위치 파라미터 제거 (새로고침 후 한 번만 실행됨)
        const cleanUrl = new URL(window.location.href);
        cleanUrl.searchParams.delete('latitude');
        cleanUrl.searchParams.delete('longitude');
        window.history.replaceState({}, document.title, cleanUrl.pathname);
      }
    });
  }