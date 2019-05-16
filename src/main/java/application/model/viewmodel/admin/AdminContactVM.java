package application.model.viewmodel.admin;

import application.model.viewmodel.common.LayoutHeaderAdminVM;
import application.model.viewmodel.contact.ContactVM;

import java.util.List;

public class AdminContactVM {
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<ContactVM> contactVMList;

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public List<ContactVM> getContactVMList() {
        return contactVMList;
    }

    public void setContactVMList(List<ContactVM> contactVMList) {
        this.contactVMList = contactVMList;
    }
}
