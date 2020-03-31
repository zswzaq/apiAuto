package base.pojo;

import lombok.Data;

/**
 * @author ZS
 * @Description:
 * @date 2020/3/29 17:13
 */
@Data
public class SqlCheckInfo extends ExcelBase {
    private String sqlId;
    private String caseId;
    private String type;
    private String sql;
    private String expect;
    private String actual;
    private String checkRespond;

    @Override
    public String toString() {
        return "SqlCheckInfo{" +
                "sqlId='" + sqlId + '\'' +
                ", caseId='" + caseId + '\'' +
                ", type='" + type + '\'' +
                ", sql='" + sql + '\'' +
                ", expect='" + expect + '\'' +
                ", actual='" + actual + '\'' +
                ", checkRespond='" + checkRespond + '\'' +
                "} " + super.toString();
    }
}
