function initProperty(){
    var cid = GetQueryString("cid");
    // 定义面包屑导航中间一栏
    $.get(
        "getCategory?id="+cid,
        function(result){
            $("#cname").html(result.name);
            $('#cname').attr("href","admin_property_list?cid="+cid)
        }
    );
    //渲染出property的tbody
    $.get(
        "listProperty?cid="+cid,
        {start: GetQueryString("start") ? GetQueryString("start") : 1},
        function (data) {
            console.log(data);
            $('#index').attr("href", "?cid="+cid+"&start="+data["firstPage"]);//1
            if (data["hasPreviousPage"] == true)
                $('#previous').attr("href", "?cid="+cid+"&start=" + (data["pageNum"] - 1));
            else
                $('#previous').parent("li").addClass("disabled");
            if (data["hasNextPage"] == true)
                $('#next').attr("href", "?cid="+cid+"&start=" + (data["pageNum"] + 1));
            else
                $('#next').parent("li").addClass("disabled");
            $('#last').attr("href", "?cid="+cid+"&start=" + data["lastPage"]);
            data = data["list"];
            for (var i = 0; i < data.length; i++) {
                // html = "<tr><td>" + data[i].id + "</td><td><img height='40px' src='img/category/" + data[i].id + ".jpg'></td><td>" + data[i].name + "</td>"
                // 图片直接从云存储上加载
                html = "<tr><td>" + data[i].id + "</td><td>" + data[i].name + "</td>"
                    + "<td><a href='admin_property_edit?id=" + data[i].id + "'><span class='glyphicon glyphicon-edit'></span></a></td>"
                    + "<td><a deleteLink='true' href='admin_property_delete?id=" + data[i].id + "'><span class='glyphicon glyphicon-trash'></span></a></td></tr>";
                $('#t').append(html);
            }

        }
    )
}
function GetQueryString(name) {//获取url中key为name的value
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}