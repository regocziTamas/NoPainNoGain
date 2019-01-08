package com.codecool.nopainnogain.sync;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class DatabaseSyncFileHandler {
    Context context;
    String updateDataFilename = "lastupdated";


    public DatabaseSyncFileHandler(Context context) {
        this.context = context;

    }

    private boolean checkLastUpdatedAvailable(){
        try {
            context.openFileInput(updateDataFilename);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    private File getUpdateFile(){
        File directory = context.getFilesDir();
        File file = new File(directory, updateDataFilename);
        return file;
    }

    private Long readLastUpdatedTimestamp(){
        File file = getUpdateFile();
        String timestamp = "";
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                timestamp += sc.nextLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return Long.valueOf(timestamp);
    }

    private void updateLastUpdatedTimestamp(Long timestamp){
        File file = getUpdateFile();
        try {
            FileOutputStream writer = new FileOutputStream(file,false);
            writer.write(String.valueOf(timestamp).getBytes());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long getLastUpdateTimestamp(){
        if(!checkLastUpdatedAvailable()){
            return 1500L;
        }else{
            return readLastUpdatedTimestamp();
        }
    }

}
