package base.pojo;

import lombok.Data;

import java.util.List;

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
    private String tqRespData;//要提取数据的json数组
    private String actualRespond;//实际结果
    private List<SqlCheckInfo> beforeCheckList;//sql前置验证
    private List<SqlCheckInfo> afterCheckList;//sql后置验证

    @Override
    public String toString() {
        return "ApiCaseDetail{" +
                "caseId='" + caseId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", apiInfo=" + apiInfo +
                ", requestData='" + requestData + '\'' +
                ", exceptedInfo='" + exceptedInfo + '\'' +
                ", tqRespData='" + tqRespData + '\'' +
                ", actualRespond='" + actualRespond + '\'' +
                ", beforeCheckList=" + beforeCheckList +
                ", afterCheckList=" + afterCheckList +
                "} " + super.toString();
    }
}
