package lab2.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RequestsHistoryBean implements Serializable{
    
    private List<RecordBean> records = new ArrayList<>();

    public void addRecord(RecordBean record) {
        this.records.add(record);
    }

    public List<RecordBean> getRecords() {
        return this.records;
    }

}
