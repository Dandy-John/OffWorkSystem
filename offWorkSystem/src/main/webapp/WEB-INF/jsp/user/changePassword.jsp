<%--
  Created by IntelliJ IDEA.
  User: 张栋迪
  Date: 2016/12/9
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改密码</title>
</head>
<body>

</body><%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/tag.jsp"%>
<html>
<head>
    <title>修改密码</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>修改密码</h2>
        </div>
        <div class="panel-body text-center" style="text-align:center">
            <form id="editForm" style="text-align:center">
                <table class="table text-center" style="width: 60%;margin-left: 20%">
                    <tbody>
                        <tr>
                            <td><label class="control-label">用户名</label></td>
                            <td><label id="userUsername" value="${user.userUsername}" >${user.userUsername}</label></td>
                        </tr>
                        <tr>
                            <td><label class="control-label">旧密码</label></td>
                            <td><input type="password" id="oldPasword" /></td>
                        </tr>
                        <tr>
                            <td><label class="control-label">新密码</label></td>
                            <td><input type="password" id="newPassword" /></td>
                        </tr>
                        <tr>
                            <td><label class="control-label">新密码确认</label></td>
                            <td><input type="password" id="newPasswordRepeat" /></td>
                        </tr>
                        <tr>
                            <td><input type="button" class="btn btn-primary" id="submit" value="更改" onclick="submitForm()"/></td>
                            <td><input type="button" class="btn" id="cancel" value="取消" onclick="cancelEdit()" /></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
<script>
    function submitForm() {
        var userId = ${user.userId};
        var oldPassword = $('#oldPasword').val();
        var newPassword = $('#newPassword').val();
        var newPasswordRepeat = $('#newPasswordRepeat').val();
        if (newPassword != newPasswordRepeat) {
            alert("两次密码输入不一致");
            return;
        }

        $.post("/off_work_system/user/api/changePassword", {
            'userId' : userId,
            'oldPassword' : oldPassword,
            'newPassword' : newPassword
        }, function(result) {
            if (result.state == 200) {
                alert("修改成功，请重新登录");
                window.location.href = "/off_work_system";
            }
            else {
                $.post("/off_work_system/error/api/getErrorInfo", {"state" : result.state}, function(r) {
                    var message;
                    if (r.state == 200) {
                        message = r.data;
                    }
                    else {
                        message = "未知错误";
                    }
                    alert("修改失败，错误号：" + result.state + "\n" + message);
                });
            }
        });
    }

    function cancelEdit() {
        window.location.href = "/off_work_system/user/edit";
    }
</script>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>
</html>
