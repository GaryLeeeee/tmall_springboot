$(function() {
    $("#editForm").submit(function() {
        if (!checkEmpty("name", "产品名称"))
            return false;
//          if (!checkEmpty("subTitle", "小标题"))
//              return false;
        if (!checkNumber("originalPrice", "原价格"))
            return false;
        if (!checkNumber("promotePrice", "优惠价格"))
            return false;
        if (!checkInt("stock", "库存"))
            return false;
        return true;
    });
});
$(function(){
        $('.adminHeader').load("adminHeader");
        $('.adminNavigator').load("adminNavigator");
        //此处需要优化，现在是导航栏的内容晚显示出来
        $.get(
            "getProduct",
            {id: GetQueryString("id")},
            function (data) {
//                        console.log(data);
                // 面包屑导航的中间
                $.get(
                    "getCategory?id="+data.cid,
                    function(result){
                        $("#cname").html(result.name);
                        $('#cname').attr("href","admin_product_list?cid="+data.cid);
                    }
                );
                // 修改value值用val,改文本值用html(a,li等)
                $('#pname').html(data.name);
                $('#name').val(data.name);
                $('#originalPrice').val(data.originalPrice);
                $('#subTitle').val(data.subTitle);
                $('#promotePrice').val(data.promotePrice);
                $('#stock').val(data.stock);
                //隐式
                $('#id').val(data.id);
                $('#cid').val(data.cid);
            }
        )

    }
)
function GetQueryString(name) {//获取url中key为name的value
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

