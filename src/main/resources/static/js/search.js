function search(){
    let search = document.getElementById("search").value;
    window.location.href = '/main_admin/?search=' + search;
}