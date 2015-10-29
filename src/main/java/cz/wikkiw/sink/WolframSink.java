package cz.wikkiw.sink;

import cz.wikkiw.fitnessfunctions.objects.Individual;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adam
 */
public class WolframSink implements Sink {

    private List<TreeMap<SinkKeysEnum,Object>> recordList;
    private TreeMap<SinkKeysEnum,Object> description;

    public WolframSink(TreeMap<SinkKeysEnum, Object> description) {
        
        Date date = new java.util.Date();
        description.put(SinkKeysEnum.TIMESTAMP, new Timestamp(date.getTime()));
        
        this.description = description;
        this.recordList = new ArrayList<>();
    }

    @Override
    public void addRecord(TreeMap<SinkKeysEnum, Object> record) {

        Date date = new java.util.Date();
        record.put(SinkKeysEnum.TIMESTAMP, new Timestamp(date.getTime()));
        
        this.recordList.add(record);
        
    }

    @Override
    public void printOut() {
        
        String filename = "" + this.description.get(SinkKeysEnum.ALGORITHM) + this.description.get(SinkKeysEnum.DIMENSION) + "_" + this.description.get(SinkKeysEnum.TIMESTAMP) + ".txt";
        
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            
            writer.print("{");
            
            int i = 0;
            
            for(SinkKeysEnum key : this.description.keySet()){
                i++;
                
                writer.print("{" + key + "," + this.description.get(key) + "}");
                
                if(i < this.description.size()){
                    writer.print(",");
                }
                
            }
            
            writer.print(",");
            writer.print("{");
            
            i = 0;
            int j;
            Individual ind;
            
            for(TreeMap<SinkKeysEnum, Object> record : this.recordList){
                i++;
                
                writer.print("{");
                
                j = 0;
                
                for(SinkKeysEnum key : record.keySet()){

                    j++;
                    
                    if(key != SinkKeysEnum.INDIVIDUAL){
                        writer.print(record.get(key));
                    } else {
                        
                        ind = (Individual) record.get(key);
                        writer.print(ind.getFitness());
                        writer.print(",");
                        
                        writer.print("{");
                        
                        for(int k=0;k<ind.getFeatures().length-1;k++){
                            writer.print(ind.getFeatures()[k] + ",");
                        }
                        writer.print(ind.getFeatures()[ind.getFeatures().length-1]);
                        
                        writer.print("}");
                        
                    }
                    
                    if(j < record.size()){
                        writer.print(",");
                    }
                    
                }
                
                writer.print("}");
                
                if(i < this.recordList.size()){
                    writer.print(",");
                }
                
            }
            
            writer.print("}");
            writer.print("}");
            
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(WolframSink.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public List<TreeMap<SinkKeysEnum, Object>> getRecordList() {
        return recordList;
    }

    @Override
    public void setRecordList(List<TreeMap<SinkKeysEnum, Object>> recordList) {
        this.recordList = recordList;
    }

    @Override
    public TreeMap<SinkKeysEnum, Object> getDescription() {
        return description;
    }

    @Override
    public void setDescription(TreeMap<SinkKeysEnum, Object> description) {
        this.description = description;
    }
    
    
    
}
