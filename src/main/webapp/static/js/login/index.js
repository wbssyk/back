/**
 * Created by jie.yang on 2019/7/1.
 */
$(function () {
    var $src = window.localStorage.getItem('url');
    var id = window.localStorage.getItem('id');
    var token = window.localStorage.getItem('token');
    var $src1 = "../";//假数据
    if(!token){
        window.location.href=$src1+'login.html';
    }
        var loginUser = window.localStorage.getItem('loginUser');
        $("#dropdownUser").text(loginUser);
        $('#logout').on('click',function () {
            var logoutBox = bootbox.confirm({
                size: "small",
                message: "确认退出系统?",
                callback: function(result){
                    if(result === true) {
                        $.ajax({
                            type: "get",
                            url: $src + "logout",
                            dataType: "json",
                            beforeSend: function(request) {
                                 request.setRequestHeader("Authorization", token);
                            },
                            contentType:'application/json',
                            success: function (data) {
                                if(data.result==200){
                                    //退出时清空所有缓存数据
                                    //window.localStorage.clear();
                                    window.location.href=$src1+'login.html';
                                }if(data.result==300){
                                    //退出时清空所有缓存数据
                                    //window.localStorage.clear();
                                    window.location.href=$src1+'login.html';
                                }else{
                                    $(logoutBox).remove();
                                    $('.modal-backdrop').remove();
                                    bootbox.alert('访问异常！');
                                    return false;
                                }
                            },
                            error: function(){
                                bootbox.alert('未登录，请先登录！');
                            }
                        })
                    }else {
                        $(logoutBox).remove();
                        $('.modal-backdrop').remove();
                        return false;
                    }
                }
            })
        });

        $('#rePwd').on('click',function () {
            var rePwdBox = bootbox.confirm({
                size: "small",
               message: '<div class="box" style="margin-top: 10px;"><span>&nbsp;&nbsp;&nbsp;<span style="color:red;">*</span>旧密码</span><input type="password" class="oldPwd"/></div>'+
            '<div class="box"><span>&nbsp;&nbsp;&nbsp;<span style="color:red;">*</span>新密码</span><input class="newPwd" type="password" /></div>'+
            '<div class="box"><span style="color:red;">*</span><span>确认密码</span><input class="rePwd" type="password" /></div>',
                callback: function(result){
                    if(result === true) {
                        if($(rePwdBox).find('.rePwd').val() !== $(rePwdBox).find('.newPwd').val()){
                            bootbox.alert('两次密码输入不一致！');
                            return false;
                        }
                        if($(rePwdBox).find('.newPwd').val()==""||$(rePwdBox).find('.oldPwd').val()==""||$(".rePwd").find('.oldPwd').val()==""){
                            bootbox.alert('密码不能为空！');
                            return false;
                        }
                        var data = {
                            password:$(rePwdBox).find('.newPwd').val(),
                            oldPassword:$(rePwdBox).find('.oldPwd').val(),
                            loginUser:loginUser,
                            id:id,
                            up:1
                        };
                        data = JSON.stringify(data);
                        $.ajax({
                            type: "post",
                            url: $src + "user/update",
                            data: data,
                            dataType: "json",
                            contentType:'application/json',
                            beforeSend: function(request) {
                                request.setRequestHeader("Authorization", token);
                            },
                            success: function (data) {
                                if(data.result==200){
                                    $(rePwdBox).remove();
                                    $(rePwdBox).remove();
                                    $('.modal-backdrop').remove();
                                    window.location.href=$src1+'login.html';
                                }else if(data.result==300){
                                    //退出时清空所有缓存数据
                                    //window.localStorage.clear();
                                    window.location.href=$src1+'login.html';
                                }else{
                                    $(rePwdBox).remove();
                                    $(rePwdBox).remove();
                                    bootbox.alert(data.message);
                                    $('.modal-backdrop').remove();

                                    return false;
                                }
                            },
                            error: function(){
                                $(rePwdBox).remove();
                                $(rePwdBox).remove();
                                $('.modal-backdrop').remove();
                                bootbox.alert('未登录，请先登录！');
                                window.location.href=$src1+'login.html';
                            }
                        })
                    }else {
                        $(rePwdBox).remove();
                        $('.modal-backdrop').remove();
                        return false;
                    }
                }
            })
        });
        //默认展示
        //$("iframe").attr("src",'http://localhost:63342/webBanks/views/queryService/query.html');
        //$("iframe").attr("src",'http://localhost:63342/webBanks/views/systemManagement/userManagement.html');
        //console.log(id)
        var data = {userId:id};
        data = JSON.stringify(data);
        $.ajax({
            type: "POST",
            url: $src+"resource/left_data",
            data: data,
            dataType: "json",
            contentType:'application/json',
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", token);
            },
            success: function(data){
                if(data.result=='200') {
                    var str='';
                    $('.menu').html('');
                    var d=data.data;
                    $("iframe").attr("src",d[0].children[0].htmlUrl);
                    var s = 0;
                    for (var i = 0; i < d.length; i++) {
                        if(d[i].iconName=='icon1'){
                            d[i].iconName = 'glyphicon glyphicon-cog';
                        }else if(d[i].iconName=='icon2'){
                            d[i].iconName = 'glyphicon glyphicon-th-list';
                        }else if(d[i].iconName=='icon3'){
                            d[i].iconName = 'glyphicon glyphicon-list-alt';
                        }else if(d[i].iconName=='icon4'){
                            d[i].iconName = 'glyphicon glyphicon-wrench';//glyphicon glyphicon-th-list
                        }else if(d[i].iconName=='icon10'){
                            d[i].iconName = 'glyphicon glyphicon-facetime-video';//glyphicon glyphicon-th-list
                        }else if(d[i].iconName=='icon11'){
                            d[i].iconName = 'glyphicon glyphicon-bell';//glyphicon glyphicon-th-list
                        }else{
                            d[i].iconName = 'glyphicon glyphicon-tasks';
                        }
                        str += '<li class="level1">' +
                            /*'<a class="firstLevel" href="#none"><em class="ico ico'+(i+1)+'"></em>'+d[i].name+'<i class="down iStyle">+</i></a>';*/
                           /* '<a class="firstLevel" href="#none"><em class="ico '+d[i].iconName+'"></em>'+d[i].name+'<i class="down iStyle">+</i></a>';*/
                            '<a class="firstLevel" href="#none"><em class="ico '+d[i].iconName+'"></em>'+d[i].name+'<i class="down iStyle">+</i></a>';

                        if (d[i].children && d[i].children.length > 0) {
                            var child=d[i].children;
                            str+='<ul class="level2">';
                            for (var k = 0; k < d[i].children.length; k++) {
                                s = s+1;
                                str+='<li data-src="'+child[k].htmlUrl+'"><a class="secondLevel" href="javascript:;">'+child[k].resourceName+'</a></li>';
                            }
                            str+='</ul>';
                        }
                        str+='</li>';
                    }
                    $('.menu').html(str);
                    // var texts = '';
                    $('.menu .level1').eq(0).addClass('current').find('.level2').show();
                    $('.menu .level1 a').eq(0).addClass('current');
                    $('.menu .level2 a').eq(0).addClass('currentEj');
                    $('.menu .level1:first-child a').find('i').text('-').addClass('activeA');
                    $('.menu .level1:first-child a').find('em').removeClass('icon4').addClass('icon7');
                    $('.menu .level2').on('click','li',function () {
                        var sId = $(this).attr("data-src"); //获取data-id的值
                        $(this).find('a').addClass('currentEj').parent().siblings().find('a').removeClass('currentEj');
                        $(this).find('a').addClass('currentEj').parent().parent().parent().siblings().find('a').removeClass('currentEj');
                        $("iframe").attr("src",sId);

                    });



                }else if(data.result==300){
                    //bootbox.alert('未登录!');
                    //退出时清空所有缓存数据
                    window.location.href=$src1+'login.html';
                }
            },
            error:function(xhr){
            }
        });
        $(".amazonmenu").on("click",".level1>a",function(){
           /* var arr = $(this).text().split("-");*/
            $(this).addClass('current')   //给当前元素添加"current"样式
                .find('i').text('-').addClass('activeA')  //小箭头向下样式
                .parent().next().slideDown('slow','easeOutQuad')  //下一个元素显示
                .parent().siblings().children('a').removeClass('current')//父元素的兄弟元素的子元素去除"current"样式
                .find('i').text('+').removeClass('activeA').parent().next().slideUp('slow','easeOutQuad');//隐藏
            return false; //阻止默认事件
        });

});