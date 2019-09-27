$(function(){
    var $src = window.localStorage.getItem('url');
    var token = window.localStorage.getItem('token');
    var loginUser = window.localStorage.getItem('loginUser');
    var roleType = window.localStorage.getItem('roleType');
    var $rc = "../../views";
    var $src1 = "../../";//假数据//假数据//http://localhost:63342/webBanks/
    var selectStr = '';
    var h = 0;
    resize();

    var $listGrid=$('#detailGrid1');
    $listGrid.bootstrapTable({
        url: $src+"browserSafeStrategy/list",
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
                      field: 'copyState',
                      title: '是否拷贝',
                      width: 80,
                      align: 'center',
                      valign: 'middle',
                        cellStyle:formatTableUnit,
                        formatter: function(idx,row){
                            if(row.copyState==1){
                                return '已启用';
                            }else{
                                return '已禁用';
                            }
                        }
                  },{
                      field: 'downloadState',
                      title: '是否下载',
                      width: 80,
                      align: 'center',
                      valign: 'middle',
                      cellStyle:formatTableUnit,
                      formatter: function(idx,row){
                          if(row.downloadState==1){
                              return '已启用';
                          }else{
                              return '已禁用';
                          }
                      }
                  },{
                      field: 'dragState',
                      title: '是否拖拽',
                      width: 140,
                      align: 'center',
                      valign: 'middle',
            cellStyle:formatTableUnit,
            formatter: function(idx,row){
                if(row.dragState==1){
                    return '已启用';
                }else{
                    return '已禁用';
                }
            }
                  },
                  {
                      field: 'printState',
                      title: '是否打印',
                      width: 180,
                      align: 'center',
                      valign: 'middle',
            cellStyle:formatTableUnit,
            formatter: function(idx,row){
                if(row.printState==1){
                    return '已启用';
                }else{
                    return '已禁用';
                }
            }
                  },{
                      field: 'watermarkState',
                      title: '是否水印',
                      width: 180,
                      align: 'center',
                      valign: 'middle',
            cellStyle:formatTableUnit,
            formatter: function(idx,row){
                if(row.watermarkState==1){
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
            cellStyle:formatTableUnit,
            valign: 'middle',
            formatter: function(idx,row){
                if(row.reviewStatus==0){
                    return '已禁用';
                }else{
                    return '已启用';
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
                        if(roleType=='2'||roleType=="3"){
                        	 str = [
                                    '<i title="编辑" class="editUser glyphicon glyphicon-edit"></i>'
                                ].join('');
                        }else{
                        	str = '<p style="margin:0;">无权限</p>';
                        } 
                        /*if(row.checkStatus==0){
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
                        }*/
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

    /*function resetForm(c){
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
    }*/

    

    //修改
    $("#strategyList",window.parent.document).on("click",".submitUserForm",function () {
            var data = {
            		 copyState:$('.settingBox',window.parent.document).find('input[name="gender1"]:checked').val(),
                     downloadState:$('.settingBox',window.parent.document).find('input[name="gender2"]:checked').val(),
                     dragState:$('.settingBox',window.parent.document).find('input[name="gender3"]:checked').val(),
                     printState:$('.settingBox',window.parent.document).find('input[name="gender4"]:checked').val(),
                     watermarkState:$('.settingBox',window.parent.document).find('input[name="gender5"]:checked').val(),
                     reviewStatus:$('.settingBox',window.parent.document).find('input[name="gender6"]:checked').val(),
                     id:ids
            };
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "browserSafeStrategy/update",
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
                        modelTxt('修改成功!');
                        $("#strategyList",window.parent.document).find(".js_cancel").trigger('click');
                    }else if(data.result==16){
                        modelTxt('插入数据重复!');
                        $("#strategyList",window.parent.document).find(".js_cancel").trigger('click');
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        window.top.location.href=$src1+'login.html';
                    }else{
                        modelTxt(data.message);
                        $("#strategyList",window.parent.document).find(".js_cancel").trigger('click');
                    }
                }
            })
        
    });

    //编辑用户回显赋值
    $listGrid.on("click",'.editUser',function () {
        var _iframe = window.parent;
        _iframe.strategyList('strategy/editStrategy.html');
        ids = $(this).parent().parent().attr('data-uniqueid');
        setTimeout(function(){
        	$(".popTitle",window.parent.document).text('修改策略信息');
            var data = {
            		limit: 10,
            		offset: 10
            };
            data = JSON.stringify(data);
            $.ajax({
                url: $src + "browserSafeStrategy/list",
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
                       var copyState = ds[0].copyState;
                       var downloadState = ds[0].downloadState;
                       var dragState = ds[0].dragState;
                       var printState = ds[0].printState;
                       var watermarkState = ds[0].watermarkState;
                       var reviewStatus = ds[0].reviewStatus;
                       $('.settingBox',window.parent.document).find('input[name=gender1][value="'+copyState+'"]').prop("checked",true);
                       $('.settingBox',window.parent.document).find('input[name=gender2][value="'+downloadState+'"]').prop("checked",true);
                       $('.settingBox',window.parent.document).find('input[name=gender3][value="'+dragState+'"]').prop("checked",true);
                       $('.settingBox',window.parent.document).find('input[name=gender4][value="'+printState+'"]').prop("checked",true);
                       $('.settingBox',window.parent.document).find('input[name=gender5][value="'+watermarkState+'"]').prop("checked",true);
                       $('.settingBox',window.parent.document).find('input[name=gender6][value="'+reviewStatus+'"]').prop("checked",true);
                    }else if(data.result==300){
                        modelTxt('未登录!');
                        //退出时清空所有缓存数据
                        window.top.location.href=$src1+'login.html';
                    }else{
                        modelTxt(data.message);
                        $("#strategyList",window.parent.document).find(".js_cancel").trigger('click');
                    }
                }
            });
        },200);
    });


    

    
    //新建用户取消按钮
    $("#strategyList",window.parent.document).on("click",".js_cancel",function () {
        //hideUser();
    });

    //重置
    $(".js_resetQuery").click(function(){
        $(".js_accountNumber,.js_name,.js_data").val("");   //系统管理  ---》 用户管理
        $(".query").trigger('click');
    });
    /*查询*/
    $(".query").click(function(){
    	var opt={
                url: $src+"browserSafeStrategy/list",
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
