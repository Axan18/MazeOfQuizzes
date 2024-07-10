package UI;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimeLabel extends JLabel {
    Timer timer;
    int secondsToPass;
    public TimeLabel() {
        super();
        timer = new Timer();
        timer.schedule(task, 0, 1000);
        TimeLabel.super.setFont(new Font("Arial", Font.PLAIN, 30));
    }
    public void setTime(int minutes) {
        secondsToPass = minutes * 60;
    }
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            secondsToPass--;
            TimeLabel.super.setText(String.format("%02d:%02d", secondsToPass / 60, secondsToPass % 60));
        }
    };
    public void stop() {
        timer.cancel();
    }
}
