package uni.fmi.eventify.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.VelocityTracker;

import java.util.ArrayList;

import uni.fmi.eventify.R;

public class GameView extends SurfaceView implements Runnable{
    public static int MOVEMENT = 10;
    int lives = 4;
    int screenSizeX;
    int screenSizeY;
    static int discountPercent = 5;
    int score = 0;
    Player player;
    ArrayList<Voucher> vouchers = new ArrayList<>();
    ArrayList<Bomb> bombs = new ArrayList<>();

    public GameView(Context context, int screenSizeX, int screenSizeY) {
        super(context);
        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;

        player = new Player(context, screenSizeX, screenSizeY);

        vouchers.add(new Voucher(context, screenSizeX, screenSizeY));

        new Thread(this).start();
    }

    @Override
    public void run() {
        while(lives > 0){
            update();
            draw();

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void draw() {

        if(getHolder().getSurface().isValid()){
            Canvas can = getHolder().lockCanvas();
            Paint paint = new Paint();


            can.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);

            for (Bomb bomb:bombs) {
                can.drawBitmap(bomb.skin, bomb.x, bomb.y, paint);
            }

            for (Voucher voucher:vouchers) {
                can.drawBitmap(voucher.skin, voucher.x, voucher.y, paint);
            }

            can.drawBitmap(player.skin, player.x, player.y, paint);

            paint.setTextSize(50);
            can.drawText("Score: " + score, screenSizeX - 300, 100, paint);
            can.drawText(discountPercent + " %", screenSizeX/2, 100, paint);

            for(int i = 1; i <= lives; i++){

                Bitmap live = BitmapFactory.decodeResource(getContext().getResources(),
                        R.drawable.live );

                can.drawBitmap(live,(50 + live.getWidth()) * i, 100, paint);
            }

            getHolder().unlockCanvasAndPost(can);

        }
    }

    private void update() {
        score += 1 * (discountPercent / 3);

        if(score / 5000 > vouchers.size() && vouchers.size() < 6){
            vouchers.add(new Voucher(getContext(), screenSizeX, screenSizeY));
        }

        if(score / 5000 > bombs.size() && bombs.size() < 3){
            bombs.add(new Bomb(getContext(), screenSizeX, screenSizeY));
        }

        for (Bomb bomb : bombs) {
            bomb.update();
            if(bomb.hitBox.intersect(player.hitBox)){
                bomb.reuseBomb();
                lives--;
            }
        }

        for (Voucher voucher: vouchers) {
            voucher.update();

            if(voucher.hitBox.intersect(player.hitBox)){
                discountPercent += voucher.value;
                if(discountPercent > 99){
                    discountPercent = 99;
                }else if(discountPercent < 0){
                    lives = 0;
                }
                voucher.reuseItem();
            }
        }
    }

    VelocityTracker vt;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()){

            case MotionEvent.ACTION_DOWN:
                if(vt == null){
                    vt = VelocityTracker.obtain();
                }else{
                    vt.clear();
                }
                vt.addMovement(event);
                break;
                case MotionEvent.ACTION_MOVE:
                    vt.addMovement(event);
                    vt.computeCurrentVelocity(1000);

                    int xMovement = 0;

                    if(vt.getXVelocity() > 0){
                        xMovement = MOVEMENT;
                    }else if(vt.getXVelocity() < 0){
                        xMovement = -MOVEMENT;
                    }

                    player.update(xMovement);

                    break;
        }
        return true;

    }
}
