package com.mygdx.game;


import static com.mygdx.game.CarRoad.MODE_EASY;
import static com.mygdx.game.CarRoad.MODE_HARD;
import static com.mygdx.game.CarRoad.MODE_NORMAL;
import static com.mygdx.game.CarRoad.SCR_HEIGHT;
import static com.mygdx.game.CarRoad.SCR_WIDTH;

import com.badlogic.gdx.math.MathUtils;

public class EnemyCar extends RoadObject{
    public static final int SPEED_LOW=-5, SPEED_NORMAL=-8, SPEED_HIGH=-12;
    public static final int TYPE_POLICE=0, TYPE_WHITEJEEP=1, TYPE_TRUCK=2;
    float[][] sizeCar = {{70, 175}, {75, 160}, {85, 230}};

    static float speed;
    CarRoad crd;
    int type;

    public EnemyCar(CarRoad crd, int type) {
        super(0, 0, 0, 0);
        width = sizeCar[type][0];
        height = sizeCar[type][1];
        this.crd=crd;
        this.type=type;
        x = MathUtils.random(width/2+100, SCR_WIDTH-width/2-100);
        y = MathUtils.random(SCR_HEIGHT+height/2, SCR_HEIGHT*2);
        setSpeed();
        vy = speed;
    }

    boolean outOfScreen() {
        return y < -height/2;
    }
    void setSpeed(){
        if(crd.modeOfGame==MODE_EASY){
            speed=SPEED_LOW;
        }
        if(crd.modeOfGame==MODE_NORMAL){
            speed=SPEED_NORMAL;
        }
        if(crd.modeOfGame==MODE_HARD){
            speed=SPEED_HIGH;
        }
    }
}
