let bookId = document.getElementById("bookId")
    .innerHTML
    .replace("&nbsp;", "");
let disciplineId = document.getElementById("disciplineId")
    .innerHTML
    .replace("&nbsp;", "");
let comparisonId = document.getElementById("comparisonId")
    .innerHTML
    .replace("&nbsp;", "");

function load_book(id) {
    document.location.href = "/book/load?disciplineId=" + id;
}

function delete_book() {
    let response = http_post(
        "/book/delete?disciplineId=" + disciplineId + "&bookId=" + bookId,
        []
    );
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

function refactor_book() {
    document.location.href = "/book/update?bookId=" + bookId +
        "&disciplineId=" + disciplineId +
        "&comparisonId=" + comparisonId;
}

function check_book(){

}