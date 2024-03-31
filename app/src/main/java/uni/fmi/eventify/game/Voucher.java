package uni.fmi.eventify.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

import uni.fmi.eventify.R;

public class Voucher extends Unit{

    boolean isGood;

    int[] badSkins = {R.drawable.bad10, R.drawable.bad20,
        R.drawable.bad40, R.drawable.bad60};
    int[] goodSkins = {R.drawable.good25, R.drawable.good45,
            R.drawable.good70};
    int value;
    Random random = new Random();

    int screenX;
    int screenY;

    int speed = 8;

    public Voucher(Context context, int screenX, int screenY){
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;

        reuseItem();
    }

    public void reuseItem(){
        if(random.nextInt(101) > 30){
            isGood = true;
        }else{
            isGood = false;
        }

        changeSkin();

        maxX = screenX - skin.getWidth();
        maxY = screenY + skin.getHeight();

        y = -30;
        x = random.nextInt(maxX);

        hitBox = new Rect(x, y, x+ skin.getWidth(),
                y + skin.getHeight());
    }

    public void changeSkin(){
        if(isGood){
            int skinIndex = random.nextInt(goodSkins.length);
            skin = BitmapFactory.decodeResource( context.getResources(),
                    goodSkins[skinIndex]);
            value = random.nextInt(76);
        }else{
            skin = BitmapFactory.decodeResource( context.getResources(),
                    badSkins[random.nextInt(badSkins.length)]);
            value = random.nextInt(60) * -1;
        }
    }

    public void update(){
        y += speed;

        if(y > maxY){
            if(isGood){
                GameView.discountPercent -= value;
            }

            reuseItem();

            if(speed < 20){
                speed++;
            }
        }

        hitBox.top = y;
        hitBox.bottom = y + skin.getHeight();

    }
}
