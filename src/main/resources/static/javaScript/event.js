window.onload = function () {
    /*첨부파일 */
    (function (){
        /* div image area 요소 */
        const imageArea = document.querySelectorAll(".image-area");
        /* input type=file 요소 */
        const fileElements = document.querySelectorAll("[type=file]");
        /* div 클릭 시 open 함수 동작하여 input type=file 클릭 */
        imageArea.forEach(item => item.addEventListener('click', open));
        /* 파일 첨부가 발생하는 상황에 preview 함수 동작 */
        fileElements.forEach(item => item.addEventListener('change', preview));

        function open() {
            const index = Array.from(imageArea).indexOf(this);
            fileElements[index].click();
        }

        function preview() {
            const index = Array.from(fileElements).indexOf(this);
            /* 첨부 된 파일이 존재한다면 */
            if(this.files && this.files[0]) {
                const reader = new FileReader();
                reader.readAsDataURL(this.files[0]);
                /* 파일 로드 후 동작하는 이벤트 설정 */
                reader.onload = function () {
                    console.log(reader.result);
                    imageArea[index].innerHTML = `<img src='${reader.result}' style='width:50px;height:50px'>`;
                }
            }
        }

    })();

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

//
//     /* 클릭시 색상 변경 */
//
//     // 모든 categoryList2 요소를 가져옵니다.
//     var categoryList2Items = document.querySelectorAll('.categoryList2');
//
//     // 클릭 이벤트 핸들러 함수
//     function handleClick(event) {
//         // 선택된 카테고리의 스타일 변경
//         categoryList2Items.forEach(function(item) {
//             item.classList.remove("clicked");
//         });
//         event.currentTarget.classList.add("clicked");
//     }
//
//     // 각각의 categoryList2 요소에 클릭 이벤트 리스너 추가
//     categoryList2Items.forEach(function(item) {
//         item.addEventListener("click", handleClick);
//     });
 }
/* 별점 로드 */
window.addEventListener("load", function() {
    const rating = document.querySelectorAll(".ratingView");

    rating.forEach(function (item){
        const targetScore = item.dataset.rate;
        console.log(targetScore);
        item.querySelectorAll('svg').forEach(function (star, index) {
            if (index < targetScore) {
                star.style.color = '#FFFF00';
            }
        });
    });
});



