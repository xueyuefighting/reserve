
$(document).ready(function(){

    // $(".footer-content").load("../footer");
});

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
    $(".body-content").load("../outOrder/list");
}

function createOutOrder(){
    $(".body-content").load("../outOrder/new");
}

function inOrderList(){
    $(".body-content").load("../in/list");
}

function createInOrder(){
    $(".body-content").load("../in/new");
}
