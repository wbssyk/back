/**
 * Created by jie.yang on 2019/7/9.
 */
$(function(){
    var $src = window.localStorage.getItem('url');
    var $src1 = "../../";//假数据//假数据//http://localhost:63342/webBanks/
    var token = window.localStorage.getItem('token');
    var loginUser = window.localStorage.getItem('loginUser');
    var h = 0;
    var selectStr = '';
    resize();

    $.ajax({
        type: "get",
        url: $src + "widgetKey/upgrade/keyList",
        dataType: "json",
        contentType:'application/json',
        beforeSend: function(request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (data) {
            console.log(data)
            if(data.result == 200){
                var d=data.data;
                for(var i=0;i<d.length;i++){
                    selectStr+='<option value="'+d[i].roleName+'">'+d[i].roleName+'</option>'
                }
            }else if(data.result==300){
                modelTxt('未登录!');
                //退出时清空所有缓存数据
                window.top.location.href=$src1+'login.html';
                hideUser();
            }
        },
        error: function(){
            modelTxt('服务出错！');
        }
    });

    function resetForm(c){
        c.find('input').each(function(){
            $(this).val('');
        });
        c.find('select').each(function(){
            $(this).val('');
        })
    }
    var $listGrid=$('#detailGrid');
    $listGrid.bootstrapTable({
        url: $src+"widgetKey/list",
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
            field: 'keyName',
            title: '名称',
            width: 80,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
        },{
            field: 'keyDescribe',
            title: '描述',
            width: 80,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
        },{
            field: 'reviewStatus',
            title: '状态',
            width: 140,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter: function(idx,row){
                if(row.reviewStatus==1){
                    return '未审核';
                }else if(row.reviewStatus==2){
                    return '审核成功';
                }else if(row.reviewStatus==3){
                    return '审核失败';
                }else{
                    return '未知';
                }
            }
        }/*,{
                field: 'review',
                title: '审核意见',
                width: 180,
                align: 'center',
                valign: 'middle',
                cellStyle:formatTableUnit,
                formatter: function(idx,row){
                    if(row.review=='undefined'){
                        return '-';
                    }else{
                        return row.review;
                    }
                }
            }*/,{
            field: 'createTime',
            title: '创建时间',
            width: 180,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
        }
            /*,{
            field: 'operate',
            title: '操作',
            width: 220,
            align: 'center',
            cellStyle:formatTableUnit,
            formatter: function(){
                return [
                   '<i title="编辑" class="editUser glyphicon glyphicon-edit"></i>',
                    /!* '<i title="详情" class="js_details glyphicon glyphicon-eye-open"></i>',*!/
                    /!*'<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>'*!/
                ].join('');
            }
        }*/],
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
            keyName:$(".js_legalPerson").val(),
            //username:$('.js_name').val(),
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }

    //新建角色弹窗
    $(".customerAdd").click(function(){
        var _iframe = window.parent;
        _iframe.controls('controlType/addContolType.html');
        setTimeout(function(){
            //$(".settingStyle select",window.parent.document).html("");
            $("#controls .popBoxCon .settingBox",window.parent.document).find('.popTitle').text('新增名称');
            $(".roleList",window.parent.document).val('1');
            $(".keyName",window.parent.document).val('');
            $(".roleDesc2",window.parent.document).val('');
            $(".roleFile",window.parent.document).val('1');
            $(".roleDesc2",window.parent.document).attr('placeholder',"请输入备注");

        },200);
    });
    //保存 存库
    $("#controls",window.parent.document).on("click",".submitUserForm",function(){
        var validator = $('#popBoxCon',window.parent.document).find('#formConts1').validate();
        if(!validator.form()){
            return false;
        }
        if($(".roleDesc2",window.parent.document).val()==""){
            $(".roleDesc2",window.parent.document).attr('placeholder',"这是必填字段！");
            return false;
        }
        //data.appType = $(".roleList option:selected",window.parent.document).val();
        //data.browserType = $(".roleType option:selected",window.parent.document).val();
        var data = {};
        data.keyName = $(".keyName",window.parent.document).val();
        data.keyDescribe = $(".roleDesc2",window.parent.document).val();
        data = JSON.stringify(data);
        $.ajax({
            url: $src + "widgetKey/insert",
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
                    $("#controls",window.parent.document).find(".js_cancel").trigger('click');
                }else if(data.result==300){
                    modelTxt('未登录!');
                    //退出时清空所有缓存数据
                    window.top.location.href=$src1+'login.html';
                    $("#controls",window.parent.document).find(".js_cancel").trigger('click');
                }else{
                    modelTxt(data.message);
                    $("#controls",window.parent.document).find(".js_cancel").trigger('click');
                }
            }
        })

    });

    //删除 先放着吧，以后 可能有用
    $listGrid.on("click",'.delOpt',function () {
        /*var del = $(this).parent().parent().find('td:nth-child(4)').text();
         if(del=='已启用'){
         del = 1;
         }else{
         del = 0;
         }*/
        var id = $(this).parent().parent().attr('data-uniqueid');
        var _iframe = window.parent;
        _iframe.modalOutDele();
        $("#ajaxDele",window.parent.document).find('#saveBtn').on("click",function () {
            //var data = {id:id,checkStatus:del};
            var data = {id:id,checkStatus:2};
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "widget/update",
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
                },
                error:function(){
                    modelTxt('服务出错!');
                    $("#ajaxDele",window.parent.document).find(".js_cancel").trigger('click');
                }
            })
        })
    });

    //重置
    $(".js_resetQuery").click(function(){
        $(".js_legalPerson,.js_creditCode,.js_nameOfUnit").val("");  //查询服务  ---》基础库查询/业务信息查询
        $(".js_jqQuery").trigger('click');
    });

    /*查询*/
    $(".js_jqQuery").click(function(){
        var opt={
            url: $src+"widgetKey/list",
            pageNumber: 1, // 首页页码
            silent: true

        };
        $listGrid.bootstrapTable('refresh',opt);
    });

    function hideUser() {
        $("#fileList1",window.parent.document).html("");
        $(".roleDesc2",window.parent.document).val("");
    }

    //添加表格提示信息
    function formatTableUnit(value, row, index) {
        return {
            css: {
                "white-space": 'nowrap',
                "text-overflow": 'ellipsis',
                "overflow": 'hidden'
            }
        }
    }

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
