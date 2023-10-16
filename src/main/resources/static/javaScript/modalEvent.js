/* 모달 이벤트 */
// window.addEventListener("load", function() {
//
//     const loremIpsum = document.getElementById("lorem-ipsum");
//     fetch("https://baconipsum.com/api/?type=all-meat&paras=200&format=html")
//         .then(response => response.text())
//         // .then(result => loremIpsum.innerHTML = result)
//     /* 키는 이벤트 */
//     const modal = document.getElementById("modal")
//     const btnModal = document.getElementById("btn-modal")
//     btnModal.addEventListener("click", e => {
//         modal.style.display = "flex"
//     })
//     /* .close-area 이거 누르면 꺼짐 */
//     const closeBtn = modal.querySelector(".close-area")
//     closeBtn.addEventListener("click", e => {
//         modal.style.display = "none"
//     })
//     /* Esc 누르면 꺼지는 이벤트 */
//     window.addEventListener("keyup", e => {
//         if (modal.style.display === "flex" && e.key === "Escape") {
//             modal.style.display = "none"
//         }
//     })
// })

window.addEventListener("load", function() {
    const modal = document.getElementById("modal");
    const closeBtn = modal.querySelector(".close-area");

    const reviewButtons = document.querySelectorAll(".review-button");

    reviewButtons.forEach(button => {
        button.addEventListener("click", e => {
            e.preventDefault(); // 기본 링크 동작 방지

            // 클릭한 버튼에 설정된 고유 식별자를 가져옵니다.
            const orderCode = button.getAttribute("data-order-code");

            // 모달 내용을 업데이트하고 모달을 표시
            const modalContent = modal.querySelector(".modal-content");
            modalContent.innerHTML = "주문 번호: " + orderCode;

            // <input type="hidden" name="orderCode"> 요소를 찾습니다.
            const orderCodeInput = modal.querySelector('input[name="orderCode"]');
            // 찾은 요소의 값을 주문 코드로 설정합니다.
            orderCodeInput.value = orderCode;

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


