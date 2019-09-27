$(function(){
    var $src = window.localStorage.getItem('url');
    var token = window.localStorage.getItem('token');
	var loginUser = window.localStorage.getItem('loginUser');
	var $src1 = "../../";//假数据//假数据//http://localhost:63342/webBanks/
	var selectStr = '';
	var h = 0;
	var roleIds = [];
	resize();
    /*function popRest(c){
    	c.find('.roleName').val('');
    	c.find('.roleDesc').val('');
    	var checks = c.find('input[type=checkbox]');
    	if(checks&&checks.length>0){
    		for(var i=0;i<checks.length;i++){
        		checks[i].checked=false;
        	}
    	}
    	
    }*/
	function resetForm(c){
		c.find('input').each(function(){
			$(this).val('');
		});
		c.find('select').each(function(){
			$(this).val('');
		})
		c.find('textarea').each(function(){
			$(this).val('');
		})
	}
	var $listGrid=$('#detailGrid');
	$listGrid.bootstrapTable({
		url: $src+"role/list",
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
		ajaxOptions:{
			headers:{"Authorization":token}
		},
		dataType:'json',
		queryParams: queryParams,//传递参数（*）
		showHeader:true,
		singleSelect:true,
		queryParamsType:'limit',
		offset: 10, // 页面数据条数
		pageNumber: 1, // 首页页码
		pageList: [10, 20,30], // 设置页面可以显示的数据条数
		uniqueId: "roleId",
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
		},/*{
			field: 'loginUser',
			title: '账号',
			width: 80,
			align: 'center',
			valign: 'middle',
			cellStyle:formatTableUnit,
			formatter:paramsMatter
		}*/{
			field: 'roleName',
			title: '角色',
			width: 140,
			align: 'center',
			valign: 'middle',
			cellStyle:formatTableUnit,
			formatter:paramsMatter
		},{
			field: 'roleDescription',
			title: '角色描述',
			width: 80,
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
		}/*,{
			field: 'sex',
			title: '性别',
			width: 140,
			align: 'center',
			valign: 'middle',
			cellStyle:formatTableUnit,
		//formatter:paramsMatter,
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
		}*/,{
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
					'<i title="添加角色" class="editUser glyphicon glyphicon-plus-sign"></i>',
					'<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>'
				].join('');*/
				var str = "";
				if(row.checkStatus==0){
					str = [
						'<i title="添加角色权限" class="editUser glyphicon glyphicon-plus-sign"></i>',
						'<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>',
						'<i title="禁用" style="color: #0079FE;" class="js_detailES glyphicon glyphicon-minus-sign"></i>'
					].join('');
				}else{
					str = [
						'<i title="添加角色权限" class="editUser glyphicon glyphicon-plus-sign"></i>',
						'<i title="删除" id="delOpt" data-events=”delOpt” class="delOpt glyphicon glyphicon-trash"></i>',
						'<i title="启用" style="color: green;" class="js_detailES glyphicon glyphicon-play-circle"></i>'
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
			//获取roleid
            for(var i=0;i<data.length;i++){
				if(data[i].roleId){
					var obj = {};
					obj.id = data[i].userId;
					obj.roleId = data[i].roleId;
					roleIds.push(obj)
				}
			};
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
			limit : params.limit,// 每页显示数量
			offset: params.limit,
			pageSize: params.pageSize,
			roleName:$(".js_user").val(),//角色
			//username:$('.js_fullName').val(),
			current:$listGrid.bootstrapTable('getOptions').pageNumber
		};
		return temp;
	}

    
    /*跳转详情*/
    /*$(".js_details").click(function(){
        window.location.href = $rc + "/systemManagement/userDetails.html"
    });*/
    //新建角色弹窗
    $(".js_role").click(function(){
		var _iframe = window.parent;
		_iframe.modalOut('systemManagement/addEditUser.html');
		setTimeout(function(){
			$("#popUser",window.parent.document).hide();
			$("#popRole",window.parent.document).show();
			$("#popEditRole",window.parent.document).hide();
			$(".settingStyle select",window.parent.document).html("");
			$("#ajax .popBox1 .settingBox",window.parent.document).find('.popTitle').text('新建角色');
			$("#ajax .popBox1 .settingBox",window.parent.document).find('input').val("");
			$("#ajax .popBox1 .settingBox",window.parent.document).find('input').val("");
			$("#ajax .popBox1 .settingBox",window.parent.document).find('textarea').val("");
			$("#ajax .popBox1 .settingBox",window.parent.document).find('.submitUserForm').css({display:'block'});
			$(".settingStyle select",window.parent.document).append(selectStr);
		},200);
    });

	var ids = '';
	//新建用户确定按钮
	$("#ajax",window.parent.document).on("click",".addRoleBtn",function () {
		var validator = $('#ajax',window.parent.document).find('#formAS').validate();
		if(!validator.form()){
			return false;
		}
		if(ids){//修改
			var roleId = "";
			for(var i = 0;i<roleIds.length;i++){
				if(ids == roleIds[i].id){
					roleId = roleIds[i].roleId;
				}
			}
			var data = {
				roleName:$('.settingBox',window.parent.document).find('.roleList1').val(),
				userId:ids,
				roleId:roleId
			};
			//console.log(data)
			data = JSON.stringify(data);
			$.ajax({
				url: $src + "role/user_role/insert",
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
							url: $src+"role/list",
							silent: true
						};
						$listGrid.bootstrapTable('refresh',opt);
						modelTxt('添加角色成功!');
						$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
					}else if(data.result==16){
						modelTxt('插入数据重复!');
						$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
					}else if(data.result==300){
						modelTxt('未登录!');
						//退出时清空所有缓存数据

						window.top.location.href=$src1+'login.html';
						$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
					}else{
						modelTxt(data.message);
						$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
					}
				}
			})
		}else{
			var data = {
				roleName:$('.settingBox',window.parent.document).find('.roleName').val(),
				roleDescription:$('.settingBox',window.parent.document).find('.roleDesc').val()
			};
			data = JSON.stringify(data);
			$.ajax({
				url: $src + "role/insert",
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
							url: $src+"role/list",
							silent: true
						};
						$listGrid.bootstrapTable('refresh',opt);
						modelTxt('新增成功!');
						$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
					}else if(data.result==16){
						modelTxt('插入数据重复!');
						$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
					}else if(data.result==300){
						modelTxt('未登录!');
						//退出时清空所有缓存数据

						window.top.location.href=$src1+'login.html';
						$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
					}else{
						modelTxt(data.message);
						$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
					}
				}
			})
		}
	});

	//编辑用户回显赋值
	$listGrid.on("click",'.editUser',function () {
		$(".settingStyle #electionBox1",window.parent.document).html("");
	 var _iframe = window.parent;
	 _iframe.modalOut('systemManagement/addEditUser.html');
		//判断是否为admin账号，admin无法添加权限，无法删除
		var adminflag = $(this).parent().parent().find('td:nth-child(2)').text();
		if(adminflag=='admin'){
			$('.btnBox .addRoleBtn1',window.parent.document).hide();//隐藏确定按钮
		}else{
			$('.btnBox .addRoleBtn1',window.parent.document).show();//显示确定按钮
		}
	 setTimeout(function(){
		 if(adminflag=='admin'){
			 $('.btnBox .addRoleBtn1',window.parent.document).hide();//隐藏确定按钮
		 }else{
			 $('.btnBox .addRoleBtn1',window.parent.document).show();//显示确定按钮
		 }
	 $("#popUser",window.parent.document).hide();
	 $("#popRole",window.parent.document).hide();
	 $("#popEditRole",window.parent.document).show();
	 $("#ajax .popBox1 .settingBox",window.parent.document).find('.popTitle').text('添加角色权限');
	 $(".settingStyle select",window.parent.document).append(selectStr);
	 $("#ajax .popBox1 .settingBox",window.parent.document).find('.submitUserForm').css({display:'block'});
	 },200);
		ids = $(this).parent().parent().attr('data-uniqueid');
		var data = {roleId:ids};
		data = JSON.stringify(data);
		$.ajax({
			url: $src + "role/role_permission/list",
			type: "post",
			contentType:'application/json',
			beforeSend: function(request) {
				request.setRequestHeader("Authorization", token);
			},
			data: data,
			dataType: "json",
			success: function(data){
				if(data.result==200){
					var flag = false;
					var yy = 0;
					var ds = data.data;
					for(var i = 0; i < ds.length; i++) {
						var a = ds[i];
						var z = i+1000;
						var p = i+2000;
						var data = ds[i];
						for (var j = 0; j < data.resourceList.length; j++) {
							var sourceData = data.resourceList[j];
							if(sourceData.selectEd==1){
								flag = true;
							}else {
								flag = false;
							}
						}
						if(flag){
							var dom = $('<div id="'+p+'" class="electionBox"><span class="partyOrganTitle">'+a.resourceType+'</span><ul class="col-md-2 col-sm-4 col-xs-6 demo-col election flags" style="margin-left: 0;    padding-left: 0;">' +
								'<li class="icheck-warning">' +
								'<input type="checkbox" id="'+z+'" class="primary" checked/>' +
								'<label for="'+z+'">全选</label>' +
								'</li>'+
								'</ul></div>');
						}else{
							var dom = $('<div id="'+p+'" class="electionBox"><span class="partyOrganTitle">'+a.resourceType+'</span><ul style="padding-left: 0;margin-left: 0;" class="col-md-2 col-sm-4 col-xs-6 demo-col election flags">' +
								'<li class="icheck-warning">' +
								'<input type="checkbox" id="'+z+'" class="primary"/>' +
								'<label for="'+z+'">全选</label>' +
								'</li>'+
								'</ul></div>');
						}


						var domStr = '';
						for (var j = 0; j < data.resourceList.length; j++) {
							var c = yy++;
							var sourceData = data.resourceList[j];
							if(sourceData.selectEd==1){
								domStr += '<ul><li style="margin-left: 150px;" class="icheck-warning"><input type="checkbox" class="check" name="checkbox" functionName="'+sourceData.resourceId+'" checked id="'+c+'"/><label style="width: 50%;text-align: left" for="'+c+'">'+sourceData.resourceName+'</label></li></ul>';
							}else{
								domStr += '<ul><li style="margin-left: 150px;" class="icheck-warning"><input type="checkbox" class="check" name="checkbox" functionName="'+sourceData.resourceId+'" id="'+c+'"/><label style="width: 50%;text-align: left;" for="'+c+'">'+sourceData.resourceName+'</label></li></ul>'
							}

						}
						dom.append(domStr);
						$("#electionBox1",window.parent.document).append(dom)
					}
				}else if(data.result==300){
					modelTxt('未登录!');
					//退出时清空所有缓存数据

					window.top.location.href=$src1+'login.html';
					hideUser();
				}else{
					modelTxt(data.message);
					$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
				}



				//全选获取数值
				var checkAllIds = $('#electionBox1 .electionBox',window.parent.document);  //div的id
				$.each(checkAllIds,function (i,n) {
					if(this.id){
						var $thisIds = $(this);
						var checkAll = $thisIds.find('.primary');
						var checkboxs = $thisIds.find('.check');//所有单选的input
						checkAll.on("click",function () {//全选
							if($(this).is(':checked')){
								checkboxs.each(function(){
									$(this).prop("checked",true);
								});
							}else{
								checkboxs.each(function(){
									$(this).removeAttr("checked",false);
								});
							}
						});
						checkboxs.on("click",function (x,b) {
							var checkedLength = $(this).parent().parent().parent().find(".check[name='checkbox']:checked").length;
							var checkLength = $(this).parent().parent().parent().find(".check[name='checkbox']").length;
							if(checkLength == checkedLength){
								checkAll.prop("checked",true);
								return true;
							}else{
								checkAll.prop("checked",false);
								return true;
							}
						})
					}
				});
			}
		});
	 });

	//添加权限确定按钮
	$("#ajax",window.parent.document).on("click",".addRoleBtn1",function () {
		var data = {
			roleId:ids,
			params:[]
		};

		//全选获取数值
		var checkboxs =$('#electionBox1 input[name="checkbox"]:checked',window.parent.document); //所有单选的input
		checkboxs.each(function (inx,ele) {
			var obj = {};
			obj.resourceId = $(this).attr('functionName');
			data.params.push(obj);
		});
		data = JSON.stringify(data);
		$.ajax({
			url: $src + "role/role_permission/insert",
			type: "post",
			contentType:'application/json',
			beforeSend: function(request) {
				request.setRequestHeader("Authorization", token);
			},
			data: data,
			dataType: "json",
			success: function(data){
				if(data.result==200){
					modelTxt('添加权限成功!');
					$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
				}else if(data.result==300){
					modelTxt('未登录!');
					//退出时清空所有缓存数据

					window.top.location.href=$src1+'login.html';
					$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
				}else{
					modelTxt(data.message);
					$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
				}
			}
		})
	});

	//新建用户取消按钮
	$("#ajax",window.parent.document).on("click",".js_cancel",function () {
		hideUser();
	});
	function hideUser() {
		//$("#setting",window.parent.document).hide();
		$("#popRole",window.parent.document).hide();
		$("#popUser",window.parent.document).hide();
		$("#popEditRole",window.parent.document).hide();
		$('.btnBox .addRoleBtn1',window.parent.document).show();//显示确定按钮
		/*$("#ajaxDele",window.parent.document).hide();*/
		resetForm($(".pop,.popBox",window.parent.document));
	}

	//删除 
	$listGrid.on("click",'.delOpt',function () {
		var adminflag = $(this).parent().parent().find('td:nth-child(2)').text();
		if(adminflag=='admin'){
			modelTxt('您没有admin删除权限!');
			$listGrid.bootstrapTable('refresh');
			$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
			return false;
		}
		var _iframe = window.parent;
		var ids = $(this).parent().parent().attr('data-uniqueid');
		_iframe.modalOutDele();
		//判断是否为admin账号，admin无法添加权限，无法删除
		$("#ajaxDele",window.parent.document).find('#saveBtn').on("click",function () {
			var data = {roleId:ids,checkStatus:2};
			data = JSON.stringify(data);
			$.ajax({
				url: $src + "role/delete",
				type: "post",
				contentType:'application/json',
				beforeSend: function(request) {
					request.setRequestHeader("Authorization", token);
				},
				data: data,
				dataType: "json",
				success: function(data){
					if(data.result==200){
						modelTxt('删除成功!');
						$listGrid.bootstrapTable('refresh');
						$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
					}else if(data.result==300){
						modelTxt('未登录!');
						//退出时清空所有缓存数据

						window.top.location.href=$src1+'login.html';
						$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
					}else{
						modelTxt(data.message);
						$("#ajax",window.parent.document).find(".js_cancel").trigger('click');
					}
				}
			})
		})

	});


	//禁用或启用按钮 js_detailES
	$listGrid.on("click",'.js_detailES',function () {
		var del = $(this).parent().parent().find('td:nth-child(4)').text();
		if(del=='已启用'){
			del = 1;
		}else{
			del = 0;
		}
		var ids = $(this).parent().parent().attr('data-uniqueid');
		var _iframe = window.parent;
		_iframe.ajaxRes();
		$("#ajaxRes",window.parent.document).find('#saveBtn').unbind('click').bind("click",function () {
			var roleId = "";
			/*for(var i = 0;i<roleIds.length;i++){
				if(ids == roleIds[i].userId){
					roleId = roleIds[i].roleId;
				}
			}*/
			var data = {roleId:ids,checkStatus:del};
			data = JSON.stringify(data);
			$.ajax({
				url: $src + "role/delete",
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

    //重置
    $(".js_resetQuery").click(function(){
        $(".js_fullName,.js_user").val("");  //系统管理  --- 》角色管理
		$(".query").trigger('click');
    });
    /*查询*/
    $(".query").click(function(){
    	var opt={
    			url: $src+"role/list",
			pageNumber: 1, // 首页页码
    			silent: true
    			
    	};
    	$listGrid.bootstrapTable('refresh',opt);
    });

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

	//错误信息提示
	function modelTxt(modelTxts){
		var _iframe = window.parent;
		_iframe.modalOut2('systemManagement/model.html');
		setTimeout(function(){
			$("#ajax2",window.parent.document).find('#modelText').text(modelTxts);
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
