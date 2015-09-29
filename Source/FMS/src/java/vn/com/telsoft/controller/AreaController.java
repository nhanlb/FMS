package vn.com.telsoft.controller;

import com.faplib.lib.ClientMessage;
import com.faplib.lib.SystemLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.faplib.lib.TSFuncTemplate;
import com.faplib.lib.util.DataUtil;
import com.faplib.lib.util.ResourceBundleUtil;
import com.faplib.util.StringUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import vn.com.telsoft.entity.Area;
import vn.com.telsoft.model.AreaModel;
import vn.com.telsoft.util.Utility;

/**
 *
 * @author HienTN
 */
@ManagedBean(name = "areaController")
@ViewScoped
public class AreaController extends TSFuncTemplate implements Serializable {

    /**
     * Creates a new instance of AreaController
     */
    private TreeNode mroot;
    private TreeNode mselectedNote;

    private Area area, areaBackup;
    private List<Area> lstArea;
    private List<SelectEvent> lstAreaType;
    private boolean isSelectedApp, isDisplay, isView;
    private String mstrAreaSearch;

    public AreaController() {
        try {

            Area rootValue = new Area();
            mroot = new DefaultTreeNode(rootValue, null);
            area = new Area();
            isDisplay = false;
            isView = true;
            lstAreaType = DataUtil.getData(Utility.class, "getApDomain", "AREA_TYPE");
            lstArea = DataUtil.getData(AreaModel.class, "getAllArea");
            if (!lstArea.isEmpty()) {
                areaBackup = new Area(lstArea.get(0));
                area = new Area(lstArea.get(0));
                isSelectedApp = true;
            } else {
                isSelectedApp = false;
                areaBackup = new Area();
            }

            buildTreeArea(mroot);
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ex.getMessage());
        }
    }

    public final void buildTreeArea(TreeNode parent) throws Exception {
        TreeNode node = null;
        for (Area temp : lstArea) {
            if (temp.getParentAreaId() == ((Area) parent.getData()).getAreaId()) {
                if (temp.getAreaId() == area.getAreaId()) {
                    mselectedNote = new DefaultTreeNode(temp, parent);
                    mselectedNote.setSelected(true);
                    mselectedNote.setExpanded(true);
                    buildTreeArea(mselectedNote);
                } else {
                    node = new DefaultTreeNode(temp, parent);
                    if (temp.getAreaId() == 1) {
                        node.setExpanded(true);
                    }
                    buildTreeArea(node);
                }
            }
        }
    }

    public void onTreeSelect(NodeSelectEvent evt) {
        try {

            isDisplay = false;
            isView = true;
            area = new Area((Area) mselectedNote.getData());
        } catch (Exception ex) {
            SystemLogger.getLogger().error(ex);
            ClientMessage.logErr(ClientMessage.MESSAGE_TYPE.ERR, ex.toString());
        }

    }

    public void onAreaSearchSelect(SelectEvent event) {
        String strAreaId = event.getObject().toString().substring(1, event.getObject().toString().indexOf(") "));

        for (Area tmpArea : lstArea) {
            String strId = String.valueOf(tmpArea.getAreaId());
            if (strId.equals(strAreaId)) {
                area = new Area(tmpArea);
                areaBackup = new Area(tmpArea);
                isEDIT = false;
                break;
            }
        }
        recusiveTree(mroot.getChildren());
    }

// ham get parentId de expanded va selected lai node
    private void recusiveTree(List<TreeNode> lstTreeNode) {
        for (TreeNode node : lstTreeNode) {
            node.setSelected(false);
            if (((Area) node.getData()).getAreaId() == area.getAreaId()) {
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

    public List<String> completeAreaSearch(String strQuery) {
        List<String> returnVal = new ArrayList<>();

        for (Area item : lstArea) {
            if (StringUtil.removeSign(item.getDisplayValue().toLowerCase()).contains(StringUtil.removeSign(strQuery).toLowerCase())) {
                returnVal.add("(" + item.getAreaId() + ") " + item.getDisplayValue().replaceAll("^-+", "").trim());
            }
        }

        return returnVal;
    }

    @Override
    public void changeStateAdd() {
        super.changeStateAdd();
        area = new Area();
        Area par = (Area) mselectedNote.getData();
        area.setParentAreaId(par.getAreaId());
        area.setAreaType(par.getAreaType() + 1);
        isDisplay = true;
        isView = false;
    }

    @Override
    public void changeStateCopy() {
        super.changeStateCopy();
        isCOPY = true;
        isDisplay = true;
        areaBackup = new Area(area);
        isView = false;
    }

    @Override
    public void changeStateEdit() {
        super.changeStateEdit();
        areaBackup = new Area(area);
        isDisplay = true;
        isView = false;
    }

    @Override
    public void changeStateDel() {
        super.changeStateDel();
    }

    @Override
    public void handOK() {
        try {

            AreaModel model = new AreaModel();
            if (isADD || isCOPY) {
                Area itemReturn = model.addArea(area);
                lstArea.add(itemReturn);
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "add_success"));
                area = new Area(itemReturn);

                //build lai tree
                mroot.getChildren().clear();
                buildTreeArea(mroot);

                //set selected va set expand cho node vua them
                recusiveTree(mroot.getChildren());
            } else if (isEDIT) {
                model.updateArea(area);
                int i = 0;
                for (Area rs : lstArea) {
                    if (rs.getAreaId() == area.getAreaId()) {
                        lstArea.set(i, area);
                    }
                    i++;
                }
                ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "update_success"));

                isEDIT = false;
                //build li tree
                mroot.getChildren().clear();
                buildTreeArea(mroot);
            }
            if (isCOPY) {
                areaBackup = null;
                isCOPY = false;
            }
            isDisplay = false;
            isView = true;

        } catch (Exception ex) {
            ex.printStackTrace();
            ClientMessage.logErr(ex.getMessage());
        }
    }

    @Override
    public void handDelete() {
        try {
            AreaModel model = new AreaModel();
            model.deleteArea(area);
            int i = 0;
            for (Area rs : lstArea) {
                if (rs.getAreaId() == area.getAreaId()) {
                    break;
                }
                i++;
            }
            lstArea.remove(i);
            if (lstArea.isEmpty()) {
                area = new Area();
                areaBackup = new Area();
            } else {
                area = new Area(lstArea.get(0));
                areaBackup = new Area(lstArea.get(0));
            }
            mroot.getChildren().clear();
            buildTreeArea(mroot);
            ClientMessage.log(ResourceBundleUtil.getAMObjectAsString("PP_SHARED", "del_success"));
        } catch (Exception ex) {
            Logger.getLogger(AreaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void handCancel() {
        if (isADD) {
            area = new Area((Area) mselectedNote.getData());
        } else if (isEDIT || isCOPY) {
            area = new Area(areaBackup);
        }
        isADD = false;
        isEDIT = false;
        isCOPY = false;
        isDISABLE = true;
        isDisplay = false;
        isView = true;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Area getAreaBackup() {
        return areaBackup;
    }

    public void setAreaBackup(Area areaBackup) {
        this.areaBackup = areaBackup;
    }

    public List<Area> getLstArea() {
        return lstArea;
    }

    public void setLstArea(List<Area> lstArea) {
        this.lstArea = lstArea;
    }

    public List<SelectEvent> getLstAreaType() {
        return lstAreaType;
    }

    public void setLstAreaType(List<SelectEvent> lstAreaType) {
        this.lstAreaType = lstAreaType;
    }

    public boolean isIsSelectedApp() {
        return isSelectedApp;
    }

    public void setIsSelectedApp(boolean isSelectedApp) {
        this.isSelectedApp = isSelectedApp;
    }

    public boolean isIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public boolean isIsView() {
        return isView;
    }

    public void setIsView(boolean isView) {
        this.isView = isView;
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

    public String getMstrAreaSearch() {
        return mstrAreaSearch;
    }

    public void setMstrAreaSearch(String mstrAreaSearch) {
        this.mstrAreaSearch = mstrAreaSearch;
    }

}
