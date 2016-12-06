<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/tag.jsp"%>
<html>
<head>
    <title>用户列表页</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>用户列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>用户名</th>
                    <th>密码</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>年龄</th>
                    <th>所属部门</th>
                    <th>是否是部门领导</th>
                    <th>剩余年假</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${list}">
                    <tr>
                        <td>${user.userUsername}</td>
                        <td>${user.userPassword}</td>
                        <td>${user.userName}</td>
                        <td>${user.userSex}</td>
                        <td>${user.userAge}</td>
                        <td>${user.department.departmentName}</td>
                        <td>${user.userLeader == 1 ? "是" : "否"}</td>
                        <td>${user.userTimeLeft} 天</td>
                        <td><a href="/off_work_system/user/${user.userId}/edit">编辑</a> <a href="off_work_system/user/${user.userId}/delete">删除</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>