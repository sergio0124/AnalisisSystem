function select_discipline(){
    let planId = document.getElementById("plan_select").value;
    let location = "/disciplines/?academicPlanId=" + planId;
    document.location.href = location;
}


