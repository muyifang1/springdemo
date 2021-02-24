package geektest.spring.hello.simplejdecdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 * SimpleJdbcDemo示例
 *
 * @author YangQi
 */
@SpringBootApplication
public class SimpleJdbcDemoApplication implements CommandLineRunner {

    @Autowired
    private FooDao fooDao;

    @Autowired
    private BatchFooDao batchFooDao;

    public static void main(String[] args) {
        SpringApplication.run(SimpleJdbcDemoApplication.class, args);
    }

    @Bean
    @Autowired
    public SimpleJdbcInsert simpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate).withTableName("FOO").usingGeneratedKeyColumns("ID");
    }

    @Override
    public void run(String... args) throws Exception {
//        fooDao.insertData();
        batchFooDao.batchInsert();
        fooDao.listData();
    }

}
