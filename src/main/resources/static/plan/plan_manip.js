function load_plans() {
    let start = document.getElementById("start-year").value;
    let end = document.getElementById("end-year").value;

    if (start.length < 1 ||
        end.length < 1 ||
        start < 2017 ||
        start > 2027 ||
        end < 2017 ||
        end > 2027) {
        alert("Неверный ввод дат-ограничителей");
        return;
    }


    let response = http_post("/plan/load?start=" + start + "&end=" + end, []);
    if (response[1] != 200) {
        alert(response[0]);
    } else {
        location.reload()
    }
}

function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}


function delete_plan(academicPlanId) {
    let id_len = 9;
    let more = id_len - academicPlanId.toString().length;
    let full_id = Array(more).fill(0).join("") + String(academicPlanId);

    let data = {
        "academicPlanId": full_id
    }
    let response = http_post("/plan/delete", JSON.stringify(data));
    if (response[1] != 200) {
        alert(response[0]);
    } else {
        location.reload()
    }
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
    let body = document.getElementById("plan_table_body");
    body.innerHTML = '';

    document.getElementById("page").value = page_number;
    let before = document.getElementById("before");
    let after = document.getElementById("after");
    before.disabled = true;
    after.disabled = true;
    if (page_number > 1) {
        before.disabled = false;
    }
    if (plan_list.length === 20) {
        after.disabled = false;
    }

    plan_list.forEach(plan => {
        body.innerHTML += `<tr>
                    <th scope="row">${plan.academicPlanId}</th>
                    <td>${plan.academicPlanQualificationName}</td>
                    <td>${plan.academicPlanSpecialtyProfile}, ${plan.academicPlanSpecialtyName}, ${plan.academicPlanStudyForm}, ${plan.academicPlanComment}</td>
                    <td>
                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <button class="btn btn-primary me-md-2" type="button" onclick="delete_plan(${plan.academicPlanId})">Удалить</button>
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


function search(){
    let search = document.getElementById("search").value;
    window.location.href = "/plan/?search=" + search;

}