function search(){
    let search = document.getElementById("search").value;
    window.location.href = '/admin/?search=' + search;
}