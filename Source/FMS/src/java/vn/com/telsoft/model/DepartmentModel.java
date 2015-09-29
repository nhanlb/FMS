package vn.com.telsoft.model;

import com.faplib.admin.security.User;
import com.faplib.lib.SystemLogger;
import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

import vn.com.telsoft.entity.Department;

/**
 *
 * @author HienTN
 */
public class DepartmentModel extends AMDataPreprocessor implements Serializable {

    public List<Department> getAllDepartment() throws Exception {
        List<Department> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "SELECT a.`DEPT_ID`, a.`PARENT_ID`, "
                + " a.`NAME`, a.`DEPT_CODE`,  "
                + " a.`PHONE`, a.`FAX`, a.`CONTACT_WHO`, a.`ADDRESS`, "
                + " a.`CITY_ID`,a.`DISTRICT_ID`,a.`WARD_ID`,a.`WORK_ADDRESS`, "
                + " a.`EMAIL`, "
                + " a.`DESCRIPTION`,a.`LEVEL`,a.`LAST_UPDATE_TIME`,a.`USER_NAME`,a.`STATUS`   "
                + " FROM `DEPARTMENT` a "
                + " WHERE a.DEPT_ID > 0 "
                + " ORDER BY a.`DEPT_ID`";
        try {
            open();
            stmt = mConnection.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Department item = new Department();
                item.setDeptId(rs.getLong("DEPT_ID"));
                item.setParentId(rs.getLong("PARENT_ID"));
                item.setName(rs.getString("NAME"));
                item.setDeptCode(rs.getString("DEPT_CODE"));
                item.setPhone(rs.getString("PHONE"));
                item.setFax(rs.getString("FAX"));
                item.setContactWho(rs.getString("CONTACT_WHO"));
                item.setAddress(rs.getString("ADDRESS"));
                item.setCityId(rs.getLong("CITY_ID"));
                item.setDistrictId(rs.getLong("DISTRICT_ID"));
                item.setWardId(rs.getLong("WARD_ID"));
                item.setWorkAddress(rs.getString("WORK_ADDRESS"));
                item.setEmail(rs.getString("EMAIL"));
                item.setDescription(rs.getString("DESCRIPTION"));
                item.setLevel(rs.getInt("LEVEL"));
                item.setLastUpdateTime(rs.getTimestamp("LAST_UPDATE_TIME"));
                item.setUserName(rs.getString("USER_NAME"));
                item.setStatus(rs.getLong("STATUS"));

                lstReturn.add(item);
            }
        } finally {
            close(rs);
            close(stmt);
            close(mConnection);
        }
        return lstReturn;
    }

    //load Nguoi lien he tham chieu bang STAFF
    public List<SelectItem> getStaff() throws Exception {
        List<SelectItem> cboStaff = new ArrayList();

        try {
            open();
            String strSQL = "SELECT `USER_ID`, `FULL_NAME` FROM `AM_USER` ORDER BY `USER_ID` ";

            mStmt = mConnection.prepareStatement(strSQL);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                cboStaff.add(new SelectItem(mRs.getString("USER_ID"), mRs.getString("FULL_NAME")));
            }
        } finally {
            close(mConnection, mStmt, mRs);
            close();
        }
        return cboStaff;
    }

    //load thanh pho
    public List<SelectItem> getCity() throws Exception {
        List<SelectItem> cboCity = new ArrayList();

        try {
            open();
            String strSQL = "SELECT `AREA_ID`, `DISPLAY_VALUE` FROM `AREA_REF` WHERE `AREA_TYPE` = 1 AND `STATUS` = 1  ORDER BY `DISPLAY_VALUE`";

            mStmt = mConnection.prepareStatement(strSQL);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                cboCity.add(new SelectItem(mRs.getString("AREA_ID"), mRs.getString("DISPLAY_VALUE")));
            }
        } finally {
            close(mConnection, mStmt, mRs);
            close();
        }
        return cboCity;
    }

    //load quan huyen
    public List<SelectItem> getDistrict(Long cityId) throws Exception {
        List<SelectItem> cboDistrict = new ArrayList();

        try {
            open();
            String strSQL = "SELECT a.`AREA_ID`, a.`DISPLAY_VALUE` "
                    + "FROM AREA_REF a "
                    + "INNER JOIN AREA_KEY b "
                    + "ON a.`AREA_TYPE` = 2 AND b.`PARENT_AREA_ID` = ? AND a.`AREA_ID` = b.`AREA_ID` AND a.`STATUS` = 1  ORDER BY a.`DISPLAY_VALUE`";

            mStmt = mConnection.prepareStatement(strSQL);
            mStmt.setLong(1, cityId);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                cboDistrict.add(new SelectItem(mRs.getString("AREA_ID"), mRs.getString("DISPLAY_VALUE")));
            }
        } finally {
            close(mConnection, mStmt, mRs);
            close();
        }
        return cboDistrict;
    }

    //load xa phuong
    public List<SelectItem> getWard(Long districtId) throws Exception {
        List<SelectItem> cboWard = new ArrayList();

        try {
            open();
            String strSQL = "SELECT a.`AREA_ID`, a.`DISPLAY_VALUE`  "
                    + " FROM AREA_REF a  "
                    + " INNER JOIN AREA_KEY b  "
                    + " ON a.`AREA_TYPE` = 3 AND b.`PARENT_AREA_ID` = ? AND a.`AREA_ID` = b.`AREA_ID` AND a.`STATUS` = 1  ORDER BY a.`DISPLAY_VALUE`";

            mStmt = mConnection.prepareStatement(strSQL);
            mStmt.setLong(1, districtId);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                cboWard.add(new SelectItem(mRs.getString("AREA_ID"), mRs.getString("DISPLAY_VALUE")));
            }
        } finally {
            close(mConnection, mStmt, mRs);
            close();
        }
        return cboWard;
    }

    public Department addDepartment(Department item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strUserLogin = User.getUserLogged().getUserName();
        String strSql = "INSERT INTO DEPARTMENT(`DEPT_ID`, `PARENT_ID`, `NAME`, `DEPT_CODE`,  "
                + " `PHONE`, `FAX`, `CONTACT_WHO`, `ADDRESS`, "
                + "`CITY_ID`,`DISTRICT_ID`,`WARD_ID`,`WORK_ADDRESS`,"
                + "`EMAIL`, `DESCRIPTION`,`LEVEL`,`LAST_UPDATE_TIME`,`USER_NAME`,`STATUS`) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE(),?,?)";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            int iID = Integer.parseInt(SQLUtil.getSequenceValue(mConnection, "SEQ_DEPARTMENT"));
            stmt.setLong(1, iID);
            stmt.setLong(2, item.getParentId());
            stmt.setString(3, item.getName());
            stmt.setString(4, item.getDeptCode());
            stmt.setString(5, item.getPhone());
            stmt.setString(6, item.getFax());
            stmt.setString(7, item.getContactWho());
            stmt.setString(8, item.getAddress());
            stmt.setLong(9, item.getCityId());
            stmt.setLong(10, item.getDistrictId());
            stmt.setLong(11, item.getWardId());
            stmt.setString(12, item.getWorkAddress());
            //stmt.setTimestamp(15, DateUtil.convertTimestamp(item.getDecisionDate()));
            stmt.setString(13, item.getEmail());
            stmt.setString(14, item.getDescription());
            stmt.setInt(15, item.getLevel());
            stmt.setString(16, strUserLogin);
            stmt.setLong(17, item.getStatus());

            stmt.execute();
            item.setDeptId(iID);
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

    public void updateDepartment(Department item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "UPDATE DEPARTMENT SET `PARENT_ID` = ?, `NAME` = ?, `DEPT_CODE` = ?,  "
                + " `PHONE` = ?, `FAX` = ?, `CONTACT_WHO` = ?, `ADDRESS` = ?, "
                + " `CITY_ID` = ?,`DISTRICT_ID` = ?,`WARD_ID` = ?,`WORK_ADDRESS` = ?,"
                + " `EMAIL` = ?, `DESCRIPTION` = ?,`LAST_UPDATE_TIME` = SYSDATE(),`STATUS` = ? "
                + " WHERE `DEPT_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setLong(1, item.getParentId());
            stmt.setString(2, item.getName());
            stmt.setString(3, item.getDeptCode());
            stmt.setString(4, item.getPhone());
            stmt.setString(5, item.getFax());
            stmt.setString(6, item.getContactWho());
            stmt.setString(7, item.getAddress());
            stmt.setLong(8, item.getCityId());
            stmt.setLong(9, item.getDistrictId());
            stmt.setLong(10, item.getWardId());
            stmt.setString(11, item.getWorkAddress());
            stmt.setString(12, item.getEmail());
            stmt.setString(13, item.getDescription());
            stmt.setLong(14, item.getStatus());
            stmt.setLong(15, item.getDeptId());

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

    public void deleteDepartment(Department item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "DELETE FROM `DEPARTMENT` WHERE `DEPT_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setLong(1, item.getDeptId());
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
