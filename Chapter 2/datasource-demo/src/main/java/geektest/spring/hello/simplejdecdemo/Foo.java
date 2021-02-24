package geektest.spring.hello.simplejdecdemo;

import lombok.Builder;
import lombok.Data;

/**
 * Foo 一个Data Bean例子
 * 这里导入了 Lomok 简化了代码，省略了setter, getter
 *
 * @author YangQi
 */
@Data
@Builder
public class Foo {
    private Long id;
    private String bar;
}
