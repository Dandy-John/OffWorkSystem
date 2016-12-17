<%--
  Created by IntelliJ IDEA.
  User: hui
  Date: 16-12-14
  Time: 下午8:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/tag.jsp"%>
<html>
<head>
    <title>个人信息</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>
<%@include file="../common/title.jsp"%>

<div class="jumbotron masthead">
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-left" style="margin-top: 10px">
            <input type="button" class="btn btn-primary" id="submit" value="修改密码" onclick="submitForm()"/>
        </div>
        <div class="panel-body" style="text-align:center">
            <form id="editForm">
                <table border="1" class="table table-hover">
                    <tbody>
                    <tr><td>用户编号</td><td>${user.userId}</td></tr>
                    <tr><td>用户名</td><td>${user.userUsername}</td></tr>
                    <tr><td>姓名</td><td>${user.userName}</td></tr>
                    <tr><td>性别</td><td>${user.userSex}</td></tr>
                    <tr><td>年龄</td><td>${user.userAge}</td></tr>
                    <tr><td>所属部门</td><td>${user.userDepartment}</td></tr>
                    <tr><td>剩余年假</td><td>${user.userTimeLeft}</td></tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
</div>
</body>
<script type="application/javascript">
    function submitForm(){
        window.location.href = '<%=PROJECT_PATH%>' + "user/changePassword";
    }
</script>
</html>
