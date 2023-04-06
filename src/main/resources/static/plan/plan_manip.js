function load_plans() {
    let response = http_post("/plan/load", []);
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


function delete_plan(academicPlanId){
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