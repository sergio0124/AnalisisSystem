function update_user(id){
    let login = document.getElementById("login").value;
    let old = document.getElementById("old").value;
    let newpass = document.getElementById("new").value;
    let fullname = document.getElementById("fullname").value;

    let data = {
        "username": login,
        "password": newpass,
        "oldPassword": old,
        "fullname": fullname
    }
    let response = http_post("/user/update_user", JSON.stringify(data));
    if(response[1] == 200){
        history.back()
    } else {
        alert(response[0]);
    }
}

function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}