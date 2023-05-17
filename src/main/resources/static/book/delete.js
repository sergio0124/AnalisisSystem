
function delete_book(id){
    let response = http_post("/book/delete?bookId=" + id, []);
    if (response[1] != 200) {
        alert(response[0]);
    } else {
        document.location.reload();
    }
}

function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}