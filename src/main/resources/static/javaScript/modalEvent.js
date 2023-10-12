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