function load_venec(id) {
    let response = http_post("/book/load/venec?disciplineId=" + id, []);
    if (response[1] != 200) {
        alert(response[0]);
    }
    add_books(JSON.parse(response[0]));
}

function add_books(book_list) {
    let books_div = document.getElementById("books_div");
    books_div.innerHTML = '';

    book_list.forEach(ur => {
        books_div.innerHTML += `
            <div style="display: flex; border-radius: 10px; background-color: #80b4ff; margin-top: 20px">
                <div style="padding:5px">
                    <h3>Данные о книге:</h3>
                    <div class="data" >${ur.name}</div>
                    <a class="url" href="${ur.url}"><h5>Ссылка на ресурс</h5></a>
                </div>
                <button class="btn btn-primary me-md-2" onclick="save_book(this.parentNode.childNodes[1])">Добавить в книгообеспеченность</button>
            </div>`
    });
}

function save_book(el) {
    let name;
    let url;
    for (var i = 0; i < el.childNodes.length; i++) {
        if (el.childNodes[i].className === "data") {
            name = el.childNodes[i].innerHTML;
        }
        if (el.childNodes[i].className === "url") {
            url = el.childNodes[i].href;
        }
    }
    let disciplineId = document.getElementById("disciplineId").textContent;
    let data = {
        "name": name,
        "url": url
    }
    let response = http_post("/book/load/save_loaded?disciplineId=" + disciplineId, JSON.stringify(data));
    if (response[1] == 200) {
        history.back()
    } else {
        alert(response[0]);
    }
}

function load_similar(id) {

}

function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}



function load_url(){
    let url = document.getElementById("load_url").value;
    let response = http_post("/book/load/save_loaded?disciplineId=" + disciplineId, JSON.stringify(data));
    if (response[1] == 200) {
        history.back()
    } else {
        alert(response[0]);
    }
}