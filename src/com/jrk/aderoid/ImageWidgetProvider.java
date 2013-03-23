package com.jrk.aderoid;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.preference.PreferenceManager;

@SuppressLint("NewApi")
public class ImageWidgetProvider extends AppWidgetProvider {

	SharedPreferences prefs;

	private PendingIntent service = null; 

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds)
	{
		// On récupère les préférences
		this.prefs = PreferenceManager.getDefaultSharedPreferences(context);

		// Création de l'alarme pour mettre à jour le widget
		final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		final Calendar TIME = Calendar.getInstance();
		TIME.set(Calendar.MINUTE, 0);
		TIME.set(Calendar.SECOND, 0);
		TIME.set(Calendar.MILLISECOND, 0);

		System.out.println("UPDATE");
		
		for(int wid : appWidgetIds)
		{
			// Création de l'intent du service
			final Intent i = new Intent(context, ImageWidgetService.class);
			// On passe l'id du widget
			i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[] { wid });

    		/*
    		 * Cette ligne de code permet de corriger un problème sur android
    		 * qui ne met à jour uniquement le dernier widget lorsque vous en ajoutez
    		 * plusieurs sur votre bureau. Ne le supprimez pas ;)
    		 */
			i.setData(Uri.withAppendedPath( Uri.parse("imgwidget://widget/id/"), String.valueOf(wid)));

			service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);  

			// Par défaut interval de 60 minutes
						long time = Long.valueOf(this.prefs.getString("widget"+wid+"time", "60"));

						m.setRepeating(AlarmManager.RTC, TIME.getTime().getTime(), time * 1000 * 60, service);
		}
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds)
	{
		
		
		System.out.println("DELETE");

		this.prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor prefeditor = this.prefs.edit();

		for(int wid : appWidgetIds)
		{
			// On supprime les préférences
			prefeditor.remove("widget"+wid+"time");

			// On récupère l'intent à supprimer
			final Intent i = new Intent(context, ImageWidgetService.class);
			i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[] { wid });
			i.setData(Uri.withAppendedPath( Uri.parse("imgwidget://widget/id/"), String.valueOf(wid)));

			service = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);

			final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

			// On supprime l'alarme
			m.cancel(service); 
		}

		prefeditor.commit();

		super.onDeleted(context, appWidgetIds);
	}
}