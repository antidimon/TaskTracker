package antidimon.web.tasktrackerstatistics.services;


import antidimon.web.tasktrackerstatistics.kafka.Actions;
import antidimon.web.tasktrackerstatistics.kafka.MainEvent;
import antidimon.web.tasktrackerstatistics.kafka.MyUserOutputDTO;
import antidimon.web.tasktrackerstatistics.models.DayStatsTransfer;
import antidimon.web.tasktrackerstatistics.models.MainEventEntity;
import antidimon.web.tasktrackerstatistics.models.MyUser;
import antidimon.web.tasktrackerstatistics.models.TaskStatuses;
import antidimon.web.tasktrackerstatistics.repositories.MainEventRepository;
import antidimon.web.tasktrackerstatistics.repositories.MyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final MainEventRepository mainEventRepository;
    private final MyUserRepository myUserRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public void save(MainEvent mainEvent) {
        MyUser user;
        if (mainEvent.getAction() == Actions.CREATE_ACCOUNT) {
            user = this.getUser(mainEvent.getUser());
            user.setCreatedAt(mainEvent.getUser().getTime());
            myUserRepository.save(user);
            log.debug("Saved new user({}) to db", user.getUsername());
        }else{
            user = myUserRepository.findByUsername(mainEvent.getUser().getUsername()).get();
            log.debug("Founded user {}", user.getUsername());
        }
        MainEventEntity mainEventEntity = modelMapper.map(mainEvent, MainEventEntity.class);
        mainEventEntity.setUser(user);
        mainEventEntity.setMsg(this.getMessage(mainEvent));

        log.debug("Created event entity to save for user {}", user.getUsername());
        mainEventRepository.save(mainEventEntity);
    }

    private String getMessage(MainEvent mainEvent) {
        return switch (mainEvent.getAction()) {
            case LOG_IN -> "Logged in as " + mainEvent.getUser().getUsername();
            case CREATE_ACCOUNT -> "created account " + mainEvent.getUser().getUsername();
            case CREATE_PROJECT -> "created project " + mainEvent.getMessage().get("projectName");
            case DELETE_PROJECT -> "deleted project " + mainEvent.getMessage().get("projectName");
            case INVITE_USER -> "invite user " + mainEvent.getMessage().get("invitedUsername");
            case KICK_USER -> "kicked user " + mainEvent.getMessage().get("kickedUsername");
            case ADD_TASK -> "added task " + mainEvent.getMessage().get("taskName");
            case DELETE_TASK -> "deleted task " + mainEvent.getMessage().get("taskName");
            case UPDATE_TASK ->
                    "updated task " + mainEvent.getMessage().get("taskName") + ((mainEvent.getMessage().get("taskNewStatus") == null) ? "" : " Status: " + mainEvent.getMessage().get("taskNewStatus"));
        };
    }

    public MyUser getUser(MyUserOutputDTO user) {
        return modelMapper.map(user, MyUser.class);
    }

    public List<DayStatsTransfer> getDayStats() {
        List<DayStatsTransfer> dayStats = new ArrayList<>();
        List<MyUser> allUsers = myUserRepository.findAll();
        for (MyUser user : allUsers) {
            DayStatsTransfer dayStat = new DayStatsTransfer();
            dayStat.setEmail(user.getEmail());
            List<MainEventEntity> updateEvents =mainEventRepository.getUserDayEvents(user.getId());
            log.debug("Founded day events {}", updateEvents.size());
            for (MainEventEntity event : updateEvents) {
                if (event.getMsg().contains("Status:")) {
                    String newStatus = event.getMsg().split("Status: ")[1];
                    if (newStatus.equals(TaskStatuses.IN_PROGRESS.toString())) dayStat.setStartedTasks(dayStat.getStartedTasks() + 1);
                    if (newStatus.equals(TaskStatuses.COMPLETED.toString())) dayStat.setCompletedTasks(dayStat.getCompletedTasks() + 1);
                }
            }
            log.debug("Transfer object is ready for user {}", user.getUsername());
            dayStats.add(dayStat);
            log.debug("Added transfer object for user {} to list of transfer objects", user.getUsername());
        }
        return dayStats;
    }
}
