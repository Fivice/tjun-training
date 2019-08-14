<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
    String path = request.getContextPath();
    String projectPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>评价管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="../static/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="../static/css/jqmobo.css">
    <link rel="stylesheet" href="../static/common/layui/layui/css/layui.css" />
    <link rel="stylesheet" href="../static/css/studentRegisterBySelfForm.css">
    <script src="../static/jquery/jquery.min.js"></script>
    <script src="../static/common/bootstrap/js/bootstrap.js"></script>
    <script src="../static/common/layer/layer.js"></script>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>

</head>
<body>
<section class="panel">
    <input id="basePath" type="hidden" value="<%=projectPath%>">
    <form id="form1" action="${ctx}/remoteOperate/saveEvaluation.action" method="post"  style="overflow:hidden;">

        <div id="divLoadAnswer" style="display: none; font-size: 14px;line-height:24px;padding:6px 8px;background-color: #fff9f0;">

        </div>
        <div id="toptitle">
            <h1 class="htitle">${classInfo.className}</h1>

        </div>

        <div id="divDesc" class="formfield">
				<span class="description">
                <p>尊敬的学员：</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;    您好！为确保培训实效，更好地发挥培训对人才成长和业务绩效提升的促进作用，特制定本调查问卷。</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;    您的每项反馈和建议都将是我们做好培训工作的重要参考。非常感谢您抽出宝贵时间填写本问卷！感谢您的大力支持！</p>
<p>&nbsp;</p></span>
        </div>

        <div id="divQuestion">
            <fieldset class="fieldset" style="" id="fieldset1">
                <input type="hidden" id="regId" name="id" value="${regId}">
                <input type="hidden" name="classId" id="classId" value="${classInfo.id}">
                <div id="divPage1" style="margin:7px 12px;"><b><b><span style="color:#0021b0;">一、请您对培训总体情况做出评价。</span></b></b>
                </div>
                <div class="field ui-field-contain" id="div1" req="1" topic="1" data-role="fieldcontain" type="9">
                        <div class="field-label">1.${type1}<span class="req">*</span><span class="qtypetip">&nbsp;</span></div>
                        <table cellspacing="0"  class="matrix-rating" style="">
                            <colgroup>
                                <col width="20%">
                                <col width="20%">
                                <col width="20%">
                                <col width="20%">
                                <col width="20%">
                            </colgroup>
                            <tbody>
                            <tr class="trlabel">
                                <th>非常满意</th>
                                <th>满意</th>
                                <th>较满意</th>
                                <th>一般</th>
                                <th>不满意</th>
                            </tr>
                            <c:forEach items="${mapList}" var="mapList" varStatus="index">
                            <c:if test="${mapList.evaProject.largeClass==1}">
                                    <tr>
                                        <input type="hidden" name="project${mapList.evaProject.id}" value="${mapList.evaProject.id}">
                                        <td style="text-align:left;" class="title" colspan="5">${mapList.evaProject.project}</td>
                                    </tr>
                                    <tr style="text-align: center">
                                        <td >
                                            <input type="radio" value="1" name="result${index.index }"  checked="checked">
                                        </td>
                                        <td >
                                            <input type="radio" value="2" name="result${index.index }">
                                        </td>
                                        <td >
                                            <input type="radio" value="3" name="result${index.index }">
                                        </td>
                                        <td>
                                            <input type="radio"  value="4" name="result${index.index }">
                                        </td>
                                        <td>
                                            <input type="radio"  value="5" name="result${index.index }">
                                        </td>
                                    </tr>
                            </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                <div class="field-label">2.${type2}<span class="req">*</span><span class="qtypetip">&nbsp;</span></div>
                <table cellspacing="0"  class="matrix-rating" style="">
                    <colgroup>
                        <col width="20%">
                        <col width="20%">
                        <col width="20%">
                        <col width="20%">
                        <col width="20%">
                    </colgroup>
                    <tbody>
                    <tr class="trlabel">
                        <th>非常满意</th>
                        <th>满意</th>
                        <th>较满意</th>
                        <th>一般</th>
                        <th>不满意</th>
                    </tr>
                    <c:forEach items="${mapList}" var="mapList" varStatus="index">
                        <c:if test="${mapList.evaProject.largeClass==2}">
                            <tr>
                                <input type="hidden" name="project${mapList.evaProject.id}" value="${mapList.evaProject.id}">
                                <td style="text-align:left;" class="title" colspan="5">${mapList.evaProject.project}</td>
                            </tr>
                            <tr style="text-align: center">
                                <td >
                                    <input type="radio" value="1" name="result${index.index }" checked="checked">
                                </td>
                                <td >
                                    <input type="radio" value="2" name="result${index.index }">
                                </td>
                                <td >
                                    <input type="radio" value="3" name="result${index.index }">
                                </td>
                                <td>
                                    <input type="radio"  value="4" name="result${index.index }">
                                </td>
                                <td>
                                    <input type="radio"  value="5" name="result${index.index }">
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                    <div class="field-label">3.${type3}<span class="req">*</span><span class="qtypetip">&nbsp;</span></div>
                    <table cellspacing="0"  class="matrix-rating" style="">
                        <colgroup>
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                        </colgroup>
                        <tbody>
                        <tr class="trlabel">
                            <th>非常满意</th>
                            <th>满意</th>
                            <th>较满意</th>
                            <th>一般</th>
                            <th>不满意</th>
                        </tr>
                        <c:forEach items="${mapList}" var="mapList" varStatus="index">
                            <c:if test="${mapList.evaProject.largeClass==3}">
                                <tr>
                                    <input type="hidden" name="project${mapList.evaProject.id}" value="${mapList.evaProject.id}">
                                    <td style="text-align:left;" class="title" colspan="5">${mapList.evaProject.project}</td>
                                </tr>
                                <tr style="text-align: center">
                                    <td >
                                        <input type="radio" value="1" name="result${index.index }"  checked="checked">
                                    </td>
                                    <td >
                                        <input type="radio" value="2" name="result${index.index }">
                                    </td>
                                    <td >
                                        <input type="radio" value="3" name="result${index.index }" >
                                    </td>
                                    <td>
                                        <input type="radio"  value="4" name="result${index.index }" >
                                    </td>
                                    <td>
                                        <input type="radio"  value="5" name="result${index.index }" >
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="field-label">4.${type4}<span class="req">*</span><span class="qtypetip">&nbsp;</span></div>
                    <table cellspacing="0"  class="matrix-rating" style="">
                        <colgroup>
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                        </colgroup>
                        <tbody>
                        <tr class="trlabel">
                            <th>非常满意</th>
                            <th>满意</th>
                            <th>较满意</th>
                            <th>一般</th>
                            <th>不满意</th>
                        </tr>
                        <c:forEach items="${mapList}" var="mapList" varStatus="index">
                            <c:if test="${mapList.evaProject.largeClass==4}">
                                <tr>
                                    <input type="hidden" name="project${mapList.evaProject.id}" value="${mapList.evaProject.id}">
                                    <td style="text-align:left;" class="title" colspan="5">${mapList.evaProject.project}</td>
                                </tr>
                                <tr style="text-align: center">
                                    <td >
                                        <input type="radio" value="1" name="result${index.index }"  checked="checked">
                                    </td>
                                    <td >
                                        <input type="radio" value="2" name="result${index.index }">
                                    </td>
                                    <td >
                                        <input type="radio" value="3" name="result${index.index }">
                                    </td>
                                    <td>
                                        <input type="radio"  value="4" name="result${index.index }">
                                    </td>
                                    <td>
                                        <input type="radio" value="5" name="result${index.index }">
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="field-label">5.${type5}<span class="req">*</span><span class="qtypetip">&nbsp;</span></div>
                    <table cellspacing="0"  class="matrix-rating" style="">
                        <colgroup>
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                        </colgroup>
                        <tbody>
                        <tr class="trlabel">
                            <th>非常满意</th>
                            <th>满意</th>
                            <th>较满意</th>
                            <th>一般</th>
                            <th>不满意</th>
                        </tr>
                        <c:forEach items="${mapList}" var="mapList" varStatus="index">
                            <c:if test="${mapList.evaProject.largeClass==5}">
                                <tr>
                                    <input type="hidden" name="project${mapList.evaProject.id}" value="${mapList.evaProject.id}">
                                    <td style="text-align:left;" class="title" colspan="5">${mapList.evaProject.project}</td>
                                </tr>
                                <tr style="text-align: center">
                                    <td >
                                        <input type="radio" value="1" name="result${index.index }"  checked="checked">
                                    </td>
                                    <td >
                                        <input type="radio" value="2" name="result${index.index }" >
                                    </td>
                                    <td >
                                        <input type="radio" value="3" name="result${index.index }" >
                                    </td>
                                    <td>
                                        <input type="radio"  value="4" name="result${index.index }" >
                                    </td>
                                    <td>
                                        <input type="radio"  value="5" name="result${index.index }" >
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="field-label">6.${type6}<span class="req">*</span><span class="qtypetip">&nbsp;</span></div>
                    <table cellspacing="0"  class="matrix-rating" style="">
                        <colgroup>
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                            <col width="20%">
                        </colgroup>
                        <tbody>
                        <tr class="trlabel">
                            <th>非常满意</th>
                            <th>满意</th>
                            <th>较满意</th>
                            <th>一般</th>
                            <th>不满意</th>
                        </tr>
                        <c:forEach items="${mapList}" var="mapList"  varStatus="index">
                            <c:if test="${mapList.evaProject.largeClass==6}">
                                <tr>
                                    <input type="hidden" name="project${mapList.evaProject.id}" value="${mapList.evaProject.id}">
                                    <td style="text-align:left;" class="title" colspan="5">${mapList.evaProject.project}</td>
                                </tr>
                                <tr style="text-align: center">
                                    <td >
                                        <input type="radio" value="1" name="result${index.index }" checked="checked">
                                    </td>
                                    <td >
                                        <input type="radio" value="2"  name="result${index.index }" >
                                    </td>
                                    <td >
                                        <input type="radio" value="3"  name="result${index.index }" >
                                    </td>
                                    <td>
                                        <input type="radio"  value="4"  name="result${index.index }" >
                                    </td>
                                    <td>
                                        <input type="radio"  value="5"  name="result${index.index }" >
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                    <table cellspacing="0"  class="matrix-rating" style="">
                    <div class="field-label">7.${type7}<span class="req">*</span><span class="qtypetip">&nbsp;</span></div>
                        <c:forEach items="${mapList}" var="mapList"  varStatus="index">
                            <c:if test="${mapList.evaProject.largeClass==7}">
                                <tr>
                                    <input type="hidden" name="project${mapList.evaProject.id}" value="${mapList.evaProject.id}">
                                    <td style="text-align:left;" class="title" >${mapList.evaProject.project}</td>
                                    <td>
                                        <div class="form-group">
                                            <div class="col-xs-12">
                                                <input type="text"  class="form-control"   name="result${index.index }" >
                                            </div>
                                        </div>
                                    </td>
                                </tr>

                            </c:if>
                        </c:forEach>
                    </table>
                    <div class="field-label">8.请推荐以往培训中让您印象深刻的${type8}<span class="qtypetip">&nbsp;</span></div>

                        <c:forEach items="${mapList}" var="mapList"  varStatus="index">
                            <c:if test="${mapList.evaProject.largeClass==8}">
                                <input type="hidden" name="project${mapList.evaProject.id}" value="${mapList.evaProject.id}">
                                <div class="form-group">

                                    <div class="col-xs-10" style="width: 100%">
                                        <input type="text" class="form-control" id="email" name="result${index.index }" >
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                </div>

                <div class="cutfield" id="divCut1" qtopic="9" topic="c1">
                    <div style="margin:7px 12px;"><b><span style="color:#013add;">二、请您对以下各门课程的授课情况和教师进行评价。</span></b></div>
                </div>
                <div class="field ui-field-contain" id="div9" req="1" topic="9" data-role="fieldcontain" type="6">
                    <c:forEach items="${evaSubjList}" var="evaSubjList" varStatus="index">
                    <div class="field-label">${index.index+9}.${evaSubjList.schContent}(${evaSubjList.teacher})<span class="req">*</span><span class="qtypetip">&nbsp;</span></div>
                    <div>
                        <table cellspacing="0" id="divRefTab9" class="matrix-rating" style="">
                            <colgroup>
                                <col width="20%">
                                <col width="20%">
                                <col width="20%">
                                <col width="20%">
                                <col width="20%">
                            </colgroup>
                            <tbody>
                            <tr class="trlabel">
                                <th>非常满意</th>
                                <th>满意</th>
                                <th>较满意</th>
                                <th>一般</th>
                                <th>不满意</th>
                            </tr>
                                    <tr>
                                        <input type="hidden" name="pro${index.index}" value="${evaSubjList.schId}">
                                        <td style="text-align:left;" class="title" colspan="5">课程评价</td>
                                    </tr>
                                    <tr style="text-align: center">
                                        <td >
                                            <input type="radio" value="1" name="res${index.index }"  checked="checked">
                                        </td>
                                        <td >
                                            <input type="radio" value="2" name="res${index.index }">
                                        </td>
                                        <td >
                                            <input type="radio" value="3" name="res${index.index }">
                                        </td>
                                        <td>
                                            <input type="radio"  value="4" name="res${index.index }">
                                        </td>
                                        <td>
                                            <input type="radio"  value="5" name="res${index.index }">
                                        </td>
                                    </tr>
                            <tr>
                                    <%--<input type="hidden" name="project${mapList.evaProject.id}" value="${mapList.evaProject.id}">--%>
                                <td style="text-align:left;" class="title" colspan="5">教师评价</td>
                            </tr>
                            <tr style="text-align: center">
                                <td >
                                    <input type="radio" value="1" name="resu${index.index }"  checked="checked">
                                </td>
                                <td >
                                    <input type="radio" value="2" name="resu${index.index }">
                                </td>
                                <td >
                                    <input type="radio" value="3" name="resu${index.index }">
                                </td>
                                <td>
                                    <input type="radio"  value="4" name="resu${index.index }">
                                </td>
                                <td>
                                    <input type="radio"  value="5" name="resu${index.index }">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    </c:forEach>
                </div>

            </fieldset>
        </div>
        <div class="footer">
            <div class="ValError" id="ValError">
            </div>

            <div id="captcha" style="margin: 0 auto;"></div>
            <div id="divSubmit" style="padding: 0px 20px 10px;">
                <div id="tdCode" style="display: none; padding-bottom: 15px;">

                </div>

                <div>
                    <button type="button" style="border:none;" id="ctlNext" href="javascript:;" class="button blue">提交</button>
                </div>
            </div>
        </div>

        <div id="divTimeUp" style="display: none;">
            <div style="padding: 10px; overflow: auto; line-height: 20px; font-size: 16px; text-align: center;" id="divTimeUpTip"></div>
        </div>

        <input type="hidden" value="1" id="action" name="action">
        <input type="hidden" value="2018/11/7 10:26:31" id="starttime" name="starttime">
        <input type="hidden" value="directphone" id="source" name="source">
    </form>
</section>
<div class="main_nav_bottom">
    <nav class="navbar navbar-default navbar-fixed-bottom bottom-info">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-sm-8 col-lg-8">@copyRight:2017-2018</div>
                <div class="col-xs-4 col-sm-4 col-lg-4">天君智云</div>
            </div>
        </div>
    </nav>
</div>
</body>
<script type="text/javascript">
    if (top.location != location){
        top.location.href = location.href;
    }

$("#ctlNext").click(function()
    {

        $.ajax({
            data: $("#form1").serialize(),
            dataType: 'json',
            type: 'post',
            url: "${ctx}/remoteOperate/saveEvaluation.action",
            success: function (result) {
                if (result.code == 1) {

                    parent.layer.msg('评价成功!感谢您的评价！', {
                        icon: 1,
                        time: 3000
                    }, function() {
                        console.log("评价完成");
                        window.location.href = '${ctx}/signUpManager/studentRegisterBySelf.action?classId=' + $("#classId").val();
                    });
                } else {
                    layer.msg(result.message, {
                        icon: 2,
                        time: 1000
                    });
                }
            }
        })
    })

</script>
</html>

