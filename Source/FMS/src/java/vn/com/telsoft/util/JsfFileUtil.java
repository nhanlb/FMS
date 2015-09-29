/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */ 
package vn.com.telsoft.util;

import com.faplib.applet.util.FileUtil;
import com.faplib.lib.Session;
import com.faplib.lib.SystemLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author ChienDX
 */
public class JsfFileUtil {

    public static String getRealPath(String strRelativePath) {
        ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return context.getRealPath(strRelativePath);
    }
    ////////////////////////////////////////////////////////////////////////    

    public static String uploadFile(UploadedFile file) throws Exception {
        try {
            //get sessionid
            String sessionId = Session.getSessionId() + "_" + System.currentTimeMillis();
            String strFileName = "/resources/tmp/" + sessionId + file.getFileName().substring(file.getFileName().lastIndexOf("."), file.getFileName().length());

            //Delete olf file
            FileUtil.deleteOldFile(getRealPath("/resources/tmp/"), "*", 1000 * 60 * 60 * 24);

            return uploadFile(file, strFileName.toLowerCase());

        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            throw ex;
        }
    }
    ////////////////////////////////////////////////////////////////////////

    public static String uploadFile(UploadedFile file, String strRelativeFilePath) throws Exception {
        return uploadFileAbsolute(file.getInputstream(), getRealPath(strRelativeFilePath));
    }
    ////////////////////////////////////////////////////////////////////////

    public static String uploadFile(InputStream is, String strRelativeFilePath) throws Exception {
        return uploadFileAbsolute(is, getRealPath(strRelativeFilePath));
    }
    ////////////////////////////////////////////////////////////////////////

    public static String uploadFileAbsolute(InputStream is, String strAbsoluteFilePath) throws Exception {
        String strFileName = "";
        OutputStream out = null;

        try {
            try {
                FileUtil.forceFolderExist(strAbsoluteFilePath.substring(0,
                        strAbsoluteFilePath.lastIndexOf("/") == -1 ? strAbsoluteFilePath.lastIndexOf("\\") : strAbsoluteFilePath.lastIndexOf("/")));

            } catch (Exception ex) {
                if (!ex.toString().contains("already exist")) {
                    throw new Exception(ex.toString());
                }
            }

            strFileName = strAbsoluteFilePath;

            out = new FileOutputStream(strFileName);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

        } catch (Exception ex) {
            throw new Exception(ex.toString());

        } finally {
            is.close();
            out.flush();
            out.close();
        }

        return strFileName;
    }
    ////////////////////////////////////////////////////////////////////////

    public static DefaultStreamedContent downloadFile(String strFileName, String strRelativeFilePath) throws Exception {
        return downloadFileAbsolute(strFileName, getRealPath(strRelativeFilePath));
    }
    ////////////////////////////////////////////////////////////////////////

    public static DefaultStreamedContent downloadFileAbsolute(String strFileName, String strAbsoluteFilePath) throws Exception {
        String strExtensions = (strAbsoluteFilePath.substring(strAbsoluteFilePath.lastIndexOf(".") + 1, strAbsoluteFilePath.length())).toLowerCase();

        String strType = "";
        if ("xls".equals(strExtensions) || "xlsx".equals(strExtensions)) {
            strType = "application/vnd.ms-excel";

        } else if ("doc".equals(strExtensions) || "docx".equals(strExtensions)) {
            strType = "application/msword";

        } else if ("ppt".equals(strExtensions) || "pptx".equals(strExtensions)) {
            strType = "application/msword";

        } else if ("pdf".equals(strExtensions)) {
            strType = "application/pdf";

        } else if ("txt".equals(strExtensions)) {
            strType = "text/plain";

        } else if ("csv".equals(strExtensions)) {
            strType = "text/csv";

        } else if ("mp4".equals(strExtensions)) {
            strType = "video/mp4";

        } else if ("avi".equals(strExtensions)) {
            strType = "video/avi";
        }

        InputStream stream = new FileInputStream(new File(strAbsoluteFilePath));
        return new DefaultStreamedContent(stream, strType, strFileName + "." + strExtensions);
    }
}
