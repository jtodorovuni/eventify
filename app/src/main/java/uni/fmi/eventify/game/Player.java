package uni.fmi.eventify.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import uni.fmi.eventify.R;

public class Player {

    Context context;
    int maxX;
    int maxY;
    int x;
    int y;
    Bitmap skin;

    Rect hitBox;//Collision Detection rectangle

    public Player(Context context,int sizeX,int sizeY){
        this.context = context;

        skin = BitmapFactory.decodeResource(context.getResources(), R.drawable.cart);

        maxY = sizeY - skin.getHeight();
        maxX = sizeX - skin.getWidth();

        Log.wtf("player", skin.getHeight() + "");
        Log.wtf("player maxY", maxY + "");


        y = maxY - 250;
        x = maxX / 2;

        int yHit = y + skin.getHeight() / 3;

        hitBox = new Rect(x, yHit , x + skin.getWidth(), y + skin.getHeight());

    }

    public void update(int movementByX){
        x += movementByX;

        if(x < 0){
            x = 0;
        }else if(x > maxX){
            x = maxX;
        }

        hitBox.left = x;
        hitBox.right = x + skin.getWidth();
    }
}
