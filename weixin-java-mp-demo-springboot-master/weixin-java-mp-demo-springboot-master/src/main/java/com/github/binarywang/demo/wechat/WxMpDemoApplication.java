package com.github.binarywang.demo.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.common.system.dao.ActDao;
import com.common.system.mapper.RcMenuMapper;
import com.common.system.service.WxDetailService;
import com.common.system.service.WxUserService;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@SpringBootApplication
@ComponentScan(basePackageClasses={WxUserService.class,WxDetailService.class, ActDao.class})  
public class WxMpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxMpDemoApplication.class, args);
    }
}
