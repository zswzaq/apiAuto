package base.pojo;

import lombok.Data;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/27 00:31
 */

@Data
public class ApiInfo {
    private String apiId;
    private String url;
    private String apiName;
    private String Type;

    @Override
    public String toString() {
        return "ApiInfo{" +
                "apiId='" + apiId + '\'' +
                ", url='" + url + '\'' +
                ", apiName='" + apiName + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }
}
