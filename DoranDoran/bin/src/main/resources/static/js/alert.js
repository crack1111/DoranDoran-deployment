document.addEventListener("DOMContentLoaded", function() {
	connectWebSocketAlert();
});

let socket = new SockJS("/ws"); // WebSocket 엔드포인트
let stompClient = Stomp.over(socket);

function connectWebSocketAlert() {
	stompClient.connect({}, function(frame) {
		console.log("✅ STOMP 연결됨: ", frame);

		stompClient.subscribe("/user/queue/alerts", function(message) {
			console.log("알림 수신", message);
			   const alert = JSON.parse(message.body);
			   handleIncomingAlert(alert);

			try {
				const alert = JSON.parse(message.body);
				handleIncomingAlert(alert);
			} catch (e) {
				console.error("⚠️ 메시지 파싱 실패:", e);
			}
		});

		console.log("📡 알림 구독 완료: /user/queue/alerts");
	});
}

function handleIncomingAlert(alert) {
	console.log("알림 처리 중:", alert);

	let badge = document.querySelector(".notification-badge");
	if (!badge) {
		// 없으면 새로 생성해서 붙임 (예: 아이콘 옆에)
		const icon = document.querySelector("#notificationToggle");
		if (icon) {
			badge = document.createElement("span");
			badge.className = "notification-badge";
			icon.appendChild(badge);
		}
	}
	badge.style.display = "inline-block";

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
document.getElementById("readAlert").addEventListener("click", function() {
	fetch('/alert/read', {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		}
	}).then(response => {
		if (response.ok) {
			console.log("전체 알림 읽음 처리 완료");
			// 배지 숨기기 등 UI 업데이트
			const badge = document.querySelector(".notification-badge");
			if (badge) badge.style.display = "none";
		} else {
			console.error("알림 읽음 처리 실패");
		}
	});
});
