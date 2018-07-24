package com.moxie.client.manager;

import com.proguard.annotation.NotProguard;

@NotProguard
/* compiled from: TbsSdkJava */
public class MoxieCallBackData {
    private String account = "";
    private String businessUserId = "";
    private int code = -1;
    private boolean loginDone = false;
    private String message = "";
    private String result = "";
    private String taskId = "";
    private String taskType = "";

    public String getBusinessUserId() {
        return this.businessUserId;
    }

    public void setBusinessUserId(String str) {
        this.businessUserId = str;
    }

    public boolean isLoginDone() {
        return this.loginDone;
    }

    public void setLoginDone(boolean z) {
        this.loginDone = z;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public void setTaskType(String str) {
        this.taskType = str;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String str) {
        this.taskId = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String str) {
        this.account = str;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("code=").append(this.code).append(", taskType=").append(this.taskType).append(", taskId=").append(this.taskId).append(", message=").append(this.message).append(", account=").append(this.account).append(", loginDone=").append(this.loginDone).append(", businessUserId=").append(this.businessUserId);
        return stringBuilder.toString();
    }
}
