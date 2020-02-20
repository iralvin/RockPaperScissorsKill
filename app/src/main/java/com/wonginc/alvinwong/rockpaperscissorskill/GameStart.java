package com.wonginc.alvinwong.rockpaperscissorskill;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by alvinwong on 7/5/16.
 */
public class GameStart extends Activity{

    ImageView battleOutcome, playerHP, cpuHP;
    ImageButton r_imgBtn, p_imgBtn, s_imgBtn;
    ImageButton restartBtn, backBtn;

    TextView winTitle;
    int rand;
    int numWins = 0;
    int numLosses = 0;
    int playerHitCount = 3;
    int cpuHitCount = 3;
    MediaPlayer winnerPlayer, player;
    boolean soundFXcheckedState, musiccheckedState;
    boolean suddenDeathcheckedState;

    int battleResult;
    WinnerAnimationAsync animationAsync = new WinnerAnimationAsync();

//    Switch suddenDeathSwitch = (Switch) findViewById(R.id.suddendeathswitch);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_start);

//        setVolumeControlStream(AudioManager.STREAM_MUSIC);


        battleOutcome = (ImageView) findViewById(R.id.battleOutcome);
        battleOutcome.setBackgroundResource(android.R.color.transparent);

        restartBtn = (ImageButton) findViewById(R.id.restartButton);
//        backBtn = (ImageButton) findViewById(R.id.backButton);

        r_imgBtn = (ImageButton) findViewById(R.id.rockButton);
        p_imgBtn = (ImageButton) findViewById(R.id.paperButton);
        s_imgBtn = (ImageButton) findViewById(R.id.scissorsButton);
        playerHP = (ImageView) findViewById(R.id.playerHPbar);
        cpuHP = (ImageView) findViewById(R.id.playertwohpbar);
        winTitle = (TextView) findViewById(R.id.playerOutcome);


        Typeface killBillFont = Typeface.createFromAsset(getAssets(), "fonts/killbillkatana.ttf");
        winTitle.setTypeface(killBillFont);


        // ROCK BUTTON ACTION
        r_imgBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    r_imgBtn.setImageResource(R.mipmap.rockiconpressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    r_imgBtn.setImageResource(R.mipmap.rockicon);
                    battleSequence(v);
                }
                return false;
            }
        });

        // PAPER BUTTON ACTION
        p_imgBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    p_imgBtn.setImageResource(R.mipmap.papericonpressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    p_imgBtn.setImageResource(R.mipmap.papericon);
                    battleSequence(v);
                }
                return false;
            }
        });

        // SCISSORS BUTTON ACTION
        s_imgBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    s_imgBtn.setImageResource(R.mipmap.scissorsiconpressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    s_imgBtn.setImageResource(R.mipmap.scissorsicon);
                    battleSequence(v);
                }
                return false;
            }
        });


        restartBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    restartBtn.setImageResource(R.mipmap.restartpressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    restartBtn.setImageResource(R.mipmap.restartunpressed);
                    restartGame(v);
                }
                return false;
            }
        });

//        backBtn.setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch (View v, MotionEvent event){
//                if (event.getAction() == MotionEvent.ACTION_DOWN){
//                    backBtn.setImageResource(R.mipmap.backbuttonpressed);
//                } else if (event.getAction() == MotionEvent.ACTION_UP){
//                    backBtn.setImageResource(R.mipmap.backbutton);
//                    finish();
//                }
//                return false;
//            }
//        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void disablePlayerOne() {

        r_imgBtn.setEnabled(false);
        p_imgBtn.setEnabled(false);
        s_imgBtn.setEnabled(false);
    }

    public void enablePlayerOne() {
        r_imgBtn.setEnabled(true);
        p_imgBtn.setEnabled(true);
        s_imgBtn.setEnabled(true);
    }

    public void rockWinsSound(){
        winnerPlayer = MediaPlayer.create(this, R.raw.rockwins);
        winnerPlayer.start();
        winnerPlayer.setLooping(false);
    }

    public void paperWinsSound(){
        winnerPlayer = MediaPlayer.create(this, R.raw.paperwins);
        winnerPlayer.start();
        winnerPlayer.setLooping(false);
    }

    public void scissorsWinsSound(){
        winnerPlayer = MediaPlayer.create(this, R.raw.scissorswins);
        winnerPlayer.start();
        winnerPlayer.setLooping(false);
    }

    public void rockTiesSound(){
        player = MediaPlayer.create(this, R.raw.rocktie);
        player.start();
        player.setLooping(false);
    }

    public void paperTiesSound(){
        player = MediaPlayer.create(this, R.raw.papertie);
        player.start();
        player.setLooping(false);
    }

    public void scissorsTiesSound(){
        player = MediaPlayer.create(this, R.raw.scissorstie);
        player.start();
        player.setLooping(false);
    }



    public void playerOneRockWin() {
        battleOutcome.setBackgroundResource(R.drawable.rockwin);
        battleOutcome.setScaleX(1);
        final AnimationDrawable rockAnimation = (AnimationDrawable) battleOutcome.getBackground();
        rockAnimation.stop();
        rockAnimation.start();
        if (soundFXcheckedState) {
            rockWinsSound();
        } else {
        }
    }

    public void playerOnePaperWin() {
        battleOutcome.setBackgroundResource(R.drawable.paperwin);
        battleOutcome.setScaleX(-1);
        final AnimationDrawable paperAnimation = (AnimationDrawable) battleOutcome.getBackground();
        paperAnimation.stop();
        paperAnimation.start();
        if (soundFXcheckedState) {
            paperWinsSound();
        } else {
        }
    }

    public void playerOneScissorsWin() {
        battleOutcome.setBackgroundResource(R.drawable.scissorswin);
        battleOutcome.setScaleX(1);

        final AnimationDrawable scissorsAnimation = (AnimationDrawable) battleOutcome.getBackground();
        scissorsAnimation.stop();
        scissorsAnimation.start();
        if (soundFXcheckedState) {
            scissorsWinsSound();
        } else {
        }
    }

    public void playerTwoRockWin() {
        battleOutcome.setBackgroundResource(R.drawable.rockwin);
        battleOutcome.setScaleX(-1);
        final AnimationDrawable rockAnimation = (AnimationDrawable) battleOutcome.getBackground();
        rockAnimation.stop();
        rockAnimation.start();
        if (soundFXcheckedState) {
            rockWinsSound();
        } else {
        }
    }

    public void playerTwoPaperWin() {
        battleOutcome.setBackgroundResource(R.drawable.paperwin);
        battleOutcome.setScaleX(1);

        final AnimationDrawable paperAnimation = (AnimationDrawable) battleOutcome.getBackground();
        paperAnimation.stop();
        paperAnimation.start();
        if (soundFXcheckedState) {
            paperWinsSound();
        } else {
        }
    }

    public void playerTwoScissorsWin() {
        battleOutcome.setBackgroundResource(R.drawable.scissorswin);
        battleOutcome.setScaleX(-1);
        final AnimationDrawable scissorsAnimation = (AnimationDrawable) battleOutcome.getBackground();
        scissorsAnimation.stop();
        scissorsAnimation.start();
        if (soundFXcheckedState) {
            scissorsWinsSound();
        } else {
        }
    }

    public void playerRockTie() {
        battleOutcome.setBackgroundResource(R.drawable.rocktie);
        final AnimationDrawable rockTie = (AnimationDrawable) battleOutcome.getBackground();
        rockTie.stop();
        rockTie.start();
        if (soundFXcheckedState) {
            rockTiesSound();
        } else {
        }
    }

    public void playerPaperTie() {
        battleOutcome.setBackgroundResource(R.drawable.papertie);
        final AnimationDrawable paperTie = (AnimationDrawable) battleOutcome.getBackground();
        paperTie.stop();
        paperTie.start();
        if (soundFXcheckedState) {
            paperTiesSound();
        } else {
        }
    }

    public void playerScissorsTie() {
        battleOutcome.setBackgroundResource(R.drawable.scissorstie);
        final AnimationDrawable scissorsTie = (AnimationDrawable) battleOutcome.getBackground();
        scissorsTie.stop();
        scissorsTie.start();
        if (soundFXcheckedState) {
            scissorsTiesSound();
        } else {
        }
    }


    public void battleSequence(View view){
        disablePlayerOne();
      rand = (int) (Math.random() * 3 + 1);
//        rand = 1;
//        count++;

        // rand 1 = cpu rock
        // rand 2 = cpu paper
        // rand 3 = cpu scissors

        if (rand == 1) {
            view.getId();
            if (view.getId() == R.id.rockButton) {
//                playerRockTie();
                battleResult = 4;
            } else if (view.getId() == R.id.paperButton) {
                cpuHitCount--;
//                playerOnePaperWin();
                battleResult = 2;
            } else {
                // ELSE WHEN PLAYER PICKS SCISSORS
                playerHitCount--;
//                playerTwoRockWin();
                battleResult = 7;
            }
        } else if (rand == 2) {
            view.getId();
            if (view.getId() == R.id.rockButton) {
                playerHitCount--;
//                playerTwoPaperWin();
                battleResult = 8;
            } else if (view.getId() == R.id.paperButton) {
//                playerPaperTie();
                battleResult = 5;
            } else {
                // ELSE WHEN PLAYER PICKS SCISSORS
                cpuHitCount--;
//                playerOneScissorsWin();
                battleResult = 3;
            }
        } else {
            view.getId();
            if (view.getId() == R.id.rockButton) {
                cpuHitCount--;
//                playerOneRockWin();
                battleResult = 1;
            } else if (view.getId() == R.id.paperButton) {
                playerHitCount--;
//                playerTwoScissorsWin();
                battleResult = 9;
            } else {
                // ELSE WHEN PLAYER PICKS SCISSORS
//                playerScissorsTie();
                battleResult = 6;
            }
        }

        animationAsync = new WinnerAnimationAsync();
        animationAsync.execute(battleResult);

        // PLAYER HEALTH COUNT AND HP BAR IMAGE
        if (playerHitCount ==3){
            playerHP.setImageResource(R.mipmap.hpbarfull);
        } else if (playerHitCount == 2){
            playerHP.setImageResource(R.mipmap.hpbaryellow);
        } else if (playerHitCount == 1){
            playerHP.setImageResource(R.mipmap.hpbarred);
        } else {
            playerHP.setImageResource(R.mipmap.hpbarempty);
            winTitle.setText("YOU LOSE!");
        }

        // CPU HEALTH COUNT AND HP BAR IMAGE
        if (cpuHitCount ==3){
            cpuHP.setImageResource(R.mipmap.hpbarfull);
        } else if (cpuHitCount == 2){
            cpuHP.setImageResource(R.mipmap.hpbaryellow);
        } else if (cpuHitCount == 1){
            cpuHP.setImageResource(R.mipmap.hpbarred);
        } else {
            cpuHP.setImageResource(R.mipmap.hpbarempty);
            winTitle.setText("YOU WIN!");
        }

        if (playerHitCount == 0 || cpuHitCount == 0){
            disablePlayerOne();
        } else {
            enablePlayerOne();
        }


    }



    class WinnerAnimationAsync extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {

            // 1 - player rock win
            // 2 - player paper win
            // 3 - player scissors win
            // 4 - rock tie
            // 5 - paper tie
            // 6 - scissors tie
            // 7 - cpu rock win
            // 8 - cpu paper win
            // 9 - cpu scissors win

                publishProgress(battleResult);


            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            switch (battleResult){
                case 1:
                    playerOneRockWin();
                    break;
                case 2:
                    playerOnePaperWin();
                    break;
                case 3:
                    playerOneScissorsWin();
                    break;
                case 4:
                    playerRockTie();
                    break;
                case 5:
                    playerPaperTie();
                    break;
                case 6:
                    playerScissorsTie();
                    break;
                case 7:
                    playerTwoRockWin();
                    break;
                case 8:
                    playerTwoPaperWin();
                    break;
                case 9:
                    playerTwoScissorsWin();
                    break;
            }

//            if (battleResult == 1){
//                playerOneRockWin();
//            } else if (battleResult == 2){
//                playerOnePaperWin();
//            } else if (battleResult ==3){
//                playerOneScissorsWin();
//            } else if (battleResult == 4){
//                playerRockTie();
//            } else if (battleResult == 5){
//                playerPaperTie();
//            } else if (battleResult == 6){
//                playerScissorsTie();
//            } else if (battleResult == 7){
//                playerTwoRockWin();
//            } else if (battleResult == 8){
//                playerTwoPaperWin();
//            } else if (battleResult == 9){
//                playerTwoScissorsWin();
//            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }













    public void restartGame(View view) {
//        super.recreate();
        startActivity(getIntent());
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences gameSettings = getSharedPreferences("Settings", MODE_PRIVATE);
        soundFXcheckedState = gameSettings.getBoolean("SoundFX State", soundFXcheckedState);
        musiccheckedState = gameSettings.getBoolean("Music State", musiccheckedState);
        suddenDeathcheckedState = gameSettings.getBoolean("Sudden Death State", suddenDeathcheckedState);
        MainActivity.themePlayer.start();

        if (suddenDeathcheckedState) {
            playerHitCount = 1;
            playerHP.setImageResource(R.mipmap.hpbarred);
            cpuHitCount = 1;
            cpuHP.setImageResource(R.mipmap.hpbarred);
        } else if (!suddenDeathcheckedState) {
            playerHitCount = 3;
            playerHP.setImageResource(R.mipmap.hpbarfull);
            cpuHitCount = 3;
            cpuHP.setImageResource(R.mipmap.hpbarfull);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences gameSettings = getSharedPreferences("Settings", MODE_PRIVATE);
        soundFXcheckedState = gameSettings.getBoolean("SoundFX State", soundFXcheckedState);
        musiccheckedState = gameSettings.getBoolean("Music State", musiccheckedState);
        suddenDeathcheckedState = gameSettings.getBoolean("Sudden Death State", suddenDeathcheckedState);
        MainActivity.themePlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity.themePlayer.pause();

        SharedPreferences gameSettings = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor settingsEditor = gameSettings.edit();
        settingsEditor.putBoolean("SoundFX State", soundFXcheckedState);
        settingsEditor.putBoolean("Music State", musiccheckedState);
        settingsEditor.putBoolean("Sudden Death State", suddenDeathcheckedState);
        settingsEditor.apply();


    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        MainActivity.themePlayer.pause();
//
//        SharedPreferences gameSettings = getSharedPreferences("Settings", MODE_PRIVATE);
//        SharedPreferences.Editor settingsEditor = gameSettings.edit();
//        settingsEditor.putBoolean("SoundFX State", soundFXcheckedState);
//        settingsEditor.putBoolean("Music State", musiccheckedState);
//        settingsEditor.putBoolean("Sudden Death State", suddenDeathcheckedState);
//        settingsEditor.apply();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences gameSettings = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor settingsEditor = gameSettings.edit();
        settingsEditor.putBoolean("SoundFX State", soundFXcheckedState);
        settingsEditor.putBoolean("Music State", musiccheckedState);
        settingsEditor.putBoolean("Sudden Death State", suddenDeathcheckedState);
        settingsEditor.apply();
        finish();
    }


}
