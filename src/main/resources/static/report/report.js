function to_report(){
    let mode = document.getElementById("mode_select").value;
    let special = document.getElementById("special_mode_select").value;
    let form = document.getElementById("form_select").value;
    if (form === "web") {
        document.location.href = "/report/create?mode=" + mode + "&special=" + special ;
    } else {
        document.location.href = "/report/create/word?mode=" + mode + "&special=" + special ;
    }
}

function select_mode(){
    let response;
    let mode = document.getElementById("mode_select").value;
    if (mode === "plan") {
        response = http_post("/report/get_plans", []);
    } else if (mode === "kaf") {
        response = http_post("/report/get_kafs", []);
    } else if (mode === "fak") {
        response = http_post("/report/get_faks", []);
    } else if (mode === "pro") {
        response = http_post("/report/get_pros", []);
    }
    if (response[1] !== 200) {
        alert(response[0]);
    } else {
        update_special_mode(JSON.parse(response[0]));
    }
}

function update_special_mode(data){
    let special_select = document.getElementById("special_mode_select");
    special_select.innerHTML = "";
    data.forEach(kort => {
        special_select.innerHTML += `
            <option value="${kort.id}">${kort.value}</option>
            `
    });
}

function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}