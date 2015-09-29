package vn.com.telsoft.model;

import com.faplib.lib.SystemLogger;
import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vn.com.telsoft.entity.SpecialObject;
import vn.com.telsoft.util.DateUtil;

/**
 *
 * @author
 */
public class SpecialObjectModel extends AMDataPreprocessor implements Serializable {

    public List<SpecialObject> getAllSpecialObject() throws Exception {
        List<SpecialObject> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = " SELECT a.`SPECIAL_OBJECT_ID`, a.`SPECIAL_OBJECT_NAME`, a.`START_DATE`, DATE_FORMAT(a.`START_DATE`,'%d/%m/%Y') startDateView, "
                + "a.`END_DATE`, DATE_FORMAT(a.`END_DATE`,'%d/%m/%Y') endDateView, a.`DECISION_NUM`, a.`DECISION_DATE`, "
                + "DATE_FORMAT(a.`DECISION_DATE`,'%d/%m/%Y') decisionDateView, a.`DECISION_DEPT`, a.`DECISION_SIGNER`, a.`DECISION_DOC_ID`,"
                + "a.`DESCRIPTION`, a.`STATUS`"
                + "FROM `SPECIAL_OBJECT` a ORDER BY a.`SPECIAL_OBJECT_ID` ";
        try {
            open();
            stmt = mConnection.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                SpecialObject item = new SpecialObject();
                item.setSpecialObjectId(rs.getInt("SPECIAL_OBJECT_ID"));
                item.setSpecialObjectName(rs.getString("SPECIAL_OBJECT_NAME"));
                item.setStartDate(rs.getDate("START_DATE"));
                item.setStartDateView(rs.getString("startDateView"));
                item.setEndDate(rs.getDate("END_DATE"));
                item.setEndDateView(rs.getString("endDateView"));
                item.setDecisionNum(rs.getString("DECISION_NUM"));
                item.setDecisionDate(rs.getDate("DECISION_DATE"));
                item.setDecisionDateView(rs.getString("decisionDateView"));
                item.setDecisionDept(rs.getInt("DECISION_DEPT"));
                item.setDecisionSigner(rs.getString("DECISION_SIGNER"));
                item.setDecisionDocId(rs.getInt("DECISION_DOC_ID"));
                item.setDescription(rs.getString("DESCRIPTION"));
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

    public SpecialObject addSpecialObject(SpecialObject item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = " INSERT INTO `SPECIAL_OBJECT` (`SPECIAL_OBJECT_ID`, `SPECIAL_OBJECT_NAME`, `START_DATE`, "
                + " `END_DATE`, `DECISION_NUM`, `DECISION_DATE`, "
                + " `DECISION_DEPT`, `DECISION_SIGNER`, `DECISION_DOC_ID`,"
                + " `DESCRIPTION`, `STATUS`) VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            int iID = Integer.parseInt(SQLUtil.getSequenceValue(mConnection, "SEQ_SPECIAL_OBJECT"));
            stmt.setLong(1, iID);
            stmt.setString(2, item.getSpecialObjectName());
            stmt.setTimestamp(3, DateUtil.convertTimestamp(item.getStartDate()));
            stmt.setTimestamp(4, DateUtil.convertTimestamp(item.getEndDate()));
            stmt.setString(5, item.getDecisionNum());
            stmt.setTimestamp(6, DateUtil.convertTimestamp(item.getDecisionDate()));
            stmt.setInt(7, item.getDecisionDept());
            stmt.setString(8, item.getDecisionSigner());
            stmt.setInt(9, item.getDecisionDocId());
            stmt.setString(10, item.getDescription());
            stmt.setInt(11, item.getStatus());

            stmt.execute();
            item.setSpecialObjectId(iID);
            item.setStartDateView(DateUtil.convertDateToString(item.getStartDate()));
            item.setEndDateView(DateUtil.convertDateToString(item.getEndDate()));
            item.setDecisionDateView(DateUtil.convertDateToString(item.getDecisionDate()));
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

    public void updateSpecialObject(SpecialObject item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = " UPDATE `SPECIAL_OBJECT` SET `SPECIAL_OBJECT_NAME`= ?, `START_DATE`= ?, "
                + " `END_DATE`= ?, `DECISION_NUM`= ?, `DECISION_DATE`= ?, `DECISION_DEPT`= ?, `DECISION_SIGNER`= ?, "
                + " `DECISION_DOC_ID`= ?,`DESCRIPTION`= ?, `STATUS` = ? WHERE `SPECIAL_OBJECT_ID` = ? ";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);

            stmt.setString(1, item.getSpecialObjectName());
            stmt.setTimestamp(2, DateUtil.convertTimestamp(item.getStartDate()));
            stmt.setTimestamp(3, DateUtil.convertTimestamp(item.getEndDate()));
            stmt.setString(4, item.getDecisionNum());
            stmt.setTimestamp(5, DateUtil.convertTimestamp(item.getDecisionDate()));
            stmt.setInt(6, item.getDecisionDept());
            stmt.setString(7, item.getDecisionSigner());
            stmt.setInt(8, item.getDecisionDocId());
            stmt.setString(9, item.getDescription());
            stmt.setInt(10, item.getStatus());
            stmt.setInt(11, item.getSpecialObjectId());

            stmt.execute();
            item.setStartDateView(DateUtil.convertDateToString(item.getStartDate()));
            item.setEndDateView(DateUtil.convertDateToString(item.getEndDate()));
            item.setDecisionDateView(DateUtil.convertDateToString(item.getDecisionDate()));
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

    public void deleteSpecialObject(SpecialObject item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = " DELETE FROM `SPECIAL_OBJECT` WHERE `SPECIAL_OBJECT_ID` = ? ";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setLong(1, item.getSpecialObjectId());
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
