<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="WEB-INF/jsp/common/tag.jsp"%>
<html>
<head>
    <title>用户列表页</title>
    <%@include file="WEB-INF/jsp/common/head.jsp"%>
</head>
<body>
<script>
    $.get("/off_work_system/user/api/islogin", function(result){
        //console.log(result);
        if (result.state == 200) {
            var info = '你好，' + result.data.userName + ' <a href="javascript:logout();">退出登录</a>';
            info += ' <a href="/off_work_system/user/edit">编辑个人信息</a>';
            //console.log(result.data.department.departmentParent);
            //console.log(result.data.userLeader);
            if (result.data.department.departmentParent == -1 && result.data.userLeader == 1) {
                info += ' <a href="/off_work_system/user/list" target="_blank">用户管理</a>'
            }
            $('#userInfo').html(info);

            window.location.href = "${pageContext.request.contextPath}/model/queryHoliday";
        }
        else {
            window.location.href = "${pageContext.request.contextPath}/user/login";
        }
        console.log(result);
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
<div id="userInfo"></div>
</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>