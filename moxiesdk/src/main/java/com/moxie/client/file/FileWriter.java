package com.moxie.client.file;

import java.io.File;

/* compiled from: TbsSdkJava */
public class FileWriter {
    private final FileWriter a = this;

    public static boolean a(File file, boolean z) {
        if (file != null && file.isDirectory()) {
            String[] list = file.list();
            for (String file2 : list) {
                if (!b(new File(file, file2))) {
                    return false;
                }
            }
            if (z) {
                return b(file);
            }
        }
        return true;
    }

    public static boolean a(File file) {
        if (file.exists() && file.isDirectory()) {
            return true;
        }
        return file.mkdirs();
    }

    private static boolean b(File file) {
        if (!a(file, false)) {
            return false;
        }
//        if (file != null) {
//            return file.delete();
//        }
        return true;
    }
}
