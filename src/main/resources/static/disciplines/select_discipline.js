function select_discipline(){
    let planId = document.getElementById("plan_select").value;
    document.location.href = "/disciplines/?academicPlanId=" + planId;
}

function go_to_books(id){
    document.location.href = "/disciplines/books?disciplineId=" + id;
}

function load_book(){
    alert("Функция пока не готова");
}

