package antidimon.web.tasktrackeremails.controllers;

import antidimon.web.tasktrackeremails.models.DayStatsTransfer;
import antidimon.web.tasktrackeremails.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DayStatsController {

    private final EmailService emailService;

    @PostMapping("/stats")
    public ResponseEntity<DayStatsTransfer> dayStats(@RequestBody DayStatsTransfer dayStatsTransfer) {
        log.info("Received stats for email: {}", dayStatsTransfer.getEmail());
        try {
            String msg = "Начато тасков: " + dayStatsTransfer.getStartedTasks() + "\nВыполнено тасков: " + dayStatsTransfer.getCompletedTasks();
            emailService.sendEmail(dayStatsTransfer.getEmail(), "Дневная статистика", msg);
            log.info("Sent email to {}", dayStatsTransfer.getEmail());
            return ResponseEntity.ok(dayStatsTransfer);
        }catch (MailException e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
