function formatem(em){
	switch(em){
	case "angry":
        return "生气";
        break;
    case "sad":
        return "悲伤";
        break;
    case "neutral":
        return "正常";
        break;
    case "happy":
        return "高兴";
        break;
    case "fear":
        return "害怕";
        break;
    case "disgust":
        return "厌恶";
        break;
    case "surprise":
        return "惊喜";
        break;
    default:
    	return "未启用";
		
	}
}
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