package vn.com.telsoft.controller;

import com.faplib.lib.ClientMessage;
import com.faplib.lib.SystemLogger;
import com.faplib.lib.TSFuncTemplate;
import com.faplib.lib.util.DataUtil;
import com.faplib.lib.util.ResourceBundleUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import vn.com.telsoft.entity.Scales;
import vn.com.telsoft.model.ScalesModel;
import vn.com.telsoft.util.Utility;

/**
 *
 * @author AnhTD
 */
@ManagedBean(name = "scalesController")
@ViewScoped
public class ScalesController extends TSFuncTemplate implements Serializable {

    private Scales scales, mselectedScales, scalesBackup;
    private List<Scales> lstScales, lstScalesFiltered;
    private List<SelectEvent> lstScalesCate, lstSNNB;
    private boolean isSelectedApp, isDisplayBtnConfirm;

    public ScalesController() {
        try {
            scales = new Scales();
            isDisplayBtnConfirm = false;
            lstScalesCate = DataUtil.getData(Utility.class, "getApDomain", "SALARY_CATE");
            lstSNNB = DataUtil.getData(Utility.class, "getApDomain", "SNNB");
            lstScales = DataUtil.getData(ScalesModel.class, "getAllScales");
            if (!lstScales.isEmpty()) {
                mselectedScales = new Scales(lstScales.get(0));
                scales = new Scales(lstScales.get(0));
                isSelectedApp = true;
            } else {
                isSelectedApp = false;
                mselectedScales = new Scales();
            }
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }
    }

    public void onRowSelect(SelectEvent event) {
        scales = new Scales(mselectedScales);
    }

    @Override
    public void changeStateAdd() {
        super.changeStateAdd();
        setIsDisplayBtnConfirm(true);
        scales = new Scales();
    }

    @Override
    public void changeStateCopy() {
        super.changeStateCopy();
        isCOPY = true;
        isDisplayBtnConfirm = true;
        scalesBackup = new Scales(scales);
    }

    @Override
    public void changeStateEdit() {
        super.changeStateEdit();
        setIsDisplayBtnConfirm(true);
        scalesBackup = new Scales(scales);
    }

    @Override
    public void changeStateDel() {
        super.changeStateDel();
    }

    @Override
    public void handOK() {
        try {
            ScalesModel model = new ScalesModel();
            if (isADD || isCOPY) {
                Scales itemReturn = new Scales(model.addScales(scales));
                lstScales.add(itemReturn);

                // Bo sung
                lstScalesFiltered.add(itemReturn);

                mselectedScales = new Scales(itemReturn);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "add_success"));
//                scales = new Scales();
            } else if (isEDIT) {
                model.updateScales(scales);
                int i = 0;
                for (Scales rs : lstScales) {
                    if (rs.getScalesId() == scales.getScalesId()) {
                        lstScales.set(i, scales);
                    }
                    i++;
                }

                // Bo sung filter
                if (lstScalesFiltered == null) {
                    lstScalesFiltered = new ArrayList<>(lstScales);
                }
                updateFilterValue(scales);

                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "update_success"));
                isDisplayBtnConfirm = false;
                isEDIT = false;
                isDISABLE = true;
            }

            // Bo sung doan nay
            if (isCOPY) {
                scalesBackup = new Scales(mselectedScales);
                isDisplayBtnConfirm = false;
                isCOPY = false;
                isDISABLE = true;
            } else if (isADD) {
                scales = new Scales();
                isSelectedApp = true;
            }

//            isDISABLE = true; // them vao day de DISABLE cac truong
        } catch (Exception ex) {
            ClientMessage.logErr(ex.getMessage());
        }
    }

    // Bo sung ham nay
    private void updateFilterValue(Scales item) {
        int i = 0;
        for (Scales pos : lstScalesFiltered) {
            if (item.getScalesId() == pos.getScalesId()) {
                break;
            }
            i++;
        }
        lstScalesFiltered.set(i, item);
    }

    @Override
    public void handDelete() {
        try {
            ScalesModel model = new ScalesModel();
            model.deleteScales(scales);
            int i = 0;
            for (Scales rs : lstScales) {
                if (rs.getScalesId() == scales.getScalesId()) {
                    break;
                }
                i++;
            }
            lstScales.remove(i);
            if (lstScales.isEmpty()) {
                scales = new Scales();
                mselectedScales = new Scales();
            } else {
                scales = new Scales(lstScales.get(0));
                mselectedScales = new Scales(lstScales.get(0));
            }
            ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "del_success"));
        } catch (Exception ex) {
            Logger.getLogger(ScalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handCancel() {
        if (isADD) {
            scales = new Scales(mselectedScales);
        } else if (isEDIT || isCOPY) {
            scales = new Scales(scalesBackup);
        }
        isADD = false;
        isEDIT = false;
        isCOPY = false;
        isDisplayBtnConfirm = false;
        isDISABLE = true; // them vao day de DISABLE cac truong
    }

    /**
     * @return the scales
     */
    public Scales getScales() {
        return scales;
    }

    /**
     * @param scales the scales to set
     */
    public void setScales(Scales scales) {
        this.scales = scales;
    }

    /**
     * @return the lstScales
     */
    public List<Scales> getLstScales() {
        return lstScales;
    }

    /**
     * @param lstScales the lstScales to set
     */
    public void setLstScales(List<Scales> lstScales) {
        this.lstScales = lstScales;
    }

    public List<SelectEvent> getLstScalesCate() {
        return lstScalesCate;
    }

    public void setLstScalesCate(List<SelectEvent> lstScalesCate) {
        this.lstScalesCate = lstScalesCate;
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
     * @return the mselectedScales
     */
    public Scales getMselectedScales() {
        return mselectedScales;
    }

    /**
     * @param mselectedScales the mselectedScales to set
     */
    public void setMselectedScales(Scales mselectedScales) {
        this.mselectedScales = mselectedScales;
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

    public List<SelectEvent> getLstSNNB() {
        return lstSNNB;
    }

    public void setLstSNNB(List<SelectEvent> lstSNNB) {
        this.lstSNNB = lstSNNB;
    }

    public List<Scales> getLstScalesFiltered() {
        return lstScalesFiltered;
    }

    public void setLstScalesFiltered(List<Scales> lstScalesFiltered) {
        this.lstScalesFiltered = lstScalesFiltered;
    }
}
