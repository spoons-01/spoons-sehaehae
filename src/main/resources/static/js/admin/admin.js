// document.getElementById('resetButton').addEventListener('click', function() {
//     document.getElementById('allSearch').reset();
// });




/*---------- 날짜 ---------*/

function searchDate(range) {
    var today = new Date();
    var startDate = new Date();
    var endDate = new Date();

    if (range == '오늘') {
        startDate = today;
        endDate = today;
    } else if (range == '1주') {
        startDate.setDate(today.getDate() + 6);
        endDate.setDate(today.getDate() + 6);
    } else if (range == '15일') {
        startDate.setDate(today.getDate() + 14);
        endDate.setDate(today.getDate() + 14);
    } else if (range === '1개월') {
        endDate.setMonth(today.getMonth() + 1);
        endDate.setDate(1); // 다음 달의 첫 번째 날로 설정
        startDate = new Date(endDate);
    } else if (range === '3개월') {
        endDate.setMonth(today.getMonth() + 3);
        endDate.setDate(1); // 다음 달의 첫 번째 날로 설정
        startDate = new Date(endDate);
    } else if (range === '6개월') {
        endDate.setMonth(today.getMonth() + 6);
        endDate.setDate(1); // 다음 달의 첫 번째 날로 설정
        startDate = new Date(endDate);
    }

    // input 요소에 날짜 설정
    document.getElementById('start-date').valueAsDate = startDate;
    document.getElementById('end-date').valueAsDate = endDate;
}

    /*---------- 체크박스 전체 선택/해제 ---------*/
function selectAll(selectAll) {
    const checkboxes = document.querySelectorAll('input.checkStatus');
    const entireCheckbox = document.getElementById('selectAll');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    });

    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('change', function () {
            if (!this.checked) {
                entireCheckbox.checked = false;
            }
        });
    });

}


/*----------------------------------------------*/

function markAsCollected() {
    const checkboxes = $('input.checkStatus:checked');
    console.log(checkboxes);
    const orderId = checkboxes.map(function() {
        // 각 체크된 체크박스의 데이터 식별자를 가져오기 (주문번호)
        return $(this).data('order-id'); // HTML에서 data-order-id 속성을 설정해야 합니다.
    }).get();
     console.log(orderId);
    //주문번호 목록을 서버에 보내고 업데이트 요청 처리
    if (orderId.length > 0) {
        $.ajax({
            url: '/orderManagement/update-order-status', // 서버 업데이트 엔드포인트
            method: 'POST', // 또는 PUT, PATCH 등 업데이트에 적합한 HTTP 메서드 사용
            data: { orderId },
            success: function (response) {
                if (response === 'success'){
                    alert('주문상태가 "수거완료" 로 업데이트되었습니다.');

                    //페이지 새로고침
                    location.reload();
                }else {
                    alert('주문상태 업데이트에 실패하였습니다');
                }
            },
            error: function (error) {
                // 오류 처리를 수행합니다.
                console.error('상태 업데이트 실패:', error);
            }
        });
    }
}

/*-------------------------------------------------------------*/



