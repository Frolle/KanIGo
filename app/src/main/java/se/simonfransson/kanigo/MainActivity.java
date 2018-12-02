package se.simonfransson.kanigo;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

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
        final TextView descriptText = findViewById(R.id.answerTextView);

        checkInButton.setEnabled(false);
        checkInButton.setClickable(false);
        rootLayout.setBackgroundColor(getResources().getColor(R.color.bestRed, null));
        descriptText.setText("NAJ! Du får vänta:");

        LocalDateTime now = LocalDateTime.now();
        final LocalDateTime whenToStop = now.plusSeconds(10);
        Duration between = Duration.between(now, whenToStop);
        long millis = between.toMillis();

        new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long l) {
                Duration timeLeft = Duration.between(LocalDateTime.now(), whenToStop);
                timeUntil.setText("Days: " + timeLeft.toDays() + ", " +
                        "Hours: " + timeLeft.toHours() + ", " +
                        "Minutes: " + timeLeft.toMinutes() + ", " +
                        "Seconds: " + timeLeft.getSeconds());
            }

            @Override
            public void onFinish() {
                rootLayout.setBackgroundColor(getResources().getColor(R.color.bestGreen, null));
                checkInButton.setEnabled(true);
                checkInButton.setClickable(true);
                timeUntil.setText(null);
                descriptText.setText("JA FÖR FAN! GÅ NU!");
            }
        }.start();
    }
}
