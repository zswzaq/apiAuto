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

    @Override
    public String toString() {
        return super.toString() + "ApiInfo{" +
                "apiId='" + apiId + '\'' +
                ", url='" + url + '\'' +
                ", apiName='" + apiName + '\'' +
                ", Type='" + Type + '\'' +
                ", headers='" + headers + '\'' +
                "} ";
    }
}
