package vn.com.telsoft.model;

import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.SQLUtil;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vn.com.telsoft.entity.PolicyObject;

/**
 *
 * @author AnhTD
 */
public class PolicyObjectModel extends AMDataPreprocessor implements Serializable {

    public List<PolicyObject> getAllPolicies() throws Exception {
        List<PolicyObject> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql =
                "SELECT "
                + "  `POLICY_OBJECT_ID`, "
                + "  `POLICY_OBJECT_NAME`, "
                + "  `POLICY_OBJECT_GROUP`, "
                + "  `DESCRIPTION`, "
                + "  `STATUS` "
                + "FROM `POLICY_OBJECT`";
        try {
            open();
            stmt = mConnection.prepareStatement(strSql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                PolicyObject item = new PolicyObject();
                item.setPolicyObjectID(rs.getLong("POLICY_OBJECT_ID"));
                item.setPolicyObjectName(rs.getString("POLICY_OBJECT_NAME"));
                item.setPolicyObjectGroup(rs.getString("POLICY_OBJECT_GROUP"));
                item.setDescription(rs.getString("DESCRIPTION"));
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

    public List<PolicyObject> searchPolicy(PolicyObject itemIn) throws Exception {
        List<PolicyObject> lstReturn = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "SELECT "
                + "  `POLICY_OBJECT_ID`, "
                + "  `POLICY_OBJECT_NAME`, "
                + "  `POLICY_OBJECT_GROUP`, "
                + "  `DESCRIPTION`, "
                + "  `STATUS` "
                + "FROM `POLICY_OBJECT` WHERE 1=1 ";
        String condition = "";
        List<String> lstParam = new ArrayList<>();
        if (itemIn.getPolicyObjectName() != null && !itemIn.getPolicyObjectName().equals("")) {
            condition += " AND POLICY_OBJECT_NAME LIKE '%?%'";
            lstParam.add(itemIn.getPolicyObjectName());
        }
        if (itemIn.getPolicyObjectGroup() != null && !itemIn.getPolicyObjectGroup().equals("")) {
            condition += " AND POLICY_OBJECT_GROUP LIKE '%?%'";
            lstParam.add(itemIn.getPolicyObjectGroup());
        }
        if (itemIn.getDescription() != null && !itemIn.getDescription().equals("")) {
            condition += " AND DESCRIPTION LIKE '%?%'";
            lstParam.add(itemIn.getDescription());
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
                PolicyObject item = new PolicyObject();
                item.setPolicyObjectID(rs.getLong("POLICY_OBJECT_ID"));
                item.setPolicyObjectName(rs.getString("POLICY_OBJECT_NAME"));
                item.setPolicyObjectGroup(rs.getString("POLICY_OBJECT_GROUP"));
                item.setDescription(rs.getString("DESCRIPTION"));
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

    public PolicyObject addPolicy(PolicyObject item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql =
                "INSERT INTO `POLICY_OBJECT` "
                + "            (`POLICY_OBJECT_ID`, "
                + "             `POLICY_OBJECT_NAME`, "
                + "             `POLICY_OBJECT_GROUP`, "
                + "             `DESCRIPTION`, "
                + "             `STATUS`) "
                + "VALUES (?, "
                + "        ?, "
                + "        ?, "
                + "        ?, "
                + "        ?)";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            String policy_id = SQLUtil.getSequenceValue(mConnection, "SEQ_POLICY_OBJECT");
            stmt.setLong(1, Integer.parseInt(policy_id));
            stmt.setString(2, item.getPolicyObjectName());
            stmt.setString(3, item.getPolicyObjectGroup());
            stmt.setString(4, item.getDescription());
            stmt.setString(5, item.getStatus());
            stmt.execute();
            mConnection.commit();
            item.setPolicyObjectID(Integer.parseInt(policy_id));
            return item;
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

    public void updatePolicy(PolicyObject item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql =
                "UPDATE  "
                + "  `POLICY_OBJECT`  "
                + "SET "
                + "  `POLICY_OBJECT_NAME` = ?, "
                + "  `POLICY_OBJECT_GROUP` = ?, "
                + "  `DESCRIPTION` = ?, "
                + "  `STATUS` = ? "
                + "WHERE `POLICY_OBJECT_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setString(1, item.getPolicyObjectName());
            stmt.setString(2, item.getPolicyObjectGroup());
            stmt.setString(3, item.getDescription());
            stmt.setString(4, item.getStatus());
            stmt.setLong(5, item.getPolicyObjectID());
            stmt.execute();
            mConnection.commit();
        } finally {
            mConnection.setAutoCommit(true);
            close(rs);
            close(stmt);
            close(mConnection);
        }
    }

    public void deletePolicy(PolicyObject item) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String strSql = "DELETE FROM POLICY_OBJECT WHERE `POLICY_OBJECT_ID` = ?";
        try {
            open();
            mConnection.setAutoCommit(false);
            stmt = mConnection.prepareStatement(strSql);
            stmt.setLong(1, item.getPolicyObjectID());
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
