package vn.com.telsoft.entity;

import java.io.Serializable;

/**
 *
 * @author AnhTD
 */
public class Scales implements Serializable {

    private long scalesId;
    private String scalesCode;
    private String scalesName;
    private String scalesCate;
    private double snnb;
    private String status;

    public Scales() {
    }

    public Scales(Scales obj) {
        this.scalesId = obj.scalesId;
        this.scalesCode = obj.scalesCode;
        this.scalesName = obj.scalesName;
        this.scalesCate = obj.scalesCate;
        this.snnb = obj.snnb;
        this.status = obj.status;
    }

    public long getScalesId() {
        return scalesId;
    }

    public void setScalesId(long scalesId) {
        this.scalesId = scalesId;
    }

    public String getScalesCode() {
        return scalesCode;
    }

    public void setScalesCode(String scalesCode) {
        this.scalesCode = scalesCode;
    }

    public String getScalesName() {
        return scalesName;
    }

    public void setScalesName(String scalesName) {
        this.scalesName = scalesName;
    }

    public String getScalesCate() {
        return scalesCate;
    }

    public void setScalesCate(String scalesCate) {
        this.scalesCate = scalesCate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getSnnb() {
        return snnb;
    }

    public void setSnnb(double snnb) {
        this.snnb = snnb;
    }
}
