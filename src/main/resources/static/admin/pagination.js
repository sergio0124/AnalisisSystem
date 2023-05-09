function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}

function increase_page() {
    let num = Number(document
        .getElementById("page").value);
    let url_string = window.location.href;
    let url = new URL(url_string);
    url.searchParams.append('page', num);

    let response = http_post(url.toString(), []);
    if (response[1] != 200) {
        alert(response[0]);
    }
    update_data(num + 1, JSON.parse(response[0]));
}


function update_data(page_number, plan_list) {
    let body = document.getElementById("user_table_body");
    body.innerHTML = '';

    document.getElementById("page").value = page_number;
    let before = document.getElementById("before");
    let after = document.getElementById("after");
    before.disabled = true;
    after.disabled = true;
    if (page_number > 1) {
        before.disabled = false;
    }
    if (plan_list.length === 10) {
        after.disabled = false;
    }

    plan_list.forEach(ur => {
        let day = new Date(Date.parse(ur.registrationDate));
        const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
        body.innerHTML += ` <tr>
                        <th scope="row">${ur.username}</th>
                        <td>${ur.fullname}</td>
                        <td>${ur.roles}</td>
                        <td>${day.toLocaleDateString('ru-RU', options)}</td>
                        <td>
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <button class="btn btn-outline-success" type="submit"
                                        onclick="delete_user(${ur.id}, this)">Удалить
                                </button>
                            </div>
                        </td>
                    </tr>`
    });
}


function decrease_page() {
    let num = Number(document
        .getElementById("page").value) - 2;
    let url_string = window.location.href;
    let url = new URL(url_string);
    url.searchParams.append('page', num);

    let response = http_post(url.toString(), []);
    if (response[1] != 200) {
        alert(response[0]);
    }
    update_data(num + 1, JSON.parse(response[0]));
}

function change_page(){
    let num = Number(document
        .getElementById("page").value) - 1;
    let url_string = window.location.href;
    let url = new URL(url_string);
    url.searchParams.append('page', num);

    let response = http_post(url.toString(), []);
    if (response[1] != 200) {
        alert(response[0]);
    }
    update_data(num + 1, JSON.parse(response[0]));
}
