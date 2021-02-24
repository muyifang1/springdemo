package geektest.spring.hello.simplejdecdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * FooDao 示例
 *
 * @author YangQi
 */
@Slf4j
@Repository
public class FooDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    /**
     * 插入数据Simple
     */
    public void insertData() {
        // 循环插入了 "b" 和 “c”
        Arrays.asList("b", "c").forEach(bar -> {
            jdbcTemplate.update("INSERT INTO FOO (BAR) VALUES (?)", bar);
        });

        // 通过simpleJdbcInsert 插入 d
        HashMap<String, String> row = new HashMap<>();
        row.put("BAR", "d");
        Number id1 = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("ID of d: {}", id1.longValue());
        // 通过simpleJdbcInsert 插入 e
        row.put("BAR", "e");
        Number id2 = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("ID of e: {}", id2.longValue());
    }

    public void listData() {
        log.info("Count: {}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO", Long.class));

        List<String> list = jdbcTemplate.queryForList("SELECT BAR FROM FOO", String.class);
        list.forEach(s -> log.info("Bar: {}", s));

        List<Foo> fooList = jdbcTemplate.query("SELECT * FROM FOO", new RowMapper<Foo>() {
            @Override
            public Foo mapRow(ResultSet resultSet, int i) throws SQLException {
                return Foo.builder()
                        .id(resultSet.getLong(1))
                        .bar(resultSet.getString(2))
                        .build();
            }
        });
        fooList.forEach(f -> log.info("Foo: {}", f));
    }
}
