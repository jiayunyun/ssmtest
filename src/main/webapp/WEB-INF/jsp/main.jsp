<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="utils/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!--[if lt IE 9]>
    <script type="text/javascript" src="lib/html5shiv.js"></script>
    <script type="text/javascript" src="lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/h-ui/css/H-ui.min.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/h-ui.admin/css/H-ui.admin.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/h-ui.admin/skin/default/skin.css"
          id="skin"/>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath }/js/h-ui.admin/css/style.css"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/layer/2.4/layer.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/h-ui/js/H-ui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath }/js/h-ui.admin/js/H-ui.admin.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-confirm.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/jquery-confirm.css"/>
    <!--[if IE 6]>
    <script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>欢迎使用温测系统</title>
    <style>
        #baojing img {
            width: 10px;
            height: 10px;
        }
    </style>
</head>

<script type="text/javascript">
	$(function(){
		$("#changepsw").click(function () {
			$("#chpsw").modal({
                backdrop: "static",//点击空白处不关闭对话框
                show: true
            });
		});
		//验证旧密码
		$("#oldpsw").blur(function(){  
			var username = $("#username").val();
			var oldpsw = $("#oldpsw").val();
			$.ajax({
				url: "../wence/checkpsw.action",
		        type: "post",
		        data: {
		        	username: username,
		        	oldpsw: oldpsw
		        },
		        dataType: "json",
		        success: function (data) {
		        	console.log(data);
		        	if(data=="0"){
		        		$.alert("密码不正确！！");
		        		$("#oldpsw").val("");
		        	}
		        }
			});
		});
		//确认两次输入的密码相同
		$("#rnewpsw").blur(function(){
			var newpsw = $("#newpsw").val();
			var rnewpsw = $("#rnewpsw").val();
			if(newpsw != rnewpsw){
				$.alert("两次密码不一样，请重新输入！");
				$("#rnewpsw").val("");
			}
		});
		
		//确定修改密码
		$("#confirm").click(function(){
			 var username = $("#username").val();
			var newpsw = $("#newpsw").val();
			var oldpsw = $("#oldpsw").val();
			var rnewpsw = $("#rnewpsw").val();
			if(oldpsw =="" || oldpsw =="" || rnewpsw==""){
				$.alert("密码不能为空！");
			}else{
				$.ajax({
					url: "../wence/changepsw.action",
			        type: "post",
			        data: {
			        	username:username,
			        	newpsw: newpsw,
			        	oldpsw: oldpsw
			        },
			        dataType: "json",
			        success: function (data) {
			        	console.log(data);
			        	$.alert("修改完成，请重新登录！");
			        }
				}); 
			}
		});
	});
</script>
<body>
<header class="navbar-wrapper">
    <div class="navbar navbar-fixed-top" style="background : linear-gradient(179deg, #033d53, #5a98de);">
        <div class="container-fluid cl">
            <a class="logo navbar-logo f-l mr-10 hidden-xs"  href="javascript:void(0);"  style="font-size: 18px; font-weight: 800;"> 
              <img src="${pageContext.request.contextPath }/img/logo.jpg" style="height: 35px;"> 东道创成温测系统
            </a>
            <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
            <nav class="nav navbar-nav">
            	<ul class="cl">
					<li id="time" style="margin-left: 20px;">
						<script language=javascript type=text/javascript>
							function init(){
								var enabled = 0; today = new Date();
								var day;
								if(today.getDay()==0) day = " 星期日"
								if(today.getDay()==1) day = " 星期一"
								if(today.getDay()==2) day = " 星期二"
								if(today.getDay()==3) day = " 星期三"
								if(today.getDay()==4) day = " 星期四"
								if(today.getDay()==5) day = " 星期五"
								if(today.getDay()==6) day = " 星期六"
								var date = new Date(); 
								var year = date.getFullYear(); 
								var month = date.getMonth()+1; 
								if(month<10)month="0"+month; 
								var day1 = date.getDate(); 
								if(day1<10)day1="0"+day1; 
								var hour = date.getHours(); 
								if(hour<10)hour="0"+hour; 
								var minute = date.getMinutes(); 
								if(minute<10)minute="0"+minute; 
								var second = date.getSeconds(); 
								if(second<10)second="0"+second; 
								var dateTime = year+"年"+month+"月"+day1+"日  "+hour+":"+minute+":"+second+" "+day +"";
								$("#time").html(dateTime);
							}
							setInterval('init()',1000);
						</script>
					</li>
				</ul>
            </nav>
            <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class="cl">
                    <li>管理员</li>
                    <li class="dropDown dropDown_hover"><a href="#"
                                                           class="dropDown_A">${username } <i class="Hui-iconfont">&#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a
                                    href="#" id="changepsw">修改密码</a></li>
                            <li><a
                                    href="${pageContext.request.contextPath }/logout.action">退出</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<div class="modal fade" id="chpsw" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">修改密码</h4>
            </div>
            <div class="modal-body">
            	<div class="form-horizontal">
	            	<div style="margin: 3px auto; width: 100%;text-align: center;">
	                	<table>
	                		<tr>
	                			<td style="text-align: right; width: 40%;">旧密码：</td>
	                			<td style="text-align: left;">
	                			<input  type="hidden" value="${username }" id="username" name="username"/>
	                			<input type="password" name="oldpsw" id="oldpsw" style="width:170px" class="input-text"></td>
	                		</tr>
	                		<tr>
	                			<td style="text-align: right;">新密码：</td>
	                			<td  style="text-align: left;"><input type="password" name="newpsw" id="newpsw" style="width:170px" class="input-text"></td>
	                		</tr>
	                		<tr>
	                			<td style="text-align: right;">确认密码：</td>
	                			<td style="text-align: left;"><input type="password" name="rnewpsw" id="rnewpsw" style="width:170px" class="input-text"></td>
	                		</tr>
	                	</table>
	                </div>
            	</div>
            </div>
            <div class="modal-footer">
                <div class="center-block">
                    <button id="confirm" type="button" class="btn btn-success" data-dismiss="modal">确定</button>
                    <button id="cancel" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>
<aside class="Hui-aside" style="background:linear-gradient(174deg, #033d53, #5a98de);color:white;">
    <div class="menu_dropdown bk_2">
        <dl id="menu-comments">
            <dt style="color: white;">
                <i class="Hui-iconfont">&#xe622;</i> 实时采集<i
                    class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
            </dt>
            <dd>
                <ul>
                    <li><a
                            data-href="${pageContext.request.contextPath }/wence/toanylises.action"
                            data-title="实时分析" href="javascript:;" style="color: white;">实时采集</a>
                    </li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-comments">
            <dt style="color: white;">
                <i class="Hui-iconfont">&#xe620;</i> 高级检索<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
            </dt>
            <dd>
                <ul>
                    <li><a
                            data-href="${pageContext.request.contextPath }/wence/tosearch.action"
                            data-title="底库检索" href="javascript:;" style="color: white;">历史检索</a></li>
                    <li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-comments">
            <dt style="color: white;">
                <i class="Hui-iconfont">&#xe620;</i> 参数管理<i
                    class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i>
            </dt>
            <dd>
                <ul>
                    <li><a
                            data-href="${pageContext.request.contextPath }/config/tolist.action"
                            data-title="参数配置" href="javascript:;" style="color: white;">参数配置</a></li>
					<li><a
                            data-href="${pageContext.request.contextPath }/user/tolist.action"
                            data-title="账户管理" href="javascript:;" style="color: white;">账户管理</a></li>
                </ul>
            </dd>
        </dl>
    </div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
    <div id="Hui-tabNav" class="Hui-tabNav hidden-xs"
         style="/* background: linear-gradient(0deg, #033d53, #5a98de); */">
        <div class="Hui-tabNav-wp">
            <ul id="min_title_list" class="acrossTab cl">
                <li class="active">
                    <span title="我的桌面" data-href="welcome.html">系统主页</span>
                    <!-- <i></i> -->
                    <em></em>
                </li>
            </ul>
        </div>
        <div class="Hui-tabNav-more btn-group">
            <a id="js-tabNav-prev" class="btn radius btn-default size-S"
               href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a
                id="js-tabNav-next" class="btn radius btn-default size-S"
                href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a>
        </div>
    </div>
    <div id="iframe_box" class="Hui-article">
        <div class="show_iframe"
            <div style="display: none" class="loading"></div>
            <iframe scrolling="yes" frameborder="0" src="">
            </iframe>
        </div>
    </div>
</section>

<div class="contextMenu" id="Huiadminmenu">
    <ul>
        <li id="closethis">关闭当前</li>
        <li id="closeothers">关闭其他</li>
        <li id="closeall">关闭全部</li>
    </ul>
</div>

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript"
        src="${pageContext.request.contextPath }/js/jquery.contextmenu/jquery.contextmenu.r2.js">
        </script>
</body>
</html>