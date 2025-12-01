document.addEventListener('DOMContentLoaded', () => {
	const cityModal = document.getElementById('city-modal');
	const guModal = document.getElementById('gu-modal');
	const openCityBtn = document.getElementById('open-city-modal');
	const closeCityBtn = document.getElementById('close-city-modal');
	const closeGuBtn = document.getElementById('close-gu-modal');
	const cityList = document.getElementById('city-list');
	const guList = document.getElementById('gu-list');
	const tbody = document.querySelector('.house-table tbody');
	let selectedCity = null;

	// ✅ 현재 URL의 type 파라미터 가져오기
	const urlParams = new URLSearchParams(window.location.search);
	const selectedType = urlParams.get('type');

	// ✅ option 파라미터도 가져오기 추가
	const selectedOption = urlParams.get('option');

	openCityBtn.addEventListener('click', () => cityModal.classList.remove('hidden'));
	closeCityBtn.addEventListener('click', () => cityModal.classList.add('hidden'));
	closeGuBtn.addEventListener('click', () => guModal.classList.add('hidden'));

	cityList.querySelectorAll('.city-item').forEach(item => {
		item.addEventListener('click', () => {
			selectedCity = item.getAttribute('data-city');
			cityModal.classList.add('hidden');

			// 구 목록 로드
			guList.innerHTML = '';
			fetch('/house/address/gu?city=' + encodeURIComponent(selectedCity))
				.then(res => res.json())
				.then(data => {
					const allItem = document.createElement('li');
					allItem.textContent = '전체';
					allItem.classList.add('gu-item');
					allItem.setAttribute('data-gu', '');
					guList.appendChild(allItem);
					data.forEach(gu => {
						const li = document.createElement('li');
						li.textContent = gu;
						li.classList.add('gu-item');
						li.setAttribute('data-gu', gu);
						guList.appendChild(li);
					});

					guModal.classList.remove('hidden');

					// 구 클릭 이벤트
					guList.querySelectorAll('.gu-item').forEach(guItem => {
						guItem.addEventListener('click', () => {
							const selectedGu = guItem.getAttribute('data-gu');
							guModal.classList.add('hidden');
							tbody.innerHTML = '';

							// ✅ city, gu, type, option 모두 포함하여 검색 요청
							let searchUrl = `/house/search?city=${encodeURIComponent(selectedCity)}&gu=${encodeURIComponent(selectedGu)}`;
							if (selectedType) {
								searchUrl += `&type=${encodeURIComponent(selectedType)}`;
							}
							// option 파라미터도 붙이기
							if (selectedOption) {
								searchUrl += `&option=${encodeURIComponent(selectedOption)}`;
							}

							fetch(searchUrl)
								.then(res => res.json())
								.then(houses => {
									houses.forEach(house => {
										const tr = document.createElement('tr');

										// 이미지 셀
										const tdImg = document.createElement('td');
										const aImg = document.createElement('a');
										aImg.href = `/house/detail/${house.houseId}`;
										const img = document.createElement('img');
										img.src = `/upload/${house.photo[0].photoUuid}_${house.photo[0].photoFilename}`;
										img.alt = '하우스 이미지';
										img.style.width = '100px';
										img.style.height = '80px';
										img.style.objectFit = 'cover';
										aImg.appendChild(img);
										tdImg.appendChild(aImg);
										tr.appendChild(tdImg);

										// 이름 셀
										const tdName = document.createElement('td');
										const aName = document.createElement('a');
										aName.href = `/house/detail/${house.houseId}`;
										aName.textContent = house.houseName;
										tdName.appendChild(aName);
										tr.appendChild(tdName);

										// 유형 셀
										const tdType = document.createElement('td');
										tdType.textContent = house.houseType;
										tr.appendChild(tdType);

										// 가격 셀
										const tdPrice = document.createElement('td');
										tdPrice.textContent = house.housePrice;
										tr.appendChild(tdPrice);

										// 보증금 셀
										const tdDeposit = document.createElement('td');
										tdDeposit.textContent = house.houseDeposit;
										tr.appendChild(tdDeposit);

										// 주소 셀
										const tdAddress = document.createElement('td');
										tdAddress.textContent = house.houseAddress;
										tr.appendChild(tdAddress);

										// 평점 셀
										const tdRate = document.createElement('td');
										tdRate.textContent = house.houseRate;
										tr.appendChild(tdRate);

										tbody.appendChild(tr);
									});
								});
						});
					});
				});
		});
	});
});
