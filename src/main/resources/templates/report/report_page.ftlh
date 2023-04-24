<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="delete_user.js"></script>
    <script src="search.js"></script>
    <title>Информация о пользователе</title>
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
        <div class="d-flex gap-2">
            <h2>Собранные данные</h2>

        </div>

        <div style="margin-top: 40px;">
            <h3>Дисциплины без привязанных материалов:</h3>
            <div style="overflow-y: scroll; height:400px;">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">id</th>
                        <th scope="col">Наименование</th>
                        <th scope="col">Формат</th>
                        <th scope="col">Приоритет</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- вот это потом в цикл вставить -->
                    <#list nodisc as discipline>
                        <tr>
                            <th scope="row">${discipline.academicPlanDisciplineId}</th>
                            <td>${discipline.academicPlanDisciplineName}</td>
                            <td>${discipline.academicPlanForm}, ${discipline.academicPlanSemesterName}</td>
                            <td>${discipline.value}</td>
                            <td>
                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <button class="btn btn-primary me-md-2" type="button" onclick="go_to_books(${discipline.id})">Книгообеспеченность</button>
                                </div>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>

        </div>


        <div style="margin-top: 40px;">
            <h3>Дисциплины c привязанными материалами, но без оценки сопоставления:</h3>
            <div style="overflow-y: scroll; height:400px;">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">id</th>
                        <th scope="col">Наименование</th>
                        <th scope="col">Формат</th>
                        <th scope="col">Приоритет</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- вот это потом в цикл вставить -->
                    <#list normdisc as discipline>
                        <tr>
                            <th scope="row">${discipline.academicPlanDisciplineId}</th>
                            <td>${discipline.academicPlanDisciplineName}</td>
                            <td>${discipline.academicPlanForm}, ${discipline.academicPlanSemesterName}</td>
                            <td>${discipline.value}</td>
                            <td>
                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <button class="btn btn-primary me-md-2" type="button" onclick="go_to_books(${discipline.id})">Книгообеспеченность</button>
                                </div>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>

        </div>



        <div style="margin-top: 40px;">
            <h3>Дисциплины с заполненной обеспеченностью</h3>
            <div style="overflow-y: scroll; height:400px;">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">id</th>
                        <th scope="col">Наименование</th>
                        <th scope="col">Формат</th>
                        <th scope="col">Приоритет</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- вот это потом в цикл вставить -->
                    <#list okdisc as discipline>
                        <tr>
                            <th scope="row">${discipline.academicPlanDisciplineId}</th>
                            <td>${discipline.academicPlanDisciplineName}</td>
                            <td>${discipline.academicPlanForm}, ${discipline.academicPlanSemesterName}</td>
                            <td>${discipline.value}</td>
                            <td>
                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <button class="btn btn-primary me-md-2" type="button" onclick="go_to_books(${discipline.id})">Книгообеспеченность</button>
                                </div>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>

        </div>


        <div style="margin-top: 30px">
            <h3>Итоги</h3>
            <div style="margin-top: 10px">
                Незаполненных дисциплин: ${nodisc?size},
                Частично заполненных: ${normdisc?size},
                Заполненных: ${okdisc?size}
            </div>
            <div>

            </div>
        </div>


    </div>

</div>

</body>
</html>