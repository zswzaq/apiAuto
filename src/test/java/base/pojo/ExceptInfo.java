package base.pojo;

import lombok.Data;

/**
 * @author ZS
 * @Description: 期望结果，jsonPath提取类
 * @date 2020/3/28 16:16
 */

@Data
public class ExceptInfo {
    private String jsonStr;//提取jsonPath方法
    private Object expected;//期望的值
}
