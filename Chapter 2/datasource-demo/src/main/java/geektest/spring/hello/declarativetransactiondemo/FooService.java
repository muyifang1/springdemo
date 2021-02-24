package geektest.spring.hello.declarativetransactiondemo;

/**
 * FooService interface for 注解式事务
 *
 * @author YangQi
 */
public interface FooService {

    void insertRecord();

    void insertThenRollback() throws RollbackException;

    void invokeInsertThenRollback() throws RollbackException;

    void invokeInsertThenRollbackByAopContext() throws RollbackException;

    void invokeInsertThenRollbackTestPropagation() throws RollbackException;
}
