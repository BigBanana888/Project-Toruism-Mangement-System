package com.api.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.api.mapper")
public class MybatisPlusConfig {


    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
//
//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new MPJSqlInjector() {
//            @Override
//            public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
//                List<AbstractMethod> list = super.getMethodList(mapperClass, tableInfo);
//                //添加你的方法
//                list.add(new InsertBatchSomeColumn());
//                return list;
//            }
//        };
//    }
//    /**
//     * 关联SqlSessionFactory与GlobalConfig
//     * 设置mybatis 拦截器
//     */
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
//        // 关联SqlSessionFactory与GlobalConfig
//        GlobalConfig globalConfig = new GlobalConfig();
//        globalConfig.setSqlInjector(new MPJSqlInjector());
//        sessionFactory.setGlobalConfig(globalConfig);
//        // 添加拦截器 MPJInterceptor需要放在最后面
//        // 如果项目没有使用拦截器, 只需要添加MPJ拦截器sessionFactory.setPlugins(new MPJInterceptor());
//        sessionFactory.setPlugins(new MPJInterceptor());
//        // 其他配置 略
//        return sessionFactory.getObject();
//    }

}
