package se.simonfransson.kanigo;

import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkedIn(final View view) {
        final Button checkInButton = (Button) view;
        final ConstraintLayout rootLayout = findViewById(R.id.rootLayout);
        final TextView timeUntil = findViewById(R.id.timeUntilTextView);
        final TextView descriptionText = findViewById(R.id.answerTextView);

        checkInButton.setEnabled(false);
        checkInButton.setClickable(false);
        rootLayout.setBackgroundColor(getResources().getColor(R.color.bestRed, null));
        descriptionText.setText(R.string.time_to_wait);

        final LocalDateTime whenToStop = LocalDateTime.now().plusSeconds(10);

        final Handler timerHandler = new Handler();
        Runnable tickRunnable = new Runnable() {
            @Override
            public void run() {
                if (LocalDateTime.now().isAfter(whenToStop)) {
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.bestGreen, null));
                    checkInButton.setEnabled(true);
                    checkInButton.setClickable(true);
                    timeUntil.setText(null);
                    descriptionText.setText(R.string.time_to_go);
                } else {
                    Duration timeLeft = Duration.between(LocalDateTime.now(), whenToStop);
                    String dateText = formatDateString(timeLeft);
                    timeUntil.setText(dateText);
                    timerHandler.postDelayed(this, 1000);
                }
            }
        };
        timerHandler.post(tickRunnable);
    }

    private String formatDateString(Duration timeLeft) {
        return String.format(Locale.getDefault(),
                "%d " + getResources().getString(R.string.days_left) +
                        " %d " + getResources().getString(R.string.hours_left) +
                        " %d " + getResources().getString(R.string.minutes_left) +
                        " %d " + getResources().getString(R.string.seconds_left),
                timeLeft.toDays(),
                timeLeft.toHours(),
                timeLeft.toMinutes(),
                timeLeft.getSeconds());
    }
}
