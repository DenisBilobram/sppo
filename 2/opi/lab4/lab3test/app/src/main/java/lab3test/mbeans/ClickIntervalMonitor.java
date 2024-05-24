package lab3test.mbeans;

import javax.management.NotificationBroadcasterSupport;
import java.util.ArrayList;
import java.util.List;

public class ClickIntervalMonitor extends NotificationBroadcasterSupport implements ClickIntervalMonitorMBean {

    private List<Long> clickTimestamps = new ArrayList<>();

    @Override
    public double getAverageInterval() {
        if (clickTimestamps.size() < 2) {
            return 0.0;
        }

        long totalInterval = 0;
        for (int i = 1; i < clickTimestamps.size(); i++) {
            totalInterval += clickTimestamps.get(i) - clickTimestamps.get(i - 1);
        }

        return (double) totalInterval / (clickTimestamps.size() - 1);
    }

    @Override
    public void clearMonitor() {
        clickTimestamps.clear();
    }

    public void addClickTimestamp(long timestamp) {
        clickTimestamps.add(timestamp);
    }
}

