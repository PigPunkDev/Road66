package com.mygdx.game;


import static com.mygdx.game.CarRoad.SCR_HEIGHT;
import static com.mygdx.game.CarRoad.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class EnemyCarPoliceCar extends RoadObject{
    public static final float SPEED_LOW=5, SPEED_NORMAL=-10, SPEED_HIGH=-14;
    static float speed=SPEED_NORMAL;
    CarRoad crd;

    public EnemyCarPoliceCar(CarRoad crd, float width, float height) {
        super(0, 0, width, height);
        this.crd=crd;
        x = MathUtils.random(width/2+100, SCR_WIDTH-width/2-100);
        y = MathUtils.random(SCR_HEIGHT+height/2, SCR_HEIGHT*2);
        vy= speed;
    }

    boolean outOfScreen() {
        return y < -height/2;
    }

}