<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script src="pagination.js"></script>
    <script src="select_discipline.js"></script>
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
        <h2>Список дисциплин</h2>
        <form style="width: 500px;">
            <div>
                <label class="form-label">Выберите план</label>
                <select class="form-select" aria-label="Default select example" onchange="select_discipline()"
                        id="plan_select">

                    <#list plans as plan>
                        <option value="${plan.academicPlanId}"
                                <#if RequestParameters.academicPlanId??>
                                    <#if RequestParameters.academicPlanId == plan.academicPlanId>
                                        selected
                                    </#if>
                                </#if>
                        >${plan.academicPlanQualificationName},
                            ${plan.academicPlanSpecialtyProfile}, ${plan.academicPlanStudyForm}
                            , ${plan.academicPlanComment}</option>
                    </#list>

                </select>
            </div>
        </form>


        <div class="d-flex" role="search" style="margin-top: 20px; width: 600px;">
            <label>Поиск дисциплин по наименованию дисциплины:</label>
            <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" id="search"
                    <#if RequestParameters.search??>
                        value="${RequestParameters.search}"
                    </#if>
            >
            <button class="btn btn-primary" id="search" onclick="search()">Поиск</button>
        </div>


        <table class="table table-hover" style="margin-top: 20px;">
            <thead>
            <tr>
                <th scope="col">id</th>
                <th scope="col">Наименование</th>
                <th scope="col">Формат</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody id="discipline_table_body">
            <!-- вот это потом в цикл вставить -->

            <#list disciplines as discipline>
                <tr>
                    <th scope="row">${discipline.academicPlanDisciplineId}</th>
                    <td>${discipline.academicPlanDisciplineName}</td>
                    <td>${discipline.academicPlanForm}, ${discipline.academicPlanSemesterName}</td>
                    <td>
                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <button class="btn btn-primary me-md-2" type="button"
                                    onclick="go_to_books(${discipline.id})">Книгообеспеченность
                            </button>
                        </div>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>


        <div style="margin-top: 10px; display: flex; justify-content: center; align-items: center;">
            <button style="margin-left: 5px; margin-right: 5px;" class="btn btn-primary" id="before"
                    onclick="decrease_page()" disabled><
            </button>
            <input style="margin-left: 5px; margin-right: 5px; width: 50px;" class="form-control" id="page" value="1"
                   onchange="change_page()">
            <button style="margin-left: 5px; margin-right: 5px;" class="btn btn-primary" id="after"
                    onclick="increase_page()"
                    <#if disciplines?size < 20>
                        disabled
                    </#if>
            >>
            </button>
        </div>


    </div>
</div>

</body>
</html>