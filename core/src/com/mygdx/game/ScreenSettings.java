package com.mygdx.game;

import static com.mygdx.game.CarRoad.MODE_EASY;
import static com.mygdx.game.CarRoad.MODE_HARD;
import static com.mygdx.game.CarRoad.MODE_NORMAL;
import static com.mygdx.game.CarRoad.SCR_HEIGHT;
import static com.mygdx.game.CarRoad.SCR_WIDTH;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;


public class ScreenSettings implements Screen {
    CarRoad crd;

    InputKeyboard keyboard;

    Texture imgBackGround;

    CarButton btnName;
    CarButton btnMode;
    CarButton btnSound;
    CarButton btnMusic;
    CarButton btnClearRecords;
    CarButton btnBack;


    boolean isEnterName;

    public ScreenSettings(CarRoad carRoad) {
        crd = carRoad;

        keyboard = new InputKeyboard(SCR_WIDTH, SCR_HEIGHT/2, 8);

        imgBackGround = new Texture("RoadForSettings.png");

        btnName = new CarButton(crd.fontMedium, "Name: Noname", 50, 600);
        btnMode = new CarButton(crd.fontMedium, "Mode: Easy", 50, 550);
        btnSound = new CarButton(crd.fontMedium, "Sound: ON", 50, 500);
        btnMusic = new CarButton(crd.fontMedium, "Music: ON", 50, 450);
        btnClearRecords = new CarButton(crd.fontMedium, "Clear Records", 50, 400);
        btnBack = new CarButton(crd.fontMedium, "Back", 50, 150);

        loadSettings();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(Gdx.input.justTouched()) {
            crd.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            crd.camera.unproject(crd.touch);
            if(isEnterName) {
                if(keyboard.endOfEdit(crd.touch.x, crd.touch.y)){
                    isEnterName = false;
                    crd.playerName = keyboard.getText();
                    updateButtons();
                }
            } else {
                if (btnName.hit(crd.touch.x, crd.touch.y)) {
                    isEnterName = true;
                }
                if (btnMode.hit(crd.touch.x, crd.touch.y)) {
                    changeMode();
                    if(btnMode.hit(crd.touch.x, crd.touch.y)){
                        if(crd.modeOfGame == MODE_EASY){
                            crd.modeOfGame = MODE_NORMAL;
                            btnMode.text = "Mode: Normal";
                            crd.screenGame.timeEnemyInterval=1500;
                            EnemyCar.speed = EnemyCar.SPEED_NORMAL;
                       } else if(crd.modeOfGame == MODE_NORMAL){
                            crd.modeOfGame = MODE_HARD;
                            btnMode.text = "Mode: Hard";
                            crd.screenGame.timeEnemyInterval=750;
                            EnemyCar.speed= EnemyCar.SPEED_HIGH;
                        } else if(crd.modeOfGame == MODE_HARD){
                            crd.modeOfGame = MODE_EASY;
                            btnMode.text = "Mode: Easy";
                            crd.screenGame.timeEnemyInterval = 3000;
                            EnemyCar.speed= EnemyCar.SPEED_LOW;
                        }
                    }
                }
                if (btnSound.hit(crd.touch.x, crd.touch.y)) {
                    crd.sound = !crd.sound;
                    updateButtons();
                }
                if (btnMusic.hit(crd.touch.x, crd.touch.y)) {
                    crd.music = !crd.music;
                    updateButtons();
                    if (crd.music) {
                        crd.screenGame.sndMusic.play();
                    } else {
                        crd.screenGame.sndMusic.stop();
                    }
                }
                if (btnClearRecords.hit(crd.touch.x, crd.touch.y)) {
                    btnClearRecords.setText("Records Pured");
                    crd.screenGame.clearTableOfRecords();
                }
                if (btnBack.hit(crd.touch.x, crd.touch.y)) {
                    crd.setScreen(crd.screenIntro);
                    saveSettings();
                    btnClearRecords.setText("Clear Records");
                }
            }
        }


        crd.camera.update();
        crd.batch.setProjectionMatrix(crd.camera.combined);
        crd.batch.begin();
        crd.batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        btnName.font.draw(crd.batch, btnName.text, btnName.x, btnName.y);
        btnMode.font.draw(crd.batch, btnMode.text, btnMode.x, btnMode.y);
        btnSound.font.draw(crd.batch, btnSound.text, btnSound.x, btnSound.y);
        btnMusic.font.draw(crd.batch, btnMusic.text, btnMusic.x, btnMusic.y);
        btnClearRecords.font.draw(crd.batch, btnClearRecords.text, btnClearRecords.x, btnClearRecords.y);
        btnBack.font.draw(crd.batch, btnBack.text, btnBack.x, btnBack.y);
        if(isEnterName) {
            keyboard.draw(crd.batch);
        }
        crd.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        imgBackGround.dispose();
        keyboard.dispose();
    }

    void changeMode(){
        if(crd.modeOfGame == MODE_EASY){
            crd.modeOfGame = MODE_NORMAL;
        } else if(crd.modeOfGame == MODE_NORMAL){
            crd.modeOfGame = MODE_HARD;
        } else if(crd.modeOfGame == MODE_HARD){
            crd.modeOfGame = MODE_EASY;
        }
        updateButtons();
    }

    void saveSettings() {
        Preferences prefs = Gdx.app.getPreferences("SpaceShooterSettings");
        prefs.putString("Name", crd.playerName);
        prefs.putInteger("Mode", crd.modeOfGame);
        prefs.putBoolean("Sound", crd.sound);
        prefs.putBoolean("Music", crd.music);
        prefs.flush();
    }

    void loadSettings() {
        Preferences prefs = Gdx.app.getPreferences("SpaceShooterSettings");
        if(prefs.contains("Name")) crd.playerName = prefs.getString("Name");
        if(prefs.contains("Mode")) crd.modeOfGame = prefs.getInteger("Mode");
        if(prefs.contains("Sound")) crd.sound = prefs.getBoolean("Sound");
        if(prefs.contains("Music")) crd.music = prefs.getBoolean("Music");
        updateButtons();
    }

    void updateButtons() {
        btnName.setText("Name: "+crd.playerName);

        if (crd.modeOfGame == MODE_EASY) btnMode.setText("Mode: Easy");
        if (crd.modeOfGame == MODE_NORMAL) btnMode.setText("Mode: Normal");
        if (crd.modeOfGame == MODE_HARD) btnMode.setText("Mode: Hard");

        btnSound.setText( crd.sound ? "Sound: ON" : "Sound: OFF");


        btnMusic.setText( crd.music ? "Music: ON" : "Music: OFF");

    }
}
