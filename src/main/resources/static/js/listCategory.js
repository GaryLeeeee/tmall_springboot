function initCategory() {
    $.get(
        "listCategory",
        {start: GetQueryString("start") ? GetQueryString("start") : 1},
        function (data) {
            console.log(data);
            //如果有上一页则上一页及首页按钮可选
            //有则赋超链href值,无则直接添加不可选class样式
            if (data["hasPreviousPage"] == true) {
                $('#previous').attr("href", "?start=" + (data["pageNum"] - 1));
                $('#index').attr("href", "?start=" + data["firstPage"]);//1
            }
            else {
                $('#previous').parent("li").addClass("disabled");
                $('#index').parent("li").addClass("disabled");
            }
            //同理
            if (data["hasNextPage"] == true) {
                $('#next').attr("href", "?start=" + (data["pageNum"] + 1));
                $('#last').attr("href", "?start=" + data["lastPage"]);
            }
            else {
                $('#next').parent("li").addClass("disabled");
                $('#last').parent("li").addClass("disabled");
            }

            data = data["list"];
            console.log("list:"+data);
            for (var i = 0; i < data.length; i++) {
                // html = "<tr><td>" + data[i].id + "</td><td><img height='40px' src='img/category/" + data[i].id + ".jpg'></td><td>" + data[i].name + "</td>"
                // 图片直接从云存储上加载
                // html = "<tr><td>" + data[i].id + "</td><td><img height='40px' src='http://p8iu5y6va.bkt.clouddn.com/" + data[i].id + ".jpg'></td><td>" + data[i].name + "</td>"
                html = "<tr><td>" + data[i].id + "</td><td><img height='40px' src='img/category/" + data[i].id + ".jpg'></td><td>" + data[i].name + "</td>"
                    + "<td><a href='admin_property_list?cid=" + data[i].id + "'><span class='glyphicon glyphicon-th-list'></span></a></td>"
                    + "<td><a href='admin_product_list?cid=" + data[i].id + "'><span class='glyphicon glyphicon-shopping-cart'></span></a></td>"
                    + "<td><a href='admin_category_edit?id=" + data[i].id + "'><span class='glyphicon glyphicon-edit'></span></a></td>"
                    + "<td><a deleteLink='true' href='admin_category_delete?id=" + data[i].id + "'><span class='glyphicon glyphicon-trash'></span></a></td></tr>";
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