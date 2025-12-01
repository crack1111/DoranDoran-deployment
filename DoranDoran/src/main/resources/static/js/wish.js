function toggleWish(icon) {
	const houseId = icon.getAttribute('data-house-id');

	fetch(`/wish/toggle/${houseId}`, {
		method: 'POST',
		headers: {
			'X-Requested-With': 'XMLHttpRequest'
		}
	})
		.then(response => {
			if (response.status === 401) {
				const proceed = confirm('로그인이 필요한 서비스입니다. 로그인 하시겠습니까?');
				if (proceed) {
					window.location.href = '/login';
				}
				return Promise.reject('Unauthorized'); // 이후 then 체인 중단
			}

			if (!response.ok) throw new Error('서버 오류');

			return response.text();
		})
		.then(result => {
		    if (icon.classList.contains('far')) {
		      icon.classList.remove('far');
		      icon.classList.add('fas');
		    } else {
		      icon.classList.remove('fas');
		      icon.classList.add('far');
		    }
		  })
		.catch(error => {
			console.error('찜 토글 실패:', error);
		});
}