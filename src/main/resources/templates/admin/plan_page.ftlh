<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Информация о пользователе</title>
    <script src="plan_manip.js"></script>
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
    <div style="margin-top: 20px; margin-left: 20px;">
        <h2>Учебные планы</h2>

        <div class="d-flex" role="search" style="width: 500px; margin-top: 20px;">
            <input class="form-control me-2" type="number" min="2017" max="2027" placeholder="Начальный год" aria-label="Search" id="start-year">
            <input class="form-control me-2" type="number" min="2017" max="2027" placeholder="Конечный год" aria-label="Search" id="end-year">
            <button class="btn btn-outline-success" onclick="load_plans()">Загрузка плана</button>
        </div>


        <div class="d-flex" role="search" style="margin-top: 20px; width: 600px;">
            <label>Поиск планов по профилю специальности:</label>
            <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" id="search"
                <#if RequestParameters.search??>
                    value="${RequestParameters.search}"
                </#if>
            >
            <button class="btn btn-primary" id="search" onclick="search()">Поиск</button>
        </div>

        <table class="table table-hover" id="plan_table">
            <thead>
            <tr>
                <th scope="col">План</th>
                <th scope="col">Специалитет</th>
                <th scope="col">Форма</th>
                <th scope="col">Действие</th>
            </tr>
            </thead>
            <tbody id="plan_table_body">
            <!-- вот это потом в цикл вставить -->
            <#list plans as plan>
                <tr>
                    <th scope="row">${plan.academicPlanId}</th>
                    <td>${plan.academicPlanQualificationName}</td>
                    <td>${plan.academicPlanSpecialtyProfile}, ${plan.academicPlanStudyForm}, ${plan.academicPlanComment}</td>
                    <td>
                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <button class="btn btn-primary me-md-2" type="button" onclick="delete_plan(${plan.academicPlanId?string})">Удалить</button>
                        </div>
                    </td>
                </tr>
            </#list>


        </table>

        <div style="margin-top: 10px; display: flex; justify-content: center; align-items: center;">
            <button style="margin-left: 5px; margin-right: 5px;" class="btn btn-primary" id="before" onclick="decrease_page()" disabled><</button>
            <input style="margin-left: 5px; margin-right: 5px; width: 50px;" class="form-control" id="page" value="1" onchange="change_page()">
            <button style="margin-left: 5px; margin-right: 5px;" class="btn btn-primary" id="after" onclick="increase_page()"
            <#if plans?size < 20>
                disabled
            </#if>
            >></button>
        </div>
    </div>

</div>

</body>
</html>