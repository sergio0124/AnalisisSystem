<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="load.js"></script>

    <title>Книгообеспеченность дисциплины</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
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
                    <#list user.roles?filter(x -> x == "ADMIN") as x>
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
                    </#list>
                    <#list user.roles?filter(x -> x == "TEACHER") as x>
                        <li class="nav-item">
                            <a class="nav-link" href="/disciplines/">Дисциплины</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/books/">Методические материалы</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/subscribes/">Подписки</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/notifications/">Уведомления</a>
                        </li>
                    </#list>
                    <#list user.roles?filter(x -> x == "RULER") as x>
                        <li class="nav-item">
                            <a class="nav-link" href="/disciplines/">Дисциплины</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/books/">Методические материалы</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/subscribes/">Подписки</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/report/">Отчеты</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="#">Сопоставления</a>
                        </li>
                    </#list>
                </ul>
            </div>
            <div class="d-flex">
                <ul class="navbar-nav">
                    <a class="nav-link active" aria-current="page"
                       href="/user/user_info">Пользователь: ${user.username}</a>
                    <a class="nav-link active" aria-current="page" href="/logout">Выйти</a>
                </ul>
            </div>
        </div>
    </nav>
    <div style="padding: 20px;">
        <div style="margin-top: 30px">
            <h4>Книга: "${book.name}"</h4>

            <h5>Данные о книге:</h5>


            <div class="mb-3">
                <label class="form-label">Название</label>
                <input type="text" class="form-control" id="name" value="${book.name}">
            </div>
            <div class="mb-3">
                <label class="form-label">Аннотация</label>
                <textarea type="text" style="height: 200px; overflow: scroll" class="form-control" id="annotation" >${book.annotation}></textarea>
            </div>
            <div class="mb-3">
                <label class="form-label">Дата публикации</label>
<#--                ${book.creationDate?date?string('yyyy')?number}-${book.creationDate?date?string('MM')?number}-${book.creationDate?date?string('dd')?number}-->
                <input type="date" class="form-control" id="creationDate" value="${book.creationDate?date}">
            </div>
            <div class="mb-3">
                <label class="form-label">Автор</label>
                <textarea type="text" style="height: 100px" class="form-control" id="author">${book.author}</textarea>
            </div>
            <div class="mb-3">
                <label class="form-label">Введение</label>
                <textarea type="text" style="height: 300px; overflow:scroll" class="form-control" id="introduction">${book.introduction}</textarea>
            </div>
            <label for="basic-url" class="form-label">Ссылка на издание</label>
            <div class="input-group">
                <span class="input-group-text" id="basic-addon3">https://example.com/users/</span>
                <input type="text" class="form-control" id="url" aria-describedby="basic-addon3 basic-addon4" value="${book.url}">
            </div>
            <div class="mb-3">
                <h5><a href="${book.url}">Ссылка на издание</a></h5>
            </div>


<#--            comparison-->
            <h5>Рассматривается связь с дисциплиной "${discipline.academicPlanDisciplineName}
                , ${discipline.academicPlanForm}, ${discipline.academicPlanSemesterName}"</h5>

            <div>При изменении информации о сопоставлении материала и дисциплины,
                сопоставления данного издания с другими дисциплинами не будут изменены
            </div>

            <div class="mb-3">
                <label class="form-label">Оценка</label>
                <input class="form-control" type="number" min="0" max="5" id="mark" value="${comparison.mark}">
            </div>
            <div class="mb-3">
                <label class="form-label">Описание</label>
                <textarea type="text" style="height: 300px; overflow:scroll" class="form-control" id="description">${comparison.description}</textarea>
            </div>

            <div hidden="true" id="bookId">${book.id}</div>
            <div hidden="true" id="comparisonId">${comparison.id}</div>
            <button type="submit" class="btn btn-primary" onclick="refactor()">Сохранить</button>


        </div>


    </div>


</div>


</body>
</html>