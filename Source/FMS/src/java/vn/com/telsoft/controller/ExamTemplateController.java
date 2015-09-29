package vn.com.telsoft.controller;

import com.faplib.lib.ClientMessage;
import com.faplib.lib.SystemLogger;
import com.faplib.lib.TSFuncTemplate;
import com.faplib.lib.util.DataUtil;
import com.faplib.lib.util.ResourceBundleUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import vn.com.telsoft.entity.ExamTemplate;
import vn.com.telsoft.model.ExamTemplateModel;

/**
 *
 * @author AnhTD
 */
@ManagedBean(name = "examTemplateController")
@ViewScoped
public class ExamTemplateController extends TSFuncTemplate implements Serializable {

    private ExamTemplate template, mselectedTemplate, templateBackup;
    private List<ExamTemplate> lstTemplates, lstTemplatesFiltered;
    private boolean isSelectedApp, isDisplayBtnConfirm;

    public ExamTemplateController() {
        try {
            template = new ExamTemplate();
            isDisplayBtnConfirm = false;
            lstTemplates = DataUtil.getData(ExamTemplateModel.class, "getAllTemplates");
            if (!lstTemplates.isEmpty()) {
                mselectedTemplate = new ExamTemplate(lstTemplates.get(0));
                template = new ExamTemplate(lstTemplates.get(0));
                isSelectedApp = true;
            } else {
                isSelectedApp = false;
                mselectedTemplate = new ExamTemplate();
            }
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }
    }

    public void onRowSelect(SelectEvent event) {
        template = new ExamTemplate(mselectedTemplate);
    }

    @Override
    public void changeStateAdd() {
        super.changeStateAdd();
        setIsDisplayBtnConfirm(true);
        template = new ExamTemplate();
    }

    @Override
    public void changeStateCopy() {
        super.changeStateCopy();
        isCOPY = true;
        isDisplayBtnConfirm = true;
        templateBackup = new ExamTemplate(template);
    }

    @Override
    public void changeStateEdit() {
        super.changeStateEdit();
        setIsDisplayBtnConfirm(true);
        templateBackup = new ExamTemplate(template);
    }

    @Override
    public void changeStateDel() {
        super.changeStateDel();
    }

    @Override
    public void handOK() {
        try {
            ExamTemplateModel model = new ExamTemplateModel();
            if (isADD || isCOPY) {
                ExamTemplate itemReturn = new ExamTemplate(model.addTemplate(template));
                lstTemplates.add(itemReturn);

                // Bo sung
                lstTemplatesFiltered.add(itemReturn);

                mselectedTemplate = new ExamTemplate(itemReturn);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "add_success"));
//                template = new ExamTemplate();
            } else if (isEDIT) {
                model.updateTemplate(template);
                int i = 0;
                for (ExamTemplate rs : lstTemplates) {
                    if (rs.getExamId() == template.getExamId()) {
                        lstTemplates.set(i, template);
                    }
                    i++;
                }

                // Bo sung filter
                if (lstTemplatesFiltered == null) {
                    lstTemplatesFiltered = new ArrayList<>(lstTemplates);
                }
                updateFilterValue(template);

                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "update_success"));
                isDisplayBtnConfirm = false;
                isEDIT = false;
                isDISABLE = true;
            }
            if (isCOPY) {
                templateBackup = null;
                isDisplayBtnConfirm = false;
                isCOPY = false;
            }

            // Bo sung doan nay
            if (isCOPY) {
                templateBackup = new ExamTemplate(mselectedTemplate);
                isDisplayBtnConfirm = false;
                isCOPY = false;
                isDISABLE = true;
            } else if (isADD) {
                template = new ExamTemplate();
                isSelectedApp = true;
            }

//            isDISABLE = true; // them vao day de DISABLE cac truong
        } catch (Exception ex) {
            ClientMessage.logErr(ex.getMessage());
        }
    }

    // Bo sung ham nay
    private void updateFilterValue(ExamTemplate item) {
        int i = 0;
        for (ExamTemplate pos : lstTemplatesFiltered) {
            if (item.getExamId() == pos.getExamId()) {
                break;
            }
            i++;
        }
        lstTemplatesFiltered.set(i, item);
    }

    @Override
    public void handDelete() {
        try {
            ExamTemplateModel model = new ExamTemplateModel();
            model.deleteTemplates(template);
            int i = 0;
            for (ExamTemplate rs : lstTemplates) {
                if (rs.getExamId() == template.getExamId()) {
                    break;
                }
                i++;
            }
            lstTemplates.remove(i);
            if (lstTemplates.isEmpty()) {
                template = new ExamTemplate();
                mselectedTemplate = new ExamTemplate();
            } else {
                template = new ExamTemplate(lstTemplates.get(0));
                mselectedTemplate = new ExamTemplate(lstTemplates.get(0));
            }
            ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "del_success"));
        } catch (Exception ex) {
            Logger.getLogger(ExamTemplateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handCancel() {
        if (isADD) {
            template = new ExamTemplate(mselectedTemplate);
        } else if (isEDIT || isCOPY) {
            template = new ExamTemplate(templateBackup);
        }
        isADD = false;
        isEDIT = false;
        isCOPY = false;
        isDisplayBtnConfirm = false;
        isDISABLE = true; // them vao day de DISABLE cac truong
    }

    /**
     * @return the isSelectedApp
     */
    public boolean isIsSelectedApp() {
        return isSelectedApp;
    }

    /**
     * @param isSelectedApp the isSelectedApp to set
     */
    public void setIsSelectedApp(boolean isSelectedApp) {
        this.isSelectedApp = isSelectedApp;
    }

    /**
     * @return the isDisplayBtnConfirm
     */
    public boolean isIsDisplayBtnConfirm() {
        return isDisplayBtnConfirm;
    }

    /**
     * @param isDisplayBtnConfirm the isDisplayBtnConfirm to set
     */
    public void setIsDisplayBtnConfirm(boolean isDisplayBtnConfirm) {
        this.isDisplayBtnConfirm = isDisplayBtnConfirm;
    }

    public ExamTemplate getTemplate() {
        return template;
    }

    public void setTemplate(ExamTemplate template) {
        this.template = template;
    }

    public List<ExamTemplate> getLstTemplates() {
        return lstTemplates;
    }

    public void setLstTemplates(List<ExamTemplate> lstTemplates) {
        this.lstTemplates = lstTemplates;
    }

    public ExamTemplate getMselectedTemplate() {
        return mselectedTemplate;
    }

    public void setMselectedTemplate(ExamTemplate mselectedTemplate) {
        this.mselectedTemplate = mselectedTemplate;
    }

    public ExamTemplate getTemplateBackup() {
        return templateBackup;
    }

    public void setTemplateBackup(ExamTemplate templateBackup) {
        this.templateBackup = templateBackup;
    }

    public List<ExamTemplate> getLstTemplatesFiltered() {
        return lstTemplatesFiltered;
    }

    public void setLstTemplatesFiltered(List<ExamTemplate> lstTemplatesFiltered) {
        this.lstTemplatesFiltered = lstTemplatesFiltered;
    }

}
