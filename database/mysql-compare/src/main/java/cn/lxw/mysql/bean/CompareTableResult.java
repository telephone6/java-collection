package cn.lxw.mysql.bean;

import java.util.List;
import java.util.Map;

/**
 * 功能描述: <br>
 * 〈The Result after comparing tables.〉
 * @Param:
 * @Return:
 * @Author: Administrator
 * @Date: 2021/6/4 18:26
 */
public class CompareTableResult {

    private List<String> lackTableList;
    private Map<String, List<String>> tblLackColMap;
    private Map<String,Map<String, List<String>>> tblColDiffMap;

    // getter、setter below

    public List<String> getLackTableList() {

        return lackTableList;
    }

    public void setLackTableList(List<String> lackTableList) {
        this.lackTableList = lackTableList;
    }

    public Map<String, List<String>> getTblLackColMap() {
        return tblLackColMap;
    }

    public void setTblLackColMap(Map<String, List<String>> tblLackColMap) {
        this.tblLackColMap = tblLackColMap;
    }

    public Map<String,Map<String, List<String>>> getTblColDiffMap() {
        return tblColDiffMap;
    }

    public void setTblColDiffMap(Map<String,Map<String, List<String>>> tblColDiffMap) {
        this.tblColDiffMap = tblColDiffMap;
    }
}
