package com.demo.back.shiro.filter;

import com.demo.back.dao.AuthResourcesMapper;
import com.demo.back.dao.AuthUserMapper;
import com.demo.back.entity.AuthUser;
import com.demo.back.shiro.config.ICustomFilterChainDefinitions;
import com.demo.back.shiro.util.ShiroUtil;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author yakun.shi
 * @Description //TODO
 * @Date 2019/7/11 15:39
 **/
@Component
public class CustomFilterChainDefinitions implements ICustomFilterChainDefinitions {

    private static Logger logger = LoggerFactory.getLogger(CustomFilterChainDefinitions.class);

    @Autowired
    private AuthResourcesMapper authResourcesMapper;

    @Autowired
    private AuthUserMapper authUserMapper;

    /**
     * 初始化权限
     */
    @Override
    public Map<String, String> loadFilterChainDefinitions() {
        // 权限控制map.从数据库获取

        Map<String, String> filter = getAllowPermission();
        //拦截所有请求
        filter.put("/**", "authc");
//        filter.put("/**", "anon");
        return filter;
    }

    /**
     * @return java.util.Map<java.lang.String       ,       java.lang.String>
     * @Author yakun.shi
     * @Description //放行的权限地址
     * @Date 2019/7/11 15:38
     * @Param []
     **/
    private Map<String, String> getAllowPermission() {
        Map<String, String> filter = new LinkedHashMap<>();
        filter.put("/login", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filter.put("/logout", "anon");

        //客户端升级包下载
        filter.put("/browser/client/detail", "anon");
        //控件升级包下载
        filter.put("/widget/client/detail", "anon");

        // 客户端获取消息
        filter.put("/notice/client/detail", "anon");
        //客户端获取轮播图
        filter.put("/banner/client/detail", "anon");
        //客户端收集日志
        filter.put("/log/browser/insert", "anon");


        //放行静态文件
        filter.put("/login.html", "anon");
        filter.put("/static/**", "anon");
        filter.put("/views/**", "anon");
        //放行swagger
        filter.put("/swagger-ui.html", "anon");
        filter.put("/swagger-resources/**", "anon");
        filter.put("/v2/api-docs", "anon");
        filter.put("/webjars/springfox-swagger-ui/**", "anon");
        return filter;
    }

    /**
     * @return java.util.Map<java.lang.String       ,       java.lang.String>
     * @Author yakun.shi
     * @Description //获取每个角色的 访问权限
     * @Date 2019/7/11 15:32
     * @Param [filterChainDefinitionMap]
     **/
    private Map<String, String> getPermission() {
        Map<String, String> filter = new LinkedHashMap<>();
        //获取url
        List<HashMap<String, Object>> list = authResourcesMapper.findRoleNameAndResourceUrl();
        System.out.println(list.toString());
        Map<String, List<HashMap<String, Object>>> url = list.stream().collect(Collectors.groupingBy(t -> t.get("resourceurl").toString()));
        Iterator<Map.Entry<String, List<HashMap<String, Object>>>> iterator = url.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<HashMap<String, Object>>> next = iterator.next();
            String key = next.getKey();
            List<HashMap<String, Object>> value = next.getValue();
            String shirorolename = value.stream().filter(t -> !"admin".equals(t.get("rolename"))).map(t -> t.get("rolename")).distinct().collect(Collectors.toList()).toString();
            String replace = shirorolename.replace(" ", "");
            //如果数据中没有查到url,默认角色为admin
            if (("[]").equals(replace)) {
                filter.put(key, "roles[admin]");
                continue;
            }
            //给每个url赋予admin权限
            String roles = new StringBuffer(replace.substring(0, replace.length() - 1)).append(",admin]").toString();
            filter.put(key, "roles" + roles);
        }
        return filter;
    }

    /**
     * 在对角色进行增删改操作时，需要调用此方法进行动态刷新
     *
     * @param shiroFilterFactoryBean
     */
    @Override
    public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean, String roleId) {
        synchronized (this) {
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
                throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
            // 清空老的权限控制
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(loadFilterChainDefinitions());
            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }

            //根据角色id来获取用户
            List<AuthUser> users = authUserMapper.findByRoleId(roleId);
            // TODO: 2019/6/13
            if (users.size() == 0) {
                return;
            }
            for (AuthUser user : users) {
                ShiroUtil.kickOutUser(user.getLoginUser(), false);
            }
        }
    }
}
