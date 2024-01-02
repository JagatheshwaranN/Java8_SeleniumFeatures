package concepts.browsers.edge.logs;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static File getTempFile(String fileName, String fileExtension){
        File logFileLocation = null;
        try{
            logFileLocation = File.createTempFile(fileName, fileExtension);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logFileLocation.deleteOnExit();
        return logFileLocation;
    }
}