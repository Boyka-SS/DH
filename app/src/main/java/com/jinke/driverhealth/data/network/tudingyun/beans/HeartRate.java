package com.jinke.driverhealth.data.network.tudingyun.beans;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/1/20
 */
@Entity(tableName = "heart_rate")
public class HeartRate {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "heart_rate_id")
    private int id;
    @Embedded
    private DataDTO data;
    @Ignore
    private String message;



    @Ignore
    private int status;


    public HeartRate() {
    }

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
        @Ignore
        private List<ResultDTO> result;
        @Ignore
        private int total;

        public List<ResultDTO> getResult() {
            return result;
        }

        public void setResult(List<ResultDTO> result) {
            this.result = result;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public static class ResultDTO {
            @ColumnInfo(name = "生成时间")
            private String created;
            @ColumnInfo(name = "心率")
            private int heart_rate;
            @ColumnInfo(name = "设备号")
            private String imei_sn;
            @ColumnInfo(name = "UUID")
            private String uuid;

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public int getHeart_rate() {
                return heart_rate;
            }

            public void setHeart_rate(int heart_rate) {
                this.heart_rate = heart_rate;
            }

            public String getImei_sn() {
                return imei_sn;
            }

            public void setImei_sn(String imei_sn) {
                this.imei_sn = imei_sn;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }
    }
}
