package com.wonginc.alvinwong.rockpaperscissorskill;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alvinwong on 7/6/16.
 */
public class PvPGame extends Activity {

    ImageView playerTwoMove, battleOutcome, playerOneHP, playerTwoHP, deathScene;


    ImageButton r_imgBtnOne, p_imgBtnOne, s_imgBtnOne, r_imgBtnTwo, p_imgBtnTwo, s_imgBtnTwo;
    ImageButton restartBtn, backBtn;
    MediaPlayer player;

    TextView winTitle;
    int count = 0;
    int numWins = 0;
    int numLosses = 0;
    int buttonPresses = 0;
    int playerOneHitCount = 3;
    int playerTwoHitCount = 3;
    int asyncOutcome;
    View playerOneSelection, playerTwoSelection;
    boolean soundFXcheckedState, musiccheckedState, suddenDeathcheckedState;
    MyAsyncTask myTask = new MyAsyncTask();

//    MediaPlayer themePlayer;
//    MainActivity musicAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pvp_game);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);



        playerOneHP = (ImageView) findViewById(R.id.playerHPbar);
        playerTwoHP = (ImageView) findViewById(R.id.playertwohpbar);
        winTitle = (TextView) findViewById(R.id.playerOutcome);

        battleOutcome = (ImageView) findViewById(R.id.battleOutcome);
        battleOutcome.setBackgroundResource(android.R.color.transparent);

        r_imgBtnOne = (ImageButton) findViewById(R.id.rockButtonOne);
        p_imgBtnOne = (ImageButton) findViewById(R.id.paperButtonOne);
        s_imgBtnOne = (ImageButton) findViewById(R.id.scissorsButtonOne);

        r_imgBtnTwo = (ImageButton) findViewById(R.id.rockButtonTwo);
        p_imgBtnTwo = (ImageButton) findViewById(R.id.paperButtonTwo);
        s_imgBtnTwo = (ImageButton) findViewById(R.id.scissorsButtonTwo);

        restartBtn = (ImageButton) findViewById(R.id.restartButton);
//        backBtn = (ImageButton) findViewById(R.id.backButton);


        Typeface killBillFont = Typeface.createFromAsset(getAssets(), "fonts/killbillkatana.ttf");
        winTitle.setTypeface(killBillFont);


        // PLAYER ONE ROCK BUTTON ACTION
        r_imgBtnOne.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    r_imgBtnOne.setImageResource(R.mipmap.rockiconpressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    r_imgBtnOne.setImageResource(R.mipmap.rockicon);
                    playerSelections(v);
                }
                return false;
            }
        });

        // PLAYER ONE PAPER BUTTON ACTION
        p_imgBtnOne.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    p_imgBtnOne.setImageResource(R.mipmap.papericonpressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    p_imgBtnOne.setImageResource(R.mipmap.papericon);
                    playerSelections(v);
                }
                return false;
            }
        });

        // PLAYER ONE SCISSORS BUTTON ACTION
        s_imgBtnOne.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    s_imgBtnOne.setImageResource(R.mipmap.scissorsiconpressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    s_imgBtnOne.setImageResource(R.mipmap.scissorsicon);
                    playerSelections(v);
                }
                return false;
            }
        });



        // PLAYER TWO ROCK BUTTON ACTION
        r_imgBtnTwo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    r_imgBtnTwo.setImageResource(R.mipmap.rockiconpressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    r_imgBtnTwo.setImageResource(R.mipmap.rockicon);
                    playerSelections(v);
                }
                return false;
            }
        });

        // PLAYER TWO PAPER BUTTON ACTION
        p_imgBtnTwo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    p_imgBtnTwo.setImageResource(R.mipmap.papericonpressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    p_imgBtnTwo.setImageResource(R.mipmap.papericon);
                    playerSelections(v);
                }
                return false;
            }
        });

        // PLAYER TWO SCISSORS BUTTON ACTION
        s_imgBtnTwo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    s_imgBtnTwo.setImageResource(R.mipmap.scissorsiconpressed);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    s_imgBtnTwo.setImageResource(R.mipmap.scissorsicon);
                    playerSelections(v);
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









    public void rockWinsSound(){
        player = MediaPlayer.create(this, R.raw.rockwins);
        player.start();
        player.setLooping(false);
    }

    public void paperWinsSound(){
        player = MediaPlayer.create(this, R.raw.paperwins);
        player.start();
        player.setLooping(false);
    }

    public void scissorsWinsSound(){
        player = MediaPlayer.create(this, R.raw.scissorswins);
        player.start();
        player.setLooping(false);
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

    public void disablePlayerOne() {

        r_imgBtnOne.setEnabled(false);
        p_imgBtnOne.setEnabled(false);
        s_imgBtnOne.setEnabled(false);
    }

    public void disablePlayerTwo() {

        r_imgBtnTwo.setEnabled(false);
        p_imgBtnTwo.setEnabled(false);
        s_imgBtnTwo.setEnabled(false);
    }

    public void enablePlayerOne() {

        r_imgBtnOne.setEnabled(true);
        p_imgBtnOne.setEnabled(true);
        s_imgBtnOne.setEnabled(true);
    }

    public void enablePlayerTwo() {

        r_imgBtnTwo.setEnabled(true);
        p_imgBtnTwo.setEnabled(true);
        s_imgBtnTwo.setEnabled(true);
    }


    public void playerSelections (View view){
        //SEE WHICH PLAYERS BUTTON WAS PRESSED
        view.getId();

        if (view.getId() == R.id.rockButtonOne || view.getId() == R.id.paperButtonOne || view.getId() == R.id.scissorsButtonOne) {
            playerOneSelection = view;
            buttonPresses++;
            disablePlayerOne();
        }

        if (view.getId() == R.id.rockButtonTwo || view.getId() == R.id.paperButtonTwo || view.getId() == R.id.scissorsButtonTwo) {
            playerTwoSelection = view;
            buttonPresses++;
            disablePlayerTwo();
        }

        if (buttonPresses == 2){
            battleSequence();
        }
    }




    public void battleSequence(){
//        playerOneHP = (ImageView) findViewById(R.id.playerHPbar);
//        playerTwoHP = (ImageView) findViewById(R.id.playertwohpbar);
//        winTitle = (TextView) findViewById(R.id.playerOutcome);


        // IF PLAYER ONE PICKS ROCK
        if (playerOneSelection.getId() == R.id.rockButtonOne && playerTwoSelection.getId() == R.id.rockButtonTwo) {
            asyncOutcome = 1;

        } else if (playerOneSelection.getId() == R.id.rockButtonOne && playerTwoSelection.getId() == R.id.paperButtonTwo) {
            asyncOutcome = 2;
            playerOneHitCount--;

        } else if (playerOneSelection.getId() == R.id.rockButtonOne && playerTwoSelection.getId() == R.id.scissorsButtonTwo) {
            asyncOutcome = 3;
            playerTwoHitCount--;
        }

        // IF PLAYER ONE PICKS PAPER
        else if (playerOneSelection.getId() == R.id.paperButtonOne && playerTwoSelection.getId() == R.id.rockButtonTwo) {
            asyncOutcome = 4;
            playerTwoHitCount--;

        } else if (playerOneSelection.getId() == R.id.paperButtonOne && playerTwoSelection.getId() == R.id.paperButtonTwo) {
            asyncOutcome = 5;

        } else if (playerOneSelection.getId() == R.id.paperButtonOne && playerTwoSelection.getId() == R.id.scissorsButtonTwo) {
            asyncOutcome = 6;
            playerOneHitCount--;
        }

        // IF PLAYER ONE PICKS SCISSORS
        else if (playerOneSelection.getId() == R.id.scissorsButtonOne && playerTwoSelection.getId() == R.id.rockButtonTwo) {
            asyncOutcome = 7;
            playerOneHitCount--;

        } else if (playerOneSelection.getId() == R.id.scissorsButtonOne && playerTwoSelection.getId() == R.id.paperButtonTwo) {
            asyncOutcome = 8;
            playerTwoHitCount--;

        } else if (playerOneSelection.getId() == R.id.scissorsButtonOne && playerTwoSelection.getId() == R.id.scissorsButtonTwo) {
            asyncOutcome = 9;
        }

        myTask = new MyAsyncTask();
        myTask.execute(asyncOutcome);
    }


    public void HPDamage (){
        //RESET BUTTON PRESS COUNTER
        if (buttonPresses == 2) {
            buttonPresses = 0;
        }

        // PLAYER ONE HEALTH COUNT AND HP BAR IMAGE + DEATH SCENE
        if (playerOneHitCount == 3) {
            playerOneHP.setImageResource(R.mipmap.hpbarfull);

        } else if (playerOneHitCount == 2) {
            playerOneHP.setImageResource(R.mipmap.hpbaryellow);

        } else if (playerOneHitCount == 1) {
            playerOneHP.setImageResource(R.mipmap.hpbarred);

        } else {
            playerOneHP.setImageResource(R.mipmap.hpbarempty);


            winTitle.setText("PLAYER 2 WINS!");
            disablePlayerOne();
            disablePlayerTwo();
        }

        // PLAYER TWO HEALTH COUNT AND HP BAR IMAGE + DEATH SCENE
        if (playerTwoHitCount == 3) {
            playerTwoHP.setImageResource(R.mipmap.hpbarfull);

        } else if (playerTwoHitCount == 2) {
            playerTwoHP.setImageResource(R.mipmap.hpbaryellow);

        } else if (playerTwoHitCount == 1) {
            playerTwoHP.setImageResource(R.mipmap.hpbarred);

        } else {
            playerTwoHP.setImageResource(R.mipmap.hpbarempty);
            winTitle.setText("PLAYER 1 WINS!");
            disablePlayerOne();
            disablePlayerTwo();
        }
    }


    class MyAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Integer... params) {
            publishProgress(asyncOutcome);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (asyncOutcome == 1){
                playerRockTie();
            } else if (asyncOutcome == 2){
                playerTwoPaperWin();
            } else if (asyncOutcome == 3){
                playerOneRockWin();
            } else if (asyncOutcome == 4){
                playerOnePaperWin();
            } else if (asyncOutcome == 5){
                playerPaperTie();
            } else if (asyncOutcome == 6){
                playerTwoScissorsWin();
            } else if (asyncOutcome == 7){
                playerTwoRockWin();
            } else if (asyncOutcome == 8){
                playerOneScissorsWin();
            } else if (asyncOutcome == 9){
                playerScissorsTie();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            enablePlayerOne();
            enablePlayerTwo();
            HPDamage();
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
            playerOneHitCount = 1;
            playerOneHP.setImageResource(R.mipmap.hpbarred);
            playerTwoHitCount = 1;
            playerTwoHP.setImageResource(R.mipmap.hpbarred);
        } else if (!suddenDeathcheckedState) {
            playerOneHitCount = 3;
            playerOneHP.setImageResource(R.mipmap.hpbarfull);
            playerTwoHitCount = 3;
            playerTwoHP.setImageResource(R.mipmap.hpbarfull);
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
        SharedPreferences gameSettings = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor settingsEditor = gameSettings.edit();
        settingsEditor.putBoolean("SoundFX State", soundFXcheckedState);
        settingsEditor.putBoolean("Music State", musiccheckedState);
        settingsEditor.putBoolean("Sudden Death State", suddenDeathcheckedState);
        settingsEditor.apply();

        MainActivity.themePlayer.pause();
    }

//    @Override
//    public void onStop() {
//        super.onStop();
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

