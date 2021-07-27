package cn.lxw.frame.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "cn.lxw.frame.coursedb",sqlSessionFactoryRef = "courseSqlSessionFactory")
public class CourseMybatisConfig {

    @Autowired
    @Qualifier("courseDataSource")
    private DataSource ds;

    @Bean(name = "courseSqlSessionFactory")
    public SqlSessionFactory courseSqlSessionFactory() throws Exception {
        //配置myabtisSqlSession
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        // 指明mapper.xml位置(配置文件中指明的xml位置会失效用此方式代替，具体原因未知)
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/coursedb/*Mapper.xml"));
        // 指明实体扫描(多个package用逗号或者分号分隔)
        sessionFactoryBean.setTypeAliasesPackage("cn.frame.lxw.entity");
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        // mybatisConfiguration.setJdbcTypeForNull(JdbcType.NULL);
        //驼峰
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        //是否开启缓存
        mybatisConfiguration.setCacheEnabled(false);
        //多数据源下分页模式
        mybatisConfiguration.addInterceptor(new PaginationInterceptor());
        // 配置打印sql语句
        mybatisConfiguration.setLogImpl(StdOutImpl.class);
        sessionFactoryBean.setConfiguration(mybatisConfiguration);
        //数据源注入
        sessionFactoryBean.setDataSource(ds);
        return sessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateBusiness() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(courseSqlSessionFactory()); // 使用上面配置的Factory
        return template;
    }

}
