package healthcaremanagementsystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    final static String DBADMIN = "admin.json"; /* admin credentials */
    final static String DBFILE = "data.json";   /* Database filename */
    
    /* Shorter and more convenient way to output on console */
    private static void log(String string) {
        System.out.println(string);
    }
    
    public static Object loadData(java.lang.Class c, String filename) {
        File file = new File(filename);
        
        if (!file.exists()) {
            log("File '" + filename + "' doesn't exist!");
        } else {
            InputStreamReader isReader;
            try {
                isReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
                JsonReader myReader = new JsonReader(isReader);
                Gson gson = new Gson();
                Object data = gson.fromJson(myReader, c);
                return data;
            } catch (UnsupportedEncodingException | FileNotFoundException ex) {
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public static void saveData(Object obj, String filename) {
        File file = new File(filename);
        
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log("Excepton Occured: " + e.toString());
            }
        }
        
        try {
            FileWriter fileWriter;
            fileWriter = new FileWriter(file.getAbsoluteFile(), false);
            
            BufferedWriter bufferWriter;
            bufferWriter = new BufferedWriter(fileWriter);
            
            Gson gson = new Gson();
            bufferWriter.write(gson.toJson(obj));
            bufferWriter.close();
        } catch (IOException e) {
            log("Excepton Occured: " + e.toString());
        }
    }
}
