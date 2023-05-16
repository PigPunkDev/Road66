package com.mygdx.game;

import static com.mygdx.game.CarRoad.SCR_HEIGHT;
import static com.mygdx.game.CarRoad.SCR_WIDTH;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenIntro implements Screen {
    CarRoad crd;
    Texture imgBackGround;
    CarButton btnPlay,btnSettings, btnAbout,btnExit;


    public ScreenIntro(CarRoad carRoad){
        crd=carRoad;
        imgBackGround = new Texture("AmericanRoute.jfif");

        btnPlay = new CarButton(crd.fontLarge, "PLAY", 50, 450);
        btnAbout = new CarButton(crd.fontLarge, "ABOUT", 50, 250);
        btnSettings = new CarButton(crd.fontLarge, "SETTINGS", 50, 350);
        btnExit = new CarButton(crd.fontLarge, "EXIT", 50, 150);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            crd.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            crd.camera.unproject(crd.touch);
            if(btnPlay.hit(crd.touch.x, crd.touch.y)){
                sleep(1000);
                crd.setScreen(crd.screenGame);
            }
            if(btnAbout.hit(crd.touch.x, crd.touch.y)){
                crd.setScreen(crd.screenAbout);
            }
            if(btnSettings.hit(crd.touch.x, crd.touch.y)){
                crd.setScreen(crd.screenSettings);
            }
            if(btnExit.hit(crd.touch.x, crd.touch.y)){
                Gdx.app.exit();
            }
        }
        crd.camera.update();
        crd.batch.setProjectionMatrix(crd.camera.combined);
        crd.batch.begin();
        crd.batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        btnPlay.font.draw(crd.batch, btnPlay.text, btnPlay.x, btnPlay.y);
        btnSettings.font.draw(crd.batch, btnSettings.text, btnSettings.x, btnSettings.y);
        btnAbout.font.draw(crd.batch, btnAbout.text, btnAbout.x, btnAbout.y);
        btnExit.font.draw(crd.batch, btnExit.text, btnExit.x, btnExit.y);
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

    }
    void sleep(long time) {
        try{
            Thread.sleep(time);
        } catch (Exception ignored){
        }
    }
}
