package hackathon.perk.truegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.perk.perksdk.PerkManager;

public class GameoverActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton replay, home, volume;
    TextView scoreView, congratsText, highScoreView;
    ApplicationClass applicationClass;
    private View decorView;
    int userScore, highScore, flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        decorView = getWindow().getDecorView();

        PerkManager.startSession(GameoverActivity.this, Constants.PERK_APP_KEY);
        PerkManager.trackEvent(GameoverActivity.this, Constants.PERK_APP_KEY, Constants.PERK_EVENT_KEY, true, null);

        applicationClass = (ApplicationClass) getApplication();

        scoreView = (TextView) findViewById(R.id.scoreView);
        replay = (ImageButton) findViewById(R.id.go_replay);
        home = (ImageButton) findViewById(R.id.go_home);
        volume = (ImageButton) findViewById(R.id.go_volume);
        congratsText = (TextView) findViewById(R.id.congrats_text);
        highScoreView = (TextView) findViewById(R.id.highScoreView);

        SharedPreferences sharedPreferences = getSharedPreferences("True_Color", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        userScore = getIntent().getIntExtra("points", 0);
        scoreView.setText(String.valueOf(userScore));

        if(sharedPreferences.getString("Score","").equals(""))
        {
            editor.putString("Score", userScore+"");
            editor.apply();
            flag = 0;
        }else if(Integer.valueOf(sharedPreferences.getString("Score", "")) < userScore){
            editor.putString("Score", userScore+"");
            editor.apply();
            flag = 1;
        }
        else{
            flag = 0;
        }
        highScoreView.setText(sharedPreferences.getString("Score", ""));

        if(flag == 1){
            congratsText.setVisibility(View.VISIBLE);
        }else{
            congratsText.setVisibility(View.GONE);
        }

        replay.setOnClickListener(this);
        home.setOnClickListener(this);
        volume.setOnClickListener(this);

        if(Constants.click % 2 == 0){
            volume.setImageResource(R.drawable.volume);
        }
        else{
            volume.setImageResource(R.drawable.mute);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_replay:
                Intent intent = new Intent(GameoverActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.go_home:
                Intent homeIntent = new Intent(GameoverActivity.this, HomeActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.go_volume:
                Constants.click++;
                if(Constants.click % 2 == 0){
                    volume.setImageResource(R.drawable.volume);
                    applicationClass.resumePlayer();
                }
                else{
                    volume.setImageResource(R.drawable.mute);
                    applicationClass.pausePlayer();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
