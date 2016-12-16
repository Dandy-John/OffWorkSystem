<%--
  Created by IntelliJ IDEA.
  User: hui
  Date: 16-12-14
  Time: 下午8:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/tag.jsp"%>
<html>
<head>
    <title>申请假期</title>
    <%@include file="../common/head.jsp"%>
</head>
<body>
<%@include file="../common/title.jsp"%>

<!-- 日历插件需要 -->
<meta charset="UTF-8" />
<link href="../datepicker/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="all" href="../datepicker/daterangepicker-bs3.css" />
<script type="text/javascript" src="../datepicker/moment.js"></script>
<script type="text/javascript" src="../datepicker/daterangepicker.js"></script>

<div class="container">
    <div class="panel panel-default">
        <div class="panel-body" style="text-align:center">
            <form id="editForm">
                <div>
                    <label class="control-label">假期类型</label>
                    <select id="formType">
                        <c:forEach var="type" items="${typeList}">
                            <option value="${type.state}">${type.stateInfo}</option>
                        </c:forEach>
                    </select>
                </div>
                <h4>请假时段</h4>
                <div class="well">
                    <form class="form-horizontal">
                        <fieldset>
                            <div class="control-group">
                                <div class="controls">
                                    <div class="input-prepend input-group">
                                <span class="add-on input-group-addon">
                                    <i class="glyphicon glyphicon-calendar fa fa-calendar"></i>
                                </span>
                                        <input type="text" readonly style="width: 200px" name="reservation"
                                               id="reservation" class="form-control" value="2016-12-19 - 2016-12-21" />
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </form>

                    <script type="text/javascript">
                        $(document).ready(function() {
                            $('#reservation').daterangepicker(null, function(start, end, label) {
                                $('#formStartTime').val(start.toISOString());
                                $('#formEndTime').val(end.toISOString());
                                console.log(start.toISOString(), end.toISOString(), label);
                            });
                        });
                    </script>

                </div>
                <div hidden>
                    <label class="control-label">假期起始时间</label>
                    <input type="text" id="formStartTime"/>
                </div>
                <div hidden>
                    <label class="control-label">假期结束时间</label>
                    <input type="text" id="formEndTime"/>
                </div>
                <div>
                    <input type="button" class="btn btn-primary" id="submit" value="提交" onclick="approveHoliday()"/>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="container">
    <div class="span12">

    </div>
</div>


</body>
<script type="application/javascript">
    function approveHoliday(){
        var formType = $('#formType').val();
        var formStartTime = $('#formStartTime').val();
        var formEndTime = $('#formEndTime').val();

        $.post('/off_work_system/model/' + 'addApplyHoliday', {
            'formType': formType,
            'formStartTime': formStartTime,
            'formEndTime': formEndTime
        }, function(result){
            console.log(result);
            if(result.state == 200){
                alert("申请提交成功");
            }else
            {
                alert("申请提交失败");
            }
        })
    }
</script>
</html>
