package base.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ZS
 * @Description: 提取数据的json数组
 * @date 2020/3/31 23:54
 */

@Getter
@Setter
@ToString
public class TqRespData {
    private String jsonStr;//参数的jsonPath，用于提取数据
    private String paramName;//参数名，保存到全局数据中的key
}
