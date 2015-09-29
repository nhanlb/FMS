package vn.com.telsoft.model;

import com.faplib.admin.security.User;
import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vn.com.telsoft.entity.Staff;
import vn.com.telsoft.util.Utility;

/**
 *
 * @author HaiDT
 */
public class StaffModel extends AMDataPreprocessor implements Serializable {

    public List<Staff> getStaffList(String deptId) throws SQLException, Exception {
        List<Staff> lstRs = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            open();
            String strSQL = "SELECT "
                    + "  STAFF_ID, STAFF_CODE, FULL_NAME, "
                    + "  PHONE, ADDRESS, CMND, DEPT_ID, "
                    + "  POSITION, CITY_ID, DISTRICT_ID, "
                    + "  WARD_ID, DATE_FORMAT(a.`BIRTH_DAY`,'%d/%m/%Y') BIRTH_DAY, GENDER, "
                    + "  TAX_NUM, EMAIL, USER_NAME, STATUS "
                    + " FROM staff a "
                    + " WHERE a.DEPT_ID = ? ";
            stmt = mConnection.prepareStatement(strSQL);
            stmt.setString(1, deptId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Staff item = new Staff();
                item.setStaffId(rs.getString("STAFF_ID"));
                item.setStaffCode(rs.getString("STAFF_CODE"));
                item.setFullName(rs.getString("FULL_NAME"));
                item.setPhone(rs.getString("PHONE"));
                item.setAddress(rs.getString("ADDRESS"));
                item.setCmnd(rs.getString("CMND"));
                item.setDeptId(rs.getString("DEPT_ID"));
                item.setPosition(rs.getString("POSITION"));
                item.setCityId(rs.getString("CITY_ID"));
                item.setDistrictId(rs.getString("DISTRICT_ID"));
                item.setWardId(rs.getString("WARD_ID"));
                item.setBirthDay(Utility.convertStringToDate(rs.getString("BIRTH_DAY"), "dd/MM/yyyy"));
                item.setGender(rs.getString("GENDER"));
                item.setTaxNum(rs.getString("TAX_NUM"));
                item.setEmail(rs.getString("EMAIL"));
                item.setUserName(rs.getString("USER_NAME"));
                item.setStatus(rs.getString("STATUS"));
                lstRs.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            close(rs);
            close(stmt);
            close();
        }
        return lstRs;
    }

    public Staff insertStaff(Staff item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "INSERT INTO fms_owner.staff "
                + "            (STAFF_CODE, FULL_NAME, PHONE, ADDRESS, "
                + "             CMND, DEPT_ID, POSITION, CITY_ID, "
                + "             DISTRICT_ID, WARD_ID, BIRTH_DAY,GENDER,  "
                + "             TAX_NUM, EMAIL, USER_NAME, STATUS, STAFF_ID) "
                + "         VALUES (?, ?, ? , ?, "
                + "                 ?, ?, ? , ?,"
                + "                 ?, ?, STR_TO_DATE(?, '%d/%m/%Y'), ?, "
                + "                 ?, ?, ?, ?, ?)";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            String strId = SQLUtil.getSequenceValue(mConnection, "SEQ_STAFF");
            stmt.setString(1, item.getStaffCode());
            stmt.setString(2, item.getFullName());
            stmt.setString(3, item.getPhone());
            stmt.setString(4, item.getAddress());
            stmt.setString(5, item.getCmnd());
            stmt.setString(6, item.getDeptId());
            stmt.setString(7, item.getPosition());
            stmt.setString(8, item.getCityId());
            stmt.setString(9, item.getDistrictId());
            stmt.setString(10, item.getWardId());
            stmt.setString(11, Utility.converDatetoString(item.getBirthDay()));
            stmt.setString(12, item.getGender());
            stmt.setString(13, item.getTaxNum());
            stmt.setString(14, item.getEmail());
            stmt.setString(15, User.getUserLogged().getUserName());
            stmt.setString(16, item.getStatus());
            stmt.setString(17, strId);
            stmt.executeUpdate();
            mConnection.commit();
            item.setStaffId(strId);
            return item;
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

    public void updateStaff(Staff item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "UPDATE staff"
                + "     SET STAFF_CODE = ?, FULL_NAME = ?, PHONE = ?, ADDRESS = ?,"
                + "         CMND = ?, DEPT_ID = ?, POSITION = ?, CITY_ID = ?,"
                + "         DISTRICT_ID = ?, WARD_ID = ?, BIRTH_DAY = STR_TO_DATE(?, '%d/%m/%Y'), GENDER = ?,"
                + "         TAX_NUM = ?, EMAIL = ?, STATUS = ?"
                + "     WHERE STAFF_ID = ? ";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setString(1, item.getStaffCode());
            stmt.setString(2, item.getFullName());
            stmt.setString(3, item.getPhone());
            stmt.setString(4, item.getAddress());
            stmt.setString(5, item.getCmnd());
            stmt.setString(6, item.getDeptId());
            stmt.setString(7, item.getPosition());
            stmt.setString(8, item.getCityId());
            stmt.setString(9, item.getDistrictId());
            stmt.setString(10, item.getWardId());
            stmt.setString(11, Utility.converDatetoString(item.getBirthDay()));
            stmt.setString(12, item.getGender());
            stmt.setString(13, item.getTaxNum());
            stmt.setString(14, item.getEmail());
            stmt.setString(15, item.getStatus());
            stmt.setString(16, item.getStaffId());
            stmt.executeUpdate();
            mConnection.commit();
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

    public void deleteStaff(Staff item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "DELETE FROM staff WHERE STAFF_ID = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setString(1, item.getStaffId());
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
