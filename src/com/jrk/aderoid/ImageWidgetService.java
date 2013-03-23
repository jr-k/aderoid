package com.jrk.aderoid;

import java.util.Calendar;
import java.util.Locale;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

@SuppressLint("NewApi")
public class ImageWidgetService extends Service {

	AppWidgetManager appWidgetManager;

	protected Handler handler = new Handler();

	SharedPreferences prefs;
	
	protected Calendar _calendar;

	
	public String getStringDateUS() {

		String month = _calendar.get(Calendar.MONTH) + 1 + "";
		String year = _calendar.get(Calendar.YEAR) + "";
		String day = _calendar.get(Calendar.DAY_OF_MONTH) + "";

		if (month.length() <= 1)
			month = "0" + month;

		if (day.length() <= 1)
			day = "0" + day;

		return year + "-" + month + "-" + day;

	}
	
	@Override
	public void onStart(Intent intent, int startId)
	{
		
		
		System.out.println("SERVICE START");

		
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

		this.appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());

		int[] allWidgetIds = intent
				.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

		for(int widgetId : allWidgetIds)
		{
			if(widgetId == 0)
				continue;

			RemoteViews remoteViews = new RemoteViews(this
					.getApplicationContext().getPackageName(),
					R.layout.widget);
			
			
			_calendar = Calendar.getInstance(Locale.getDefault());
			
			String HOURS = _calendar.get(Calendar.HOUR_OF_DAY)+"".length() <= 1 ? "0"+_calendar.get(Calendar.HOUR_OF_DAY) : _calendar.get(Calendar.HOUR_OF_DAY)+"";
			
			Course course = Main.getWidgetContent(this.getApplicationContext(),
					getStringDateUS(),_calendar.get(Calendar.WEEK_OF_YEAR)+"",HOURS);

			
			
			
			
			// Chargement
			if (course != null){
				
				String matiere = course.getMatiere().length() > 13 ? course.getMatiere().substring(0,13)+"..." : course.getMatiere() ;
				
				remoteViews.setTextViewText(R.id.widget_text, matiere);
				
				remoteViews.setTextViewText(R.id.widget_text_2, course.getContent() );
				
				remoteViews.setTextViewText(R.id.widget_text_3, course.getHdebut() + " - " + course.getHfin());
			}
			else {
				remoteViews.setTextViewText(R.id.widget_text, "Aucun cours disponible");
				remoteViews.setTextViewText(R.id.widget_text_3, "");
				remoteViews.setTextViewText(R.id.widget_text_2, "");
			}
			this.appWidgetManager.updateAppWidget(widgetId, remoteViews);

			// Clique sur l'image
			Intent clickIntentRefresh = new Intent(this.getApplicationContext(),
					ImageWidgetProvider.class);
			clickIntentRefresh.setData(Uri.withAppendedPath(Uri.parse("imgwidget://widget/id/"), String.valueOf(widgetId)));

			clickIntentRefresh.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntentRefresh.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
					new int[] { widgetId });

			PendingIntent pendingIntentRefresh = PendingIntent.getBroadcast(
					getApplicationContext(), 0, clickIntentRefresh,
					PendingIntent.FLAG_UPDATE_CURRENT);

			remoteViews.setOnClickPendingIntent(R.id.widget_text, pendingIntentRefresh);

			// Notre image aléatoire
			load(remoteViews, widgetId);
		}

		stopSelf();

		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void load(RemoteViews remoteViews, int widgetIdl)
	{
		
		System.out.println("load widget");
		_calendar = Calendar.getInstance(Locale.getDefault());
		
		String HOURS = _calendar.get(Calendar.HOUR_OF_DAY)+"".length() <= 1 ? "0"+_calendar.get(Calendar.HOUR_OF_DAY) : _calendar.get(Calendar.HOUR_OF_DAY)+"";
		
		Course course = Main.getWidgetContent(this.getApplicationContext(),
				getStringDateUS(),_calendar.get(Calendar.WEEK_OF_YEAR)+"",HOURS);

		
		
		
		
		// Chargement
		if (course != null){
			
			String matiere = course.getMatiere().length() > 13 ? course.getMatiere().substring(0,13)+"..." : course.getMatiere() ;

			remoteViews.setTextViewText(R.id.widget_text, matiere );
			
			remoteViews.setTextViewText(R.id.widget_text_2, course.getContent() );
			
			remoteViews.setTextViewText(R.id.widget_text_3, course.getHdebut() + " - " + course.getHfin());
		}
		else {
			remoteViews.setTextViewText(R.id.widget_text, "Aucun cours disponible");
			remoteViews.setTextViewText(R.id.widget_text_3, "");
			remoteViews.setTextViewText(R.id.widget_text_2, "");
		}
		this.appWidgetManager.updateAppWidget(widgetIdl, remoteViews);
		
	}


}