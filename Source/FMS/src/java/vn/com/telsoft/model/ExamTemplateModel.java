package vn.com.telsoft.model;

import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vn.com.telsoft.entity.ExamTemplate;

/**
 *
 * @author AnhTD
 */
public class ExamTemplateModel extends AMDataPreprocessor implements Serializable {

    public List<ExamTemplate> getAllTemplates() throws Exception {
        List<ExamTemplate> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql =
                "SELECT "
                + "  `EXAM_ID`, "
                + "  `EXAM_NAME`, "
                + "  `EXAM_CODE`, "
                + "  `EXAM_ADDRESS`, "
                + "  `DURATION`, "
                + "  `MAX_POINT`, "
                + "  `EXAM_PASS_TERM`, "
                + "  `EXAM_RATE`, "
                + "  `IS_DEFAULT`, "
                + "  `ORDER_TYPE`, "
                + "  `PRIORITY`, "
                + "  `ORD`, "
                + "  `STATUS` "
                + "FROM `EXAM_TEMPLATE`";
        try {
            open();
            stmt = mConnection.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ExamTemplate item = new ExamTemplate();
                item.setExamId(rs.getLong("EXAM_ID"));
                item.setExamName(rs.getString("EXAM_NAME"));
                item.setExamCode(rs.getString("EXAM_CODE"));
                item.setExamAddress(rs.getString("EXAM_ADDRESS"));
                item.setDuration(rs.getInt("DURATION"));
                item.setMaxPoint(rs.getInt("MAX_POINT"));
                item.setExamPassTerm(rs.getDouble("EXAM_PASS_TERM"));
                item.setExamRate(rs.getDouble("EXAM_RATE"));
                item.setIsDefault(rs.getString("IS_DEFAULT"));
                item.setOrderType(rs.getString("ORDER_TYPE"));
                item.setPriority(rs.getInt("PRIORITY"));
                item.setOrd(rs.getInt("ORD"));
                item.setStatus(rs.getString("STATUS"));
                lstReturn.add(item);
            }
        } finally {
            close(rs);
            close(stmt);
            close(mConnection);
        }
        return lstReturn;
    }

    public List<ExamTemplate> searchTemplates(ExamTemplate itemIn) throws Exception {
        List<ExamTemplate> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "SELECT "
                + "  `EXAM_ID`, "
                + "  `EXAM_NAME`, "
                + "  `EXAM_CODE`, "
                + "  `EXAM_ADDRESS`, "
                + "  `DURATION`, "
                + "  `MAX_POINT`, "
                + "  `EXAM_PASS_TERM`, "
                + "  `EXAM_RATE`, "
                + "  `IS_DEFAULT`, "
                + "  `ORDER_TYPE`, "
                + "  `PRIORITY`, "
                + "  `ORD`, "
                + "  `STATUS` "
                + "FROM `EXAM_TEMPLATE` a WHERE 1=1 ";
        String condition = "";
        List<String> lstParam = new ArrayList<>();
        if (itemIn.getExamCode() != null && !itemIn.getExamCode().equals("")) {
            condition += " AND EXAM_CODE LIKE '%?%'";
            lstParam.add(itemIn.getExamCode());
        }
        if (itemIn.getExamName() != null && !itemIn.getExamName().equals("")) {
            condition += " AND EXAM_NAME LIKE '%?%'";
            lstParam.add(itemIn.getExamName());
        }
        if (itemIn.getExamAddress() != null && !itemIn.getExamAddress().equals("")) {
            condition += " AND EXAM_ADDRESS = ?";
            lstParam.add(itemIn.getExamAddress());
        }
        if (itemIn.getDuration() != -1) {
            condition += " AND DURATION = ?";
            lstParam.add(Integer.toString(itemIn.getDuration()));
        }
        if (itemIn.getMaxPoint() != -1) {
            condition += " AND MAX_POINT = ?";
            lstParam.add(Integer.toString(itemIn.getMaxPoint()));
        }
        if (itemIn.getExamPassTerm() != -1) {
            condition += " AND EXAM_PASS_TERM = ?";
            lstParam.add(Double.toString(itemIn.getExamPassTerm()));
        }
        if (itemIn.getExamRate() != -1) {
            condition += " AND EXAM_RATE = ?";
            lstParam.add(Double.toString(itemIn.getExamRate()));
        }
        if (itemIn.getIsDefault() != null && !itemIn.getIsDefault().equals("")) {
            condition += " AND IS_DEFAULT = ?";
            lstParam.add(itemIn.getIsDefault());
        }
        if (itemIn.getOrderType() != null && !itemIn.getOrderType().equals("")) {
            condition += " AND ORDER_TYPE LIKE '%?%'";
            lstParam.add(itemIn.getOrderType());
        }
        if (itemIn.getPriority() != -1) {
            condition += " AND PRIORITY = ?";
            lstParam.add(Integer.toString(itemIn.getPriority()));
        }
        if (itemIn.getOrd() != -1) {
            condition += " AND ORD = ?";
            lstParam.add(Integer.toString(itemIn.getOrd()));
        }
        if (itemIn.getStatus() != null && !itemIn.getStatus().equals("")) {
            condition += " AND STATUS = ?";
            lstParam.add(itemIn.getStatus());
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
                ExamTemplate item = new ExamTemplate();
                item.setExamId(rs.getLong("EXAM_ID"));
                item.setExamName(rs.getString("EXAM_NAME"));
                item.setExamCode(rs.getString("EXAM_CODE"));
                item.setExamAddress(rs.getString("EXAM_ADDRESS"));
                item.setDuration(rs.getInt("DURATION"));
                item.setMaxPoint(rs.getInt("MAX_POINT"));
                item.setExamPassTerm(rs.getDouble("EXAM_PASS_TERM"));
                item.setExamRate(rs.getDouble("EXAM_RATE"));
                item.setIsDefault(rs.getString("IS_DEFAULT"));
                item.setOrderType(rs.getString("ORDER_TYPE"));
                item.setPriority(rs.getInt("PRIORITY"));
                item.setOrd(rs.getInt("ORD"));
                item.setStatus(rs.getString("STATUS"));
                lstReturn.add(item);
            }
        } finally {
            close(rs);
            close(stmt);
            close(mConnection);
        }
        return lstReturn;
    }

    public ExamTemplate addTemplate(ExamTemplate item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql =
                "INSERT INTO `EXAM_TEMPLATE` "
                + "            (`EXAM_ID`, "
                + "             `EXAM_NAME`, "
                + "             `EXAM_CODE`, "
                + "             `EXAM_ADDRESS`, "
                + "             `DURATION`, "
                + "             `MAX_POINT`, "
                + "             `EXAM_PASS_TERM`, "
                + "             `EXAM_RATE`, "
                + "             `IS_DEFAULT`, "
                + "             `ORDER_TYPE`, "
                + "             `PRIORITY`, "
                + "             `ORD`, "
                + "             `STATUS`) "
                + "VALUES (?, "
                + "        ?, "
                + "        ?, "
                + "        ?, "
                + "        ?, "
                + "        ?, "
                + "        ?, "
                + "        ?, "
                + "        ?, "
                + "        ?, "
                + "        ?, "
                + "        ?, "
                + "        ?)";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            String template_id = SQLUtil.getSequenceValue(mConnection, "SEQ_EXAM_TEMPLATE");
            stmt.setLong(1, Integer.parseInt(template_id));
            stmt.setString(2, item.getExamName());
            stmt.setString(3, item.getExamCode());
            stmt.setString(4, item.getExamAddress());
            stmt.setInt(5, item.getDuration());
            stmt.setInt(6, item.getMaxPoint());
            stmt.setDouble(7, item.getExamPassTerm());
            stmt.setDouble(8, item.getExamRate());
            stmt.setString(9, item.getIsDefault());
            stmt.setString(10, item.getOrderType());
            stmt.setInt(11, item.getPriority());
            stmt.setInt(12, item.getOrd());
            stmt.setString(13, item.getStatus());
            stmt.execute();
            mConnection.commit();
            item.setExamId(Integer.parseInt(template_id));
            return item;
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

    public void updateTemplate(ExamTemplate item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql =
                "UPDATE `EXAM_TEMPLATE` "
                + "SET  "
                + "  `EXAM_NAME` = ?, "
                + "  `EXAM_CODE` = ?, "
                + "  `EXAM_ADDRESS` = ?, "
                + "  `DURATION` = ?, "
                + "  `MAX_POINT` = ?, "
                + "  `EXAM_PASS_TERM` = ?, "
                + "  `EXAM_RATE` = ?, "
                + "  `IS_DEFAULT` = ?, "
                + "  `ORDER_TYPE` = ?, "
                + "  `PRIORITY` = ?, "
                + "  `ORD` = ?, "
                + "  `STATUS` = ? "
                + "WHERE `EXAM_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setString(1, item.getExamName());
            stmt.setString(2, item.getExamCode());
            stmt.setString(3, item.getExamAddress());
            stmt.setInt(4, item.getDuration());
            stmt.setInt(5, item.getMaxPoint());
            stmt.setDouble(6, item.getExamPassTerm());
            stmt.setDouble(7, item.getExamRate());
            stmt.setString(8, item.getIsDefault());
            stmt.setString(9, item.getOrderType());
            stmt.setInt(10, item.getPriority());
            stmt.setInt(11, item.getOrd());
            stmt.setString(12, item.getStatus());
            stmt.setLong(13, item.getExamId());
            stmt.execute();
            mConnection.commit();
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

    public void deleteTemplates(ExamTemplate item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "DELETE FROM EXAM_TEMPLATE WHERE `EXAM_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setLong(1, item.getExamId());
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
