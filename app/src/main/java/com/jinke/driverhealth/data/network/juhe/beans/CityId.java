package com.jinke.driverhealth.data.network.juhe.beans;

import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/5/2
 */
public class CityId {


    private List<ChildrenDTO> children;
    private String label;
    private String value;

    public List<ChildrenDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenDTO> children) {
        this.children = children;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static class ChildrenDTO {
        private String label;
        private String value;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
