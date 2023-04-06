<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Вход</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
          integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>

</head>
<body>


<div style="display: flex; justify-content: center; align-items: center; margin-top: 100px;">
    <p class="h2">Вход</p>
</div>

<div style="display: flex; justify-content: center; align-items: center; margin-top: 100px;">

    <form style="width: 500px;" action="/authenticateUser" method="post">
        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Логин</label>
            <input type="text" name="username" class="form-control" id="exampleInputEmail1"
                   aria-describedby="emailHelp">
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Пароль</label>
            <input type="password" name="password" class="form-control" id="exampleInputPassword2">
        </div>
        <button type="submit" class="btn btn-primary">Войти</button>
    </form>
</div>


</body>
</html>