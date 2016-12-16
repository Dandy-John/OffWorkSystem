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

<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>新增</h2>
        </div>
        <div class="panel-body" style="text-align:center">
            <form id="editForm">
                <div>
                    <label class="control-label">用户编号</label>
                    <input type="text" id="userUsername" value="${user.userId}" disabled/>
                </div>
                <div>
                    <label class="control-label">姓名</label>
                    <input type="text" id="userName" value="${user.userUsername}" disabled/>
                </div>
                <div>
                    <label class="control-label">性别</label>
                    <input type="text" id="userSex" value="${user.userSex}" disabled/>
                </div>
                <div>
                    <label class="control-label">年龄</label>
                    <input type="text" id="userAge" value="${user.userAge}" disabled/>
                </div>
                <div>
                    <label class="control-label">所属部门</label>
                    <!--<input type="text" id="userDepartment" /> -->
                    <input type="text" id="userDepartment" value="${user.userDepartment}" disabled/>
                </div>
                <div>
                    <label class="control-label">剩余年假</label>
                    <input type="text" id="userTimeLeft" value="${user.userTimeLeft}" disabled/>
                </div>
                <div>
                    <input type="button" class="btn btn-primary" id="submit" value="修改密码" onclick="submitForm()"/>
                </div>
            </form>
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
