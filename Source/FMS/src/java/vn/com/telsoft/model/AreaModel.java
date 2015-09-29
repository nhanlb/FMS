package vn.com.telsoft.model;

import com.faplib.lib.SystemLogger;
import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vn.com.telsoft.entity.Area;

/**
 *
 * @author HienTN
 */
public class AreaModel extends AMDataPreprocessor implements Serializable {

    public List<Area> getAllArea() throws Exception {
        List<Area> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = " SELECT a.`area_id`,a.`AREA_CODE`, b.`PARENT_AREA_ID`, a.`area_type`, a.`IS_INTERNAL`, a.`short_display`, a.`display_value`, a.`description`, a.`status`  "
                + " FROM `AREA_REF` a "
                + " INNER JOIN `AREA_KEY` b "
                + " WHERE a.`AREA_ID` = b.`AREA_ID` ";
        try {
            open();
            stmt = mConnection.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Area item = new Area();
                item.setAreaId(rs.getLong("area_id"));
                item.setParentAreaId(rs.getLong("PARENT_AREA_ID"));
                item.setAreaCode(rs.getString("AREA_CODE"));
                item.setAreaType(rs.getLong("area_type"));
                item.setIsInternal(rs.getString("IS_INTERNAL"));
                item.setShortDisplay(rs.getString("short_display"));
                item.setDisplayValue(rs.getString("display_value"));
                item.setDescription(rs.getString("description"));
                item.setStatus(rs.getString("status"));
                lstReturn.add(item);
            }
        } finally {
            close(rs);
            close(stmt);
            close(mConnection);
        }
        return lstReturn;
    }

    public Area addArea(Area item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String StrSql = "INSERT INTO AREA_KEY(`area_id`,`PARENT_AREA_ID`) VALUES (?,?)";
        String strSql2 = "INSERT INTO AREA_REF(`area_id`, `area_type`, `AREA_CODE`, `IS_INTERNAL`, `short_display`, `display_value`, `description`, `status`) VALUES (?,?, ?, ?, ?, ?, ?,?)";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(StrSql);
            int area_id = Integer.parseInt(SQLUtil.getSequenceValue(mConnection, "SEQ_AREA"));
            stmt.setLong(1, area_id);
            stmt.setLong(2, item.getParentAreaId());
            stmt.execute();

            stmt = mConnection.prepareStatement(strSql2);
            stmt.setLong(1, area_id);
            stmt.setLong(2, item.getAreaType());
            stmt.setString(3, item.getAreaCode());
            stmt.setString(4, item.getIsInternal());
            stmt.setString(5, item.getShortDisplay());
            stmt.setString(6, item.getDisplayValue());
            stmt.setString(7, item.getDescription());
            stmt.setString(8, item.getStatus());
            stmt.execute();

            item.setAreaId(area_id);
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

    public void updateArea(Area item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "UPDATE AREA_REF SET `area_type` = ?, `AREA_CODE`=?, `IS_INTERNAL` = ?, `short_display` = ?, `display_value` = ?, `description` = ?, `status` = ? WHERE `area_id` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setLong(1, item.getAreaType());
            stmt.setString(2, item.getAreaCode());
            stmt.setString(3, item.getIsInternal());
            stmt.setString(4, item.getShortDisplay());
            stmt.setString(5, item.getDisplayValue());
            stmt.setString(6, item.getDescription());
            stmt.setString(7, item.getStatus());
            stmt.setLong(8, item.getAreaId());

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

    public void deleteArea(Area item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "DELETE FROM AREA_REF WHERE `area_id` = ?";
        String StrSql2 = "DELETE FROM AREA_KEY WHERE `area_id` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setLong(1, item.getAreaId());
            stmt.execute();

            stmt = mConnection.prepareStatement(StrSql2);
            stmt.setLong(1, item.getAreaId());
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
