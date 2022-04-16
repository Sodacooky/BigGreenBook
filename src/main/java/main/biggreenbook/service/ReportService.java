package main.biggreenbook.service;

import main.biggreenbook.entity.dao.ReportMapper;
import main.biggreenbook.entity.pojo.ReportMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    ReportMapper reportMapper;

    public List<ReportMessage> getReports(Map<?, ?> map) {

        return reportMapper.getReports(map);
    }

    public int countAllReports(int state) {
        return reportMapper.countAllReports(state);
    }

    public int handleReports(Map<?, ?> map) {
        return reportMapper.handleReports(map);
    }

}
