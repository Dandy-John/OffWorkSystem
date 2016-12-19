<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/tag.jsp"%>
<html>
<head>
    <title>部门列表</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>

<%@include file="../common/departmentTitle.jsp"%>
<c:if test="${state == 200}">
<div class="jumbotron masthead">
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>部门列表</h2>
            </div>
            <div class="panel-body">
                <table class="table table-hover" id="pages">
                    <thead>
                    <tr>
                        <th>部门编号</th>
                        <th>部门名称</th>
                        <th>上级部门编号</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tr>
                        <td></td>
                        <td><input type="text" id="addDepartmentName" value="新增部门名称"/></td>
                        <td>
                            <select id="addDepartmentParentId">
                                <c:forEach var="department" items="${list}">
                                   <c:if test="${department.departmentParent == -1}">
                                       <option value="${department.departmentId}">${department.departmentName}</option>
                                   </c:if>
                                </c:forEach>
                                <%--<option value="-1">无</option>--%>
                            </select>
                        </td>
                        <td>
                            <input type="button" class="btn btn-primary" onclick="addDepartment()" value="新增" />
                        </td>
                    </tr>


                    <c:forEach var="department" items="${list}" varStatus="status">
                    <c:if test="${status.index % pageSize == 0}">
                    <fmt:formatNumber var="c" value="${status.index / pageSize + 1}" pattern="#"/>
                    <tbody id="page${c}" hidden>
                    </c:if>

                    <tr>
                        <td>${department.departmentId}</td>
                        <td>${department.departmentName}</td>
                        <td>${department.departmentParent == -1 ? "无": department.departmentParent}</td>
                        <td><a href="javascript:deleteDepartment(${department.departmentId})">删除</a></td>
                    </tr>

                    <c:if test="${(status.index + 1) % pageSize == 0}">
                    <tbody>
                    </c:if>
                    </c:forEach>
                </table>

                <!-- 分页插件 -->
                <div>
                    <style>
                        *{ margin:0; padding:0; list-style:none;}
                        a{ text-decoration:none;}
                        a:hover{ text-decoration:none;}
                        .tcdPageCode{padding: 15px 20px;text-align: center;color: #ccc;}
                        .tcdPageCode a{display: inline-block;color: #428bca;display: inline-block;height: 25px;	line-height: 25px;	padding: 0 10px;border: 1px solid #ddd;	margin: 0 2px;border-radius: 4px;vertical-align: middle;}
                        .tcdPageCode a:hover{text-decoration: none;border: 1px solid #428bca;}
                        .tcdPageCode span.current{display: inline-block;height: 25px;line-height: 25px;padding: 0 10px;margin: 0 2px;color: #fff;background-color: #428bca;	border: 1px solid #428bca;border-radius: 4px;vertical-align: middle;}
                        .tcdPageCode span.disabled{	display: inline-block;height: 25px;line-height: 25px;padding: 0 10px;margin: 0 2px;	color: #bfbfbf;background: #f2f2f2;border: 1px solid #bfbfbf;border-radius: 4px;vertical-align: middle;}
                    </style>
                    <div class="tcdPageCode">

                        <script src="http://www.lanrenzhijia.com/ajaxjs/jquery.min.js"></script>
                        <script src="http://www.lanrenzhijia.com/ajaxjs/jquery.page.js"></script>
                        <script>
                            $("#page1").removeAttr("hidden");
                            $(".tcdPageCode").createPage({
                                pageCount:'${c}',
                                current:1,
                                backFn:function(p){
                                    $("#pages").find("tbody").attr("hidden", "hidden");
                                    $("#page"+p).removeAttr("hidden");
                                    console.log(p);
                                }
                            });
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</c:if>
</body>
<script>
    function deleteDepartment(departmentId) {
        if (confirm("确认删除部门编号为 " + departmentId + " 的部门吗?")) {
            $.post("/off_work_system/department/api/deleteDepartment",
                    {"departmentId" : departmentId}, function(result) {
                if (result.state == 200) {
                    alert("删除成功");
                    window.location.reload();
                }
                else {
                    $.post("/off_work_system/error/api/getErrorInfo", {"state" : result.state}, function(r) {
                        var message;
                        if (r.state == 200) {
                            message = r.data;
                        }
                        else {
                            message = "未知的错误";
                        }
                        alert("删除失败，错误码：" + result.state + "\n错误信息：" + message);
                    });
                }
            });
        }
    }

    function addDepartment(){
        var departmentName = $("#addDepartmentName").val();
        var departmentParentId = $("#addDepartmentParentId").val();

        console.log(departmentName, departmentParentId);

        $.post("/off_work_system/department/api/addDepartment", {
            "departmentName": departmentName,
            "departmentParent": departmentParentId
        }, function(result){
            if(result.state == 200){
                alert("添加成功");
                window.location.reload();
            }else {
                $.post("/off_work_system/error/api/getErrorInfo", {"state" : result.state}, function(r) {
                    var message;
                    if (r.state == 200) {
                        message = r.data;
                    }
                    else {
                        message = "未知的错误";
                    }
                    alert("添加失败，错误码：" + result.state + "\n错误信息：" + message);
                });
            }
        })
    }
</script>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>