/**
 * Created by jie.yang on 2019/7/9.
 */
$(function(){
    var $src = window.localStorage.getItem('url');
    var $src1 = "../../";//假数据//假数据//http://localhost:63342/webBanks/
    var token = window.localStorage.getItem('token');
    var loginUser = window.localStorage.getItem('loginUser');
    var h = 0;
    resize();
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
        url: $src+"notice/list",
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
            field: 'noticeName',
            title: '名称',
            width: 80,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
        },{
            field: 'noticeDescribe',
            title: '描述',
            width: 80,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter: function(idx,row){
                if(row.noticeDescribe=='undefined') {
                    return '';
                }else{
                    return row.noticeDescribe;
                }
            }
        },/*{
            field: 'version',
            title: '版本号',
            width: 80,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
        },*/{
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
        },{
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
         /!* '<i title="编辑" class="editUser glyphicon glyphicon-pencil"></i>',
         '<i title="详情" class="js_details glyphicon glyphicon-eye-open"></i>',*!/
         '<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>'
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
            noticeName:$(".js_legalPerson").val(),
            //username:$('.js_name').val(),
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }

    //新建角色弹窗
    $(".customerAdd").click(function(){
        var _iframe = window.parent;
        _iframe.noticess('noticeList/addNotice.html');
        setTimeout(function(){
            //$(".settingStyle select",window.parent.document).html("");
            $("#notice1 .settingBox",window.parent.document).find('.popTitle').text('新增公告');
            $(".lastName",window.parent.document).val("");
            $(".roleDesc1",window.parent.document).val("");
            $(".roleDesc1",window.parent.document).attr('placeholder',"请输入备注");
            $("#-error",window.parent.document).hide();
        },200);
    });


    //保存 存库
    $("#noticess",window.parent.document).on("click",".submitUserForm",function(){
        var validator = $('#notice1',window.parent.document).find('#formWids2').validate();
        if(!validator.form()){
            return false;
        }
        if($(".roleDesc1",window.parent.document).val()==""){
            $(".roleDesc1",window.parent.document).attr('placeholder',"此公告内容不能为空！");
            return false;
        }
        var data = {};
        data.noticeName = $(".lastName",window.parent.document).val();
        data.noticeDescribe = $(".roleDesc1",window.parent.document).val();
        /*data.appType = $(".roleList option:selected",window.parent.document).val();
        data.constraintStatus = $(".roleFile option:selected",window.parent.document).val();
        data.browserDescribe = $(".roleDesc1",window.parent.document).val();*/
        //data.files = list;
        data = JSON.stringify(data);
        $.ajax({
            url: $src + "notice/insert",
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
                    $("#-error",window.parent.document).hide();
                    $("#noticess",window.parent.document).find(".js_cancel").trigger('click');
                }else if(data.result==300){
                    modelTxt('未登录!');
                    //退出时清空所有缓存数据

                    window.top.location.href=$src1+'login.html';
                    $("#noticess",window.parent.document).find(".js_cancel").trigger('click');
                }else if(data.result==30){
                    modelTxt('控件上传错误，请重新上传!');
                    $("#noticess",window.parent.document).find(".js_cancel").trigger('click');
                }else{
                    modelTxt(data.message);
                    $("#noticess",window.parent.document).find(".js_cancel").trigger('click');
                }
            }
        })

    });

    //删除 先放着吧，以后 可能有用
    /*$listGrid.on("click",'.delOpt',function () {
        /!*var del = $(this).parent().parent().find('td:nth-child(4)').text();
         if(del=='已启用'){
         del = 1;
         }else{
         del = 0;
         }*!/
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
*/
    //重置
    $(".js_resetQuery").click(function(){
        $(".js_legalPerson,.js_creditCode,.js_nameOfUnit").val("");  //查询服务  ---》基础库查询/业务信息查询
        $(".js_jqQuery").trigger('click');
    });

    /*查询*/
    $(".js_jqQuery").click(function(){
        var opt={
            url: $src+"notice/list",
            pageNumber: 1, // 首页页码
            silent: true

        };
        $listGrid.bootstrapTable('refresh',opt);
    });

    function hideUser() {
        $("#btn",window.parent.document).val("开始上传");
        $("#fileList1",window.parent.document).html("");
        $(".roleDesc1",window.parent.document).val("");
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
