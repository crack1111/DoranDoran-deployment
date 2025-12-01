function deleteContract(button) {
  const contractId = button.getAttribute('data-contract-id');

  if (!confirm('정말 계약 신청을 취소하시겠습니까?')) return;

  fetch(`/mypage/contract/delete/${contractId}`, {
    method: 'DELETE',
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  })
  .then(response => {
    if (response.ok) {
      alert('계약 신청이 취소되었습니다.');
      
      // button이 있는 td -> tr 선택해서 삭제
      const tr = button.closest('tr');
      if (tr) {
        tr.remove();
      }
    } else {
      alert('계약 신청 취소에 실패했습니다.');
    }
  })
  .catch(error => {
    console.error('삭제 요청 실패:', error);
    alert('서버 오류가 발생했습니다.');
  });
}
function denyContract(button) {
  const contractId = button.getAttribute('data-contract-id');  // 이 부분 꼭 있어야 함

  if (!confirm('정말 계약을 취소하시겠습니까?')) return;
  
  fetch(`/mypage/contract/deny/${contractId}`, {
    method: 'PUT',
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  }).then(response => {
    if (response.ok) {
      alert('계약 취소 되었습니다.');
      window.location.reload();
    } else {
      alert('계약 취소 실패');
    }
  }).catch(() => alert('서버 오류'));
}
function acceptContract(button) {
  const contractId = button.getAttribute('data-contract-id');  // 이 부분 꼭 있어야 함

  if (!confirm('정말 계약을 수락하시겠습니까?')) return;
  
  fetch(`/mypage/contract/accept/${contractId}`, {
    method: 'PUT',
    headers: {
      'X-Requested-With': 'XMLHttpRequest'
    }
  }).then(response => {
    if (response.ok) {
      alert('계약이 수락되었습니다.');
      window.location.reload();
    } else {
      alert('계약 수락 실패');
    }
  }).catch(() => alert('서버 오류'));
}