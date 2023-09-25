// const socket = new SockJS('/ws');
// const stompClient = Stomp.over(socket);

const eSearchCarForm = document.getElementById("search-car-form");
const eConfirm_body = document.getElementById("confirm-body");
const ePickup_time = document.getElementById("pickup-time");
const eDrop_off_time = document.getElementById("drop-off-time");
const eRent_price = document.getElementById("rent-price");
const rentNowButton = document.getElementById("rent-now-btn");
const urlAPICar = "/user/api/cars" + window.location.pathname;

const ePickUpLocation = document.getElementById("pickup-location");
const eDropOffLocation = document.getElementById("drop-off-location");
const eDeliveryFeeDisplay = document.getElementById("delivery-fee-display");
const eTotalDisplay = document.getElementById("total-display");
const eTradeCode = document.getElementById("trade-code");
const eDoneBtn = document.getElementById("done-btn");

const formData = new FormData(eSearchCarForm);
let pickup_time = formData.get("pickup-time");
let drop_off_time = formData.get("drop-off-time");
let pickup_location = formData.get("pickup-location");
let drop_off_location = formData.get("drop-off-location");

let officeLocation = "28 Nguyễn Tri Phương, Phú Nhuận, thừa thiên huế, Việt Nam";
let deliveryLocation = "12 Trần Quốc Toản, phường Tây Lộc, Huế, Thừa Thiên Huế, Việt Nam";
officeLocation = encodeURIComponent(officeLocation);
let car;
let customer;
let data = {
    pickup_time: pickup_time,
    drop_off_time: drop_off_time,
    pickup_location: pickup_location,
    drop_off_location: drop_off_location,

    rent_price: eRent_price.value,
    delivery_fee: eDeliveryFeeDisplay.value,
    total: eTotalDisplay.value,
    deposit: (+eTotalDisplay.value * 10 / 100).toFixed(1)
};

async function getCurrentCar(urlAPICar) {
    let res = await fetch(urlAPICar);
    return await res.json();
}

async function getCurrentCustom() {
    let res = await fetch("http://localhost:8080/user/api/customer-detail");
    return await res.json();
}


eRelatedCars = document.getElementById("related-car-container");

async function calculateDeliveryFee() {
    let distancePickUp = await getDistancePickUp();
    let distanceDropOff = await getDistanceDropOff();
    let deliveryFee = 0;
    if (!Number.isNaN(Number.parseFloat(distancePickUp))) {
        deliveryFee = (+deliveryFee + +distancePickUp * car.priceDelivery).toFixed(1);
    }
    if (!Number.isNaN(Number.parseFloat(distanceDropOff))) {
        deliveryFee = (+deliveryFee + +distanceDropOff * car.priceDelivery).toFixed(1);

    }
    eDeliveryFeeDisplay.innerText = deliveryFee + ' $';
    data.delivery_fee = deliveryFee;
    return deliveryFee;
}

async function calculateTotal() {
    let deliveryFee = await calculateDeliveryFee();
    let rentCarPrice = await calculateRentPrice();
    let total = (+deliveryFee + +rentCarPrice).toFixed(1);
    data.total = total;
    data.deposit = (+total * 10 / 100).toFixed(1);
    eTotalDisplay.innerText = total;
    return total;


}

function renderDataInFormSearch() {

    let formInputData = JSON.parse(localStorage.getItem('formData'));
    if (formInputData) {
        document.getElementById("pickup-time").value = formInputData.pickupTime;
        document.getElementById("pickup-time").innerText = formInputData.pickupTime;
        document.getElementById("drop-off-time").value = formInputData.dropOffTime;
        document.getElementById("drop-off-time").innerText = formInputData.dropOffTime;
        document.getElementById("pickup-location").value = formInputData.pickupLocation;
        document.getElementById("pickup-location").innerText = formInputData.pickupLocation;
        document.getElementById("drop-off-location").value = formInputData.dropOffLocation;
        document.getElementById("drop-off-location").innerText = formInputData.dropOffLocation;
    }

}


async function renderRelatedCars() {
    let url = `/user/api/cars/related-cars/${car.id}/${car.agency}/${car.seats || '4-seats'}/${car.priceDays}`;
    console.log(url);
    const response = await fetch(url);
    const result = await response.json();
    renderItems(result);
}

function renderItems(result) {
    let divItems = '';
    result.forEach((car) => {
        divItems +=
            `
              <div class="col-md-4">
                <div class="car-wrap rounded ftco-animate fadeInUp ftco-animated">
                    <div class="img rounded d-flex align-items-end" style="background-image: url(${car.urlImages[0]});">
                    </div>
                    <div class="text">
                        <h2 class="mb-0"><a href="/car-detail/${car.id}">${car.name}</a></h2>
                        <div class="d-flex mb-3">
                            <span class="cat">${car.agency}</span>
                            <p class="price ml-auto">$${car.priceDays} <span>/day</span></p>
                        </div>
                        <p class="d-flex mb-0 d-block"><a href="/car-detail/${car.id}" class="btn btn-primary" style="width: 100%">Book now</a></p>
                    </div>
                </div>
            </div>
            `
    })
    eRelatedCars.innerHTML = divItems;
}

async function handleLogBtn() {
    let customer = await getCurrentCustom();
    console.log(customer);

    const loginBtn = document.getElementById("log-btn");
    const eRegisterLi = document.getElementById("menu-register");

    if (customer.email === null) {
        loginBtn.innerText = "Login";
        loginBtn.href = "javascript:void(0)"; // Remove the "href" attribute
        loginBtn.onclick = () => {
            showLogin();
        };
        eRegisterLi.innerHTML = `<a href="/register" class="nav-link">Register</a>`;
    } else {
        loginBtn.innerText = "Logout"; // Change the text for authenticated users
        loginBtn.href = "/logout"; // Update the "href" attribute for logout
        loginBtn.onclick = null; // Remove the click event handler
        eRegisterLi.innerHTML = "";
        if (customer.role === "ROLE_ADMIN") {
            eRegisterLi.innerHTML = `<a href="/home" class="nav-link">Dashboard</a>`;
        }
    }
}

async function getDistancePickUp() {
    let pickup = ePickUpLocation.value.trim();
    if (pickup !== "") {
        pickup = encodeURIComponent(pickup);
        let mapApiUrl = createMapQuestURL(officeLocation, pickup)
        const res = await fetch(mapApiUrl);
        let result = await res.json();
        console.log(result.route.distance);
        if (result.route.distance) {
            data.pickup_location = ePickUpLocation.value.trim();
            return (result.route.distance * 1.6).toFixed(1);

        } else {
            toastr.error("Invalid pickup location");
            return false;
        }

    }
    return true;
}

async function getDistanceDropOff() {
    let dropOff = eDropOffLocation.value.trim();
    if (dropOff !== "") {
        dropOff = encodeURIComponent(dropOff);
        let mapApiUrl = createMapQuestURL(officeLocation, dropOff)
        const res = await fetch(mapApiUrl);
        let result = await res.json();
        console.log(result)
        if (result.route.distance) {
            data.drop_off_location = eDropOffLocation.value.trim();
            return (result.route.distance * 1.6).toFixed(1);
        } else {
            toastr.error("Invalid drop-off location");
            return false;
        }
    }
    return true;
}


ePickUpLocation.onblur = async function () {
    await calculateTotal()
}
eDropOffLocation.onblur = async function () {
    await calculateTotal()
}

function createMapQuestURL(location1, location2) {
    return `https://www.mapquestapi.com/directions/v2/route?key=tHhcvtGjlPCJsOE1HBmjKwL7u1j5wV1V&from=${location1}&to=${location2}`;
}

rentNowButton.addEventListener("click", async function () {

    const currentTime = new Date();

    // Validate pickup time and drop-off time
    const pickup = document.getElementById("pickup-time").value;
    const dropOff = document.getElementById("drop-off-time").value;
    if (pickup === '' || dropOff === '') {
        toastr.error("Invalid rental time");
        return;
    }
    if (new Date(pickup) <= currentTime) {
        toastr.error("Invalid Pickup time");
        return;
    }

    if (new Date(dropOff) <= new Date(pickup)) {
        toastr.error("Invalid drop-off time");
        return;
    }

    const isCarAvailable = await checkCarIfAvailable();
    if (!isCarAvailable) {
        toastr.warning("Car is not available this time");
        return;
    }

    showConfirm(data)


});

function showConfirm(data) {
    console.log(car);
    if (customer.email === null) {
        toastr.warning("Need to login first!");
        return;
    }
    let pickup_time = formatDate(data.pickup_time);
    let drop_off_time = formatDate(data.drop_off_time);
    let pickup_location = truncateTextWithEllipsis(data.pickup_location, 50);
    let drop_off_location = truncateTextWithEllipsis(data.drop_off_location, 50);


    let bodyHTML =
        `
    <div id="user-detail-container" class="row justify-content-between" style="width: 95%; margin: auto">
                    <h5 class="col-12">Customer detail: </h5>
                    <p class="col-6">Name: <span>${customer.name}</span></p>
                    <p class="col-6">Phone: <span>${customer.numberPhone}</span></p>
                    <p class="col-6">Email: <span>${customer.email}</span></p>
                    <p class="col-6">ID Number: <span>${customer.idNumber}</span></p>
                </div>
                <hr style="background-color: #b4bdf1; width: 90%; margin: auto">
                <div id="car-detail-container" class="row justify-content-between mt-3"
                     style="width: 95%; margin: auto">
                    <h5 class="col-12">Car detail: </h5>
                    <p class="col-6">Name: <span> ${car.name}</span></p>
                    <p class="col-6">Agency: <span> ${car.agency}</span></p>
                    <p class="col-6">Seats: <span>${car.seats || '4-seats'}</span></p>
                    <p class="col-6">Transmission: <span>${car.transmission === '' ? "AUTO" : car.transmission}</span></p>
                </div>
                <hr style="background-color: #b4bdf1; width: 90%; margin: auto">

                <div id="rental-detail-container" class="row justify-content-between mt-3"
                     style="width: 95%; margin: auto">
                    <h5 class="col-12">Rental detail: </h5>
                    <p class="col-6">Pickup Time: <span id="confirm-pickup-time">${pickup_time}</span></p>
                    <p class="col-6">Drop-off Time: <span id="confirm-drop-off-time"> ${drop_off_time}</span></p>
                    <p class="col-12">Pickup Location: <span id="confirm-pickup-location">${pickup_location}</span></p>
                    <p class="col-12">Drop-off Location: <span id="confirm-drop-off-location">loc${drop_off_location}ation</span></p>
                    <p class="col-6">Rent price: </p>
                    <p class="col-6 text-right pr-5" id="confirm-rent-price">${data.rent_price} $</p>
                    <p class="col-6">Delivery Fee: </p>
                    <p class="col-6 text-right pr-5" id="confirm-delivery-fee">${data.delivery_fee} $</p>
                    <p class="col-6 ">Total: </p>
                    <p class="col-6 text-right pr-5" id="confirm-total">${data.total} $</p>
                    <p class="col-6 ">Deposit: </p>
                    <p class="col-6 text-right pr-5" id="confirm-deposit">${data.deposit} $</p>
                    <p class="col-12">(Please scan the QR code below to make a deposit)</p>
                    <div style="width: 25%; margin: auto">
                        <img src="/assets/user/images/QR.jpg" alt="qr" style="width: 100%">
                    </div>
                    <label class="col-12 pt-3" style="color: black"> Enter your Trading code here:
                        <input id="trade-code" type="text"  placeholder="xxxx-xxxx-xxxx">
                    </label>

                </div>
    `
    eConfirm_body.innerHTML = bodyHTML;
    $('#exampleModalLong').modal('show');
}

function formatCurrency(currency) {
    return currency + " $"
}

function formatDate(inputDateString) {
    const inputDate = new Date(inputDateString);

    const yyyy = inputDate.getFullYear();
    const mm = String(inputDate.getMonth() + 1).padStart(2, '0'); // Months are zero-based
    const dd = String(inputDate.getDate()).padStart(2, '0');
    const HH = String(inputDate.getHours()).padStart(2, '0');
    const mmFormatted = String(inputDate.getMinutes()).padStart(2, '0');

    return `${yyyy}-${mm}-${dd} ${HH}:${mmFormatted}`;
}

function truncateTextWithEllipsis(text, maxLength) {
    if (text.length > maxLength) {
        const textStart = text.substring(0, maxLength / 2);
        const textEnd = text.substring(text.length - maxLength / 2);
        return `${textStart}....${textEnd}`;
    } else {
        return text;
    }
}

function calculateRentPrice() {
    pickup_time = ePickup_time.value;
    drop_off_time = eDrop_off_time.value;
    let result = 0;
    if (pickup_time && drop_off_time) {
        data.pickup_time = pickup_time;
        data.drop_off_time = drop_off_time;
        const date1 = new Date(pickup_time);
        const date2 = new Date(drop_off_time);
        // Calculate the time difference in milliseconds
        const timeDifference = date2 - date1;

// Convert the time difference to days and hours
        const millisecondsPerDay = 24 * 60 * 60 * 1000;
        let daysDifference = Math.floor(timeDifference / millisecondsPerDay);
        const remainingMilliseconds = timeDifference % millisecondsPerDay;
        let hoursDifference = Math.floor(remainingMilliseconds / (60 * 60 * 1000));


        if (hoursDifference >= 12) {
            daysDifference++;
            hoursDifference = 0;
        }
        result = daysDifference * car.priceDays + hoursDifference * car.priceHours;
        data.rent_price = result;
        console.log(`Number of days: ${daysDifference} days`);
        console.log(`Number of hours: ${hoursDifference} hours`);
        eRent_price.innerText = formatCurrency(result);
        return result;

    }
    eRent_price.innerText = formatCurrency(result);
    return result;
}

ePickup_time.onchange = async () => {
    const isCarAvailable = await checkCarIfAvailable();
    if (!isCarAvailable) {
        toastr.warning("Car is not available this time");
        return;
    }
    await calculateTotal();
}
eDrop_off_time.onchange = async () => {
    const isCarAvailable = await checkCarIfAvailable();
    if (!isCarAvailable) {
        toastr.warning("Car is not available this time");
        return;
    }
    await calculateTotal();
}

async function checkCarIfAvailable() {
    const pickup = document.getElementById("pickup-time").value;
    const dropOff = document.getElementById("drop-off-time").value;
    const carId = car.id;
    const apiCheckAvailable = `http://localhost:8080/user/api/cars/check-available?id=${carId || ""}&pickupTime=${pickup || ""}&dropOffTime=${dropOff || ""}`
    const response = await fetch(apiCheckAvailable);
    return await response.json();
}

function sendRentalNotification() {
    const time = Date.now();

    var rentalMessage = customer.name + " rented a car at " + formatDate(time);
    stompClient.send("/app/message", {},
        JSON.stringify({
            'content': rentalMessage
        }));
}

async function createBill(data) {
    data = {
        customerName: customer.name,
        customerPhoneNumber: customer.numberPhone,
        customerEmail: customer.email,
        customerIdNumber: customer.idNumber,
        carId: car.id,
        licensePlate: car.licensePlate,
        pickupTime: data.pickup_time,
        expectedDropOffTime: data.drop_off_time,
        pickupLocation: data.pickup_location,
        dropOffLocation: data.drop_off_location,
        rentPrice: data.rent_price,
        deliveryFee: data.delivery_fee,
        totalPrice: data.total,
        deposit: data.deposit,
        tradeCode: document.getElementById("trade-code").value,
    }
    console.log(data);
    const res = await fetch('/user/api/cars/rent', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    if (res.ok) {
        toastr.success("Completed!")
        sendRentalNotification();
        $('#exampleModalLong').modal('hide');
    } else {
        toastr.error("Something went wrong!")
        // $('#exampleModalLong').modal('hide');
    }

}

eDoneBtn.onclick = async () => {
    if (!isEnteredTradeCode()) {
        toastr.error("Please enter trade code!")
        return;
    }
    await createBill(data);
}

function isEnteredTradeCode() {
    if (document.getElementById("trade-code").value) {
        return true;
    }
}

function showLogin() {
    // $("#exampleModal").show()
    $('#exampleModal').modal('show');
}

async function handleLogBtn() {
    let customer = await getCurrentCustom();
    console.log(customer);

    const loginBtn = document.getElementById("log-btn");
    const eRegisterLi = document.getElementById("menu-register");

    if (customer.email === null) {
        loginBtn.innerText = "Login";
        loginBtn.href = "javascript:void(0)"; // Remove the "href" attribute
        loginBtn.onclick = () => {
            showLogin();
        };
        eRegisterLi.innerHTML = `<a href="/register" class="nav-link">Register</a>`;
    } else {
        loginBtn.innerText = "Logout"; // Change the text for authenticated users
        loginBtn.href = "/logout"; // Update the "href" attribute for logout
        loginBtn.onclick = null; // Remove the click event handler
        eRegisterLi.innerHTML = "";
        if (customer.role === "ROLE_ADMIN") {
            eRegisterLi.innerHTML = `<a href="/home" class="nav-link">Dashboard</a>`;
        }
    }
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


async function handleReceiveMsg() {
    let customer = await getCurrentCustom();
    if (customer.email !== null) {
        // Create a WebSocket connection
        var socket = new SockJS('/ws');
        var stompClient = Stomp.over(socket);

        // Connect to the WebSocket server
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            var destination = `/user/${customer.email}/private`; // Replace 'your-username' with the user's username
            stompClient.subscribe(destination, function (message) {
                // Handle incoming messages from the admin here
                var notification = JSON.parse(message.body);
                toastr.info('From Admin', notification.content);
                // console.log('Received message from admin:', notification);
            });
        });
    }
}

window.onload = async () => {
    await handleLogBtn();
    renderDataInFormSearch();
    car = await getCurrentCar(urlAPICar);
    await renderRelatedCars();
    await calculateTotal()
    customer = await getCurrentCustom();
    await handleReceiveMsg()
}