package vn.com.telsoft.entity;

import java.io.Serializable;

/**
 *
 * @author HienTN
 */
public class Area implements Serializable {

    private long areaId;
    private Long parentAreaId;
    private String areaCode;
    private Long areaType;
    private String isInternal;
    private String shortDisplay;
    private String displayValue;
    private String description;
    private String status;

    public Area() {
    }

    public Area(Area object) {
        this.areaId = object.areaId;
        this.parentAreaId = object.parentAreaId;
        this.areaCode = object.areaCode;
        this.areaType = object.areaType;
        this.isInternal = object.isInternal;
        this.shortDisplay = object.shortDisplay;
        this.displayValue = object.displayValue;
        this.description = object.description;
        this.status = object.status;
    }

    public long getAreaId() {
        return areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }

    public Long getParentAreaId() {
        return parentAreaId;
    }

    public void setParentAreaId(Long parentAreaId) {
        this.parentAreaId = parentAreaId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Long getAreaType() {
        return areaType;
    }

    public void setAreaType(Long areaType) {
        this.areaType = areaType;
    }

    public String getIsInternal() {
        return isInternal;
    }

    public void setIsInternal(String isInternal) {
        this.isInternal = isInternal;
    }

    public String getShortDisplay() {
        return shortDisplay;
    }

    public void setShortDisplay(String shortDisplay) {
        this.shortDisplay = shortDisplay;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
