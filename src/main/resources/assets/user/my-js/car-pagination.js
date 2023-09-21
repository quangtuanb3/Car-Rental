
const ePagination = document.getElementById('car-Pagination')





let pageable = {
    page: 1,
    search: ''
}

// async function renderCarTable() {
//     let url = `user/api/cars?page=${pageable.page - 1 || 0}&sort=${pageable.sortCustom || 'id,desc'}&search=${pageable.search || ''}`;
//     const response = await fetch(url);
//     // const pageable = await getRooms();
//     // rooms = pageable.content;
//     // renderTBody(rooms);
//
//
//     const result = await response.json();
//     pageable = {
//         ...pageable,
//         ...result
//     };
//     genderCarPagination();
//
//
// }

async function renderCarTable() {
    let url = `user/api/cars?page=${pageable.page - 1 || 0}&size=5`;
    const response = await fetch(url);
    const result = await response.json();
    pageable = {
        ...pageable,
        ...result
    };
    genderCarPagination();
}

const genderCarPagination = () => {
    ePagination.innerHTML = '';
    let str = '';

    const currentPage = pageable.page + 1; // Lấy trang hiện tại

    // Tạo nút "Previous" ("<")
    str += `<li class="page-item ${currentPage === 1 ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="${currentPage === 1}"><</a>
            </li>`

    // Tạo các nút trang
    for (let i = 1; i <= pageable.totalPages; i++) {
        str += ` <li class="page-item ${(currentPage === i) ? 'active' : ''}" aria-current="page">
      <a class="page-link" href="#">${i}</a>
    </li>`
    }

    // Tạo nút "Next" (">")
    str += `<li class="page-item ${(currentPage === pageable.totalPages) ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="${currentPage === pageable.totalPages}">></a>
            </li>`

    ePagination.innerHTML = str;

    const ePages = ePagination.querySelectorAll('li');
    const ePrevious = ePages[0];
    const eNext = ePages[ePages.length - 1]

    // Xử lý sự kiện khi click vào nút "Previous" ("<")
    ePrevious.onclick = () => {
        if (currentPage > 1) {
            pageable.page -= 1;
            renderCarTable();
        }
    }

    // Xử lý sự kiện khi click vào nút "Next" (">")
    eNext.onclick = () => {
        if (currentPage < pageable.totalPages) {
            pageable.page += 1;
            renderCarTable();
        }
    }

    // Xử lý sự kiện khi click vào các nút trang
    for (let i = 1; i < ePages.length - 1; i++) {
        if (currentPage !== i) {
            ePages[i].onclick = () => {
                pageable.page = i - 1;
                renderCarTable();
            }
        }
    }
}

// Gọi hàm renderCarTable() lần đầu để hiển thị dữ liệu ban đầu
renderCarTable();







// const genderCarPagination = () => {
//     ePagination.innerHTML = '';
//     let str = '';
//     //generate preview truoc
//     str += `<li class="page-item ${pageable.first ? 'disabled' : ''}">
//               <a class="page-link" href="#" tabindex="-1" aria-disabled="true"><</a>
//             </li>`
//     //generate 1234
//
//     for (let i = 1; i <= pageable.totalPages; i++) {
//         str += ` <li class="page-item ${(pageable.page) === i ? 'active' : ''}" aria-current="page">
//       <a class="page-link" href="#">${i}</a>
//     </li>`
//     }
//     //
//     //generate next truoc
//     str += `<li class="page-item ${pageable.last ? 'disabled' : ''}">
//               <a class="page-link" href="#" tabindex="-1" aria-disabled="true">></a>
//             </li>`
//     //generate 1234
//     ePagination.innerHTML = str;
//
//     const ePages = ePagination.querySelectorAll('li'); // lấy hết li mà con của ePagination
//     const ePrevious = ePages[0];
//     const eNext = ePages[ePages.length-1]
//
//     ePrevious.onclick = () => {
//         if(pageable.page === 1){
//             return;
//         }
//         pageable.page -= 1;
//         renderCarTable();
//     }
//     eNext.onclick = () => {
//         if(pageable.page === pageable.totalPages){
//             return;
//         }
//         pageable.page += 1;
//         renderCarTable();
//     }
//     for (let i = 1; i < ePages.length - 1; i++) {
//         if(i === pageable.page){
//             continue;
//         }
//         ePages[i].onclick = () => {
//             pageable.page = i;
//             renderCarTable();
//         }
//     }
// }
//
// const pageCar = document.querySelector('#limit');
// pageCar.addEventListener('click', () => {
//     onLimit(event)
// });
//
// const onLimit = (e) => {
//     e.preventDefault();
//     pageable.page = 1;
//     renderTable();
// }

