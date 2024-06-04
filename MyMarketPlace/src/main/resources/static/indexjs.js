async function addnewitem() {
    window.open('new-item.html');
}

var page = 1;
window.onload = async function () {
    var quantity=document.getElementById('total items');
    document.getElementById('backbutton').classList.add('hidebutt');
    var url=`http://localhost:8080/market/${page}`;
    var response = await fetch(url, {method: "GET"});
    const body = await response.json();
    console.log(body);
    var url2=`http://localhost:8080/market/total`;
    var response2=await fetch(url2,{method: "GET"})
    const body2=await response2.json();
    quantity.innerText="total items: "+body2;
    fillGridWithItems(body.itemDtoList);
    console.log(page);
}

async function nextpage() {
    page++;
    var url=`http://localhost:8080/market/${page}`;
    var response = await fetch(url, {method: "GET"});
    const body = await response.json();
    console.log(body);
    if (body.quantity!=0) {
        console.log("modis");
        document.getElementById('pagenumber').innerText = page;
        document.getElementById('backbutton').classList.remove('hidebutt');
        fillGridWithItems(body.itemDtoList);
    }
    console.log(page);
}

async function backpage() {
    page--;
    if (page == 1) {
        document.getElementById('backbutton').classList.add('hidebutt');
    }
    document.getElementById('pagenumber').innerText = page;
    var url=`http://localhost:8080/market/${page}`;
    var response = await fetch(url, {method: "GET"});
    const body = await response.json();
    fillGridWithItems(body.itemDtoList);
    console.log(page);
}

document.addEventListener('DOMContentLoaded', (event) => {
    const gridItems = document.querySelectorAll('.grid-item');

    gridItems.forEach(item => {
        item.addEventListener('click', () => {
            const label = item.querySelector('label');
            handleClick(label.textContent);
        });
    });
});

async function fillGridWithItems(itemList) {
    const gridItems = document.querySelectorAll('.grid-item');
    for (let i = 0; i < 6; i++) {
        const gridItem = gridItems[i];
        if(itemList.length===0){
            gridItem.style.display = 'none';
        }else{
            const item = itemList[i];
            if (item) {
                const img = gridItem.querySelector('img');

                img.src =item.photo+".jpg";

                const nameLabel = gridItem.querySelector('label:not(.forprice)');
                nameLabel.textContent = item.name;

                const priceLabel = gridItem.querySelector('.forprice');
                priceLabel.textContent = item.price+"$";

            } else {
                gridItem.style.display = 'none';
            }
        }
    }
}

function handleClick(labeltext) {
    window.open("item.html");
    localStorage.setItem('labeltext', labeltext);
}