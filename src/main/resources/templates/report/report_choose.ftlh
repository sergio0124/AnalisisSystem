<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="report.js"></script>
    <title>Список дисциплин</title>
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
        <form style="width: 500px;">
            <h4>Составление отчета</h4>
            <div>
                <label class="form-label">Выберите поле для составления отчета:</label>
                <select class="form-select" aria-label="Default select example" id="mode_select"
                        onchange="select_mode()">
                    <option selected="selected" value="plan">По плану</option>
                    <option value="kaf">По кафедре</option>
                    <option value="fak">По факультету</option>
                    <option value="pro">По профилю</option>
                </select>

                <label class="form-label">Выберете значение для отчета:</label>
                <select class="form-select" aria-label="Default select example" id="special_mode_select">
                    <#list plans as plan>
                        <option value='${plan.academicPlanId}'>${plan.academicPlanQualificationName},
                            ${plan.academicPlanSpecialtyProfile}, ${plan.academicPlanStudyForm}
                            , ${plan.academicPlanComment}</option>
                    </#list>
                </select>

                <label class="form-label">Выберите формат создаваемого отчета:</label>
                <select class="form-select" aria-label="Default select example" id="form_select">
                    <option value="word">Создать и загрузить документ</option>
                    <option selected="selected" value="web">Отобразить отчет в браузере</option>
                </select>

                <div>
                    <button class="btn btn-primary me-md-2" type="button" style="margin-top: 30px" onclick="to_report()">
                        Создать отчет
                    </button>
                </div>
            </div>
        </form>


    </div>

</div>

</body>
</html>