package geektest.spring.hello.programmatictransactiondemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 编程式事务Demo
 *
 * @author YangQi
 */
@SpringBootApplication
@Slf4j
public class ProgrammaticTransactionDemoApplication implements CommandLineRunner {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ProgrammaticTransactionDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Count before Transaction:{}", getCount());

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                jdbcTemplate.execute("INSERT INTO FOO (ID,BAR) VALUES (888,'testTrans')");
                log.info("Count in transaction:{}", getCount());
                // rollback in transaction
                transactionStatus.setRollbackOnly();
            }
        });

        log.info("Count after Transaction:{}", getCount());
    }

    private long getCount() {
        return (long) jdbcTemplate.queryForList("SELECT COUNT(*) AS CNT FROM FOO").get(0).get("CNT");
    }
}
