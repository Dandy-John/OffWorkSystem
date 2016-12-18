<%--
  Created by IntelliJ IDEA.
  User: hui
  Date: 16-12-18
  Time: 下午7:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="tag.jsp"%>
<%@include file="head.jsp"%>
<%@include file="globalVar.jsp"%>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <p class="navbar-brand">用户管理</p>
        </div>
        <div class="navbar-collapse collapse" role="navigation">
            <ul class="nav navbar-nav">
                <li class="hidden-sm hidden-md"><a href="${pageContext.request.contextPath}/user/list">用户列表</a></li>
                <li><a href="${pageContext.request.contextPath}/user/add">添加用户</a></li>
                <li><a href="${pageContext.request.contextPath}/model/queryHoliday">返回上级</a></li>
            </ul>
        </div>
    </div>
</div>
