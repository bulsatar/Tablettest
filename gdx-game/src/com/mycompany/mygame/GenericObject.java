package com.mycompany.mygame;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * Created by bulsatar on 1/20/15.
 */
public class GenericObject {
    public Animation explosion;
    public Boolean alive;
    public Integer touchX;
    public Integer touchY;
    public float stateTime;
    public Integer frame;

    public GenericObject(Animation pAnim, Integer pX, Integer pY){
        this.explosion = pAnim;
        this.touchX = pX;
        this.touchY = pY;
        this.alive = true;
        this.stateTime = 0;
        this.frame = 0;
    }

    public GenericObject(Animation pAnim, Integer pX, Integer pY, float pTime){
        this.explosion = pAnim;
        this.touchX = pX;
        this.touchY = pY;
        this.alive = true;
        this.stateTime = pTime;
    }


}
