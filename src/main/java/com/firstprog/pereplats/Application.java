package com.firstprog.pereplats;

import com.firstprog.pereplats.dao.RosDao;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class Application implements ApplicationRunner {
/*    @Autowired
    private LogsRepo logsRepo;*/


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

/*        Logs logs;
        String username = "system";
        String description =  "Запуск программы / Программный комплекс запущен ";
        logs = new Logs(new Date(), username, description);
        logsRepo.save(logs);*/
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "datasource2")
    @ConfigurationProperties(prefix = "ros.datasource.second")
    public DataSource rosDatasource() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(rosDatasource());
    }

    @Bean
    public RosDao rosDao() {
        RosDao rosDao = new RosDao();
        rosDao.setJdbcTemplate(jdbcTemplate());
        return rosDao;
    }


}