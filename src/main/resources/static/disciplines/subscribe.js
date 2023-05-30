function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.responseText, xmlHttp.status];
}

function subscribe_discipline(disciplineId){
    let elem = document.getElementById("subscribe_button");
    if (elem.innerHTML === "Подписаться"){
        let response = http_post("/subscribe?disciplineId=" + disciplineId, []);
        if (response[1] !== 200) {
            alert(response[0]);
        } else {
            elem.innerHTML = "Отписаться";
        }
    } else if (elem.innerHTML === "Отписаться") {
        let response = http_post("/unsubscribe?disciplineId=" + disciplineId, []);
        if (response[1] !== 200) {
            alert(response[0]);
        } else {
            elem.innerHTML = "Подписаться";
        }
    }
}