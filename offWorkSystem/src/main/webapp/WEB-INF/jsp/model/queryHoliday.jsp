<%--
  Created by IntelliJ IDEA.
  User: hui
  Date: 16-12-14
  Time: 下午8:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/tag.jsp"%>
<html>
<head>
    <title>申请查看</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>
<%@include file="../common/title.jsp"%>

<c:if test="${state == 200}">
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>历史假条</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>假条编号</th>
                    <th>申请状态</th>
                    <th>请假类型</th>
                    <th>假期长度</th>
                    <th>假期开始时间</th>
                    <th>假期结束时间</th>
                </tr>
                </thead>
                <tbody>
                <fmt:setLocale value="zh_CN"/>
                <c:forEach var="form" items="${list}">
                    <tr>
                        <td>${form.formId}</td>
                        <td>${enums:stateInfoOf(form.formState)}</td>
                        <td>${enums:typeInfoOf(form.formType)}</td>
                        <td>${form.formLength}天</td>
                        <td><fmt:formatDate value="${form.formStartTime}" pattern="yyyy年MM月dd日 E"/></td>
                        <td><fmt:formatDate value="${form.formEndTime}" pattern="yyyy年MM月dd日 E"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</c:if>
</body>

<script>
    console.log('${enums:stateInfoOf(1)}')
</script>
</html>
