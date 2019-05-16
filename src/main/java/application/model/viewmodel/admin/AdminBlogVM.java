package application.model.viewmodel.admin;

import application.model.viewmodel.blog.BlogVM;
import application.model.viewmodel.common.LayoutHeaderAdminVM;
import application.model.viewmodel.user.UserVM;

import java.util.List;

public class AdminBlogVM {

    private LayoutHeaderAdminVM layoutHeaderAdminVM;
    private List<BlogVM> blogVMList;
    private List<UserVM> userVMList;

    public LayoutHeaderAdminVM getLayoutHeaderAdminVM() {
        return layoutHeaderAdminVM;
    }

    public void setLayoutHeaderAdminVM(LayoutHeaderAdminVM layoutHeaderAdminVM) {
        this.layoutHeaderAdminVM = layoutHeaderAdminVM;
    }

    public List<BlogVM> getBlogVMList() {
        return blogVMList;
    }

    public void setBlogVMList(List<BlogVM> blogVMList) {
        this.blogVMList = blogVMList;
    }

    public List<UserVM> getUserVMList() {
        return userVMList;
    }

    public void setUserVMList(List<UserVM> userVMList) {
        this.userVMList = userVMList;
    }

}
