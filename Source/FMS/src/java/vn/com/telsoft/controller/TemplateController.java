package vn.com.telsoft.controller;

import com.faplib.lib.TSFuncTemplate;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author HaiDT
 */
@ManagedBean(name = "MngAppsController")
@ViewScoped
public class TemplateController extends TSFuncTemplate implements Serializable {

    public void onRowSelect(SelectEvent event) {
    }

    @Override
    public void changeStateAdd() {
    }

    @Override
    public void changeStateCopy() {
    }

    @Override
    public void changeStateEdit() {
    }

    @Override
    public void handOK() {
    }

    @Override
    public void handDelete() {
    }

    @Override
    public void handCancel() {
    }
}
