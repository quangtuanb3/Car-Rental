const eSearchCarForm = document.getElementById("search-car-form");
// const eConfirm_pickup_time = document.getElementById("confirm-pickup-time");
// const eConfirm_drop_off_time = document.getElementById("confirm-drop-off-time");
// const eConfirm_pickup_location = document.getElementById("confirm-pickup-location");
// const eConfirm_drop_off_location = document.getElementById("confirm-drop-off-location");
// const eConfirm_delivery_fee = document.getElementById("confirm-delivery-fee");
// const eConfirm_total = document.getElementById("confirm-total");
// const eConfirm_rent_price = document.getElementById("confirm-rent-price");
// const eConfirm_deposit = document.getElementById("confirm-deposit");

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
const urlAPICustomer = "/user/api/customers/1";

async function getCurrentCar(urlAPICar) {
    let res = await fetch(urlAPICar);
    return await res.json();
}

async function getCurrentCustom(urlAPICustomer) {
    let res = await fetch(urlAPICustomer);
    return await res.json();
}


toastr.options = {
    closeButton: true,
    progressBar: true,
    positionClass: 'toast-top-right',
    timeOut: 2000
};

eRelatedCars = document.getElementById("related-car-container");

async function calculateDeliveryFee() {
    let distancePickUp = await getDistancePickUp();
    let distanceDropOff = await getDistanceDropOff();
    let deliveryFee = 0;
    if (!Number.isNaN(Number.parseFloat(distancePickUp))) {
        deliveryFee += distancePickUp * car.priceDelivery.toFixed(1);
    }
    if (!Number.isNaN(Number.parseFloat(distanceDropOff))) {
        deliveryFee += +distanceDropOff * car.priceDelivery.toFixed(1);

    }
    eDeliveryFeeDisplay.innerText = deliveryFee + ' VND';
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

window.onload = async () => {
    car = await getCurrentCar(urlAPICar);
    await renderRelatedCars();
    await calculateTotal()
    customer = await getCurrentCustom(urlAPICustomer);
}

async function renderRelatedCars() {
    let url = `/user/api/cars/related-cars/${car.id}/${car.agency}/${car.seats}/${car.priceDays}`;
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

rentNowButton.addEventListener("click", function () {

    const currentTime = new Date();

    // Validate pickup time and drop-off time
    if (pickup_time <= currentTime) {
        toastr.error("Invalid Pickup time");
        return;
    }

    if (drop_off_time <= pickup_time) {
        toastr.error("Invalid drop-off time");
        return;
    }

    showConfirm(data)
    $('#exampleModalLong').modal('show');

});

function showConfirm(data) {
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
                    <p class="col-6">Name: <span ${car.name}</span></p>
                    <p class="col-6">Agency: <span> ${car.agency}</span></p>
                    <p class="col-6">Seats: <span >${car.seats}</span></p>
                    <p class="col-6">Transmission: <span>${car.transmission === '' ? "Defaul" : car.transmission}</span></p>
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
                    <p class="col-6 text-right pr-5" id="confirm-rent-price">${data.rent_price} VND</p>
                    <p class="col-6">Delivery Fee: </p>
                    <p class="col-6 text-right pr-5" id="confirm-delivery-fee">${data.delivery_fee} VND</p>
                    <p class="col-6 ">Total: </p>
                    <p class="col-6 text-right pr-5" id="confirm-total">${data.total} VND</p>
                    <p class="col-6 ">Deposit: </p>
                    <p class="col-6 text-right pr-5" id="confirm-deposit">${data.deposit} VND</p>
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
}

function formatCurrency(currency) {
    return currency + " VND"
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
        console.log(pickup_time)
        console.log(drop_off_time)
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
    await calculateTotal();
}
eDrop_off_time.onchange = async () => {
    await calculateTotal();
}

async function createBill(data) {
    data = {
        customerName: "username",
        customerPhoneNumber: "1234565",
        customerEmail: "email@gmail.com",
        customerIdNumber: "1234677",
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
    const res = await fetch('/user/api/cars', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    if (res.ok) {
        toastr.success("Completed!")
        $('#exampleModalLong').modal('hide');
    } else {
        toastr.error("Something went wrong!")
        // $('#exampleModalLong').modal('hide');
    }

}

eDoneBtn.onclick = async () => {
    if (checkTradeCode()) return;
    await createBill(data);
}

function checkTradeCode() {
    if (document.getElementById("trade-code").value) {
        return false;
    }
}