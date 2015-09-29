package vn.com.telsoft.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HienTN
 */
public class Position implements Serializable {

    private int positionsRefId;
    private int deptId;
    private String position;
    private Double coefficient;
    private Double allowance;
    private Date startDate;
    private Date endDate;
    private Integer status;

    private String startDateView;
    private String endDateView;

    public Position() {
    }

    public Position(Position object) {
        this.positionsRefId = object.positionsRefId;
        this.deptId = object.deptId;
        this.position = object.position;
        this.coefficient = object.coefficient;
        this.allowance = object.allowance;
        this.startDate = object.startDate;
        this.endDate = object.endDate;
        this.status = object.status;
        this.startDateView = object.startDateView;
        this.endDateView = object.endDateView;
    }

    public int getPositionsRefId() {
        return positionsRefId;
    }

    public void setPositionsRefId(int positionsRefId) {
        this.positionsRefId = positionsRefId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public Double getAllowance() {
        return allowance;
    }

    public void setAllowance(Double allowance) {
        this.allowance = allowance;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartDateView() {
        return startDateView;
    }

    public void setStartDateView(String startDateView) {
        this.startDateView = startDateView;
    }

    public String getEndDateView() {
        return endDateView;
    }

    public void setEndDateView(String endDateView) {
        this.endDateView = endDateView;
    }

}
