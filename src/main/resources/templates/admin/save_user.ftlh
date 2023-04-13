<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Пользователь</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <img src="logo.png" alt="Logo" width="60" height="45" class="d-inline-block align-text-top">
        </a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/plan/">Планы</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Контингент студентов</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/admin/">Управление списком пользователей</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/disciplines/">Дисциплины</a>
                </li>
            </ul>
        </div>
        <div class="d-flex">
            <ul class="navbar-nav">
                <a class="nav-link active" aria-current="page" href="/user/user_info">Пользователь: ${user.username}</a>
                <a class="nav-link active" aria-current="page" href="/logout">Выйти</a>
            </ul>
        </div>
    </div>
</nav>


<div style="display: flex; justify-content: center; align-items: center; margin-top: 100px;">
    <#if ur??>
        <p class="h2">Изменение пользователя</p>
    <#else>
        <p class="h2">Создание пользователя</p>
    </#if>
</div>

<#if ur??>

    <div style="width: 500px; justify-content: center; align-items: center; margin-top: 100px; margin-left: auto; margin-right: auto;">

        <div class="mb-3">
            <label class="form-label">Логин</label>
            <input type="text" name="username" class="form-control" id="login" value="${ur.username}">
        </div>
        <div class="mb-3">
            <label class="form-label">Пароль</label>
            <input type="text" name="password" class="form-control" id="pass" value="${ur.password}">
        </div>
        <div class="mb-3">
            <label class="form-label">Полное имя</label>
            <input type="text" name="password" class="form-control" id="fullname" value="${ur.fullname}">
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Роль</label>
            <select class="form-select" id="role">
                <option value="TEACHER">Учитель</option>
                <option value="RULER">Руководитель</option>
                <option value="ADMIN">Админ</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary" onclick="refactor(${ur.id})">Сохранить</button>
    </div>

<#else>

    <div style="width: 500px; justify-content: center; align-items: center; margin-top: 100px; margin-left: auto; margin-right: auto;">

        <div class="mb-3">
            <label class="form-label">Логин</label>
            <input type="text" name="username" class="form-control" id="login">
        </div>
        <div class="mb-3">
            <label class="form-label">Пароль</label>
            <input type="password" name="password" class="form-control" id="pass">
        </div>
        <div class="mb-3">
            <label class="form-label">Полное имя</label>
            <input type="text" name="password" class="form-control" id="fullname">
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Роль</label>
            <select class="form-select" id="role">
                <option value="TEACHER">Учитель</option>
                <option value="RULER">Руководитель</option>
                <option value="ADMIN">Админ</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary" onclick="register()">Сохранить</button>
    </div>
</#if>


<script src="register.js"></script>
</body>
</html>