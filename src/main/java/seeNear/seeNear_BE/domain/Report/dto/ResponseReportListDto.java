package seeNear.seeNear_BE.domain.Report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import seeNear.seeNear_BE.domain.Report.domain.Report;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseReportListDto {
    List<Report> reportList;
}
