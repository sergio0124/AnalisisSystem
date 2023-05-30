<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="refactor.js"></script>

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
                            <a class="nav-link" href="/admin/">Управление списком пользователей</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/disciplines/">Дисциплины</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/report/">Отчеты</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/subscribes/">Подписки</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/subscribes/events">Уведомления</a>
                        </li>
                    </#list>
                    <#list user.roles?filter(x -> x == "TEACHER") as x>
                        <li class="nav-item">
                            <a class="nav-link" href="/disciplines/">Дисциплины</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/subscribes/">Подписки</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/report/">Отчеты</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/subscribes/events">Уведомления</a>
                        </li>
                    </#list>
                    <#list user.roles?filter(x -> x == "RULER") as x>
                        <li class="nav-item">
                            <a class="nav-link" href="/disciplines/">Дисциплины</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/subscribes/">Подписки</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="/subscribes/events">Уведомления</a>
                        </li>
                    </#list>
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
    <div style="padding: 20px;">
        <div style="margin-top: 30px">
            <h4>Книга: "${book.name}"</h4>

            <h5>Данные о книге:</h5>


            <div class="mb-3">
                <label class="form-label">Название:</label>
                <div>
                    ${book.name}
                </div>
                <div class="mb-3">
                    <label class="form-label">Аннотация:</label>
                    <div>${book.annotation}></div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Дата публикации</label>
                    <div>${book.creationDate?date?iso_utc}"</div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Количество страниц:</label>
                    <div style="">${book.pages} c.</div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Автор:</label>
                    <div>${book.author}</div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Введение:</label>
                    <div>${book.introduction}</div>
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
                    <div>${comparison.mark}</div>
                </div>
                <div class="mb-3">
                    <label class="form-label">Описание</label>
                    <div>${comparison.description}</div>
                </div>


            </div>


        </div>


    </div>


</body>
</html>