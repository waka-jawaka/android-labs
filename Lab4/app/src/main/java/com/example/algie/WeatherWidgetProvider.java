package com.example.algie;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

public class WeatherWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        context.startService(new Intent(context, UpdateService.class));
    }

    public static class UpdateService extends Service {
    	
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            buildUpdate(this);
            return START_STICKY;
        }

        private static WeatherInfo parseResponse (String response) {
            WeatherInfo result = new WeatherInfo();
            try {
                XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
                parser.setInput(new StringReader(response));

                String tagName = null;

                int event = parser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT) {
                    tagName = parser.getName();
                    if (event == XmlPullParser.START_TAG) {
                        if (tagName.equals("yweather:wind")) {
                            result.wind = parser.getAttributeValue(3);
                        } else if (tagName.equals("yweather:atmosphere")) {
                            result.humidity = parser.getAttributeValue(1);
                            result.pressure = parser.getAttributeValue(2);
                        } else if (tagName.equals("yweather:condition")) {
                            result.temperature = parser.getAttributeValue(3);
                            result.sky = parser.getAttributeValue(4);
                        }
                    }
                    event = parser.next();
                }

            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            return result;
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        public void buildUpdate(Context context) {
            String uri = "https://query.yahooapis.com/v1/public/yql?q=select%20wind,atmosphere,item%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22kharkiv%2Cukraine%22)&format=xml";
            StringRequest req = new StringRequest(Request.Method.GET, uri,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            WeatherInfo info = parseResponse(s);
                            RemoteViews views = null;

                            views = new RemoteViews(UpdateService.this.getPackageName(), R.layout.example_appwidget);

                            Log.i("INFO", info.humidity);
                            views.setTextViewText(R.id.textViewTemp, info.temperature + (char) 0x00B0 + "F");
                            views.setTextViewText(R.id.textViewPressure, "Pressure: " + info.pressure + " in");
                            views.setTextViewText(R.id.textViewHumidity, "Humidity: " + info.humidity + "%");
                            views.setTextViewText(R.id.textViewWind, "Wind: " + info.wind + " mph");

                            ComponentName thisWidget = new ComponentName(UpdateService.this, WeatherWidgetProvider.class);
                            AppWidgetManager manager = AppWidgetManager.getInstance(UpdateService.this);
                            manager.updateAppWidget(thisWidget, views);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {}
            }
            );

            RequestQueue rq = Volley.newRequestQueue(UpdateService.this);
            rq.add(req);
        }
    }
}