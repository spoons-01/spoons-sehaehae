window.onload = function () {

    if (document.getElementById("writeNotice")) {
        const $writeNotice = document.getElementById("writeNotice");
        $writeNotice.onclick = function () {
            location.href = "/admin/board/adminNoticeRegist";
        }
    }

    if (document.getElementById("modifiedNotice")) {
        const $modifiedNotice = document.getElementById("modifiedNotice");
        $modifiedNotice.onclick = function () {
            location.href = "/admin/board/adminNoticeUpdate";
        }
    }
}

/* 경로 ----------------------------------------------- */
// 현재 페이지의 경로 정보
var currentPagePath = ["게시판", "공지사항"];

// 브레드크럼 업데이트 함수
function updateBreadcrumb() {
    var breadcrumbContainer = document.getElementById("breadcrumb");
    breadcrumbContainer.innerHTML = ""; // 브레드크럼 초기화

    // 현재 페이지 경로를 반복하여 브레드크럼에 추가
    for (var i = 0; i < currentPagePath.length; i++) {
        var breadcrumbLink = document.createElement("a");
        breadcrumbLink.href = "/admin/board/adminNotice"; // 링크 주소는 여기서 설정
        breadcrumbLink.innerText = currentPagePath[i];

        // 링크를 브레드크럼에 추가
        breadcrumbContainer.appendChild(breadcrumbLink);

        // 경로 구분을 추가 (">" 문자)
        if (i < currentPagePath.length - 1) {
            var separator = document.createTextNode(" > ");
            breadcrumbContainer.appendChild(separator);
        }
    }
}

// 페이지 로드 시 브레드크럼 업데이트 실행
window.onload = function () {
    updateBreadcrumb();
};

/* 모달창 이벤트 ------------------------------- */
window.onload = function () {
    /* 모달 이벤트 */
    const loremIpsum = document.getElementById("lorem-ipsum")
    fetch("https://baconipsum.com/api/?type=all-meat&paras=200&format=html")
        .then(response => response.text())
        .then(result => loremIpsum.innerHTML = result)
    /* 키는 이벤트 */
    const modal = document.getElementById("modal")
    const btnModal = document.getElementById("btn-modal")
    btnModal.addEventListener("click", e => {
        modal.style.display = "flex"
    })
    /* .close-area 이거 누르면 꺼짐 */
    const closeBtn = modal.querySelector(".close-area")
    closeBtn.addEventListener("click", e => {
        modal.style.display = "none"
    })
    /* Esc 누르면 꺼지는 이벤트 */
    window.addEventListener("keyup", e => {
        if (modal.style.display === "flex" && e.key === "Escape") {
            modal.style.display = "none"
        }
    })
}


