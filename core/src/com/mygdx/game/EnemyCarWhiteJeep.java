package com.mygdx.game;


import static com.mygdx.game.CarRoad.SCR_HEIGHT;
import static com.mygdx.game.CarRoad.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class EnemyCarWhiteJeep extends RoadObject{
    public static final float SPEED_LOW=-5, SPEED_NORMAL=-8, SPEED_HIGH=-12;
    static float speed=SPEED_NORMAL;

    public EnemyCarWhiteJeep(float width, float height) {
        super(0, 0, width, height);

        x = MathUtils.random(width/2+100, SCR_WIDTH-width/2-100);
        y = MathUtils.random(SCR_HEIGHT+height/2, SCR_HEIGHT*2);
        vy= speed;
    }

    boolean outOfScreen() {
        return y < -height/2;
    }

}
