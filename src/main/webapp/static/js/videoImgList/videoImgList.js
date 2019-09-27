/**
 * Created by jie.yang on 2019/7/9.
 */
$(function(){
    var $src = window.localStorage.getItem('url');
    var $src1 = "../../";//假数据//假数据//http://localhost:63342/webBanks/
    var token = window.localStorage.getItem('token');
    var loginUser = window.localStorage.getItem('loginUser');
    var selectStr = '';
    var h = 0;
    var uploader1=null;
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
         /!*'<i title="编辑" class="editUser glyphicon glyphicon-pencil"></i>',
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
            bannerName:$(".js_accountNumber").val(),
            //username:$('.js_name').val(),
            current:$listGrid.bootstrapTable('getOptions').pageNumber
        };
        return temp;
    }

    //新建角色弹窗
    $(".customerAdd").click(function(){
        var _iframe = window.parent;
        _iframe.videoImgs('videoImgList/addVideo.html');
        setTimeout(function(){
            $("#popBoxVideo .settingBox",window.parent.document).find('.popTitle').text('上传图片以及视频');
            /*$(".roleList",window.parent.document).val('1');
            $(".roleType",window.parent.document).val('1');
            $(".roleFile",window.parent.document).val('1');*/

            var filePicker = $("#popBoxVideo #filePicker",window.parent.document);
            webUploadrs(filePicker);

        },200);
    });

    //上传文件
    function webUploadrs(filePicker) {
        var $list = $('.uploadery #fileList',window.parent.document),
        // 优化retina, 在retina下这个值是2
            ratio = window.devicePixelRatio || 1,
        // 缩略图大小
            thumbnailWidth = 100 * ratio,
            thumbnailHeight = 100 * ratio,
        // Web Uploader实例
        //uploader;
        // 初始化Web Uploader
            uploader1 = WebUploader.create({
                // 自动上传。
                auto: false,
                // swf文件路径
                swf : $src1+'uploadHeader/Uploader.swf',
                // 文件接收服务端。控制层,可以带参数
                server : $src+'/banner/upload',
                threads:'30',        //同时运行30个线程传输
                fileNumLimit:'1',  //文件总数量只能选择10个 
                headers:{"Authorization":token},
                // 选择文件的按钮。可选。
                pick: {id:filePicker,  //选择文件的按钮
                    multiple:false},   //允许可以同时选择多个图片
                // 图片质量，只有type为`image/jpeg`的时候才有效。
                quality: 100,

                //限制传输文件类型，accept可以不写 (用于显示文件类型筛选)
                accept: {
                    title: 'Images',//描述
                    extensions: 'gif*,jpg*,jpeg*,bmp*,png*,zip*,mp4*,.gif*,.jpg*,.jpeg*,.bmp*,.png*,.zip*,.mp4*',//类型
                    mimeTypes: 'gif*,jpg*,jpeg*,bmp*,png*,zip*,mp4*,.gif*,.jpg*,.jpeg*,.bmp*,.png*,.zip*,.mp4*'//mime类型
                }
            });
        // 当有文件添加进来的时候，创建img显示缩略图使用
        uploader1.on( 'fileQueued', function( file ) {
           /* if(file.ext=='mp4'){
                //下面的放开就是视频上传了
                var $li = $(
                        '<div style="background-color: #f5f5f5; color: #000000;" id="'+ file.id + '" class="item">'+'<div class="info" title="'+file.name+'" style="width: 350px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">'
                        +'<video class="img-responsive" id="v1" autoplay muted loop style="width: 100%"><source src="" type="video/mp4"></video>'+'<i class="glyphicon glyphicon-remove remove-this" id="removeClick" style="font-size: 16px; float: right;margin: -6px -5px 0 5px;cursor: pointer;"></i>'
                        + '</div>' + '<p class="state" style="font-size: 14px;">等待上传...</p>'
                        + '</div>'
                    ),
                    $img = $li.find('video');
            }else{*/
                //下面的放开就是图片上传了
                var $li = $(
                        '<div style="background-color: #f5f5f5; color: #000000;" id="'+ file.id + '" class="item">'+'<div class="info" title="'+file.name+'" style="width: 350px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">'
                        +'<img src=""/>'+'<i class="glyphicon glyphicon-remove remove-this" id="removeClick" style="font-size: 16px; float: right;margin: -6px -5px 0 5px;cursor: pointer;"></i>'
                        + '</div>' + '<p class="state" style="font-size: 14px;">等待上传...</p>'
                        + '</div>'
                    ),
                    $img = $li.find('img');
            /*}*/
            // $list为容器jQuery实例
            $list.append( $li );
            /*var $li = $(
                    '<div style="background-color: #f5f5f5; color: #000000;" id="'+ file.id + '" class="item">'+'<div class="info" title="'+file.name+'" style="width: 350px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">'
                    + file.name
                    +'<i class="glyphicon glyphicon-remove remove-this" id="removeClick" style="font-size: 16px; float: right;margin: -6px -5px 0 5px;cursor: pointer;"></i>'
                    + '</div>' + '<p class="state" style="font-size: 14px;">等待上传...</p>'
                    + '</div>'
                ),*/


            // 创建缩略图
            // 如果为非图片文件，可以不用调用此方法。
            // thumbnailWidth x thumbnailHeight 为 100 x 100  打开后可以看到上传图片的代码，先关掉
            uploader1.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $img.replaceWith('<span>视频无法预览</span>');
                    return;
                }
                $img.attr( 'src', src );
            }, thumbnailWidth, thumbnailHeight );

            //删除上传的文件
            $list.on('click', '.remove-this', function() {
                if ($(this).parent().parent().attr('id') == file.id) {
                    uploader1.removeFile(file);
                    $(this).parent().parent().remove();
                }
            });
        });


        // 文件上传过程中创建进度条实时显示。    uploadProgress事件：上传过程中触发，携带上传进度。 file文件对象 percentage传输进度 Nuber类型
        uploader1.on( 'uploadProgress', function( file, percentage ) {
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
        uploader1.on( 'uploadSuccess', function( file,response) {
            $('#' + file.id,window.parent.document).find('p.state').text('已上传');
            $( '#'+file.id,window.parent.document).addClass('upload-state-done');
            var obj = {};
            obj.fileName = response.data.fileName;
            obj.filePath = response.data.filePath;
            obj.bannerType = response.data.bannerType;
            obj.id = response.data.id;
            list.push(obj);
            //$("#upInfo").html("<font color='red'>"+response._raw+"</font>");
        });


        // 文件上传失败                                file:文件对象 ， code：出错代码
        uploader1.on( 'uploadError', function(file,code) {
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
        uploader1.on('uploadComplete', function (file) {
            $('#' + file.id,window.parent.document).find('.progress').fadeOut();
        });

        // 不管成功或者失败，文件上传完成时触发。 file： 文件对象
        uploader1.on( 'uploadComplete', function( file ) {
            $( '#'+file.id,window.parent.document).find('.progress').remove();
        });

        //新建用户取消按钮
        $("#popBoxVideo",window.parent.document).on("click",".js_cancel",function () {
            if(uploader1){
                uploader1.destroy();
            }
            hideUser();
        });

        //绑定提交事件
        $("#popBoxVideo",window.parent.document).on("click",'#btn',function () {
            uploader1.upload();   //执行手动提交
        });

        //保存 存库
        $("#popBoxVideo",window.parent.document).on("click",".submitUserForm",function(){
            if(list.length==0){
                $(".flagPs",window.parent.document).show();
                return false;
            }
            for(var i=0;i<list.length;i++){
                var data = {};
                data.bannerName = list[i].fileName;
                data.url = list[i].filePath;
                data.id = list[i].id;
                data.bannerType = list[i].bannerType;
            }
            data.bannerDescribe = $(".textarea",window.parent.document).val();
           /* data.constraintStatus = $(".roleFile option:selected",window.parent.document).val();
            data.browserType = $(".roleType option:selected",window.parent.document).val();*/
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "banner/insert",
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
                        $("#popBoxVideo",window.parent.document).find(".js_cancel").trigger('click');
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据
                        window.top.location.href=$src1+'login.html';
                        $("#popBoxVideo",window.parent.document).find(".js_cancel").trigger('click');
                    }else{
                        modelTxt(data.message);
                        $("#popBoxVideo",window.parent.document).find(".js_cancel").trigger('click');
                    }
                }
            })

        });
    }

    //删除
    /*$listGrid.on("click",'.delOpt',function () {
        /!*var del = $(this).parent().parent().find('td:nth-child(4)').text();
         if(del=='已启用'){
         del = 1;
         }else{
         del = 0;
         }*!/
        var id = $(this).parent().parent().attr('data-uniqueid');
        /!*var stust = $(this).parent().parent().find('td:nth-child(4)').text();
         if(stust=='审核成功'){
         modelTxt('审核成功，您没有删除权限!');
         $("#ajaxDele",window.parent.document).find(".js_cancel").trigger('click');
         return false;
         }*!/
        var _iframe = window.parent;
        _iframe.modalOutDele();
        $("#ajaxDele",window.parent.document).find('#saveBtn').on("click",function () {
            //var data = {id:id,checkStatus:del};
            var data = {id:id,checkStatus:2};
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "browser/update",
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
    });*/


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


    function hideUser() {
        $("#btn",window.parent.document).val("开始上传");
        $("#fileList",window.parent.document).html("");
        $(".textarea",window.parent.document).val("");
    };

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

    //表格提示信息
    function paramsMatter(value, row, index) {
        var span=document.createElement('span');
        span.setAttribute('title',value);
        span.innerHTML = value;
        return span.outerHTML;
    }

    //错误信息提示
    function modelTxt(modelTxts){
        var _iframe = window.parent;
        _iframe.modalOut1('systemManagement/model.html');
        setTimeout(function(){
            $("#ajax1",window.parent.document).find('#modelText').text(modelTxts);
        },200);
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
