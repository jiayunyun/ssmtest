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

function checkdiku(usercode, isbukong) {//查看底库
    $.ajax({
        url: "../faces/checkdikubyusercode.action",
        type: "post",
        data: {
            usercode: usercode,
            isbukong: isbukong
        },
        success: function (result) {
            con = "";
            for (var i = 0; i < result.length; i++) {
                //alert(result[i].path);
                con += "<li class='photos'><div class='img'><img src=" + result[i].path + " width='160' height='160'><div class='desc'>" + result[i].name + "</div></div></li></div></ul></div>"
            }
            $("#photos").html(con);
        }
    });
    $("#checkdiku").modal({
        backdrop: "static",//点击空白处不关闭对话框
        show: true
    });
}
function Init(page,starttime,endtime,cap) {
	$.ajax({
        url: "../car/videolist.action",
        type: "post",
        data: {
            page: page,
            starttime: starttime,
            endtime: endtime,
            cap: cap
        },
        dataType: "json",
        success: function (data) {
            con = "";
            totalcon = "";
            console.log(data);
            for (var i = 0; i < data.length; i++) {
                con +=
                    '<li class="listbox">' +
                    '<div class="img">' +
                    '<img class="wuji" onclick=\'yulan(\"'+ data[i].fivedirpath + '\")\' src=' + data[i].fivedirpath + '  />' +
                    '</div>' +
                    '</li>';
            }
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
						url: "../car/videolist.action",
				        type: "post",
				        data: {
				            page: api.getCurrent(),
				            starttime: starttime,
				            endtime: endtime,
				            cap: cap
				        },
						dataType : "json",
						success : function(data) {
							con = "";
							totalcon = "";
							for (var i = 0; i < data.length; i++) {
								con +=
				                    '<li class="listbox">' +
				                    '<div class="img">' +
				                    '<img class="wuji" onclick=\'yulan(\"'+ data[i].fivedirpath + '\")\' src=' + data[i].fivedirpath + '  />' +
				                    '</div>' +
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
            	console.log(Math.ceil((totalData)/100));
            	$("#totalpage").html(Math.ceil((totalData)/100));
            }
        }
    });
}
var page = 1;
var starttime = "";
var endtime = "";
var cap = "";
$(function () {
    var myDate = new Date;
    var year = myDate.getFullYear(); //获取当前年
    var mon = myDate.getMonth() + 1; //获取当前月
    var date = myDate.getDate(); //获取当前日
    var h = myDate.getHours();//获取当前小时数(0-23)
    var m = myDate.getMinutes();//获取当前分钟数(0-59)
    var s = myDate.getSeconds();//获取当前秒
    mon = mon < 10 ? "0" + mon : mon;
    date = date < 10 ? "0" + date : date;
    var endtime = today();//year+''+mon+''+date+''+h+''+m+''+s;
    var starttime = year + '' + mon + '' + date + '000000';
    Init(page,starttime,endtime,cap)

});

function goform() {
    page = 1;
    var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    var cap = $("#cap").val();
    Init(page,starttime,endtime,cap)
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

// 轨迹展示
function guiji(plate, time) {
    var h = time.substring(0, 4);
    var m = time.substring(4, 6);
    var d = time.substring(6, 8);
    var hh = "23";
    var mm = "59";
    var ss = "59";
    var endtime = h + m + d + hh + mm + ss;
    $.ajax({
        url: "../car/checkguijibyplate.action",
        type: "post",
        data: {
            plate: plate,
            starttime: time,
            endtime: endtime
        },
        success: function (result) {
            con = "";
            var jsonresult = jsonterms(result);
            //alert(jsonresult);
            var dataObj = eval("(" + jsonresult + ")");//转换为json对象
            //alert(dataObj.length);
            var jsondata = dataObj[0].data;
            //console.log(jsondata.length);

            for (var i = 0; i < dataObj.length; i++) {
                var data = dataObj[i].data;
                //for(var j =0;j<data.length;j++){
                con += "<li class='photos'><div class='img'><img src=" + data[0].fivedirpath + " width='160' height='160'><div class='desc'>" + data[0].cap + "-" + format(data[0].time) + "</div></div></li></div></ul></div>"
                //}
            }
            $("#guijiphotos").html(con);
        }
    });
    $("#guijizhanshi").modal({
        backdrop: "static",//点击空白处不关闭对话框
        show: true
    });
}

function yulan(src) {
	console.log(src);
    $("#yulan").attr("src", src);
    $("#yulan").css({width: "100%"});
    $("#datu").modal({
        backdrop: "static",//点击空白处不关闭对话框
        show: true
    });
}