<%--
  Created by IntelliJ IDEA.
  User: hui
  Date: 16-12-14
  Time: 下午8:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../common/tag.jsp"%>
<!DOCTYPE html>
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

<div class="jumbotron masthead">
    <div class="container">
        <div class="panel panel-default">
            <div class="panel-heading text-center">
                <h2>申请假期</h2>
            </div>
            <div class="panel-body" style="text-align:center">
                <form id="editForm">
                    <table class="table text-left" style="width: 60%;margin-left: 20%">
                        <tbody>
                        <tr>
                            <td><h4 class="control-label">假期类型</h4></td>
                            <td>
                                <select id="formType" style="width: 238px; height: 34px" onchange="changeType()">
                                    <c:forEach var="type" items="${typeList}">
                                        <option value="${type.state}">${type.stateInfo}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr id="hd" hidden="hidden">
                            <td>配偶年龄</td>
                            <td>
                                <input type="text" class="form-control" id="peiou" />
                            </td>
                        </tr>

                        <tr>
                            <td><h4>请假时段</h4></td>
                            <td>
                                <fieldset>
                                    <div class="control-group">
                                        <div class="controls">
                                            <div class="input-prepend input-group">
                                                <span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
                                                <input type="text" readonly style="width: 200px" name="reservation" id="reservation" class="form-control" value="2016-12-19 - 2016-12-21" />
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>

                                <script type="text/javascript">
                                    $(document).ready(function() {
                                        $('#reservation').daterangepicker(null, function(start, end, label) {
                                            $('#formStartTime').val(start.toISOString());
                                            $('#formEndTime').val(end.toISOString());
                                            console.log(start.toISOString(), end.toISOString(), label);
                                        });
                                    });
                                </script>
                            </td>
                        </tr>
                        </tbody>
                    </table>


                    <div hidden>
                        <label class="control-label">假期起始时间</label>
                        <input type="text" id="formStartTime" value="2016-12-19"/>
                    </div>
                    <div hidden>
                        <label class="control-label">假期结束时间</label>
                        <input type="text" id="formEndTime" value="2016-12-21"/>
                    </div>
                    <div>
                        <input type="button" class="btn btn-primary" id="submit" value="提交" onclick="approveHoliday()"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
<script type="application/javascript">
    function approveHoliday(){
        var formType = $('#formType').val();
        var formStartTime = $('#formStartTime').val();
        var formEndTime = $('#formEndTime').val();


        if (formType == 3) {
            var page = $('#peiou').val();
            if (isNaN(page)) {
                alert("配偶年龄必须是数字");
                return;
            }
            var sex = '${user.userSex}';
            var age = ${user.userAge};
            var limit;
            if ((sex == "男" && age >= 25 && page >= 23) || (sex == "女" && age >= 23 && page >= 25)) {
                limit = 13;
            }
            else {
                limit = 3;
            }

            var start = new Date(formStartTime);
            var end = new Date(formEndTime);
            var cmp = end.getTime() - start.getTime() + 1;
            var days = Math.floor(cmp/(24*3600*1000))
            /*
            console.log(start);
            console.log(end);
            console.log(days);
            */

            var detail = "当前您和配偶";
            if (limit == 3) {
                detail += "不";
            }
            detail += "符合晚婚条件,可以请假的天数为 ";
            detail += limit;
            detail += " 天";

            if (days > limit) {
                alert('申请的假期时间超过天数限制\n' + detail);
                return;
            }
        }


        $.post('/off_work_system/model/' + 'api/addApplyHoliday', {
            'formType': formType,
            'formStartTime': formStartTime,
            'formEndTime': formEndTime
        }, function(result){
            console.log(result);
            if(result.state == 200){
                alert("申请提交成功");
                window.location.href="/off_work_system/model/queryHoliday";
            }else
            {
                $.post("/off_work_system/error/api/getErrorInfo", {'state' : result.state}, function(r) {
                    alert("申请提交失败\n" + r.data);
                });
            }
        })
    }

    function changeType() {
        if ($('#formType').val() == 3) {
            $('#hd').removeAttr('hidden');
        }
        else {
            $('#hd').attr('hidden', 'hidden');
        }
    }
</script>
</html>
