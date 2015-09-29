/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.telsoft.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HaiDT
 */
public class ApParam implements Serializable {

    private static final long serialVersionUID = 165435465465L;
    private String strParType = "";
    private String strParName = "";
    private String strParValue = "";
    private String strDescription = "";
    public ApParam() {
    }

    public ApParam(ApParam ett) {
        strParType = ett.getStrParType();
        strParName = ett.getStrParName();
        strDescription = ett.getStrDescription();
        strParValue = ett.getStrParValue();
    }

    public String getStrParType() {
        return strParType.toUpperCase();
    }

    public void setStrParType(String strParType) {
        this.strParType = strParType.toUpperCase();
    }

    public String getStrParName() {
        return strParName;
    }

    public void setStrParName(String strParName) {
        this.strParName = strParName;
    }

    public String getStrParValue() {
        return strParValue;
    }

    public void setStrParValue(String strParValue) {
        this.strParValue = strParValue;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

}
