<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/tag.jsp"%>
<html>
<head>
    <title>用户列表页</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>

<%@include file="../common/userTitle.jsp"%>

<div class="jumbotron masthead">
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>用户列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>用户编号</th>
                    <th>用户名</th>
                    <th>密码</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>年龄</th>
                    <th>所属部门</th>
                    <th>是否是部门领导</th>
                    <th>剩余年假</th>
                    <td>是否是管理员</td>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${list}">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.userUsername}</td>
                        <td>${user.userPassword}</td>
                        <td>${user.userName}</td>
                        <td>${user.userSex}</td>
                        <td>${user.userAge}</td>
                        <td>${user.department.departmentName}</td>
                        <td>${user.userLeader == 1 ? "是" : "否"}</td>
                        <td>${user.userTimeLeft} 天</td>
                        <td>${user.isAdmin == 1 ? "是" : "否"}</td>
                        <td><a href="/off_work_system/user/${user.userId}/edit">编辑</a> <a href="javascript:deleteUser(${user.userId})">删除</a></td>
                    </tr>
                </c:forEach>
                <tr>
                    <input type="button" class="btn btn-primary" onclick="addUser()" value="新增" />
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</div>
</body>
<script>
    function deleteUser(userId) {
        if (confirm("确认删除用户编号为 " + userId + " 的用户吗?")) {
            $.post("/off_work_system/user/api/delete", {"userId" : userId}, function(result) {
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

    function addUser(){
        window.location.href = "/off_work_system/user/add";
    }
</script>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>