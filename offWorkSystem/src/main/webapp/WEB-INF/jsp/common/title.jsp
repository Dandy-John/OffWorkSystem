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
            case "nologin": window.location.href='<%=PROJECT_PATH%>' + "user/login";
        }
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

    G={};
    G.FormType = ['年假', '公假', '婚假', '产假', '工伤假', '陪产假', '病假', '事假'];
    function toFormType(index) {
        if(index > 0 && index <= G.FormType.length)
        return G.FormType[index-1];
        else return "未知类型";
    }
</script>

<div id="title">
    <input id="_queryHoliday" type="button" value="申请查看" onclick="changePage('queryHoliday')"/>
    <input id="_applyHoliday" type="button" value="申请假期" onclick="changePage('applyHoliday')"/>
    <input id="_approvalHoliday" type="button" value="审批假期" onclick="changePage('approvalHoliday')"/>
    <input id="_personalInfo" type="button" value="个人信息" onclick="changePage('personalInfo')"/>
    <input id="_logout" type="button" value="退出登录" onclick="changePage('logout')"/>
</div>