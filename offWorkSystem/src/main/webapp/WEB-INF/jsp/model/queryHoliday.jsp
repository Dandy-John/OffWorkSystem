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
<div class="jumbotron masthead">
    <div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>历史假条</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover" id="pages">
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
                <fmt:setLocale value="zh_CN"/>
                <c:forEach var="form" items="${list}" varStatus="status">
                    <c:if test="${status.index % pageSize == 0}">
                    <fmt:formatNumber var="c" value="${status.index / pageSize + 1}" pattern="#"/>
                    <tbody id="page${c}" hidden>
                    </c:if>
                    <tr>
                        <td>${form.formId}</td>
                        <td>${enums:stateInfoOf(form.formState)}</td>
                        <td>${enums:typeInfoOf(form.formType)}</td>
                        <td>${form.formLength}天</td>
                        <td><fmt:formatDate value="${form.formStartTime}" pattern="yyyy年MM月dd日 E"/></td>
                        <td><fmt:formatDate value="${form.formEndTime}" pattern="yyyy年MM月dd日 E"/></td>
                    </tr>
                    <c:if test="${(status.index + 1) % pageSize == 0}">
                    <tbody>
                    </c:if>
                </c:forEach>
            </table>

            <!-- 分页插件 -->
            <div>
                <style>
                    *{ margin:0; padding:0; list-style:none;}
                    a{ text-decoration:none;}
                    a:hover{ text-decoration:none;}
                    .tcdPageCode{padding: 15px 20px;text-align: center;color: #ccc;}
                    .tcdPageCode a{display: inline-block;color: #428bca;display: inline-block;height: 25px;	line-height: 25px;	padding: 0 10px;border: 1px solid #ddd;	margin: 0 2px;border-radius: 4px;vertical-align: middle;}
                    .tcdPageCode a:hover{text-decoration: none;border: 1px solid #428bca;}
                    .tcdPageCode span.current{display: inline-block;height: 25px;line-height: 25px;padding: 0 10px;margin: 0 2px;color: #fff;background-color: #428bca;	border: 1px solid #428bca;border-radius: 4px;vertical-align: middle;}
                    .tcdPageCode span.disabled{	display: inline-block;height: 25px;line-height: 25px;padding: 0 10px;margin: 0 2px;	color: #bfbfbf;background: #f2f2f2;border: 1px solid #bfbfbf;border-radius: 4px;vertical-align: middle;}
                </style>
                <div class="tcdPageCode">

                    <script src="http://www.lanrenzhijia.com/ajaxjs/jquery.min.js"></script>
                    <script src="http://www.lanrenzhijia.com/ajaxjs/jquery.page.js"></script>
                    <script>
                        $("#page1").removeAttr("hidden");
                        $(".tcdPageCode").createPage({
                            pageCount:'${c}',
                            current:1,
                            backFn:function(p){
                                $("#pages").find("tbody").attr("hidden", "hidden");
                                $("#page"+p).removeAttr("hidden");
                                console.log(p);
                            }
                        });
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</c:if>
</body>

<script>
    console.log('${enums:stateInfoOf(1)}')
</script>
</html>
