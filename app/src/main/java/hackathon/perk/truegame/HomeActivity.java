package hackathon.perk.truegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {

    ImageButton instruction, sound;
    ImageView play;
    View decorView;
    ApplicationClass applicationClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        decorView = getWindow().getDecorView();

        applicationClass = (ApplicationClass) getApplication();

        play = (ImageView) findViewById(R.id.play);
        instruction = (ImageButton) findViewById(R.id.instruction);
        sound = (ImageButton) findViewById(R.id.sound);

        play.setOnTouchListener(this);
        instruction.setOnClickListener(this);
        sound.setOnClickListener(this);

        if(Constants.click % 2 == 0){
            sound.setImageResource(R.drawable.volume);
        }
        else{
            sound.setImageResource(R.drawable.mute);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.instruction:
                Intent intent = new Intent(HomeActivity.this, InstructionActivity.class);
                startActivity(intent);
                break;
            case R.id.sound:
                Constants.click++;
                if(Constants.click % 2 == 0){
                    sound.setImageResource(R.drawable.volume);
                    applicationClass.resumePlayer();
                }
                else{
                    sound.setImageResource(R.drawable.mute);
                    applicationClass.pausePlayer();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.play) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                play.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else if(event.getAction() == MotionEvent.ACTION_UP){
                play.setScaleType(ImageView.ScaleType.FIT_XY);
                Intent playintent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(playintent);
                }

            }
        return true;
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

    @Override
    public void onBackPressed() {
        ExitDialogFragment exitDialogFragment = new ExitDialogFragment();
        exitDialogFragment.show(HomeActivity.this.getSupportFragmentManager(), "exit");
    }
}
