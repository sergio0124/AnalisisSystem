<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Информация о пользователе</title>
    <script src="/update_user.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<div>


    <label>Имя пользователя:</label>
    <div>${user.username}</div>

    <div>
        <div>Изменить логин аккаунта:</div>
        <label>Введите новый логин
            <input id="ll" type="text" value="${user.username}">
        </label>
        <button onclick="update_username()">Отправить изменения логина</button>
    </div>


    <div>
        <div>Изменить пароль аккаунта:</div>
        <label>Введите новый пароль
            <input id="pnp" type="password">
        </label>
        <button onclick="update_password()">Отправить изменения пароля</button>
    </div>


    <label>Дата регистрации</label>
    <div>${user.registrationDate}</div>
</div>

</body>
</html>