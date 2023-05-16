package com.mygdx.game;


import static com.mygdx.game.CarRoad.SCR_HEIGHT;
import static com.mygdx.game.CarRoad.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class EnemyCar extends RoadObject{
    public EnemyCar(float width, float height) {
        super(0, 0, width, height);
        x = MathUtils.random(width/2+100, SCR_WIDTH-width/2-100);
        y = MathUtils.random(SCR_HEIGHT+height/2, SCR_HEIGHT*2);
        vy = MathUtils.random(-5f,-3f);
    }

    boolean outOfScreen() {
        return y < -height/2;
    }
}
