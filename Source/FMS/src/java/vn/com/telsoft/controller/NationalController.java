package vn.com.telsoft.controller;

import com.faplib.lib.ClientMessage;
import com.faplib.lib.SystemLogger;
import com.faplib.lib.TSFuncTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.faplib.lib.util.DataUtil;
import com.faplib.lib.util.ResourceBundleUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import vn.com.telsoft.entity.National;
import vn.com.telsoft.model.NationalModel;
import vn.com.telsoft.util.Utility;

/**
 *
 * @author HienTN
 */
@ManagedBean(name = "nationalController")
@ViewScoped
public class NationalController extends TSFuncTemplate implements Serializable {

    private National national, mselectedNational, nationalBackup;
    private List<National> lstNational, lstNationalFiltered;
    private boolean isSelectedApp, isDisplayBtnConfirm;

    /**
     * Creates a new instance of NationalController
     */
    public NationalController() {
        try {
            national = new National();
            isDisplayBtnConfirm = false;
            lstNational = DataUtil.getData(NationalModel.class, "getAllNational");
            if (!lstNational.isEmpty()) {
                mselectedNational = new National(lstNational.get(0));
                national = new National(lstNational.get(0));
                isSelectedApp = true;
            } else {
                isSelectedApp = false;
                mselectedNational = new National();
            }
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }
    }

    public void onRowSelect(SelectEvent event) {
        national = new National(mselectedNational);
    }

    @Override
    public void changeStateAdd() {
        super.changeStateAdd();
        setIsDisplayBtnConfirm(true);
        national = new National();
    }

    @Override
    public void changeStateCopy() {
        super.changeStateCopy();
        isCOPY = true;
        isDisplayBtnConfirm = true;
        nationalBackup = new National(national);
    }

    @Override
    public void changeStateEdit() {
        super.changeStateEdit();
        setIsDisplayBtnConfirm(true);
        nationalBackup = new National(national);
    }

    @Override
    public void changeStateDel() {
        super.changeStateDel();
    }

    public String integerToString(Integer par) {
        return String.valueOf(par);
    }

    @Override
    public void handOK() {
        try {
            NationalModel model = new NationalModel();
            if (isADD || isCOPY) {
                National itemReturn = new National(model.addNational(national));
                lstNational.add(itemReturn);
                lstNationalFiltered.add(itemReturn);
                mselectedNational = new National(itemReturn);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "add_success"));

            } else if (isEDIT) {
                model.updateNational(national);
                int i = 0;
                for (National rs : lstNational) {
                    if (rs.getNationalId() == national.getNationalId()) {
                        lstNational.set(i, national);
                    }
                    i++;
                }
                if (lstNationalFiltered == null) {
                    lstNationalFiltered = new ArrayList<>(lstNational);
                }
                updateFilterValue(national);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "update_success"));
                isDisplayBtnConfirm = false;
                isDISABLE = true;
                mselectedNational = new National(national);
                isEDIT = false;
            }
            if (isCOPY) {
                nationalBackup = new National(mselectedNational);
                isDisplayBtnConfirm = false;
                isCOPY = false;
                isDISABLE = true;
            } else if (isADD) {
                national = new National();
                isSelectedApp = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ClientMessage.logErr(ex.getMessage());
        }
    }

    private void updateFilterValue(National item) {
        int i = 0;
        for (National pos : lstNationalFiltered) {
            if (item.getNationalId() == pos.getNationalId()) {
                break;
            }
            i++;
        }
        lstNationalFiltered.set(i, item);
    }

    @Override
    public void handDelete() {
        try {
            NationalModel model = new NationalModel();
            model.deleteNational(national);
            int i = 0;
            for (National rs : lstNational) {
                if (rs.getNationalId() == national.getNationalId()) {
                    break;
                }
                i++;
            }
            lstNational.remove(i);
            if (lstNational.isEmpty()) {
                national = new National();
                mselectedNational = new National();
            } else {
                national = new National(lstNational.get(0));
                mselectedNational = new National(lstNational.get(0));
            }

            ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "del_success"));
        } catch (Exception ex) {
            Logger.getLogger(NationalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handCancel() {
        if (isADD) {
            national = new National(mselectedNational);
        } else if (isEDIT || isCOPY) {
            national = new National(nationalBackup);
        }
        isADD = false;
        isEDIT = false;
        isCOPY = false;
        isDISABLE = true;
        isDisplayBtnConfirm = false;
    }

    public National getNational() {
        return national;
    }

    public void setNational(National national) {
        this.national = national;
    }

    public National getMselectedNational() {
        return mselectedNational;
    }

    public void setMselectedNational(National mselectedNational) {
        this.mselectedNational = mselectedNational;
    }

    public National getNationalBackup() {
        return nationalBackup;
    }

    public void setNationalBackup(National nationalBackup) {
        this.nationalBackup = nationalBackup;
    }

    public List<National> getLstNational() {
        return lstNational;
    }

    public void setLstNational(List<National> lstNational) {
        this.lstNational = lstNational;
    }

    public List<National> getLstNationalFiltered() {
        return lstNationalFiltered;
    }

    public void setLstNationalFiltered(List<National> lstNationalFiltered) {
        this.lstNationalFiltered = lstNationalFiltered;
    }

    public boolean isIsSelectedApp() {
        return isSelectedApp;
    }

    public void setIsSelectedApp(boolean isSelectedApp) {
        this.isSelectedApp = isSelectedApp;
    }

    public boolean isIsDisplayBtnConfirm() {
        return isDisplayBtnConfirm;
    }

    public void setIsDisplayBtnConfirm(boolean isDisplayBtnConfirm) {
        this.isDisplayBtnConfirm = isDisplayBtnConfirm;
    }

}
