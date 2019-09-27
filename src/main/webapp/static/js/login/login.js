$(function(){
    // var $src = "http://192.168.1.201:8080/browser/";
    // var $src = "http://192.168.1.202:8080/dg-back/";
    var $src = "http://localhost:9080/";
    var $rc = "views";
    //关闭弹层
    $(".js_cancel").click(function(){
        $(".popBox,.pop,.rolePopBox").hide();
    });

    document.onkeydown=function(){
        //回车键的键值为 13
        if (event.keyCode == 13){
            login();
        }
    };

    /*登录*/
    $(".loginBtn").click(function(){
        login();
    });

    function login(){
        var data = {
            loginUser : $(".js_userName").val(),
            password:$(".js_passWord").val()
        };
        data = JSON.stringify(data);
        $.ajax({
            url: $src + "login",
            type: "post",
            data: data,
            dataType: "json",
            contentType:'application/json',
            success: function(data){
                if (data.result == "200"){
                    window.localStorage.setItem('url',$src);
                    var data=data.data;
                    window.localStorage.setItem('id',data.id);
                    window.localStorage.setItem('loginUser',data.loginUser);
                    window.localStorage.setItem('token',data.token);
                    window.localStorage.setItem('roleType',data.roleType);
                    window.location.href = $rc + "/index.html"
                }else{
                    bootbox.alert(data.message);
                }
            },
            error: function(){
                bootbox.alert('服务错误，请稍后再试！');
            }
        })
    };
});
