package lab3test.mbeans;

import javax.management.Notification;
import javax.management.NotificationListener;

public class RequestsNotificationListener implements NotificationListener {
    @Override
    public void handleNotification(Notification notification, Object handback) {
        System.out.println("Received notification: " + notification.getMessage());
    }
}
