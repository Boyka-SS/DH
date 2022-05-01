package com.jinke.driverhealth.data.network.baidu.beans;

/**
 * @author: fanlihao
 * @date: 2022/5/1
 */
public class BehaviorMonitorError {

    private long log_id;
    private String error_msg;
    private int error_code;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }
}
