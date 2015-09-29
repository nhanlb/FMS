package vn.com.telsoft.util;

import com.faplib.applet.util.FileUtil;
import com.faplib.lib.SystemLogger;
import com.faplib.lib.admin.data.AMDataPreprocessor;
import com.faplib.lib.util.ResourceBundleUtil;
import com.faplib.lib.util.SQLUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import vn.com.telsoft.entity.Document;

/**
 *
 * @author HaiDT
 */
public class Utility extends AMDataPreprocessor implements Serializable {

    static Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private String typeAttachfile = "docx|doc|xls|xlsx|pdf|jpg|png|bmp|gif|ppt|pptx|tif|tiff";

    public List<SelectItem> getApDomain(String strType) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<SelectItem> lstReturn = new ArrayList<>();
        String strSQL = "";
        try {
            open();
            strSQL = "SELECT a.ap_domain_id, a.type, a.`code`, a.`value`"
                    + " FROM AP_DOMAIN_REF a"
                    + "    WHERE a.type = (SELECT `code` FROM AP_DOMAIN_REF WHERE `value` = ?)";
            stmt = mConnection.prepareStatement(strSQL);

            stmt.setString(1, strType.toUpperCase());
            rs = stmt.executeQuery();
            while (rs.next()) {
                SelectItem item = new SelectItem();
                item.setValue(rs.getString("code"));
                item.setLabel(rs.getString("value"));
                lstReturn.add(item);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            close(rs);
            close(stmt);
            close();
        }
        return lstReturn;
    }

    public List<SelectItem> getListSelectItemArea(String areaType, String parentId) throws Exception {
        List<SelectItem> cboArea = new ArrayList();

        try {
            open();
            String strSQL = "SELECT a.`AREA_ID`, a.`DISPLAY_VALUE` "
                    + " FROM AREA_REF a "
                    + " INNER JOIN AREA_KEY b ON a.area_id = b.area_id "
                    + " WHERE a.`AREA_TYPE` = ? AND b.`PARENT_AREA_ID` = ? AND a.`STATUS` = 1 ORDER BY a.`DISPLAY_VALUE`";

            mStmt = mConnection.prepareStatement(strSQL);
            mStmt.setString(1, areaType);
            mStmt.setString(2, parentId);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                SelectItem item = new SelectItem();
                item.setLabel(mRs.getString("DISPLAY_VALUE"));
                item.setValue(mRs.getString("AREA_ID"));
                cboArea.add(item);
            }
        } finally {
            close(mConnection, mStmt, mRs);
            close();
        }
        return cboArea;
    }

    public Document writeFile(UploadedFile fileTmp, String table_name) {
        try {
            InputStream inputStream;
            Document document = new Document();
            File fileUp;

            if (fileTmp != null) {
                ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String direct = ctx.getRealPath("../documents/" + table_name);
                File targetFolder = new File(direct);
                if (!targetFolder.exists()) {
                    FileUtil.forceFolderExist(direct);
                }
                inputStream = fileTmp.getInputstream();
                String nameFiletmp = FilenameUtils.getName(fileTmp.getFileName());
                String docSeq = SQLUtil.getSequenceValue(mConnection, "SEQ_DOCUMENT");
                String nameFile = docSeq + "_" + nameFiletmp;
                fileUp = new File(targetFolder, nameFile);
                document.setDocId(Integer.parseInt(docSeq));
                document.setFileName(fileUp.getName());
//                document.setDoc_path("/u01/app/apache-tomcat-7.0.37/webapps/documents/" + prjID + "/" + fileUp.getName());
                document.setFilePath(fileUp.getCanonicalPath());
                document.setFileSize(String.valueOf(fileTmp.getSize()));
                SimpleDateFormat dateformatMMDDYYYY = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dateUpload = new String(dateformatMMDDYYYY.format(new Date()));
                document.setDateUpload(convertStringToDate(dateUpload, "dd/MM/yyyy HH:mm:ss"));
                OutputStream out = new FileOutputStream(fileUp);
                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = inputStream.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
                return document;
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundleUtil.getAMObjectAsString("PP_DOCUMENTS", "msg_path_req"), ""));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            SystemLogger.getLogger().error(e);
            return null;
        }

    }

    public void download(String fileName, String table_name) {
        OutputStream output = null;
        try {
            String curent_path = new File("").getAbsolutePath();
            String webapp_path = new File(curent_path).getParent();
            String document_path = webapp_path + "/webapps/documents/" + table_name;
            String file_path = document_path + "/" + fileName;
            System.out.println("document_path: " + file_path);
            File f = new File(file_path);
            System.out.println("real: " + f.getAbsolutePath());

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            ec.responseReset();
            ec.setResponseContentType("application/soap+xml; charset=utf-8");
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + file_path + "\"");
            output = ec.getResponseOutputStream();
            fc.responseComplete();

        } catch (IOException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean checkFileExist(String dir_path, String file_name) {
        File folder = new File(dir_path);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null || listOfFiles.length == 0) {
            return false;
        }
        for (File file : listOfFiles) {
            if (file.isFile()) {
                if (file.getName().equals(file_name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkTypeFile(String fileName) {
        String nameType = fileName.trim().substring(fileName.lastIndexOf(".") + 1);
        if (typeAttachfile.contains(nameType)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundleUtil.getAMObjectAsString("PP_DOCUMENTS", "msg_add_file"), ""));
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ResourceBundleUtil.getAMObjectAsString("PP_DOCUMENTS", "msg_format"), ""));
            return false;
        }
    }

    public static Date convertStringToDate(String dateStr, String format) {
        SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat(format);
        try {
            return mySimpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String converDatetoString(Date dtDate) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String strValue = "";
        if (dtDate != null) {
            strValue = format.format(dtDate);
            try {
                Date checkDate = (Date) format.parse(strValue);
            } catch (Exception e) {
                throw new ParseException(e.getMessage(), 0);
            }
        }
        return strValue;
    }
}
