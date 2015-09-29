package vn.com.telsoft.entity;

import java.io.Serializable;

/**
 *
 * @author HaiDT
 */
public class Reason implements Serializable {

    private long reasonId;
    private String reasonType;
    private String shortDisplay;
    private String displayValue;
    private String description;
    private int status;

    public Reason() {
    }

    public Reason(Reason obj) {
        this.reasonId = obj.reasonId;
        this.reasonType = obj.reasonType;
        this.shortDisplay = obj.shortDisplay;
        this.displayValue = obj.displayValue;
        this.description = obj.description;
        this.status = obj.status;
    }

    /**
     * @return the reasonId
     */
    public long getReasonId() {
        return reasonId;
    }

    /**
     * @param reasonId the reasonId to set
     */
    public void setReasonId(long reasonId) {
        this.reasonId = reasonId;
    }

    /**
     * @return the reasonType
     */
    public String getReasonType() {
        return reasonType;
    }

    /**
     * @param reasonType the reasonType to set
     */
    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    /**
     * @return the shortDisplay
     */
    public String getShortDisplay() {
        return shortDisplay;
    }

    /**
     * @param shortDisplay the shortDisplay to set
     */
    public void setShortDisplay(String shortDisplay) {
        this.shortDisplay = shortDisplay;
    }

    /**
     * @return the displayValue
     */
    public String getDisplayValue() {
        return displayValue;
    }

    /**
     * @param displayValue the displayValue to set
     */
    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
