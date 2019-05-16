package application.model.viewmodel.blog;

import application.model.viewmodel.common.CategoryVM;
import application.model.viewmodel.common.LayoutHeaderVM;
import application.model.viewmodel.user.UserVM;

import java.util.List;

public class BlogDetailVM {

    private LayoutHeaderVM layoutHeaderVM;
    private List<CategoryVM> categoryVMList;
    private List<UserVM> userVMList;
    private List<BlogVM> blogVMList;
    private String keyWord;

    public LayoutHeaderVM getLayoutHeaderVM() {
        return layoutHeaderVM;
    }

    public void setLayoutHeaderVM(LayoutHeaderVM layoutHeaderVM) {
        this.layoutHeaderVM = layoutHeaderVM;
    }

    public List<CategoryVM> getCategoryVMList() {
        return categoryVMList;
    }

    public void setCategoryVMList(List<CategoryVM> categoryVMList) {
        this.categoryVMList = categoryVMList;
    }

    public List<UserVM> getUserVMList() {
        return userVMList;
    }

    public void setUserVMList(List<UserVM> userVMList) {
        this.userVMList = userVMList;
    }

    public List<BlogVM> getBlogVMList() {
        return blogVMList;
    }

    public void setBlogVMList(List<BlogVM> blogVMList) {
        this.blogVMList = blogVMList;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
