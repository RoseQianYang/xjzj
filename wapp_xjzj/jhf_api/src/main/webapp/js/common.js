/*!
 * @huangling
 * 20170613
 * 公共函数文件
 */


//获取浏览器地址中参数
function GetQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r!=null) return (r[2]); return null; 
} 

//计算月份差
function addmulMonth(dtstr,n){   //n个月后 
   var s=dtstr.split("-");
   var yy=parseInt(s[0]), mm=parseInt(s[1]-1), d=1;
   var dt=new Date(yy,mm,d);
   dt.setMonth(dt.getMonth()+n);
  
   var year = dt.getFullYear();
   var month = dt.getMonth()+1;
   if(month<10){
	  month = 0+""+month;
   }
   var dd = year+""+month;
   return dd;
} 

//获取开始年月结束年月之间的月份
function getMonthsum(date1,date2) {
    var year1 =  date1.substr(0,4);
    var year2 =  date2.substr(0,4); 
    var month1 = date1.substr(4,2);
    var month2 = date2.substr(4,2); 
    var len=(year2-year1)*12+(month2-month1)+1; 
    return len;
}

//图片名称处理
function pathOrg(src) {
	return path(src, "_o");
}
function pathBig(src) {
	return path(src, "_b");
}
function pathMiddle(src) {
	return path(src, "_m");
}	
function pathSquare(src) {
	return path(src, "_sq");
}
function pathSmall(src) {
	return path(src, "_s");
}
function path(src, append) {
	if (src == undefined) {
		return "";
	}
	var pos = src.lastIndexOf(".");
	if (pos <= 0) {
		return src;
	}
	var str = "";
	str += src.substring(0, pos);
	str += append;
	str += src.substring(pos);
	return str;
}

/**input得到focus加绿色下边框**/
$(".input-no-border").focus(function(){
	$(".form-group").removeClass("form-group-borderb");
	$(this).parent(".form-group").addClass("form-group-borderb");
})
/**头部js****/
var ctxPath = "${ctxPath}";
function callAction(url, onSuccess) {
	var data = {
		page: page
	};
	$("#load-data #loading").show();
	$("#load-data #load-more").hide();
	jQuery.ajax({
		url:url,
		type: 'post',
		dataType:'json',
		cache:false,
		timeout: 100000,
		error: function(ret){
			alert("加载数据失败!");
		},
		success: function(ret){
			$("#load-data #loading").hide();
			$("#load-data #load-more").show();
			if (onSuccess) {
				onSuccess(ret);
			}
		},
		data: data
	});
}

function getData(url, onSuccess, showWage) {
	if(showWage){
		showFlashMsg(progress);
	}
	jQuery.ajax({
		url:url,
		type: 'post',
		dataType:'json',
		cache:false,
		timeout: 30000,
		error: function(ret){
			if(showWage){
				closeFlashMsg();
			}
			alert("加载数据失败!");
		},
		success: function(ret){
			if(showWage){
				closeFlashMsg();
			}
			if (onSuccess) {
				onSuccess(ret);
			}
		}
	});
}

var progress = '<span id="loadingDiv"><img src="'+ ctxPath +'/img/api/loading.gif" align="absmiddle"/> 正在加载中...</span>';
function ajaxMobileForm(id, onSuccess) {
	var form = "#"+id;
	var uri = $(form).attr("action");
	var options = {
		type: 'post',
		url: uri,
		dataType:'json',
		timeout: 30000,
		beforeSubmit: function(){
			showFlashMsg(progress);
		},
		success: function(ret){
			closeFlashMsg();
			if (onSuccess) {
				onSuccess(ret);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			closeFlashMsg();
			alert(XMLHttpRequest.readyState);
			alert(XMLHttpRequest.status);
			alert(textStatus);
			alert("服务器错误，请稍候访问！");
			return false;
		}
	};
	jQuery(form).ajaxSubmit(options);
	return false;
}

function showFlashMsg(str){
	jQuery("#flash-layer").show();
	jQuery("#flash-msg").html(str).show();
}
function closeFlashMsg(){
	jQuery('#flash-msg,#flash-layer').html('').hide();
}


function left(str, len) {
	if (str == undefined) {
		return "";
	}
	str = str.replace(/(^\s*)|(\s*$)/g, "");

    if (str.length > len) {
      str = str.substring(0,len);
	  str += "…";
    }
	return str;
}

/**
 * 存入cookie
 * @param cookieName
 * @param cookieValue
 */
function setCookie(cookieName, cookieValue) {
    
    var Days = 7;  
    var exp = new Date();  
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);//过期时间一个星期分钟  
    document.cookie = cookieName + "=" + escape(cookieValue) + ";expires=" + exp.toGMTString()+";path=/"; 
}

/**
 * 获取cookie
 */
function getCookie(name) {
    
    var cookies = document.cookie.split(";");
    for(var i = 0; i < cookies.length; i++){
            var cookieCrumbs = cookies[i].split("=");
            var cookieName = cookieCrumbs[0];
            var cookieValue = cookieCrumbs[1];
            if (cookieName == name){
                return cookieValue;
            }
    }    
    return null;
}

