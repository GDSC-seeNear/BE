package seeNear.seeNear_BE.domain.Report;

import org.springframework.stereotype.Service;
import seeNear.seeNear_BE.domain.Chat.ChatRepository;
import seeNear.seeNear_BE.domain.NamedEntity.NamedEntityRepository;
import seeNear.seeNear_BE.domain.Report.domain.Report;
import seeNear.seeNear_BE.domain.Sentiment.SentimentService;
import seeNear.seeNear_BE.domain.StatusCheck.StatusCheckRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ChatRepository chatRepository;
    private final NamedEntityRepository namedEntityRepository;

    private StatusCheckRepository statusCheckRepository;

    private SentimentService sentimentService;

    public ReportService(ChatRepository chatRepository, NamedEntityRepository namedEntityRepository, StatusCheckRepository statusCheckRepository, SentimentService sentimentService) {
        this.chatRepository = chatRepository;
        this.namedEntityRepository = namedEntityRepository;
        this.statusCheckRepository = statusCheckRepository;
        this.sentimentService = sentimentService;
    }


    public void getReportByDate(int elderlyId) {

    }

    public List<Report> getReportAll(int elderlyId) {
        //유저가 채팅 사용한 전체 날 가져오기
        var date = chatRepository.getDistinctChatDates(elderlyId);
        System.out.println(date);

        List<Report> r = date.stream().map(d -> createReportByDate(elderlyId, d)).collect(Collectors.toList());
        return r;
        //.forEach(reportRepository::save);
    }

    public Report createReportByDate(int elderlyId, Date date) {
        //ner 가져오기
        var namedEntityResult = namedEntityRepository.getNamedEntityByDate(elderlyId, date);

        //status 가져오기
        var statusCheckResult = statusCheckRepository.getStatusByElderlyIdAndDate(elderlyId, date);

        //감성분석 가져오기
        var sentimentResult = sentimentService.createSentimentStatistics(elderlyId, date);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);
        Report a = new Report(formattedDate, statusCheckResult, namedEntityResult, sentimentResult);
        return a;

    }

}
