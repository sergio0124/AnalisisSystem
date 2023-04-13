<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="select_discipline.js"></script>
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
            <h2>Книгообеспеченность дисциплины ${discipline.academicPlanDisciplineName}</h2>

            <h3>Данные о дисциплине:</h3>

            <div>
                ${discipline.academicPlanDisciplineId}, ${discipline.academicPlanDisciplineName}
                , ${discipline.academicPlanForm}, ${discipline.academicPlanSemesterName}
            </div>

            <div>
                <button class="btn btn-primary me-md-2" type="button" onclick="subscribe_discipline(${discipline.id})">
                    Подписаться
                </button>
            </div>

        </div>


        <div style="justify-content: center; align-items: center; margin-top: 30px">

            <div><h2>Литература:</h2></div>
            <div class="mb-3">
                <label class="form-label">Вставьте название материала, загрузка которого должна произойти (Размещенный
                    файл в формате .pdf)</label>
                <input type="text" class="form-control" id="source" aria-describedby="emailHelp">
            </div>
            <div>
                <button class="btn btn-primary" onclick="load_book()">Загрузить</button>
            </div>

        </div>


        <div>
            <h2>Текущая книгообеспеченность</h2>
            <div style="display: flex; border-radius: 10px; background-color: #80b4ff">

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

            <div style="display: flex">
                <div style="align-items:center; padding:5px; width: 50%">
                    <button class="btn btn-primary" style="margin-left: auto; margin-right: auto;" onclick="delete_book()">Удалить материал</button>
                </div>
                <div style="align-items:center; padding:5px; width: 50%">
                    <button class="btn btn-primary" style="margin-left: auto; margin-right: auto;" onclick="compare_book()">Оценка сопоставимости</button>
                </div>
            </div>

        </div>


    </div>

</div>

</body>
</html>