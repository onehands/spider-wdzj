package com.stbbd.spider.wdzj.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by lei on 16-12-20.
 */
@Configuration
@PropertySource(value = {"classpath:db.properties", "classpath:mysql.properties"})
public class DBConfig {

    @Bean
    MapperScannerConfigurer setMapConfig() {
        MapperScannerConfigurer config = new MapperScannerConfigurer();
        config.setBasePackage("com.stbbd.spider.wdzj.mapper");
        config.setAnnotationClass(Mapper.class);
        return config;
    }
}
