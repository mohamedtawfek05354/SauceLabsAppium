package Reuse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.invoke.MethodHandles.lookup;

public class CSVDataEntry {
    private final Logger log = LogManager.getLogger(lookup().lookupClass());
    private String filePath;
    private boolean append;
    public CSVDataEntry(String filePath, boolean append) {
        init();
        this.filePath = filePath;
        this.append = append;
    }
    public void writeRow(String Scenario,String Error,String numberOfRetry , String timeStamp) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))){

            writer.write(Scenario+","+Error+","+numberOfRetry+","+timeStamp);
            writer.newLine();
            writer.flush();
            log.info(" Data added to CSV: {}", Scenario+","+Error+","+numberOfRetry+","+timeStamp);
        } catch (IOException e) {
            log.error("Error writing to CSV: {}", e.getMessage());
        }
    }
    public void init(){
        filePath=null;
        append=true;
    }
}
