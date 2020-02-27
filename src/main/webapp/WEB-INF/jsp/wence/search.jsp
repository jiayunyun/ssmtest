<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>报警页面</title>
    <link rel="stylesheet" type="text/css" href="../js/h-ui/css/H-ui.min.css"/>
    <script type="text/javascript" src="../js/jquery-2.2.4.min.js"></script>
    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <script src="../js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/jquery-confirm.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/jquery-confirm.css"/>
    <link rel="stylesheet" type="text/css" href="../css/pagination.css" media="screen">
    <script type="text/javascript" src="../js/My97DatePicker/4.8/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/jquery.dataTables.min.css">
    <script src="../js/jquery.dataTables.js"></script>
    <script src="../js/jq.mousewheel.js"></script>
    <script src="../js/shishi.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/zzsc.css"/>
    <link rel='stylesheet' href='../css/jquery.cardtabs.css'/>
    <link rel='stylesheet' href='../css/cardtabsdemo.css'/>
    <script src="../js/layer.js"></script>
</head>

<style>
    body {
        font-family: Arial, Helvetica, sans-serif;
        text-align: center;
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

    #know .img {
        float: left;
    }

    #know .img img {
        width: 64px;
        height: 80px;
    }

    #know .list {
        float: left;
        width: 184px;
        margin-left: 10px;
        display: inline;
        text-align: left;
    }

    #know .list li {
        line-height: 24px;
        font-size: 14px;
    }

    .list li span {
        font-weight: bold;
    }

    .listbox {
        float: left;
        width: 191px;
        border: solid 1px #E7EAED;
        position: relative;
        margin: 0px 0px 5px 5px;
    }

    .check {
        position: absolute;
        top: 1px;
        right: 4px;
    }

    .photos {
        margin: 10px;
    }

    .photos .img {
        margin: 3px;
        border: 1px solid #bebebe;
        height: auto;
        width: auto;
        float: left;
        text-align: center;
    }

    .photos .img img {
        display: inline;
        margin: 3px;
        border: 1px solid #bebebe;
    }

    .photos .img:hover img {
        border: 1px solid #333333;
    }

    div.desc {
        text-align: center;
        font-weight: normal;
        width: 150px;
        font-size: 12px;
        margin: 10px 5px 10px 5px;
        word-break: break-word;
    }

</style>
</head>
<body>
<article class="cl pd-20">
    <div class="text-c" style="text-align: left;">
        <div class='example'>
            <div class='tabsholder'>
                <div data-tab="高级查询">
                    <table>
                        <tr>
                            <td style="width:10%;">通道:</td>
                            <td style="width:15%;">
								<input type="text" name="code" id="code" placeholder=" 请输入通道"
                                                          style="width:170px" class="input-text">
                                <!-- <span class="select-box inline">
								 <select name="code" id="code" class="select" style="width: 180px;">
									<option value="">请选择</option>
									
								</select>
							</span> -->
                            </td>
                            <td style="width:10%;">红外线传感器IP:</td>
                            <td style="width:15%;"><input type="text" name="t_ip" id="t_ip"
                                                          placeholder=" 请输入红外线传感器IP" style="width:180px"
                                                          class="input-text"></td>
                            <td style="width:10%;">温度:</td>
                            <td style="width:15%;">
								>=<input type="text" name="t_num" id="t_num"
                                                          placeholder=" 请输入温度" style="width:170px"
                                                          class="input-text">℃</td>
                        </tr>
                        <tr>
                            
                            <td style="width:10%;">开始时间:</td>
                            <td style="width:15%;"><input type="text" name="starttime" id="starttime"
                                                          onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'starttime\')||\'%y%M%d%H%m%s\'}'})"
                                                          class="input-text Wdate" style="width:170px;"
                                                          ></td>
                            <td style="width:10%;">结束时间:</td>
                            <td style="width:15%;"><input type="text" name="endtime" id="endtime"
                                                          onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'endtime\')}',maxDate:'%y%M%d%H%m%s'})"
                                                          class="input-text Wdate" style="width:170px;"
                                                          ></td>
                            <td>
                                <button name="" id="go" class="btn btn-success" onclick="goform();"> 查询</button>
                                <button name="" id="export" class="btn btn-success" onclick="exportexcel();"> 导出</button>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</article>
<input type="hidden" id="usercode" name="usercode">
<div id="know" style="width: 100%; margin: 0px 0px 13px 30px;float: left;"></div>
<div style="    float: left; line-height: 38px;  margin-left: 30px;">
	共<span id="totalcount">0</span>个结果，第<span id="currentpage">1</span>页/共<span id="totalpage">0</span>页
</div>
<div class="m-style M-box3" style="margin-left: 30px;"></div>
<script type="text/javascript" src="../js/jquery.pagination.js"></script>
<div class="modal fade" id="datu" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">大图预览</h4>
            </div>
            <div class="modal-footer">
                <div id="photos" class="photos-demo">
                	<div style="text-align: left;font-weight: 900; margin-left: 13px;">
                		通道：<span id="tongdao"></span>
                		摄像机：<span id="cap"></span>
                		体表温度：<span id="num"></span>
                		时间：<span id="time"></span>
                	</div>
                    <ul>
                        <li class="photos">
                            <div class="img">
                                <img id="yulan2" style="width: 380px;height: 380px;"/>
                            </div>
                        </li>
                        <li class="photos">
                            <div class="img">
                                <img id="yulan1" style="width: 380px;height: 380px;"/>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script type='text/javascript' src='../js/jquery.cardtabs.js'></script>
<script type="text/javascript">
    $('.tabsholder').cardTabs({theme: 'graygreen'});
    $(function () {
        //补0操作,当时间数据小于10的时候，给该数据前面加一个0
        function getzf(num) {
            if (parseInt(num) < 10) {
                num = '0' + num;
            }
            return num;
        }
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
</script>
</html>