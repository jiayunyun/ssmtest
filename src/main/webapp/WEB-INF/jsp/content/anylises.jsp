<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../utils/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>实时分析</title>
    <link rel="stylesheet" type="text/css" href="../js/h-ui/css/H-ui.min.css"/>
    <link href="../css/nifty.min.css" rel="stylesheet">
    <script type="text/javascript" src="../js/jquery-2.2.4.min.js"></script>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="../js/nifty.min.js"></script>
    <script type="text/javascript" src="../js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/echarts.min.js"></script>
    <script type="text/javascript" src="../js/echarts-gl.min.js"></script>
    <script type="text/javascript">
        function format(time) {
            var h = time.substring(0, 4);
            var m = time.substring(4, 6);
            var d = time.substring(6, 8);
            var hh = time.substring(8, 10);
            var mm = time.substring(10, 12);
            var ss = time.substring(12);
            return h + "-" + m + "-" + d + " " + hh + ":" + mm + ":" + ss;
        }

        function formatscore(score){
        	return Math.round(score * 10000) / 100;
        }
        $(function () {
            $.ajax({
                url: "${pageContext.request.contextPath }/wence/images.action",
                type: "post",
                success: function (result) {
                    result = JSON.parse(result);
                    bukong = "";
                    $.each(result, function (index, img) {
                        bukong += '<li class="listbox"><div class="img"><img style="width: 120px;" src=' + result[index].capimagepath + ' /><img src=' + result[index].timagepath + '  /></div><ul class="list"><li>通道编号：' + result[index].dir_path + '</li><li>红外传感器：'+result[index].t_ip+'</li><li>体表温度：' + result[index].t_num + '℃</li><li>时间：' + format(result[index].go_time) + '</li></ul></li>';
                    });
                    $("#bukong").html(bukong += "<div style='clear: both;'></div>"); //把内容入到这个div中即完成

                }
            });
            document.onkeydown = grabEvent;
            function grabEvent(e) {
                switch (e.which) {
                    case 27:
                        $(".close").click();
                        return 0;
                        break;
                }
            }
        });
        
        setInterval(function () {
            $.ajax({
                url: "${pageContext.request.contextPath }/wence/images.action",
                type: "post",
                success: function (result) {
                    result = JSON.parse(result);
                    bukong = "";
                    $.each(result, function (index, img) {
                        bukong += '<li class="listbox"><div class="img"><img style="width: 120px;" src=' + result[index].capimagepath + ' /><img src=' + result[index].timagepath + '  /></div><ul class="list"><li>通道编号：' + result[index].dir_path + '</li><li>红外传感器：'+result[index].t_ip+'</li><li>体表温度：' + result[index].t_num + '℃</li><li>时间：' + format(result[index].go_time) + '</li></ul></li>';
                    });
                    $("#bukong").html("<ul>"+bukong +"</ul>"+ "<div style='clear: both;'></div>"); //把内容入到这个div中即完成
                }
            });
        }, 2000);

    </script>
    <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
            text-align: center;
        }
        td {
            font-family: Arial, Helvetica, sans-serif;
        }
        th {
            font-family: Arial, Helvetica, sans-serif;
        }
        div {
            border: grey solid 0px
        }
        #top {
            height: 330px;
            width: 100%;
        }
        #top div {
            border-radius: 13px;
            height: 316px;
            float: left;
            width: 49%;
            padding: 5px;
        }

        ul, p {
            margin: 0;
            padding: 0;
        }

        li {
            list-style: none;

        }

        .box {
            width: 100%;
        }

        #bukong .img {
            float: left;
        }

        #bukong .img img {
            width: 64px;
            height: 72px;
        }

        #bukong .list {
            float: left;
            width: 158px;
            margin-left: 5px;
            display: inline;
            text-align: left;
        }

        #bukong .list li {
            line-height: 24px;
            font-size: 12px;
        }

        .list li span {
            font-weight: bold;
        }

        .listbox {
            float: left;
            width: 191px;
            border: solid 1px #E7EAED;
        }
    </style>
</head>
<body>
<div id="content-container" style="padding-top: 10px;">
    <div class="col-sm-12">
        <div class="panel">
            <div class="panel-body">
                <div class="table-responsive" style="height: auto;">
                    <div style="width: 100%; height: 100%; float: right; border: solid 1px; overflow: auto;    text-align: right;">
                        <div id="bukong" style="width: 100%; border-bottom: solid 1px;">
                        	<!-- <ul>
								<li class="listbox">
									<div class="img">
										<img style="width: 120px;" src="/diku/images/history/2019/11/13/12/25/2020.02.11.00.29.46-src-36.2.jpg">
										<img src="/diku/images/history/2019/11/13/12/25/2020.02.11.00.29.46-dst-36.2.jpg">
									</div>
									<ul class="list"><li>通道编号：G5A-1</li><li>摄像机：192.168.23.1"</li><li>温度：36.7</li><li>时间：2019-02-15 09:58:56</li>
									</ul>
								</li>
								<li class="listbox">
									<div class="img">
										<img style="width: 120px;" src="/diku/images/history/2019/11/13/12/25/2020.02.11.00.29.46-src-36.2.jpg">
										<img src="/diku/images/history/2019/11/13/12/25/2020.02.11.00.29.46-dst-36.2.jpg">
									</div>
									<ul class="list"><li>通道编号：G5A-1</li><li>摄像机：192.168.23.1"</li><li>温度：36.7</li><li>时间：2019-02-15 09:58:56</li>
									</ul>
								</li>
							</ul> -->
						</div>
                    </div>
                </div>
            </div>
        </div>
     </div>
</div>
</body>
</html>