function to_report(id){
    let planId = document.getElementById("plan_select").value;
    document.location.href = "/report/create?academicPlanId=" + planId;
}