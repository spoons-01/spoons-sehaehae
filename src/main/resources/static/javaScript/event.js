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
//별점 마킹 모듈 프로토타입으로 생성
    function Rating(){}
    Rating.prototype.rate = 0;
    Rating.prototype.setRate = function(newrate){
        //별점 마킹 - 클릭한 별 이하 모든 별 체크 처리
        this.rate = newrate;
        let items = document.querySelectorAll('.rate_radio');
        items.forEach(function(item, idx){
            if(idx < newrate){
                item.checked = true;
            }else{
                item.checked = false;
            }
        });
    }
    let rating = new Rating();//별점 인스턴스 생성

    document.addEventListener('DOMContentLoaded', function(){
        //별점선택 이벤트 리스너
        document.querySelector('.rating').addEventListener('click',function(e){
            let elem = e.target;
            if(elem.classList.contains('rate_radio')){
                rating.setRate(parseInt(elem.value));
            }
        })
    });

    //상품평 작성 글자수 초과 체크 이벤트 리스너
    document.querySelector('.reviewregist-body').addEventListener('keydown',function(){
        //리뷰 400자 초과 안되게 자동 자름
        let review = document.querySelector('.reviewregist-body');
        let lengthCheckEx = /^.{400,}$/;
        if(lengthCheckEx.test(review.value)){
            //400자 초과 컷
            review.value = review.value.substr(0,400);
        }
    });

    //저장 전송전 필드 체크 이벤트 리스너
    document.querySelector('#save').addEventListener('click', function(e){
        //별점 선택 안했으면 메시지 표시
        if(rating.rate == 0){
            rating.showMessage('별점을 체크해주세요.');
            return false;
        }
        //리뷰 5자 미만이면 메시지 표시
        if(document.querySelector('.reviewregist-body').value.length < 5){
            rating.showMessage('리뷰 내용 5자 이상 작성해주세요.');
            return false;
        }
        //폼 서밋
    });
}



