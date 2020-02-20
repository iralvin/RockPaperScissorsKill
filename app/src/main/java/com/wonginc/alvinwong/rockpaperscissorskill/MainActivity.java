package com.wonginc.alvinwong.rockpaperscissorskill;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    MusicAsyncTask musicAsyncTask = new MusicAsyncTask();
    static MediaPlayer themePlayer;
    boolean soundFXcheckedState, musiccheckedState, suddenDeathcheckedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        themePlayer = MediaPlayer.create(MainActivity.this, R.raw.rpstheme);


        TextView oneplayerfont = (TextView) findViewById(R.id.vscpubutton);
        Typeface impact = Typeface.createFromAsset(getAssets(), "fonts/impact.ttf");
        oneplayerfont.setTypeface(impact);

        TextView twoplayerfont = (TextView) findViewById(R.id.vsplayerbutton);
        twoplayerfont.setTypeface(impact);

        TextView settingsfont = (TextView) findViewById(R.id.settingsbutton);
        settingsfont.setTypeface(impact);
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

    public void onePlayer(View view) {
        Intent intent = new Intent(MainActivity.this, GameStart.class);
        startActivity(intent);
    }

    public void twoPlayer(View view) {
        Intent intent = new Intent(MainActivity.this, PvPGame.class);
        startActivity(intent);
    }

    public void settings(View view) {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        startActivity(intent);
    }



    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences gameSettings = getSharedPreferences("Settings", MODE_PRIVATE);
        soundFXcheckedState = gameSettings.getBoolean("SoundFX State", Settings.soundFXcheckedState);
        musiccheckedState = gameSettings.getBoolean("Music State", Settings.musiccheckedState);
        suddenDeathcheckedState = gameSettings.getBoolean("Sudden Death State", Settings.suddenDeathcheckedState);

        if (musiccheckedState) {
            themePlayer.start();
            musicAsyncTask = new MusicAsyncTask();
            musicAsyncTask.execute();
        } else if (!musiccheckedState) {
            themePlayer.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences gameSettings = getSharedPreferences("Settings", MODE_PRIVATE);
        soundFXcheckedState = gameSettings.getBoolean("SoundFX State", Settings.soundFXcheckedState);
        musiccheckedState = gameSettings.getBoolean("Music State", Settings.musiccheckedState);
        suddenDeathcheckedState = gameSettings.getBoolean("Sudden Death State", Settings.suddenDeathcheckedState);

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

        themePlayer.pause();

    }

    @Override
    public void onStop() {
        super.onStop();
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
        themePlayer.stop();
        SharedPreferences gameSettings = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor settingsEditor = gameSettings.edit();
        settingsEditor.putBoolean("SoundFX State", soundFXcheckedState);
        settingsEditor.putBoolean("Music State", musiccheckedState);
        settingsEditor.putBoolean("Sudden Death State", suddenDeathcheckedState);
        settingsEditor.apply();
        finish();
    }



    // CODE TO START THE BACKGROUND MUSIC
    class MusicAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            themePlayer.setLooping(true);
            themePlayer.setVolume(100, 100);
            themePlayer.start();
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}
