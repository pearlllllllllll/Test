package com.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.catalina.manager.StatusManagerServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//Druid数据源配置
@Configuration
public class DruidConfig {
    //将自定义的datasourse加入到springboot中
    @ConfigurationProperties(prefix="spring.datasource")
    @Bean
    public DataSource druidDatasoure(){
        return new DruidDataSource();
    }
    //后台监控 ServletRegistrationBean
    //springBoot 内置了servlet容器,没有web.xml,想用web.xml用替代类 @Bean注入  bean = new ServletRegistrationBean<>()
    @Bean
    public ServletRegistrationBean statViewServlet(){
        //设置进入后台的url
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
//后台需要登录,账户密码
        HashMap<String,String> initParameters = new HashMap<>();
//增加配置 begin
        initParameters.put("loginUsername","admin");//登录key不能修改 loginUsername loginPassword
        initParameters.put("loginPassword","admin");//密码key不能修改
        //允许谁可以访问
        initParameters.put("allow", "");//如果为空,谁都可以访问 localhost只有本机可以访问
        //静止谁可以访问
//        initParameters.put("cx", "192.168.0.2");//前面是禁止的人,后面是ip地址
// 增加配置 end
        bean.setInitParameters(initParameters);//设置初始化参数
        return bean;
    }

//filter 过滤器
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        //可以过滤哪些请求
        Map<String,String> initParameters= new HashMap<>();
//增加配置 begin
        //这些东西不进行统计
        initParameters.put("exclusions","*.js,*.css,/druid/*");

// 增加配置 end
        bean.setInitParameters(initParameters);
        return bean;
    }



}
