package application.model.viewmodel.admin;

import application.model.viewmodel.common.ChartVM;
import application.model.viewmodel.common.LayoutHeaderAdminVM;

public class HomeAdminVM {

    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private ChartVM countProductByCategory;
    private ChartVM sumProductOrderByCategory;

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public ChartVM getCountProductByCategory() {
        return countProductByCategory;
    }

    public void setCountProductByCategory(ChartVM countProductByCategory) {
        this.countProductByCategory = countProductByCategory;
    }

    public ChartVM getSumProductOrderByCategory() {
        return sumProductOrderByCategory;
    }

    public void setSumProductOrderByCategory(ChartVM sumProductOrderByCategory) {
        this.sumProductOrderByCategory = sumProductOrderByCategory;
    }
}
