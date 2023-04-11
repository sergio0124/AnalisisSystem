<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Информация о пользователе</title>
    <script src="update_user.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<div>

    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img src="logo.png" alt="Logo" width="60" height="45" class="d-inline-block align-text-top">
            </a>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/plan/">Планы</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/disciplines/">Дисциплины</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Контингент студентов</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Формирование отчетов</a>
                    </li>
                </ul>
            </div>
            <div class="d-flex">
                <ul class="navbar-nav">
                    <a class="nav-link active" aria-current="page" href="#">Пользователь: ${user.username}</a>
                    <a class="nav-link" aria-current="page" href="/logout">Выйти</a>
                </ul>
            </div>
        </div>
    </nav>

    <div style="display: flex; justify-content: center; align-items: center; margin-top: 100px;">
        <div style="width: 500px;">
            <div class="mb-3">
                <label for="exampleInputEmail1" class="form-label">Логин</label>
                <input type="email" class="form-control" id="login" aria-describedby="emailHelp" value="${user.username}">
            </div>
            <div class="mb-3">
                <label for="exampleInputEmail1" class="form-label">Полное имя</label>
                <input type="email" class="form-control" id="fullname" aria-describedby="emailHelp" value="${user.fullname}">
            </div>
            <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label">Старый пароль</label>
                <input type="password" class="form-control" id="old">
            </div>
            <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label">Новый пароль</label>
                <input type="password" class="form-control" id="new">
            </div>
            <button class="btn btn-primary" onclick="update_user(${user.id})">Сохранить</button>
        </div>
    </div>
</div>

</body>
</html>