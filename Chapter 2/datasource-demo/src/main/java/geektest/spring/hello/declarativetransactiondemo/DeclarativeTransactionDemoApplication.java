package geektest.spring.hello.declarativetransactiondemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 注解使用事务，并指定动态代理模式实现 @EnableTransactionManagement(mode = AdviceMode.PROXY)
@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.PROXY) // 注解使用事务，并指定动态代理模式实现
@Slf4j
public class DeclarativeTransactionDemoApplication implements CommandLineRunner {

    @Autowired
    private FooService fooService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DeclarativeTransactionDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        fooService.insertRecord();
//        log.info("fooService.insertRecord {}",
//                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='AAA'", Long.class));
//
//        try {
//            fooService.insertThenRollback();
//        } catch (Exception e) {
//            log.info("fooService.insertThenRollback {}",
//                    jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='testForTrans BBB'", Long.class));
//        }
//
//        try {
//            // invokeInsertThenRollback() 内部使用(AopContext.currentProxy())调用当前代理对象
//            fooService.invokeInsertThenRollbackByAopContext();
//        } catch (Exception e) {
//            log.info("fooService.invokeInsertThenRollbackByAopContext {}",
//                    jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='testForTrans BBB'", Long.class));
//        }
//
//        try {
//            // invokeInsertThenRollback()方法本身没有事务，内部调用的insertThenRollback()虽然有事务回滚 但是没有触发内部代理类调用
//            fooService.invokeInsertThenRollback();
//        } catch (Exception e) {
//            log.info("fooService.invokeInsertThenRollback {}",
//                    jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='testForTrans BBB'", Long.class));
//        }

        try {
            fooService.invokeInsertThenRollbackTestPropagation();
        } catch (Exception e) {
            log.info("fooService.invokeInsertThenRollback {}",
                    jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='testForTrans BBB'", Long.class));
        }

        log.info("testForTrans AAA {}",
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='testForTrans AAA'", Long.class));
        log.info("testForTrans BBB {}",
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='testForTrans BBB'", Long.class));
        log.info("testForTrans CCC {}",
                jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='testForTrans CCC'", Long.class));

    }
}
