const eSearchCarBtn = document.getElementById("search-car-btn");
const eSearchCarForm = document.getElementById("search-car-form");
// const eShowDetail = document.getElementById("show-detail-btn");
const eBestCarContainer = document.getElementById("best-car-container");
let pageable = {
    page: 1,
    sort: 'id,desc',
    search: '',
    pickupTime: '',
    dropOffTime: '',
    pickupLocation: '',
    dropOffLocation: '',
}
eSearchCarBtn.onclick = () => {
    const form = new FormData(eSearchCarForm);
    pageable.search = form.get("key-search");
    pageable.pickupTime = form.get("pickup_time")
    pageable.dropOffTime = form.get("drop_off_time")
    pageable.pickupLocation = form.get("pickup_location")
    pageable.dropOffLocation = form.get("drop_off_location")
    let url = `/cars/available-cars?search=${pageable.search}&pickupTime=${pageable.pickupTime}&dropOffTime=${pageable.dropOffTime}&pickupLocation=${pageable.pickupLocation}&dropOffLocation=${pageable.dropOffLocation}`
    console.log(url);
    window.location.href = url;

}

function renderItems(result) {
    let divItems = '';
    result.forEach((car) => {
        divItems +=
            `
               <div class="item">
                        <div class="car-wrap rounded">
                            <div class="img rounded d-flex align-items-end"
                                 style="background-image: url(${car.urlImages[0]});">
                            </div>
                            <div class="text">
                                <h2 class="mb-0"><a href="/car-detail/${car.id}">${car.name}</a></h2>
                                <div class="d-flex mb-3">
                                    <span class="cat">${car.agency}</span>
                                    <p class="price ml-auto">$ ${car.priceDays} <span>/day</span></p>
                                </div>
                                <p class="d-flex mb-0 d-block"><a href="/car-detail/${car.id}" class="btn btn-primary py-2 mr-1">Book
                                    now</a> <a href="/car-detail/${car.id}" class="btn btn-secondary py-2 ml-1">Details</a></p>
                            </div>
                        </div>
                    </div>
            `
    })
    eBestCarContainer.innerHTML = divItems;
}

const carousel = function () {
    $('.carousel-car').owlCarousel({
        center: true,
        loop: true,
        autoplay: true,
        items: 4,
        margin: 30,
        stagePadding: 0,
        nav: false,
        navText: ['<span class="ion-ios-arrow-back">', '<span class="ion-ios-arrow-forward">'],
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 2
            },
            1000: {
                items: 3
            }
        }
    });
    $('.carousel-testimony').owlCarousel({
        center: true,
        loop: true,
        items: 1,
        margin: 30,
        stagePadding: 0,
        nav: false,
        navText: ['<span class="ion-ios-arrow-back">', '<span class="ion-ios-arrow-forward">'],
        responsive: {
            0: {
                items: 1
            },
            600: {
                items: 2
            },
            1000: {
                items: 3
            }
        }
    });

};

async function renderBestCars() {
    let url = `/user/api/cars/best-cars`;
    console.log(url);
    const response = await fetch(url);
    const result = await response.json();
    renderItems(result);
    carousel();
}

window.onload = async () => {
    await renderBestCars();
    await handleLogBtn();
    showMsg();


}

function showLogin() {
    $('#exampleModal').modal('show');
}

async function handleLogBtn() {
    let customer = await getCurrentCustom();
    console.log(customer);

    const loginBtn = document.getElementById("log-btn");
    if (customer.email === null) {
        loginBtn.innerText = "Login";
        loginBtn.href = "javascript:void(0)"; // Remove the "href" attribute
        loginBtn.onclick = () => {
            showLogin();
        };
    } else {
        loginBtn.innerText = "Logout"; // Change the text for authenticated users
        loginBtn.href = "/logout"; // Update the "href" attribute for logout
        loginBtn.onclick = null; // Remove the click event handler
    }
}

async function getCurrentCustom() {
    let res = await fetch("user/api/customer-detail");
    return await res.json();
}

function showMsg() {
    // Check if a "message" query parameter exists in the URL
    const message = getQueryParam("message");

// Show the logout message if it exists
    if (message === null) {
        return;
    } else if (message.includes("successfully")) {
        toastr.success(message);
    } else {
        toastr.error(message);
    }
}

function getQueryParam(key) {
    const urlSearchParams = new URLSearchParams(window.location.search);
    return urlSearchParams.get(key);
}

