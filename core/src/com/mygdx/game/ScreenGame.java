package com.mygdx.game;


import static com.mygdx.game.CarRoad.SCR_HEIGHT;
import static com.mygdx.game.CarRoad.SCR_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class ScreenGame implements Screen {
    CarRoad crd;
    boolean isAccelerometerPresent;

    Texture imgRoad;
    Texture imgMainCar;
    Texture imgEnemyCarWhiteJeep;
    Texture imgEnemyCarPoliceCar;

    Sound sndExplosion;

  long timeStart,timeCurrent;
    boolean isGameOver;
    CarButton btnExit;

    ArrayList<EnemyCar> enemyCars = new ArrayList<>();
    MainCar mainCar;

    long timeEnemySpawn, timeEnemyInterval = 3000;

    Player[] players = new Player[6];
    Road[] roads= new Road[2];

    public ScreenGame(CarRoad carRoad) {
        crd = carRoad;



        isAccelerometerPresent = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);

        imgRoad = new Texture("Highway.png");
        imgMainCar = new Texture("Cars/PurpleCar.png");
        imgEnemyCarWhiteJeep = new Texture("Cars/WhiteJeep.png");
        imgEnemyCarPoliceCar = new Texture("Cars/PoliceCar.png");

        btnExit = new CarButton(crd.fontMedium, "Exit", SCR_WIDTH-100, 50);
        sndExplosion = Gdx.audio.newSound(Gdx.files.internal("CrushSound.mp3"));


        mainCar = new MainCar(200,200,70,150);

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("Noname", 0);
        }
        loadTableOfRecords();

        roads[0] = new Road(0);
        roads[1] = new Road(SCR_HEIGHT);



    }

    @Override
    public void show() {
        gameStart();
    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isTouched()) {
            crd.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            crd.camera.unproject(crd.touch);
            mainCar.vx = (crd.touch.x - mainCar.x)/50;
            mainCar.vy = (crd.touch.y - mainCar.y)/50;


            if(btnExit.hit(crd.touch.x, crd.touch.y)) {
                crd.setScreen(crd.screenIntro);
            }
        } else if(isAccelerometerPresent) {
            mainCar.vx = -Gdx.input.getAccelerometerX()*2;
        }


        for (int i = 0; i < roads.length; i++) {
            roads[i].move();
        }



        if(mainCar.isAlive){
            spawnEnemyCar();
            timeCurrent=TimeUtils.millis()-timeStart;
        }
        for (int i = 0; i < enemyCars.size(); i++) {
            enemyCars.get(i).move();
            if(mainCar.overlap(enemyCars.get(i))) {
                if(mainCar.isAlive){
                    destroyMainCar();
                    if(crd.sound)sndExplosion.play();//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }
                enemyCars.remove(i);
                i--;
            }
        }






        if(mainCar.isAlive){
            mainCar.move();
        }


        crd.camera.update();
        crd.batch.setProjectionMatrix(crd.camera.combined);
        crd.batch.begin();
        for (int i = 0; i < roads.length; i++) {
            crd.batch.draw(imgRoad, roads[i].x, roads[i].y, roads[i].width, roads[i].height);
        }

        for (int i = 0; i < enemyCars.size(); i++) {
            crd.batch.draw(imgEnemyCarWhiteJeep, enemyCars.get(i).getX(), enemyCars.get(i).getY(), enemyCars.get(i).width, enemyCars.get(i).height);
        }


        if(mainCar.isAlive) {
            crd.batch.draw(imgMainCar, mainCar.getX(), mainCar.getY(), mainCar.width, mainCar.height);
        }

        crd.fontSmall.draw(crd.batch, "Time alive: "+timeCurrent/1000, 20, SCR_HEIGHT-20);
        btnExit.font.draw(crd.batch, btnExit.text, btnExit.x, btnExit.y);
        if(isGameOver){
            crd.fontLarge.draw(crd.batch, "GAME OVER", 0, SCR_HEIGHT/5*3, SCR_WIDTH, Align.center, false);
            for (int i = 0; i < players.length; i++) {
                String str = players[i].name + "......." + players[i].time;
                crd.fontSmall.draw(crd.batch, str, 0, SCR_HEIGHT/2-i*40, SCR_WIDTH, Align.center, true);
            }
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

    }



    void gameOver() {
        isGameOver = true;
        players[players.length-1].name= crd.playerName;
        players[players.length-1].time=timeCurrent/1000;
        sortTableOfRecords();
        saveTableOfRecords();


    }

    void gameStart() {
        isGameOver = false;
        mainCar.isAlive=true;
        timeStart=TimeUtils.millis();
        enemyCars.clear();


    }

    void saveTableOfRecords(){
        Preferences prefs = Gdx.app.getPreferences("Table Of Time Car Alive");
        for (int i = 0; i < players.length; i++) {
            prefs.putString("name"+i, players[i].name);
            prefs.putLong("time"+i, players[i].time);
        }
        prefs.flush();
    }

    void loadTableOfRecords(){
        Preferences prefs = Gdx.app.getPreferences("Table Of Time Car Alive");
        for (int i = 0; i < players.length; i++) {
            if(prefs.contains("name"+i)) players[i].name = prefs.getString("name"+i);
            if(prefs.contains("time"+i)) players[i].time = prefs.getLong("time"+i);
        }
    }

    void sortTableOfRecords(){
        for (int j = 0; j < players.length-1; j++) {
            for (int i = 0; i < players.length-1; i++) {
                if(players[i].time<players[i+1].time){
                    long c = players[i].time;
                    players[i].time = players[i+1].time;
                    players[i+1].time = c;
                    String s = players[i].name;
                    players[i].name = players[i+1].name;
                    players[i+1].name = s;

                }
            }
        }
    }

    void clearTableOfRecords(){
        for (int i = 0; i < players.length; i++) {
            players[i].name = "Noname";
            players[i].time = 0;
        }
    }
    void spawnEnemyCar() {
        if(TimeUtils.millis() > timeEnemySpawn+timeEnemyInterval) {
            enemyCars.add(new EnemyCar(75, 160));
            timeEnemySpawn = TimeUtils.millis();
        }
    }
    void destroyMainCar() {

        mainCar.isAlive = false;
        gameOver();

    }
}
