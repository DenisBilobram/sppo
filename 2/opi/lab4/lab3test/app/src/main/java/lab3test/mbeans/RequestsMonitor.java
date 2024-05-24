package lab3test.mbeans;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class RequestsMonitor extends NotificationBroadcasterSupport implements RequestsMonitorMBean {

    private int totalPoints = 0;
    private int pointsInArea = 0;
    private int consecutiveMisses = 0;
    private long sequenceNumber = 1;

    @Override
    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public int getPointsInArea() {
        return pointsInArea;
    }

    @Override
    public void clearMonitor() {

        totalPoints = 0;
        pointsInArea = 0;
        consecutiveMisses = 0;
        sequenceNumber = 0;

    }    

    public void addPoint(boolean inArea) {
        totalPoints++;
        if (inArea) {
            pointsInArea++;
            consecutiveMisses = 0;
        } else {
            consecutiveMisses++;
            if (consecutiveMisses >= 2) {
                Notification notification = new Notification(
                    "lab3test.beans.RequestsMonitor.twoConsecutiveMisses",
                    this,
                    sequenceNumber++,
                    System.currentTimeMillis(),
                    "User made two consecutive misses"
                );
                consecutiveMisses = 0;
                sendNotification(notification);
            }
        }
    }
}
