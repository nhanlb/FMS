/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.telsoft.controller;

import com.faplib.lib.ClientMessage;
import com.faplib.lib.SystemLogger;
import com.faplib.lib.TSFuncTemplate;
import com.faplib.lib.TelsoftException;
import com.faplib.lib.admin.gui.data.DATA_AppGUI;
import com.faplib.lib.admin.gui.entity.ETT_AppGUI;
import com.faplib.lib.admin.security.Authorizator;
import com.faplib.lib.util.DataUtil;
import com.faplib.util.StringUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author HaiDT
 */
@ManagedBean(name = "MngAppsController")
@ViewScoped
public class MngAppsController extends TSFuncTemplate implements Serializable {

    private List<ETT_AppGUI> mlistApp;
    private List<ETT_AppGUI> mlistAppFiltered;
    private ETT_AppGUI mtmpApp;
    private ETT_AppGUI[] mselectedApp;
    private String mstrAuth;

    public MngAppsController() {
        try {
            //Init list
            mlistApp = DataUtil.getData(DATA_AppGUI.class, "getListApp");

            if (!mlistApp.isEmpty()) {
                mtmpApp = new ETT_AppGUI(mlistApp.get(0));
                mselectedApp = new ETT_AppGUI[1];
                mselectedApp[0] = new ETT_AppGUI(mtmpApp);

            } else {
                mtmpApp = new ETT_AppGUI();
                mselectedApp = new ETT_AppGUI[0];
            }

            //get list auth
            mstrAuth = Authorizator.checkAuthorizator();

        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
        }
    }

    //Getters
    public List<ETT_AppGUI> getMlistApp() {
        return mlistApp;
    }

    public ETT_AppGUI getMtmpApp() {
        return mtmpApp;
    }

    public ETT_AppGUI[] getMselectedApp() {
        return mselectedApp;
    }

    public List<ETT_AppGUI> getMlistAppFiltered() {
        return mlistAppFiltered;
    }

    //Setters
    public void setMtmpApp(ETT_AppGUI mtmpApp) {
        this.mtmpApp = mtmpApp;
    }

    public void setMselectedApp(ETT_AppGUI[] mselectedApp) {
        if (!isDISABLE) {
            return;
        }
        this.mselectedApp = mselectedApp;
    }

    //Methods
    public void onRowSelect() {
        mtmpApp = new ETT_AppGUI(mselectedApp[mselectedApp.length - 1]);
    }

    public void setMlistAppFiltered(List<ETT_AppGUI> mlistAppFiltered) {
        this.mlistAppFiltered = mlistAppFiltered;
    }

    //Mothods
    @Override
    public void changeStateAdd() {
        super.changeStateAdd();
        mtmpApp = new ETT_AppGUI();
    }

    @Override
    public void changeStateCopy() {
        super.changeStateCopy();
        mtmpApp = new ETT_AppGUI(mselectedApp[mselectedApp.length - 1]);
    }

    @Override
    public void handOK() {
        //Trim text
        mtmpApp.setName(mtmpApp.getName().trim());
        mtmpApp.setCode(mtmpApp.getCode().trim());

        if (isADD || isCOPY) {
            try {
                //Check permission
                if (!getPermission("I")) {
                    return;
                }

                if (!StringUtil.isAlphaString(mtmpApp.getCode())) {
                    ClientMessage.logPErr("PP_MNGAPP", "app_code_invalid");
                    return;
                }

                String strResult = (String) DataUtil.performAction(DATA_AppGUI.class, "add", mtmpApp);
                mselectedApp = new ETT_AppGUI[1];
                mselectedApp[0] = new ETT_AppGUI(mtmpApp);
                mselectedApp[0].setAppId(strResult);

                //Reset list
                mlistApp = DataUtil.getData(DATA_AppGUI.class, "getListApp");
                mtmpApp = new ETT_AppGUI();
                mlistAppFiltered = null;

                //Message to client
                ClientMessage.logAdd();

            } catch (TelsoftException ex) {
                SystemLogger.getLogger().error(ex);
                ClientMessage.logErr(ClientMessage.MESSAGE_TYPE.ADD, ex.toString());
            }

        } else if (isEDIT) {
            try {
                //Check permission
                if (!getPermission("U")) {
                    return;
                }

                DataUtil.performAction(DATA_AppGUI.class, "edit", mtmpApp);
                mselectedApp[mselectedApp.length - 1] = new ETT_AppGUI(mtmpApp);

                //Reset list
                mlistApp = DataUtil.getData(DATA_AppGUI.class, "getListApp");
                mlistAppFiltered = null;

                //Message to client
                ClientMessage.logUpdate();

            } catch (TelsoftException ex) {
                SystemLogger.getLogger().error(ex);
                ClientMessage.logErr(ClientMessage.MESSAGE_TYPE.UPDATE, ex.toString());
            }
        }
    }

    @Override
    public void handDelete() {
        try {
            //Check permission
            if (!getPermission("D")) {
                return;
            }

            String strAppID = "";
            for (ETT_AppGUI app : mselectedApp) {
                strAppID += app.getAppId() + ",";
            }
            strAppID = StringUtil.removeLastChar(strAppID);
            DataUtil.performAction(DATA_AppGUI.class, "delete", strAppID);

            mlistApp = DataUtil.getData(DATA_AppGUI.class, "getListAll");
            mtmpApp = new ETT_AppGUI(mlistApp.get(0));
            mselectedApp = new ETT_AppGUI[1];
            mselectedApp[0] = new ETT_AppGUI(mtmpApp);
            mlistAppFiltered = null;

            //Message to client
            ClientMessage.logDelete();

        } catch (TelsoftException ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ClientMessage.MESSAGE_TYPE.DELETE, ex.toString());
        }
    }

    @Override
    public void handCancel() {
        super.handCancel();
        if (mselectedApp.length != 0) {
            mtmpApp = new ETT_AppGUI(mselectedApp[mselectedApp.length - 1]);
        }
    }

    public boolean isIsSelectedApp() {
        return mselectedApp.length != 0 && !mselectedApp[0].getAppId().isEmpty();
    }

    public String removeSign(String strInput) {
        return StringUtil.removeSign(strInput);
    }

    public boolean getPermission(String strRight) {
        boolean bReturnValue = false;

        try {
            bReturnValue = mstrAuth.contains(strRight);

        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
        }

        return bReturnValue;
    }
}
