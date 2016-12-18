<%--
  Created by IntelliJ IDEA.
  User: hui
  Date: 16-12-14
  Time: 下午8:13
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="tag.jsp"%>
<%@include file="head.jsp"%>
<%@include file="globalVar.jsp"%>

<script type="application/javascript">
    $.get("/off_work_system/user/api/islogin", function(result){
        function state(result){
            if (result.state == 200) {
                if(result.data.isAdmin == 1) return "admin";
                else if (result.data.department.departmentParent != -1) return "normal";
                else return "approver";
            }
            else {
                return "nologin";
            }
        }

        switch (state(result)){
            case "nologin": window.location.href='<%=PROJECT_PATH%>' + "user/login"; break;
            case "admin": $("#userManage").html('<a href="${pageContext.request.contextPath}/user/list">用户管理</a>');
            case "approver": $("#approve").html('<a onclick="changePage(\'approvalHoliday\')">审批假期</a>');
        }
        $("#welcome").html('<a>您好,' + result.data.userUsername + '</a>');
    });

    function logout() {
        $.get('/off_work_system/user/api/logout', function(result) {
            if (result.state == 200) {
                alert('退出登录');
                window.location.href = "/off_work_system";
            }
        });
    }
</script>

<script type="application/javascript">
    function changePage(modelname){
        window.location.href='<%=MODEL_PATH%>'+modelname;
    }

    function stateAlert(state){
        if(state == 200){
            alert("申请提交成功");
        }else if(state == 490) {
            alert("未登录")
        }
        else return false;
        return true;
    }
</script>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <p class="navbar-brand">海宁医院请假系统</p>
        </div>
        <div class="navbar-collapse collapse" role="navigation">
            <ul class="nav navbar-nav">
                <li class="hidden-sm hidden-md"><a onclick="changePage('queryHoliday')">申请查看</a></li>
                <li><a onclick="changePage('applyHoliday')">申请假期</a></li>
                <li id="approve"></li>
                <li><a onclick="changePage('personalInfo')">个人信息</a></li>
                <li id="userManage"></li>
                <li><a onclick="changePage('logout')">退出登录</a></li>
            </ul>


            <ul class="nav navbar-nav navbar-right hidden-sm">
                <li id="welcome"></li>
            </ul>
        </div>
    </div>
</div>

<%--<div id="title">--%>
    <%--<input id="_queryHoliday" type="button" value="申请查看" onclick="changePage('queryHoliday')"/>--%>
    <%--<input id="_applyHoliday" type="button" value="申请假期" onclick="changePage('applyHoliday')"/>--%>
    <%--<input id="_approvalHoliday" type="button" value="审批假期" onclick="changePage('approvalHoliday')"/>--%>
    <%--<input id="_personalInfo" type="button" value="个人信息" onclick="changePage('personalInfo')"/>--%>
    <%--<input id="_logout" type="button" value="退出登录" onclick="changePage('logout')"/>--%>
<%--</div>--%>