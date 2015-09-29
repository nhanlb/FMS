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
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import vn.com.telsoft.entity.Position;
import vn.com.telsoft.model.PositionModel;
import vn.com.telsoft.util.Utility;

/**
 *
 * @author HienTN
 */
@ManagedBean(name = "positionController")
@ViewScoped
public class PositionController extends TSFuncTemplate implements Serializable {

    private Position position, mselectedPosition, positionBackup;
    private List<Position> lstPosition, lstPositionFiltered;
    private List<SelectEvent> lstPositionType;
    private List<SelectItem> cboDepartment = new ArrayList<SelectItem>();
    private boolean isSelectedApp, isDisplayBtnConfirm;

    /**
     * Creates a new instance of PositionController
     */
    public PositionController() {
        try {
            position = new Position();
            isDisplayBtnConfirm = false;
            lstPosition = DataUtil.getData(PositionModel.class, "getAllPosition");
            lstPositionType = DataUtil.getData(Utility.class, "getApDomain", "POSITION");
            cboDepartment = DataUtil.getData(PositionModel.class, "getDepartment");
            if (!lstPosition.isEmpty()) {
                mselectedPosition = new Position(lstPosition.get(0));
                position = new Position(lstPosition.get(0));
                isSelectedApp = true;
            } else {
                isSelectedApp = false;
                mselectedPosition = new Position();
            }

        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }
    }

    public void onRowSelect(SelectEvent event) {
        position = new Position(mselectedPosition);
    }

    @Override
    public void changeStateAdd() {
        super.changeStateAdd();
        setIsDisplayBtnConfirm(true);
        position = new Position();
    }

    @Override
    public void changeStateCopy() {
        super.changeStateCopy();
        isCOPY = true;
        isDisplayBtnConfirm = true;
        positionBackup = new Position(position);
    }

    @Override
    public void changeStateEdit() {
        super.changeStateEdit();
        setIsDisplayBtnConfirm(true);
        positionBackup = new Position(position);
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
            PositionModel model = new PositionModel();
            if (isADD || isCOPY) {
                Position itemReturn = new Position(model.addPosition(position));
                lstPosition.add(itemReturn);
                lstPositionFiltered.add(itemReturn);
                mselectedPosition = new Position(itemReturn);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "add_success"));

            } else if (isEDIT) {
                model.updatePosition(position);
                int i = 0;
                for (Position rs : lstPosition) {
                    if (rs.getPositionsRefId() == position.getPositionsRefId()) {
                        lstPosition.set(i, position);
                    }
                    i++;
                }
                if (lstPositionFiltered == null) {
                    lstPositionFiltered = new ArrayList<>(lstPosition);
                }
                updateFilterValue(position);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "update_success"));
                isDisplayBtnConfirm = false;
                isDISABLE = true;
                mselectedPosition = new Position(position);

                isEDIT = false;
            }
            if (isCOPY) {
                positionBackup = new Position(mselectedPosition);
                isDisplayBtnConfirm = false;
                isCOPY = false;
                isDISABLE = true;
            } else if (isADD) {
                position = new Position();
                isSelectedApp = true;
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            ClientMessage.logErr(ex.getMessage());
        }
    }

    private void updateFilterValue(Position item) {
        int i = 0;
        for (Position pos : lstPositionFiltered) {
            if (item.getPositionsRefId() == pos.getPositionsRefId()) {
                break;
            }
            i++;
        }
        lstPositionFiltered.set(i, item);
    }

    @Override
    public void handDelete() {
        try {
            PositionModel model = new PositionModel();
            model.deletePosition(position);
            int i = 0;
            for (Position rs : lstPosition) {
                if (rs.getPositionsRefId() == position.getPositionsRefId()) {
                    break;
                }
                i++;
            }
            lstPosition.remove(i);
            if (lstPosition.isEmpty()) {
                position = new Position();
                mselectedPosition = new Position();
            } else {
                position = new Position(lstPosition.get(0));
                mselectedPosition = new Position(lstPosition.get(0));
            }

            ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "del_success"));
        } catch (Exception ex) {
            Logger.getLogger(PositionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handCancel() {
        if (isADD) {
            position = new Position(mselectedPosition);
        } else if (isEDIT || isCOPY) {
            position = new Position(positionBackup);
        }
        isADD = false;
        isEDIT = false;
        isCOPY = false;
        isDISABLE = true;
        isDisplayBtnConfirm = false;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getMselectedPosition() {
        return mselectedPosition;
    }

    public void setMselectedPosition(Position mselectedPosition) {
        this.mselectedPosition = mselectedPosition;
    }

    public Position getPositionBackup() {
        return positionBackup;
    }

    public void setPositionBackup(Position positionBackup) {
        this.positionBackup = positionBackup;
    }

    public List<Position> getLstPosition() {
        return lstPosition;
    }

    public void setLstPosition(List<Position> lstPosition) {
        this.lstPosition = lstPosition;
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

    public List<SelectItem> getCboDepartment() {
        return cboDepartment;
    }

    public void setCboDepartment(List<SelectItem> cboDepartment) {
        this.cboDepartment = cboDepartment;
    }

    public List<SelectEvent> getLstPositionType() {
        return lstPositionType;
    }

    public void setLstPositionType(List<SelectEvent> lstPositionType) {
        this.lstPositionType = lstPositionType;
    }

    public List<Position> getLstPositionFiltered() {
        return lstPositionFiltered;
    }

    public void setLstPositionFiltered(List<Position> lstPositionFiltered) {
        this.lstPositionFiltered = lstPositionFiltered;
    }
}
