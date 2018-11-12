function initProductImage() {
    var pid = GetQueryString("pid");
    // 定义面包屑导航中间一栏

}


function GetQueryString(name) {//获取url中key为name的value
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}