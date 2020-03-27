package base.pojo;

import lombok.Data;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/26 23:51
 */

@Data
public class ApiCaseDemo {
    private String caseID;
    private String Url;
    private String requestData;

    @Override
    public String toString() {
        return "ApiCase{" +
                "caseID='" + caseID + '\'' +
                ", Url='" + Url + '\'' +
                ", requestData='" + requestData + '\'' +
                '}';
    }
}
