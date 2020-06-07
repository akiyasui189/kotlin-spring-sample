package io.spring.start.kotlin.sample.configurations

import org.apache.ibatis.session.ExecutorType
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
@MapperScan(basePackages = ["io.spring.start.kotlin.sample.repositories.mappers"])
class MybatisConfiguration (val dataSource: DataSource) {

    @Bean
    fun sqlSessionFactory (): SqlSessionFactory? {
        val sqlSessionFactoryBean: SqlSessionFactoryBean = SqlSessionFactoryBean()
        sqlSessionFactoryBean.setDataSource(dataSource)
        return sqlSessionFactoryBean.`object`
    }

    @Bean
    fun batchSqlSessionTemplate(): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH)
    }

    @Primary
    @Bean
    fun simpleSqlSessionTemplate(): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory(), ExecutorType.SIMPLE)
    }

}