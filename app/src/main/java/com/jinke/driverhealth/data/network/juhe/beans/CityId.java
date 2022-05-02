package com.jinke.driverhealth.data.network.juhe.beans;

import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/5/2
 */
public class CityId {

    private String value;
    private String label;
    private List<ChildrenDTO> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ChildrenDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenDTO> children) {
        this.children = children;
    }

    public static class ChildrenDTO {
        private String value;
        private String label;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
