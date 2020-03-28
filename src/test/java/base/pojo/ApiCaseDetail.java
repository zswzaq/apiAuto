package base.pojo;

import lombok.Data;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/27 00:31
 */

@Data
public class ApiCaseDetail {
    private String caseId;
    private String apiId;
    private String requestData;
    private String exceptedInfo;
    private ApiInfo apiInfo;

    @Override
    public String toString() {
        return "ApiCaseDetail{" +
                "caseId='" + caseId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", requestData='" + requestData + '\'' +
                ", exceptedInfo='" + exceptedInfo + '\'' +
                ", apiInfo=" + apiInfo +
                '}';
    }
}
