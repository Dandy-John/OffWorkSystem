<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/tag.jsp"%>
<html>
<head>
    <title>用户编辑页</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>
<%@include file="../common/userTitle.jsp"%>

<div class="jumbotron masthead">
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>编辑</h2>
        </div>
        <div class="panel-body" style="text-align:center">
            <form id="editForm">
                <table class="table text-center" style="width: 60%;margin-left: 20%">
                    <tbody>
                    <tr><td><label class="control-label">用户名</label></td> <td><input type="text" id="userUsername" value="${user.userUsername}" disabled /></td></tr>
                    <tr><td><label class="control-label">密码</label></td> <td>
                        <%--<input type="text" id="userPassword" value="${user.userPassword}" disabled />--%>
                        <input type="button" class="btn" id="reset" value="重置" onclick="resetPassword()" /></td>
                    </tr>
                    <tr><td><label class="control-label">姓名</label></td><td><input type="text" id="userName" value="${user.userName}" /></td></tr>
                    <tr><td><label class="control-label">性别</label></td><td>
                        <select id="userSex">
                            <option value="男" ${user.userSex.equals("男") ? "selected=\"selected\"" : ""}>男</option>
                            <option value="女" ${user.userSex.equals("女") ? "selected=\"selected\"" : ""}>女</option>
                        </select></td>
                    </tr>
                    <tr><td><label class="control-label">年龄</label></td> <td><input type="text" id="userAge" value="${user.userAge}" /></td></tr>
                    <tr><td><label class="control-label">所属部门</label></td> <td>
                        <select id="userDepartment">
                        <c:forEach var="de" items="${departments}">
                            <option value="${de.departmentId}" ${user.userDepartment == de.departmentId ? "selected=\"selected\"" : ""}>${de.departmentName}</option>
                        </c:forEach>
                        </select></td>
                    </tr>
                    <tr><td><label class="control-label">是否是领导</label></td><td>
                        <select id="userLeader">
                            <option value="1" ${user.userLeader == 1 ? "selected=\"selected\"" : ""}>是</option>
                            <option value="0" ${user.userLeader == 0 ? "selected=\"selected\"" : ""}>否</option>
                        </select></td>
                    </tr>
                    <tr><td><label class="control-label">剩余年假</label></td> <td><input type="text" id="userTimeLeft" value="${user.userTimeLeft}" /></td></tr>
                    <tr><td><label class="control-label">是否是管理员</label></td> <td>
                        <select id="isAdmin">
                            <option value="1" ${user.isAdmin == 1 ? "selected=\"selected\"" : ""}>是</option>
                            <option value="0" ${user.isAdmin == 0 ? "selected=\"selected\"" : ""}>否</option>
                        </select></td>
                    </tr>
                    <tr><td><input type="button" class="btn btn-primary" id="submit" value="更改" onclick="submitForm()"/></td>
                        <td><input type="button" class="btn" id="cancel" value="取消" onclick="cancelEdit()" /></td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</div>
</div>
</body>
<script>
    function submitForm() {
        var userId = ${user.userId};
        var userName = $('#userName').val();
        var userSex = $('#userSex').val();
        var userAge = $('#userAge').val();
        var userDepartment = $('#userDepartment').val();
        var userLeader = $('#userLeader').val();
        var userTimeLeft = $('#userTimeLeft').val();
        var isAdmin = $('#isAdmin').val();
        if (userName == "") {
            alert("姓名不能为空");
            return;
        }
        if (userAge == "") {
            alert("年龄不能为空");
            return;
        }
        if (isNaN(userAge)) {
            alert("年龄必须是数字");
            return;
        }
        if (userTimeLeft == "") {
            alert("剩余年假不能为空");
            return;
        }
        if (isNaN(userTimeLeft)) {
            alert("剩余年假必须是数字");
            return;
        }

        console.log(userId + " "
            + userName + " "
            + userSex + " "
            + userAge + " "
            + userDepartment + " "
            + userLeader + " "
            + userTimeLeft + " "
            + isAdmin);

        $.post("/off_work_system/user/api/edit", {
            'userId' : userId,
            'userName' : userName,
            'userSex' : userSex,
            'userAge' : +userAge,
            'userDepartment' : +userDepartment,
            'userLeader' : +userLeader,
            'userTimeLeft' : +userTimeLeft,
            'isAdmin' : isAdmin
        }, function(result) {
            if (result.state == 200) {
                alert("更改成功");
                window.location.href = "/off_work_system/user/list";
            }
            else {
                alert("更改失败");
                window.location.href = "/off_work_system/user/list";
            }
        });
    }

    function cancelEdit() {
        window.location.href = "/off_work_system/user/list";
    }

    function resetPassword() {
        $.post("/off_work_system/user/api/reset", {'userId' : ${user.userId}}, function (result) {
            if (result.state == 200) {
                alert("重置成功");
                window.location.href = "/off_work_system/user/${user.userId}/edit";
            }
            else {
                alert("重置失败");
                window.location.href = "/off_work_system/user/${user.userId}/edit";
            }
        });
    }
</script>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>