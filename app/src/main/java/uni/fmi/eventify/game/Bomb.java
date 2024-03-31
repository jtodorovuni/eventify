package uni.fmi.eventify.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

import uni.fmi.eventify.R;

public class Bomb extends Unit{
    Context context;
    int screenX;
    int screenY;
    int speed = 10;
    Random random = new Random();

    int moveCounter = 0;
    int direction = 0;

    public Bomb (Context context, int screenX, int screenY){
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;

        skin = BitmapFactory.decodeResource(
                context.getResources(), R.drawable.bomb);
        reuseBomb();
    }
    public void reuseBomb(){

        maxX = screenX - skin.getWidth();
        maxY = screenY + skin.getHeight();

        y = -50;
        x = random.nextInt(maxX);

        hitBox = new Rect(x, y, x + skin.getWidth(),
                y + skin.getHeight());
    }

    public void update(){
        y+= speed;

        if(moveCounter > 0){
            moveCounter--;

            x += speed * direction;

            if(x > maxX){
                x = maxX;
            }else if(x < 0){
                x = 0;
            }

            hitBox.left = x;
            hitBox.right = x + skin.getWidth();

        }else if(moveCounter <= 0 && random.nextBoolean()){
            moveCounter = 30;

            if(random.nextBoolean()){
                direction = 1;
            }else{
                direction = -1;
            }
        }


        if(y > maxY){
            reuseBomb();
        }

        Log.wtf("Bomb", y + " of " + maxY);

        hitBox.top = y;
        hitBox.bottom = y + skin.getHeight();

    }
}
