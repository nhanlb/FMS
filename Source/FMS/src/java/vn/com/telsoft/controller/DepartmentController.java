package vn.com.telsoft.controller;

import com.faplib.lib.ClientMessage;
import com.faplib.lib.SystemLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.faplib.lib.util.DataUtil;
import com.faplib.lib.util.ResourceBundleUtil;
import com.faplib.util.StringUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import vn.com.telsoft.entity.Department;
import vn.com.telsoft.model.DepartmentModel;
import vn.com.telsoft.util.Utility;

/**
 *
 * @author HienTN
 */
@ManagedBean(name = "departmentController")
@ViewScoped
public class DepartmentController implements Serializable {

    private TreeNode mroot;
    private TreeNode mselectedNote;
    private Department department, departmentBackup;
    private List<Department> lstDepartment;
    private List<SelectEvent> lstDepartmentType;
    private boolean isSelectedApp, isDisplayBtnConfirm, isView;
    private List<SelectItem> cboStaff = new ArrayList<>();
    private List<SelectItem> cboCity, cboCityBackup = new ArrayList<>();
    private List<SelectItem> cboDistrict, cboDistrictBackup = new ArrayList<>();
    private List<SelectItem> cboWard, cboWardBackup = new ArrayList<>();
    private PersistAction mflag;
    private String mstrDepartmentSearch;

    public static enum PersistAction {

        SELECT,
        CREATE,
        DELETE,
        UPDATE,
        COPY
    }

    /**
     * Creates a new instance of DepartmentController
     */
    public DepartmentController() {
        try {

            lstDepartment = DataUtil.getData(DepartmentModel.class, "getAllDepartment");
            Department rootValue = new Department();
//            rootValue = new Department(lstDepartment.get(0));
            mroot = new DefaultTreeNode(rootValue, null);
            department = new Department();

            isDisplayBtnConfirm = false;
            isView = true;
            lstDepartmentType = DataUtil.getData(Utility.class, "getApDomain", "DEPT_TYPE");

            if (!lstDepartment.isEmpty()) {
                department = new Department(lstDepartment.get(0));

                departmentBackup = new Department(lstDepartment.get(0));
                isSelectedApp = true;
                cboStaff = DataUtil.getData(DepartmentModel.class, "getStaff");
                cboCity = DataUtil.getData(DepartmentModel.class, "getCity");
                cboCityBackup = new ArrayList<>(cboCity);

                cboDistrict = DataUtil.getData(DepartmentModel.class, "getDistrict", department.getCityId());
                cboDistrictBackup = new ArrayList<>(cboDistrict);

                cboWard = DataUtil.getData(DepartmentModel.class, "getWard", department.getDistrictId());
                cboWardBackup = new ArrayList<>(cboWard);
            } else {
                isSelectedApp = false;
                departmentBackup = new Department();
            }

            buildTreeDepartment(mroot);

            mflag = PersistAction.SELECT;
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }
    }

    public final void buildTreeDepartment(TreeNode parent) throws Exception {
        TreeNode node = null;
        for (Department temp : lstDepartment) {
            if (temp.getParentId() == ((Department) parent.getData()).getDeptId()) {
                if (temp.getDeptId() == department.getDeptId()) {
                    mselectedNote = new DefaultTreeNode(temp, parent);
                    mselectedNote.setSelected(true);
                    mselectedNote.setExpanded(true);
                    buildTreeDepartment(mselectedNote);
                } else {
                    node = new DefaultTreeNode(temp, parent);
                    if (temp.getLevel() <= 2) {
                        node.setExpanded(true);
                    }
                    buildTreeDepartment(node);
                }
            }
        }
    }

    public void onTreeSelect(NodeSelectEvent evt) {
        try {
            mflag = PersistAction.SELECT;
            isDisplayBtnConfirm = false;
            isView = true;
            department = new Department((Department) mselectedNote.getData());

            cboCity = DataUtil.getData(DepartmentModel.class, "getCity");
            cboCityBackup = new ArrayList<>(cboCity);

            cboDistrict = DataUtil.getData(DepartmentModel.class, "getDistrict", department.getCityId());
            cboDistrictBackup = new ArrayList<>(cboDistrict);

            cboWard = DataUtil.getData(DepartmentModel.class, "getWard", department.getDistrictId());
            cboWardBackup = new ArrayList<>(cboWard);

            int t = 0;
            for (int i = 0; i < lstDepartment.size(); i++) {
                Department dept = (Department) lstDepartment.get(i);
                if (department.getDeptId() == dept.getParentId()) {
                    t++;
                    break;
                }
            }
            if (t > 0) {
                lstDepartmentType = DataUtil.getData(DepartmentModel.class, "getApDomain", "DEPT_TYPE");
            } else {
                lstDepartmentType = DataUtil.getData(Utility.class, "getApDomain", "DEPT_TYPE");
            }
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ClientMessage.MESSAGE_TYPE.ERR, ex.toString());
        }

    }

    public void onDepartmentSearchSelect(SelectEvent event) {
        try {
            String strDepartmentId = event.getObject().toString().substring(1, event.getObject().toString().indexOf(") "));

            for (Department tmpDepartment : lstDepartment) {
                String strId = String.valueOf(tmpDepartment.getDeptId());
                if (strId.equals(strDepartmentId)) {
                    department = new Department(tmpDepartment);
                    departmentBackup = new Department(tmpDepartment);

                    cboCity = DataUtil.getData(DepartmentModel.class, "getCity");
                    cboCityBackup = new ArrayList<>(cboCity);

                    cboDistrict = DataUtil.getData(DepartmentModel.class, "getDistrict", department.getCityId());
                    cboDistrictBackup = new ArrayList<>(cboDistrict);

                    cboWard = DataUtil.getData(DepartmentModel.class, "getWard", department.getDistrictId());
                    cboWardBackup = new ArrayList<>(cboWard);

                    int t = 0;
                    for (int i = 0; i < lstDepartment.size(); i++) {
                        Department dept = (Department) lstDepartment.get(i);
                        if (department.getDeptId() == dept.getParentId()) {
                            t++;
                            break;
                        }
                    }
                    if (t > 0) {
                        lstDepartmentType = DataUtil.getData(DepartmentModel.class, "getApDomain", "DEPT_TYPE");
                    } else {
                        lstDepartmentType = DataUtil.getData(Utility.class, "getApDomain", "DEPT_TYPE");
                    }
                    mflag = PersistAction.SELECT;
                    break;
                }
            }
            recusiveTree(mroot.getChildren());
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }
    }

    // ham get parentId de expanded va selected lai node
    private void recusiveTree(List<TreeNode> lstTreeNode) {
        for (TreeNode node : lstTreeNode) {
            node.setSelected(false);
            if (((Department) node.getData()).getDeptId() == department.getDeptId()) {
                node.setSelected(true);
                mselectedNote = node;
                expandedParend(node);
            }
            recusiveTree(node.getChildren());
        }
    }

    //ham expand
    private void expandedParend(TreeNode node) {
        if (node.getParent() != null) {
            node.getParent().setExpanded(true);
            expandedParend(node.getParent());
        }
    }

    public List<String> completeDepartmentSearch(String strQuery) {
        List<String> returnVal = new ArrayList<>();

        for (Department item : lstDepartment) {
            if (StringUtil.removeSign(item.getName().toLowerCase()).contains(StringUtil.removeSign(strQuery).toLowerCase())) {
                returnVal.add("(" + item.getDeptId() + ") " + item.getName().replaceAll("^-+", "").trim());
            }
        }

        return returnVal;
    }

    public void addDepartment() {
        try {
            mflag = PersistAction.CREATE;
            department = new Department();
            //add new
            Department par = (Department) mselectedNote.getData();
            department.setParentId(par.getDeptId());
            department.setLevel(par.getLevel() + 1);
            lstDepartmentType = DataUtil.getData(Utility.class, "getApDomain", "DEPT_TYPE");
            isDisplayBtnConfirm = true;
            isView = false;
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ClientMessage.MESSAGE_TYPE.ERR, ex.toString());
        }
    }

    public void editDepartment() throws Exception {
        try {
            mflag = PersistAction.UPDATE;
            isDisplayBtnConfirm = true;
            isView = false;
            this.departmentBackup = new Department(department);
            int t = 0;
            for (int i = 0; i < lstDepartment.size(); i++) {
                Department dept = (Department) lstDepartment.get(i);
                if (department.getDeptId() == dept.getParentId()) {
                    t++;
                    break;
                }
            }
            if (t > 0) {
                lstDepartmentType = DataUtil.getData(DepartmentModel.class, "getApDomain", "DEPT_TYPE");
            } else {
                lstDepartmentType = DataUtil.getData(Utility.class, "getApDomain", "DEPT_TYPE");
            }
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ClientMessage.MESSAGE_TYPE.ERR, ex.toString());
        }

    }

    public void copyDepartment() throws Exception {
        mflag = PersistAction.COPY;
        isDisplayBtnConfirm = true;
        isView = false;
        this.departmentBackup = new Department(department);
        lstDepartmentType = DataUtil.getData(Utility.class, "getApDomain", "DEPT_TYPE");

    }

    //lay quan huyen theo tinh thanh pho
    public void changCity() {
        try {
            if (department.getCityId() != 0) {
                cboDistrict = DataUtil.getData(DepartmentModel.class, "getDistrict", department.getCityId());
                if (department.getDistrictId() != 0) {
                    cboWard = DataUtil.getData(DepartmentModel.class, "getWard", department.getDistrictId());
                } else {
                    cboWard = new ArrayList<SelectItem>(Arrays.asList(new SelectItem("", "")));
                }
            } else {
                cboDistrict = new ArrayList<SelectItem>(Arrays.asList(new SelectItem("", "")));
                cboWard = new ArrayList<SelectItem>(Arrays.asList(new SelectItem("", "")));
            }

        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }

    }

    //lay xa phuong theo quan huyen
    public void changDistrict() {
        try {
            if (department.getDistrictId() != 0) {
                cboWard = DataUtil.getData(DepartmentModel.class, "getWard", department.getDistrictId());
            } else {
                cboWard = new ArrayList<SelectItem>(Arrays.asList(new SelectItem("", "")));
            }

        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }

    }

    public void preDeleteDepartment() {
        mflag = PersistAction.DELETE;
    }

    public void deleteDepartment() {
        try {
            DepartmentModel model = new DepartmentModel();
            model.deleteDepartment(department);
            int i = 0;
            for (Department rs : lstDepartment) {
                if (rs.getDeptId() == department.getDeptId()) {
                    break;
                }
                i++;
            }
            lstDepartment.remove(i);
            if (lstDepartment.isEmpty()) {
                department = new Department();
                departmentBackup = new Department();
            } else {
                department = new Department(lstDepartment.get(0));
                departmentBackup = new Department(lstDepartment.get(0));
                cboCity = DataUtil.getData(DepartmentModel.class, "getCity");

                cboCityBackup = new ArrayList<>(cboCity);

                cboDistrict = DataUtil.getData(DepartmentModel.class, "getDistrict", department.getCityId());
                cboDistrictBackup = new ArrayList<>(cboDistrict);

                cboWard = DataUtil.getData(DepartmentModel.class, "getWard", department.getDistrictId());
                cboWardBackup = new ArrayList<>(cboWard);
            }
            mroot.getChildren().clear();
            buildTreeDepartment(mroot);
            ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "del_success"));
        } catch (Exception ex) {
            Logger.getLogger(DepartmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveDepartment() {

        try {

            DepartmentModel model = new DepartmentModel();
            if (mflag == PersistAction.CREATE || mflag == PersistAction.COPY) {
                Department itemReturn = model.addDepartment(department);
                lstDepartment.add(itemReturn);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "add_success"));
                department = new Department(itemReturn);
            } else if (mflag == PersistAction.UPDATE) {
                model.updateDepartment(department);
                int i = 0;
                for (Department rs : lstDepartment) {
                    if (rs.getDeptId() == department.getDeptId()) {
                        lstDepartment.set(i, department);
                        departmentBackup = new Department(department);
                    }
                    i++;
                }
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "update_success"));

                mflag = PersistAction.SELECT;
            }
            if (mflag == PersistAction.COPY) {
                departmentBackup = null;
                mflag = PersistAction.SELECT;
            }

            isDisplayBtnConfirm = false;
            cboCityBackup = new ArrayList<>(cboCity);
            cboDistrictBackup = new ArrayList<>(cboDistrict);
            cboWardBackup = new ArrayList<>(cboWard);

            mroot.getChildren().clear();
            buildTreeDepartment(mroot);
            isView = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            ClientMessage.logErr(ex.getMessage());
        }
    }

    public void backDepartment() throws Exception {
        if (mflag == PersistAction.CREATE) {
            department = new Department((Department) mselectedNote.getData());
        } else if (mflag == PersistAction.UPDATE || mflag == PersistAction.COPY) {
            department = new Department(departmentBackup);
        }
        cboCity = new ArrayList<>(cboCityBackup);
        cboDistrict = new ArrayList<>(cboDistrictBackup);
        cboWard = new ArrayList<>(cboWardBackup);
        isDisplayBtnConfirm = false;
        isView = true;
        mflag = PersistAction.SELECT;

    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getDepartmentBackup() {
        return departmentBackup;
    }

    public void setDepartmentBackup(Department departmentBackup) {
        this.departmentBackup = departmentBackup;
    }

    public List<Department> getLstDepartment() {
        return lstDepartment;
    }

    public void setLstDepartment(List<Department> lstDepartment) {
        this.lstDepartment = lstDepartment;
    }

    public List<SelectEvent> getLstDepartmentType() {
        return lstDepartmentType;
    }

    public void setLstDepartmentType(List<SelectEvent> lstDepartmentType) {
        this.lstDepartmentType = lstDepartmentType;
    }

    public boolean isIsSelectedApp() {
        return isSelectedApp;
    }

    public void setIsSelectedApp(boolean isSelectedApp) {
        this.isSelectedApp = isSelectedApp;
    }

    public boolean isIsDisplayBtnConfirm() {
        return isDisplayBtnConfirm;
    }

    public void setIsDisplayBtnConfirm(boolean isDisplayBtnConfirm) {
        this.isDisplayBtnConfirm = isDisplayBtnConfirm;
    }

    public PersistAction getMflag() {
        return mflag;
    }

    public void setMflag(PersistAction mflag) {
        this.mflag = mflag;
    }

    public TreeNode getMroot() {
        return mroot;
    }

    public void setMroot(TreeNode mroot) {
        this.mroot = mroot;
    }

    public TreeNode getMselectedNote() {
        return mselectedNote;
    }

    public void setMselectedNote(TreeNode mselectedNote) {
        this.mselectedNote = mselectedNote;
    }

    public List<SelectItem> getCboStaff() {
        return cboStaff;
    }

    public void setCboStaff(List<SelectItem> cboStaff) {
        this.cboStaff = cboStaff;
    }

    public boolean isIsView() {
        return isView;
    }

    public void setIsView(boolean isView) {
        this.isView = isView;
    }

    public List<SelectItem> getCboCity() {
        return cboCity;
    }

    public void setCboCity(List<SelectItem> cboCity) {
        this.cboCity = cboCity;
    }

    public List<SelectItem> getCboDistrict() {
        return cboDistrict;
    }

    public void setCboDistrict(List<SelectItem> cboDistrict) {
        this.cboDistrict = cboDistrict;
    }

    public List<SelectItem> getCboWard() {
        return cboWard;
    }

    public void setCboWard(List<SelectItem> cboWard) {
        this.cboWard = cboWard;
    }

    public List<SelectItem> getCboCityBackup() {
        return cboCityBackup;
    }

    public void setCboCityBackup(List<SelectItem> cboCityBackup) {
        this.cboCityBackup = cboCityBackup;
    }

    public List<SelectItem> getCboDistrictBackup() {
        return cboDistrictBackup;
    }

    public void setCboDistrictBackup(List<SelectItem> cboDistrictBackup) {
        this.cboDistrictBackup = cboDistrictBackup;
    }

    public List<SelectItem> getCboWardBackup() {
        return cboWardBackup;
    }

    public void setCboWardBackup(List<SelectItem> cboWardBackup) {
        this.cboWardBackup = cboWardBackup;
    }

    public String getMstrDepartmentSearch() {
        return mstrDepartmentSearch;
    }

    public void setMstrDepartmentSearch(String mstrDepartmentSearch) {
        this.mstrDepartmentSearch = mstrDepartmentSearch;
    }
}
