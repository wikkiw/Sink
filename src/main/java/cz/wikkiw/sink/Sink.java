package cz.wikkiw.sink;

import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author adam
 */
public interface Sink {
    
    public List<TreeMap<SinkKeysEnum, Object>> getRecordList();

    public void setRecordList(List<TreeMap<SinkKeysEnum, Object>> recordList);

    public TreeMap<SinkKeysEnum, Object> getDescription();

    public void setDescription(TreeMap<SinkKeysEnum, Object> description);
    
    public void addRecord(TreeMap<SinkKeysEnum, Object> record);
    public void printOut();
    
}
