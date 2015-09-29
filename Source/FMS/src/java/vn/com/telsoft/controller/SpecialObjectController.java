package vn.com.telsoft.controller;

/**
 *
 * @author Administrator
 */
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
import vn.com.telsoft.entity.SpecialObject;
import vn.com.telsoft.model.SpecialObjectModel;

@ManagedBean(name = "specialObjectController")
@ViewScoped

public class SpecialObjectController extends TSFuncTemplate implements Serializable {

    private SpecialObject specialObject, mselectedSpecialObject, specialObjectBackup;
    private List<SpecialObject> lstSpecialObject;
    private List<SpecialObject> lstSpecialObjectFiltered;
    private boolean isSelectedApp, isDisplayBtnConfirm;


    public SpecialObjectController() {
        try {
            specialObject = new SpecialObject();
            isDisplayBtnConfirm = false;
            lstSpecialObject = DataUtil.getData(SpecialObjectModel.class, "getAllSpecialObject");
            lstSpecialObjectFiltered = lstSpecialObject;
            if (!lstSpecialObject.isEmpty()) {
                mselectedSpecialObject = new SpecialObject(lstSpecialObject.get(0));
                specialObject = new SpecialObject(lstSpecialObject.get(0));
                isSelectedApp = true;
            } else {
                isSelectedApp = false;
                mselectedSpecialObject = new SpecialObject();
            }
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }
    }

    public void onRowSelect(SelectEvent event) {
        specialObject = new SpecialObject(mselectedSpecialObject);
    }

    @Override
    public void changeStateAdd() {
        super.changeStateAdd();
        setIsDisplayBtnConfirm(true);
        specialObject = new SpecialObject();
    }

    @Override
    public void changeStateCopy() {
        super.changeStateCopy();
        isCOPY = true;
        isDisplayBtnConfirm = true;
        specialObjectBackup = new SpecialObject(specialObject);
    }

    @Override
    public void changeStateEdit() {
        super.changeStateEdit();
        setIsDisplayBtnConfirm(true);
        specialObjectBackup = new SpecialObject(specialObject);
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
            SpecialObjectModel model = new SpecialObjectModel();
            if (isADD || isCOPY) {
                SpecialObject itemReturn = new SpecialObject(model.addSpecialObject(specialObject));
                lstSpecialObject.add(itemReturn);
                lstSpecialObjectFiltered.add(itemReturn);
                mselectedSpecialObject = new SpecialObject(itemReturn);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "add_success"));

            } else if (isEDIT) {
                model.updateSpecialObject(specialObject);
                int i = 0;
                for (SpecialObject rs : lstSpecialObject) {
                    if (rs.getSpecialObjectId() == specialObject.getSpecialObjectId()) {
                        lstSpecialObject.set(i, specialObject);
                    }
                    i++;
                }
                if (lstSpecialObjectFiltered == null) {
                    lstSpecialObjectFiltered = new ArrayList<>(lstSpecialObject);
                }
                updateFilterValue(specialObject);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "update_success"));
                isDisplayBtnConfirm = false;
                isDISABLE = true;
                mselectedSpecialObject = new SpecialObject(specialObject);
                isEDIT = false;
            }
            if (isCOPY) {
                specialObjectBackup = new SpecialObject(mselectedSpecialObject);
                isDisplayBtnConfirm = false;
                isCOPY = false;
                isDISABLE = true;
            } else if (isADD) {
                specialObject = new SpecialObject();
                isSelectedApp = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ClientMessage.logErr(ex.getMessage());
        }
    }

    private void updateFilterValue(SpecialObject item) {
        int i = 0;
        for (SpecialObject pos : lstSpecialObjectFiltered) {
            if (item.getSpecialObjectId() == pos.getSpecialObjectId()) {
                break;
            }
            i++;
        }
        lstSpecialObjectFiltered.set(i, item);
    }

    @Override
    public void handDelete() {
        try {
            SpecialObjectModel model = new SpecialObjectModel();
            model.deleteSpecialObject(specialObject);
            int i = 0;
            for (SpecialObject rs : lstSpecialObject) {
                if (rs.getSpecialObjectId() == specialObject.getSpecialObjectId()) {
                    break;
                }
                i++;
            }
            lstSpecialObject.remove(i);
            if (lstSpecialObject.isEmpty()) {
                specialObject = new SpecialObject();
                mselectedSpecialObject = new SpecialObject();
            } else {
                specialObject = new SpecialObject(lstSpecialObject.get(0));
                mselectedSpecialObject = new SpecialObject(lstSpecialObject.get(0));
            }

            ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "del_success"));
        } catch (Exception ex) {
            Logger.getLogger(vn.com.telsoft.controller.SpecialObjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handCancel() {
        if (isADD) {
            specialObject = new SpecialObject(mselectedSpecialObject);
        } else if (isEDIT || isCOPY) {
            specialObject = new SpecialObject(specialObjectBackup);
        }
        isADD = false;
        isEDIT = false;
        isCOPY = false;
        isDISABLE = true;
        isDisplayBtnConfirm = false;
    }

    public SpecialObject getSpecialObject() {
        return specialObject;
    }

    public void setSpecialObject(SpecialObject specialObject) {
        this.specialObject = specialObject;
    }

    public SpecialObject getMselectedSpecialObject() {
        return mselectedSpecialObject;
    }

    public void setMselectedSpecialObject(SpecialObject mselectedSpecialObject) {
        this.mselectedSpecialObject = mselectedSpecialObject;
    }

    public SpecialObject getSpecialObjectBackup() {
        return specialObjectBackup;
    }

    public void setSpecialObjectBackup(SpecialObject specialObjectBackup) {
        this.specialObjectBackup = specialObjectBackup;
    }

    public List<SpecialObject> getLstSpecialObject() {
        return lstSpecialObject;
    }

    public void setLstSpecialObject(List<SpecialObject> lstSpecialObject) {
        this.lstSpecialObject = lstSpecialObject;
    }

    public List<SpecialObject> getLstSpecialObjectFiltered() {
        return lstSpecialObjectFiltered;
    }

    public void setLstSpecialObjectFiltered(List<SpecialObject> lstSpecialObjectFiltered) {
        this.lstSpecialObjectFiltered = lstSpecialObjectFiltered;
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
