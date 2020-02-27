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
    $.ajax({
        url: "../judg/attendancelist.action",
        type: "post",
        data: {},
        dataType: "json",
        success: function (data) {
            con = "";
            totalcon = "";
            for (var i = 0; i < data.length; i++) {
                var time1 = format(data[i].time);
                con +=
                    '<tr><td>'+data[i].cos_usr_code+'</td><td>'+data[i].ryxm+'</td><td>'+data[i].cap+'</td><td>'+ time1 +'</td><td>'+data[i].type+'</td></tr>';
            }
            $("#attendancetable tbody").html(con); //把内容入到这个div中即完成
        }
    });

});

function goform() {
    var ryxm = $("#ryxm").val();
    var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    var cap = $("#cap").val();
    var type = $("#type").val();
    var usercode = $("#usercode").val();
    $.ajax({
    	url: "../judg/attendancelist.action",
        type: "post",
        data: {
            ryxm: ryxm,
            starttime: starttime,
            endtime: endtime,
            cap: cap,
            type: type,
            usercode: usercode
        },
        dataType: "json",
        success: function (data) {
            con = "";
            totalcon = "";
            for (var i = 0; i < data.length; i++) {
                var time1 = format(data[i].time);
                con +=
                	'<tr><td>'+data[i].cos_usr_code+'</td><td>'+data[i].ryxm+'</td><td>'+data[i].cap+'</td><td>'+time1+'</td><td>'+data[i].type+'</td></tr>';;
            }
            $("#attendancetable tbody").html(con); //把内容入到这个div中即完成
        }
    });
}