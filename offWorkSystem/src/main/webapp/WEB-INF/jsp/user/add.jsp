<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/tag.jsp"%>
<html>
<head>
    <title>用户新增页</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>
<%@include file="../common/userTitle.jsp"%>

<div class="jumbotron masthead">
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>新增</h2>
        </div>
        <div class="panel-body" style="text-align:center">
            <form id="editForm">
                <table class="table text-center" style="width: 60%;margin-left: 20%">
                    <tbody>
                    <tr><td><label class="control-label">用户名</label></td> <td><input type="text" id="userUsername" /></td></tr>
                    <tr><td><label class="control-label">密码</label></td>   <td><input type="text" id="userPassword" value="000000" disabled /></td></tr>
                    <tr><td><label class="control-label">姓名</label></td>   <td><input type="text" id="userName" /></td></tr>
                    <tr><td><label class="control-label">性别</label></td><td>
                        <select id="userSex">
                            <option value="男">男</option>
                            <option value="女">女</option>
                        </select></td>
                    </tr>
                    <tr><td><label class="control-label">年龄</label></td>     <td><input type="text" id="userAge" /></td></tr>
                    <tr><td><label class="control-label">所属部门</label></td> <td>
                        <select id="userDepartment">
                            <c:forEach var="de" items="${departments}">
                                <option value="${de.departmentId}">${de.departmentName}</option>
                            </c:forEach>
                        </select></td>
                    </tr>
                    <tr><td><label class="control-label">是否是领导</label></td><td>
                        <select id="userLeader">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select></td>
                    </tr>
                    <tr><td><label class="control-label">剩余年假</label></td><td><input type="text" id="userTimeLeft" /></td></tr>
                    <tr><td><label class="control-label">是否是管理员</label></td><td>
                        <select id="isAdmin">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td><input type="button" class="btn btn-primary" id="submit" value="新增" onclick="submitForm()"/></td>
                        <td><input type="button" class="btn" id="cancel" value="取消" onclick="cancelAdd()" /></td>
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
        var userUsername = $('#userUsername').val();
        var userName = $('#userName').val();
        var userSex = $('#userSex').val();
        var userAge = $('#userAge').val();
        var userDepartment = $('#userDepartment').val();
        var userLeader = $('#userLeader').val();
        var userTimeLeft = $('#userTimeLeft').val();
        var isAdmin = $('#isAdmin').val();
        if (userUsername == "") {
            alert("用户名不能为空");
            return;
        }
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

        console.log(userUsername + " "
            + userName + " "
            + userSex + " "
            + userAge + " "
            + userDepartment + " "
            + userLeader + " "
            + userTimeLeft + " "
            + isAdmin);

        $.post("/off_work_system/user/api/add", {
            'userUsername' : userUsername,
            'userName' : userName,
            'userSex' : userSex,
            'userAge' : +userAge,
            'userDepartment' : +userDepartment,
            'userLeader' : +userLeader,
            'userTimeLeft' : +userTimeLeft,
            'isAdmin' : isAdmin
        }, function(result) {
            if (result.state == 200) {
                alert("添加成功");
                window.location.href = "/off_work_system/user/list";
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
                    alert("添加失败\n错误信息：" + result.state + " " + message);
                });
            }
        });
    }

    function cancelAdd() {
        window.location.href = "/off_work_system/user/list";
    }
</script>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>