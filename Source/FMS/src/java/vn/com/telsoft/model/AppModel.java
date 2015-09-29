/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.telsoft.model;

import com.faplib.lib.SystemLogger;
import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.admin.gui.entity.ETT_AccessRight;
import com.faplib.lib.admin.gui.entity.ETT_AppGUI;
import com.faplib.lib.admin.gui.entity.ETT_ModuleGUI;
import com.faplib.lib.config.Config;
import com.faplib.lib.util.SQLUtil;
import com.faplib.util.StringUtil;
import com.faplib.ws.client.ClientRequestProcessor;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ChienDX
 */
public class AppModel extends AMDataPreprocessor implements Serializable {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void delete(String strAppID) throws Exception {
        PreparedStatement stmt = null;

        try {
            open();
            mConnection.setAutoCommit(false);

            //Delete app
            logBeforeDelete("am_app", "app_id IN (" + strAppID + ")");
            String strSQL = "DELETE FROM am_app WHERE app_id IN (" + strAppID + ")";
            stmt = mConnection.prepareStatement(strSQL);
            stmt.execute();

            //Commit
            mConnection.commit();

        } catch (Exception ex) {
            mConnection.rollback();
            SystemLogger.getLogger().error(ex);
            throw new Exception(ex.toString());

        } finally {
            close(stmt);
            close();
        }
    }

    public String add(ETT_AppGUI app) throws Exception {
        PreparedStatement stmt = null;
        String strIdFromSequence = "";

        try {
            open();

            //Update app
            strIdFromSequence = SQLUtil.getSequenceValue(mConnection, "SEQ_AM_APP_ID", "am_app", "app_id");
            String strSQL = "INSERT INTO am_app(app_id, name, code, status, ord) VALUES(?,?,?,?,?)";
            stmt = mConnection.prepareStatement(strSQL);
            stmt.setString(1, strIdFromSequence);
            stmt.setString(2, StringUtil.fix(app.getName()));
            stmt.setString(3, StringUtil.fix(app.getCode()));
            stmt.setString(4, StringUtil.fix(app.getStatus()));
            stmt.setString(5, StringUtil.fix(app.getOrd()));
            stmt.execute();
            logAfterInsert("am_app", "app_id=" + strIdFromSequence);

        } catch (Exception ex) {
            mConnection.rollback();
            SystemLogger.getLogger().error(ex);
            throw new Exception(ex.toString());

        } finally {
            close(stmt);
            close();
        }

        return strIdFromSequence;
    }

    public void edit(ETT_AppGUI app) throws Exception {
        PreparedStatement stmt = null;

        try {
            open();
            mConnection.setAutoCommit(false);

            //Update app
            List listChange = logBeforeUpdate("am_app", "app_id=" + app.getAppId());
            String strSQL = "UPDATE am_app SET name=?, code=?, status=?, ord=? WHERE app_id=?";
            stmt = mConnection.prepareStatement(strSQL);
            stmt.setString(1, StringUtil.fix(app.getName()));
            stmt.setString(2, StringUtil.fix(app.getCode()));
            stmt.setString(3, StringUtil.fix(app.getStatus()));
            stmt.setString(4, StringUtil.fix(app.getOrd()));
            stmt.setString(5, StringUtil.fix(app.getAppId()));
            stmt.execute();
            logAfterUpdate(listChange);
            close(stmt);

            //Commit
            mConnection.commit();

        } catch (Exception ex) {
            mConnection.rollback();
            SystemLogger.getLogger().error(ex);
            throw new Exception(ex.toString());

        } finally {
            close(stmt);
            close();
        }
    }

    public List<ETT_AppGUI> getListApp() throws Exception {
        List<ETT_AppGUI> listReturn = new ArrayList<ETT_AppGUI>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            open();
            String strSQL = "SELECT a.app_id, a.code, a.name, a.status, a.ord FROM am_app a ORDER BY a.ord ASC";
            stmt = mConnection.prepareStatement(strSQL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ETT_AppGUI tmpAppGUI = new ETT_AppGUI();
                tmpAppGUI.setAppId(StringUtil.fix(rs.getString(1)));
                tmpAppGUI.setCode(StringUtil.fix(rs.getString(2)));
                tmpAppGUI.setName(StringUtil.fix(rs.getString(3)));
                tmpAppGUI.setStatus(StringUtil.fix(rs.getString(4)));
                tmpAppGUI.setOrd(StringUtil.fix(rs.getString(5)));
                listReturn.add(tmpAppGUI);
            }

        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            throw new Exception(ex.toString());

        } finally {
            close(rs);
            close(stmt);
            close();
        }

        return listReturn;
    }

    public List<ETT_AppGUI> getListAppObj(String strAppCode) throws Exception {

        if (Config.isWSEnabled()) {
            return ClientRequestProcessor.getListAppGUI(strAppCode);
        }

        List<ETT_AppGUI> listReturn = new ArrayList<ETT_AppGUI>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        PreparedStatement stmt2 = null;
        ResultSet rs2 = null;

        String strIn = "";
        String[] arrAppCode = strAppCode.split(",");
        for (String strCode : arrAppCode) {
            if (!strAppCode.isEmpty()) {
                strIn = strIn + "'" + strCode.trim() + "',";
            }
        }
        strIn = StringUtil.removeLastChar(strIn);

        try {
            open();
            String strSQL = "SELECT a.app_id, a.code, a.name, a.status "
                    + "FROM am_app a WHERE a.code IN(" + strIn + ")"
                    + "AND a.status=1 ORDER BY a.ord ASC";
            
            String strSQL2 = "SELECT a.obj_id, b.name, b.status, b.parent_id, b.path, b.obj_type, "
                    + "b.ord, b.description FROM am_app_obj a, am_object b WHERE a.app_id = ? AND a.obj_id = b.object_id "
                    + "ORDER BY b.ord ASC";
            stmt = mConnection.prepareStatement(strSQL);
            stmt2 = mConnection.prepareStatement(strSQL2);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ETT_AppGUI tmpAppGUI = new ETT_AppGUI();
                tmpAppGUI.setAppId(StringUtil.fix(rs.getString(1)));
                tmpAppGUI.setCode(StringUtil.fix(rs.getString(2)));
                tmpAppGUI.setName(StringUtil.fix(rs.getString(3)));
                tmpAppGUI.setStatus(StringUtil.fix(rs.getString(4)));

                //Get object
                stmt2.setString(1, tmpAppGUI.getAppId());
                rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    ETT_ModuleGUI tmpModuleGUI = new ETT_ModuleGUI();
                    tmpModuleGUI.setObjectId(StringUtil.fix(rs2.getString(1)));
                    tmpModuleGUI.setName(StringUtil.fix(rs2.getString(2)));
                    tmpModuleGUI.setStatus(StringUtil.fix(rs2.getString(3)));
                    tmpModuleGUI.setParentId(StringUtil.fix(rs2.getString(4)));
                    tmpModuleGUI.setPath(StringUtil.fix(rs2.getString(5)));
                    tmpModuleGUI.setObjType(StringUtil.fix(rs2.getString(6)));
                    tmpModuleGUI.setOrder(StringUtil.fix(rs2.getString(7)));
                    tmpModuleGUI.setDescription(StringUtil.fix(rs2.getString(8)));
                    tmpAppGUI.getMlistModule().add(tmpModuleGUI);
                }

                listReturn.add(tmpAppGUI);
            }

        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            throw new Exception(ex.toString());

        } finally {
            close(rs);
            close(stmt);
            close(rs2);
            close(stmt2);
            close();
        }

        return listReturn;
    }

    public List<ETT_AppGUI> getListAll() throws Exception {
        List<ETT_AppGUI> listReturn = new ArrayList<ETT_AppGUI>();
        List<ETT_AccessRight> listAcessRight = new ArrayList<ETT_AccessRight>();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        PreparedStatement stmt2 = null;
        ResultSet rs2 = null;

        ResultSet rsRight = null;
        PreparedStatement stmtRight = null;

        ResultSet rsApp = null;
        PreparedStatement stmtApp = null;

        try {
            open();
            //Get list access right
            String StrSQL = "SELECT code, name FROM am_right ORDER BY code";
            stmt = mConnection.prepareStatement(StrSQL);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ETT_AccessRight tmp = new ETT_AccessRight();
                tmp.setRightCode(rs.getString(1));
                tmp.setName(rs.getString(2));
                tmp.setAccess(0);
                tmp.setActive(false);
                listAcessRight.add(tmp);
            }
            close(rs);
            close(stmt);

            //Get apps
            String strSQL = "SELECT a.app_id, a.code, a.name, a.status FROM am_app a ORDER BY a.ord ASC";
            String strSQL2 = "SELECT a.obj_id, b.name, b.status, b.parent_id, b.path, b.obj_type, b.ord, b.description "
                    + "FROM am_app_obj a, am_object b WHERE a.app_id = ? AND a.obj_id = b.object_id "
                    + "ORDER BY b.ord ASC";
            String strSqlRight = "SELECT a.right_code, a.access_type FROM am_object_right a WHERE a.object_id=?";
            String strSqlApp = "SELECT a.app_id FROM am_app_obj a, am_app b WHERE a.obj_id = ? AND a.app_id = b.app_id";

            stmt = mConnection.prepareStatement(strSQL);
            stmt2 = mConnection.prepareStatement(strSQL2);
            stmtRight = mConnection.prepareStatement(strSqlRight);
            stmtApp = mConnection.prepareStatement(strSqlApp);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ETT_AppGUI tmpAppGUI = new ETT_AppGUI();
                tmpAppGUI.setAppId(StringUtil.fix(rs.getString(1)));
                tmpAppGUI.setCode(StringUtil.fix(rs.getString(2)));
                tmpAppGUI.setName(StringUtil.fix(rs.getString(3)));
                tmpAppGUI.setStatus(StringUtil.fix(rs.getString(4)));

                stmt2.setString(1, tmpAppGUI.getAppId());
                rs2 = stmt2.executeQuery();
                while (rs2.next()) {
                    ETT_ModuleGUI tmpModuleGUI = new ETT_ModuleGUI();
                    tmpModuleGUI.setObjectId(StringUtil.fix(rs2.getString(1)));
                    tmpModuleGUI.setName(StringUtil.fix(rs2.getString(2)));
                    tmpModuleGUI.setStatus(StringUtil.fix(rs2.getString(3)));
                    tmpModuleGUI.setParentId(StringUtil.fix(rs2.getString(4)));
                    tmpModuleGUI.setPath(StringUtil.fix(rs2.getString(5)));
                    tmpModuleGUI.setObjType(StringUtil.fix(rs2.getString(6)));
                    tmpModuleGUI.setOrder(StringUtil.fix(rs2.getString(7)));
                    tmpModuleGUI.setDescription(StringUtil.fix(rs2.getString(8)));

                    if (tmpModuleGUI.getParentId().equals("0")) {
                        tmpModuleGUI.setParentAppId("app" + tmpAppGUI.getAppId());
                    } else {
                        tmpModuleGUI.setParentAppId(tmpModuleGUI.getParentId());
                    }

                    //Get object right
                    List<ETT_AccessRight> tmpListAcessRight = new ArrayList<ETT_AccessRight>();
                    for (ETT_AccessRight accessRight : listAcessRight) {
                        tmpListAcessRight.add(new ETT_AccessRight(accessRight));
                    }
                    tmpModuleGUI.setListAccessRight(tmpListAcessRight);

                    stmtRight.setString(1, tmpModuleGUI.getObjectId());
                    rsRight = stmtRight.executeQuery();
                    while (rsRight.next()) {
                        for (ETT_AccessRight accessRight : tmpListAcessRight) {
                            if (rsRight.getString(1).equals(accessRight.getRightCode())) {
                                accessRight.setActive(true);
                                accessRight.setAccess(rsRight.getInt(2));
                            }
                        }
                    }
                    close(rsRight);

                    //Get module app
                    stmtApp.setString(1, tmpModuleGUI.getObjectId());
                    rsApp = stmtApp.executeQuery();
                    while (rsApp.next()) {
                        ETT_AppGUI tmpApp = new ETT_AppGUI();
                        tmpApp.setAppId(StringUtil.fix(rsApp.getString(1)));
                        tmpModuleGUI.setApp(tmpApp);
                    }
                    close(rsApp);

                    //Add to tmpApp
                    tmpAppGUI.getMlistModule().add(tmpModuleGUI);
                }

                listReturn.add(tmpAppGUI);
            }

        } catch (Exception ex) {
            throw new Exception(ex.toString());

        } finally {
            close(rsApp);
            close(stmtApp);
            close(rsRight);
            close(stmtRight);
            close(rs2);
            close(stmt2);
            close(rs);
            close(stmt);
            close();
        }

        return listReturn;
    }
}
