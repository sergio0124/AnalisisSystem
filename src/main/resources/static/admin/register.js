submit = document.getElementById("registration_submit");
username = document.getElementById("login");
password = document.getElementById("pass");
select = document.getElementById("role");
fullname = document.getElementById("fullname");


function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}


function register() {

    let type = select.value;
    let pass = password.value;
    let name = username.value;
    let full = fullname.value;

    let data = {
        "username": name,
        "password": pass,
        "roles": [type],
        "fullname": full
    }
    let response = http_post("save_user", JSON.stringify(data));
    if(response[1] == 200){
        window.location.href = "/"
    } else {
        alert(response[0]);
    }
}


function refactor(id) {

    let type = select.value;
    let pass = password.value;
    let name = username.value;
    let full = fullname.value;

    let data = {
        "id": id,
        "username": name,
        "password": pass,
        "roles": [type],
        "fullname": full
    }
    let response = http_post("save_user", JSON.stringify(data));
    if(response[1] == 200){
        window.location.href = "/"
    } else {
        alert(response[0]);
    }
}