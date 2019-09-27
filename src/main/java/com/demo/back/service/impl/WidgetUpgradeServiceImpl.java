package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.widgetupgrade.request.WidgetUpgradeRequest;
import com.demo.back.dao.WidgetUpgradeMapper;
import com.demo.back.entity.WidgetUpgrade;
import com.demo.back.enums.ResponseEnum;
import com.demo.back.exception.ParamNullException;
import com.demo.back.service.IWidgetUpgradeService;
import com.demo.back.utils.Constants;
import com.demo.back.utils.upload.UploadCommon;
import com.demo.back.utils.upload.UploadUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


/**
 * @author lin.cong
 * @ClassName WidgetUpgradeServiceImpl
 */
@Service
public class WidgetUpgradeServiceImpl implements IWidgetUpgradeService {

	private static final String WIDGET_KEY_TYPE = "widget_key_type:";

    @Autowired
    private WidgetUpgradeMapper widgetUpgradeMapper;

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UploadUtils uploadUtils;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public ServiceResponse widgetUpload(MultipartFile file) {
        ServiceResponse response = new ServiceResponse();
        try {
            UploadCommon upload = uploadUtils.upload(file,"/browsers");
            //上传结束
            Map<String, Object> map = new HashMap<>(16);
            map.put("fileName", upload.getOriginalFilename());
            map.put("filePath", upload.getDownLoadUrl());
            map.put("id", upload.getUuid());
            map.put("md5",upload.getMd5());
            response.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    @SuppressWarnings({"rawtypes" })
    @Override
    @Transactional
    public ServiceResponse widgetInsert(WidgetUpgrade request) {
        ServiceResponse response = new ServiceResponse();
        try {
        	
            //获取上传安装包的应用类型
            String appType = request.getAppType();
            //获取集合信息
            List<WidgetUpgrade> list = widgetUpgradeMapper.selectWidgetUpgradeByAppType(appType);
        	
            if(request.getUpdateNewKey()==null||request.getUpdateNewKey().length()==0){
            	
            	if(list!=null && !list.isEmpty()){
            		response.setMessage(ResponseEnum.KEY_EXISIT.getMsg());
            		response.setResult(ResponseEnum.KEY_EXISIT.getCode());
            		return response;
            	}else{
            		//默认上传后为未审核状态
            		request.setReviewStatus("1");
            		request.setCreateTime(new Date());
            		request.setWidgetName(request.getOriginalFilename());
            		//信息存入数据库
            		widgetUpgradeMapper.insert(request);
            	}
            	
            }else{
            	
            	WidgetUpgrade widget = (WidgetUpgrade)list.get(0);
            	//删除数据库中的同类型key对象
            	widget.setCheckStatus(Constants.DELETE_STATUE_NOT);
            	widgetUpgradeMapper.widgetUpdate(widget);
            	//删除缓存中的同名对象
            	if(widget.getReviewStatus().equals("2")){
            		deleteWidGetCache(WIDGET_KEY_TYPE, widget);
            	}
            	
                //默认上传后为未审核状态
            	request.setReviewStatus("1");
            	request.setCreateTime(new Date());
            	request.setWidgetName(request.getOriginalFilename());
            	//信息存入数据库
            	widgetUpgradeMapper.insert(request);
            	
            }
        	
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(ResponseEnum.WIDGET_UPLOAD_ERROR.getMsg());
            response.setResult(ResponseEnum.WIDGET_UPLOAD_ERROR.getCode());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return response;
        }

    }


    /**
     * @return com.demo.back.entity.WidgetUpgrade
     * @Author lin.cong
     * @Description //处理解析 控件上传的格式和信息
     * @Param [widgetUpgrade]
     **/
	/*private WidgetUpgrade handle(WidgetUpgrade widgetUpgrade,List<WidgetUpgrade> list) throws Exception {
    	
        //根据名称判断对象是否为空
        if (list!=null && !list.isEmpty()) {
            WidgetUpgrade widget = (WidgetUpgrade)list.get(0);
        	//删除数据库中的同类型key对象
        	widget.setCheckStatus(Constants.DELETE_STATUE_NOT);
        	widgetUpgradeMapper.widgetUpdate(widget);
        	//删除缓存中的同名对象
        	deleteWidGetCache(WIDGET_KEY_TYPE, widget);
        	
            //默认上传后为未审核状态
            widgetUpgrade.setReviewStatus("1");
            widgetUpgrade.setCreateTime(new Date());
            widgetUpgrade.setWidgetName(widgetUpgrade.getOriginalFilename());
            
        } else {
        	//默认上传后为未审核状态
            widgetUpgrade.setReviewStatus("1");
            widgetUpgrade.setCreateTime(new Date());
            widgetUpgrade.setWidgetName(widgetUpgrade.getOriginalFilename());
            
        }
        return widgetUpgrade;
    }*/

    @Override
    public PageResponse widgetList(WidgetUpgradeRequest request) {
        List<WidgetUpgrade> widgetUpgrades = widgetUpgradeMapper.widgetList(request);
        Integer count = widgetUpgradeMapper.widgetListCount(request);
        return PageResponse.createResponse(count, widgetUpgrades, request);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public ServiceResponse widgetUpdate(WidgetUpgrade request) {
        ServiceResponse response = new ServiceResponse();
        if (StringUtils.isEmpty(request.getId())) {
            throw new ParamNullException();
        }
        WidgetUpgrade widgetUpgrade = widgetUpgradeMapper.selectById(request.getId());
        try {
            //审核通过后，更新数据库，然后同步redis中的缓存
            widgetUpgradeMapper.widgetUpdate(request);
            if (request.getReviewStatus() != null && request.getReviewStatus() != "") {
                handleWidGetCache(WIDGET_KEY_TYPE, widgetUpgrade);
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("id", request.getId());
        return response.setData(map);
    }

    /**
     * @Method
     * @Author yakun.shi
     * @Description 放入缓存逻辑
     * @Return
     * @Date 2019/8/1 10:06
     */
    @SuppressWarnings("unchecked")
	private void handleWidGetCache(String redisKey, WidgetUpgrade widgetUpgrade) {
    	//安装包上传ftp地址
        String downLoadUrl = widgetUpgrade.getUrl();
        //获取更新类型（1：强制，2：非强制）
        String constraintStatus = widgetUpgrade.getConstraintStatus();
        //获取id值
        String id = widgetUpgrade.getId();
        //获取控件应用类型
        String appType = widgetUpgrade.getAppType();
        //获取控件名称
        String widgetName = widgetUpgrade.getWidgetName();
        
        //获取key值
        StringBuilder builder = new StringBuilder();
        builder.append(redisKey).append(id).append("_").append(appType);
        String key = builder.toString();
        
        Map<String, Object> map = new HashMap<>(16);
        map.put("downLoadUrl",downLoadUrl);
        map.put("constraintStatus",constraintStatus);
        map.put("widgetName",widgetName);
        map.put("id",id);
        map.put("md5", widgetUpgrade.getMd5());
        map.put("appType", appType);
        map.put("keyAppName", widgetUpgrade.getKeyAppName());
        
        //存入缓存
        redisTemplate.opsForValue().set(key, map);
    }
    
    /**
     * @Method
     * @Description 删除缓存同类型key数据
     * @Return
     */
    @SuppressWarnings({ "unchecked" })
	private void deleteWidGetCache(String redisKey, WidgetUpgrade widget) {
    	Set<String> set = redisTemplate.keys(redisKey + "*");
		if (CollectionUtils.isNotEmpty(set)) {
            Object key = null;
            while (set.iterator().hasNext()) {
                key = set.iterator().next();
                String st = key.toString();
                String sub = st.substring(st.lastIndexOf("_")+1);
                if(sub.equals(widget.getAppType())){
                	redisTemplate.delete(st);
                	break;
                }
            }
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ServiceResponse browserForWidget() {
		// TODO Auto-generated method stub
		ServiceResponse serviceResponse = new ServiceResponse();
        String redisKey = WIDGET_KEY_TYPE + "*";
       
        Set<String> keys = redisTemplate.keys(redisKey);
        if (CollectionUtils.isNotEmpty(keys)) {
        	List<Map<String, Object>> list =  new ArrayList<Map<String, Object>>();
            for (String str : keys) {
            	Map<String, Object> redisMap = (Map<String, Object>) redisTemplate.opsForValue().get(str);
            	list.add(redisMap);
			}
            serviceResponse.setData(list);
            
            return serviceResponse;
            
        }
        serviceResponse.setMessage(ResponseEnum.NOT_NEED_UPGRADE.getMsg());
        serviceResponse.setResult(ResponseEnum.NOT_NEED_UPGRADE.getCode());
        return serviceResponse;
        
	}
    
}
