package org.c99.pebblecanvascalendarplugin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.pennas.pebblecanvas.plugin.PebbleCanvasPlugin;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by sam on 9/30/13.
 */
public class CalendarPlugin extends PebbleCanvasPlugin {
    private static final int HEIGHT = 20;

    @Override
    protected ArrayList<PluginDefinition> get_plugin_definitions(Context context) {
        ArrayList<PluginDefinition> plugins = new ArrayList<PluginDefinition>();

        ImagePluginDefinition p = new ImagePluginDefinition();
        p.id = 1;
        p.name = "1 Line Calendar";
        plugins.add(p);

        p = new ImagePluginDefinition();
        p.id = 2;
        p.name = "2 Line Calendar";
        plugins.add(p);

        p = new ImagePluginDefinition();
        p.id = 3;
        p.name = "3 Line Calendar";
        plugins.add(p);

        return plugins;
    }

    @Override
    protected String get_format_mask_value(int def_id, String format_mask, Context context, String param) {
        return null;
    }

    @Override
    protected Bitmap get_bitmap_value(int def_id, Context context, String param) {
        String days[] = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(15);
        paint.setAntiAlias(false);
        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), "RobotoCondensed-Bold.ttf"));
        paint.setTextAlign(Paint.Align.CENTER);

        Bitmap b = Bitmap.createBitmap(144, HEIGHT * (def_id + 1), Bitmap.Config.ARGB_8888);
        int w = b.getWidth() / 7;
        Canvas c = new Canvas(b);
        c.drawColor(Color.WHITE);
        for(int x = 0; x < 7; x++) {
            c.drawText(days[x], x * w + (w/2), HEIGHT - 6, paint);
        }
        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), "RobotoCondensed-Regular.ttf"));
        c.translate(0,HEIGHT);

        Calendar cal = Calendar.getInstance();
        int today = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        cal.add(Calendar.DAY_OF_MONTH, -dayOfWeek);

        for(int y = 0; y < def_id; y++) {
            for(int x = 0; x < 7; x++) {
                c.save();
                c.clipRect(x*w + 1, y*HEIGHT, (x+1)*w, (y+1)*HEIGHT - 1);
                if(cal.get(Calendar.DAY_OF_MONTH) == today) {
                    c.drawColor(Color.BLACK);
                    paint.setColor(Color.WHITE);
                } else {
                    c.drawColor(Color.WHITE);
                    paint.setColor(Color.BLACK);
                }
                String text = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
                c.drawText(text, x*w + (w/2), b.getHeight()-(HEIGHT * (def_id-y)) - 6, paint);
                c.restore();
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        return b;
    }
}
