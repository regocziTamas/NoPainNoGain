package com.codecool.nopainnogain.sync;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class DatabaseSyncer {

    String baseUrl = "https://fc8eccdc.ngrok.io";
    DatabaseSyncFileHandler fileHandler;
    Context context;

    public DatabaseSyncer(Context context) {
        this.context = context;
    }
}
