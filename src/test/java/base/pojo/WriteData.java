package base.pojo;

import lombok.Data;

/**
 * @author ZS
 * @Description: 回写数据类
 * @date 2020/3/29 11:16
 */
@Data
public class WriteData {
    private int rowNo;//行号（从一开始)
    private int cellNo;//列号（从一开始）
    private String data;//写的内容

    public WriteData(int rowNo, int cellNo, String data) {
        this.rowNo = rowNo;
        this.cellNo = cellNo;
        this.data = data;
    }
}

