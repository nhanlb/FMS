/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.telsoft.controller;

import com.faplib.lib.ClientMessage;
import com.faplib.lib.SystemLogger;
import com.faplib.lib.TSFuncTemplate;
import com.faplib.lib.TelsoftException;
import com.faplib.lib.admin.security.Authorizator;
import com.faplib.lib.util.DataUtil;
import com.faplib.lib.util.ResourceBundleUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import vn.com.telsoft.entity.ApParam;
import vn.com.telsoft.model.ApParamModel;
import vn.com.telsoft.util.ConfigEx;

/**
 *
 * @author HaiDT
 */
@ManagedBean(name = "apParamBean")
@ViewScoped
public class ApParamController extends TSFuncTemplate implements Serializable {

    private List<ApParam> listApparam = null;
    private List<ApParam> listApparamOld = null;
    private ApParam[] arrayApparamSelect;
    private ApParam objEttApparam;
    private String strParType = "";
    private String strParName = "";
    private String strParValue = "";
    private String strRowId = "";
    private String strDescription;
    private String strInputFocus = "par_type";
    private boolean blRequiredInputType = true;
    private boolean blRequiredInputName = true;
    private boolean blRequiredInputValue = true;
    private boolean blDisableButtonOther;
    private boolean blDisableInput = true;
    private boolean blFocus = true;
    private boolean blBlockUI = false;
    private boolean blRequireInputLabel = true;
    private boolean blFunctionAdd = false;
    private boolean blEditAction = false;
    private boolean blValueChange = false;
    private boolean blActionSearch = false;
    private boolean blFunctionCopy = false;
    private boolean blDisableParName = true;
    public int iValueFunction;
    public int iValueIndexSelect;
    public ApParamController() {
        try {
            listApparam = DataUtil.getData(ApParamModel.class, "getListApparamAll");
            setControlButton();
            if (listApparam.size() > 0) {
                objEttApparam = new ApParam();
                arrayApparamSelect = new ApParam[1];
                arrayApparamSelect[0] = listApparam.get(0);
                objEttApparam = listApparam.get(0);
//                fillValue(objEttApparam);
                fillValue(arrayApparamSelect[0]);
            }
        } catch (TelsoftException ex) {
            Logger.getLogger(ApParamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isBlDisableParName() {
        return blDisableParName;
    }

    public void setBlDisableParName(boolean blDisableParName) {
        this.blDisableParName = blDisableParName;
    }

    public List<ApParam> getListApparamOld() {
        return listApparamOld;
    }

    public void setListApparamOld(List<ApParam> listApparamOld) {
        this.listApparamOld = listApparamOld;
    }

    public boolean isBlActionSearch() {
        return blActionSearch;
    }

    public boolean isBlFunctionCopy() {
        return blFunctionCopy;
    }

    public void setBlFunctionCopy(boolean blfunctionCopy) {
        this.blFunctionCopy = blfunctionCopy;
    }

    public void setBlActionSearch(boolean blActionSearch) {
        this.blActionSearch = blActionSearch;
    }

    public boolean isBlValueChange() {
        return blValueChange;
    }

    public void setBlValueChange(boolean blValueChange) {
        this.blValueChange = blValueChange;
    }

    public boolean isBlEditAction() {
        return blEditAction;
    }

    public void setBlEditAction(boolean blEditAction) {
        this.blEditAction = blEditAction;
    }

    public boolean isBlFunctionAdd() {
        return blFunctionAdd;
    }

    public void setBlFunctionAdd(boolean blFunctionAdd) {
        this.blFunctionAdd = blFunctionAdd;
    }

    public List<ApParam> getListApparam() {
        return listApparam;
    }

    public void setListApparam(List<ApParam> listApparam) {
        this.listApparam = listApparam;
    }

    public ApParam[] getArrayApparamSelect() {
        return arrayApparamSelect;
    }

    public void setArrayApparamSelect(ApParam[] arrayApparamSelect) {
        this.arrayApparamSelect = arrayApparamSelect;
    }

    public ApParam getObjEttApparam() {
        return objEttApparam;
    }

    public void setObjEttApparam(ApParam objEttApparam) {
        this.objEttApparam = objEttApparam;
    }

    public String getStrParType() {
        return strParType;
    }

    public void setStrParType(String strParType) {
        this.strParType = strParType;
    }

    public String getStrRowId() {
        return strRowId;
    }

    public void setStrRowId(String strRowId) {
        this.strRowId = strRowId;
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

    public boolean isBlDisableButtonOther() {
        return blDisableButtonOther;
    }

    public void setBlDisableButtonOther(boolean blDisableButtonOther) {
        this.blDisableButtonOther = blDisableButtonOther;
    }

    public boolean isBlDisableInput() {
        return blDisableInput;
    }

    public void setBlDisableInput(boolean blDisableInput) {
        this.blDisableInput = blDisableInput;
    }

    public String getStrInputFocus() {
        return strInputFocus;
    }

    public void setStrInputFocus(String strInputFocus) {
        this.strInputFocus = strInputFocus;
    }

    public boolean isBlFocus() {
        return blFocus;
    }

    public void setBlFocus(boolean blFocus) {
        this.blFocus = blFocus;
    }

    public boolean isBlRequiredInputType() {
        return blRequiredInputType;
    }

    public void setBlRequiredInputType(boolean blRequiredInputType) {
        this.blRequiredInputType = blRequiredInputType;
    }

    public boolean isBlRequiredInputName() {
        return blRequiredInputName;
    }

    public void setBlRequiredInputName(boolean blRequiredInputName) {
        this.blRequiredInputName = blRequiredInputName;
    }

    public boolean isBlRequiredInputValue() {
        return blRequiredInputValue;
    }

    public void setBlRequiredInputValue(boolean blRequiredInputValue) {
        this.blRequiredInputValue = blRequiredInputValue;
    }

    public boolean isBlBlockUI() {
        return blBlockUI;
    }

    public void setBlBlockUI(boolean blBlockUI) {
        this.blBlockUI = blBlockUI;
    }

    public boolean isBlRequireInputLabel() {
        return blRequireInputLabel;
    }

    public void setBlRequireInputLabel(boolean blRequireInputLabel) {
        this.blRequireInputLabel = blRequireInputLabel;
    }

    public void fillValue(ApParam objApParam) {
        if (objApParam == null) {
            objApParam = listApparam.get(0);
        }
        strParType = objApParam.getStrParType();
        strParName = objApParam.getStrParName();
        strParValue = objApParam.getStrParValue();
        strDescription = objApParam.getStrDescription();
    }

    public void cleadValue() {
        strParType = "";
        strParName = "";
        strParValue = "";
        strDescription = "";
        blRequiredInputValue = true;
        blRequiredInputType = true;
        blRequiredInputName = true;
    }

    public void onRowSelect(SelectEvent event) {
        objEttApparam = (ApParam) event.getObject();
        iValueIndexSelect = listApparam.indexOf(objEttApparam);
        fillValue(objEttApparam);
    }

    public void setControlButton() {
        int iSizeDataTable = listApparam.size();
        if (iSizeDataTable > 0) {
            blDisableButtonOther = false;
        } else {
            blDisableButtonOther = true;
        }
    }

    public boolean checkValuesInput() {
        boolean blReturn;
        if (nvl(strParType).equals("") || nvl(strParName).equals("") || nvl(strParValue).equals("")) {
            blReturn = false;
        } else {
            blReturn = true;
            blRequiredInputValue = true;
            blRequiredInputType = true;
            blRequiredInputName = true;
            strInputFocus = "par_type";
        }
        return blReturn;
    }

    public void checkValueInput() {
        if (nvl(strParType).equals("")) {
            blRequiredInputType = false;
            strInputFocus = "par_type";
            ClientMessage.logPErr("PP_MNGAPPARAM", "errortype");
        } else {
            blRequiredInputType = true;
        }
        if (nvl(strParName).equals("")) {
            blRequiredInputName = false;
            if (!nvl(strParType).equals("")) {
                strInputFocus = "par_name";
                ClientMessage.logPErr("PP_MNGAPPARAM", "errorname");
            }
        } else {
            blRequiredInputName = true;
        }
        if (nvl(strParValue).equals("")) {
            blRequiredInputValue = false;
            if (!nvl(strParName).equals("") && !nvl(strParType).equals("")) {
                strInputFocus = "par_value";
                ClientMessage.logPErr("PP_MNGAPPARAM", "errorvalue");
            }
        } else {
            blRequiredInputValue = true;
        }
        blEditAction = false;
        blBlockUI = true;
    }

    @Override
    public void changeStateAdd() {
        super.changeStateAdd();
        blDisableInput = false;
        blDisableParName = false;
        cleadValue();
        blBlockUI = true;
        blFunctionAdd = true;
    }

    @Override
    public void changeStateCopy() {
        super.changeStateCopy();
        blDisableInput = false;
        blDisableParName = false;
        blBlockUI = true;
        blFunctionAdd = true;
        blFunctionCopy = true;
    }

    @Override
    public void changeStateEdit() {
        super.changeStateEdit();
        blDisableInput = false;
        blDisableParName = true;
        strInputFocus = "par_name";
        blBlockUI = true;
        blFunctionAdd = false;
        isEDIT = true;
        isADD = false;
    }

    @Override
    public void changeStateDel() {
        super.changeStateAdd();
    }

    public void changeStateSearch() {
        super.changeStateAdd();
        blDisableInput = false;
        blDisableParName = false;
        cleadValue();
        strInputFocus = "par_type";
        blRequireInputLabel = false;
        blBlockUI = true;
        blActionSearch = true;
    }

    @Override
    public void handCancel() {
        //checkKeyupChangeValue();
        if (!blValueChange) {
            super.handCancel();
            strInputFocus = "par_type";
            cleadValue();
            blDisableInput = true;
            blDisableParName = true;
            if (blRequireInputLabel) {
                if (listApparam.size() > 0) {
                    arrayApparamSelect = new ApParam[1];
                    arrayApparamSelect[0] = objEttApparam;
                    fillValue(arrayApparamSelect[0]);
                } else {
                    objEttApparam = null;
                }
            } else {
                if (listApparam.size() < 1) {
                    try {
                        listApparam = DataUtil.getData(ApParamModel.class, "getListApparamAll");
                        arrayApparamSelect = new ApParam[1];
                        arrayApparamSelect[0] = objEttApparam;
                        fillValue(arrayApparamSelect[0]);
                    } catch (TelsoftException ex) {
                        Logger.getLogger(ApParamController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    arrayApparamSelect = new ApParam[1];
                    arrayApparamSelect[0] = objEttApparam;
                    fillValue(arrayApparamSelect[0]);
                }
            }
            setControlButton();
            blBlockUI = false;
            blRequireInputLabel = true;
            blEditAction = false;
            blFunctionCopy = false;
        }
        arrayApparamSelect = new ApParam[1];
        arrayApparamSelect[0] = objEttApparam;
        fillValue(arrayApparamSelect[0]);
        isADD = false;
        isEDIT = false;
        isCOPY = false;
    }

    @Override
    public void handOK() {
        boolean blCheckError = false;
        arrayApparamSelect = new ApParam[1];
        ApParam ettApparam = new ApParam();
        ettApparam.setStrParType(nvl(strParType).trim());
        ettApparam.setStrParName(nvl(strParName).trim());
        ettApparam.setStrParValue(nvl(strParValue).trim());
        ettApparam.setStrDescription(nvl(strDescription).trim());
        if (blRequireInputLabel) {
            if (checkValuesInput()) {
                if (isADD || isCOPY) {
                    if (!blCheckError) {
                        try {
                            DataUtil.performAction(ApParamModel.class, "insertData", ettApparam);
                            listApparam = DataUtil.getData(ApParamModel.class, "getListApparamAll");
                        } catch (Exception e) {
                            //      blCheck = false;
                            arrayApparamSelect[0] = objEttApparam;
                            if (e.toString().equals("DupKey")) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundleUtil.getAMObjectAsString("PP_MNGAPPARAM", "insertErrorInsert"), ""));
                            } else if (e.toString().equals("PAR_TYPE")) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundleUtil.getAMObjectAsString("PP_MNGAPPARAM", "max_type"), ""));
                                strInputFocus = "par_type";
                            } else if (e.toString().equals("PAR_NAME")) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundleUtil.getAMObjectAsString("PP_MNGAPPARAM", "max_name"), ""));
                                strInputFocus = "par_name";
                            } else if (e.toString().equals("PAR_VALUE")) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundleUtil.getAMObjectAsString("PP_MNGAPPARAM", "max_value"), ""));
                                strInputFocus = "par_value";
                            } else if (e.toString().equals("DESCRIPTION")) {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundleUtil.getAMObjectAsString("PP_MNGAPPARAM", "max_description"), ""));
                                strInputFocus = "description";
                            } else {
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundleUtil.getAMObjectAsString("PP_MNGAPPARAM", "err_add"), ""));
                            }
                            SystemLogger.getLogger().error(e);
//                            ClientMessage.logErr(ClientMessage.MESSAGE_TYPE.ADD);
                            return;
                        }
                        //  if (blCheck) {
//                        ettApparam.setStrRowId(strRowId);
                        objEttApparam = ettApparam;
                        arrayApparamSelect[0] = ettApparam;
                        if (blActionSearch) {
                            blActionSearch = false;
                            functionBack();
                        } else {
//                            listApparam.add(objEttApparam);
                            blBlockUI = true;
                            blFunctionAdd = true;
                            blEditAction = false;
                            fillValue(arrayApparamSelect[0]);
                            if (!blFunctionCopy) {
                                cleadValue();
                            }
                        }
                        ClientMessage.logAdd();
                        //   }
                    } else {
                        arrayApparamSelect[0] = objEttApparam;
                    }
                    arrayApparamSelect = new ApParam[1];
                    arrayApparamSelect[0] = new ApParam(ettApparam);
                } else if (isEDIT) {
//                    iValueIndexSelect = listApparam.indexOf(objEttApparam);
                    int i = 0;
                    for (ApParam eTT_Apparam : listApparam) {
                        if (eTT_Apparam.getStrParType().equals(strParType) && eTT_Apparam.getStrParValue().equals(strParValue)) {
                            break;
                        }
                        i++;
                    }
                    if (iValueIndexSelect < 0) {
                        iValueIndexSelect = 0;
                    }
                    try {
                        DataUtil.performAction(ApParamModel.class, "updateData", ettApparam);
                        arrayApparamSelect = new ApParam[1];
                        arrayApparamSelect[0] = new ApParam(ettApparam);
                        listApparam.set(i, ettApparam);
                        functionBack();
//                        if (blActionSearch) {
//                            listApparam = DataUtil.getData(ApParamModel.class, "getListApparamAll");
//                        }
                    } catch (Exception ex) {
                        SystemLogger.getLogger().error(ex);
                        ClientMessage.logErr(ClientMessage.MESSAGE_TYPE.UPDATE);
                        return;
                    }
//                    if (blCheck) {
//                        objEttApparam = ettApparam;
//                        arrayApparamSelect[0] = ettApparam;
//                        if (!blActionSearch) {
//                            listApparam.set(iValueIndexSelect, arrayApparamSelect[0]);
//                        } else {
//                            blActionSearch = false;
//                        }
//                        functionBack();
//                        ClientMessage.logUpdate();
//                    }
                    isEDIT = false;
                }

            } else {
                checkValueInput();
            }
        } else {
            try {
                ApParamModel dataapparam = new ApParamModel();
                arrayApparamSelect[0] = ettApparam;
                List<ApParam> listEttSearch = new ArrayList<ApParam>();
                listEttSearch.add(ettApparam);
                listApparamOld = DataUtil.getData(ApParamModel.class, "getListApparamAll");
                listApparam = DataUtil.getData(ApParamModel.class, "getListApparamSearch", listEttSearch);
                if (listApparam.size() < 1) {
                    ClientMessage.logPErr("PP_MNGAPPARAM", "searchError");
                    blRequireInputLabel = false;
                    cleadValue();
                } else {
                    handCancel();
                    arrayApparamSelect[0] = listApparam.get(0);
                    objEttApparam = arrayApparamSelect[0];
                    fillValue(arrayApparamSelect[0]);
                    blRequireInputLabel = true;
                }
            } catch (TelsoftException ex) {
                Logger.getLogger(ApParamController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void handDelete() {
        boolean blCheck;
        ApParam ettApparam1 = new ApParam();
        iValueIndexSelect = listApparam.indexOf(objEttApparam);
        try {
            for (ApParam obj : arrayApparamSelect) {
                DataUtil.performAction(ApParamModel.class, "deleteData", obj);
            }

            listApparam = DataUtil.getData(ApParamModel.class, "getListApparamAll");
            blCheck = true;
        } catch (Exception ex) {
            blCheck = false;
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ClientMessage.MESSAGE_TYPE.UPDATE);
            return;
        }
        if (blCheck) {
            if (listApparam.size() > 0) {
                int iCheckRowSelect = iValueIndexSelect - arrayApparamSelect.length;
                if (iCheckRowSelect < 0) {
                    //if (iValueIndexSelect < 0) {
                    objEttApparam = listApparam.get(0);
                    iValueIndexSelect = 0;
//                    } else {
//                        objEttApparam = listApparam.get(iValueIndexSelect);
//                    }
                } else {
                    objEttApparam = listApparam.get(iCheckRowSelect);
                    iValueIndexSelect = iCheckRowSelect;
                }
                arrayApparamSelect = new ApParam[1];
                arrayApparamSelect[0] = objEttApparam;
                fillValue(arrayApparamSelect[0]);
            } else {
                cleadValue();
            }
            ClientMessage.logDelete();
        }
        blBlockUI = false;
        setControlButton();
    }

    public boolean getPermission(String strRight) {
        boolean bReturnValue = false;
        try {
            bReturnValue = Authorizator.checkAuthorizator(ConfigEx.AP_PARAM).contains(strRight) ? true : false;
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
        }
        return bReturnValue;
    }

    @SuppressWarnings("CallToThreadDumpStack")
    public void checkKeyupChangeValue() {
        if (blRequireInputLabel) {
            if (blFunctionAdd && !blFunctionCopy) {
                if (!nvl(strParType).trim().equals("") || !nvl(strParName).trim().equals("") || !nvl(strParValue).trim().equals("")) {
                    blValueChange = true;
                    return;
                }
                blValueChange = false;
            } else {
                if (!nvl(objEttApparam.getStrParType()).equals(nvl(strParType).trim())) {
                    blValueChange = true;
                    return;
                }
                if (!nvl(objEttApparam.getStrParName()).equals(nvl(strParName).trim())) {
                    blValueChange = true;
                    return;
                }
                if (!nvl(objEttApparam.getStrParValue()).equals(nvl(strParValue).trim())) {
                    blValueChange = true;
                    return;
                }
                if (!nvl(objEttApparam.getStrDescription()).equals(nvl(strDescription).trim())) {
                    blValueChange = true;
                    return;
                }
                blValueChange = false;
            }
        }
    }

    public void functionBack() {
        strInputFocus = "par_type";
        cleadValue();
        blFunctionAdd = false;
        blDisableInput = true;
        blDisableParName = true;
        arrayApparamSelect = new ApParam[1];
        arrayApparamSelect[0] = objEttApparam;
        fillValue(arrayApparamSelect[0]);
        setControlButton();
        blBlockUI = false;
        blRequireInputLabel = true;
        blEditAction = false;
        super.handCancel();
        blValueChange = false;
    }

    public String nvl(String strInput) {
        if (strInput == null) {
            return "";
        } else {
            return strInput;
        }
    }

}
