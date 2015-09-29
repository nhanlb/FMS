/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.com.telsoft.model;

import com.faplib.lib.admin.data.AMDataPreprocessor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import vn.com.telsoft.entity.ApParam;

/**
 *
 * @author HaiDT
 */
public class ApParamModel extends AMDataPreprocessor {

    private List<ApParam> listApparam = new ArrayList<ApParam>();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    boolean blValueReturn = true;
    public List<ApParam> getListApparamAll()
            throws SQLException {
        List<ApParam> lstResult = new ArrayList<ApParam>();
        String strSql = "SELECT a.par_type, a.par_name, a.par_value, a.description\n" +
                "  FROM ap_param a" +
                "  ORDER BY update_time desc";
        try {
            open();
            stmt = mConnection.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ApParam objApparam = new ApParam();
                objApparam.setStrParType(rs.getString("PAR_TYPE"));
                objApparam.setStrParName(rs.getString("PAR_NAME"));
                objApparam.setStrParValue(rs.getString("PAR_VALUE"));
                objApparam.setStrDescription(rs.getString("DESCRIPTION"));
                lstResult.add(objApparam);
            }
        } catch (Exception ex) {
            Logger.getLogger(ApParamModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close(rs);
            close(stmt);
            close();
        }
        return lstResult;
    }

    public List<ApParam> getListApparamSearch(List<ApParam> list)
            throws SQLException {
        List<ApParam> lstResult = new ArrayList<ApParam>();
        String strSql = "SELECT a.par_type, a.par_name, a.par_value, a.description\n" +
                "  FROM ap_param a " +
                "   Where 3=3 ";
        String strType = list.get(0).getStrParType();
        String strName = list.get(0).getStrParName();
        String strValue = list.get(0).getStrParValue();
        String strDescription = list.get(0).getStrDescription();
        if (!strType.equals("")) {
            strSql = strSql + " AND upper(PAR_TYPE) LIKE upper('%" + strType + "%')";
        }
        if (!strName.equals("")) {
            strSql = strSql + " AND upper(PAR_NAME) LIKE upper('%" + strName + "%') ";
        }
        if (!strValue.equals("")) {
            strSql = strSql + " AND upper(PAR_VALUE) LIKE upper('%" + strValue + "%')";
        }
        if (!strDescription.equals("")) {
            strSql = strSql + " AND upper(DESCRIPTION) LIKE upper('%" + strDescription + "%')";
        }
        strSql = strSql + " ORDER BY update_time desc";
        try {
            open();
            stmt = mConnection.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ApParam objApparam = new ApParam();
                objApparam.setStrParType(rs.getString("PAR_TYPE"));
                objApparam.setStrParName(rs.getString("PAR_NAME"));
                objApparam.setStrParValue(rs.getString("PAR_VALUE"));
                objApparam.setStrDescription(rs.getString("DESCRIPTION"));
                lstResult.add(objApparam);
            }
        } catch (Exception ex) {
            Logger.getLogger(ApParamModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close(rs);
            close(stmt);
            close();
        }
        return lstResult;
    }

    public void insertData(ApParam objApparam)
            throws SQLException, Exception {
        String strSql = "INSERT INTO ap_param(par_type, par_name, par_value, description) VALUES (?,?,?,?)";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setString(1, objApparam.getStrParType().toUpperCase());
            stmt.setString(2, objApparam.getStrParName());
            stmt.setString(3, objApparam.getStrParValue());
            stmt.setString(4, objApparam.getStrDescription());
            stmt.execute();
            mConnection.commit();
            //  blValueReturn = true;
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close();
        }
    }

//    public boolean updateData(ApParam objApparam) throws SQLException {
    public void updateData(ApParam objApparam)
            throws SQLException, Exception {
        String strSql = "UPDATE ap_param SET ap_param.par_name= ?, ap_param.description = ?" +
                " WHERE ap_param.par_type=? AND ap_param.par_value = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setString(1, objApparam.getStrParName());
            stmt.setString(2, objApparam.getStrDescription());
            stmt.setString(3, objApparam.getStrParType().toUpperCase());
            stmt.setString(4, objApparam.getStrParValue());
            stmt.execute();
            mConnection.commit();
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close();
        }
    }

    public void deleteData(ApParam obj)
            throws SQLException, Exception {
        String strSql = "DELETE ap_param WHERE ap_param.par_type = ? and ap_param.par_value = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setString(1, obj.getStrParType());
            stmt.setString(2, obj.getStrParValue());
            stmt.execute();
            mConnection.commit();
        } finally {
            mConnection.setAutoCommit(true);
            close(stmt);
            close();
            blValueReturn = true;
        }
    }

}
