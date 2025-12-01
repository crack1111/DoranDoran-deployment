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
		const url = new URL(window.location.href);
		url.searchParams.delete('latitude');
		url.searchParams.delete('longitude');
		window.history.replaceState({}, document.title, url.toString());
	}
  });
}