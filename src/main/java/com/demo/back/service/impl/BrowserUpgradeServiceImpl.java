package com.demo.back.service.impl;

import com.demo.back.common.page.PageResponse;
import com.demo.back.common.page.ServiceResponse;
import com.demo.back.controller.browser.browserupgrade.request.BrowserUpgradeRequest;
import com.demo.back.dao.BrowserUpgradeMapper;
import com.demo.back.entity.BrowserUpgrade;
import com.demo.back.enums.ResponseEnum;
import com.demo.back.exception.ParamNullException;
import com.demo.back.service.IBrowserUpgradeService;
import com.demo.back.utils.Constants;
import com.demo.back.utils.StringUtil;
import com.demo.back.utils.upload.UploadCommon;
import com.demo.back.utils.upload.UploadUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.demo.back.enums.ResponseEnum.NOT_NEED_UPGRADE;

//import org.springframework.transaction.event.TransactionalEventListener;


/**
 * @ClassName BrowserUpgradeServiceImpl
 * @Author yakun.shi
 * @Date 2019/6/27 9:21
 * @Version 1.0
 **/
@Service
public class BrowserUpgradeServiceImpl implements IBrowserUpgradeService {

    private static final String BROWSER_KEY_WIN = "browser_version_win:";

    private static final String BROWSER_KEY_MAC = "browser_version_mac:";

    @Autowired
    private BrowserUpgradeMapper browserUpgradeMapper;

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UploadUtils uploadUtils;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public ServiceResponse browserUpload(MultipartFile file) {
        ServiceResponse response = new ServiceResponse();
        try {
            String version = getVersion(file.getOriginalFilename());
            UploadCommon upload = uploadUtils.upload(file,"/browsers");
            Map<String, Object> map = new HashMap<>(16);
            map.put("fileName", upload.getOriginalFilename());
            map.put("filePath", upload.getDownLoadUrl());
            map.put("id", upload.getUuid());
            map.put("version", version);
            map.put("md5", upload.getMd5());
            response.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public ServiceResponse browserInsert(BrowserUpgrade request) {
        ServiceResponse response = new ServiceResponse();
        try {
            BrowserUpgrade browserUpgrade2 = browserUpgradeMapper.selectMaxVersionByType(request.getBrowserType());
            request.setCreateTime(new Date());
            //默认上传后为未审核状态
            request.setReviewStatus("1");
            Integer versionMark = 1;
            if (browserUpgrade2 != null) {
                versionMark = browserUpgrade2.getVersionMark() + 1;
            }
            request.setMd5(request.getMd5());
            request.setVersionMark(versionMark);
            browserUpgradeMapper.insert(request);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(ResponseEnum.BROWSER_UPLOAD_ERROR.getMsg());
            response.setResult(ResponseEnum.BROWSER_UPLOAD_ERROR.getCode());
            return response;
        }
    }


    @Override
    public PageResponse browserList(BrowserUpgradeRequest request) {
        List<Map<String, Object>> browserUpgrades = browserUpgradeMapper.browserList(request);
        Integer count = browserUpgradeMapper.browserListCount(request);
        return PageResponse.createResponse(count, browserUpgrades, request);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public ServiceResponse browserUpdate(BrowserUpgrade request) {
        ServiceResponse response = new ServiceResponse();
        if (StringUtils.isEmpty(request.getId())) {
            throw new ParamNullException();
        }
        BrowserUpgrade upgrade = browserUpgradeMapper.selectById(request);
        //审核通过后，更新数据库，然后同步redis中的缓存
        browserUpgradeMapper.browserUpdate(request);
        BrowserUpgrade browserUpgrade = browserUpgradeMapper.selectById(request);
        if (browserUpgrade != null) {
            handleRedisCache(browserUpgrade);
        } else {
            deleteRedisCache(upgrade);
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("id", request.getId());
        return response.setData(map);
    }

    @Override
    public ServiceResponse browserForClient(String browserType, Integer versionMark) {
        ServiceResponse serviceResponse = new ServiceResponse();
        // 升级类型为 win版
        String redisKey;
        if (Constants.BROWSER_TYPE_WIN.equals(browserType)) {
            redisKey = BROWSER_KEY_WIN + "*";
        } else {
            redisKey = BROWSER_KEY_MAC + "*";
        }
        Set keys = redisTemplate.keys(redisKey);
        if (CollectionUtils.isNotEmpty(keys)) {
            Object key = null;
            while (keys.iterator().hasNext()) {
                key = keys.iterator().next();
                break;
            }
            Map<String, Object> redisMap = (Map<String, Object>) redisTemplate.opsForValue().get(key);
            int redisVersionMark = Integer.parseInt(redisMap.get("versionMark").toString());
            // redis中的版本高于客户端版本，需要更新
            if (redisVersionMark > versionMark) {
                serviceResponse.setData(redisMap);
                return serviceResponse;
            }
        }
        serviceResponse.setMessage(NOT_NEED_UPGRADE.getMsg());
        serviceResponse.setResult(NOT_NEED_UPGRADE.getCode());
        return serviceResponse;
    }

    /**
     * @Method
     * @Author yakun.shi
     * @Description 把审核成功的下载地址放入缓存
     * @Return
     * @Date 2019/8/1 9:42
     */
    private void handleRedisCache(BrowserUpgrade browserUpgrade) {
        String redisKey = null;
        //安装类型为win版
        if (browserUpgrade.getBrowserType().equals(Constants.BROWSER_TYPE_WIN)) {
            redisKey = BROWSER_KEY_WIN;
        }
        //安装类型为mac版
        else if (browserUpgrade.getBrowserType().equals(Constants.BROWSER_TYPE_MAC)) {
            redisKey = BROWSER_KEY_MAC;
        }
        String db_downLoadUrl = browserUpgrade.getUrl();
        Integer newVersion = browserUpgrade.getVersionMark();
        //获取更新类型（1：强制，2：非强制）
        String constraintStatus = browserUpgrade.getConstraintStatus();
        //获取新的redis key
        String newKey = redisKey + browserUpgrade.getId();
        Set<String> set = redisTemplate.keys(redisKey + "*");
        Map<String, Object> map = new HashMap<>(16);
        map.put("downLoadUrl", db_downLoadUrl);
        map.put("constraintStatus", constraintStatus);
        map.put("versionMark", newVersion);
        map.put("browserName", browserUpgrade.getBrowserName());
        map.put("md5", browserUpgrade.getMd5());
        //判断缓存中是否存在set集合
        if (CollectionUtils.isNotEmpty(set)) {
            Object key = null;
            while (set.iterator().hasNext()) {
                key = set.iterator().next();
                break;
            }
            //获取redis中的浏览器key和版本号
            Map<String, Object> redisMap = (Map<String, Object>) redisTemplate.opsForValue().get(key);
            //redis中的版本号
            int redisOldVersion = Integer.parseInt(redisMap.get("versionMark").toString());
            if (newVersion > redisOldVersion) {
                //redis中的浏览器版本号 小于新上传的版本号,进行redis缓存更新
                //删除原有缓存的key值
                redisTemplate.delete(key);
                redisTemplate.opsForValue().set(newKey, map);
            }
        } else {
            redisTemplate.opsForValue().set(newKey, map);
        }
    }

    private void deleteRedisCache(BrowserUpgrade upgrade) {
        String redisKey = null;
        //安装类型为win版
        if (upgrade.getBrowserType().equals(Constants.BROWSER_TYPE_WIN)) {
            redisKey = BROWSER_KEY_WIN + upgrade.getId();
        }
        //安装类型为mac版
        else if (upgrade.getBrowserType().equals(Constants.BROWSER_TYPE_MAC)) {
            redisKey = BROWSER_KEY_MAC + upgrade.getId();
        }
        redisTemplate.delete(redisKey);
    }

    /**
     * @return com.dg.back.entity.BrowserUpgrade
     * @Author yakun.shi
     * @Description //处理解析 浏览器上传的格式和信息
     * @Date 2019/7/11 9:05
     * @Param [browserUpgrade]
     **/
    private String getVersion(String originalFilename) throws Exception {
        String[] split = originalFilename.split("_");
        String string_version = "未知";
        if (split != null && split.length > 1) {
            int i = split[1].lastIndexOf(".");
            string_version = split[1].substring(0, i);
        }
        if (!StringUtil.isExe(originalFilename)) {
            throw new Exception();
        }
        return string_version;
    }

}

