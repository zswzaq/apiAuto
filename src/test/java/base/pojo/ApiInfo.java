package base.pojo;

import lombok.Data;

/**
 * @author ZS
 * @Description: 接口基本信息类
 * @date 2020/3/27 00:31
 */


@Data
public class ApiInfo extends ExcelBase {
    private String apiId;
    private String url;
    private String apiName;
    private String Type;
    private String headers;
    private String auth;//是否需要鉴权

    @Override
    public String toString() {
        return "ApiInfo{" +
                "apiId='" + apiId + '\'' +
                ", url='" + url + '\'' +
                ", apiName='" + apiName + '\'' +
                ", Type='" + Type + '\'' +
                ", headers='" + headers + '\'' +
                ", auth='" + auth + '\'' +
                "} " + super.toString();
    }
}
