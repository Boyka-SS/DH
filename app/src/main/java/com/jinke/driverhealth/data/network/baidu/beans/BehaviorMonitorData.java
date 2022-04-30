package com.jinke.driverhealth.data.network.baidu.beans;

import java.util.List;

/**
 * @author: fanlihao
 * @date: 2022/5/1
 */
public class BehaviorMonitorData {

    private int person_num;
    private List<PersonInfoDTO> person_info;
    private int driver_num;
    private long log_id;

    public int getPerson_num() {
        return person_num;
    }

    public void setPerson_num(int person_num) {
        this.person_num = person_num;
    }

    public List<PersonInfoDTO> getPerson_info() {
        return person_info;
    }

    public void setPerson_info(List<PersonInfoDTO> person_info) {
        this.person_info = person_info;
    }

    public int getDriver_num() {
        return driver_num;
    }

    public void setDriver_num(int driver_num) {
        this.driver_num = driver_num;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public static class PersonInfoDTO {
        private AttributesDTO attributes;
        private LocationDTO location;

        public AttributesDTO getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesDTO attributes) {
            this.attributes = attributes;
        }

        public LocationDTO getLocation() {
            return location;
        }

        public void setLocation(LocationDTO location) {
            this.location = location;
        }

        public static class AttributesDTO {
            private BothHandsLeavingWheelDTO both_hands_leaving_wheel;
            private EyesClosedDTO eyes_closed;
            private NoFaceMaskDTO no_face_mask;
            private NotBucklingUpDTO not_buckling_up;
            private SmokeDTO smoke;
            private NotFacingFrontDTO not_facing_front;
            private CellphoneDTO cellphone;
            private YawningDTO yawning;
            private HeadLoweredDTO head_lowered;

            public BothHandsLeavingWheelDTO getBoth_hands_leaving_wheel() {
                return both_hands_leaving_wheel;
            }

            public void setBoth_hands_leaving_wheel(BothHandsLeavingWheelDTO both_hands_leaving_wheel) {
                this.both_hands_leaving_wheel = both_hands_leaving_wheel;
            }

            public EyesClosedDTO getEyes_closed() {
                return eyes_closed;
            }

            public void setEyes_closed(EyesClosedDTO eyes_closed) {
                this.eyes_closed = eyes_closed;
            }

            public NoFaceMaskDTO getNo_face_mask() {
                return no_face_mask;
            }

            public void setNo_face_mask(NoFaceMaskDTO no_face_mask) {
                this.no_face_mask = no_face_mask;
            }

            public NotBucklingUpDTO getNot_buckling_up() {
                return not_buckling_up;
            }

            public void setNot_buckling_up(NotBucklingUpDTO not_buckling_up) {
                this.not_buckling_up = not_buckling_up;
            }

            public SmokeDTO getSmoke() {
                return smoke;
            }

            public void setSmoke(SmokeDTO smoke) {
                this.smoke = smoke;
            }

            public NotFacingFrontDTO getNot_facing_front() {
                return not_facing_front;
            }

            public void setNot_facing_front(NotFacingFrontDTO not_facing_front) {
                this.not_facing_front = not_facing_front;
            }

            public CellphoneDTO getCellphone() {
                return cellphone;
            }

            public void setCellphone(CellphoneDTO cellphone) {
                this.cellphone = cellphone;
            }

            public YawningDTO getYawning() {
                return yawning;
            }

            public void setYawning(YawningDTO yawning) {
                this.yawning = yawning;
            }

            public HeadLoweredDTO getHead_lowered() {
                return head_lowered;
            }

            public void setHead_lowered(HeadLoweredDTO head_lowered) {
                this.head_lowered = head_lowered;
            }

            public static class BothHandsLeavingWheelDTO {
                private double score;
                private double threshold;

                public double getScore() {
                    return score;
                }

                public void setScore(double score) {
                    this.score = score;
                }

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }
            }

            public static class EyesClosedDTO {
                private double score;
                private double threshold;

                public double getScore() {
                    return score;
                }

                public void setScore(double score) {
                    this.score = score;
                }

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }
            }

            public static class NoFaceMaskDTO {
                private double score;
                private double threshold;

                public double getScore() {
                    return score;
                }

                public void setScore(double score) {
                    this.score = score;
                }

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }
            }

            public static class NotBucklingUpDTO {
                private double score;
                private double threshold;

                public double getScore() {
                    return score;
                }

                public void setScore(double score) {
                    this.score = score;
                }

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }
            }

            public static class SmokeDTO {
                private double score;
                private double threshold;

                public double getScore() {
                    return score;
                }

                public void setScore(double score) {
                    this.score = score;
                }

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }
            }

            public static class NotFacingFrontDTO {
                private double score;
                private double threshold;

                public double getScore() {
                    return score;
                }

                public void setScore(double score) {
                    this.score = score;
                }

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }
            }

            public static class CellphoneDTO {
                private double score;
                private double threshold;

                public double getScore() {
                    return score;
                }

                public void setScore(double score) {
                    this.score = score;
                }

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }
            }

            public static class YawningDTO {
                private double score;
                private double threshold;

                public double getScore() {
                    return score;
                }

                public void setScore(double score) {
                    this.score = score;
                }

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }
            }

            public static class HeadLoweredDTO {
                private double score;
                private double threshold;

                public double getScore() {
                    return score;
                }

                public void setScore(double score) {
                    this.score = score;
                }

                public double getThreshold() {
                    return threshold;
                }

                public void setThreshold(double threshold) {
                    this.threshold = threshold;
                }
            }
        }

        public static class LocationDTO {
            private double score;
            private int top;
            private int left;
            private int width;
            private int height;

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}
