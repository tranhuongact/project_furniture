package application.model.viewmodel.admin;

import application.model.viewmodel.common.LayoutHeaderAdminVM;
import application.model.viewmodel.order.OrderHistoryVM;
import application.model.viewmodel.order.OrderProductVM;
import application.model.viewmodel.order.OrderVM;

import java.util.List;

public class AdminOrderVM {

    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<OrderHistoryVM> orderHistoryVMList;
    private List<OrderProductVM> orderProductVMList;
    private List<OrderVM> orderVMList;

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public List<OrderHistoryVM> getOrderHistoryVMList() {
        return orderHistoryVMList;
    }

    public void setOrderHistoryVMList(List<OrderHistoryVM> orderHistoryVMList) {
        this.orderHistoryVMList = orderHistoryVMList;
    }

    public List<OrderProductVM> getOrderProductVMList() {
        return orderProductVMList;
    }

    public void setOrderProductVMList(List<OrderProductVM> orderProductVMList) {
        this.orderProductVMList = orderProductVMList;
    }

    public List<OrderVM> getOrderVMList() {
        return orderVMList;
    }

    public void setOrderVMList(List<OrderVM> orderVMList) {
        this.orderVMList = orderVMList;
    }
}
