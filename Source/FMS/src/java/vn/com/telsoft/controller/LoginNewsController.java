package vn.com.telsoft.controller;

import com.faplib.lib.ClientMessage;
import com.faplib.lib.SystemLogger;
import java.io.File;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.FileUtils;
import vn.com.telsoft.util.JsfFileUtil;

/**
 *
 * @author ChienDX
 */
@ManagedBean(name = "LoginNewsController")
@ViewScoped
public class LoginNewsController implements Serializable {

    private String mstrContent;
    private String mstrTemplate;
    private final String FILE_NAME = "news.xhtml";
    private final String FILE_NAME_TEMPLATE = "newstpl.xhtml";

    public LoginNewsController() {
        try {
            mstrContent = FileUtils.readFileToString(new File(JsfFileUtil.getRealPath(FILE_NAME)), "UTF8");
            mstrTemplate = FileUtils.readFileToString(new File(JsfFileUtil.getRealPath(FILE_NAME_TEMPLATE)), "UTF8");

            //Get clear content
            Pattern p;
            p = Pattern.compile("<ui:composition>\\s+(.+)\\s+<\\/ui:composition>");
            Matcher m = p.matcher(mstrContent);

            while (m.find()) {
                mstrContent = m.group(1);
            }

        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ClientMessage.MESSAGE_TYPE.ERR, ex.toString());
        }
    }
    ////////////////////////////////////////////////////////////////////////

    public void handSave() {
        try {
            String strNewXhtml = mstrTemplate.replace("#news_content#", mstrContent);
            FileUtils.writeStringToFile(new File(JsfFileUtil.getRealPath(FILE_NAME)), strNewXhtml, "UTF8");
            ClientMessage.logUpdate();

        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ClientMessage.MESSAGE_TYPE.ERR, ex.toString());
        }
    }
    ////////////////////////////////////////////////////////////////////////

    public String getMstrContent() {
        return mstrContent;
    }

    public void setMstrContent(String mstrContent) {
        this.mstrContent = mstrContent;
    }

}
