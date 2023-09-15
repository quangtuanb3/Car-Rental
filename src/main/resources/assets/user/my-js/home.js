// const eSearchCarBtn = document.getElementById("search-car-btn");
// const eSearchCarForm = document.getElementById("search-car-form");
// const eShowDetail = document.getElementById("show-detail-btn");
const eBestCarContainer = document.getElementById("best-car-container");


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
                                <h2 class="mb-0"><a href="#">${car.name}</a></h2>
                                <div class="d-flex mb-3">
                                    <span class="cat">${car.agency}</span>
                                    <p class="price ml-auto">$ ${car.priceDays} <span>/day</span></p>
                                </div>
                                <p class="d-flex mb-0 d-block"><a href="#" class="btn btn-primary py-2 mr-1">Book
                                    now</a> <a href="#" class="btn btn-secondary py-2 ml-1">Details</a></p>
                            </div>
                        </div>
                    </div>
            `
    })
    eBestCarContainer.innerHTML = divItems;
}

const carousel = function() {
    $('.carousel-car').owlCarousel({
        center: true,
        loop: true,
        autoplay: true,
        items:4,
        margin: 30,
        stagePadding: 0,
        nav: false,
        navText: ['<span class="ion-ios-arrow-back">', '<span class="ion-ios-arrow-forward">'],
        responsive:{
            0:{
                items: 1
            },
            600:{
                items: 2
            },
            1000:{
                items: 3
            }
        }
    });
    $('.carousel-testimony').owlCarousel({
        center: true,
        loop: true,
        items:1,
        margin: 30,
        stagePadding: 0,
        nav: false,
        navText: ['<span class="ion-ios-arrow-back">', '<span class="ion-ios-arrow-forward">'],
        responsive:{
            0:{
                items: 1
            },
            600:{
                items: 2
            },
            1000:{
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


}


