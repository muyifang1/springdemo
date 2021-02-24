package geektest.spring.data.errorcodedemo;

import org.springframework.dao.DuplicateKeyException;

/**
 * 自定义重复主键异常
 *
 * @author YangQi
 */
public class CustomDuplicatedKeyException extends DuplicateKeyException {

    public CustomDuplicatedKeyException(String msg) {
        super(msg);
    }

    public CustomDuplicatedKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
