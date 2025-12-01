document.addEventListener("DOMContentLoaded", () => {
  const idInput = document.getElementById("memberId");
  const pwInput = document.getElementById("memberPassword");
  const pwConfirmInput = document.getElementById("passwordConfirm");
  const nickInput = document.getElementById("memberNickname");
  const emailInput = document.getElementById("memberEmail");
  
  const idResult = document.getElementById("idCheckResult");
  const pwResult = document.getElementById("passwordCheckResult");
  const pwConfirmResult = document.getElementById("passwordConfirmResult");
  const nickResult = document.getElementById("nickCheckResult");
  const emailResult = document.getElementById("emailCheckResult");

  function showResult(elem, message, color) {
      elem.textContent = message;
      elem.style.color = color;
      elem.classList.add("visible");
    }

  function hideResult(elem) {
      elem.textContent = "";
      elem.classList.remove("visible");
    }
  
  // debounce 함수 (입력 멈춘 후 500ms 뒤 실행)
  function debounce(fn, delay) {
    let timer;
    return function (...args) {
      clearTimeout(timer);
      timer = setTimeout(() => fn.apply(this, args), delay);
    };
  }
  
  function validateMemberId(value) {
    const regex = /^[a-zA-Z0-9]{4,12}$/;
    if (!regex.test(value)) return "아이디는 4~12자 영문/숫자여야 합니다.";
    return null;
  }

  function validatePassword(value) {
    const regex = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=.{8,16})/;
    if (!regex.test(value)) return "비밀번호는 8~16자 사이, 영문, 숫자, 특수문자 포함해야 합니다.";
    return null;
  }

  function validateEmail(value) {
    const regex = /^(?:\w+\.?)*\w+@(?:\w+\.)+\w+$/;
    if (!regex.test(value)) return "이메일 형식이 올바르지 않습니다.";
    return null;
  }
  
  function validateNickname(value) {
      const regex = /^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$/;
      if (!regex.test(value)) return "닉네임은 특수문자를 제외한 2~10자리여야 합니다.";
      return null;
    }

  // 중복 검사 호출 함수
  async function checkDuplicate(type, value, resultElem) {
      if (!resultElem) return;
      if (!value) {
        hideResult(resultElem);
        return;
      }
      try {
        const res = await fetch(`/api/check-duplicate?type=${type}&value=${encodeURIComponent(value)}`);
        if (!res.ok) throw new Error("서버 오류");
        const data = await res.json();

        if (data.exists) {
          showResult(resultElem, `${type === "id" ? "아이디가" : type === "nickname" ? "닉네임이" : "이메일이"} 이미 사용중입니다.`, "red");
        } else {
          showResult(resultElem, `사용 가능한 ${type === "id" ? "아이디" : type === "nickname" ? "닉네임" : "이메일"}입니다.`, "green");
        }
      } catch (err) {
        showResult(resultElem, "서버 오류가 발생했습니다.", "orange");
      }
    }

  // 이벤트 핸들러 (debounce 적용)
  const debouncedIdCheck = debounce(() => checkDuplicate("id", idInput.value.trim(), idResult), 500);
  const debouncedNickCheck = debounce(() => checkDuplicate("nickname", nickInput.value.trim(), nickResult), 500);
  const debouncedEmailCheck = debounce(() => checkDuplicate("email", emailInput.value.trim(), emailResult), 500);

  // 이벤트 등록
  idInput.addEventListener("input", () => {
    const val = idInput.value.trim();
    const error = validateMemberId(val);

	if (!val) {
	    hideResult(idResult);
	    return;
	}
	
    if (error) {
      showResult(idResult, error, "red");
    } else {
      hideResult(idResult);
      debouncedIdCheck(); // 중복 검사 실행
    }
  });
  
  pwInput.addEventListener("input", () => {
    const val = pwInput.value.trim();
    const error = validatePassword(val);
	
	if (!val) {
		hideResult(pwResult);
		return;
	}
	
    if (error) {
      showResult(pwResult, error, "red");
    } else {
      showResult(pwResult, "사용 가능한 비밀번호입니다.", "green");
    }

    // 비밀번호 변경 시 확인 필드도 다시 비교
    comparePasswords();
  });

  // 비밀번호 확인 입력 시
  pwConfirmInput.addEventListener("input", comparePasswords);

  function comparePasswords() {
    const password = pwInput.value.trim();
    const confirm = pwConfirmInput.value.trim();

    if (!confirm) {
      hideResult(pwConfirmResult);
      return;
    }

    if (password === confirm) {
      showResult(pwConfirmResult, "비밀번호가 일치합니다.", "green");
    } else {
      showResult(pwConfirmResult, "비밀번호가 일치하지 않습니다.", "red");
    }
  }
	
  nickInput.addEventListener("input", () => {
    const val = nickInput.value.trim();
    const error = validateNickname(val);

	if (!val) {
	    hideResult(nickResult);
	    return;
	}
	
    if (error) {
      showResult(nickResult, error, "red");
    } else {
      hideResult(nickResult);
      debouncedNickCheck();
    }
  });

  emailInput.addEventListener("input", () => {
    const val = emailInput.value.trim();
    const error = validateEmail(val);

	if (!val) {
	    hideResult(emailResult);
	    return;
	}
	
    if (error) {
      showResult(emailResult, error, "red");
    } else {
      hideResult(emailResult);
      debouncedEmailCheck();
    }
  });
});

document.addEventListener("DOMContentLoaded", () => {
  // 모든 input에 input 이벤트 걸기 (주소 포함)
  document.querySelectorAll("input").forEach(input => {
    input.addEventListener("input", () => {
      // nextElementSibling이 .valid인지 체크 후 숨기기
      const nextValidSpan = input.nextElementSibling;
      if (nextValidSpan && nextValidSpan.classList.contains("valid")) {
        nextValidSpan.textContent = "";
        nextValidSpan.style.display = "none";
      }
    });
  });

  // 성별 라디오 버튼 change 이벤트
  document.querySelectorAll("input[name='memberGender']").forEach(radio => {
    radio.addEventListener("change", () => {
      const genderMessage = document.getElementById("noselectGender");
      if (genderMessage) {
        genderMessage.textContent = "";
        genderMessage.style.display = "none";
      }
    });
  });
});