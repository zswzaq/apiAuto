package base.pojo;

import lombok.Data;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/27 00:31
 */

@Data
public class ApiCaseDetail extends ExcelBase {
    //private Integer rowNo;//行号
    private String caseId;
    private String apiId;
    private ApiInfo apiInfo;
    private String requestData;//请求结果
    private String exceptedInfo;//预期结果
    private String actualRespond;//实际结果

    @Override
    public String toString() {
        return super.toString() + "ApiCaseDetail{" +
                "caseId='" + caseId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", apiInfo=" + apiInfo +
                ", requestData='" + requestData + '\'' +
                ", exceptedInfo='" + exceptedInfo + '\'' +
                ", actualRespond=" + actualRespond +
                "} ";
    }

}
