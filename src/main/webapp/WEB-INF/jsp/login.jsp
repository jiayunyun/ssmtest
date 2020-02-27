<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>欢迎使用东道创成温测系统</title>
    <link href="${pageContext.request.contextPath }/css/component.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/css/style.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/css/normalize.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/css/logindemo.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.4.4.min.js"></script>
    <style type="text/css">
        .ai-logo-box {
            width: 100%;
            display: -ms-flexbox;
            display: flex;
            -ms-flex-pack: center;
            justify-content: center;
            font-size: 20px;
            color: #FFF;
            align-items: center;
            font-style: italic;
        }

        .ai-logo-box img {
            margin-right: 8px
        }

        .ai-logo-bigBox {
            position: absolute;
            top: 170px;
            display: -ms-flexbox;
            display: flex;
            -ms-flex-pack: center;
            justify-content: center;
            width: 100%;
            top: -50px;
            width: 654px
        }

        .box1 {
            position: relative;
            top: calc(50% - 214px)
        }

        .ai-tips-box {
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .message-text p, a {
            font-size: 20px;
            color: #333;
            text-align: center;
        }
    </style>
</head>

<body>
<div class="container demo-1 demo-2" id="mianBox">
    <div class="content">
        <div id="large-header" class="large-header" style="width: 100%; height: 608px; position: absolute;">
            <canvas id="demo-canvas" width="1366" height="608"></canvas>
        </div>
    </div>
    <div class="content">
        <div id="large-header2" class="large-header" style="width: 100%; height: 608px; position: absolute;">
            <canvas id="demo-canvas2" width="1366" height="608"></canvas>
        </div>
    </div>

    <div class="box1" style="display:-ms-flexbox;-ms-flex-align:center;-ms-flex-pack:center;">

        <div>
            <div class="loginbox" style="display:-ms-flexbox;-ms-flex-align:center;-ms-flex-pack:center;">
                <div class="login-header">
                    <div class="tname" style="font-size: 21px;">东道创成温测系统</div>
                    <div class="ename"></div>
                </div>
                <form action="${pageContext.request.contextPath }/login.action" method="post" role="form" id="form1"
                      onsubmit="return formcheck();" style="width: 70%;margin-top: 100px">
                    <ul>
                        <li>
                            <input autocomplete="off" required="" name="username" type="text" class="loginuser"
                                   placeholder="用户名">
                        </li>
                        <li>
                            <input name="password" required="" type="password" class="loginpwd" placeholder="密码">
                        </li>
                        <li>
                            <input type="submit" id="submit" name="submit" value="登录" class="loginbtn">
                        </li>
                    </ul>
                    <input name="token" value="91af2c73" type="hidden">
                </form>
            </div>
        </div>
    </div>
</div>
<div class="container" id="mianBox2" style="display: none;">
    <div class="ai-tips-box">
        <div>
            <div style="position: relative;"><img style="position: absolute;left: 37%"
                                                  src="${pageContext.request.contextPath }/img/ai-fail.png"></div>
            <div style="position: absolute;top: 70%;left: 42%" class="message-text">
                <p>你的浏览器版本过低,请升级你的最新版本
                    <br> <a href="https://support.microsoft.com/zh-cn/help/17621/internet-explorer-downloads">如果你的跳转升级，请点击此链接</a>
                </p>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    if (navigator.appName == 'Microsoft Internet Explorer') {
        if (navigator.userAgent.indexOf("MSIE 5.0") > 0 || navigator.userAgent.indexOf("MSIE 6.0") > 0 || navigator.userAgent.indexOf("MSIE 7.0") > 0 || navigator.userAgent.indexOf("MSIE 8.0") > 0 || navigator.userAgent.indexOf("MSIE 9.0") > 0) {

            $('#mianBox').css('display', 'none')
            $('#mianBox2').css('display', 'flex')
        }
    }
</script>
<script src="${pageContext.request.contextPath }/js/TweenLite.min.js"></script>
<script src="${pageContext.request.contextPath }/js/EasePack.min.js"></script>
<script src="${pageContext.request.contextPath }/js/rAF.js"></script>
<script src="${pageContext.request.contextPath }/js/demo-1.js"></script>
<script src="${pageContext.request.contextPath }/js/demo-2.js"></script>
</body>
</html>