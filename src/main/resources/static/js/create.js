function create() {
    let id = null;
    if (document.getElementById("admin_id") != null){
        id = document.getElementById("admin_id").textContent;
    }
    let name = document.getElementById("username").value;
    if(name.length < 3){
        return;
    }
    let password = document.getElementById("password").value;
    let data = {
        "id": id,
        "username": name,
        "password": password
    }
    let response = http_post(window.location.href, JSON.stringify(data));
    if(response[1] == 200){
        window.location.href = "/"
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