package vn.com.telsoft.model;

import com.faplib.lib.SystemLogger;
import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vn.com.telsoft.entity.Ethnics;

/**
 *
 * @author HienTN
 */


public class EthnicsModel extends AMDataPreprocessor implements Serializable{
    public List<Ethnics> getAllEthnics() throws Exception {
        List<Ethnics> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "SELECT `ETHNIC_ID`,`ETHNIC_NAME`,`DESCRIPTION`,`STATUS` "
                + "FROM `ETHNICS` ORDER BY `ETHNIC_ID`";
        try {
            open();
            stmt = mConnection.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Ethnics item = new Ethnics();
                item.setEthnicId(rs.getInt("ETHNIC_ID"));
                item.setEthnicName(rs.getString("ETHNIC_NAME"));
                item.setDescription(rs.getString("DESCRIPTION"));
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

    public Ethnics addEthnics(Ethnics item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "INSERT INTO ETHNICS(`ETHNIC_ID`, `ETHNIC_NAME`,`DESCRIPTION`,`STATUS`) VALUES (?,?,?,?)";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            int iID = Integer.parseInt(SQLUtil.getSequenceValue(mConnection, "SEQ_ETHNICS"));
            stmt.setLong(1, iID);
            stmt.setString(2, item.getEthnicName());
            stmt.setString(3, item.getDescription());
            stmt.setInt(4, item.getStatus());


            stmt.execute();
            item.setEthnicId(iID);
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

    public void updateEthnics(Ethnics item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "UPDATE ETHNICS SET `ETHNIC_NAME` = ?,`DESCRIPTION` = ?,`STATUS` = ? "
                + " WHERE `ETHNIC_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setString(1, item.getEthnicName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getStatus());
            stmt.setDouble(4, item.getEthnicId());

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

    public void deleteEthnics(Ethnics item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "DELETE FROM ETHNICS WHERE `ETHNIC_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setLong(1, item.getEthnicId());
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
