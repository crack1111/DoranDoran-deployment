// 1. 실시간 알림 오면 UI에 바로 추가 + 배지 표시
function handleIncomingAlert(alert) {
	console.log("알림 처리 중:", alert);

	const icon = document.getElementById("notificationToggle");
	if (!icon) return;

	// 배지 찾거나 생성
	let badge = icon.querySelector(".notification-badge");
	if (!badge) {
		badge = document.createElement("span");
		badge.className = "notification-badge";
		icon.appendChild(badge);
	}
	badge.style.display = "block"; // 무조건 보이게
	icon.classList.add("has-alert");

	// 알림 리스트에 추가
	const container = document.getElementById("dropdownMenuNotification");
	if (!container) return;

	const alertDiv = document.createElement("div");
	alertDiv.className = "alert-item";

	let link = "";
	if (alert.alertType === "채팅") {
		link = `/chatroom/${alert.alertChatroomid}`;
	} else if (alert.alertType === "계약") {
		link = `/mypage/contract/list/${alert.memberId}`;
	}

	alertDiv.innerHTML = `
    <a href="${link}">
      <span class="alert-message">${alert.alertMessage}</span>
      <span class="alert-timestamp">${alert.alertTimestamp}</span>
    </a>
    <span class="alert-close" data-alert-id="${alert.alertId}" onclick="deleteAlert(this)">×</span>
  `;

	container.prepend(alertDiv);
}

// 2. 알림 리스트 전체 받아오기 + 배지 표시 여부 결정
function fetchAndRenderAlerts() {
	fetch('/alert/item')
		.then(response => response.json())
		.then(alerts => {
			const container = document.getElementById("dropdownMenuNotification");
			if (!container) return;
			container.innerHTML = "";

			alerts.forEach(alert => {
				const alertDiv = document.createElement("div");
				alertDiv.className = "alert-item";

				let link = "";
				if (alert.alertType === "채팅") {
					link = `/chatroom/${alert.alertChatroomid}`;
				} else if (alert.alertType === "계약") {
					link = `/mypage/contract/list/${alert.memberId}`;
				}

				alertDiv.innerHTML = `
          <a href="${link}">
            <span class="alert-message">${alert.alertMessage}</span>
            <span class="alert-timestamp">${alert.alertTimestamp}</span>
          </a>
          <span class="alert-close" data-alert-id="${alert.alertId}" onclick="deleteAlert(this)">×</span>
        `;

				container.appendChild(alertDiv);
			});

			// 배지 표시 토글
			const hasUnread = alerts.some(alert => alert.alertRead === false);
			const icon = document.getElementById("notificationToggle");
			if (!icon) return;

			let badge = icon.querySelector(".notification-badge");
			if (!badge) {
				badge = document.createElement("span");
				badge.className = "notification-badge";
				icon.appendChild(badge);
			}

			if (hasUnread) {
				badge.style.display = "block";
				icon.classList.add("has-alert");
			} else {
				badge.style.display = "none";
				icon.classList.remove("has-alert");
			}
		});
}

// 3. 알림 아이콘 클릭 시 알림 목록 갱신 + 배지 숨김
document.getElementById("notificationToggle").addEventListener("click", () => {
	fetchAndRenderAlerts();
	const icon = document.getElementById("notificationToggle");
	const badge = icon.querySelector(".notification-badge");
	if (badge) badge.style.display = "none";
	icon.classList.remove("has-alert");
});

function deleteAlert(element) {
  const alertId = element.getAttribute("data-alert-id");
  if (!alertId) return;

  fetch(`/alert/delete/${alertId}`, {
    method: 'DELETE'
  })
  .then(response => {
    if (response.ok) {
      // UI에서 알림 삭제
      const alertItem = element.closest(".alert-item");
      if (alertItem) alertItem.remove();

      // 남은 알림 개수에 따라 배지 처리
      const container = document.getElementById("dropdownMenuNotification");
      const icon = document.getElementById("notificationToggle");
      if (container && icon) {
        if (container.children.length === 0) {
          const badge = icon.querySelector(".notification-badge");
          if (badge) badge.style.display = "none";
          icon.classList.remove("has-alert");
        }
      }
    } else {
      console.error("알림 삭제 실패");
    }
  })
  .catch(error => {
    console.error("삭제 요청 오류:", error);
  });
}

