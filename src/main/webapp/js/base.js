/**
* 判断ajax请求返回的code值
* @param object obj ajax返回的json对象
* @return boolen true代表成功，false代表失败
* @author zhaolei
* @date 2015年8月4日
*/
function checkCode( obj ) {
	if ( obj.code != 0 ) {
		alertLayel(obj.message);
		return false;
	} else {
		return true;
	}
}
