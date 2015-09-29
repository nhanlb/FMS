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
import vn.com.telsoft.entity.PolicyObject;
import vn.com.telsoft.model.PolicyObjectModel;
import vn.com.telsoft.util.Utility;

/**
 *
 * @author AnhTD
 */
@ManagedBean(name = "policyObjectController")
@ViewScoped
public class PolicyObjectController extends TSFuncTemplate implements Serializable {

    private PolicyObject policy, mselectedPolicy, policyBackup;
    private List<PolicyObject> lstPolicies, lstPolicyFiltered;
    private List<SelectEvent> lstPolicyObjectGroup;
    private boolean isSelectedApp, isDisplayBtnConfirm;

    public PolicyObjectController() {
        try {
            policy = new PolicyObject();
            isDisplayBtnConfirm = false;
            lstPolicyObjectGroup = DataUtil.getData(Utility.class, "getApDomain", "POLICY_OBJECT_GROUP");
            lstPolicies = DataUtil.getData(PolicyObjectModel.class, "getAllPolicies");
            if (!lstPolicies.isEmpty()) {
                mselectedPolicy = new PolicyObject(lstPolicies.get(0));
                policy = new PolicyObject(lstPolicies.get(0));
                isSelectedApp = true;
            } else {
                isSelectedApp = false;
                mselectedPolicy = new PolicyObject();
            }
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }
    }

    public void onRowSelect(SelectEvent event) {
        policy = new PolicyObject(mselectedPolicy);
    }

    @Override
    public void changeStateAdd() {
        super.changeStateAdd();
        setIsDisplayBtnConfirm(true);
        policy = new PolicyObject();
    }

    @Override
    public void changeStateCopy() {
        super.changeStateCopy();
        isCOPY = true;
        isDisplayBtnConfirm = true;
        policyBackup = new PolicyObject(policy);
    }

    @Override
    public void changeStateEdit() {
        super.changeStateEdit();
        setIsDisplayBtnConfirm(true);
        policyBackup = new PolicyObject(policy);
    }

    @Override
    public void changeStateDel() {
        super.changeStateDel();
    }

    @Override
    public void handOK() {
        try {
            PolicyObjectModel model = new PolicyObjectModel();
            if (isADD || isCOPY) {
                PolicyObject itemReturn = new PolicyObject(model.addPolicy(policy));
                lstPolicies.add(itemReturn);

                // Bo sung
                lstPolicyFiltered.add(itemReturn);

                mselectedPolicy = new PolicyObject(itemReturn);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "add_success"));
            } else if (isEDIT) {
                model.updatePolicy(policy);
                int i = 0;
                for (PolicyObject rs : lstPolicies) {
                    if (rs.getPolicyObjectID() == policy.getPolicyObjectID()) {
                        lstPolicies.set(i, policy);
                    }
                    i++;
                }

                // Bo sung filter
                if (lstPolicyFiltered == null) {
                    lstPolicyFiltered = new ArrayList<>(lstPolicies);
                }
                updateFilterValue(policy);

                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "update_success"));
                isDisplayBtnConfirm = false;
                isEDIT = false;
                isDISABLE = true;
            }

            // Bo sung doan nay
            if (isCOPY) {
                policyBackup = new PolicyObject(mselectedPolicy);
                isDisplayBtnConfirm = false;
                isCOPY = false;
                isDISABLE = true;
            } else if (isADD) {
                policy = new PolicyObject();
                isSelectedApp = true;
            }

//            isDISABLE = true; // them vao day de DISABLE cac truong
        } catch (Exception ex) {
            ClientMessage.logErr(ex.getMessage());
        }
    }

    // Bo sung ham nay
    private void updateFilterValue(PolicyObject item) {
        int i = 0;
        for (PolicyObject pos : lstPolicyFiltered) {
            if (item.getPolicyObjectID() == pos.getPolicyObjectID()) {
                break;
            }
            i++;
        }
        lstPolicyFiltered.set(i, item);
    }

    @Override
    public void handDelete() {
        try {
            PolicyObjectModel model = new PolicyObjectModel();
            model.deletePolicy(policy);
            int i = 0;
            for (PolicyObject rs : lstPolicies) {
                if (rs.getPolicyObjectID() == policy.getPolicyObjectID()) {
                    break;
                }
                i++;
            }
            lstPolicies.remove(i);
            if (lstPolicies.isEmpty()) {
                policy = new PolicyObject();
                mselectedPolicy = new PolicyObject();
            } else {
                policy = new PolicyObject(lstPolicies.get(0));
                mselectedPolicy = new PolicyObject(lstPolicies.get(0));
            }
            ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "del_success"));
        } catch (Exception ex) {
            Logger.getLogger(ScalesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handCancel() {
        if (isADD) {
            policy = new PolicyObject(mselectedPolicy);
        } else if (isEDIT || isCOPY) {
            policy = new PolicyObject(policyBackup);
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

    public PolicyObject getPolicy() {
        return policy;
    }

    public void setPolicy(PolicyObject policy) {
        this.policy = policy;
    }

    public PolicyObject getMselectedPolicy() {
        return mselectedPolicy;
    }

    public void setMselectedPolicy(PolicyObject mselectedPolicy) {
        this.mselectedPolicy = mselectedPolicy;
    }

    public PolicyObject getPolicyBackup() {
        return policyBackup;
    }

    public void setPolicyBackup(PolicyObject policyBackup) {
        this.policyBackup = policyBackup;
    }

    public List<PolicyObject> getLstPolicies() {
        return lstPolicies;
    }

    public void setLstPolicies(List<PolicyObject> lstPolicies) {
        this.lstPolicies = lstPolicies;
    }

    public List<PolicyObject> getLstPolicyFiltered() {
        return lstPolicyFiltered;
    }

    public void setLstPolicyFiltered(List<PolicyObject> lstPolicyFiltered) {
        this.lstPolicyFiltered = lstPolicyFiltered;
    }

    public List<SelectEvent> getLstPolicyObjectGroup() {
        return lstPolicyObjectGroup;
    }

    public void setLstPolicyObjectGroup(List<SelectEvent> lstPolicyObjectGroup) {
        this.lstPolicyObjectGroup = lstPolicyObjectGroup;
    }
}
