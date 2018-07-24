package com.moxie.client.fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GanQuan on 2017/9/15.
 */

public class LogEntry {
    public String method_name;
    public List<String> params = new ArrayList<>();

    public LogEntry(String method, String... params) {
        this.method_name = method;
        for (String str : params) {
            this.params.add(str);
        }
    }

    @Override
    public String toString() {
        return "{" +
                "method_name='" + method_name + '\'' +
                "\n params=" + params +
                '}';
    }
}
