function renderMemberTable(members) {
	const tbody = document.getElementById('member-body');
	tbody.innerHTML = '';
	if (members.length === 0) {
		const tr = document.createElement('tr');
		const td = document.createElement('td');
		td.colSpan = 7;
		td.textContent = '회원 없음';
		tr.appendChild(td);
		tbody.appendChild(tr);
		return;
	}
	members.forEach(item => {
		const tr = document.createElement('tr');
		tr.innerHTML = `
      <td>${item.memberId}</td>
      <td>${item.memberName}</td>
      <td>${item.memberGender}</td>
      <td>${item.memberEmail}</td>
      <td>${item.memberRole}</td>
      <td>${item.memberNickname}</td>
      <td>
        <a href="/update?memberId=${item.memberId}">변경</a> |
        <a href="/delete?memberId=${item.memberId}" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
      </td>
    `;
		tbody.appendChild(tr);
	});
}

function renderHouseTable(houses) {
	const tbody = document.getElementById('house-body');
	tbody.innerHTML = '';
	if (houses.length === 0) {
		const tr = document.createElement('tr');
		const td = document.createElement('td');
		td.colSpan = 8;
		td.textContent = '하우스 없음';
		tr.appendChild(td);
		tbody.appendChild(tr);
		return;
	}
	houses.forEach(item => {
		const tr = document.createElement('tr');
		tr.innerHTML = `
      <td>${item.houseId}</td>
      <td><a href="/house/detail/${item.houseId}">${item.houseName}</a></td>
      <td>${item.houseType}</td>
      <td>${item.housePrice}</td>
      <td>${item.houseDeposit}</td>
      <td>${item.houseAddress}</td>
      <td>${item.houseRate}</td>
      <td>
        <a href="/house/update/${item.houseId}">변경</a> |
        <a href="/house/delete/${item.houseId}" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
      </td>
    `;
		tbody.appendChild(tr);
	});
}

function renderContractTable(contracts) {
	const tbody = document.getElementById('contract-body');
	tbody.innerHTML = '';
	if (contracts.length === 0) {
		const tr = document.createElement('tr');
		const td = document.createElement('td');
		td.colSpan = 7;
		td.textContent = '계약 없음';
		tr.appendChild(td);
		tbody.appendChild(tr);
		return;
	}
	contracts.forEach(item => {
		const tr = document.createElement('tr');
		tr.innerHTML = `
      <td><a href="/house/detail/${item.house.houseId}">${item.house.houseName}</a></td>
      <td>${item.owner.memberName}</td>
      <td>${item.tenant.memberName}</td>
      <td>${item.startDate}</td>
      <td>${item.endDate}</td>
      <td>${item.contractDate}</td>
      <td>${item.contractStatus}</td>
    `;
		tbody.appendChild(tr);
	});
}

function renderPagination(containerId, currentPage, totalPages, onPageClick) {
	const container = document.getElementById(containerId);
	container.innerHTML = '';

	const createPageButton = (text, page, disabled = false, active = false) => {
		const btn = document.createElement('button');
		btn.textContent = text;
		btn.disabled = disabled;
		if (active) btn.classList.add('active');
		btn.onclick = () => onPageClick(page);
		return btn;
	};

	container.appendChild(createPageButton('« 이전', currentPage - 1, currentPage === 1));

	for (let i = 1; i <= totalPages; i++) {
		container.appendChild(createPageButton(i, i, false, i === currentPage));
	}

	container.appendChild(createPageButton('다음 »', currentPage + 1, currentPage === totalPages));
}

function loadMemberPage(page = 1) {
	fetch(`/admin/fragment/members?page=${page}`)
		.then(res => res.json())
		.then(data => {
			renderMemberTable(data.members);
			renderPagination('member-pagination', data.currentPage, data.totalPages, loadMemberPage);
		})
		.catch(err => console.error(err));
}

function loadHousePage(page = 1) {
	fetch(`/admin/fragment/houses?page=${page}`)
		.then(res => res.json())
		.then(data => {
			renderHouseTable(data.houses);
			renderPagination('house-pagination', data.currentPage, data.totalPages, loadHousePage);
		})
		.catch(err => console.error(err));
}

function loadContractPage(page = 1) {
	fetch(`/admin/fragment/contracts?page=${page}`)
		.then(res => res.json())
		.then(data => {
			renderContractTable(data.contracts);
			renderPagination('contract-pagination', data.currentPage, data.totalPages, loadContractPage);
		})
		.catch(err => console.error(err));
}

document.addEventListener('DOMContentLoaded', () => {
	loadMemberPage();
	loadHousePage();
	loadContractPage();
});
