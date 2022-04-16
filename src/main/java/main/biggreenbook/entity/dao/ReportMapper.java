package main.biggreenbook.entity.dao;

import main.biggreenbook.entity.pojo.ReportMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {
    List<ReportMessage> getReports(Map<?, ?> map);

    int countAllReports(int state);

    int handleReports(Map<?, ?> map);
}
