window.onload=async function(){
    const labeltext=localStorage.getItem('labeltext');
    var response =await fetch("http://localhost:8080/market"+`?name=${labeltext}`,{method: 'GET'});
    const body = await response.json();
    var img=document.getElementById("img");
    var title=document.getElementById("titlename");
    var price=document.getElementById("price");
    var time=document.getElementById("time");
    var desc=document.getElementById("description");
    var src=body.photo+".jpg"
    console.log(src);
    img.src=src;
    title.innerText=body.name;
    price.innerText=body.price+"$";
    time.innerText=body.submittionTime;
    desc.innerText=body.description;
}
window.onclose=async function(){
    localStorage.removeItem('labeltext');
}