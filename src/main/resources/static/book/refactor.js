
function refactor(){
    let name = document.getElementById("name").value;
    let annotation = document.getElementById("annotation").value;
    let creationDate = Date.parse(document.getElementById("creationDate").value);
    let author = document.getElementById("author").value;
    let introduction = document.getElementById("introduction").value;
    let url = document.getElementById("url").value;
    let mark = document.getElementById("mark").value;
    let bookId = document.getElementById("bookId").innerHTML.replace("&nbsp;", '');
    let comparisonId = document.getElementById("comparisonId").innerHTML.replace("&nbsp;", '');
    let disciplineId = document.getElementById("disciplineId").innerHTML.replace("&nbsp;", '');
    let description = document.getElementById("description").value;

    if (parseInt(document.getElementById("pages").value) < 0){
        alert("Неверно указаны страницы");
        return;
    }
    let pages = parseInt(document.getElementById("pages").value);

    if (mark > 5 || mark < 0 ){
        alert("Оценка должна быть в диапазоне от 0 до 5");
        return;
    }

    let data = {
        "id": bookId,
        "name": name,
        "pages": pages,
        "annotation": annotation,
        "creationDate": creationDate,
        "author": author,
        "introduction": introduction,
        "url": url,
        "comparisons": [{
            "type": "MANUAL",
            "date": Date.now(),
            "mark": mark,
            "description": description,
            "id": comparisonId,
            "disciplineId": disciplineId,
        }]
    }
    let response = http_post("/book/update", JSON.stringify(data));
    if(response[1] == 200){
        history.back();
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