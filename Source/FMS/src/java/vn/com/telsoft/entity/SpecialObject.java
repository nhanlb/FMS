package vn.com.telsoft.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Administrator
 */


public class SpecialObject implements Serializable {
   
    private int specialObjectId;
    private String specialObjectName;
    private Date startDate;
    private Date endDate;
    private String decisionNum;
    private Date decisionDate;
    private int decisionDept;
    private String decisionSigner;
    private int decisionDocId;
    private String description;
    private int status;
    //
    private String startDateView;
    private String endDateView;
    private String decisionDateView;

    public SpecialObject() {
    }

    public SpecialObject(SpecialObject obj) {
        this.specialObjectId = obj.specialObjectId;
        this.specialObjectName = obj.specialObjectName;
        this.startDate = obj.startDate;
        this.endDate = obj.endDate;
        this.decisionNum = obj.decisionNum;
        this.decisionDate = obj.decisionDate;
        this.decisionDept = obj.decisionDept;
        this.decisionSigner = obj.decisionSigner;
        this.decisionDocId = obj.decisionDocId;
        this.description = obj.description;
        this.status = obj.status;
        //
        this.startDateView = obj.startDateView;
        this.endDateView = obj.endDateView;
        this.decisionDateView = obj.decisionDateView;
    }

    public int getSpecialObjectId() {
        return specialObjectId;
    }

    public String getSpecialObjectName() {
        return specialObjectName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getDecisionNum() {
        return decisionNum;
    }

    public Date getDecisionDate() {
        return decisionDate;
    }

    public int getDecisionDept() {
        return decisionDept;
    }

    public String getDecisionSigner() {
        return decisionSigner;
    }

    public int getDecisionDocId() {
        return decisionDocId;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public void setSpecialObjectId(int specialObjectId) {
        this.specialObjectId = specialObjectId;
    }

    public void setSpecialObjectName(String specialObjectName) {
        this.specialObjectName = specialObjectName;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDecisionNum(String decisionNum) {
        this.decisionNum = decisionNum;
    }

    public void setDecisionDate(Date decisionDate) {
        this.decisionDate = decisionDate;
    }

    public void setDecisionDept(int decisionDept) {
        this.decisionDept = decisionDept;
    }

    public void setDecisionSigner(String decisionSigner) {
        this.decisionSigner = decisionSigner;
    }

    public void setDecisionDocId(int decisionDocId) {
        this.decisionDocId = decisionDocId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStartDateView() {
        return startDateView;
    }

    public String getEndDateView() {
        return endDateView;
    }

    public void setStartDateView(String startDateView) {
        this.startDateView = startDateView;
    }

    public void setEndDateView(String endDateView) {
        this.endDateView = endDateView;
    }

    public String getDecisionDateView() {
        return decisionDateView;
    }

    public void setDecisionDateView(String decisionDateView) {
        this.decisionDateView = decisionDateView;
    }
}
