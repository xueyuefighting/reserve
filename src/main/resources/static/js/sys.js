
$(document).ready(function(){
    initHtml();
    // $(".footer-content").load("../footer");
    initModels();
    initUsers();
});

// noinspection JSAnnotator

//存放型号-规格-单价的array
var modelobject = [];
//初始化用户
var user_object = [];

//初始化
function initModels(){

    if(modelobject && modelobject.length>0){return;}

    refreshModels();
}

//刷新
function refreshModels() {
    $.ajax({
        contentType:"application/json; charset=utf-8",
        url:HttpParam.http_prefix+"/in/allList",
        type:"GET",
        cache:false,
        success:function(data){
            modelobject = JSON.parse(data);
        },error:function(){
            alert("请求出错，请刷新页面尝试");
        }
    });
}

function initUsers(){

    if(user_object && user_object.length>0){return;}

    refreshUsers();
}

//刷新
function refreshUsers() {
    $.ajax({
        contentType:"application/json; charset=utf-8",
        url:HttpParam.http_prefix+"/user/allList",
        type:"GET",
        cache:false,
        success:function(data){
            user_object = data;
        },error:function(){
            alert("请求出错，请刷新页面尝试");
        }
    });
}

var UserObject = {
    id_name:function(){
        if(!user_object || user_object.length<=0){initUsers();}
        var array = [];
        $.each(user_object, function (i, v) {
            var obj = {};
            obj.id = v.id;
            obj.text = v.name;
            array[i] = obj;
        });
        return array;
    },
    tel:function (id) {
        if(!user_object || user_object.length<=0){initUsers();}
        var user = user_object.find(x => x.id == id);
        return user.tel;
    }
}

var ModelObject = {
    models:function(){
        if(!modelobject || modelobject.length<=0){initModels();}
        var array = [];
        $.each(modelobject, function (i, v) {
            var obj = {};
            obj.id = v.id;
            obj.text = v.model;
            array[i] = obj;
        });
        return array;
    },
    standards:function(model_id){
        if(!modelobject || modelobject.length<=0){initModels();}
        var obj_model = modelobject.find(x => x.id == model_id);
        var ios = obj_model["ios"];
        var array = [];
        $.each(ios, function (i, v) {
            var obj = {};
            obj.id = v.id;
            obj.text = v.standard;
            array[i] = obj;
        });
        return array;
    },
    pri:function (model_id, standard_id) {
        if(!modelobject || modelobject.length<=0){initModels();}
        var obj_standard = modelobject.find(x => x.id == model_id);
        var ios = obj_standard["ios"];
        var p = ios.find(x => x.id == standard_id);
        return p.price;
    }
}

function initHtml(){
    $(".header").load("../header");
    $(".body-content").load("../hello");
}

function loadBody(url){
    $(".body-content", window.parent.document).load(url);
}

function topstory(){
    $(".body-content").load("../hello");
}

function userList(){
    $(".body-content").load("../user/list?page=0");
}

function createUser(){
    $(".body-content").load("../user/new");
}

function outOrderList(){
    $(".body-content").load("../out/list");
}

function createOutOrder(){
    $(".body-content").load("../out/new");
}

function inOrderList(){
    $(".body-content").load("../in/list");
}

function createInOrder(){
    $(".body-content").load("../in/new");
}
