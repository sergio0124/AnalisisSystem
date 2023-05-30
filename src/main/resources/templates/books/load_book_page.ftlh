<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="load.js"></script>
    <script src="onchange_select.js"></script>

    <title>Добавление материалов книгообеспеченности</title>
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
            <div hidden="true" id="disciplineId">${discipline.id}</div>
            <h4>Добавление материалов книгообеспеченности дисциплины ${discipline.academicPlanDisciplineName}</h4>

            <h5>Данные о дисциплине:</h5>

            <div style="margin-top: 10px;">
                ${discipline.academicPlanDisciplineId}, ${discipline.academicPlanDisciplineName}
                , ${discipline.academicPlanForm}, ${discipline.academicPlanSemesterName}
            </div>

            <h5>Данные о плане:</h5>

            <div>${plan.academicPlanQualificationName},
                ${plan.academicPlanSpecialtyProfile}, ${plan.academicPlanStudyForm}
                , ${plan.academicPlanComment}</div>
        </div>

        <div hidden="hidden" id="disciplineId">${discipline.id}</div>

        <div style="margin-top: 30px;">
            <h4>Поиск материалов (выберете опцию):</h4>
            <select class="form-select" aria-label="Default select example" id="load_select" onchange="load_option()">
                <option value="venec" selected="selected">Поиск по venec.ulstu</option>
                <option value="disc">Поиск материалов других
                    дисциплин
                </option>
                <option value="pdf">Загрузка материалов по
                    ссылке на pdf файл
                </option>
            </select>
        </div>

        <div style="margin-top: 30px;" id="books_div">
            <label>При нажатии кнопки будет выполнен поиск на портале venec.ulstu</label>
            <div>
                <button class="btn btn-primary me-md-2" type="button" onclick="load_venec(${discipline.id})">Поиск на портале</button>
            </div>
        </div>

    </div>

</div>

</body>
</html>