package com.jinke.driverhealth.beans;

/**
 * @author: fanlihao
 * @date: 2022/1/16
 */
public class Token {
    private DataDTO data;
    private String message;
    private int status;

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataDTO {
        private String expired;
        private String token;

        public String getExpired() {
            return expired;
        }

        public void setExpired(String expired) {
            this.expired = expired;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    @Override
    public String toString() {
        return "Token{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
