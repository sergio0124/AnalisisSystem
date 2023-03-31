function http_post(theUrl) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send();
    return [xmlHttp.responseText, xmlHttp.status];
}

function delete_admin() {
    let response = http_post(window.location.href);
    if (response[1] == 200) {
        window.history.back();
    } else {
        alert(response[0]);
    }
}

