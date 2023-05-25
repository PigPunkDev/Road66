package com.mygdx.game;


import static com.mygdx.game.CarRoad.SCR_HEIGHT;
import static com.mygdx.game.CarRoad.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class ScreenAbout implements Screen {
    CarRoad crd;
    Texture imgBackGround;
    CarButton btnBack;

    String textAbout =
                    "ROAD 66\n" +
                            "\n"+
                    "In this game you need to \n" +
                    "avoid crushing into other cars\n"+
                    "and get as far as possible.\n"+
                    "\n"+
                    "Music:AC/DC\n"+
                            "<<Highway to hell>>";

    public ScreenAbout(CarRoad carRoad) {
        crd = carRoad;
        imgBackGround = new Texture("Road66About.jfif");

        btnBack = new CarButton(crd.fontLarge, "BACK", 200, 150);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if(Gdx.input.justTouched()) {
            crd.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            crd.camera.unproject(crd.touch);

            if(btnBack.hit(crd.touch.x, crd.touch.y)){
                crd.setScreen(crd.screenIntro);
            }
        }


        crd.camera.update();
        crd.batch.setProjectionMatrix(crd.camera.combined);
        crd.batch.begin();
        crd.batch.draw(imgBackGround, 0, 0, SCR_WIDTH, SCR_HEIGHT);
        crd.fontMedium.draw(crd.batch, textAbout, 50, 650);
        btnBack.font.draw(crd.batch, btnBack.text, btnBack.x, btnBack.y);
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
    }
}
