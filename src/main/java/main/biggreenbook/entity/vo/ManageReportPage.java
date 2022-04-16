package main.biggreenbook.entity.vo;

import main.biggreenbook.entity.pojo.ReportMessage;

import java.util.List;

public class ManageReportPage {
    private List<ReportMessage> list;
    private int totalReports;

    public ManageReportPage() {
    }

    public ManageReportPage(List<ReportMessage> list, int totalReports) {
        this.list = list;
        this.totalReports = totalReports;
    }

    public List<ReportMessage> getList() {
        return list;
    }

    public void setList(List<ReportMessage> list) {
        this.list = list;
    }

    public int getTotalReports() {
        return totalReports;
    }

    public void setTotalReports(int totalReports) {
        this.totalReports = totalReports;
    }
}
