package antidimon.web.tasktrackeremails.services;


import antidimon.web.tasktrackeremails.kafka.MainEvent;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    public String getMessage(MainEvent event) {
        return switch (event.getAction()) {
            case LOG_IN -> "Успешный логин";
            case CREATE_ACCOUNT -> "Успешное создание аккаунта";
            case CREATE_PROJECT -> "Успешно создан проект:\n\t" + event.getMessage().get("projectName");
            case DELETE_PROJECT -> "Успешно удалён проект:\n\t" + event.getMessage().get("projectName");
            case INVITE_USER -> "Успешно приглашён:\n\t" + event.getMessage().get("invitedUsername") + "\nВ проект:\n\t" + event.getMessage().get("projectName");
            case KICK_USER -> "Успешно удалён:\n\t" + event.getMessage().get("kickedUsername") + "\nИз проекта:\n\t" + event.getMessage().get("projectName");
            case ADD_TASK -> "Успешно добавлен таск:\n\t" + event.getMessage().get("taskName") + "\nВ проекте:\n\t" + event.getMessage().get("projectName");
            case UPDATE_TASK -> "Успешно обновлён таск\n\t" + event.getMessage().get("taskName") + "\nВ проекте\n\t" + event.getMessage().get("projectName");
            case DELETE_TASK -> "Успешно удалён таск\n\t" + event.getMessage().get("taskName") + "\nВ проекте\n\t" + event.getMessage().get("projectName");
        };
    }

    public String getActionTopic(MainEvent event) {
        return switch (event.getAction()) {
            case LOG_IN -> "Вход";
            case CREATE_ACCOUNT -> "Аккаунт";
            case CREATE_PROJECT, DELETE_PROJECT -> "Изменения проектов";
            case INVITE_USER, KICK_USER -> "Пользователи проекта";
            case ADD_TASK, UPDATE_TASK, DELETE_TASK-> "Работа с тасками";
        };
    }
}
