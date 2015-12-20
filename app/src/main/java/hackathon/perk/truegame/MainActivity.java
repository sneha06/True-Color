package hackathon.perk.truegame;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar circularProgressBar;
    TextView colorText;
    ImageButton right, wrong;
    Random random = new Random();
    CountDownTimer countDownTimer;
    TextView timer, scorePoint;

    int[] textColor = {R.color.blue, R.color.green, R.color.red};
    String[] ringColor = {"blue", "green", "red"};
    int[] ringdrawable = {R.drawable.blue, R.drawable.green, R.drawable.red};
    int text = 0, ring = 0 , text1 = 0, ring1 = 0, points = 0;
    int time = 19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (TextView) findViewById(R.id.timer);
        circularProgressBar = (ProgressBar) findViewById(R.id.circularProgressbar);
        colorText = (TextView) findViewById(R.id.colorText);
        right = (ImageButton) findViewById(R.id.right);
        wrong = (ImageButton) findViewById(R.id.wrong);
        scorePoint = (TextView) findViewById(R.id.score_point);

        timer.setText("20");
        scorePoint.setText("0");

        right.setOnClickListener(this);
        wrong.setOnClickListener(this);

        setcolor();

        //For setting and starting the timer
        setCountDownTimer();
        countDownTimer.start();
    }

    //setting the color of text and progressbar
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void setcolor() {
        text = random.nextInt(textColor.length);
        ring = random.nextInt(ringColor.length);

        while (text == text1 && ring == ring1) {
            text = random.nextInt(textColor.length);
            ring = random.nextInt(ringColor.length);
        }
        text1 = text;
        ring1 = ring;

        circularProgressBar.setProgressDrawable(getDrawable(ringdrawable[ring]));
        colorText.setText(ringColor[text]);
        colorText.setTextColor(getResources().getColor(textColor[text]));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right:
                countDownTimer.cancel();
                if (colorText.getText().equals(ringColor[ring])) {
                    setcolor();
                    time = 19;
                    setCountDownTimer();
                    countDownTimer.start();
                    points++;
                    scorePoint.setText(points + "");
                } else {
                    gotoGameOver();
                }
                break;
            case R.id.wrong:
                countDownTimer.cancel();
                if (!colorText.getText().equals(ringColor[ring])) {
                    setcolor();
                    time = 19;
                    setCountDownTimer();
                    countDownTimer.start();
                    points++;
                    scorePoint.setText(points + "");
                } else {
                    gotoGameOver();
                }
                break;
            default:
                break;
        }

    }

    private void gotoGameOver() {
        Intent intent = new Intent(MainActivity.this, GameoverActivity.class);
        intent.putExtra("points", points);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    //Setting the count Down timer at 3 sec
    void setCountDownTimer(){
        countDownTimer = new CountDownTimer(2000, 100) {
            @Override
            public void onTick(long m) {
                time--;
                timer.setText(time + "");
            }

            @Override
            public void onFinish() {
                gotoGameOver();
            }
        };
    }
}
