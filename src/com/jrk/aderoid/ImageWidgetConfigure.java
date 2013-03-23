package com.jrk.aderoid;


import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.preference.PreferenceActivity;

@SuppressLint("NewApi")
public class ImageWidgetConfigure extends PreferenceActivity {
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    ListPreference listeTimePref;

    SharedPreferences prefs;

    public ImageWidgetConfigure() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setResult(RESULT_CANCELED);

		addPreferencesFromResource(R.xml.image_widget_preferences);

		this.listeTimePref = (ListPreference) findPreference("widget_time");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // Si l'id du widget == 0
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
 
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

		// Préférences par défault
		updateWidgetDefaultPrefs();
    }

	private void updateWidgetPrefs()
	{
		Editor prefeditor = this.prefs.edit();

        // Time
        prefeditor.putString("widget"+mAppWidgetId+"time", this.listeTimePref.getValue());

        // Set default values
        prefeditor.putString("widget_time", "");

        prefeditor.commit();
	}

	/*
	 * Valeur par défaut
	 */
	private void updateWidgetDefaultPrefs()
	{
		if(this.prefs.contains("widget"+mAppWidgetId+"time"))
			this.listeTimePref.setValue(this.prefs.getString("widget"+mAppWidgetId+"time", ""));
		else
			this.listeTimePref.setValue("60");
	}

	/*
	 * Mise à jour du widget
	 */
    private void confirm()
    {
		Intent resultValue = new Intent();
		resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
		setResult(RESULT_OK, resultValue);
		finish();

        new ImageWidgetProvider()
        .onUpdate(this,
                  AppWidgetManager.getInstance(this),
                  new int[] { mAppWidgetId }
         );
    }

    @Override
    public void onBackPressed()
    {
    	this.updateWidgetPrefs();

    	this.confirm();

    	super.onBackPressed();
    }
    
    @Override
    public void onPause() {
    	this.updateWidgetPrefs();
    	this.confirm();
    	super.onPause();
    }
    
    public void onDestroy() {
    	this.updateWidgetPrefs();
    	this.confirm();
    	super.onDestroy();
    
    }
    
  
}