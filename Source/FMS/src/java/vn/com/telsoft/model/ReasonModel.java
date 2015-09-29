package vn.com.telsoft.model;

import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.DataUtil;
import com.faplib.lib.util.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vn.com.telsoft.entity.Reason;

/**
 *
 * @author HaiDT
 */
public class ReasonModel extends AMDataPreprocessor implements Serializable {

    public List<Reason> getAllReason() throws Exception {
        List<Reason> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "SELECT a.reason_id, a.`reason_type`, a.`short_display`, a.`display_value`, a.`description`, a.`status` FROM REASON a";
        try {
            open();
            stmt = mConnection.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Reason item = new Reason();
                item.setReasonId(rs.getLong("reason_id"));
                item.setReasonType(rs.getString("reason_type"));
                item.setShortDisplay(rs.getString("short_display"));
                item.setDisplayValue(rs.getString("display_value"));
                item.setDescription(rs.getString("description"));
                item.setStatus(rs.getInt("status"));
                lstReturn.add(item);
            }
        } finally {
            close(rs);
            close(stmt);
            close(mConnection);
        }
        return lstReturn;
    }

    public List<Reason> searchReason(Reason itemIn) throws Exception {
        List<Reason> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "SELECT a.reason_id, a.`reason_type`, a.`short_display`, a.`display_value`, a.`description`, a.`status` FROM REASON a WHERE 1=1 ";
        String condition = "";
        List<String> lstParam = new ArrayList<>();
        if (itemIn.getReasonType() != null && !itemIn.getReasonType().equals("")) {
            condition += " AND reason_type = ?";
            lstParam.add(itemIn.getReasonType());
        }
        if (itemIn.getShortDisplay() != null && !itemIn.getShortDisplay().equals("")) {
            condition += " AND short_display LIKE '%?%'";
            lstParam.add(itemIn.getShortDisplay());
        }
        if (itemIn.getDisplayValue() != null && !itemIn.getDisplayValue().equals("")) {
            condition += " AND display_value LIKE '%?%'";
            lstParam.add(itemIn.getDisplayValue());
        }
        if (itemIn.getDescription() != null && !itemIn.getDescription().equals("")) {
            condition += " AND description LIKE '%?%'";
            lstParam.add(itemIn.getDescription());
        }
        try {
            open();
            strSql += condition;
            stmt = mConnection.prepareStatement(strSql);
            int i = 1;
            for (String string : lstParam) {
                stmt.setString(i, string);
                i++;
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                Reason item = new Reason();
                item.setReasonId(rs.getLong("reason_id"));
                item.setReasonType(rs.getString("reason_type"));
                item.setShortDisplay(rs.getString("short_display"));
                item.setDisplayValue(rs.getString("display_value"));
                item.setDescription(rs.getString("description"));
                item.setStatus(rs.getInt("status"));
                lstReturn.add(item);
            }
        } finally {
            close(rs);
            close(stmt);
            close(mConnection);
        }
        return lstReturn;
    }

    public Reason addReason(Reason item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "INSERT INTO REASON(`reason_id`, `reason_type`, `short_display`, `display_value`, `description`, `status`) VALUES (?,?, ?, ?, ?, ?)";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            String reason_id = SQLUtil.getSequenceValue(mConnection, "SEQ_REASON");
            stmt.setLong(1, Integer.parseInt(reason_id));
            stmt.setString(2, item.getReasonType());
            stmt.setString(3, item.getShortDisplay());
            stmt.setString(4, item.getDisplayValue());
            stmt.setString(5, item.getDescription());
            stmt.setInt(6, item.getStatus());
            stmt.executeUpdate();
            mConnection.commit();
            item.setReasonId(Integer.parseInt(reason_id));
            return item;
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

    public void updateReason(Reason item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "UPDATE REASON SET `reason_type` = ?, `short_display` = ?, `display_value` = ?, `description` = ?, `status` = ? WHERE `reason_id` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setString(1, item.getReasonType());
            stmt.setString(2, item.getShortDisplay());
            stmt.setString(3, item.getDisplayValue());
            stmt.setString(4, item.getDescription());
            stmt.setInt(5, item.getStatus());
            stmt.setLong(6, item.getReasonId());
            stmt.executeUpdate();
            mConnection.commit();
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

    public void deleteReason(Reason item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "DELETE FROM REASON WHERE `reason_id` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setLong(1, item.getReasonId());
            stmt.execute();
            mConnection.commit();
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }
}
