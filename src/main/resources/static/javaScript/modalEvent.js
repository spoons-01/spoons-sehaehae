/* 모달 이벤트 */
window.addEventListener("load", function() {

    const loremIpsum = document.getElementById("lorem-ipsum");
    fetch("https://baconipsum.com/api/?type=all-meat&paras=200&format=html")
        .then(response => response.text())
        // .then(result => loremIpsum.innerHTML = result)
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
})

window.addEventListener("load", function() {
    const modal = document.getElementById("modal");
    const closeBtn = modal.querySelector(".close-area");

    const reviewButtons = document.querySelectorAll(".review-button");

    reviewButtons.forEach(button => {
        button.addEventListener("click", e => {
            e.preventDefault(); // 기본 링크 동작 방지

            // 클릭한 버튼에 설정된 고유 식별자를 가져옵니다.
            const orderCode = button.getAttribute("data-order-code");
            const memberNo = button.getAttribute("data-point");


            // 모달 내용을 업데이트하고 모달을 표시
            const modalContent = modal.querySelector(".modal-content");
            modalContent.innerHTML = "주문 번호: " + orderCode;

            // <input type="hidden" name="orderCode"> 요소를 찾습니다.
            const orderCodeInput = modal.querySelector('input[name="orderCode"]');
            const orderMemberInput = modal.querySelector('input[name="memberNo"]');

            // 찾은 요소의 값을 주문 코드로 설정합니다.
            orderCodeInput.value = orderCode;
            orderMemberInput.value = memberNo;


            modal.style.display = "flex";
        });
    });

    closeBtn.addEventListener("click", e => {
        modal.style.display = "none";
    });

    window.addEventListener("keyup", e => {
        if (modal.style.display === "flex" && e.key === "Escape") {
            modal.style.display = "none";
        }
    });
});

/* 후기를 작성하면 후기버튼은 없어지고 수정하기 나타나기 */
// JavaScript 코드
// 이 코드는 페이지가 로드될 때 실행되며 후기 상태에 따라 버튼을 조작합니다.
// document.addEventListener("DOMContentLoaded", function() {
//     // 이 부분에서 order.reviewStatus 값을 가져오거나 필요한 데이터를 추출할 수 있습니다.
//
//     // 만약 order.reviewStatus가 '작성완료'라면 후기 버튼을 숨깁니다.
//     if (order.reviewStatus === '작성완료') {
//         const reviewButton = document.querySelector('.review-button');
//         if (reviewButton) {
//             reviewButton.style.display = 'none';
//         }
//     }
// });

