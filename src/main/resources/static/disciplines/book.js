

function load_book(id) {
    document.location.href = "/book/load?disciplineId=" + id;
}

function delete_book(el) {
    let bookId;
    let disciplineId;
    for (let i = 0; i < el.childNodes.length; i++) {
        if (el.childNodes[i].className === "bookId") {
            bookId = el.childNodes[i].innerHTML.replace("&nbsp;", '');
        }
        if (el.childNodes[i].className === "disciplineId") {
            disciplineId = el.childNodes[i].innerHTML.replace("&nbsp;", '');
        }
    }
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

function refactor_book(el) {
    let bookId;
    let disciplineId;
    let comparisonId;
    for (let i = 0; i < el.childNodes.length; i++) {
        if (el.childNodes[i].className === "bookId") {
            bookId = el.childNodes[i].innerHTML.replace("&nbsp;", '');
        }
        if (el.childNodes[i].className === "disciplineId") {
            disciplineId = el.childNodes[i].innerHTML.replace("&nbsp;", '');
        }
        if (el.childNodes[i].className === "comparisonId") {
            comparisonId = el.childNodes[i].innerHTML.replace("&nbsp;", '');
        }
    }
    document.location.href = "/book/update?bookId=" + bookId +
        "&disciplineId=" + disciplineId +
        "&comparisonId=" + comparisonId;
}