package vn.com.telsoft.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HienTN
 */
public class Department implements Serializable {

    private long deptId;
    private Long parentId;
    private String name;
    private String deptCode;
    private String deptType;
    private String phone;
    private String fax;
    private String contactWho;
    private String address;
    private Long cityId;
    private Long districtId;
    private Long wardId;
    private String workAddress;
    private String decisionNum;
    private Date decisionDate;
    private String decisionSigner;
    private String email;
    private String description;
    private int level;
    private Date lastUpdateTime;
    private String userName;
    private Long status;

    public Department() {
    }

    public Department(Department object) {
        this.deptId = object.deptId;
        this.parentId = object.parentId;
        this.name = object.name;
        this.deptCode = object.deptCode;
        this.deptType = object.deptType;
        this.phone = object.phone;
        this.fax = object.fax;
        this.contactWho = object.contactWho;
        this.address = object.address;
        this.cityId = object.cityId;
        this.districtId = object.districtId;
        this.wardId = object.wardId;
        this.workAddress = object.workAddress;
        this.decisionNum = object.decisionNum;
        this.decisionDate = object.decisionDate;
        this.decisionSigner = object.decisionSigner;
        this.email = object.email;
        this.description = object.description;
        this.level = object.level;
        this.lastUpdateTime = object.lastUpdateTime;
        this.userName = object.userName;
        this.status = object.status;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getContactWho() {
        return contactWho;
    }

    public void setContactWho(String contactWho) {
        this.contactWho = contactWho;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getWardId() {
        return wardId;
    }

    public void setWardId(Long wardId) {
        this.wardId = wardId;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getDecisionNum() {
        return decisionNum;
    }

    public void setDecisionNum(String decisionNum) {
        this.decisionNum = decisionNum;
    }

    public Date getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(Date decisionDate) {
        this.decisionDate = decisionDate;
    }

    public String getDecisionSigner() {
        return decisionSigner;
    }

    public void setDecisionSigner(String decisionSigner) {
        this.decisionSigner = decisionSigner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

}
