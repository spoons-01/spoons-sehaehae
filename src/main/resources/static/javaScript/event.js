window.onload = function () {

    // if (document.getElementById("writeNotice")) {
    //     const $writeNotice = document.getElementById("writeNotice");
    //     $writeNotice.onclick = function () {
    //         location.href = "/admin/board/adminNoticeRegist";
    //     }
    // }
    //
    // if (document.getElementById("modifiedNotice")) {
    //     const $modifiedNotice = document.getElementById("modifiedNotice");
    //     $modifiedNotice.onclick = function () {
    //         location.href = "/admin/board/adminNoticeUpdate";
    //     }
    // }

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

/* 아코디언 */

    const accordionItems = document.querySelectorAll(".accordion-item");

    accordionItems.forEach((item) => {
        const title = item.querySelector(".accordion-title");
        const content = item.querySelector(".accordion-content");

        title.addEventListener("click", () => {
            // 클릭한 아이템의 내용을 열거나 닫음
            if (content.style.display === "block") {
                content.style.display = "none";
            } else {
                content.style.display = "block";
            }

            // 다른 아이템의 내용을 닫음
            accordionItems.forEach((otherItem) => {
                if (otherItem !== item) {
                    otherItem.querySelector(".accordion-content").style.display = "none";
                }
            });
        });
    });


/* 클릭시 색상 변경 */

    // 모든 categoryList2 요소를 가져옵니다.
    var categoryList2Items = document.querySelectorAll('.categoryList2');

    // 클릭 이벤트 핸들러 함수
    function handleClick(event) {
        // 선택된 카테고리의 스타일 변경
        categoryList2Items.forEach(function(item) {
            item.classList.remove("clicked");
        });
        event.currentTarget.classList.add("clicked");
    }

    // 각각의 categoryList2 요소에 클릭 이벤트 리스너 추가
    categoryList2Items.forEach(function(item) {
        item.addEventListener("click", handleClick);
    });
}

/* 모달창 이벤트 ------------------------------- */



