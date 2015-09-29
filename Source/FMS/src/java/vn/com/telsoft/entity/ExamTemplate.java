package vn.com.telsoft.entity;

import java.io.Serializable;

/**
 *
 * @author AnhTD
 */
public class ExamTemplate implements Serializable {

    private long examId;
    private String examCode;
    private String examName;
    private String examAddress;
    private int duration;
    private int maxPoint;
    private double examPassTerm;
    private double examRate;
    private String isDefault;
    private String orderType;
    private int priority;
    private int ord;
    private String status;

    public ExamTemplate() {
    }

    public ExamTemplate(ExamTemplate obj) {
        this.examId = obj.examId;
        this.examCode = obj.examCode;
        this.examName = obj.examName;
        this.examAddress = obj.examAddress;
        this.duration = obj.duration;
        this.maxPoint = obj.maxPoint;
        this.examPassTerm = obj.examPassTerm;
        this.examRate = obj.examRate;
        this.isDefault = obj.isDefault;
        this.orderType = obj.orderType;
        this.priority = obj.priority;
        this.ord = obj.ord;
        this.status = obj.status;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamAddress() {
        return examAddress;
    }

    public void setExamAddress(String examAddress) {
        this.examAddress = examAddress;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(int maxPoint) {
        this.maxPoint = maxPoint;
    }

    public double getExamPassTerm() {
        return examPassTerm;
    }

    public void setExamPassTerm(double examPassTerm) {
        this.examPassTerm = examPassTerm;
    }

    public double getExamRate() {
        return examRate;
    }

    public void setExamRate(double examRate) {
        this.examRate = examRate;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
