package com.saphals2.whatsappstatussaver;

import android.os.Environment;

import java.io.File;

public class util {

    public static File RootDirectoryWhatsapp =
            new File(Environment.getExternalStorageDirectory()
            +"/Download/MyStorySaver/Whatsapp");

    public static void createfilefolder(){
        if (!RootDirectoryWhatsapp.exists())
            RootDirectoryWhatsapp.mkdirs();

    }

}
