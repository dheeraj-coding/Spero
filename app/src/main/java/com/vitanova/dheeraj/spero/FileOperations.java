package com.vitanova.dheeraj.spero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dheeraj on 20/1/18.
 */

public class FileOperations  {

    int flag;

    public FileOperations() {
        super();
        flag=0;
    }

    public Boolean write(String fname, String fcontent){
        try {

            String fpath = "/sdcard/"+fname+".txt";

            File file = new File(fpath);

            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fcontent);
            bw.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String read(String fname){

        BufferedReader br = null;
        String response = null;

        try {

            StringBuffer output = new StringBuffer();
            String fpath = "/sdcard/"+fname+".txt";

            br = new BufferedReader(new FileReader(fpath));
            String line = "";
            while ((line = br.readLine()) != null) {
                if(flag==0){
                    output.append(line);
                }else if(flag==1){
                    output.append(line);
                    output.append("/");
                }

            }
            response = output.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
        return response;
    }

}
