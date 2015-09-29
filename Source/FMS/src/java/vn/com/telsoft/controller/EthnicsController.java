package vn.com.telsoft.controller;

import com.faplib.lib.ClientMessage;
import com.faplib.lib.SystemLogger;
import com.faplib.lib.TSFuncTemplate;
import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.DataUtil;
import com.faplib.lib.util.ResourceBundleUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import vn.com.telsoft.entity.Ethnics;
import vn.com.telsoft.model.EthnicsModel;

/**
 *
 * @author HienTN
 */
@ManagedBean(name = "ethnicsController")
@ViewScoped
public class EthnicsController extends TSFuncTemplate implements Serializable {

    private Ethnics ethnics, mselectedEthnics, ethnicsBackup;
    private List<Ethnics> lstEthnics, lstEthnicsFiltered;
    private boolean isSelectedApp, isDisplayBtnConfirm;

    /**
     * Creates a new instance of EthnicsController
     */

    public EthnicsController() {
        try {
            ethnics = new Ethnics();
            isDisplayBtnConfirm = false;
            lstEthnics = DataUtil.getData(EthnicsModel.class, "getAllEthnics");
            if (!lstEthnics.isEmpty()) {
                mselectedEthnics = new Ethnics(lstEthnics.get(0));
                ethnics = new Ethnics(lstEthnics.get(0));
                isSelectedApp = true;
            } else {
                isSelectedApp = false;
                mselectedEthnics = new Ethnics();
            }
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }
    }

    public void onRowSelect(SelectEvent event) {
        ethnics = new Ethnics(mselectedEthnics);
    }

    @Override
    public void changeStateAdd() {
        super.changeStateAdd();
        setIsDisplayBtnConfirm(true);
        ethnics = new Ethnics();
    }

    @Override
    public void changeStateCopy() {
        super.changeStateCopy();
        isCOPY = true;
        isDisplayBtnConfirm = true;
        ethnicsBackup = new Ethnics(ethnics);
    }

    @Override
    public void changeStateEdit() {
        super.changeStateEdit();
        setIsDisplayBtnConfirm(true);
        ethnicsBackup = new Ethnics(ethnics);
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
            EthnicsModel model = new EthnicsModel();
            if (isADD || isCOPY) {
                Ethnics itemReturn = new Ethnics(model.addEthnics(ethnics));
                lstEthnics.add(itemReturn);
                lstEthnicsFiltered.add(itemReturn);
                mselectedEthnics = new Ethnics(itemReturn);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "add_success"));

            } else if (isEDIT) {
                model.updateEthnics(ethnics);
                int i = 0;
                for (Ethnics rs : lstEthnics) {
                    if (rs.getEthnicId() == ethnics.getEthnicId()) {
                        lstEthnics.set(i, ethnics);
                    }
                    i++;
                }
                if (lstEthnicsFiltered == null) {
                    lstEthnicsFiltered = new ArrayList<>(lstEthnics);
                }
                updateFilterValue(ethnics);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "update_success"));
                isDisplayBtnConfirm = false;
                isDISABLE = true;
                mselectedEthnics = new Ethnics(ethnics);
                isEDIT = false;
            }
            if (isCOPY) {
                ethnicsBackup = new Ethnics(mselectedEthnics);
                isDisplayBtnConfirm = false;
                isCOPY = false;
                isDISABLE = true;
            } else if (isADD) {
                ethnics = new Ethnics();
                isSelectedApp = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ClientMessage.logErr(ex.getMessage());
        }
    }

    private void updateFilterValue(Ethnics item) {
        int i = 0;
        for (Ethnics pos : lstEthnicsFiltered) {
            if (item.getEthnicId() == pos.getEthnicId()) {
                break;
            }
            i++;
        }
        lstEthnicsFiltered.set(i, item);
    }

    @Override
    public void handDelete() {
        try {
            EthnicsModel model = new EthnicsModel();
            model.deleteEthnics(ethnics);
            int i = 0;
            for (Ethnics rs : lstEthnics) {
                if (rs.getEthnicId() == ethnics.getEthnicId()) {
                    break;
                }
                i++;
            }
            lstEthnics.remove(i);
            if (lstEthnics.isEmpty()) {
                ethnics = new Ethnics();
                mselectedEthnics = new Ethnics();
            } else {
                ethnics = new Ethnics(lstEthnics.get(0));
                mselectedEthnics = new Ethnics(lstEthnics.get(0));
            }

            ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "del_success"));
        } catch (Exception ex) {
            Logger.getLogger(EthnicsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handCancel() {
        if (isADD) {
            ethnics = new Ethnics(mselectedEthnics);
        } else if (isEDIT || isCOPY) {
            ethnics = new Ethnics(ethnicsBackup);
        }
        isADD = false;
        isEDIT = false;
        isCOPY = false;
        isDISABLE = true;
        isDisplayBtnConfirm = false;
    }

    public Ethnics getEthnics() {
        return ethnics;
    }

    public void setEthnics(Ethnics ethnics) {
        this.ethnics = ethnics;
    }

    public Ethnics getMselectedEthnics() {
        return mselectedEthnics;
    }

    public void setMselectedEthnics(Ethnics mselectedEthnics) {
        this.mselectedEthnics = mselectedEthnics;
    }

    public Ethnics getEthnicsBackup() {
        return ethnicsBackup;
    }

    public void setEthnicsBackup(Ethnics ethnicsBackup) {
        this.ethnicsBackup = ethnicsBackup;
    }

    public List<Ethnics> getLstEthnics() {
        return lstEthnics;
    }

    public void setLstEthnics(List<Ethnics> lstEthnics) {
        this.lstEthnics = lstEthnics;
    }

    public List<Ethnics> getLstEthnicsFiltered() {
        return lstEthnicsFiltered;
    }

    public void setLstEthnicsFiltered(List<Ethnics> lstEthnicsFiltered) {
        this.lstEthnicsFiltered = lstEthnicsFiltered;
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
