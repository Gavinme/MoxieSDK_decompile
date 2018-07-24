package com.moxie.client.tasks.event;

import com.moxie.client.model.SiteAccountInfo;
import com.moxie.client.model.TaskStatusDescription;
import com.moxie.client.model.TaskStatusResult;

/* compiled from: TbsSdkJava */
public class TaskStatusEvent {

    /* compiled from: TbsSdkJava */
    static class TaskStatusEventBase {
        public int a = 0;
        public String b;
        public SiteAccountInfo c;
        public TaskStatusResult d;

        public TaskStatusEventBase(int i, String str, SiteAccountInfo siteAccountInfo, TaskStatusResult taskStatusResult) {
            this.a = i;
            this.b = str;
            this.c = siteAccountInfo;
            this.d = taskStatusResult;
        }
    }

    /* compiled from: TbsSdkJava */
    public static class TaskStatusFinishErrorEvent extends TaskStatusEventBase {
        public boolean e = false;

        public TaskStatusFinishErrorEvent(int i, String str, SiteAccountInfo siteAccountInfo, TaskStatusResult taskStatusResult) {
            super(i, str, siteAccountInfo, taskStatusResult);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class TaskStatusFinishSuccessEvent extends TaskStatusEventBase {
        public boolean e = false;

        public TaskStatusFinishSuccessEvent(int i, String str, SiteAccountInfo siteAccountInfo, TaskStatusResult taskStatusResult) {
            super(i, str, siteAccountInfo, taskStatusResult);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class TaskStatusProgressEvent extends TaskStatusEventBase {
        public TaskStatusDescription e;

        public TaskStatusProgressEvent(TaskStatusDescription taskStatusDescription) {
            super(0, taskStatusDescription.a, null, null);
            this.e = taskStatusDescription;
        }
    }

    /* compiled from: TbsSdkJava */
    public static class TaskStatusVerifycodeErrorEvent extends TaskStatusEventBase {
        public TaskStatusVerifycodeErrorEvent(String str, SiteAccountInfo siteAccountInfo) {
            super(-1, str, siteAccountInfo, null);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class TaskStatusVerifycodeEvent extends TaskStatusEventBase {
        public TaskStatusVerifycodeEvent(String str, SiteAccountInfo siteAccountInfo, TaskStatusResult taskStatusResult) {
            super(0, str, siteAccountInfo, taskStatusResult);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class TaskStatusVerifycodeSuccessEvent extends TaskStatusEventBase {
        public TaskStatusVerifycodeSuccessEvent(int i, String str, SiteAccountInfo siteAccountInfo, TaskStatusResult taskStatusResult) {
            super(i, str, siteAccountInfo, taskStatusResult);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class TaskStatusWorkingEvent extends TaskStatusEventBase {
        public TaskStatusDescription e;

        public TaskStatusWorkingEvent(TaskStatusDescription taskStatusDescription, SiteAccountInfo siteAccountInfo, TaskStatusResult taskStatusResult) {
            super(0, taskStatusDescription.a, siteAccountInfo, taskStatusResult);
            this.e = taskStatusDescription;
        }
    }
}
