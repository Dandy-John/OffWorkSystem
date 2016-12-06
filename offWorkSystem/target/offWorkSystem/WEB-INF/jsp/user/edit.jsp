<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/tag.jsp"%>
<html>
<head>
    <title>用户编辑页</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>编辑</h2>
        </div>
        <div class="panel-body" style="text-align:center">
            <form id="editForm">
                <div>
                    <label class="control-label">用户名</label>
                    <input type="text" id="userUsername" value="${user.userUsername}" disabled />
                </div>
                <div>
                    <label class="control-label">密码</label>
                    <input type="text" id="userPassword" value="${user.userPassword}" disabled />
                    <input type="button" class="btn" id="resetPassword" value="重置" />
                </div>
                <div>
                    <label class="control-label">姓名</label>
                    <input type="text" id="userName" value="${user.userName}" />
                </div>
                <div>
                    <label class="control-label">性别</label>
                    <input type="text" id="userSex" value="${user.userSex}" />
                </div>
                <div>
                    <label class="control-label">年龄</label>
                    <input type="text" id="userAge" value="${user.userAge}" />
                </div>
                <div>
                    <label class="control-label">所属部门</label>
                    <!--<input type="text" id="userDepartment" value="${user.userDepartment}" /> -->
                    <select id="userDepartment">
                        <c:forEach var="de" items="${departments}">
                            <option value="${de.departmentId}" ${user.userDepartment == de.departmentId ? "selected=\"selected\"" : ""}>${de.departmentName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <label class="control-label">是否是领导</label>
                    <select id="userLeader">
                        <option value="1" ${user.userLeader == 1 ? "selected=\"selected\"" : ""}>是</option>
                        <option value="0" ${user.userLeader == 0 ? "selected=\"selected\"" : ""}>否</option>
                    </select>
                </div>
                <div>
                    <label class="control-label">剩余年假</label>
                    <input type="text" id="userTimeLeft" value="${user.userTimeLeft}" />
                </div>
                <div>
                    <input type="button" class="btn btn-primary" id="submit" value="更改" onclick="submitForm()"/>
                    <input type="button" class="btn" id="cancel" value="取消" onclick="cancel()" />
                </div>
            </form>
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
        if (userName == "") {
            alert("姓名不能为空");
            return;
        }
        if (userSex != "男" && userSex != "女") {
            alert("性别必须是男或女");
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
            + userTimeLeft + " ");

        $.post("/off_work_system/user/api/edit", {
            'userId' : userId,
            'userName' : userName,
            'userSex' : userSex,
            'userAge' : +userAge,
            'userDepartment' : +userDepartment,
            'userLeader' : +userLeader,
            'userTimeLeft' : +userTimeLeft
        }, function(result) {
            if (result.state == 200) {
                alert("更改成功");
                window.location.href = "/off_work_system/user/list";
            }
            else {
                alert("更改失败");
                window.location.href = "/off_work_system/user/list";
            }
        })
    }

    function cancel() {
        window.location.href = "/off_work_system/user/list";
    }
</script>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>