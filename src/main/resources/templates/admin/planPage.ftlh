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
    <div style="margin-top: 20px;">
        <h2>Учебные планы</h2>

        <div class="d-flex" role="search" style="width: 500px; margin-top: 20px;">
            <input class="form-control me-2" type="search" placeholder="Год до" aria-label="Search">
            <input class="form-control me-2" type="search" placeholder="год после" aria-label="Search">
            <button class="btn btn-outline-success" onclick="load_plans()">Загрузка плана</button>
        </div>

        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">План</th>
                <th scope="col">Специалитет</th>
                <th scope="col">Форма</th>
                <th scope="col">Действие</th>
            </tr>
            </thead>
            <tbody>
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


            <tr>
                <th scope="row">1</th>
                <td>Информатика и вычислительная техника</td>
                <td>Бакалавриат, Очная</td>
                <td>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                        <button class="btn btn-primary me-md-2" type="button">Удалить</button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

</div>

</body>
</html>