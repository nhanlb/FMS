package vn.com.telsoft.controller;

import com.faplib.lib.ClientMessage;
import com.faplib.lib.TSFuncTemplate;
import com.faplib.lib.TelsoftException;
import com.faplib.lib.util.DataUtil;
import com.faplib.lib.util.ResourceBundleUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import vn.com.telsoft.entity.Department;
import vn.com.telsoft.entity.Staff;
import vn.com.telsoft.model.DepartmentModel;
import vn.com.telsoft.model.StaffModel;
import vn.com.telsoft.util.Utility;

/**
 *
 * @author HaiDT
 */
@ManagedBean(name = "staffController")
@ViewScoped
public class StaffController extends TSFuncTemplate implements Serializable {

    private TreeNode mroot;
    private TreeNode mselectedNote;
    private List<Department> lstDepartment;
    private Department department;
    private List<SelectItem> cboCity, cboDistrict, cboWard, lstPosition;
    private boolean isDisplayBtnConfirm;
    private Staff mSelectedStaff, staff, staffBackup;
    private List<Staff> mlstStaff;

    public StaffController() {
        try {
            lstDepartment = DataUtil.getData(DepartmentModel.class, "getAllDepartment");
            lstPosition = DataUtil.getData(Utility.class, "getApDomain", "POSITION");
            Department rootValue = new Department();
            mroot = new DefaultTreeNode(rootValue, null);
            department = new Department();
            isDisplayBtnConfirm = false;
            staff = new Staff();
            if (!lstDepartment.isEmpty()) {
                department = new Department(lstDepartment.get(0));
                cboCity = DataUtil.getData(Utility.class, "getListSelectItemArea", "1", "1");
                cboDistrict = DataUtil.getData(Utility.class, "getListSelectItemArea", "2", String.valueOf(department.getCityId()));
                cboWard = DataUtil.getData(Utility.class, "getListSelectItemArea", "3", String.valueOf(department.getDistrictId()));
                mlstStaff = DataUtil.getData(StaffModel.class, "getStaffList", String.valueOf(department.getDeptId()));
                if (!mlstStaff.isEmpty()) {
                    staff = new Staff(mlstStaff.get(0));
                    mSelectedStaff = new Staff(staff);
                }
            }

            buildTreeDepartment(mroot);
        } catch (TelsoftException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadDistrict() {
        try {
            cboDistrict = DataUtil.getData(Utility.class, "getListSelectItemArea", "2", staff.getCityId());
        } catch (TelsoftException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadWard() {
        try {
            cboWard = DataUtil.getData(Utility.class, "getListSelectItemArea", "3", staff.getDistrictId());
        } catch (TelsoftException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
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

    public void onTreeNodeSelected(NodeSelectEvent event) {
        try {
            department = (Department) event.getTreeNode().getData();
            mlstStaff = DataUtil.getData(StaffModel.class, "getStaffList", String.valueOf(department.getDeptId()));
            if (!mlstStaff.isEmpty()) {
                staff = new Staff(mlstStaff.get(0));
                mSelectedStaff = new Staff(staff);
            }
        } catch (TelsoftException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void changeStateAdd() {
        try {
            isADD = true;
            isDisplayBtnConfirm = true;
            staff = new Staff();
            cboCity = DataUtil.getData(Utility.class, "getListSelectItemArea", "1", "1");
            staff.setDeptId(String.valueOf(department.getDeptId()));
        } catch (TelsoftException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void changeStateCopy() {
        try {
            staff = new Staff(mSelectedStaff);
            cboCity = DataUtil.getData(Utility.class, "getListSelectItemArea", "1", "1");
            cboDistrict = DataUtil.getData(Utility.class, "getListSelectItemArea", "2", staff.getCityId());
            cboWard = DataUtil.getData(Utility.class, "getListSelectItemArea", "3", staff.getDistrictId());
            isDisplayBtnConfirm = true;
            isCOPY = true;
        } catch (TelsoftException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void changeStateEdit() {
        try {
            isEDIT = true;
            cboCity = DataUtil.getData(Utility.class, "getListSelectItemArea", "1", "1");
            cboDistrict = DataUtil.getData(Utility.class, "getListSelectItemArea", "2", staff.getCityId());
            cboWard = DataUtil.getData(Utility.class, "getListSelectItemArea", "3", staff.getDistrictId());
            isDisplayBtnConfirm = true;
        } catch (TelsoftException ex) {
            Logger.getLogger(StaffController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    @Override
    public void changeStateDel() {
    }

    @Override
    public void handOK() {
        try {
            StaffModel model = new StaffModel();
            if (isADD || isCOPY) {
                Staff itemReturn = model.insertStaff(staff);
                mlstStaff.add(itemReturn);
                mSelectedStaff = new Staff(itemReturn);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "add_success"));
                staff = new Staff();
                staff.setDeptId(String.valueOf(department.getDeptId()));
            } else if (isEDIT) {
                model.updateStaff(staff);
                int i = 0;
                for (Staff st : mlstStaff) {
                    if (st.getStaffId().equals(staff.getStaffId())) {
                        mlstStaff.set(i, staff);
                        break;
                    }
                    i++;
                }
                mSelectedStaff = new Staff(staff);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "update_success"));
                isDisplayBtnConfirm = false;
                isEDIT = false;
                isDISABLE = true;
            }
            if (isCOPY) {
                isDisplayBtnConfirm = false;
                isCOPY = false;
                isDISABLE = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ClientMessage.logErr(ex.getMessage());
        }
    }

    @Override
    public void handDelete() {
        try {
            StaffModel model = new StaffModel();
            model.deleteStaff(staff);
            int i = 0;
            for (Staff st : mlstStaff) {
                if (st.getStaffId().equals(staff.getStaffId())) {
                    mlstStaff.remove(i);
                    break;
                }
                i++;
            }
            if (mlstStaff.isEmpty()) {
                staff = new Staff();
                mSelectedStaff = new Staff(staff);
            } else {
                staff = new Staff(mlstStaff.get(0));
                mSelectedStaff = new Staff(staff);
            }
            ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "del_success"));
        } catch (Exception ex) {
            Logger.getLogger(ReasonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handCancel() {
        mSelectedStaff = new Staff(staff);
        isADD = false;
        isEDIT = false;
        isCOPY = false;
        isDISABLE = true;
        isDisplayBtnConfirm = false;
    }

    /**
     * @return the mroot
     */
    public TreeNode getMroot() {
        return mroot;
    }

    /**
     * @param mroot the mroot to set
     */
    public void setMroot(TreeNode mroot) {
        this.mroot = mroot;
    }

    /**
     * @return the mselectedNote
     */
    public TreeNode getMselectedNote() {
        return mselectedNote;
    }

    /**
     * @param mselectedNote the mselectedNote to set
     */
    public void setMselectedNote(TreeNode mselectedNote) {
        this.mselectedNote = mselectedNote;
    }

    /**
     * @return the lstDepartment
     */
    public List<Department> getLstDepartment() {
        return lstDepartment;
    }

    /**
     * @param lstDepartment the lstDepartment to set
     */
    public void setLstDepartment(List<Department> lstDepartment) {
        this.lstDepartment = lstDepartment;
    }

    /**
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * @return the cboCity
     */
    public List<SelectItem> getCboCity() {
        return cboCity;
    }

    /**
     * @param cboCity the cboCity to set
     */
    public void setCboCity(List<SelectItem> cboCity) {
        this.cboCity = cboCity;
    }

    /**
     * @return the cboDistrict
     */
    public List<SelectItem> getCboDistrict() {
        return cboDistrict;
    }

    /**
     * @param cboDistrict the cboDistrict to set
     */
    public void setCboDistrict(List<SelectItem> cboDistrict) {
        this.cboDistrict = cboDistrict;
    }

    /**
     * @return the cboWard
     */
    public List<SelectItem> getCboWard() {
        return cboWard;
    }

    /**
     * @param cboWard the cboWard to set
     */
    public void setCboWard(List<SelectItem> cboWard) {
        this.cboWard = cboWard;
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

    /**
     * @return the mSelectedStaff
     */
    public Staff getmSelectedStaff() {
        return mSelectedStaff;
    }

    /**
     * @param mSelectedStaff the mSelectedStaff to set
     */
    public void setmSelectedStaff(Staff mSelectedStaff) {
        this.mSelectedStaff = mSelectedStaff;
    }

    /**
     * @return the staff
     */
    public Staff getStaff() {
        return staff;
    }

    /**
     * @param staff the staff to set
     */
    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    /**
     * @return the mlstStaff
     */
    public List<Staff> getMlstStaff() {
        return mlstStaff;
    }

    /**
     * @param mlstStaff the mlstStaff to set
     */
    public void setMlstStaff(List<Staff> mlstStaff) {
        this.mlstStaff = mlstStaff;
    }

    public List<SelectItem> getLstPosition() {
        return lstPosition;
    }

    public void setLstPosition(List<SelectItem> lstPosition) {
        this.lstPosition = lstPosition;
    }
}
