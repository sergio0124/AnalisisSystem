<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="select_discipline.js"></script>

    <script src="book.js"></script>
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
            <h4>Книгообеспеченность дисциплины ${discipline.academicPlanDisciplineName}</h4>

            <h5>Данные о дисциплине:</h5>

            <div style="margin-top: 10px;">
                ${discipline.academicPlanDisciplineId}, ${discipline.academicPlanDisciplineName}
                , ${discipline.academicPlanForm}, ${discipline.academicPlanSemesterName}
            </div>

            <#list user.roles?filter(x -> x == "TEACHER") as x>
                <div style="margin-top: 10px">
                    <button class="btn btn-primary me-md-2" type="button" onclick="subscribe_discipline(${discipline.id})">
                        Подписаться
                    </button>
                </div>
            </#list>


        </div>


        <div style="justify-content: center; align-items: center; margin-top: 30px; width: 500px;">

            <div><h4>Литература:</h4></div>
            <div class="mb-3">
                <label class="form-label">Добавить материалы книгообеспеченности</label>
                <button class="btn btn-primary" onclick="load_book(${discipline.id})">Загрузить</button>
            </div>

        </div>


        <div style="margin-top: 30px;">
            <h4>Текущая обеспеченность учебно-методическими материалами</h4>

            <#list books as book>
            <div style="display: flex; border-radius: 10px; background-color: #80b4ff; margin-top: 20px">


                <div style="padding:5px; width: 50%">
                    <h3>Данные:</h3>
                    <div><b>Название:</b> ${book.name}</div>
                    <div><b>Аннотация:</b> ${book.anno}</div>
                    <div><b>Автор:</b> ${book.author}</div>
                    <div><b>Год:</b> ${book.year}</div>
                </div>

                <div style="padding:5px; width: 50%">
                    <h3>Сопоставление:</h3>
                    <div><b>Оценка:</b> ${book.name}</div>
                    <div><b>Тип:</b> ${book.anno}</div>
                    <div><b>Дата:</b> ${book.author}</div>
                    <div><b>Описание:</b> ${book.year}</div>
                </div>

            </div>


            <#list user.roles?filter(x -> x == "RULER") as x>
                <div style="display: flex">
                    <div style="align-items:center; padding:5px; width: 50%">
                        <button class="btn btn-primary" style="margin-left: auto; margin-right: auto;"
                                onclick="delete_book()">Удалить материал
                        </button>
                    </div>
                    <div style="align-items:center; padding:5px; width: 50%">
                        <button class="btn btn-primary" style="margin-left: auto; margin-right: auto;"
                                onclick="compare_book()">Оценка сопоставимости
                        </button>
                    </div>
                </div>
            </#list>


        </div>
        </#list>


    </div>

</div>

</body>
</html>