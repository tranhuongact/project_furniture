package application.model.viewmodel.admin;

import application.model.viewmodel.common.BrandVM;
import application.model.viewmodel.common.LayoutHeaderAdminVM;

import java.util.List;

public class AdminBrandVM {
    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<BrandVM> brandVMList;

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public List<BrandVM> getBrandVMList() {
        return brandVMList;
    }

    public void setBrandVMList(List<BrandVM> brandVMList) {
        this.brandVMList = brandVMList;
    }

}
