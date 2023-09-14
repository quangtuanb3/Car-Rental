const carForm = document.getElementById('carForm');
const eCheckBoxSpecifications = document.getElementsByName('specifications');
const eCheckBoxFeatures = document.getElementsByName('features');
const eCheckBoxSurcharges = document.getElementsByName('surcharges');
let carSelected = {};
const formBody = document.getElementById('formBody');
const tBody = document.getElementById('tBody');
const ePagination = document.getElementById('pagination')
const eSearch = document.getElementById('search')
const eHeaderPrice = document.getElementById('header-price')
let specifications;
let features;
let surcharges;
let agencies;
let urlImages
let cars = [];

let pageable = {
    page: 1,
    sort: 'id,desc',
    search: ''
}

carForm.onsubmit = async (e) => {
    e.preventDefault();
    let data = getDataFromForm(carForm);
    data = {
        ...data,
        agency: {
            id: data.agency
        },
        idSpecifications: Array.from(eCheckBoxSpecifications)
            .filter(e => e.checked)
            .map(e => e.value),
        idFeatures: Array.from(eCheckBoxFeatures)
            .filter(e => e.checked)
            .map(e => e.value),
        idSurcharges: Array.from(eCheckBoxSurcharges)
            .filter(e => e.checked)
            .map(e => e.value),
        urlImages: data.urlImages.split(","),
        id: carSelected.id

    }
    console.log(data);

    let message = "Created"
    if (carSelected.id) {
        await editCar(data);
        message = "Edited"
    } else {
        await createCar(data)
    }

    alert(message);
    renderTable();
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
async function getFeaturesSelectOption() {
    const res = await fetch('api/features');
    return await res.json();
}
async function getSurchargesSelectOption() {
    const res = await fetch('api/surcharges');
    return await res.json();
}

async function getAgenciesSelectOption() {
    const res = await fetch('api/agencies');
    return await res.json();
}
async function getImagesSelectOption() {
    const res = await fetch('api/files');
    return await res.json();
}

window.onload = async () => {
    specifications = await getSpecificationsSelectOption();
    features = await getFeaturesSelectOption();
    surcharges = await getSurchargesSelectOption();
    agencies = await getAgenciesSelectOption();
    // urlImages = await getImagesSelectOption();
    await renderTable();
    onLoadSort();

    renderForm(formBody, getDataInput());
}


function getDataInput() {
    return [
        {
            label: 'Name',
            name: 'name',
            value: carSelected.name,
            required: true,
            pattern: "^[A-Za-z ]{6,20}",
            message: "Name must have minimum is 6 characters and maximum is 20 characters",
        },
        {
            label: 'Status',
            name: 'status',
            value: carSelected.status,
            required: true,
            pattern: "^[A-Za-z ]{6,20}",
            message: "Status must have minimum is 6 characters and maximum is 20 characters",
        },
        {
            label: 'License Plate',
            name: 'licensePlate',
            value: carSelected.licensePlate,
            required: true,
            pattern: "^[A-Z0-9 ]{6,20}",
            message: "License Plate must have minimum is 6 characters and maximum is 20 characters",
        },
        {
            label: 'Agency',
            name: 'agency',
            value: carSelected.agencyId,
            type: 'select',
            required: true,
            options: agencies,
            message: 'Please choose Agency'
        },
        {
            label: 'Price Hours',
            name: 'priceHours',
            value: carSelected.priceHours,
            pattern: "[1-9][0-9]{1,10}",
            message: 'Price Hours errors',
            required: true
        },
        {
            label: 'Price Days',
            name: 'priceDays',
            value: carSelected.priceDays,
            pattern: "[1-9][0-9]{1,10}",
            message: 'Price Days Hours errors',
            required: true
        },
        {
            label: 'Description',
            name: 'description',
            value: carSelected.description,
            pattern: "^[A-Za-z ]{6,120}",
            message: "Description must have minimum is 6 characters and maximum is 20 characters",
            required: true
        },
        {
            label: 'Image',
            name: 'urlImages',
            value: carSelected.urlImages,
            required: true,
            // pattern: "^[A-Za-z ]{6,20}",
            // message: "Name must have minimum is 6 characters and maximum is 20 characters",
        },

    ];
}


async function findRoomById(id) {
    const res = await fetch('/api/cars/' + id);
    return await res.json();
}


async function showEdit(id) {
    $('#staticBackdropLabel').text('Edit Car');
    clearForm();
    carSelected = await findRoomById(id);
    carSelected.specificationIds.forEach(idSpecification => {
        for (let i = 0; i < eCheckBoxSpecifications.length; i++) {
            if (idSpecification === +eCheckBoxSpecifications[i].value) {
                eCheckBoxSpecifications[i].checked = true;
            }
        }
    })
    carSelected.featureIds.forEach(idFeature => {
        for (let i = 0; i < eCheckBoxFeatures.length; i++) {
            if (idFeature === +eCheckBoxFeatures[i].value) {
                eCheckBoxFeatures[i].checked = true;
            }
        }
    })
    carSelected.surchargeIds.forEach(idSurcharge => {
        for (let i = 0; i < eCheckBoxSurcharges.length; i++) {
            if (idSurcharge === +eCheckBoxSurcharges[i].value) {
                eCheckBoxSurcharges[i].checked = true;
            }
        }
    })
    // carSelected.imageIds.forEach(idImage => {
    //     for (let i = 0; i < eCheckBoxSurcharges.length; i++) {
    //         if (idImage === +eCheckBoxSurcharges[i].value) {
    //             eCheckBoxFeatures[i].checked = true;
    //         }
    //     }
    // })
    renderForm(formBody, getDataInput());
}


async function getCars() {
    const res = await fetch('/api/cars');
    return await res.json();
}

function renderItemStr(item, index) {
    return `<tr>
                    <td>
                        ${index + 1}
                    </td>
                    <td>
                        ${item.name}
                    </td>
                    <td>
                        ${item.status}
                    </td>
                    <td>
                        ${item.licensePlate}
                    </td>
                    <td>
                        ${item.description}
                    </td>
                   
                    <td>
                        ${item.priceHours}
                    </td>
                    <td>
                        ${item.priceDays}
                    </td>
                    <td>
                        ${item.agency}
                    </td>
                    <td>
                        ${item.specifications}
                    </td>
                    <td>
                        ${item.features}
                    </td>
                    <td>
                        ${item.surcharges}
                    </td>
                    <td>
                        ${item.urlImages}
                    </td>
                     
                    <td>
                        <a class="btn btn-primary text-white  edit " data-id="${item.id}" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Edit</a>           
                        <a class="btn btn-warning text-white delete" onclick="deleteCar(${item.id})" >Delete</a>
                      <a class="btn btn-warning text-white detail" data-id="${item.id}"  data-bs-toggle="modal" data-bs-target="#detail-modal" onclick="showDetail(${item.id})" >Detail</a>
                    </td>
                </tr>`
}

function renderTBody(items) {
    let str = '';
    items.forEach((e, i) => {
        str += renderItemStr(e, i);
    })
    tBody.innerHTML = str;
}

async function renderTable() {
    let url = `/api/cars?page=${pageable.page - 1 || 0}&sort=${pageable.sortCustom || 'id,desc'}&search=${pageable.search || ''}`;
    console.log(url);
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
    const eNext = ePages[ePages.length-1]

    ePrevious.onclick = () => {
        if(pageable.page === 1){
            return;
        }
        pageable.page -= 1;
        renderTable();
    }
    eNext.onclick = () => {
        if(pageable.page === pageable.totalPages){
            return;
        }
        pageable.page += 1;
        renderTable();
    }
    for (let i = 1; i < ePages.length - 1; i++) {
        if(i === pageable.page){
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
    eHeaderPrice.onclick = () => {
        // Xóa các biểu tượng sắp xếp trước đó trên tất cả các cột
        const headerCells = document.querySelectorAll('th');
        headerCells.forEach(cell => cell.classList.remove('sorted-asc', 'sorted-desc'));

        let sort = 'price,desc';
        if (pageable.sortCustom?.includes('price') && pageable.sortCustom?.includes('desc')) {
            sort = 'price,asc';
        }
        pageable.sortCustom = sort;
        renderTable();

        // Thêm biểu tượng sắp xếp vào cột giá (Price)
        eHeaderPrice.classList.add(sort === 'price,asc' ? 'sorted-asc' : 'sorted-desc');
    };
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
    carForm.reset();
    carSelected = {};
}

function showCreate() {
    $('#staticBackdropLabel').text('Create Car');
    clearForm();
    renderForm(formBody, getDataInput())
}

async function editCar(data) {
    const res = await fetch('/api/cars/' + data.id, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
}

async function createCar(data) {
    const res = await fetch('/api/cars', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
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


async function showDetail(id){
let carDetail = await findRoomById(id)
    let eModalCarName = document.getElementById("modal-car-name");
    let eModalCarDes = document.getElementById("modal-car-description");
eModalCarName.innerText = carDetail.name;
    eModalCarDes.innerText = carDetail.description;
}