<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../common/tag.jsp"%>
<html>
<head>
    <title>错误页</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>
<h2>${error}</h2>
<div id="info">页面将在 5 秒后跳转</div>
<a href="/off_work_system">或直接跳转到首页</a>
</body>
<script>
    var count = 4;
    function countdown(){
        var message = '页面将在 ' + count + ' 秒后跳转';
        $('#info').html(message);
        if (count-- == 0){
            window.location.href = "/off_work_system";
        }
    }
    setInterval(countdown, 1000);
</script>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>