const carForm = document.getElementById('carForm');
const eCheckBoxSpecifications = document.getElementsByClassName('valueOptions');
const eCheckBoxFeatures = document.getElementsByName('features');
let carSelected = {};
const formBody = document.getElementById('formBody');
const tBody = document.getElementById('tBody');
const ePagination = document.getElementById('pagination')
const eSearch = document.getElementById('search')
const eHeaderPrice = document.getElementById('header-price');
const eDetail_modal_body = document.getElementById("detail-modal-body");

//car-model-details

let eModalCarName = document.getElementById("modal-car-name");
let eModalCarDes = document.getElementById("modal-car-description");
let eModalCarStatus = document.getElementById("modal-car-status");
let eModalPriceHours = document.getElementById("modal-car-priceHours");
let eModalPriceDays = document.getElementById("modal-car-priceDays");
let eModalPriceDeliverys = document.getElementById("modal-car-priceDeliverys");
let eModalSpecification = document.getElementById("modal-car-specification");
let eModalFeature = document.getElementById("modal-car-feature");
// let eModalSurcharge = document.getElementById("modal-car-surcharge");
let eModalImage = document.getElementsByClassName("modal-car-image");
let eModalAgency = document.getElementById("modal-car-agency");

//modal-car-populate price Hours và Day:

// Đoạn mã JavaScript để lấy giá trị từ các thẻ <td> và định dạng chúng thành tiền tệ VND
const priceHoursElement = document.getElementById("modal-car-priceHours");
const priceDaysElement = document.getElementById("modal-car-priceDays");
const priceDeliverysElement = document.getElementById("modal-car-priceDeliverys");

// Lấy giá trị từ các thẻ <td>
const priceHoursValue = parseFloat(priceHoursElement.textContent.replace("$", ""));
const priceDaysValue = parseFloat(priceDaysElement.textContent.replace("$", ""));
const priceDeliverysValue = parseFloat(priceDeliverysElement.textContent.replace("$", ""));


// Chuyển đổi và định dạng giá trị thành tiền tệ VND bằng hàm formatCurrency
const formattedPriceHours = formatCurrency(priceHoursValue);
const formattedPriceDays = formatCurrency(priceDaysValue);
const formattedPriceDeliverys = formatCurrency(priceDeliverysValue);

// Cập nhật giá trị đã định dạng vào các thẻ <td>
priceHoursElement.textContent = formattedPriceHours;
priceDaysElement.textContent = formattedPriceDays;
priceDeliverysElement.textContent = formattedPriceDeliverys;

const BASE_URL_CLOUD_IMAGE = "https://res.cloudinary.com/dw3x98oui/image/upload";
const BASE_SCALE_IMAGE = "c_limit,w_50,h_50,q_100";

let specifications;
let features;
// let surcharges;
let agencies;
let urlImages
let cars = [];
let status;

async function getStatus() {
    const res = await fetch("/api/cars/status")
    return await res.json();
}

let idAvatar;
let pageable = {
    page: 1,
    sort: 'id,desc',
    search: ''
}
carForm.onsubmit = async (e) => {
    e.preventDefault();
    let data = getDataFromForm(carForm);

    //
    // // Định dạng giá trị thành tiền tệ VND
    // const formattedPriceHours = formatCurrency(priceHoursValue);x`
    // const formattedPriceDays = formatCurrency(priceDaysValue);
    //
    // // Cập nhật giá trị hiển thị trong modal chi tiết xe
    // document.getElementById("priceHoursValue").textContent = formattedPriceHours;
    // document.getElementById("priceDaysValue").textContent = formattedPriceDays;
    let specificationSelect = getSpecificationSelects();
    data = {
        ...data,
        agency: {
            id: data.agency
        },
        idSpecifications: Object.entries(specificationSelect).map(e => e[1]),
        idFeatures: Array.from(eCheckBoxFeatures)
            .filter(e => e.checked)
            .map(e => e.value),
        // idSurcharges: Array.from(eCheckBoxSurcharges)
        //     .filter(e => e.checked)
        //     .map(e => e.value),
        // urlImages: data.urlImages.split(","),
        id: carSelected.id,
        files: idImages.map(e => {
            return {
                id: e
            }
        })
    }

    // let formData = new FormData();
    // formData.append("id", data.id);
    // formData.append("name", data.name);
    // formData.append("licensePlate", data.licensePlate);
    // formData.append("description", data.description);
    // formData.append("priceHours", data.priceHours);
    // formData.append("priceDays", data.priceDays);
    // formData.append("priceDelivery", data.priceDelivery);
    // formData.append("status.id", data.status.id);
    // formData.append("agency.id", data.agency.id);
    // formData.append("idSpecifications", data.idSpecifications);
    // formData.append("idFeatures", data.idFeatures);

    let message = "Created"
    if (carSelected.id) {
        await editCar(data);
        message = "Edited"
    } else {
        await createCar(data)
    }

    alert(message);
    await renderTable();
    $('#staticBackdrop').modal('hide');

}

function getDataFromForm(form) {
    // event.preventDefault()
    const data = new FormData(form);
    return Object.fromEntries(data.entries())
}

async function getSpecificationsSelectOption() {
    const res = await fetch('api/specifications');
    return await res.json();
}

// const seat = [];
// const fuel = [];
// async function getSpecificationsSelectOption() {
//     const res = await fetch('api/specifications');
//     const data =  await res.json();
//     for (let item of data){
//         if(item.type === 'SEAT') seat.push(item)
//         if(item.type === 'FUEL') seat.push(item)
//     }
//     return data;
// }


async function getFeaturesSelectOption() {
    const res = await fetch('api/features');
    return await res.json();
}

// async function getSurchargesSelectOption() {
//     const res = await fetch('api/surcharges');
//     return await res.json();
// }

async function getAgenciesSelectOption() {
    const res = await fetch('api/agencies');
    return await res.json();
}

async function getImagesSelectOption() {
    const res = await fetch('api/images');
    return await res.json();
}

window.onload = async () => {
    specifications = await getSpecificationsSelectOption();
    features = await getFeaturesSelectOption();
    // surcharges = await getSurchargesSelectOption();
    agencies = await getAgenciesSelectOption();
    // urlImages = await getImagesSelectOption();

    status = await getStatus();
    await renderTable();
    onLoadSort();

    renderForm(formBody, getDataInput());
}


function getDataInput() {
    return [
        {
            label: 'Name',
            classContainer: "col-6",
            name: 'name',
            value: carSelected.name,
            required: true,
            pattern: "^[A-Za-z ]{6,20}",
            message: "Name must have minimum is 6 characters and maximum is 20 characters",
        },
        {
            label: 'Status',
            classContainer: "col-6",
            name: 'status',
            type: 'select',
            value: carSelected.status,
            required: true,
            options: status.map(s => ({
                value: s,
                name: s
            })),
            message: "Select ",
        },
        {
            label: 'License Plate',
            classContainer: "col-6",
            name: 'licensePlate',
            value: carSelected.licensePlate,
            required: true,
            pattern: "^[A-Z0-9 ]{6,20}",
            message: "License Plate must have minimum is 6 characters and maximum is 20 characters",
        },
        {
            label: 'Agency',
            classContainer: "col-6",
            name: 'agency',
            value: carSelected.agencyId,
            type: 'select',
            required: true,
            options: agencies,
            message: 'Please choose Agency'
        },
        {
            label: 'Price Hours',
            classContainer: "col-6",
            name: 'priceHours',
            value: carSelected.priceHours,
            pattern: "[1-9][0-9]{1,10}",
            message: 'Price Hours errors',
            required: true
        },
        {
            label: 'Price Days',
            classContainer: "col-6",
            name: 'priceDays',
            value: carSelected.priceDays,
            pattern: "[1-9][0-9]{1,10}",
            message: 'Price Days Hours errors',
            required: true
        },
        {
            label: 'Price Deliverys',
            classContainer: "col-6",
            name: 'priceDelivery',
            value: carSelected.priceDelivery,
            pattern: "[1-9][0-9]{1,10}",
            message: 'Price Deliverys Hours errors',
            required: true
        },
        {
            label: 'Description',
            classContainer: "col-6",
            name: 'description',
            value: carSelected.description,
            pattern: "^[A-Za-z ]{6,120}",
            message: "Description must have minimum is 6 charactfers and maximum is 20 characters",
            required: true
        },
        {
            // label: 'Image',
            // name: 'urlImages',
            // value: carSelected.urlImages,
            // required: true,
            // message: "Name must have minimum is 6 characters and maximum is 20 characters",
            // label: 'ID Avatar',
            // name: 'avatar',
            // id: 'avatarCreated',
            // type: 'text', // Đổi type thành 'text'
            // readonly: true, // Sử dụng readonly thay vì disable
            // required: false
        },

    ];
}

async function findRoomById(id) {
    const res = await fetch('/api/cars/' + id);
    return await res.json();
}

async function findRoomDetailById(id) {
    const res = await fetch('/api/cars/detail/' + id);
    return await res.json();
}

async function getCars() {
    const res = await fetch('/api/cars');
    return await res.json();
}

function renderItemStr(item) {

    return `<tr>
                    <td>
                        ${item.id}
                    </td>
                    <td>
                        ${item.name}
                    </td>
                    
                    <td>
                        ${item.licensePlate}
                    </td>

                    <td>
                        ${item.agency}
                    </td>

                    <td>
                        ${item.status}
                    </td>
                     
                    <td>
                        <a class="btn btn-primary text-white  edit " data-id="${item.id}" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Edit</a>           
                        <a class="btn btn-warning text-white delete" onclick="deleteCar(${item.id})" >Delete</a>
                      <a class="btn btn-info text-white detail" data-id="${item.id}"  data-bs-toggle="modal" data-bs-target="#detail-modal" onclick="showDetail(${item.id})" >Detail</a>
                    </td>
                </tr>`
}

function renderTBody(items) {
    let str = '';
    items.forEach(e => {
        str += renderItemStr(e);
    })
    tBody.innerHTML = str;
}

async function renderTable() {
    let url = `/api/cars?page=${pageable.page - 1 || 0}&sort=${pageable.sortCustom || 'id,desc'}&search=${pageable.search || ''}`;
    const response = await fetch(url);
    // const pageable = await getRooms();
    // rooms = pageable.content;
    // renderTBody(rooms);


    const result = await response.json();
    pageable = {
        ...pageable,
        ...result
    };
    genderPagination();
    renderTBody(result.content);
    addEventEditAndDelete();
}

const genderPagination = () => {
    ePagination.innerHTML = '';
    let str = '';
    //generate preview truoc
    str += `<li class="page-item ${pageable.first ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
            </li>`
    //generate 1234

    for (let i = 1; i <= pageable.totalPages; i++) {
        str += ` <li class="page-item ${(pageable.page) === i ? 'active' : ''}" aria-current="page">
      <a class="page-link" href="#">${i}</a>
    </li>`
    }
    //
    //generate next truoc
    str += `<li class="page-item ${pageable.last ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Next</a>
            </li>`
    //generate 1234
    ePagination.innerHTML = str;

    const ePages = ePagination.querySelectorAll('li'); // lấy hết li mà con của ePagination
    const ePrevious = ePages[0];
    const eNext = ePages[ePages.length - 1]

    ePrevious.onclick = () => {
        if (pageable.page === 1) {
            return;
        }
        pageable.page -= 1;
        renderTable();
    }
    eNext.onclick = () => {
        if (pageable.page === pageable.totalPages) {
            return;
        }
        pageable.page += 1;
        renderTable();
    }
    for (let i = 1; i < ePages.length - 1; i++) {
        if (i === pageable.page) {
            continue;
        }
        ePages[i].onclick = () => {
            pageable.page = i;
            renderTable();
        }
    }
}
const onSearch = (e) => {
    e.preventDefault()
    pageable.search = eSearch.value;
    pageable.page = 1;
    renderTable();
}
// const onLoadSort = () => {
//     eHeaderPrice.onclick = () => {
//         let sort = 'price,desc'
//         if(pageable.sortCustom?.includes('price') &&  pageable.sortCustom?.includes('desc')){
//             sort = 'price,asc';
//         }
//         pageable.sortCustom = sort;
//         renderTable();
//     }
// }
const onLoadSort = () => {
    // eHeaderPrice.onclick = () => {
    //     // Xóa các biểu tượng sắp xếp trước đó trên tất cả các cột
    //     const headerCells = document.querySelectorAll('th');
    //     headerCells.forEach(cell => cell.classList.remove('sorted-asc', 'sorted-desc'));
    //
    //     let sort = 'price,desc';
    //     if (pageable.sortCustom?.includes('price') && pageable.sortCustom?.includes('desc')) {
    //         sort = 'price,asc';
    //     }
    //     pageable.sortCustom = sort;
    //     renderTable();
    //
    //     // Thêm biểu tượng sắp xếp vào cột giá (Price)
    //     eHeaderPrice.classList.add(sort === 'price,asc' ? 'sorted-asc' : 'sorted-desc');
    // };
}

const searchInput = document.querySelector('#search');

searchInput.addEventListener('search', () => {
    onSearch(event)
});

const addEventEditAndDelete = () => {
    const eEdits = tBody.querySelectorAll('.edit');
    const eDeletes = tBody.querySelectorAll('.delete');
    for (let i = 0; i < eEdits.length; i++) {
        console.log(eEdits[i].id)
        eEdits[i].addEventListener('click', () => {
            showEdit(eEdits[i].dataset.id);
        })
    }

}

function clearForm() {
    idImages = [];

    const imgEle = document.getElementById("images");
    const imageOld = imgEle.querySelectorAll('img');
    for (let i = 0; i < imageOld.length; i++) {
        imgEle.removeChild(imageOld[i])
    }
    const avatarDefault = document.createElement('img');
    avatarDefault.src = '/assets/inputicon.png';
    avatarDefault.classList.add('avatar-preview');
    imgEle.append(avatarDefault)
    carForm.reset();
    carSelected = {};
}

function showImgInForm(images) {
    const imgEle = document.getElementById("images");
    const imageOld = imgEle.querySelectorAll('img');
    for (let i = 0; i < imageOld.length; i++) {
        imgEle.removeChild(imageOld[i])
    }
    const avatarDefault = document.createElement('img');
    avatarDefault.src = '/assets/inputicon.png';
    avatarDefault.classList.add('avatar-preview');
    imgEle.append(avatarDefault)
    images.forEach((img, index) => {
        let image = document.createElement('img');
        image.src = img;
        image.classList.add('avatar-preview');
        imgEle.append(image)
    })

}

function showCreate() {
    $('#staticBackdropLabel').text('Create Car');
    clearForm();
    renderForm(formBody, getDataInput())
}

async function showEdit(id) {
    $('#staticBackdropLabel').text('Edit Car');
    carSelected = await findRoomById(id);

    showImgInForm(carSelected.urlImages)

    carSelected.specificationIds.forEach(idSpecification => {
        for (let i = 0; i < eCheckBoxSpecifications.length; i++) {
            console.log(+eCheckBoxSpecifications[i].value)
            // console.log(+eCheckBoxSpecifications[i].value)
            if (idSpecification === +eCheckBoxSpecifications[i].value) {
                eCheckBoxSpecifications[i].selected = true;
            }
        }
    })
    renderForm(formBody, getDataInput());


    carSelected.featureIds.forEach(idFeature => {
        for (let i = 0; i < eCheckBoxFeatures.length; i++) {
            if (idFeature === +eCheckBoxFeatures[i].value) {
                eCheckBoxFeatures[i].checked = true;
            }
        }
    })
    // carSelected.surchargeIds.forEach(idSurcharge => {
    //     for (let i = 0; i < eCheckBoxSurcharges.length; i++) {
    //         if (idSurcharge === +eCheckBoxSurcharges[i].value) {
    //             eCheckBoxSurcharges[i].checked = true;
    //         }
    //     }
    // })
    renderForm(formBody, getDataInput());
}

// async function editCar(data) {
//     const res = await fetch('/api/cars/' + data.id, {
//         method: 'PUT',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(data)
//     })
// }
//
// async function createCar(data) {
//     const res = await fetch('/api/cars', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(data)
//     })
// }

async function createCar(data) {
    const res = await fetch('/api/cars', {
        method: 'POST',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json'
        },
    })
}

async function editCar(data) {
    const res = await fetch('/api/cars/' + data.id, {
        method: 'PUT',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json'
        },
    })
}


async function deleteCar(id) {
    const confirmed = confirm("Bạn có chắc chắn muốn xóa xe này?");
    if (confirmed) {
        try {
            const response = await fetch(`/api/cars/${id}`, {
                method: 'DELETE',
            });
            if (response.ok) {
                // Nếu xóa thành công, cập nhật danh sách và hiển thị thông báo
                await renderTable();
                toastr.success('Xe đã được xóa thành công.');
            } else {
                // Xử lý lỗi nếu cần
                toastr.error('Đã xảy ra lỗi khi xóa xe.');
            }
        } catch (error) {
            // Xử lý lỗi nếu cần
            console.error('Lỗi khi gửi yêu cầu xóa xe:', error);
            toastr.error('Đã xảy ra lỗi khi xóa xe.');
        }
    }
}

const sortButton = document.getElementById('sortButton');
sortButton.addEventListener('click', () => {
    const sortDirection = document.getElementById('sortDirection').value;
    pageable.sortCustom = `price,${sortDirection}`;
    renderTable();
});


async function showDetail(id) {
    const carDetail = await findRoomDetailById(id);
    document.getElementById("car-img-1").src = carDetail.urlImages[0];
    document.getElementById("car-img-2").src = carDetail.urlImages[1];
    document.getElementById("car-img-3").src = carDetail.urlImages[2];
    eModalCarName.innerText = carDetail.name;
    eModalCarDes.innerText = carDetail.description;
    eModalCarStatus.innerText = carDetail.status;
    eModalPriceHours.innerText = carDetail.priceHours;
    eModalPriceDays.innerText = carDetail.priceDays;
    eModalPriceDeliverys.innerText = carDetail.priceDelivery;
    eModalSpecification.innerText = carDetail.specificationNames.join(", ");
    eModalFeature.innerText = carDetail.featureNames.join(", ");
    eModalAgency.innerText = carDetail.agencyName;
}


let idImages = [];

async function previewImage(evt) {
    if (evt.target.files.length === 0) {
        return;
    }
    idImages = [];

    const imgEle = document.getElementById("images");
    const imageOld = imgEle.querySelectorAll('img');
    for (let i = 0; i < imageOld.length; i++) {
        imgEle.removeChild(imageOld[i])
    }

    // When the image is loaded, update the img element's src
    const files = evt.target.files

    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        await previewImageFile(file, i);

        if (file) {
            disableSaveChangesButton();
            // Create a new FormData object and append the selected file
            const formData = new FormData();
            formData.append("avatar", file);
            formData.append("fileType", "image");
            try {
                // Make a POST request to upload the image
                const response = await fetch("/api/image", {
                    method: "POST",
                    body: formData,
                });
                if (response.ok) {
                    const result = await response.json();
                    if (result) {
                        const id = result.id;
                        idImages.push(id);

                    } else {
                        console.error('Image ID not found in the response.');
                    }
                } else {
                    // Handle non-OK response (e.g., show an error message)
                    console.error('Failed to upload image:', response.statusText);
                }
            } catch (error) {
                // Handle network or other errors
                console.error('An error occurred:', error);
            }
            enableSaveChangesButton();
        }
    }
    console.log(idImages)
}

async function previewImageFile(file) {
    const reader = new FileReader();
    reader.onload = function () {
        const imgEle = document.getElementById("images");
        const img = document.createElement('img');
        img.src = reader.result;
        img.classList.add('avatar-preview');
        imgEle.append(img);

        // imgEle.ap.innerHTML = `
        //          <img class="avatar-preview" src="${urlImage}">
        //             <span class="icon-preview-delete">
        //               <i class="fa-solid fa-delete-left" onclick="onRemoveImage(index)"></i>
        //            </span>
        //     `;
        // imgEle.append(spanAPItemContainer)

    };
    reader.readAsDataURL(file);

}


function onRemoveImage(index) {
    idImages = idImages.filter((e, i) => i !== index);
    const imgEle = document.getElementById("file");
    const imageOld = imgEle.querySelectorAll('img');
    imgEle.removeChild(imageOld[index]);
}


// Hàm cập nhật giá giờ theo định dạng tiền tệ VND
// Hàm định dạng giá tiền tệ VND
function formatCurrency(amount) {
    return amount.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
}

function getSpecificationSelects() {
    // Lấy giá trị từ data cho các Specification (Seats, Fuel, Gear, ...)
    // const seatsValue = data.seats;
    // const fuelValue = data.fuel;
    // const transmissionValue = data.transmission
    // const luggageValue = data.luggage;

    const seatsSelect = document.getElementById('seats');
    let seatsValue = seatsSelect.value;

    const fuelSelect = document.getElementById('fuel');
    let fuelValue = fuelSelect.value;

    const transmissionSelect = document.getElementById('transmission');
    let transmissionValue = transmissionSelect.value;

    const luggageSelect = document.getElementById('luggage');
    let luggageValue = luggageSelect.value;

    return {
        seatsValue,
        fuelValue,
        transmissionValue,
        luggageValue
    };
}

// 2 hàm để tự động Disable nút SaveChange khi tải ảnh lên
function disableSaveChangesButton() {
    const saveChangesButton = document.getElementById('saveChangesButton');
    saveChangesButton.disabled = true;
}

function enableSaveChangesButton() {
    const saveChangesButton = document.getElementById('saveChangesButton');
    saveChangesButton.disabled = false;
}