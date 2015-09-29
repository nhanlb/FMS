package vn.com.telsoft.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HaiDT
 */
public class Staff implements Serializable {

    private static final long serialVersionUID = 12134123412341234L;
    private String staffId;
    private String staffCode;
    private String fullName;
    private String phone;
    private String address;
    private String cmnd;
    private String deptId;
    private String position;
    private String cityId;
    private String districtId;
    private String wardId;
    private Date birthDay;
    private String gender;
    private String taxNum;
    private String email;
    private String userName;
    private String status;

    public Staff() {
    }

    public Staff(String staffId) {
        this.staffId = staffId;
    }

    public Staff(Staff obj) {
        this.staffId = obj.staffId;
        this.staffCode = obj.staffCode;
        this.fullName = obj.fullName;
        this.phone = obj.phone;
        this.address = obj.address;
        this.cmnd = obj.cmnd;
        this.deptId = obj.deptId;
        this.position = obj.position;
        this.cityId = obj.cityId;
        this.districtId = obj.districtId;
        this.wardId = obj.wardId;
        this.birthDay = obj.birthDay;
        this.gender = obj.gender;
        this.email = obj.email;
        this.userName = obj.userName;
        this.status = obj.status;
    }

    public Staff(String staffId, String staffCode, String fullName, String phone, String address,
            String cmnd, String deptId, String position, String cityId, String districtId, String wardId, Date birthDay, String gender, String email, String userName, String status) {
        this.staffId = staffId;
        this.staffCode = staffCode;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.cmnd = cmnd;
        this.deptId = deptId;
        this.position = position;
        this.cityId = cityId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.birthDay = birthDay;
        this.gender = gender;
        this.email = email;
        this.userName = userName;
        this.status = status;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTaxNum() {
        return taxNum;
    }

    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "vn.com.telsoft.entity.Staff[ staffId=" + staffId + " ]";
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
