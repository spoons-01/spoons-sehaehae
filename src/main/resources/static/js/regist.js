function validEmail(email) {
    let regex = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");
    return regex.test(email);
}

window.onload = function () {
    /* event.js */
    if (document.getElementById("regist")) {
        const $regist = document.getElementById("regist");
        $regist.onclick = function () {
            location.href = "/user/member/regist";
        }
    }

    if (document.getElementById("duplicationCheck")) {

        const $duplication = document.getElementById("duplicationCheck");

        $duplication.onclick = function () {
            let memberId = document.getElementById("memberId").value.trim();

            /**
             * Promis => 실행 방법 중
             *  시점 1. 시작 - fetch or ajax
             *  시점 2. 무조건 실행 - Then
             *  시점 3. 성공 - Resolve
             *  시점 4. 실패 - Reject
             *  시점 5. 완료 - Done
             */

            fetch("/user/member/idDupCheck", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                body: JSON.stringify({memberId: memberId})
            })
                /**
                 * 성공 또는 완료케이스
                 */
                .then(result => result.text())
                .then(result => alert(result))
                /**
                 * 오류가 발생 시  에러 정보 결과를 추출(.text())
                 */
                .catch(
                    (error) => error.text()
                        // 알림창을 실행
                        .then((res) => alert(res))
                );

        }
    }

    /* 비밀번호 유효성 검사 */
    var passwordInput = document.getElementById('passwordInput');
    var passwordMessage = document.getElementById('passwordMessage');

    function validatePassword() {
        var password = passwordInput.value;

        if (password.length < 8) {
            passwordMessage.textContent = '비밀번호는 8자 이상이어야 합니다.';
            passwordMessage.style.color = 'red';
            return;
        }

        var hasLetter = /[a-zA-Z]/.test(password);
        var hasNumber = /[0-9]/.test(password);
        var hasSpecialChar = /[!@#$%^&*()_+{}\[\]:;<>,.?~\\-]/.test(password);

        if (!hasLetter || !hasNumber || !hasSpecialChar) {
            passwordMessage.textContent = '비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다.';
            passwordMessage.style.color = 'red';
            return;
        }

        passwordMessage.textContent = '';
    }

    // 비밀번호 입력 필드에 이벤트 리스너를 추가하여 유효성을 확인
    passwordInput.addEventListener('keyup', validatePassword);

    /* 비밀번호 확인 */
    var passwordInput = document.getElementById('passwordInput');
    var passwordCheckInput = document.getElementById('passwordCheck');

    var message = passwordCheckMessage;

    function passwordCheck() {
        var password = passwordInput.value;
        var passwordCheck = passwordCheckInput.value;

        if (password === passwordCheck) {
            message.textContent = '비밀번호가 일치합니다.'
            message.classList.remove('password-mismatch');
            message.classList.add('password-match');
        } else {
            message.textContent = '비밀번호가 일치하지 않습니다. 다시 확인해주세요.'
            message.classList.remove('password-match');
            message.classList.add('password-mismatch');
        }
    }

    passwordCheckInput.addEventListener('keyup', passwordCheck);


    /* 이름 유효성검사 */
    var nameInput = document.getElementById('nameInput');
    var nameCheckMessage = document.getElementById('nameCheckMessage');

    function nameCheck() {
        var name = nameInput.value;

        if (name.length > 15) {
            nameCheckMessage.textContent = '이름은 15글자를 초과할 수 없습니다.';
            nameCheckMessage.style.color = 'red';
            return;
        }

        var hasLetter = /^[가-힣]+$/.test(name);
        if (!hasLetter) {
            nameCheckMessage.textContent = '이름은 한글로만 입력되어야 합니다.';
            nameCheckMessage.style.color = 'red';
            return;
        }

        nameCheckMessage.textContent = '';
    }

    nameInput.addEventListener('keyup', nameCheck);

    /* 닉네임 유효성 검사 */
    var nicknameInput = document.getElementById('nicknameInput');
    var nicknameCheckMessage = document.getElementById('nicknameCheckMessage');

    function nicknameCheck() {
        var nickname = nicknameInput.value;

        if (nickname.length < 2) {
            nicknameCheckMessage.textContent = '닉네임은 2글자 이상이어야 합니다.';
            nicknameCheckMessage.style.color = 'red';
            return;
        }

        if (nickname.length > 15) {
            nicknameCheckMessage.textContent = '닉네임은 15글자를 초과할 수 없습니다.';
            nicknameCheckMessage.style.color = 'red';
            return;
        }


        var validNickname = /^[a-zA-Z가-힣]+$/.test(nickname);

        if (!validNickname) {
            nicknameCheckMessage.textContent = '닉네임은 한글과 영어로만 입력되어야 합니다.';
            nicknameCheckMessage.style.color = 'red';
            return;
        }

        nicknameCheckMessage.textContent = '';
    }

    nicknameInput.addEventListener('keyup', nicknameCheck);

    /* 핸드폰번호 유효성검사 */
    var phoneInput = document.getElementById('phoneInput');
    var phoneCheckMessage = document.getElementById('phoneCheckMessage');

    function phoneCheck() {
        var phone = phoneInput.value;

        if (phone.length < 10) {
            phoneCheckMessage.textContent = '핸드폰 번호는 10글자 이상이어야 합니다.';
            phoneCheckMessage.style.color = 'red';
            return;
        }

        if (phone.length > 11) {
            phoneCheckMessage.textContent = '핸드폰 번호는 11글자를 초과할 수 없습니다.';
            phoneCheckMessage.style.color = 'red';
            return;
        }

        var validPhone = /^[0-9]+$/.test(phone);

        if (!validPhone) {
            phoneCheckMessage.textContent = '핸드폰 번호는 숫자로만 입력되어야 합니다.';
            phoneCheckMessage.style.color = 'red';
            return;
        }
        phoneCheckMessage.textContent = '';
    }

    phoneInput.addEventListener('keyup', phoneCheck);


    /*
    birthday selectbox 동적 생성

    // '출생 년도' 셀렉트 박스 option 목록 동적 생성
    const birthYearEl = document.querySelector('#birth-year');
    let isYearOptionExisted = false;

    birthYearEl.addEventListener('focus', function () {
        if (!isYearOptionExisted) {
            isYearOptionExisted = true;
            for (let i = 1920; i <= 2023; i++) {
                const YearOption = document.createElement('option');
                YearOption.setAttribute('value', i);
                YearOption.innerText = i;
                this.appendChild(YearOption);
            }
        }
    });

    // '월' 셀렉트 박스 option 목록 동적 생성
    const birthMonthEl = document.querySelector('#birth-month');
    let isMonthOptionExisted = false;

    birthMonthEl.addEventListener('focus', function () {
        if (!isMonthOptionExisted) {
            isMonthOptionExisted = true;
            for (let i = 1; i <= 12; i++) {
                const monthOption = document.createElement('option');
                monthOption.setAttribute('value', i);
                monthOption.innerText = i + '월';
                this.appendChild(monthOption);
            }
        }
    });

    // '일' 셀렉트 박스 option 목록 동적 생성
    const birthDayEl = document.querySelector('#birth-day');
    let isDayOptionExisted = false;

    birthDayEl.addEventListener('focus', function () {
        if (!isDayOptionExisted) {
            isDayOptionExisted = true;
            const selectedMonth = parseInt(birthMonthEl.value, 10); // 선택된 월 가져오기
            const lastDay = new Date(2023, selectedMonth, 0).getDate(); // 선택된 월의 마지막 날짜 계산
            for (let i = 1; i <= lastDay; i++) {
                const dayOption = document.createElement('option');
                dayOption.setAttribute('value', i);
                dayOption.innerText = i + '일';
                this.appendChild(dayOption);
            }
        }
    });

    // '월' 셀렉트 박스 변경 시 '일' 셀렉트 박스를 업데이트
    birthMonthEl.addEventListener('change', function () {
        isDayOptionExisted = false; // '일' 옵션을 다시 생성하기 위해 상태 초기화
        while (birthDayEl.firstChild) {
            birthDayEl.removeChild(birthDayEl.firstChild); // 기존 '일' 옵션 제거
        }
    });

    */
}

function emailAuthSnd() {

    let email = $("#memberId").val();
    if (!email) {
        alert("이메일을 입력 해주세요.");
        $("#memberId").focus();
        return;
    }

    if (!validEmail(email)) {
        alert("이메일 형식이 아닙니다.");
        return;
    }

    // 이메일 인증 발송
    $.ajax({
        url: "/user/member/regEmailAuth"
        , type: "POST"
        , contentType: 'application/json;'
        , dataType: 'json'
        , data: JSON.stringify(
            {
                email: $("#memberId").val()
            }, null, 4)
        , success: function (result) {
            console.log("result", result);
            $("#emailAuthKey").val(result.emailAuthKey);
            alert("이메일 발송이 완료되었습니다.");
            //
        }
        , error: function (error) {
            console.log("error", error);
            alert("이메일 발송이 실패했습니다. \n 시스템 관리자에게 문의하세요.");
        }
    });

    alert("이메일 발송 처리가 완료되었습니다.");
}

function emailAuthChk() {

    fetch("/user/member/chkEmailAuth", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            emailAuthKey: $("#emailAuthKey").val()
            , emailAuthInVal: $("#emailAuthVal").val()
        })
    })
        .then(result => result.json())
        .then(result => {
            console.log("result", result);
            alert(result.resultMsg);
        })
        .catch((error) => error.json().then((res) => {
            console.log("error", JSON.stringify(res));
        }));
}

//
// function emailAuthChk() {
//     // 인증 코드와 이메일 입력값을 가져옵니다.
//     const emailAuthKey = $("#emailAuthKey").val();
//     const emailAuthInVal = $("#emailAuthVal").val();
//
//     // AJAX 요청을 보냅니다.
//     $.ajax({
//         url: "/user/member/chkEmailAuth",
//         type: "POST",
//         contentType: 'application/json;charset=UTF-8',
//         data: JSON.stringify({
//             emailAuthKey: emailAuthKey,
//             emailAuthInVal: emailAuthInVal
//         }),
//         success: function (data) {
//  alert(data.resultMsg);
//             console.log("인증 성공:", data);
//             alert("인증이 성공했습니다.");
//         },
//         error: function (error) {
//             console.error("인증 오류:", error);
//             alert("인증에 실패했습니다. 인증 번호를 다시 확인해주세요.");
//         }
//     });
// }


// ????
// function emailAuthChk() {
//     // 인증 코드와 이메일 입력값을 가져옵니다.
//     const emailAuthKey = $("#emailAuthKey").val();
//     const emailAuthInVal = $("#emailAuthVal").val();
//
//     // fetch 요청을 보냅니다.
//     fetch("/user/member/chkEmailAuth", {
//         method: "POST",
//         headers: {
//             'Content-Type': 'application/json;charset=UTF-8'
//         },
//         body: JSON.stringify({
//             emailAuthKey: emailAuthKey,
//             emailAuthInVal: emailAuthInVal
//         })
//     })
//         .then(response => {
//             if (response.ok) {
//                 return response.json();
//             } else {
//                 throw new Error("인증 실패");
//             }
//         })
//         .then(data => {
//             console.log("인증 성공:", data);
//             alert("인증이 성공했습니다.")
//         })
//         .catch(error => {
//             console.error("인증 오류:", error.message);
//             alert("인증에 실패했습니다. 인증 번호를 다시 확인해주세요.")
//         });


/**
 * Promis => 실행 방법 중
 *  시점 1. 시작 - fetch or ajax
 *  시점 2. 무조건 실행 - Then
 *  시점 3. 성공 - Resolve, Success
 *  시점 4. 실패 - Reject, ERror
 *  시점 5. 완료 - Done
 */


$(document).ready(function () {
    $("#btnEmailAuthReg")
        .off("click")
        .on("click", emailAuthSnd);

    $("#btnEmailAuthVal")
        .off("click")
        .on("click", emailAuthChk);

});