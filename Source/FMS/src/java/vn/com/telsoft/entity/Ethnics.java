package vn.com.telsoft.entity;

import java.io.Serializable;

/**
 *
 * @author HienTN
 */
public class Ethnics implements Serializable {

    private int ethnicId;
    private String ethnicName;
    private String description;
    private Integer status;

    public Ethnics() {
    }

    public Ethnics(Ethnics object) {
        this.ethnicId = object.ethnicId;
        this.ethnicName = object.ethnicName;
        this.description = object.description;
        this.status = object.status;
    }

    public int getEthnicId() {
        return ethnicId;
    }

    public void setEthnicId(int ethnicId) {
        this.ethnicId = ethnicId;
    }

    public String getEthnicName() {
        return ethnicName;
    }

    public void setEthnicName(String ethnicName) {
        this.ethnicName = ethnicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
