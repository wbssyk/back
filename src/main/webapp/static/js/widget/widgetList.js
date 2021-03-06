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
    var selectStr = '';
    $.ajax({
        type: "get",
        url: $src + "widgetKey/upgrade/keyList",
        dataType: "json",
        contentType:'application/json',
        beforeSend: function(request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (data) {
            if(data.result == 200){
                var d=data.data;
                for(var i=0;i<d.length;i++){
                    selectStr+='<option value="'+d[i].keyMark+'">'+d[i].keyName+'</option>'
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
        url: $src+"widget/list",
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
            field: 'widgetName',
            title: '名称',
            width: 80,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
        },/*{
            field: 'url',
            title: '地址',
            width: 80,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
        },*//*{
            field: 'version',
            title: '版本号',
            width: 80,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter:paramsMatter
        }*/
            {
                field: 'keyAppName',
                title: '应用类型',
                width: 80,
                align: 'center',
                valign: 'middle',
                cellStyle:formatTableUnit,
                formatter:paramsMatter
            },{
            field: 'constraintStatus',
            title: '更新类型',
            width: 80,
            align: 'center',
            valign: 'middle',
            cellStyle:formatTableUnit,
            formatter: function(idx,row){
                if(row.constraintStatus==1){
                    return '强制';
                }else if(row.reviewStatus==2){
                    return '非强制';
                }else{
                    return '未知';
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
            widgetName:$(".js_legalPerson").val(),
            //username:$('.js_name').val(),
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }

    //新建角色弹窗
    var flag = 0;
    $(".customerAdd").click(function(){
        var _iframe = window.parent;
        _iframe.widgetAds('widget/addWidget.html');
        setTimeout(function(){
            //$(".settingStyle select",window.parent.document).html("");
            $("#widgetAds .popBoxWidget .settingBox",window.parent.document).find('.popTitle').text('上传控件');
            $(".settingStyle .roleList2",window.parent.document).append(selectStr);
            $(".roleList2",window.parent.document).val('1');
            //$(".roleType",window.parent.document).val('1');
            $(".roleFile",window.parent.document).val('1');
            flag = 1;
            var filePicker = $("#popBoxWidget #filePicker",window.parent.document);
            webUploadrs(filePicker);
        },200);
    });
    //上传文件  先关掉上传控件
    function webUploadrs(filePicker) {
        var $list = $('.uploadery #fileList1',window.parent.document),
        // 优化retina, 在retina下这个值是2
            ratio = window.devicePixelRatio || 1,
        // 缩略图大小
            thumbnailWidth = 100 * ratio,
            thumbnailHeight = 100 * ratio,
        // Web Uploader实例
        //uploader;
        // 初始化Web Uploader
            uploader = WebUploader.create({
                // 自动上传。
                auto: false,
                // swf文件路径
                swf : $src1+'uploadHeader/Uploader.swf',
                // 文件接收服务端。控制层,可以带参数
                server : $src+'/widget/upload',
                threads:'30',        //同时运行30个线程传输
                fileNumLimit:'1',  //文件总数量只能选择10个 
                /*ajaxOptions:{
                 headers:{"Authorization":token}
                 },*/
                headers:{"Authorization":token},
                // 选择文件的按钮。可选。
                pick: {id:filePicker,  //选择文件的按钮
                    multiple:false},   //允许可以同时选择多个图片
                // 图片质量，只有type为`image/jpeg`的时候才有效。
                quality: 100,

                //限制传输文件类型，accept可以不写 (用于显示文件类型筛选) 这个放开就是图片上传了
                 /*accept: {
                         title: 'Images',//描述
                         extensions: 'gif,jpg,jpeg,bmp,png,zip',//类型
                         mimeTypes: 'image/!*'//mime类型
                     }*/
            });
        // 当有文件添加进来的时候，创建img显示缩略图使用
        uploader.on( 'fileQueued', function( file ) {
            $(".flagPs",window.parent.document).hide();
            var $li = $(
                    '<div style="background-color: #f5f5f5; color: #000000;" id="'+ file.id + '" class="item">'+'<div class="info" title="'+file.name+'" style="width: 350px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">'
                    + file.name
                    +'<i class="glyphicon glyphicon-remove remove-this" id="removeClick" style="font-size: 16px; float: right;margin: -6px -5px 0 5px;cursor: pointer;"></i>'
                    + '</div>' + '<p class="state" style="font-size: 14px;">等待上传...</p>'
                    + '</div>'
                ),
                //下面的放开就是图片上传了
            /*var $li = $(
                '<div style="background-color: #f5f5f5; color: #000000;" id="'+ file.id + '" class="item">'+'<div class="info" title="'+file.name+'" style="width: 350px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">'
                + file.name
                +'<img src=""/>'+'<i class="glyphicon glyphicon-remove remove-this" id="removeClick" style="font-size: 16px; float: right;margin: -6px -5px 0 5px;cursor: pointer;"></i>'
                + '</div>' + '<p class="state" style="font-size: 14px;">等待上传...</p>'
                + '</div>'
            ),*/
                $img = $li.find('img');
            // $list为容器jQuery实例
            $list.append( $li );

            // 创建缩略图
            // 如果为非图片文件，可以不用调用此方法。
            // thumbnailWidth x thumbnailHeight 为 100 x 100  打开后可以看到上传图片的代码，先关掉
            uploader.makeThumb( file, function( error, src ) {
                     if ( error ) {
                         $img.replaceWith('<span>不能预览</span>');
                         return;
                     }
                     $img.attr( 'src', src );
                 }, thumbnailWidth, thumbnailHeight );

            //删除上传的文件
            $list.on('click', '.remove-this', function() {
                if ($(this).parent().parent().attr('id') == file.id) {
                    uploader.removeFile(file);
                    $(this).parent().parent().remove();
                }
            });
        });


        // 文件上传过程中创建进度条实时显示。    uploadProgress事件：上传过程中触发，携带上传进度。 file文件对象 percentage传输进度 Nuber类型
        uploader.on( 'uploadProgress', function( file, percentage ) {
            var $li = $( '#'+file.id,window.parent.document ),$percent = $li.find('.progress .progress-bar',window.parent.document);
            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress progress-striped active">' +
                    '<div class="progress-bar" style="height:20px; background-color:green;width: 0%" role="progressbar">' +
                    '</div>' +
                    '</div>').appendTo($li).find('.progress-bar');
            }

            $li.find('p.state').text('上传中');
            $percent.css('width', percentage * 100 + '%');
        });

        
        // 文件上传成功时候触发，给item添加成功class, 用样式标记上传成功。 file：文件对象，    response：服务器返回数据
        var list = [];
        uploader.on( 'uploadSuccess', function( file,response) {
            if(response.result == '28'||response.result == '30'){
                modelTxt(response.message);
                $("#widgetAds",window.parent.document).find(".js_cancel").trigger('click');
                return false;
            };
            $('#' + file.id,window.parent.document).find('p.state').text('已上传');
            $( '#'+file.id,window.parent.document).addClass('upload-state-done');
            var obj = {};
            obj.fileName = response.data.fileName;
            obj.filePath = response.data.filePath;
            obj.id = response.data.id;
            obj.md5 = response.data.md5;
            list.push(obj);
            //$("#upInfo").html("<font color='red'>"+response._raw+"</font>");
        });


        // 文件上传失败                                file:文件对象 ， code：出错代码
        uploader.on( 'uploadError', function(file,code) {
            $('#' + file.id,window.parent.document).find('p.state').text('上传出错');
            var $li = $( '#'+file.id ),
                     $error = $li.find('div.error');

                 // 避免重复创建
                 if ( !$error.length ) {
                     $error = $('<div class="error"></div>').appendTo( $li );
                 }
                 $error.text('上传失败!');
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on('uploadComplete', function (file) {
            $('#' + file.id,window.parent.document).find('.progress').fadeOut();
        });

        // 不管成功或者失败，文件上传完成时触发。 file： 文件对象
        uploader.on( 'uploadComplete', function( file ) {
            $( '#'+file.id,window.parent.document).find('.progress').remove();
            $(".flagPs",window.parent.document).hide();
        });

        //新建用户取消按钮
        $("#widgetAds",window.parent.document).on("click",".js_cancel",function () {
            if(uploader){
                uploader.destroy();
            }
            hideUser();
        });

        //绑定提交事件
        $("#widgetAds",window.parent.document).on("click",'#btn',function () {
            uploader.upload();   //执行手动提交
            $(".flagPs",window.parent.document).hide();

        });

        //保存 存库
        $("#widgetAds",window.parent.document).on("click",".submitUserForm",function(){
            if(list.length==0){
                if(flag==1){
                    $("#fileList1",window.parent.document).append('<p style="color: red;" class="flagPs">请选择需要上传的文件!</p>');
                    flag++;
                }
                return false;
            }
            var list1 = list;
            for(var i=0;i<list.length;i++){
                var data = {};
                data.originalFilename = list[i].fileName;
                data.url = list[i].filePath;
				data.id = list[i].id;
				data.md5 = list[i].md5;
            }
            data.appType = $(".roleList2 option:selected",window.parent.document).val();
            data.keyAppName = $(".roleList2 option:selected",window.parent.document).text();
            //data.browserType = $(".roleType option:selected",window.parent.document).val();
            data.constraintStatus = $(".roleFile option:selected",window.parent.document).val();
            data.widgetDescribe = $(".textarea",window.parent.document).val();
            var data1 = data;
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "widget/insert",
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
                        $("#widgetAds",window.parent.document).find(".js_cancel").trigger('click');
                        list = [];
                        return false;
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据

                        window.top.location.href=$src1+'login.html';
                        $("#widgetAds",window.parent.document).find(".js_cancel").trigger('click');
                    }if(data.result==33){
                        $listGrid.bootstrapTable('refresh');
                        var _iframe = window.parent;
                        _iframe.ajaxDeleWidget();
                        $("#ajaxDeleWidget",window.parent.document).find('#saveBtnWidget').unbind('click').bind("click",function () {
                            data1.updateNewKey = '1';
                            var data = JSON.stringify(data1);
                            $.ajax({
                                url: $src + "widget/insert",
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
                                        modelTxt('更新成功!');
                                        $("#ajaxDeleWidget",window.parent.document).find(".js_cancel").trigger('click');
                                        list = [];
                                        return false;
                                    }else if(data.result==300){
                                        modelTxt('未登录!');
                                        //退出时清空所有缓存数据
                                        window.top.location.href=$src1+'login.html';
                                        $("#ajaxDeleWidget",window.parent.document).find(".js_cancel").trigger('click');
                                    }else{
                                        modelTxt(data.message);
                                        $("#ajaxDeleWidget",window.parent.document).find(".js_cancel").trigger('click');
                                    }
                                },
                                error:function(){
                                    modelTxt('服务出错!');
                                    $("#ajaxDele",window.parent.document).find(".js_cancel").trigger('click');
                                }
                            })
                        });
                        $("#widgetAds",window.parent.document).find(".js_cancel").trigger('click');
                    }else{
                        modelTxt(data.message);
                        $("#widgetAds",window.parent.document).find(".js_cancel").trigger('click');
                    }
                }
            })

        });

    }

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
            url: $src+"widget/list",
            pageNumber: 1, // 首页页码
            silent: true

        };
        $listGrid.bootstrapTable('refresh',opt);
    });

    function hideUser() {
        $("#btn",window.parent.document).val("开始上传");
        $("#fileList1",window.parent.document).html("");
        $(".textarea",window.parent.document).val("");
        $(".roleList2",window.parent.document).html("");
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
