<%--
  Created by IntelliJ IDEA.
  User: hui
  Date: 16-12-14
  Time: 下午8:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/tag.jsp"%>
<html>
<head>
    <title>审批假期</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>
<%@include file="../common/title.jsp"%>

<c:if test="${state == 200}">
<div class="jumbotron masthead">
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>待审批假期列表</h2>
            </div>
            <div class="panel-body">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>假条编号</th>
                        <th>用户编号</th>
                        <th>用户名</th>
                        <th>申请状态</th>
                        <th>请假类型</th>
                        <th>假期长度</th>
                        <th>假期开始时间</th>
                        <th>假期结束时间</th>
                        <th>附件</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="form" items="${list}">
                        <tr>
                            <td>${form.formId}</td>
                            <td>${form.userId}</td>
                            <td>${form.user.userName}</td>
                            <td>${enums:stateInfoOf(form.formState)}</td>
                            <td>${enums:typeInfoOf(form.formType)}</td>
                            <td>${form.formLength}天</td>
                            <td><fmt:formatDate value="${form.formStartTime}" pattern="yyyy年MM月dd日 E"/></td>
                            <td><fmt:formatDate value="${form.formEndTime}" pattern="yyyy年MM月dd日 E"/></td>
                            <td></td>
                            <td><button onclick="submit(${form.formId})">批准</button></td>
                        </tr>
                    </c:forEach>
                    <script type="application/javascript">
                        function submit(formId){
                            $.post('<%=MODEL_PATH%>' + "addApprovalHoliday",{
                                formId: formId
                            }, function(result){
                                console.log(result);
                                if(result.state == 200){
                                    alert('审批成功');
                                    window.location.href='<%=MODEL_PATH%>' + 'approvalHoliday';
                                }else{
                                    alert('审批失败');
                                }
                            });
                        }
                    </script>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</c:if>
</body>
</html>
