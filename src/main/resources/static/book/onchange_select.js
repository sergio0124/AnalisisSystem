function load_option() {
    let value = document.getElementById("load_select").value;
    let div = document.getElementById("books_div");
    let disciplineId = document.getElementById("disciplineId").innerHTML.replace("&nbsp;", '');
    if (value === "venec") {
        div.innerHTML = "";
        div.innerHTML += `
            <label>При нажатии кнопки будет выполнен поиск на портале venec.ulstu</label>
            <div>
            <button class="btn btn-primary me-md-2" type="button" onclick="load_venec(${disciplineId})">Поиск на портале</button>
            </div>
        `;
    } else if (value === "disc") {
        div.innerHTML = "";
        div.innerHTML += `
            <label>При нажатии кнопки будет выполнен поиск по схожим дисциплинам</label>
            <div>
            <button class="btn btn-primary me-md-2" type="button" onclick="load_similar(${disciplineId})">Поиск по дисциплине</button>
            </div>
        `;
    } else if (value === "pdf") {
        div.innerHTML = "";
        div.innerHTML += `
            <label>Выберете размещенный в сети файл и вставьте ссылку на него в выделенное поле</label>
            <div>
            <input type="text" id="url_search"/>
            <button class="btn btn-primary me-md-2" type="button" onclick="load_url(${disciplineId})">Поиск по дисциплине</button>
            </div>
        `;
    }
}