$(function(){
    var $src = window.localStorage.getItem('url');
    var token = window.localStorage.getItem('token');
    var loginUser = window.localStorage.getItem('loginUser');
    var $src1 = "../../";//假数据
    var selectStr = '';
    var h = 0;
    resize();

    var $listGrid=$('#detailGrid1');
    $listGrid.bootstrapTable({
        url: $src+"resource/list",
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
                      field: 'parentName',
                      title: '父名称',
                      width: 80,
                      align: 'center',
                      valign: 'middle',
                        cellStyle:formatTableUnit,
                        formatter:paramsMatter
                  },{
                      field: 'urlName',
                      title: '子名称',
                      width: 80,
                      align: 'center',
                      valign: 'middle',
                      cellStyle:formatTableUnit,
                      formatter:paramsMatter
                  }/*,{
                      field: 'htmlUrl',
                      title: 'url地址',
                      width: 140,
                      align: 'center',
                      valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
                  }*/,{
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
                        var str = "";
                        if(row.checkStatus==0){
                           str = [
                               /*'<i title="修改模块" class="editUser glyphicon glyphicon-pencil"></i>',*/
                               '<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>',
                               '<i title="禁用" style="color: #0079FE;" class="js_details glyphicon glyphicon-minus-sign"></i>'
                           ].join('');
                        }else{
                            str = [
                                /*'<i title="修改模块" class="editUser glyphicon glyphicon-pencil"></i>',*/
                                '<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>',
                                '<i title="启用" style="color: green;" class="js_details glyphicon glyphicon-play-circle"></i>'
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
            offset: params.limit,  //页码
            parentName:$(".js_accountNumber").val(),
            urlName:$('.js_name').val(),//可能也需要修改名字
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }

    function resetForm(c){
    	c.find('input').each(function(){
    		$(this).val('');
    	});
    	c.find('select').each(function(){
    		$(this).val('');
    	})
    };

    //新建模块弹框
    $(".js_newUsers").click(function(){
        var _iframe = window.parent;
        _iframe.modalBqSetting('systemManagement/addSetting.html');
        setTimeout(function(){
            $(".settingBodys .settingBox",window.parent.document).find('.popTitle').text('新建模块');
        },200);
    });

    var ids = '';
    //权限列表获取  先关掉修改功能
   /* $listGrid.on("click",'.editUser',function () {
        //$("#electionBox1 .electionBox",window.parent.document).remove();
        ids = $(this).parent().parent().attr('data-uniqueid');
        var _iframe = window.parent;
        _iframe.modalBqSetting('systemManagement/addSetting.html');
       /!* var adminflag = $(this).parent().parent().find('td:nth-child(2)').text();
        //判断是否为admin账号，admin无法添加权限，无法删除
        if(adminflag=='admin'){
            $('.btnBox .addRoleBtn',window.parent.document).hide();//隐藏确定按钮
        }else{
            $('.btnBox .addRoleBtn',window.parent.document).show();//显示确定按钮
        }*!/
        setTimeout(function(){
            $(".settingBodys .settingBox",window.parent.document).find('.popTitle').text('修改模块');
            //判断是否为admin账号，admin无法添加权限，无法删除
            /!*if(adminflag=='admin'){
                $('.btnBox .addRoleBtn',window.parent.document).hide();//隐藏确定按钮
            }else{
                $('.btnBox .addRoleBtn',window.parent.document).show();//显示确定按钮
            }*!/
            var data = {roleId:ids};
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "resource/update",
                type: "post",
                contentType:'application/json',
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", token);
                },
                data: data,
                dataType: "json",
                success: function(data){
                    if(data.result==200){

                    }
                }
            });
        },200);

    });*/

    //添加及修改模块确定按钮
    $("#modalBqSetting",window.parent.document).on("click",".addRoleBtn",function () {
        var validator = $('#modalBqSetting',window.parent.document).find('#formId').validate();
        if(!validator.form()){
            return false;
        }
        var data = {
            parentName:$(".parentName",window.parent.document).val(),
            urlName:$(".lastName",window.parent.document).val(),
            htmlUrl:$(".urlName",window.parent.document).val()
            //id:ids
        };
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "resource/insert",
                type: "post",
                contentType:'application/json',
                beforeSend: function(request) {
                    request.setRequestHeader("Authorization", token);
                },
                data: data,
                dataType: "json",
                success: function(data){
                    if(data.result==200){
                        modelTxt('添加模块成功!');
                        $("#modalBqSetting",window.parent.document).find(".js_cancel").trigger('click');
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据
                        window.top.location.href=$src1+'login.html';
                        $("#modalBqSetting",window.parent.document).find(".js_cancel").trigger('click');
                    }else{
                        modelTxt(data.message);
                        $("#modalBqSetting",window.parent.document).find(".js_cancel").trigger('click');
                    }
                },
                error:function () {
                    modelTxt('服务出错!');
                    $("#modalBqSetting",window.parent.document).find(".js_cancel").trigger('click');
                }
            })
    });

    //删除
    $listGrid.on("click",'.delOpt',function () {
        if($(this).parent().parent().find('td:nth-child(2)').text()=='admin'){
            modelTxt('admin用户不能删除!');
            return false;
        }
        var id = $(this).parent().parent().attr('data-uniqueid');
        var _iframe = window.parent;
        _iframe.modalOutDele();
        $("#ajaxDele",window.parent.document).find('#saveBtn').on("click",function () {
            var data = {id:id,checkStatus:2};
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "resource/delete",
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
                        $("#ajaxDele",window.parent.document).find(".js_cancel").trigger('click');
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据
                        window.top.location.href=$src1+'login.html';
                        $("#ajaxDele",window.parent.document).find(".js_cancel").trigger('click');
                    }else{
                        modelTxt(data.message);
                        $("#ajaxDele",window.parent.document).find(".js_cancel").trigger('click');
                    }
                }
            })
        })
    });

    //禁用启用按钮
    $listGrid.on("click",'.js_details',function () {
        var del = $(this).parent().parent().find('td:nth-child(4)').text();
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
                url: $src + "resource/delete",
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
                    }
                }
            })
        })
    });

    //取消按钮
    $("#modalBqSetting",window.parent.document).on("click",".js_cancel",function () {
        hideUser();
    });
    function hideUser() {
        $('.btnBox .addRoleBtn',window.parent.document).show();//显示确定按钮
        //$("#ajaxDele",window.parent.document).hide();
        resetForm($(".settingBodys",window.parent.document));
    }

    //重置
    $(".js_resetQuery").click(function(){
        $(".js_accountNumber,.js_name,.js_data").val("");   //系统管理  ---》 用户管理
        $(".query").trigger('click');
    });
    /*查询*/
    $(".query").click(function(){
    	var opt={
                url: $src+"resource/list",
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
