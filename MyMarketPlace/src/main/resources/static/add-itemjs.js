async function submit(){
    var title=document.getElementById('titlename').value;
    var price=document.getElementById('price').value;
    var photo=document.getElementById('fileinput').files[0];
    var description=document.getElementById('desc').value;
    if(price.includes('$')){
        var warning=document.getElementById('warning');
        document.getElementById('price').value="";
        warning.innerText="!!1your price cannot contain `$`!!!";
        setTimeout(() => {
            warning.value="";
        }, 3000);
    }else{
        if(isNumeric(price)){
            var response =await fetch("http://localhost:8080/market/check"+`?name=${title}`,{method: 'GET'});

            var body=await response.json();
            console.log(body);
            if (body) {
                var warning=document.getElementById('warning');
                document.getElementById('titlename').value="";
                warning.innerText="!!!title is taken!!!";
                setTimeout(() => {
                    warning.value="";
                }, 3000);
            } else {
                var itemDto = {
                    name: title,
                    price: price,
                    description: description,
                    submittionTime: await gettime()
                };

                var response= await fetch('http://localhost:8080/market', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(itemDto)
                })

                var formData = new FormData();
                formData.append('photo', photo);
                formData.append('name', title);

                var response = await fetch('http://localhost:8080/photo', {
                    method: "POST",
                    body: formData
                });

            }
        }else{
            var warning=document.getElementById('warning');
            document.getElementById('price').value="";
            warning.innerText="!!your price has to include only numbers!!!";
            setTimeout(() => {
                warning.value="";
            }, 3000);
        }
    }
    title.value="";
    price.value="";
    description.value="";
}
async function gettime(){
    var currentDate = new Date();

    var year = currentDate.getFullYear();
    var month = ("0" + (currentDate.getMonth() + 1)).slice(-2);
    var day = ("0" + currentDate.getDate()).slice(-2);
    var hours = ("0" + currentDate.getHours()).slice(-2);
    var minutes = ("0" + currentDate.getMinutes()).slice(-2);

    var formattedDateTime = year + "-" + month + "-" + day + " " + hours + ":" + minutes;
    return formattedDateTime;
}
function isNumeric(input) {
    const regex = /^\d+$/;
    return regex.test(input);
}