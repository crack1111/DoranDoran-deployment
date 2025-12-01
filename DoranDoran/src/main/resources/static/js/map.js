window.onload = function() {
	// 지도를 표시할 div (HTML에서 id="map"인 요소)
	var container = document.getElementById('map');

	// 지도 기본 옵션 설정
	var options = {
		center: new kakao.maps.LatLng(33.450701, 126.570667), // 기본 중심 좌표 (위도, 경도)
		level: 3 // 지도 확대/축소 레벨 (3: 기본, 숫자가 클수록 더 확대)
	};

	// 지도를 생성하고 객체를 반환
	var map = new kakao.maps.Map(container, options);

	// 주소를 좌표로 변환하는 geocoder 객체 생성
	var geocoder = new kakao.maps.services.Geocoder();

	//houseAddress로 지도에서 위치를 찾음
	var houseAddress = document.getElementById('houseAddress').textContent.trim();
	console.log(houseAddress);

	// 주소를 좌표로 변환
	geocoder.addressSearch(houseAddress, function(result, status) {
		if (status === kakao.maps.services.Status.OK) {
			// 변환된 좌표로 지도 위치 설정
			var latlng = new kakao.maps.LatLng(result[0].y, result[0].x);

			// 지도 중심 변경
			map.setCenter(latlng);

			// 마커 표시
			var marker = new kakao.maps.Marker({
				position: latlng
			});

			// 마커 지도에 추가
			marker.setMap(map);
		} else {
			alert('주소를 찾을 수 없습니다.');
		}
	});
};