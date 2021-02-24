package geektest.spring.data.errorcodedemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ErrorCodeDemoApplicationTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 期待抛出自定义主键重复异常
    @Test(expected = CustomDuplicatedKeyException.class)
    public void testThrowingCustomException() {
        jdbcTemplate.execute("INSERT INTO FOO (ID,BAR) VALUES (1,'testDuplicateKeys')");
        jdbcTemplate.execute("INSERT INTO FOO (ID,BAR) VALUES (1,'testDuplicateKeys')");
    }
}