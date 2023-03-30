package seeNear.seeNear_BE.domain.Report;

import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.Report.dto.ResponseReportListDto;


import java.time.LocalDate;


@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value="/{elderlyId}")
    public ResponseReportListDto getReport(@PathVariable int elderlyId, @RequestParam(value = "date", required = false) LocalDate date) {
        if (date == null) {
            return reportService.getReportAll(elderlyId);
        }
        return null;
    }



}
