function select_discipline(){
    let planId = document.getElementById("plan_select").value;
    document.location.href = "/disciplines/?academicPlanId=" + planId;
}

function go_to_books(id){
    document.location.href = "/disciplines/books?disciplineId=" + id;
}


function search(){
    let search = document.getElementById("search").value;
    let url_string = window.location.href;
    let url = new URL(url_string);
    url.searchParams.set('search', search);

    window.location.href = url.toString();

}

