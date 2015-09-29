package vn.com.telsoft.model;

import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vn.com.telsoft.entity.Scales;

/**
 *
 * @author AnhTD
 */
public class ScalesModel extends AMDataPreprocessor implements Serializable {

    public List<Scales> getAllScales() throws Exception {
        List<Scales> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "SELECT `SCALE_ID`, `SCALE_CODE`, `SCALE_NAME`,"
                + " `SCALE_CATE`, `SNNB`, `STATUS` FROM `SCALES`";
        try {
            open();
            stmt = mConnection.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Scales item = new Scales();
                item.setScalesId(rs.getLong("SCALE_ID"));
                item.setScalesCode(rs.getString("SCALE_CODE"));
                item.setScalesName(rs.getString("SCALE_NAME"));
                item.setScalesCate(rs.getString("SCALE_CATE"));
                item.setSnnb(rs.getDouble("SNNB"));
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

    public List<Scales> searchScales(Scales itemIn) throws Exception {
        List<Scales> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "SELECT `SCALE_ID`, `SCALE_CODE`, `SCALE_NAME`,"
                + " `SCALE_CATE`, `SNNB`, `STATUS` FROM `SCALES` a WHERE 1=1 ";
        String condition = "";
        List<String> lstParam = new ArrayList<>();
        if (itemIn.getScalesCode() != null && !itemIn.getScalesCode().equals("")) {
            condition += " AND SCALE_CODE LIKE '%?%'";
            lstParam.add(itemIn.getScalesCode());
        }
        if (itemIn.getScalesCode() != null && !itemIn.getScalesCode().equals("")) {
            condition += " AND SCALE_NAME LIKE '%?%'";
            lstParam.add(itemIn.getScalesCode());
        }
        if (itemIn.getScalesCate() != null && !itemIn.getScalesCate().equals("")) {
            condition += " AND SCALE_CATE = ?";
            lstParam.add(itemIn.getScalesCode());
        }
        if (itemIn.getSnnb() != -1) {
            condition += " AND SNNB = ?";
            lstParam.add(Double.toString(itemIn.getSnnb()));
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
                Scales item = new Scales();
                item.setScalesId(rs.getLong("SCALE_ID"));
                item.setScalesCode(rs.getString("SCALE_CODE"));
                item.setScalesName(rs.getString("SCALE_NAME"));
                item.setScalesCate(rs.getString("SCALE_CATE"));
                item.setSnnb(rs.getInt("SNNB"));
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

    public Scales addScales(Scales item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql =
                "INSERT INTO `SCALES` "
                + "            (`SCALE_ID`, "
                + "             `SCALE_CODE`, "
                + "             `SCALE_NAME`, "
                + "             `SCALE_CATE`, "
                + "             `SNNB`, "
                + "             `STATUS`) "
                + "VALUES (?, "
                + "        ?, "
                + "        ?, "
                + "        ?, "
                + "        ?, "
                + "        ?);";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            String scales_id = SQLUtil.getSequenceValue(mConnection, "SEQ_SCALES");
            stmt.setLong(1, Integer.parseInt(scales_id));
            stmt.setString(2, item.getScalesCode());
            stmt.setString(3, item.getScalesName());
            stmt.setString(4, item.getScalesCate());
            stmt.setDouble(5, item.getSnnb());
            stmt.setString(6, item.getStatus());
            stmt.execute();
            mConnection.commit();
            item.setScalesId(Integer.parseInt(scales_id));
            return item;
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

    public void updateScales(Scales item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql =
                "UPDATE  "
                + "  `SCALES`  "
                + "SET "
                + "  `SCALE_CODE` = ?, "
                + "  `SCALE_NAME` = ?, "
                + "  `SCALE_CATE` = ?, "
                + "  `SNNB` = ?, "
                + "  `STATUS` = ? "
                + "WHERE `SCALE_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setString(1, item.getScalesCode());
            stmt.setString(2, item.getScalesName());
            stmt.setString(3, item.getScalesCate());
            stmt.setDouble(4, item.getSnnb());
            stmt.setString(5, item.getStatus());
            stmt.setLong(6, item.getScalesId());
            stmt.execute();
            mConnection.commit();
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

    public void deleteScales(Scales item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "DELETE FROM SCALES WHERE `SCALE_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setLong(1, item.getScalesId());
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
