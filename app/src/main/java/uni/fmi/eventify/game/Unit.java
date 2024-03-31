package uni.fmi.eventify.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class Unit {
    public Context context;
    public int maxX;
    public int maxY;
    public int x;
    public int y;
    public Bitmap skin;
    public Rect hitBox;//Collision Detection rectangle
}
