/**
 * Created by jie.yang on 2019/7/1.
 */
$(function(){
    var $src = window.localStorage.getItem('url');
    var $src2 = "../../static/public/";
    var $src1 = "../../";//假数据
    var token = window.localStorage.getItem('token');
    var roleType = window.localStorage.getItem('roleType');//状态码为0,1,2,3
    var loginUser = window.localStorage.getItem('loginUser');
    var h = 0;
    resize();
    var $listGrid=$('#detailGrid');
    $listGrid.bootstrapTable({
        url: $src+"banner/list",
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
            field: 'bannerDescribe',
            title: '描述',
            width: 80,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter: function(idx,row){
                if(row.bannerDescribe=='undefined') {
                    return '';
                }else{
                    return row.bannerDescribe;
                }
            }
        },{
            field: 'bannerType',
            title: '文件类型',
            width: 80,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter: function(idx,row){
                if(row.bannerType==0){
                    return '图片';
                }else if(row.bannerType==1){
                    return '视频';
                }else{
                    return '未知';
                }
            }
        },{
            field: 'checkStatus',
            title: '启停状态',
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
        },{
            field: 'operate',
            title: '操作',
            width: 220,
            align: 'center',
            cellStyle:formatTableUnit,
            formatter: function(idx,row){
                var str = "";
                if(roleType==3||roleType==2){

                    /*str = [
                        '<i class="js_details" title="审核"><img style="width: 17px;margin-top: -3px;" src="../../static/images/shenhe.png"></i>',
                        '<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>'
                    ].join('');*/
                    if(row.checkStatus==0){
                        str = [
                            '<i class="js_details" title="审核"><img style="width: 17px;margin-top: -3px;" src="../../static/images/shenhe.png"></i>',
                            '<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>',
                            '<i title="禁用" style="color: #0079FE;" class="js_detailAds glyphicon glyphicon-minus-sign"></i>'
                        ].join('');
                    }else{
                        str = [
                            '<i class="js_details" title="审核"><img style="width: 17px;margin-top: -3px;" src="../../static/images/shenhe.png"></i>',
                            '<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>',
                            '<i title="启用" style="color: green;" class="js_detailAds glyphicon glyphicon-play-circle"></i>'
                        ].join('');
                    }
                }else if(roleType==0||roleType==1){
                    str = '<p style="margin:0;">无权限</p>';
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
            bannerName:$(".js_accountNumber").val(),
            //username:$('.js_name').val(),
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }
    //审核
    $listGrid.on("click",'.js_details',function () {
        $("#ajaxMine .textarea",window.parent.document).val("");
        $('#ajaxMine select option:first',window.parent.document).prop('selected', 'selected');
        var id = $(this).parent().parent().attr('data-uniqueid');
        var stust = $(this).parent().parent().find('td:nth-child(6)').text();
        if(stust=='审核成功'){
            modelTxt('您已经审核通过，不能再操作了!');
            $("#ajaxMine",window.parent.document).find(".js_cancel").trigger('click');
            return false;
        }
        var _iframe = window.parent;
        _iframe.ajaxMine();
        $("#ajaxMine",window.parent.document).find('#saveBtnMine').unbind('click').bind("click",function () {
            var data = {
                id:id,
                review:$("#ajaxMine .textarea",window.parent.document).val(),
                reviewStatus:$("#ajaxMine select option:selected",window.parent.document).val()
            };
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "banner/update",
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
                        $("#ajaxMine",window.parent.document).find(".js_cancel").trigger('click');
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据
                        window.top.location.href=$src1+'login.html';
                        $("#ajaxMine",window.parent.document).find(".js_cancel").trigger('click');
                    }else{
                        modelTxt(data.message);
                        $("#ajaxMine",window.parent.document).find(".js_cancel").trigger('click');
                    }
                }
            })
        })
    });

    //禁用或者启用
    $listGrid.on("click",'.js_detailAds',function () {
        var del = $(this).parent().parent().find('td:nth-child(5)').text();
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
                url: $src + "banner/update",
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
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据
                        window.top.location.href=$src1+'login.html';
                    }else{
                        modelTxt(data.message);
                        $("#ajax",window.parent.document).find(".js_cancel").trigger('click');
                    }
                }
            })
        })

    });

    //删除
    $listGrid.on("click",'.delOpt',function () {
        var id = $(this).parent().parent().attr('data-uniqueid');
        var stust = $(this).parent().parent().find('td:nth-child(4)').text();
        if(stust=='审核成功'&&loginUser!='admin'){
            modelTxt('审核成功，您没有删除权限!');
            $("#ajaxDele",window.parent.document).find(".js_cancel").trigger('click');
            return false;
        }else if((stust=='审核失败'||stust=='未审核')&&loginUser!='admin'){
            deleteFor();
        }
        if(loginUser=='admin'){
            deleteFor();
        }
        function deleteFor() {
            var _iframe = window.parent;
            _iframe.modalOutDele();
            $("#ajaxDele",window.parent.document).find('#saveBtn').unbind('click').bind("click",function () {
                //var data = {id:id,checkStatus:del};
                var data = {id:id,checkStatus:2};
                data = JSON.stringify(data);
                $.ajax({
                    url: $src + "banner/update",
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
                    }
                })
            })
        }
    });

    //重置
    $(".js_resetQuery").click(function(){
        $(".js_accountNumber").val("");  //查询服务  ---》基础库查询/业务信息查询
        $(".js_jqQuery").trigger('click');
    });
    /*查询*/
    $(".js_jqQuery").click(function(){
        var opt={
            url: $src+"banner/list",
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
