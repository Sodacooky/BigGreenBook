package main.biggreenbook.entity.vo;

import main.biggreenbook.entity.pojo.User;

import java.util.List;

public class ManageUserPage {
    private List<User> list;
    private int totalUsers;

    public ManageUserPage(List<User> list, int totalUsers) {
        this.list = list;
        this.totalUsers = totalUsers;
    }

    public ManageUserPage(List<User> list) {
        this.list = list;
    }

    public ManageUserPage() {
    }

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }
}
