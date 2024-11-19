package com.ddogdog.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.ddogdog.model.dao")
public class DBConfig {

}
