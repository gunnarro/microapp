package com.gunnarro.calendar.cache;

import java.util.Arrays;

//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.concurrent.ConcurrentMapCache;
//import org.springframework.cache.ehcache.EhCacheCacheManager;
//import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
//import org.springframework.cache.support.SimpleCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;


//@Configuration
//@EnableCaching
public class CacheConfig {

//    @Bean
//    public CacheManager cacheManager() {
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        cacheManager.setCaches(Arrays.asList(
//          new ConcurrentMapCache("calendarevents"), 
//          new ConcurrentMapCache("entries")));
//        return cacheManager;
//    }
    
//    @Bean
//    public CacheManager getEhCacheManager(){
//            return  new EhCacheCacheManager(getEhCacheFactory().getObject());
//    }
//    @Bean
//    public EhCacheManagerFactoryBean getEhCacheFactory(){
//        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
//        factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
//        factoryBean.setShared(true);
//        return factoryBean;
//    }
}