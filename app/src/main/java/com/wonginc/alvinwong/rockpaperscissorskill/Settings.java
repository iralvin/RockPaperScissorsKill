package com.wonginc.alvinwong.rockpaperscissorskill;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

/**
 * Created by alvinwong on 7/8/16.
 */
public class Settings extends Activity{

    static boolean soundFXcheckedState = true;
    static boolean musiccheckedState = true;
    static boolean suddenDeathcheckedState;
    ImageButton soundfxbtn, musicbtn, suddendeathbtn;
    ImageButton backBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        soundfxbtn = (ImageButton) findViewById(R.id.soundfxswitch);
        musicbtn = (ImageButton) findViewById(R.id.musicswitch);
        suddendeathbtn = (ImageButton) findViewById(R.id.suddendeathswitch);
//        backBtn = (ImageButton) findViewById(R.id.backButton);


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




    public void SoundFXClick(View view) {
        if (soundFXcheckedState){
            soundFXcheckedState = false;
            soundfxbtn.setBackgroundResource(R.mipmap.offtoggle);
        } else if (!soundFXcheckedState){
            soundFXcheckedState = true;
            soundfxbtn.setBackgroundResource(R.mipmap.ontoggle);
        }
    }


    public void MusicClick(View view) {
        if (musiccheckedState){
            musiccheckedState = false;
            musicbtn.setBackgroundResource(R.mipmap.offtoggle);
            MainActivity.themePlayer.stop();
        } else if (!musiccheckedState){
            musiccheckedState = true;
            musicbtn.setBackgroundResource(R.mipmap.ontoggle);
            MainActivity.themePlayer = MediaPlayer.create(Settings.this, R.raw.rpstheme);
            MainActivity.themePlayer.setLooping(true);
            MainActivity.themePlayer.setVolume(100, 100);
            MainActivity.themePlayer.start();
        }
    }


    public void SuddenDeathClick(View view) {
        if (suddenDeathcheckedState){
            suddenDeathcheckedState = false;
            suddendeathbtn.setBackgroundResource(R.mipmap.offtoggle);
        } else if (!suddenDeathcheckedState){
            suddenDeathcheckedState = true;
            suddendeathbtn.setBackgroundResource(R.mipmap.ontoggle);
        }
    }





    @Override
    protected void onStart() {
        super.onStart();
        MainActivity.themePlayer.start();
        SharedPreferences gameSettings = getSharedPreferences("Settings", MODE_PRIVATE);
        soundFXcheckedState = gameSettings.getBoolean("SoundFX State", soundFXcheckedState);
        musiccheckedState = gameSettings.getBoolean("Music State", musiccheckedState);
        suddenDeathcheckedState = gameSettings.getBoolean("Sudden Death State", suddenDeathcheckedState);

        if (!soundFXcheckedState){
            soundfxbtn.setBackgroundResource(R.mipmap.offtoggle);
        } else if (soundFXcheckedState){
            soundfxbtn.setBackgroundResource(R.mipmap.ontoggle);
        }

        if (!musiccheckedState){
            musicbtn.setBackgroundResource(R.mipmap.offtoggle);
        } else if (musiccheckedState){
            musicbtn.setBackgroundResource(R.mipmap.ontoggle);
        }

        if (!suddenDeathcheckedState){
            suddendeathbtn.setBackgroundResource(R.mipmap.offtoggle);
        } else if (suddenDeathcheckedState){
            suddendeathbtn.setBackgroundResource(R.mipmap.ontoggle);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.themePlayer.start();
        SharedPreferences gameSettings = getSharedPreferences("Settings", MODE_PRIVATE);
        soundFXcheckedState = gameSettings.getBoolean("SoundFX State", soundFXcheckedState);
        musiccheckedState = gameSettings.getBoolean("Music State", musiccheckedState);
        suddenDeathcheckedState = gameSettings.getBoolean("Sudden Death State", suddenDeathcheckedState);

        if (!soundFXcheckedState){
            soundfxbtn.setBackgroundResource(R.mipmap.offtoggle);
        } else if (soundFXcheckedState){
            soundfxbtn.setBackgroundResource(R.mipmap.ontoggle);
        }

        if (!musiccheckedState){
            musicbtn.setBackgroundResource(R.mipmap.offtoggle);
        } else if (musiccheckedState){
            musicbtn.setBackgroundResource(R.mipmap.ontoggle);
        }

        if (!suddenDeathcheckedState){
            suddendeathbtn.setBackgroundResource(R.mipmap.offtoggle);
        } else if (suddenDeathcheckedState){
            suddendeathbtn.setBackgroundResource(R.mipmap.ontoggle);
        }

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



}
