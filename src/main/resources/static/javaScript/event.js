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
    // /* 체크박스 전체선택 ---------------------------------------------- */
    // $("#selectAll").click(function () {
    //     console.log("전체 선택 체크박스 클릭");
    // });
}


// $("#selectAll").click(function (){
//     if($("selectAll").is(":checked")){
//         $("input[name=noticeCheckbox]").prop("checked", true);
//     } else {
//         $("input[name=noticeCheckbox]").prop("checked", false);
//     }
// });
//
// $("input[name=noticeCheckbox]").click(function (){
//     var total = $("input[namenoticeCheckbox]").length;
//     var checked = $("input[name=noticeCheckbox]:checked").length;
//
//     if(total != checked){
//         $("#selectAll").prop("checked", false);
//     } else {
//         $("#selectAll").prop("checked", true);
//     }
// });

// // 전체 선택 체크박스를 클릭할 때
// $("#selectAll").click(function () {
//     if ($("#selectAll").is(":checked")) {
//         $("input[name=noticeCheckbox]").prop("checked", true);
//     } else {
//         $("input[name=noticeCheckbox]").prop("checked", false);
//     }
// });
//
// // 개별 체크박스를 클릭할 때
// $("input[name=noticeCheckbox]").click(function () {
//     var total = $("input[name=noticeCheckbox]").length;
//     var checked = $("input[name=noticeCheckbox]:checked").length;
//
//     if (total != checked) {
//         $("#selectAll").prop("checked", false);
//     } else {
//         $("#selectAll").prop("checked", true);
//     }
// });
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


