/* 이메일 유효성 검사 */
function validEmail(email) {
    let regex = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");
    return regex.test(email);
}

/* 이메일 DB 확인 */
window.onload = function () {
    if (document.getElementById("duplicationCheck")) {
        const $duplication = document.getElementById("duplicationCheck");
        const $btnEmailAuthReg = document.getElementById("btnEmailAuthReg");

        $duplication.onclick = function () {
            let memberId = document.getElementById("memberId").value.trim();

            fetch("/user/member/idDupCheckForPwd", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json;charset=UTF-8'
                },
                body: JSON.stringify({memberId: memberId})
            })
                .then(result => result.text())
                .then(result => {
                    alert(result);

                    $btnEmailAuthReg.disabled = false;
                    $btnEmailAuthReg.style.backgroundColor = "#4B93FF";
                    $btnEmailAuthReg.style.color = "white";
                })
                .catch(error => {
                    error.text().then(res => alert(res));
                });
        }
    }
}


/* 이메일 인증 요청 */
function emailAuthSnd() {

    // 유효성 검사
    let email = $("#memberId").val();
    if (!email) {
        alert("이메일을 입력해주세요.")
        $("#memberId").focus();
        return
    }

    if(!validEmail(email)) {
        alert("올바른 이메일 형식을 입력해주세요.")
        return;
    }

    // 이메일 인증 메일 발송
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
        }
        , error: function (eroor) {
            console.log("error", eroor);
            alert("이메일 발송이 실패했습니다.")
        }
    });

    alert("이메일 발송 처리가 완료되었습니다. 이메일을 확인해주세요.")
}

// 이메일 인증 검증
function emailAuthChk() {
    fetch("/user/member/chkEmailAuth", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            emailAuthKey: $("#emailAuthKey").val(),
            emailAuthInVal: $("#emailAuthVal").val()
        })
    })
        .then(result => result.json())
        .then(result => {
            console.log("result", result);
            if (result.success) {
                alert(result.resultMsg);
                updatePasswordAndSendEmail($("#memberId").val());
            } else {
                alert(result.resultMsg);
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            alert('오류가 발생했습니다.');
        });
}

// 비밀번호 업데이트 및 이메일로 새 비밀번호 발송
function updatePasswordAndSendEmail(email) {
    fetch("/user/member/updatePasswordAndSendEmail", {
        method: "POST",
        headers: {
            'Content-type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({email: email}) // 여기에서 이메일 값을 서버로 전송
    })
        .then(response => response.json())
        .then(data => {
            if(data.success) {
                alert("새로운 비밀번호가 이메일로 전송되었습니다. 이메일을 확인해주세요.");
            } else {
                alert(data.message);
            }
        })
        .catch(error => console.error('Error:', error));
}


$(document).ready(function () {
    $("#btnEmailAuthReg")
        .off("click")
        .on("click", emailAuthSnd);

    $("#btnEmailAuthVal")
        .off("click")
        .on("click", emailAuthChk);
});