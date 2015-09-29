package vn.com.telsoft.controller;

import com.faplib.lib.ClientMessage;
import com.faplib.lib.SystemLogger;
import com.faplib.lib.TSFuncTemplate;
import com.faplib.lib.util.DataUtil;
import com.faplib.lib.util.ResourceBundleUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import vn.com.telsoft.entity.Reason;
import vn.com.telsoft.model.ReasonModel;
import vn.com.telsoft.util.Utility;

/**
 *
 * @author HaiDT
 */
@ManagedBean(name = "reasonController")
@ViewScoped
public class ReasonController extends TSFuncTemplate implements Serializable {

    private Reason reason, mselectedReason, reasonBackup;
    private List<Reason> lstReason;
    private List<SelectEvent> lstReasonType;
    private boolean isSelectedApp, isDisplayBtnConfirm;
    private String strFocus;

    public ReasonController() {
        try {
            reason = new Reason();
            strFocus = "reasonType";
            isDisplayBtnConfirm = false;
            lstReasonType = DataUtil.getData(Utility.class, "getApDomain", "REASON_TYPE");
            lstReason = DataUtil.getData(ReasonModel.class, "getAllReason");
            if (!lstReason.isEmpty()) {
                mselectedReason = new Reason(lstReason.get(0));
                reason = new Reason(lstReason.get(0));
                isSelectedApp = true;
            } else {
                isSelectedApp = false;
                mselectedReason = new Reason();
            }
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }
    }

    public void onRowSelect(SelectEvent event) {
        reason = new Reason(mselectedReason);
    }

    @Override
    public void changeStateAdd() {
        super.changeStateAdd();
        setIsDisplayBtnConfirm(true);
        reason = new Reason();
        strFocus = "reasonType";
    }

    @Override
    public void changeStateCopy() {
        super.changeStateCopy();
        isCOPY = true;
        isDisplayBtnConfirm = true;
        reasonBackup = new Reason(reason);
        strFocus = "btn_ok";
    }

    @Override
    public void changeStateEdit() {
        super.changeStateEdit();
        setIsDisplayBtnConfirm(true);
        reasonBackup = new Reason(reason);
        strFocus = "btn_ok";
    }

    @Override
    public void changeStateDel() {
        super.changeStateDel();
    }

    @Override
    public void handOK() {
        try {
//            if (reason.getReasonType() == null || reason.getReasonType().equals("")) {
//                ClientMessage.logErr(ResourceBundleUtil.getAMObjectAsString("PP_REASONMNG", "msg_req_reasonType"));
//            strFocus="reasonType";
//                return;
//            }
//            if (reason.getShortDisplay() == null || reason.getShortDisplay().equals("")) {
//                ClientMessage.logErr(ResourceBundleUtil.getAMObjectAsString("PP_REASONMNG", "msg_req_shortDisplay"));
//                return;
//            }
//            if (reason.getDisplayValue() == null || reason.getDisplayValue().equals("")) {
//                ClientMessage.logErr(ResourceBundleUtil.getAMObjectAsString("PP_REASONMNG", "msg_req_displayValue"));
//                return;
//            }
            ReasonModel model = new ReasonModel();
            if (isADD || isCOPY) {
                Reason itemReturn = model.addReason(reason);
                lstReason.add(itemReturn);
                mselectedReason = new Reason(itemReturn);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "add_success"));
                reason = new Reason();
            } else if (isEDIT) {
                model.updateReason(reason);
                int i = 0;
                for (Reason rs : lstReason) {
                    if (rs.getReasonId() == reason.getReasonId()) {
                        lstReason.set(i, reason);
                    }
                    i++;
                }
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "update_success"));
                isDisplayBtnConfirm = false;
                isEDIT = false;
            }
            if (isCOPY) {
                reasonBackup = null;
                isDisplayBtnConfirm = false;
                isCOPY = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ClientMessage.logErr(ex.getMessage());
        }
    }

    @Override
    public void handDelete() {
        try {
            ReasonModel model = new ReasonModel();
            model.deleteReason(reason);
            int i = 0;
            for (Reason rs : lstReason) {
                if (rs.getReasonId() == reason.getReasonId()) {
                    break;
                }
                i++;
            }
            lstReason.remove(i);
            if (lstReason.isEmpty()) {
                reason = new Reason();
                mselectedReason = new Reason();
            } else {
                reason = new Reason(lstReason.get(0));
                mselectedReason = new Reason(lstReason.get(0));
            }
            ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "del_success"));
        } catch (Exception ex) {
            Logger.getLogger(ReasonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handCancel() {
        if (isADD) {
            reason = new Reason(mselectedReason);
        } else if (isEDIT || isCOPY) {
            reason = new Reason(reasonBackup);
        }
        isADD = false;
        isEDIT = false;
        isCOPY = false;
        isDISABLE = true;
        isDisplayBtnConfirm = false;
    }

    /**
     * @return the reason
     */
    public Reason getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(Reason reason) {
        this.reason = reason;
    }

    /**
     * @return the lstReason
     */
    public List<Reason> getLstReason() {
        return lstReason;
    }

    /**
     * @param lstReason the lstReason to set
     */
    public void setLstReason(List<Reason> lstReason) {
        this.lstReason = lstReason;
    }

    /**
     * @return the lstReasonType
     */
    public List<SelectEvent> getLstReasonType() {
        return lstReasonType;
    }

    /**
     * @param lstReasonType the lstReasonType to set
     */
    public void setLstReasonType(List<SelectEvent> lstReasonType) {
        this.lstReasonType = lstReasonType;
    }

    /**
     * @return the isSelectedApp
     */
    public boolean isIsSelectedApp() {
        return isSelectedApp;
    }

    /**
     * @param isSelectedApp the isSelectedApp to set
     */
    public void setIsSelectedApp(boolean isSelectedApp) {
        this.isSelectedApp = isSelectedApp;
    }

    /**
     * @return the mselectedReason
     */
    public Reason getMselectedReason() {
        return mselectedReason;
    }

    /**
     * @param mselectedReason the mselectedReason to set
     */
    public void setMselectedReason(Reason mselectedReason) {
        this.mselectedReason = mselectedReason;
    }

    /**
     * @return the isDisplayBtnConfirm
     */
    public boolean isIsDisplayBtnConfirm() {
        return isDisplayBtnConfirm;
    }

    /**
     * @param isDisplayBtnConfirm the isDisplayBtnConfirm to set
     */
    public void setIsDisplayBtnConfirm(boolean isDisplayBtnConfirm) {
        this.isDisplayBtnConfirm = isDisplayBtnConfirm;
    }

    /**
     * @return the strFocus
     */
    public String getStrFocus() {
        return strFocus;
    }

    /**
     * @param strFocus the strFocus to set
     */
    public void setStrFocus(String strFocus) {
        this.strFocus = strFocus;
    }
}
