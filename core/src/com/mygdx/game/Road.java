package com.mygdx.game;

import static com.mygdx.game.CarRoad.SCR_HEIGHT;
import static com.mygdx.game.CarRoad.SCR_WIDTH;

public class Road extends RoadObject{
    public Road(float y) {
        super(0, y, SCR_WIDTH, SCR_HEIGHT);
        vy=-1;
    }

    @Override
    void move() {
        super.move();
        outOfScreen();
    }

    void outOfScreen() {
        if(y < -SCR_HEIGHT) {
            y = SCR_HEIGHT;
        }
    }
}
