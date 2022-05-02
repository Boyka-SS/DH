package com.jinke.driverhealth.data.network.juhe.beans;

import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/5/2
 */
public class RiskRegion {

    private int error_code;
    private String reason;
    private ResultDTO result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

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

    public static class ResultDTO {
        private String high_count;
        private List<HighListDTO> high_list;
        private String middle_count;
        private List<MiddleListDTO> middle_list;
        private String updated_date;

        public String getHigh_count() {
            return high_count;
        }

        public void setHigh_count(String high_count) {
            this.high_count = high_count;
        }

        public List<HighListDTO> getHigh_list() {
            return high_list;
        }

        public void setHigh_list(List<HighListDTO> high_list) {
            this.high_list = high_list;
        }

        public String getMiddle_count() {
            return middle_count;
        }

        public void setMiddle_count(String middle_count) {
            this.middle_count = middle_count;
        }

        public List<MiddleListDTO> getMiddle_list() {
            return middle_list;
        }

        public void setMiddle_list(List<MiddleListDTO> middle_list) {
            this.middle_list = middle_list;
        }

        public String getUpdated_date() {
            return updated_date;
        }

        public void setUpdated_date(String updated_date) {
            this.updated_date = updated_date;
        }

        public static class HighListDTO {
            private String area_name;
            private String city;
            private List<String> communitys;
            private String county;
            private String county_code;
            private String province;
            private String type;

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public List<String> getCommunitys() {
                return communitys;
            }

            public void setCommunitys(List<String> communitys) {
                this.communitys = communitys;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public String getCounty_code() {
                return county_code;
            }

            public void setCounty_code(String county_code) {
                this.county_code = county_code;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class MiddleListDTO {
            private String area_name;
            private String city;
            private List<String> communitys;
            private String county;
            private String county_code;
            private String province;
            private String type;

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public List<String> getCommunitys() {
                return communitys;
            }

            public void setCommunitys(List<String> communitys) {
                this.communitys = communitys;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public String getCounty_code() {
                return county_code;
            }

            public void setCounty_code(String county_code) {
                this.county_code = county_code;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
