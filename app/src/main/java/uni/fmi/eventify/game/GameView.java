package uni.fmi.eventify.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable{
    int lives = 3;
    int screenSizeX;
    int screenSizeY;
    int discountPercent = 5;
    int score = 0;
    Player player;


    public GameView(Context context, int screenSizeX, int screenSizeY) {
        super(context);
        this.screenSizeX = screenSizeX;
        this.screenSizeY = screenSizeY;

        player = new Player(context, screenSizeX, screenSizeY);

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
            //paint.setColor(Color.BLACK);

            can.drawBitmap(player.skin, player.x, player.y, paint);


            getHolder().unlockCanvasAndPost(can);

        }
    }

    private void update() {
    }
}
