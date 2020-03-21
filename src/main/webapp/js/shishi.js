/**
 *
 */

function today() {
    var today = new Date();
    var h = today.getFullYear();
    var m = today.getMonth() + 1;
    var d = today.getDate();
    var hh = today.getHours();
    var mm = today.getMinutes();
    var ss = today.getSeconds();
    m = m < 10 ? "0" + m : m;
    d = d < 10 ? "0" + d : d;
    hh = hh < 10 ? "0" + hh : hh;
    mm = mm < 10 ? "0" + mm : mm;
    ss = ss < 10 ? "0" + ss : ss;
    return h + "" + m + "" + d + "" + hh + "" + mm + "" + ss;
}

function format(time) {
    var h = time.substring(0, 4);
    var m = time.substring(4, 6);
    var d = time.substring(6, 8);
    var hh = time.substring(8, 10);
    var mm = time.substring(10, 12);
    var ss = time.substring(12);

    return h + "-" + m + "-" + d + " " + hh + ":" + mm + ":" + ss;
}

function Init(page,code,starttime,endtime,t_ip,t_num) {
	//alert(t_num);
	$.ajax({
		url: "../wence/search.action",
        type: "post",
        data: {
            page: page,
            code: code,
            starttime: starttime,
            endtime: endtime,
            t_ip: t_ip,
            t_num: t_num
        },
        dataType: "json",
        success: function (data) {
            con = "";
            totalcon = "";
            for (var i = 0; i < data.length; i++) {
                var time1 = format(data[i].go_time);
                con +=
                    '<li class="listbox">' +
                    '<div class="img">' +
                    '<img class="wuji" style="width: 120px;" onclick=\'yulan(\"' + data[i].timagepath + '\",\"' + data[i].capimagepath +'\",\"' + data[i].dir_path +'\",\"' + data[i].cap_ip +'\",\"' + data[i].t_num +'\",\"' + time1+ '\")\' src=' + data[i].capimagepath + ' />' +
                    '<img src=' + data[i].timagepath + ' onclick=\'yulan(\"' + data[i].timagepath + '\",\"' + data[i].capimagepath +'\",\"' + data[i].dir_path +'\",\"' + data[i].cap_ip +'\",\"' + data[i].t_num +'\",\"' + time1 + '\")\' /></div>' +
                    '<ul class="list">' +
                    '<li>通道：' + data[i].dir_path + '</li>' +
                    '<li>红外传感器：'+ data[i].t_ip+'</li>'+
                    '<li>体表温度：' + data[i].t_num + '℃</li>' +
                    '<li>时间：' + time1 + '</li>' +
                    '</ul>' +
                    '</li>';
            }
            console.log(api.getCurrent());
            $("#currentpage").html(1);
            if(data.length != 0){
            	totalData = data[0].total;
            }else{
            	totalData = 1;
            }
            $('.M-box3').pagination({
				//pageCount : data[0].total,
				totalData: totalData,
				showData: 100,
				jump : true,
				coping : true,
				homePage : '首页',
				endPage : '末页',
				prevContent : '上页',
				nextContent : '下页',
				current:1, 
				callback : function(api) {
					console.log(api.getCurrent())
					$("#currentpage").html(api.getCurrent());
					$.ajax({
						url: "../wence/search.action",
				        type: "post",
				        data: {
				            page: api.getCurrent(),
				            code: code,
				            starttime: starttime,
				            endtime: endtime,
				            t_ip: t_ip,
				            t_num: t_num
				        },
				        dataType: "json",
				        success: function (data) {
				            con = "";
				            totalcon = "";
				            for (var i = 0; i < data.length; i++) {
				                var time1 = format(data[i].go_time);
				                con +=
				                	'<li class="listbox">' +
				                    '<div class="img">' +
				                    '<img class="wuji" style="width: 120px;" onclick=\'yulan(\"' + data[i].timagepath + '\",\"' + data[i].capimagepath +'\",\"' + data[i].dir_path +'\",\"' + data[i].cap_ip +'\",\"' + data[i].t_num +'\",\"' + time1+ '\")\' src=' + data[i].capimagepath + ' />' +
				                    '<img src=' + data[i].timagepath + ' onclick=\'yulan(\"' + data[i].timagepath + '\",\"' + data[i].capimagepath +'\",\"' + data[i].dir_path +'\",\"' + data[i].cap_ip +'\",\"' + data[i].t_num +'\",\"' + time1+ '\")\' /></div>' +
				                    '<ul class="list">' +
				                    '<li>通道：' + data[i].dir_path + '</li>' +
				                    '<li>红外传感器：'+ data[i].t_ip+'</li>'+
				                    '<li>体表温度：' + data[i].t_num + '℃</li>' +
				                    '<li>时间：' + time1 + '</li>' +
				                    '</ul>' +
				                    '</li>';
				            }
							$("#know").html(con); //把内容入到这个div中即完成
						}
					});
				}
			});
            $("#know").html(con); //把内容入到这个div中即完成
            if (data.length > 0) {
            	$("#totalcount").html(totalData);
            	//console.log(Math.ceil((totalData)/100));
            	$("#totalpage").html(Math.ceil((totalData)/100));
            }
        }
    });
}
var page = 1;
var code = "";
var starttime = "";
var endtime = "";
var t_ip = "";
var t_num = "";
$(function () {
    var myDate = new Date;
    var year = myDate.getFullYear(); //获取当前年
    var mon = myDate.getMonth() + 1; //获取当前月
    var date = myDate.getDate(); //获取当前日
    var h = myDate.getHours();//获取当前小时数(0-23)
    var m = myDate.getMinutes();//获取当前分钟数(0-59)
    var s = myDate.getSeconds();//获取当前秒
    var endtime = today();//year+''+mon+''+date+''+h+''+m+''+s;
    var starttime = year + '' + mon + '' + date + '000000';
    Init(page,code,starttime,endtime,t_ip,t_num);

});
function dirpathinfo(){
	$.ajax({
		url: "../wence/dirpathinfo.action",
        type: "post",
        data: { },
        dataType: "json",
        success: function (data) {
        	
        }
	});
}
function goform() {
    page = 1;
    var code = $("#code").val();
    var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    var t_ip = $("#t_ip").val();
    var t_num = $("#t_num").val();
    //console.log(t_num);
    Init(page,code,starttime,endtime,t_ip,t_num);
}
function exportexcel(){
    page = 1;
    var code = $("#code").val();
    var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    var t_ip = $("#t_ip").val();
    var t_num = $("#t_num").val();
    location.href="../wence/exportexcel?page="+page+"&code="+code+"&starttime="+starttime+"&endtime="+endtime+"&t_ip="+t_ip+"&t_num="+t_num;
}

//js将后台传过来的json数据分组
function jsonterms(jsonstr) {
    var map = {},
        dest = [];
    for (var i = 0; i < jsonstr.length; i++) {
        var ai = jsonstr[i];
        if (!map[ai.cap]) {
            dest.push({
                cap: ai.cap,
                data: [ai]
            });
            map[ai.cap] = ai;
        } else {
            for (var j = 0; j < dest.length; j++) {
                var dj = dest[j];
                if (dj.cap == ai.cap) {
                    dj.data.push(ai);
                    break;
                }
            }
        }
    }
    //console.log(JSON.stringify(dest));
    return JSON.stringify(dest);
}

function yulan(src, src1, tongdao,cap,num,time) {
    //$("#yulan").attr("src",src);
    $("#yulan1").attr("src", src);
    $("#yulan2").attr("src", src1);
    $("#tongdao").text(tongdao);
    $("#cap").text(cap);
    $("#num").text(num);
    $("#time").text(time);
    $("#datu").modal({
        backdrop: "static",//点击空白处不关闭对话框
        show: true
    });
}
