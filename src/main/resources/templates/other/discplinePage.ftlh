<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <script src="select_discipline.js"></script>
    <title>Информация о пользователе</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
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
                <a class="nav-link active" aria-current="page" href="/plan/">Планы</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Дисциплины</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Контингент студентов</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Формирование отчетов</a>
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
    <div style="padding: 20px;">
        <h2>Список дисциплин</h2>
        <form style="width: 500px;">
            <div>
            <label class="form-label">Выберите план</label>
            <select class="form-select" aria-label="Default select example" onchange="select_discipline()" id="plan_select">

                <#list plans as plan>
                    <option value="${plan.academicPlanId}">${plan.academicPlanQualificationName},
                        ${plan.academicPlanSpecialtyProfile}, ${plan.academicPlanStudyForm}, ${plan.academicPlanComment}</option>
                </#list>

              </select>
            </div>
        </form>
        
        
        <table class="table table-hover" style="margin-top: 20px;" >
            <thead>
              <tr>
                <th scope="col">id</th>
                <th scope="col">Наименование</th>
                <th scope="col">Формат</th>
                <th scope="col"></th>
              </tr>
            </thead>
            <tbody>
                <!-- вот это потом в цикл вставить -->

                <#list disciplines as discipline>
                    <tr>
                        <th scope="row">${discipline.academicPlanDisciplineId}</th>
                        <td>${discipline.academicPlanDisciplineName}</td>
                        <td>${discipline.academicPlanForm}, ${discipline.academicPlanSemesterName}</td>
                        <td>
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <button class="btn btn-primary me-md-2" type="button">Книгообеспеченность</button>
                            </div>
                        </td>
                    </tr>
                </#list>

              <tr>
                <th scope="row">1</th>
                <td>Технологии создания человеко-машинного интерфейса</td>
                <td>Лабораторные занятия</td>
                <td>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                        <button class="btn btn-primary me-md-2" type="button">Книгообеспеченность</button>
                    </div>
                </td>
              </tr>
            </tbody>
        </table>
    </div>
    
</div>

</body>
</html>