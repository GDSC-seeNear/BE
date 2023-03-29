package seeNear.seeNear_BE.domain.Report;

import org.springframework.web.bind.annotation.*;
import seeNear.seeNear_BE.domain.Report.domain.Report;


import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(value="/{elderlyId}")
    public List<Report> getReport(@PathVariable int elderlyId, @RequestParam(value = "date", required = false) LocalDate date) {
        if (date == null) {
            return reportService.getReportAll(elderlyId);
        }
        return null;
    }



}
