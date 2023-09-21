const eCarsContainer = document.getElementById("cars-container");
const ePagination = document.getElementById('pagination');
const eSearchForm = document.getElementById("search-car-form");

// const eSearchCarBtn = document.getElementById("search-car-btn");

// const eKeySearch = document.getElementById("key-search");
// const ePickupTime = document.getElementById("pickup-time");
// const eDropOfTime = document.getElementById("drop_off_time");
// const ePickupLocation = document.getElementById("pickup_location");
// const eDropOffLocation = document.getElementById("drop-off-location");
// Create a function to build the API URL with query parameters
const urlParams = new URLSearchParams(window.location.href.split('?')[1]);

// Get individual query parameters
const search = urlParams.get("search");
const pickupTime = urlParams.get("pickupTime");
const dropOffTime = urlParams.get("dropOffTime");
const pickupLocation = urlParams.get("pickupLocation");
const dropOffLocation = urlParams.get("dropOffLocation");



let pageable = {
    page: 1,
    sort: 'id,desc',
    pickupTime: pickupTime || '',
    dropOffTime: dropOffTime || '',
    search: search || "",
    pickupLocation: pickupLocation || "",
    dropOffLocation: dropOffLocation || ""
}

function buildApiUrl(pageable) {
    return `http://localhost:8080/user/api/cars/available-cars?page=${pageable.page - 1 || 0}&sort=${pageable.sortCustom || 'id,desc'}&search=${pageable.search}&pickupTime=${pageable.pickupTime}&dropOffTime=${pageable.dropOffTime}&pickupLocation=${pageable.pickupLocation}&dropOffLocation=${pageable.dropOffLocation}`;
}

function renderSearchForm() {

    let formHTML =
        `
        <h2>Make your trip</h2>
                                    <div class="form-group">
                                        <label for="key-search" class="label">Enter your keyword</label>
                                        <input type="text" class="form-control" id="key-search" name="key-search"
                                               placeholder="Search by name, type,..." value="${pageable.search}">
                                    </div>
                                    <div class="form-group">
                                        <label for="pickup_location" class="label">Pick-up location</label>
                                        <input type="text" class="form-control" id="pickup_location" name="pickup_location"
                                               placeholder="City, Airport, Station, etc" value="${pageable.pickupLocation}">
                                    </div>
                                    <div class="form-group">
                                        <label for="drop_off_location" class="label">Drop-off location</label>
                                        <input type="text" class="form-control" id="drop_off_location" name="drop_off_location"
                                               placeholder="City, Airport, Station, etc" value="${pageable.dropOffLocation}">
                                    </div>

                                    <div class="form-group mr-2">
                                        <label for="pickup_time" class="label">Pick-up time</label>
                                        <input type="datetime-local" class="form-control" id="pickup_time" name="pickup_time"
                                               placeholder="Date" value="${pageable.pickupTime}">
                                    </div>
                                    <div class="form-group mr-2">
                                        <label for="drop_off_time" class="label">Drop-off time</label>
                                        <input type="datetime-local" class="form-control" id="drop_off_time" name="drop_off_time"
                                               placeholder="Date"  value="${pageable.dropOffTime}">
                                    </div>
                                    <div class="form-group">
                                        <button type="button" id="search-car-btn" class="btn btn-secondary py-3 px-4">
                                            Check Available
                                        </button>
                                    </div>
        `
    eSearchForm.innerHTML = formHTML;

}

async function getAvailableCars() {
    const APIAvailableCars = buildApiUrl(pageable); // Build the API URL
    const res = await fetch(APIAvailableCars);
    return await res.json();
}

function renderCars(cars) {
    if (cars === null || cars.length === 0) {
        eCarsContainer.innerHTML = `<img src="/assets/user/images/no-result-found.jpg" style="width: 100%" alt="NotFound">`
        return;
    }
    let carsHTML = '';
    cars.forEach((car) => {
        carsHTML +=
            `
             <div class="col-md-6">
                        <div class="car-wrap rounded ">
                            <div class="img rounded d-flex align-items-end"
                                 style="background-image: url(${car.urlImages[0]});">
                            </div>
                            <div class="text">
                                <h2 class="mb-0"><a href="/car-detai/${car.id}">${car.name}</a></h2>
                                <div class="d-flex mb-3">
                                    <span class="cat">${car.agency}</span>
                                    <p class="price ml-auto">${car.priceDays} <span>/day</span></p>
                                </div>
                                <div class="d-flex justify-content-center">
                                    <p class="d-flex mb-0 d-block"><a href="/car-detail/${car.id}" class="btn btn-primary py-2 mr-1">Book
                                        now</a> <a href="/car-detail/${car.id}" class="btn btn-secondary py-2 ml-1">Details</a>
                                    </p>
                                </div>

                            </div>
                        </div>
                    </div>
        `;
    });
    eCarsContainer.innerHTML = carsHTML;
}

async function renderAvailableCars() {
    const result = await getAvailableCars();
    pageable = {
        ...pageable,
        ...result
    };
    renderCars(result.content);
    genderPagination();
}


window.onload = async () => {
    await handleLogBtn();
    renderSearchForm();
    handleSearchCar();
    await renderAvailableCars();

};

const genderPagination = () => {
    ePagination.innerHTML = '';
    let str = '';

    const pagesToShow = 5; // Number of pages to display

    const startPage = Math.max(1, pageable.page - 2); // Calculate the start page
    const endPage = Math.min(pageable.totalPages, startPage + pagesToShow - 1); // Calculate the end page

    // Generate "Previous" button
    str += `<li class="page-item ${pageable.first ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
            </li>`;

    // Generate page numbers
    for (let i = startPage; i <= endPage; i++) {
        str += `<li class="page-item ${(pageable.page === i) ? 'active' : ''}" aria-current="page">
                  <a class="page-link" href="#">${i}</a>
                </li>`;
    }

    // Generate "Next" button
    str += `<li class="page-item ${pageable.last ? 'disabled' : ''}">
              <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Next</a>
            </li>`;

    ePagination.innerHTML = str;

    const ePages = ePagination.querySelectorAll('li');
    const ePrevious = ePages[0];
    const eNext = ePages[ePages.length - 1];

    ePrevious.onclick = async () => {
        if (!pageable.first) {
            pageable.page -= 1;
            await renderAvailableCars();
            scrollCarsContainerToTop();
        }
    };

    eNext.onclick = async () => {
        if (!pageable.last) {
            pageable.page += 1;
            await renderAvailableCars();
            scrollCarsContainerToTop();
        }
    };

    for (let i = 1; i < ePages.length - 1; i++) {
        ePages[i].onclick = async () => {
            if (pageable.page !== i) {
                pageable.page = i;
                await renderAvailableCars();
                scrollCarsContainerToTop();
            }
        };
    }
};

function scrollCarsContainerToTop() {
    // Use smooth scrolling to scroll to the top of the cars container
    eCarsContainer.scrollIntoView({behavior: 'smooth', block: 'start'});
}

function handleSearchCar() {
    const eSearchCarBtn = document.getElementById("search-car-btn");

    // eSearchCarBtn.onclick = () => {
    //     alert('OK')
    // }
    eSearchCarBtn.onclick = async () => {
        // let form = new FormData(eSearchCarForm);
        let form = new FormData(eSearchForm);
        pageable.search = form.get("key-search");
        pageable.pickupTime = form.get("pickup_time")
        pageable.dropOffTime = form.get("drop_off_time")
        pageable.pickupLocation = form.get("pickup_location")
        pageable.dropOffLocation = form.get("drop_off_location")
        let url = `/cars/available-cars?search=${pageable.search}&pickupTime=${pageable.pickupTime}&dropOffTime=${pageable.dropOffTime}&pickupLocation=${pageable.pickupLocation}&dropOffLocation=${pageable.dropOffLocation}`
        console.log("search btn:  ")
        console.log(url);
        localStorage.setItem('pageable', JSON.stringify(pageable));
        await renderAvailableCars();
    }
}

function showLogin() {
    // $("#exampleModal").show()
    $('#exampleModal').modal('hide');
}

async function handleLogBtn() {
    let customer = await getCurrentCustom();
    console.log(customer);
    if (customer.username === "anonymousUser") {
        document.getElementById("log-btn").innerText = "Log in";
        document.getElementById("log-btn").setAttribute("href", "");
        document.getElementById("log-btn").setAttribute("click", showLogin);
    }
}

async function getCurrentCustom() {
    let res = await fetch("user/api/customer-detail");
    return await res.json();
}


function showLogin() {
    // $("#exampleModal").show()
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
    if (message.includes("successfully")) {
        toastr.success(message);
    } else {
        toastr.error(message);
    }
}

function getQueryParam(key) {
    const urlSearchParams = new URLSearchParams(window.location.search);
    return urlSearchParams.get(key);
}

