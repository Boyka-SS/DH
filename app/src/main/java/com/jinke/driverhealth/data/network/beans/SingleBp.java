package com.jinke.driverhealth.data.network.beans;

/**
 * 最近一次血压数据
 *
 * @author: fanlihao
 * @date: 2022/2/17
 */
public class SingleBp {

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
        private String blood_rate;
        private String created;
        private String imei_sn;
        private int max_rate;
        private int min_rate;
        private String uuid;

        public String getBlood_rate() {
            return blood_rate;
        }

        public void setBlood_rate(String blood_rate) {
            this.blood_rate = blood_rate;
        }

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

        public int getMax_rate() {
            return max_rate;
        }

        public void setMax_rate(int max_rate) {
            this.max_rate = max_rate;
        }

        public int getMin_rate() {
            return min_rate;
        }

        public void setMin_rate(int min_rate) {
            this.min_rate = min_rate;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
