package com.moxie.client.crash;

import android.content.Context;
import com.moxie.client.utils.IOUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

/* compiled from: TbsSdkJava */
final class CrashReportPersister {
    public static final String utf8 = "UTF-8";
    private final Context b;

    CrashReportPersister(Context context) {
        this.b = context;
    }

    public final CrashReportData a(String str) throws IOException {
        InputStream openFileInput = this.b.openFileInput(str);
        if (openFileInput == null) {
            throw new IllegalArgumentException("Invalid crash report fileName : " + str);
        }
        try {
            InputStream bufferedInputStream = new BufferedInputStream(openFileInput, 8192);
            bufferedInputStream.mark(Integer.MAX_VALUE);
            bufferedInputStream.reset();
            CrashReportData a = a(new InputStreamReader(bufferedInputStream, utf8));
            return a;
        } finally {
            openFileInput.close();
        }
    }

    public final void a(CrashReportData crashReportData, String str) throws IOException {
        FileOutputStream openFileOutput = this.b.openFileOutput(str, 0);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(openFileOutput, "UTF-8"));
            bufferedWriter.write(JSONReportBuilder.a(crashReportData).toString());
            bufferedWriter.flush();
        } catch (Exception e) {
            String str2 = ErrorReporter.a;
        } finally {
            IOUtils.close(openFileOutput);
            IOUtils.close(openFileOutput);
        }
    }

    private synchronized CrashReportData a(Reader reader) throws IOException {
        CrashReportData a=null;
        BufferedReader bufferedReader = new BufferedReader(reader, 8192);
        try {
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuilder.append(readLine);
            }
            a = JSONReportBuilder.a(stringBuilder.toString());
            IOUtils.close(bufferedReader);
        } catch (Exception e) {
            String str = ErrorReporter.a;
            IOUtils.close(bufferedReader);
            a = null;
        } catch (Throwable th) {
            IOUtils.close(bufferedReader);
        }
        return a;
    }
}
