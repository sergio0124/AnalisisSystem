function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}


function delete_user(id, elem) {
    if (window.confirm("Вы уверены, что хотете удалить пользователя?")) {
        let data = {
            "id": id
        }
        let response = http_post("delete_user", JSON.stringify(data));
        if (response[1] != 200) {
            alert(response[0]);
        } else {
            location.reload();
        }
    }
}