package vn.com.telsoft.entity;

import java.io.Serializable;

/**
 *
 * @author HienTN
 */
public class National implements Serializable {

    private int nationalId;
    private String nationalName;
    private String nationalCode;
    private Integer status;

    public National() {
    }

    public National(National object) {
        this.nationalId = object.nationalId;
        this.nationalName = object.nationalName;
        this.nationalCode = object.nationalCode;
        this.status = object.status;
    }

    public int getNationalId() {
        return nationalId;
    }

    public void setNationalId(int nationalId) {
        this.nationalId = nationalId;
    }
    

  

    public String getNationalName() {
        return nationalName;
    }

    public void setNationalName(String nationalName) {
        this.nationalName = nationalName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
