document.addEventListener("DOMContentLoaded", function() {
	const dropdown = document.getElementById("userDropdown");
	const toggle = document.getElementById("dropdownToggle");
	const dropdownMenuUser = document.getElementById("dropdownMenuUser");

	const notificationDropdown = document.getElementById("notificationDropdown");
	const notificationToggle = document.getElementById("notificationToggle");
	const notificationMenu = document.getElementById("dropdownMenuNotification");

	const likeDropdown = document.getElementById("likeDropdown");
	const likeToggle = document.getElementById("likeToggle");
	const likeMenu = document.getElementById("dropdownMenuLike");

	// 모든 드롭다운 닫기
	function closeAllDropdowns() {
		if (dropdown) dropdown.classList.remove("active");
		if (notificationDropdown) notificationDropdown.classList.remove("active");
		if (likeDropdown) likeDropdown.classList.remove("active");
	}

	// 유저 메뉴
	if (toggle && dropdown && dropdownMenuUser) {
		toggle.addEventListener("click", function(e) {
			e.stopPropagation();
			const isActive = dropdown.classList.contains("active");
			closeAllDropdowns();
			if (!isActive) dropdown.classList.add("active");
		});

		dropdownMenuUser.addEventListener("click", function(e) {
			e.stopPropagation();
		});
	}

	// 알림 메뉴
	if (notificationToggle && notificationDropdown && notificationMenu) {
		notificationToggle.addEventListener("click", function(e) {
			e.stopPropagation();
			const isActive = notificationDropdown.classList.contains("active");
			closeAllDropdowns();
			if (!isActive) notificationDropdown.classList.add("active");
		});

		notificationMenu.addEventListener("click", function(e) {
			e.stopPropagation();
		});
	}

	// 찜하기 메뉴
	if (likeToggle && likeDropdown && likeMenu) {
		likeToggle.addEventListener("click", function(e) {
			e.stopPropagation();
			const isActive = likeDropdown.classList.contains("active");
			closeAllDropdowns();
			if (!isActive) likeDropdown.classList.add("active");
		});

		likeMenu.addEventListener("click", function(e) {
			e.stopPropagation();
		});
	}

	// 바깥 클릭 시 닫기
	document.addEventListener("click", function() {
		closeAllDropdowns();
	});
});
