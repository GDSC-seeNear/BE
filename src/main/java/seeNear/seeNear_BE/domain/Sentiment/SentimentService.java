package seeNear.seeNear_BE.domain.Sentiment;

import org.springframework.stereotype.Service;
import seeNear.seeNear_BE.domain.Sentiment.domain.Sentiment;

import java.util.Date;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SentimentService {
    private final SentimentRepository sentimentRepository;

    public SentimentService(SentimentRepository sentimentRepository) {
        this.sentimentRepository = sentimentRepository;
    }

    public Map<String, Long> createSentimentStatistics (int elderlyId, Date date) {
        List<Sentiment> sentiments = sentimentRepository.getSentimentByDate(elderlyId, date);
        Map<String, Long> sentimentStatistics =sentiments.stream().collect(Collectors.groupingBy(Sentiment::getType, Collectors.counting()));

        return sentimentStatistics;
    }
}
