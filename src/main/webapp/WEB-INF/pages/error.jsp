<%--
  Created by IntelliJ IDEA.
  User: Fivice
  Date: 2018/11/27
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <link rel="shortcut icon" href="#" type="image/png">

    <title>404 页面</title>

    <link href="../static/css/style.css" rel="stylesheet">
    <link href="../static/css/style-responsive.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <%--<!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->--%>
</head>
<body class="error-page" style="">

<section>
    <div class="container">

        <section class="error-wrapper text-center" >
            <div align="center">
                <h1><img alt="" src="../static/img/404-error.png"></h1>
                <h2>没找到页面</h2>
                <h3>没有发现当前页面</h3>
                <a class="back-btn" href="#" onclick="javascript:history.back(-1);"> 回到上一页</a>
            </div>
        </section>

    </div>
</section>

<!-- Placed js at the end of the document so the pages load faster -->
<script src="../static/common/jquery/jquery-3.2.0.min.js"></script>
<script src="../static/common/jquery/jquery-migrate-1.0.0.js"></script>
<script src="../static/common/bootstrap/js/bootstrap.js"></script>
<script src="../static/jquery/modernizr.min.js"></script>


<!--common scripts for all pages-->
<!--<script src="js/scripts.js"></script>-->
<script>
    //跳出iframe
    $(function () {
        if (top.location != location){
            top.location.href = location.href;
        }
    })
</script>


</body>
</html>
