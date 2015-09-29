package vn.com.telsoft.entity;

import java.io.Serializable;

/**
 *
 * @author AnhTD
 */
public class PolicyObject implements Serializable {

    private long policyObjectID;
    private String policyObjectName;
    private String policyObjectGroup;
    private String description;
    private String status;

    public PolicyObject() {
    }

    public PolicyObject(PolicyObject obj) {
        this.policyObjectID = obj.policyObjectID;
        this.policyObjectName = obj.policyObjectName;
        this.policyObjectGroup = obj.policyObjectGroup;
        this.description = obj.description;
        this.status = obj.status;
    }

    public long getPolicyObjectID() {
        return policyObjectID;
    }

    public void setPolicyObjectID(long policyObjectID) {
        this.policyObjectID = policyObjectID;
    }

    public String getPolicyObjectName() {
        return policyObjectName;
    }

    public void setPolicyObjectName(String policyObjectName) {
        this.policyObjectName = policyObjectName;
    }

    public String getPolicyObjectGroup() {
        return policyObjectGroup;
    }

    public void setPolicyObjectGroup(String policyObjectGroup) {
        this.policyObjectGroup = policyObjectGroup;
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
