package main.biggreenbook.entity.vo;

import main.biggreenbook.entity.pojo.ContentMessage;

import java.util.List;

// 管理内容界面每页的信息
public class ManageContentPage {
    private List<ContentMessage> list;
    private int totalContents;

    public ManageContentPage() {
    }

    public ManageContentPage(List<ContentMessage> list, int totalContents) {
        this.list = list;
        this.totalContents = totalContents;
    }

    public ManageContentPage(List<ContentMessage> list) {
        this.list = list;
    }

    public List<ContentMessage> getList() {
        return list;
    }

    public void setList(List<ContentMessage> list) {
        this.list = list;
    }

    public int getTotalContents() {
        return totalContents;
    }

    public void setTotalContents(int totalContents) {
        this.totalContents = totalContents;
    }
}
