document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById('addForm');

  const results = {
    houseName: document.getElementById('houseNameResult'),
    housePrice: document.getElementById('housePriceResult'),
    houseDeposit: document.getElementById('houseDepositResult'),
    houseType: document.getElementById('houseTypeResult'),
    houseMaxpeople: document.getElementById('houseMaxpeopleResult'),
    houseContent: document.getElementById('houseContentResult'),
    houseAddress: document.getElementById('houseAddressResult'),
    photoUpload: document.getElementById('photoUploadResult')
  };

  function showMessage(elem, msg, color = 'red') {
    elem.textContent = msg;
    elem.style.color = color;
    elem.classList.add("visible");
  }

  function hideMessage(elem) {
    elem.textContent = '';
    elem.style.color = '';
    elem.classList.remove("visible");
  }

  form.addEventListener('submit', (event) => {
    event.preventDefault();

    // 입력값들
    const houseName = form.querySelector('input[name="houseName"]').value.trim();
    const housePrice = form.querySelector('input[name="housePrice"]').value.trim();
    const houseDeposit = form.querySelector('input[name="houseDeposit"]').value.trim();
    const houseType = form.querySelector('input[name="houseType"]:checked');
    const houseMaxpeople = form.querySelector('input[name="houseMaxpeople"]').value.trim();
    const houseContent = $('#houseContent').summernote('code').replace(/<[^>]*>?/gm, '').trim();
    const houseAddress = form.querySelector('textarea[name="houseAddress"]').value.trim();
    const uploadFileInput = form.querySelector('input[type="file"][name="uploadFile"]');

    // 결과 메시지 초기화
    Object.values(results).forEach(hideMessage);

    let isValid = true;

    if (!houseName) {
      showMessage(results.houseName, '하우스 이름을 입력해주세요.');
      isValid = false;
    }
    if (!housePrice || isNaN(housePrice) || Number(housePrice) <= 0) {
      showMessage(results.housePrice, '가격(월)을 올바르게 입력해주세요.');
      isValid = false;
    }
    if (!houseDeposit || isNaN(houseDeposit) || Number(houseDeposit) < 0) {
      showMessage(results.houseDeposit, '보증금을 올바르게 입력해주세요.');
      isValid = false;
    }
    if (!houseType) {
      showMessage(results.houseType, '집 종류를 선택해주세요.');
      isValid = false;
    }
    if (!houseMaxpeople || isNaN(houseMaxpeople) || Number(houseMaxpeople) < 1) {
      showMessage(results.houseMaxpeople, '정원은 1명 이상이어야 합니다.');
      isValid = false;
    }
    if (!houseContent) {
      showMessage(results.houseContent, '상세정보를 입력해주세요.');
      isValid = false;
    }
    if (!houseAddress) {
      showMessage(results.houseAddress, '주소를 입력해주세요.');
      isValid = false;
    }
    if (!uploadFileInput || uploadFileInput.files.length === 0) {
      showMessage(results.photoUpload, '최소 1장 이상의 이미지를 추가해주세요.');
      isValid = false;
    }

    if (isValid) {
      form.submit();
    }
  });
});
