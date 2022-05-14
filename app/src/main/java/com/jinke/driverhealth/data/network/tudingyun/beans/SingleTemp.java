package com.jinke.driverhealth.data.network.tudingyun.beans;

/**
 * @author: fanlihao
 * @date: 2022/2/17
 */
public class SingleTemp {

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
        private String created;
        private String imei_sn;
        private String temperature;
        private String uuid;

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getImei_sn() {
            return imei_sn;
        }

        public void setImei_sn(String imei_sn) {
            this.imei_sn = imei_sn;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
