package matchhead.outputformatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class HTMLOutputFormatter implements HTMLOutputFormatable {

    PrintWriter printWriter;
    
    public HTMLOutputFormatter(String outPath) throws FileNotFoundException{
      printWriter = new PrintWriter(new File(outPath));   
    }
    
    public void close(){
        this.printWriter.close();
    }
   
    @Override
    public void formatOutput(String output) {
        if(printWriter!=null){
            printWriter.println(output);
            printWriter.flush();
        }
    }
        
}
