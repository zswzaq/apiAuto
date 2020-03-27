package base.pojo;

import lombok.Data;

/**
 * @author ZS
 * @Description:   描述请求头信息
 * @date 2020/3/28 0:30
 */
@Data
public class Header {
    private String key;
    private String value;
    //可以这样设计
    //[{"key":"X-Lemonban-Media-Type","value":"lemonban.v1"},{"key":"zs","value":"abc"}]

    @Override
    public String toString() {
        return "Header{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
