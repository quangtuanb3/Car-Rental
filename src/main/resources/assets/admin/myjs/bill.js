const billForm = document.getElementById('billForm');

let billSelected = {};
let bills = [];
const formBody = document.getElementById('formBody');
const tBody = document.getElementById('tBody');
const ePagination = document.getElementById('pagination')
const eSearch = document.getElementById('search')
const eHeaderPrice = document.getElementById('header-price')

let customers;


//bill-model-details

let eModalBillCustomerName = document.getElementById("modal-bill-customerName");
let eModalBillEmail = document.getElementById("modal-bill-customerEmail");
let eModalBillStatus = document.getElementById("modal-bill-billStatus");

let eModalBillPickupTime = document.getElementById("modal-bill-pickup-time");
let eModalBillDropOffTime = document.getElementById("modal-bill-drop-off-time");
let eModalBillPickupLocation = document.getElementById("modal-bill-pickup-location");
let eModalBillDropOfLocation = document.getElementById("modal-bill-drop-off-location");

let eModalDeposit = document.getElementById("modal-bill-deposit");
let eModalRentPrice = document.getElementById("modal-bill-rentPrice");
let eModalTotalPrice = document.getElementById("modal-bill-totalPrice");
let eModalLicensePlate = document.getElementById("modal-bill-licensePlate");


//modal-car-populate price Hours và Day:

// Đoạn mã JavaScript để lấy giá trị từ các thẻ <td> và định dạng chúng thành tiền tệ VND
// const priceHoursElement = document.getElementById("modal-car-priceHours");
// const priceDaysElement = document.getElementById("modal-car-priceDays");
// const priceDeliverysElement = document.getElementById("modal-car-priceDeliverys");

// Lấy giá trị từ các thẻ <td>
// const priceHoursValue = parseFloat(priceHoursElement.textContent.replace("$", ""));
// const priceDaysValue = parseFloat(priceDaysElement.textContent.replace("$", ""));
// const priceDeliverysValue = parseFloat(priceDeliverysElement.textContent.replace("$", ""));


// Chuyển đổi và định dạng giá trị thành tiền tệ VND bằng hàm formatCurrency
// const formattedPriceHours = formatCurrency(priceHoursValue);
// const formattedPriceDays = formatCurrency(priceDaysValue);
// const formattedPriceDeliverys = formatCurrency(priceDeliverysValue);

// Cập nhật giá trị đã định dạng vào các thẻ <td>
// priceHoursElement.textContent = formattedPriceHours;
// priceDaysElement.textContent = formattedPriceDays;
// priceDeliverysElement.textContent = formattedPriceDeliverys;


let billStatus;

async function getBillStatus() {
    const res = await fetch("/api/bills/bill-status")
    return await res.json();
}

let pageableBill = {
    page: 1,
    sort: 'id,desc',
    search: ''
}
billForm.onsubmit = async (e) => {
    e.preventDefault();
    let data = getDataFromForm(billForm);


    // Định dạng giá trị thành tiền tệ VND


    // Cập nhật giá trị hiển thị trong modal chi tiết xe


    data = {
        ...data,
        customer: {
            id: data.customer
        },


        id: billSelected.id

    }


    renderTable();
    $('#staticBackdrop').modal('hide');

}

function getDataFromForm(form) {
    // event.preventDefault()
    const data = new FormData(form);
    return Object.fromEntries(data.entries())
}


async function getCustomersSelectOption() {
    const res = await fetch('api/customers');
    return await res.json();
}


window.onload = async () => {
    await loadBills();
    customers = await getCustomersSelectOption();
    // features = await getFeaturesSelectOption();

    billStatus = await getBillStatus();
    console.log(billStatus)
    await renderTable();

    // renderForm(formBody);
}


async function findBillById(id) {
    const res = await fetch('/api/bills/' + id);
    return await res.json();
}

async function findBillDetailById(id) {
    const res = await fetch('/api/bills/detail/' + id);
    return await res.json();
}

async function renderTable() {
    let url = `/api/bills`;
    const response = await fetch(url);
    // const pageable = await getRooms();
    // rooms = pageable.content;
    // renderTBody(rooms);
    const result = await response.json();
    renderBillTable(result);

}

const onSearch = (e) => {
    e.preventDefault()
    pageable.search = eSearch.value;
    pageable.page = 1;
    renderTable();
}


const searchInput = document.querySelector('#search');

searchInput.addEventListener('search', () => {
    onSearch(event)
});


function clearForm() {
    billForm.reset();
    billSelected = {};
}


async function showBillDetail(id) {
    let billDetail = await findBillDetailById(id)
    eModalBillCustomerName.innerText = billDetail.customerName;
    eModalBillEmail.innerText = billDetail.customerEmail;
    eModalBillStatus.innerText = billDetail.billStatus;

    eModalBillPickupTime.innerText = billDetail.pickupTime;
    eModalBillDropOffTime.innerText = billDetail.expectedDropOffTime;
    eModalBillPickupLocation.innerText = billDetail.pickupLocation;
    eModalBillDropOfLocation.innerText = billDetail.dropOffLocation;


    eModalDeposit.innerText = billDetail.deposit;
    eModalRentPrice.innerText = billDetail.rentPrice;
    eModalLicensePlate.innerText = billDetail.licensePlate;
    eModalTotalPrice.innerText = billDetail.totalPrice;


    // const formattedPriceHours = formatCurrency(carDetail.priceHours);
    // const formattedPriceDays = formatCurrency(carDetail.priceDays);
    // const formattedPriceDeliverys = formatCurrency(carDetail.priceDeliveys);
    // eModalPriceHours.innerText = formattedPriceHours;
    // eModalPriceDays.innerText = formattedPriceDays;
    // eModalPriceDeliverys.innerText = formattedPriceDeliverys;
}

// Hàm cập nhật giá giờ theo định dạng tiền tệ VND

// Hàm định dạng giá tiền tệ VND


function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
}


function renderBillTable(bills) {
    let billTableHTML = '';

    bills.forEach((bill, index) => {
        // Tạo một dòng mới cho mỗi hóa đơn
        billTableHTML += `
            <tr>
                <td>${index + 1}</td>
                <td>
                        ${bill.customerName}
                    </td>
                    
                    <td>
                        ${bill.customerPhoneNumber}
                    </td>

                    <td>
                        ${bill.customerEmail}
                    </td>

                    <td>
                        ${bill.customerIdNumber}
                    </td>

                    <td>
                        <label>
                          <select class="form-control"
                                  id="${bill.id}"
                                  onchange="onChangeSelect(this)">
                
                          </select>
                        </label>
                    </td>

                    <td>
                        ${bill.totalPrice}
                    </td>
                <td>
                    <a class="btn btn-info text-white detail" data-id="${bill.id}"  data-bs-toggle="modal" data-bs-target="#detail-bill-modal" onclick="showBillDetail(${bill.id})" >Detail</a>
                </td>
            </tr>
        `;


    });


    tBody.innerHTML = billTableHTML;
    bills.forEach(bill => {
        renderSelectStatus(bill);
    })

}

function renderSelectStatus(bill) {
    const select = document.getElementById(bill.id);
    billStatus.forEach(item => {
        let option = document.createElement("option")
        option.value = item;
        option.innerHTML = item;
        option.selected = bill.billStatus === item;

        select.appendChild(option);
        select.classList.add(bill.billStatus);
    })

}

async function loadBills() {
    try {
        const response = await fetch('/api/bills'); // Thay đổi URL API tùy theo tên thư mục và đường dẫn của bạn
        if (response.ok) {
            const bills = await response.json();
            renderBillTable(bills);
        } else {
            console.error('Lỗi khi tải danh sách hóa đơn.');
        }
    } catch (error) {
        console.error('Lỗi khi gửi yêu cầu tải danh sách hóa đơn:', error);
    }
}

async function onChangeSelect(selectElement) {
    const billId = selectElement.id;
    const newStatus = selectElement.value;

    try {
        const response = await fetch(`/api/bills/${billId}/change-status?status=${newStatus}`, {
            method: 'GET',
        });


        if (response.ok) {
            const message = await response.text();
            alert(message);
            await renderTable();
        } else {
            const errorMessage = await response.text();
            alert(`Error: ${errorMessage}`);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred while changing status.');
    }
}

