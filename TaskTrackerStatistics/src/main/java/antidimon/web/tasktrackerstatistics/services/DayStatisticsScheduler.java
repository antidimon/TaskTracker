package antidimon.web.tasktrackerstatistics.services;

import antidimon.web.tasktrackerstatistics.models.DayStatsTransfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DayStatisticsScheduler {

    private final EventService eventService;
    private final RestTemplate restTemplate;

    @Value("${schedule.url}")
    private String url;

    @Scheduled(cron = "${schedule.time}")
    public void sendStats(){

        log.info("Started sending day stats");

        List<DayStatsTransfer> listOfStats = eventService.getDayStats();
        log.debug("Getted list of stats {}", listOfStats.size());
        for (DayStatsTransfer dayStat: listOfStats){
            ResponseEntity<DayStatsTransfer> response = restTemplate.postForEntity(url, dayStat, DayStatsTransfer.class);
            log.debug("Sended post request to mail service");

            if (response.getStatusCode().is2xxSuccessful()){
                log.info("Successfully sended dayStats for email: {}", dayStat.getEmail());
            } else {
                log.warn("Failed to send dayStats for email: {}", dayStat.getEmail());
            }

        }
    }

}
