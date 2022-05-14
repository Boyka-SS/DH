package com.jinke.driverhealth.data.network.juhe.beans;

/**
 * @author: fanlihao
 * @date: 2022/5/3
 */
public class PolicyContent {

    private String reason;
    private ResultDTO result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultDTO {
        private FromInfoDTO from_info;
        private ToInfoDTO to_info;
        private Object from_covid_info;
        private Object to_covid_info;
        private String tc_tag;
        private String fc_tag;
        private String t_tag;
        private String f_tag;

        public FromInfoDTO getFrom_info() {
            return from_info;
        }

        public void setFrom_info(FromInfoDTO from_info) {
            this.from_info = from_info;
        }

        public ToInfoDTO getTo_info() {
            return to_info;
        }

        public void setTo_info(ToInfoDTO to_info) {
            this.to_info = to_info;
        }

        public Object getFrom_covid_info() {
            return from_covid_info;
        }

        public void setFrom_covid_info(Object from_covid_info) {
            this.from_covid_info = from_covid_info;
        }

        public Object getTo_covid_info() {
            return to_covid_info;
        }

        public void setTo_covid_info(Object to_covid_info) {
            this.to_covid_info = to_covid_info;
        }

        public String getTc_tag() {
            return tc_tag;
        }

        public void setTc_tag(String tc_tag) {
            this.tc_tag = tc_tag;
        }

        public String getFc_tag() {
            return fc_tag;
        }

        public void setFc_tag(String fc_tag) {
            this.fc_tag = fc_tag;
        }

        public String getT_tag() {
            return t_tag;
        }

        public void setT_tag(String t_tag) {
            this.t_tag = t_tag;
        }

        public String getF_tag() {
            return f_tag;
        }

        public void setF_tag(String f_tag) {
            this.f_tag = f_tag;
        }

        public static class FromInfoDTO {
            private String province_id;
            private String city_id;
            private String city_name;
            private String health_code_desc;
            private String health_code_gid;
            private String health_code_name;
            private String health_code_picture;
            private String health_code_style;
            private String high_in_desc;
            private String low_in_desc;
            private String out_desc;
            private String province_name;
            private String risk_level;

            public String getProvince_id() {
                return province_id;
            }

            public void setProvince_id(String province_id) {
                this.province_id = province_id;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public String getHealth_code_desc() {
                return health_code_desc;
            }

            public void setHealth_code_desc(String health_code_desc) {
                this.health_code_desc = health_code_desc;
            }

            public String getHealth_code_gid() {
                return health_code_gid;
            }

            public void setHealth_code_gid(String health_code_gid) {
                this.health_code_gid = health_code_gid;
            }

            public String getHealth_code_name() {
                return health_code_name;
            }

            public void setHealth_code_name(String health_code_name) {
                this.health_code_name = health_code_name;
            }

            public String getHealth_code_picture() {
                return health_code_picture;
            }

            public void setHealth_code_picture(String health_code_picture) {
                this.health_code_picture = health_code_picture;
            }

            public String getHealth_code_style() {
                return health_code_style;
            }

            public void setHealth_code_style(String health_code_style) {
                this.health_code_style = health_code_style;
            }

            public String getHigh_in_desc() {
                return high_in_desc;
            }

            public void setHigh_in_desc(String high_in_desc) {
                this.high_in_desc = high_in_desc;
            }

            public String getLow_in_desc() {
                return low_in_desc;
            }

            public void setLow_in_desc(String low_in_desc) {
                this.low_in_desc = low_in_desc;
            }

            public String getOut_desc() {
                return out_desc;
            }

            public void setOut_desc(String out_desc) {
                this.out_desc = out_desc;
            }

            public String getProvince_name() {
                return province_name;
            }

            public void setProvince_name(String province_name) {
                this.province_name = province_name;
            }

            public String getRisk_level() {
                return risk_level;
            }

            public void setRisk_level(String risk_level) {
                this.risk_level = risk_level;
            }
        }

        public static class ToInfoDTO {
            private String province_id;
            private String city_id;
            private String city_name;
            private String health_code_desc;
            private String health_code_gid;
            private String health_code_name;
            private String health_code_picture;
            private String health_code_style;
            private String high_in_desc;
            private String low_in_desc;
            private String out_desc;
            private String province_name;
            private String risk_level;

            public String getProvince_id() {
                return province_id;
            }

            public void setProvince_id(String province_id) {
                this.province_id = province_id;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public String getHealth_code_desc() {
                return health_code_desc;
            }

            public void setHealth_code_desc(String health_code_desc) {
                this.health_code_desc = health_code_desc;
            }

            public String getHealth_code_gid() {
                return health_code_gid;
            }

            public void setHealth_code_gid(String health_code_gid) {
                this.health_code_gid = health_code_gid;
            }

            public String getHealth_code_name() {
                return health_code_name;
            }

            public void setHealth_code_name(String health_code_name) {
                this.health_code_name = health_code_name;
            }

            public String getHealth_code_picture() {
                return health_code_picture;
            }

            public void setHealth_code_picture(String health_code_picture) {
                this.health_code_picture = health_code_picture;
            }

            public String getHealth_code_style() {
                return health_code_style;
            }

            public void setHealth_code_style(String health_code_style) {
                this.health_code_style = health_code_style;
            }

            public String getHigh_in_desc() {
                return high_in_desc;
            }

            public void setHigh_in_desc(String high_in_desc) {
                this.high_in_desc = high_in_desc;
            }

            public String getLow_in_desc() {
                return low_in_desc;
            }

            public void setLow_in_desc(String low_in_desc) {
                this.low_in_desc = low_in_desc;
            }

            public String getOut_desc() {
                return out_desc;
            }

            public void setOut_desc(String out_desc) {
                this.out_desc = out_desc;
            }

            public String getProvince_name() {
                return province_name;
            }

            public void setProvince_name(String province_name) {
                this.province_name = province_name;
            }

            public String getRisk_level() {
                return risk_level;
            }

            public void setRisk_level(String risk_level) {
                this.risk_level = risk_level;
            }
        }
    }
}
