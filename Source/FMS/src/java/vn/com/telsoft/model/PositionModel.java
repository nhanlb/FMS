package vn.com.telsoft.model;

import com.faplib.lib.SystemLogger;
import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import vn.com.telsoft.entity.Position;
import vn.com.telsoft.util.DateUtil;

/**
 *
 * @author HienTN
 */
public class PositionModel extends AMDataPreprocessor implements Serializable {

    public List<Position> getAllPosition() throws Exception {
        List<Position> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = " SELECT a.`POSITIONS_REF_ID`, a.`DEPT_ID`, a.`POSITION`, a.`COEFFICIENT`, a.`ALLOWANCE`, a.`START_DATE`, a.`END_DATE`, a.`status`,  "
                + " DATE_FORMAT(`START_DATE`,'%d/%m/%Y') startDateView, DATE_FORMAT(`END_DATE`,'%d/%m/%Y') endDateView  "
                + " FROM POSITIONS_REF a ORDER BY a.`POSITIONS_REF_ID` ";
        try {
            open();
            stmt = mConnection.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Position item = new Position();
                item.setPositionsRefId(rs.getInt("POSITIONS_REF_ID"));
                item.setDeptId(rs.getInt("DEPT_ID"));
                item.setPosition(rs.getString("POSITION"));
                item.setCoefficient(rs.getDouble("COEFFICIENT"));
                item.setAllowance(rs.getDouble("ALLOWANCE"));
                item.setStartDate(rs.getDate("START_DATE"));
                item.setEndDate(rs.getDate("END_DATE"));
                item.setStatus(rs.getInt("status"));
                item.setStartDateView(rs.getString("startDateView"));
                item.setEndDateView(rs.getString("endDateView"));

                lstReturn.add(item);
            }
        } finally {
            close(rs);
            close(stmt);
            close(mConnection);
        }
        return lstReturn;
    }

    //load Nguoi lien he tham chieu bang Department
    public List<SelectItem> getDepartment() throws Exception {
        List<SelectItem> cboDepartment = new ArrayList();

        try {
            open();
            String strSQL = "SELECT `DEPT_ID`, `NAME` FROM `DEPARTMENT` WHERE `STATUS`=1 ORDER BY `NAME` ";

            mStmt = mConnection.prepareStatement(strSQL);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                SelectItem item = new SelectItem();
                item.setValue(mRs.getString("DEPT_ID"));
                item.setLabel(mRs.getString("NAME"));
                cboDepartment.add(item);
            }
        } finally {
            close(mConnection, mStmt, mRs);
            close();
        }
        return cboDepartment;
    }

    public Position addPosition(Position item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "INSERT INTO POSITIONS_REF(`POSITIONS_REF_ID`, `DEPT_ID`, `POSITION`, `COEFFICIENT`, `ALLOWANCE`, `START_DATE`, `END_DATE`, `LAST_UPDATE_TIMES`, `status`) VALUES (?,?, ?, ?, ?, ?, ?,SYSDATE(),?)";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            int iID = Integer.parseInt(SQLUtil.getSequenceValue(mConnection, "SEQ_POSITIONS_REF"));
            stmt.setLong(1, iID);
            stmt.setInt(2, item.getDeptId());
            stmt.setString(3, item.getPosition());
            stmt.setDouble(4, item.getCoefficient());
            stmt.setDouble(5, item.getAllowance());
            stmt.setTimestamp(6, DateUtil.convertTimestamp(item.getStartDate()));
            stmt.setTimestamp(7, DateUtil.convertTimestamp(item.getEndDate()));
            stmt.setInt(8, item.getStatus());

            stmt.execute();
            item.setPositionsRefId(iID);
            item.setStartDateView(DateUtil.convertDateToString(item.getStartDate()));
            item.setEndDateView(DateUtil.convertDateToString(item.getEndDate()));
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

    public void updatePosition(Position item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "UPDATE POSITIONS_REF SET `DEPT_ID` = ?, `POSITION` = ?, `COEFFICIENT` = ?, `ALLOWANCE` = ?, "
                + " `START_DATE` = ?, `END_DATE` = ?, `LAST_UPDATE_TIMES` = SYSDATE(), `status` = ? "
                + " WHERE `POSITIONS_REF_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setInt(1, item.getDeptId());
            stmt.setString(2, item.getPosition());
            stmt.setDouble(3, item.getCoefficient());
            stmt.setDouble(4, item.getAllowance());
            stmt.setTimestamp(5, DateUtil.convertTimestamp(item.getStartDate()));
            stmt.setTimestamp(6, DateUtil.convertTimestamp(item.getEndDate()));
            stmt.setInt(7, item.getStatus());
            stmt.setInt(8, item.getPositionsRefId());

            stmt.execute();
            item.setStartDateView(DateUtil.convertDateToString(item.getStartDate()));
            item.setEndDateView(DateUtil.convertDateToString(item.getEndDate()));
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

    public void deletePosition(Position item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "DELETE FROM POSITIONS_REF WHERE `POSITIONS_REF_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setLong(1, item.getPositionsRefId());
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
