package com.mycompany.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;


import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    Animation explosion;
    OrthographicCamera camera;
    Sound boom;

    ArrayList<GenericObject> genlist;

    @Override
    public void create() {

        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(this);

        //setup explosion animation
        Integer FRAME_COLS = 5;
        Integer FRAME_ROWS = 3;
        Texture explosionSheet = new Texture(Gdx.files.internal("explosion.png")); // explosion image sheet shown above
        TextureRegion[][] textureRegions = TextureRegion.split(explosionSheet, explosionSheet.getWidth() / FRAME_COLS, explosionSheet.getHeight() / FRAME_ROWS);              // #10
        TextureRegion[] explosionFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                explosionFrames[index++] = textureRegions[i][j];
            }
        }
        explosion = new Animation(0.025f, explosionFrames);
        genlist = new ArrayList<GenericObject>();
        boom = Gdx.audio.newSound(Gdx.files.internal("gernade.mp3"));
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(camera.viewportWidth * 0.5f, camera.viewportHeight * 0.5f, 0f);

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float temptime = Gdx.graphics.getDeltaTime();
        for (GenericObject temp : genlist) {
            temp.stateTime += temptime;
            if (temp.alive) {
                Vector3 touchPoint = new Vector3();
                TextureRegion currentFrame = temp.explosion.getKeyFrame(temp.stateTime, false);
                camera.unproject(touchPoint.set(temp.touchX, temp.touchY, 0));
                batch.draw(currentFrame, touchPoint.x, touchPoint.y, 96, 96);
            }
        }

        batch.end();

        if (!genlist.isEmpty()) {
            CleanList();
        }
    }

    private void CleanList() {
        ArrayList<GenericObject> temparr = new ArrayList<GenericObject>();
        for (GenericObject temp : genlist) {
            if (temp.explosion.isAnimationFinished(temp.stateTime)) {
                temp.alive = false;
            }
            if (temp.alive) {
                temparr.add(new GenericObject(temp.explosion, temp.touchX, temp.touchY, temp.stateTime));
            }
        }
        genlist.clear();
        genlist.addAll(temparr);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        genlist.add(new GenericObject(explosion, screenX, screenY));
        boom.play();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        genlist.add(new GenericObject(explosion, screenX, screenY));
        boom.play();
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}