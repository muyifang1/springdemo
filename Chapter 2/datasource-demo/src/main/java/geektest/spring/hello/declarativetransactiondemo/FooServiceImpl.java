package geektest.spring.hello.declarativetransactiondemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * FooServiceImpl
 *
 * @author YangQi
 */
@Component
@Slf4j
public class FooServiceImpl implements FooService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private FooService fooService;

    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
    }

    @Override
    // 事务处理指定如果出现指定异常则回滚，Propagation.REQUIRES_NEW 指定该方法新起独立事务 ,NESTED 在原事务内启动一个内联事务，外部回滚，内嵌事务也回滚
    @Transactional(rollbackFor = RollbackException.class, propagation = Propagation.NESTED)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('testForTrans BBB')");
//        throw new RollbackException();
    }

    // 这个方法里面事务没有回滚！
    // 原因：这里使用动态代理机制，这个类内部调用没有触发代理类，而这个方法本身没有指定事务处理，所以不会回滚！
    @Override
    public void invokeInsertThenRollback() throws RollbackException {
        insertThenRollback();
    }

    // 与上面一个方法做对比，这个内部调用会有事务回滚
    @Override
    public void invokeInsertThenRollbackByAopContext() throws RollbackException {
        // 注意：这里也可以把 FooService 自身注入 然后直接调用 fooService.insertThenRollback()
        ((FooService) (AopContext.currentProxy())).insertThenRollback();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void invokeInsertThenRollbackTestPropagation() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('testForTrans CCC')");
        try {
            fooService.insertThenRollback();
        } catch (RollbackException e) {
            log.error("RollbackException", e);
        }
        throw new RuntimeException();
    }
}
