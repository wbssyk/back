$(function(){
    var $src = window.localStorage.getItem('url');
    var token = window.localStorage.getItem('token');
    var loginUser = window.localStorage.getItem('loginUser');
    var $rc = "../../views";
    var $src1 = "../../";//假数据//假数据//http://localhost:63342/webBanks/
    var selectStr = '';
    var h = 0;
    resize();

    var $listGrid=$('#detailGrid1');
    $listGrid.bootstrapTable({
        url: $src+"user/list",
        height: h,
        method: "post",
        cache: false, // 设置为 false 禁用 AJAX 数据缓存， 默认为true
        striped: true,  //表格显示条纹，默认为false
        pagination: true, // 在表格底部显示分页组件，默认false
        toolbarAlign:'left',
        buttonsAlign:'right',
        sortable:'false',
        smartDisplay:false,
        contentType:'application/json',
        dataType:'json',
        ajaxOptions:{
            headers:{"Authorization":token}
        },
        queryParams: queryParams,//传递参数（*）
        showHeader:true,
        singleSelect:true,
        offset: 10, // 页面数据条数
        pageNumber: 1, // 首页页码
        pageList: [10, 20,30], // 设置页面可以显示的数据条数
        uniqueId: "id",
        clickToSelect: true,
        sidePagination: 'server', // 设置为服务器端分页
        columns: [{
                      align: 'center', // 居中显示
                      title: '序号',
                      width:10,
                      formatter: function (value, row, index) {
                          var pageSize=$listGrid.bootstrapTable('getOptions').pageSize;
                          var pageNumber=$listGrid.bootstrapTable('getOptions').pageNumber;
                          return pageSize * (pageNumber - 1) + index + 1;
                      }
                  },{
                      field: 'loginUser',
                      title: '账号',
                      width: 80,
                      align: 'center',
                      valign: 'middle',
                        cellStyle:formatTableUnit,
                        formatter:paramsMatter
                  },{
                      field: 'username',
                      title: '用户名',
                      width: 80,
                      align: 'center',
                      valign: 'middle',
                      cellStyle:formatTableUnit,
                      formatter:paramsMatter
                  },{
                      field: 'roleName',
                      title: '角色',
                      width: 140,
                      align: 'center',
                      valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
                  },{
                      field: 'sex',
                      title: '性别',
                      width: 140,
                      align: 'center',
            cellStyle:formatTableUnit,
                  //formatter:paramsMatter,
                      valign: 'middle',
                        formatter: function(idx,row){
                            if(row.sex==0){
                                return '女';
                            }else{
                                return '男';
                            }
                        }
                  },{
                      field: 'phone',
                      title: '电话',
                      width: 180,
                      align: 'center',
                      valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
                  },{
                      field: 'email',
                      title: '邮箱',
                      width: 180,
                      align: 'center',
                      valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
                  },{
            field: 'checkStatus',
            title: '状态',
            width: 140,
            align: 'center',
            cellStyle:formatTableUnit,
            //formatter:paramsMatter,
            valign: 'middle',
            formatter: function(idx,row){
                if(row.checkStatus==0){
                    return '已启用';
                }else{
                    return '已禁用';
                }
            }
        },{
                      field: 'createTime',
                      title: '创建时间',
                      width: 180,
                      align: 'center',
                      valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
                  },{
               	   	  field: 'operate',
               	   	  title: '操作',
                      width: 220,
                      align: 'center',
            cellStyle:formatTableUnit,
                    formatter: function(idx,row){
                        /*return [
                            '<i title="编辑" class="editUser glyphicon glyphicon-pencil"></i>',
                            '<i title="详情" class="js_details glyphicon glyphicon-eye-open"></i>',
                            '<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>'
                        ].join('');*/
                        var str = "";
                        if(row.checkStatus==0){
                            str = [
                                '<i title="编辑" class="editUser glyphicon glyphicon-edit"></i>',
                                '<i title="详情" class="js_details glyphicon glyphicon-eye-open"></i>',
                                '<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>',
                                '<i title="禁用" style="color: #0079FE;" class="js_detailAds glyphicon glyphicon-minus-sign"></i>'
                            ].join('');
                        }else{
                            str = [
                                '<i title="编辑" class="editUser glyphicon glyphicon-edit"></i>',
                                '<i title="详情" class="js_details glyphicon glyphicon-eye-open"></i>',
                                '<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>',
                                '<i title="启用" style="color: green;" class="js_detailAds glyphicon glyphicon-play-circle"></i>'
                            ].join('');
                        }
                        return str;
                    }
                  }],
        //返回的数据进行数据结构转换
        responseHandler:function(result){
            var data=result.data;
            if(result.result=='300'){
                window.top.location.href=$src1+'login.html';
            }
            if(!data){
                data=[];
                return false;
            }
            return {
                total:result.total,
                rows:result.data
            }
        }
    });

    // 请求服务器数据时发送的参数，可以在这里添加额外的查询参数，返回false则终止请求
    function queryParams(params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit : params.limit, // 每页显示数量
            offset: params.limit,  //页码,
            pageSize: params.pageSize,
            loginUser:$(".js_accountNumber").val(),
            username:$('.js_name').val(),
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }

    function resetForm(c){
    	c.find('input[type=text]').each(function(){
    		$(this).val('');
    	});
        c.find('input[type=password]').each(function(){
            $(this).val('');
        });
        c.find('input[type=radio]').each(function(){
            $(this).removeAttr('checked');
        });
        c.find('input[type=radio]').eq(0).prop("checked",true);
    	c.find('select').each(function(){
    		$(this).val('');
    	})
    }

    //获取角色列表
    var data =  {
        current:1,
        loginUser:'',
        offset:10,
        username:''
    };
    data = JSON.stringify(data);
    $.ajax({
        type: "post",
        url: $src + "user/user_role/list",
        data: data,
        dataType: "json",
        contentType:'application/json',
        beforeSend: function(request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (data) {
            if(data.result == 200){
                var d=data.data;
                for(var i=0;i<d.length;i++){
                    selectStr+='<option value="'+d[i].roleId+'">'+d[i].roleName+'</option>'
                }
            }else if(data.result==300){
                modelTxt('未登录!');
                //退出时清空所有缓存数据

                window.top.location.href=$src1+'login.html';
                hideUser();
            }else{
                modelTxt(data.message);
            }
        },
        error: function(){
            modelTxt('服务出错!');
        }
    });

    //新建角色弹窗
    var ids = '';
    var sele = "";
    $(".js_newUsers").click(function(){
        var _iframe = window.parent;
        _iframe.modalOut('systemManagement/addEditUser.html');
        setTimeout(function(){
            $("#popUser",window.parent.document).show();
            $("#popRole",window.parent.document).hide();
            $("#popEditRole",window.parent.document).hide();
            $("#password-error",window.parent.document).hide();
            resetForm($(".pop,.popBox1",window.parent.document));
            $(".settingStyle select",window.parent.document).html("");
            $("#ajax .popBox1 .settingBox",window.parent.document).find('.popTitle').text('新建用户');
            $("#ajax .popBox1 .settingBox",window.parent.document).find('input').attr("disabled",false);
            $("#ajax .popBox1 .settingBox",window.parent.document).find('select').attr("disabled",false);
            $("#ajax .popBox1 .settingBox",window.parent.document).find('.submitUserForm').css({display:'block'});
            $(".settingStyle .roleLis",window.parent.document).append(selectStr);
            ids = "";
        },200);
    });



    //新建用户确定按钮
    $("#ajax",window.parent.document).on("click",".submitUserForm",function () {
        //手机号码验证
        jQuery.validator.addMethod("isMobile", function(value, element) {
            var length = value.length;
            var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
            return this.optional(element) || (length == 11 && mobile.test(value));
        }, "请正确填写手机号码");
        var validator = $('#ajax',window.parent.document).find('#formId').validate();
        if(!validator.form()){
            return false;
        };
        $('#ajax',window.parent.document).find('#formId').validate({
            rules: {
                name: "required",
                password: {
                    required: true,
                    minlength: 5
                },
                phone: {
                    required : true,
                    minlength : 11,
                    isMobile : true
                }
            },
            messages: {
                name: "请输入姓名",
                password: {
                    required: "请输入密码",
                    minlength: "密码不能小于5个字符"
                },
                phone: {
                    required : "请输入手机号",
                    minlength : "不能小于11个字符",
                    isMobile : "请正确填写手机号码"
                }
            }
        });

        if(ids){//修改
            var data = {
                loginUser:$('.settingBox',window.parent.document).find('.uName').val(),
                username:$('.settingBox',window.parent.document).find('.name').val(),
                sex:$('.settingBox',window.parent.document).find('input[name=gender]').val(),
                phone:$('.settingBox',window.parent.document).find('.phone').val(),
                email:$('.settingBox',window.parent.document).find('.email').val(),
                roleName:$('.settingBox',window.parent.document).find('.roleLis option:checked').text(),
                roleId:$('.settingBox',window.parent.document).find('.roleLis option:checked').val(),
                //roleId:sele,
                id:ids
            };
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "user/update",
                type: "post",
                contentType:'application/json',
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", token);
                },
                data: data,
                dataType: "json",
                success: function(data){
                    if(data.result==200){
                        var opt={
                            url: $src+"user/list",
                            silent: true
                        };
                        $listGrid.bootstrapTable('refresh',opt);
                        modelTxt('修改成功!');
                        $("#ajax",window.parent.document).find(".js_cancel").trigger('click');
                    }else if(data.result==16){
                        modelTxt('插入数据重复!');
                        $("#ajax",window.parent.document).find(".js_cancel").trigger('click');
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据

                        window.top.location.href=$src1+'login.html';
                        hideUser();
                    }else{
                        modelTxt(data.message);
                        $("#ajax",window.parent.document).find(".js_cancel").trigger('click');
                    }
                }
            })
        }else{
            var data = {
                loginUser:$('.settingBox',window.parent.document).find('.uName').val(),
                password:$('.settingBox',window.parent.document).find('.pass').val(),
                username:$('.settingBox',window.parent.document).find('.name').val(),
                sex:$('.settingBox',window.parent.document).find('input[name=gender]').val(),
                phone:$('.settingBox',window.parent.document).find('.phone').val(),
                email:$('.settingBox',window.parent.document).find('.email').val(),
                roleName:$('.settingBox',window.parent.document).find('.roleLis option:checked').text(),
                roleId:$('.settingBox',window.parent.document).find('.roleLis option:checked').val(),
            };
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "user/register",
                type: "post",
                contentType:'application/json',
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", token);
                },
                data: data,
                dataType: "json",
                success: function(data){
                    if(data.result==200){
                        var opt={
                            url: $src+"user/list",
                            silent: true

                        };
                        $listGrid.bootstrapTable('refresh',opt);
                        modelTxt('新增成功!');
                        //新建用户取消按钮
                        $("#ajax",window.parent.document).find(".js_cancel").trigger('click');
                        //hideUser();
                    }else if(data.result==16){
                        modelTxt('插入数据重复!');
                        $("#ajax",window.parent.document).find(".js_cancel").trigger('click');
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据

                        window.top.location.href=$src1+'login.html';
                        hideUser();
                    }else{
                        modelTxt(data.message);
                        $("#ajax",window.parent.document).find(".js_cancel").trigger('click');
                    }
                },error:function () {
                    modelTxt('服务出错!');
                }
            })
        }
        // $('#ajax',window.parent.document).hide();
        // $(".modal-backdrop.fade",window.parent.document).hide();
        //$('#ajax',window.parent.document).modal('hide');
    });

    //编辑用户回显赋值
    $listGrid.on("click",'.editUser',function () {
        //var ds = {};
        var _iframe = window.parent;
        _iframe.modalOut('systemManagement/addEditUser.html');
        ids = $(this).parent().parent().attr('data-uniqueid');
        setTimeout(function(){
            $("#popRole",window.parent.document).hide();
            $("#popUser",window.parent.document).show();
            $("#popEditRole",window.parent.document).hide();
            $("#password-error",window.parent.document).hide();
            resetForm($(".pop,.popBox1",window.parent.document));
            $("#ajax .popBox1 .settingBox",window.parent.document).find('.popTitle').text('修改用户');
            $(".settingStyle select",window.parent.document).html("");
            $("#ajax .popBox1 .settingBox",window.parent.document).find('input').attr("disabled",false);
            $("#ajax .popBox1 .settingBox",window.parent.document).find('select').attr("disabled",false);
            $("#ajax .popBox1 .settingBox",window.parent.document).find('.submitUserForm').css({display:'block'});
            $(".settingStyle .roleLis",window.parent.document).append(selectStr);
            var data = {id:ids};
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "user/detail",
                type: "post",
                contentType:'application/json',
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", token);
                },
                data: data,
                dataType: "json",
                success: function(data){
                    if(data.result==200){
                       var ds = data.data;
                        $('.settingBox',window.parent.document).find('.uName').val(ds.loginUser);
                            $('.settingBox',window.parent.document).find('.pass').val(ds.password);
                            $('.settingBox',window.parent.document).find('.name').val(ds.username);
                        $('.settingBox',window.parent.document).find('input[name=gender][value="'+ds.sex+'"]').prop("checked",true);
                            $('.settingBox',window.parent.document).find('.phone').val(ds.phone);
                            $('.settingBox',window.parent.document).find('.email').val(ds.email);
                            $('.settingBox',window.parent.document).find('.roleList').val(ds.roleName);
                        //下拉菜单的回显赋值
                        $.each($(".settingStyle .roleLis option",window.parent.document),function (i,s) {
                            sele = $(this).val();
                            if($(this).text()==ds.roleName){
                                $(".settingStyle .roleLis",window.parent.document).html('<option value="'+$(this).val()+'">'+ds.roleName+'</option>'+selectStr)
                            }
                        });
                    }else if(data.result==16){
                        modelTxt('插入数据重复!');
                        hideUser();
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据
                        
                        window.top.location.href=$src1+'login.html';
                        hideUser();
                    }else{
                        modelTxt(data.message);
                        $("#ajax",window.parent.document).find(".js_cancel").trigger('click');
                    }
                }
            });
                $("#ajax .popBox1 .settingBox",window.parent.document).find('.uName').attr("disabled","disabled");
            $("#ajax .popBox1 .settingBox",window.parent.document).find('.pass').attr("disabled","disabled");
        },200);



    });

    //删除
    $listGrid.on("click",'.delOpt',function () {
        var id = $(this).parent().parent().attr('data-uniqueid');
        var _iframe = window.parent;
        _iframe.modalOutDele();
        $("#ajaxDele",window.parent.document).find('#saveBtn').on("click",function () {
            var data = {id:id,checkStatus:2};
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "user/delete",
                type: "post",
                contentType:'application/json',
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", token);
                },
                data: data,
                dataType: "json",
                success: function(data){
                    if(data.result==200){
                        $listGrid.bootstrapTable('refresh');
                        modelTxt('删除成功!');
                        hideUser();
                    }else if(data.result==19){
                        modelTxt("用户为超级管理员,不能被删除或者编辑!");
                        hideUser();
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据

                        window.top.location.href=$src1+'login.html';
                        hideUser();
                    }else{
                        modelTxt(data.message);
                        $("#ajax",window.parent.document).find(".js_cancel").trigger('click');
                    }
                }
            })
        })

    });

    //禁用或者启用
    $listGrid.on("click",'.js_detailAds',function () {
        var del = $(this).parent().parent().find('td:nth-child(8)').text();
        if(del=='已启用'){
            del = 1;
        }else{
            del = 0;
        }
        var id = $(this).parent().parent().attr('data-uniqueid');
        var _iframe = window.parent;
        _iframe.ajaxRes();
        $("#ajaxRes",window.parent.document).find('#saveBtn').unbind('click').bind("click",function () {
            var data = {id:id,checkStatus:del};
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "user/delete",
                type: "post",
                contentType:'application/json',
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", token);
                },
                data: data,
                dataType: "json",
                success: function(data){
                    if(data.result==200){
                        $listGrid.bootstrapTable('refresh');
                        modelTxt('操作成功!');
                        hideUser();
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据

                        window.top.location.href=$src1+'login.html';
                        hideUser();
                    }else{
                        modelTxt(data.message);
                        $("#ajax",window.parent.document).find(".js_cancel").trigger('click');
                    }
                }
            })
        })

    });

    //详情页面
    $listGrid.on("click",'.js_details',function () {
       var ids = $(this).parent().parent().attr('data-uniqueid');
        var _iframe = window.parent;
        _iframe.modalOut('systemManagement/addEditUser.html');
        setTimeout(function(){
            $("#popRole",window.parent.document).hide();
            $("#popUser",window.parent.document).show();
            $("#popEditRole",window.parent.document).hide();
            resetForm($(".pop,.popBox1",window.parent.document));
            $("#ajax .popBox1 .settingBox",window.parent.document).find('.popTitle').text('查看详情');
            $(".settingStyle .roleLis",window.parent.document).html("");
            $(".settingStyle .roleLis",window.parent.document).append(selectStr);
            var data = {id:ids};
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "user/detail",
                type: "post",
                contentType:'application/json',
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", token);
                },
                data: data,
                dataType: "json",
                success: function(data){
                    if(data.result==200){
                       var ds = data.data;
                        $('.settingBox',window.parent.document).find('.uName').val(ds.loginUser);
                            $('.settingBox',window.parent.document).find('.pass').val(ds.password);
                            $('.settingBox',window.parent.document).find('.name').val(ds.username);
                            $('.settingBox',window.parent.document).find('input[name=gender][value="'+ds.sex+'"]').prop("checked",true);
                            $('.settingBox',window.parent.document).find('.phone').val(ds.phone);
                            $('.settingBox',window.parent.document).find('.email').val(ds.email);
                        //下拉菜单的回显赋值
                        $.each($(".settingStyle .roleLis option",window.parent.document),function (i,s) {
                            if($(this).text()==ds.roleName){
                                $(".settingStyle .roleLis",window.parent.document).html('<option value="'+ds.roleId+'">'+ds.roleName+'</option>'+selectStr)
                            }
                        });
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据

                        window.top.location.href=$src1+'login.html';
                        hideUser();
                    }else{
                        modelTxt(data.message);
                    }
                }
            });

            $("#ajax .popBox1 .settingBox",window.parent.document).find('input').attr("disabled","disabled");
            $("#ajax .popBox1 .settingBox",window.parent.document).find('select').attr("disabled","disabled");
            $("#ajax .popBox1 .settingBox",window.parent.document).find('.submitUserForm').css({display:'none'});
        },200);
    });

    //新建用户取消按钮
    $("#ajax",window.parent.document).on("click",".js_cancel",function () {
        hideUser();
    });
    function hideUser() {
        $("#popRole",window.parent.document).hide();
        $("#popUser",window.parent.document).hide();
        $("#popEditRole",window.parent.document).hide();
        $("#ajaxDele",window.parent.document).hide();
        $(".uNameSpn",window.parent.document).hide();
        $(".passSpn",window.parent.document).hide();
        //$(".fade .in",window.parent.document).removeClass('modal-backdrop');
        //resetForm($(".pop,.popBox1",window.parent.document));
    }

    //重置
    $(".js_resetQuery").click(function(){
        $(".js_accountNumber,.js_name,.js_data").val("");   //系统管理  ---》 用户管理
        $(".query").trigger('click');
    });
    /*查询*/
    $(".query").click(function(){
    	var opt={
                url: $src+"user/list",
            pageNumber: 1, // 首页页码
    			silent: true
    			
    	};
        $listGrid.bootstrapTable('refresh',opt);
    });


    //错误信息提示
    function modelTxt(modelTxts){
        var _iframe = window.parent;
        _iframe.modalOut1('systemManagement/model.html');
        setTimeout(function(){
            $("#ajax1",window.parent.document).find('#modelText').text(modelTxts);
        },200);
    }

    //表格提示信息
    function paramsMatter(value, row, index) {
        var span=document.createElement('span');
        span.setAttribute('title',value);
        span.innerHTML = value;
        return span.outerHTML;
    }

    function formatTableUnit(value, row, index) {
        return {
            css: {
                "white-space": 'nowrap',
                "text-overflow": 'ellipsis',
                "overflow": 'hidden'
            }
        }
    }


    //计算高度列表
    function resize() {
        var total_height = $(window).height();
        var margin = $('.searchBox').width();
        var searchHeight = $('.searchTitle').outerHeight();
        $('.contentBox').height(total_height-10-searchHeight-margin*0.015*2);
        $('.content').height($('.contentBox').outerHeight()-$('.icon').outerHeight()-margin*0.015*2);
        $('#listGrid').height($('.content').outerHeight()-margin*0.015*2);
        h=$('.content').outerHeight()-margin*0.015;
    }
});
