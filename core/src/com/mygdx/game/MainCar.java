package com.mygdx.game;


import static com.mygdx.game.CarRoad.SCR_HEIGHT;
import static com.mygdx.game.CarRoad.SCR_WIDTH;

public class MainCar extends RoadObject {
    boolean isAlive;
    int lives;

    public MainCar(float x, float y, float width, float height) {
        super(x, y, width, height);
        isAlive = true;
        lives = 5;
    }

    @Override
    void move() {
        super.move();
        outOfScreen();
    }

    void outOfScreen() {
        if (x < 100 + width / 2) {
            x = 100 + width / 2;
            vx = 0;
        }
        if (x > SCR_WIDTH - width / 2 - 100) {
            x = SCR_WIDTH - width / 2 - 100;
            vx = 0;
        }
        if (y < 70 + height / 2) {
            y = 70 + height / 2;
            vy = 0;
        }
        if (y > SCR_HEIGHT - height / 2) {
            y = SCR_HEIGHT - height / 2;
            vy = 0;
        }

    }

    boolean overlapWhiteJeep(EnemyCarWhiteJeep e) {
        return Math.abs(x - e.x) < width / 2 + e.width / 2 & Math.abs(y - e.y) < height / 2 + e.height / 2;
    }

    boolean overlapPoliceCar(EnemyCarPoliceCar ep) {
        return Math.abs(x - ep.x) < width / 2 + ep.width / 2 & Math.abs(y - ep.y) < height / 2 + ep.height / 2;
    }
}
