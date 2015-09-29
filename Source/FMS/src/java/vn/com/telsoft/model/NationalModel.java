package vn.com.telsoft.model;

import com.faplib.lib.SystemLogger;
import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vn.com.telsoft.entity.National;

/**
 *
 * @author HienTN
 */
public class NationalModel extends AMDataPreprocessor implements Serializable {

    public List<National> getAllNational() throws Exception {
        List<National> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "SELECT `NATIONAL_ID`,`NATIONAL_NAME`,`NATIONAL_CODE`,`STATUS` "
                + "FROM `NATIONAL` ORDER BY `NATIONAL_ID`";
        try {
            open();
            stmt = mConnection.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                National item = new National();
                item.setNationalId(rs.getInt("NATIONAL_ID"));
                item.setNationalName(rs.getString("NATIONAL_NAME"));
                item.setNationalCode(rs.getString("NATIONAL_CODE"));
                item.setStatus(rs.getInt("STATUS"));

                lstReturn.add(item);
            }
        } finally {
            close(rs);
            close(stmt);
            close(mConnection);
        }
        return lstReturn;
    }

    public National addNational(National item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "INSERT INTO NATIONAL(`NATIONAL_ID`, `NATIONAL_NAME`,`NATIONAL_CODE`,`STATUS`) VALUES (?,?,?,?)";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            int iID = Integer.parseInt(SQLUtil.getSequenceValue(mConnection, "SEQ_NATIONAL"));
            stmt.setLong(1, iID);
            stmt.setString(2, item.getNationalName());
            stmt.setString(3, item.getNationalCode());
            stmt.setInt(4, item.getStatus());


            stmt.execute();
            item.setNationalId(iID);
            mConnection.commit();
            return item;
        } catch (Exception ex) {
            mConnection.rollback();
            SystemLogger.getLogger().error(ex);
            throw new Exception(ex);

        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

    public void updateNational(National item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "UPDATE NATIONAL SET `NATIONAL_NAME` = ?,`NATIONAL_CODE` = ?,`STATUS` = ? "
                + " WHERE `NATIONAL_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setString(1, item.getNationalName());
            stmt.setString(2, item.getNationalCode());
            stmt.setDouble(3, item.getStatus());
            stmt.setDouble(4, item.getNationalId());

            stmt.execute();
            mConnection.commit();
        } catch (Exception ex) {
            mConnection.rollback();
            SystemLogger.getLogger().error(ex);
            throw new Exception(ex);

        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

    public void deleteNational(National item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "DELETE FROM NATIONAL WHERE `NATIONAL_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setLong(1, item.getNationalId());
            stmt.execute();
            mConnection.commit();
        } catch (Exception ex) {
            mConnection.rollback();
            SystemLogger.getLogger().error(ex);
            throw new Exception(ex);

        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

}
