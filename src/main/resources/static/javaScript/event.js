window.onload = function () {

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

    /* 후기작성 별점 평가 */


}



