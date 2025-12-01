window.addEventListener('DOMContentLoaded', () => {

	// --- 남녀 성비 ---
	const canvas = document.getElementById('genderChart');
	const male = Number(canvas.getAttribute('data-male'));
	const female = Number(canvas.getAttribute('data-female'));
	const total = male + female;

	const ctx = canvas.getContext('2d');

	new Chart(ctx, {
		type: 'doughnut',
		data: {
			labels: ['남자', '여자'],
			datasets: [{
				label: '성비',
				data: [(male / total) * 100, (female / total) * 100],
				backgroundColor: [
					'rgba(54, 162, 235, 0.7)',
					'rgba(255, 99, 132, 0.7)'
				],
				borderColor: [
					'rgba(54, 162, 235, 1)',
					'rgba(255, 99, 132, 1)'
				],
				borderWidth: 1
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			plugins: {
				title: {
					display: true,
					text: '성별 비율 차트',
					font: {
						size: 18,
						weight: 'bold'
					}
				},
				legend: {
					position: 'bottom',
				},
				tooltip: {
					callbacks: {
						label: function(context) {
							const label = context.label || '';
							const value = context.raw.toFixed(1);
							return `${label}: ${value}%`;
						}
					}
				}
			}
		}
	});

	// --- 월별 계약 차트 ---
	const contractCanvas = document.getElementById('contractChart');
	const contractCtx = contractCanvas.getContext('2d');
	const contractCounts = JSON.parse(contractCanvas.getAttribute('data-contractCounts'));

	const months = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

	new Chart(contractCtx, {
		type: 'bar',
		data: {
			labels: months,
			datasets: [{
				label: '월별 계약 횟수',
				data: contractCounts,
				backgroundColor: 'rgba(75, 192, 192, 0.7)',
				borderColor: 'rgba(75, 192, 192, 1)',
				borderWidth: 1
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			plugins: {
				title: {
					display: true,
					text: '월별 계약 횟수',
					font: {
						size: 18,
						weight: 'bold'
					}
				},
				legend: {
					display: false
				},
				tooltip: {
					enabled: true
				}
			},
			scales: {
				y: {
					beginAtZero: true,
					stepSize: 1
				}
			}
		}
	});
	// --- 월별 집 등록 차트 ---
	const houseCanvas = document.getElementById('houseChart');
	const houseCtx = houseCanvas.getContext('2d');
	const houseCounts = JSON.parse(houseCanvas.getAttribute('data-houseCounts'));

	new Chart(houseCtx, {
		type: 'line',  
		data: {
			labels: months,
			datasets: [{
				label: '월별 집 등록 횟수',
				data: houseCounts,
				fill: false,  // 선 아래 영역 채우기 안함
				borderColor: 'rgba(255, 159, 64, 1)',
				backgroundColor: 'rgba(255, 159, 64, 1)',
				tension: 0,  // 직선
				borderWidth: 2,
				pointRadius: 4,
				pointHoverRadius: 6,
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			plugins: {
				title: {
					display: true,
					text: '월별 집 등록 횟수',
					font: {
						size: 18,
						weight: 'bold'
					}
				},
				legend: {
					display: false
				},
				tooltip: {
					enabled: true
				}
			},
			scales: {
				y: {
					beginAtZero: true,
					stepSize: 1
				}
			}
		}
	});

});
