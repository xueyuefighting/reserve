$(document).ready(function(){
    selfPhone();
    outDatepicker();//加载时间控件
    init_price_html();//加载购买单中产品行
});

function selfPhone(){
    var selfPhone = "18802457688";
    $(".selfPhone_html").text(selfPhone);
}

function outDatepicker(){
    $( ".datepicker" ).datepicker({
        inline: true,
        dateFormat: "yy-mm-dd"
    });
}

function save(){

    $.each($(".form_val"), function (i, v) {
        var $this = $(v);
        if($this.is("input")){
            alert($this.attr("name"));
        }
        if($this.is("select") && $this.hasClass("select2")){
            alert($this.select2("val"));
        }
    });


    return;

    var os = [];
    var o;
    var flag = true;
    $(".stand").find("input.st").each(function (i,v) {
        o = {};
        var thisName = $(v).attr("name");
        var stand = $(v).val();
        var price = $("input[name="+thisName+"_price]").val();
        if(!price){
            alert("请输入"+stand+"规格的单价!");
            flag = false;
            $("input["+thisName+"_price]").focus();
            return false;
        }
        o["standard"] = stand;
        o["price"] = price
        os[i]=o;
    });

    if(!flag){
        return;
    }

    var currentDateLong = new Date($("#datepicker").val()+" 00:00:00".replace(new RegExp("-","gm"),"/")).getTime();     //当前时间转换成long型

    var param = {
        "model":$("#model").val(),
        "totalCount": $("#count").val(),
        "stoneDate": currentDateLong,
        "ios":os};
    $.ajax({
        contentType:"application/json; charset=utf-8",
        data: JSON.stringify(param),
        url:HttpParam.http_prefix+"/in",
        type:"POST",
        cache:false,
        success:function(){
            alert("保存成功");
        },error:function(){
            alert("请求出错，请刷新页面尝试");
        }
    })
}


function obtainsPrice(model, standard){

}

var priceHtml;

function init_price_html(){
    price_html();
}

function price_html(){
    priceHtml = $(".price_html").clone(true);
}

function aquireTr($first) {
    debugger;
    var $this = obtainsPriceHtmlObj();
    buttonHandler($first, $this);
    addEvent($this);
    $first.after($this);
}

/**
 * 处理「添加」「删除」两个按钮
 * @author zhangjinliang
 * @date 2018/10/21 下午7:33
 * @param $first title行
 * @param $this 要添加的行
 * @return
 */
function buttonHandler($first, $this){
    var priceHtmls = $first.parent().find("tr.price_html");
    var priceHtmlLen = priceHtmls.length;

    //如果是第一个价格行，则不要「删除」按钮
    if(priceHtmlLen===0){
        $this.find("input.btn_clik_del").remove();
    }else{
        //把他们的「添加」按钮都拿掉
        $.each(priceHtmls,function (i,v) {
            $(v).find('input.btn_clik_add').remove();
        })
    }
}

/**
 * 给当前对象添加 事件
 * @author zhangjinliang
 * @date 2018/10/21 下午7:40
 * @param 
 * @return 
 */
function addEvent($this){
    //添加搜索下拉框
    initModel($this);
    //「添加」事件
    initBtnAdd($this);
    //「删除」事件
    initBtnDel($this);
}

function initBtnAdd($this) {
    $this.find("input.btn_clik_add").on("click", function(){
        aquireTr(equireFirst());
    });
}

function initBtnDel($this) {
    $this.find("input.btn_clik_del").on("click", removeTr($this));
}

function removeTr($this){
    $this.remove();
}

function obtainsPriceHtmlObj(){
    var html = "<tr class=\"detail_val price_html\">";
        html+="<td>";
        html+="<select name=\"model\" class=\"select2 models_tri form_val\">";
        html+="<option value=\"\" selected=\"selected\">请选择</option>";
        html+="</select>";
        html+="</td>";
        html+="<td>";
        html+="<select name=\"standard\" class=\"select2 standard_tri form_val\">";
        html+="<option value=\"\" selected=\"selected\">请选择</option>";
        html+="</select>";
        html+="</td>";
        html+="<td><input class=\"form_val\" type=\"number\" readonly=\"true\" name=\"price\" value=\"0\"/></td>";
        html+="<td><input class=\"form_val\" type=\"number\" name=\"price_num\" value=\"0\"/></td>";
        html+="<td style=\"    white-space: nowrap;\">";
        html+="<input type=\"number\" readonly=\"true\" name=\"model_price\" value=\"0\"/>";
        html+="<input class=\"btn_clik_add\" type=\"button\" title=\"点击添加一条\" value=\"添加\" style=\"font-size: 1px;margin-top: -11px;margin-left: 20px;\"/>";
        html+="<input class=\"btn_clik_del\" type=\"button\" title=\"点击添加一条\" value=\"删除\" style=\"font-size: 1px;margin-top: -11px;margin-left: 20px;\"/>";
        html+="</td>";
        html+="</tr>";
    return $(html);
}


/**
 * target 整行元素
 * @author zhangjinliang
 * @date 2018/10/21 下午7:04
 * @param
 * @return
 */
function standardTri(target){
    target.find(".standard_tri").on("select2:select",function(){
        var standard_id = $(this).val();
        var model_id = target.find(".models_tri").select2("val");
        var pri = ModelObject.pri(model_id, standard_id);
        $(this).parent().parent().find("input[name=price]").val(pri);
    });
}

/**
 * 复位单行型号的单价，数量和型号
 * @author zhangjinliang
 * @date 2018/10/20 下午5:47
 * @param select2Doc 选中的select2元素
 * @return
 */
function resetModel(select2Doc){
    var pa = select2Doc.parent().parent();
    pa.find("input[name=price]").val(0);
    pa.find("input[name=price_num]").val(0);
    pa.find("input[name=model_price]").val(0);
}

function priceNumChange(){
    $("input[name=price_num]").bind("input propertychange",function () {
        var $num_obj = $(this);
        //计算当前行金额
        fillModelPrice($num_obj);
        //计算总额
        fillTotalPrice();
    });
}

function fillModelPrice(obj){
    var $num_obj =obj;
    var trNum = $num_obj.val();
    var price = $num_obj.parent().parent().find("input[name=price]").val();
    var trPrice = trNum*price;
    $num_obj.parent().parent().find("input[name=model_price]").val(trPrice);

}

function fillTotalPrice(){
    var priceHtmlTrs = $(".total_price_change").find("tr.price_html");
    var totalPrice = 0;
    $.each(priceHtmlTrs, function (i, v) {
        var $this = $(v);
        totalPrice+=parseInt($this.find("input[name=model_price]").val());
    });

    $("#total_price").val(totalPrice);
}

/**
 * @author zhangjinliang
 * @date 2018/10/21 下午7:06
 * @param
 * @return
 */
function userTri() {
    $(".user_tri").select2({
        placeholder: "请选择",
        theme: "classic",
        data: UserObject.id_name()
    });

    $(".user_tri").on("select2:select", function () {
        var user_id = $(this).val();

        var tel = UserObject.tel(user_id);

        $("#userTel").text(tel);

    });
}

/**
 *
 * target 代表一行元素
 * @author zhangjinliang
 * @date 2018/10/21 下午7:01
 * @param
 * @return
 */
function initModel(target){
    target.find(".models_tri").select2({
        placeholder: "请选择",
        theme: "classic",
        data:ModelObject.models()
    });

    target.find(".models_tri").on("select2:select",function(){
        var model_id = $(this).val();

        target.find(".standard_tri").children().not(":eq(0)").remove();

        target.find(".standard_tri").select2({
            placeholder: "请选择",
            theme: "classic",
            data:ModelObject.standards(model_id)
        });

        resetModel($(this));

        standardTri(target);
    });

}

