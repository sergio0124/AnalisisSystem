<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Log in</title>
    <link href="/static/css/form.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>

<#if RequestParameters.error??>
    <div class="form">Неверный логин или пароль</div>
<#elseif RequestParameters.logout??>
    <div class="form">Вы вышли из своего аккаунта</div>
<#elseif status??>
    <p>
        ${status}
    </p>
<#elseif message??>
    <p>
        ${message}
    </p>
</#if>

</div>
<div class="form"><b>Вход</b></div>

<form action="/authenticateUser" method="post">
    <div class="form"><label > Логин : <input type="text" name="username"/> </label></div>
    <div class="form"><label > Пароль : <input type="password" name="password"/> </label></div>
    <div><input class="form button" type="submit" value="Войти"/></div>
</form>

<p></p>
<div>
<button class="form button">Регистрация </button>
<button class="form button">Восстановление пароля </button>
</div>
</body>
</html>