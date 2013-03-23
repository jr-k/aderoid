package com.jrk.aderoid;


import android.app.ListActivity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

public class DayViewActivity extends ListActivity {
	/** Called when the activity is first created. */
	private static int HOURS_PER_DAY = 14;
	private static int d = 0;

	Context mContext = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getListView().setBackgroundColor(Color.rgb(12, 12, 12));
		
		
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
	            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
	            "Linux", "OS/2" };

	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	            R.layout.list_item, values);
    setListAdapter(adapter);
		getListView().setDividerHeight(0);
		
		
		
		
		
	}
	
	public class DayListAdapter extends BaseAdapter implements ListAdapter {
	

		@Override
		public boolean areAllItemsEnabled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEnabled(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return HOURS_PER_DAY;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getItemViewType(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = getLayoutInflater();
			View listItem = (View) inflater.inflate(R.layout.list_item,getListView(), false);
			TextView hourTV = (TextView) listItem.findViewById(R.id.hourTV);
			hourTV.setTextColor(Color.BLUE);
			
			
			 final LinearLayout eventsLL = (LinearLayout) listItem.findViewById(R.id.eventsLL);
			
		
			 if ((position + 8)-d < 10)
				 hourTV.setText("0"+String.valueOf((position + 8)-d)+"h00");
			 else
				 hourTV.setText(String.valueOf((position + 8)-d)+"h00");

			 
			TextView A = new TextView(mContext);
			A.setText("PROJET\nSI01 LPDASI1\n16h00 - 18h00");
			A.setTextColor(Color.BLACK);
		
			 A.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
			 eventsLL.setBackgroundColor(Color.RED);
			 A.setPadding(50, 5, 50, 5);
			eventsLL.addView(A,0);

			eventsLL.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					TextView A = new TextView(mContext);
					A.setText("abc");
					A.setTextColor(Color.BLACK);
				
					 A.setLayoutParams(new ViewGroup.LayoutParams(
		                        ViewGroup.LayoutParams.WRAP_CONTENT,
		                        ViewGroup.LayoutParams.WRAP_CONTENT));
					eventsLL.addView(A,0);
				}

			});
			return listItem;
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 1;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void registerDataSetObserver(DataSetObserver arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver arg0) {
			// TODO Auto-generated method stub

		}

	}
	

	
	
	
	
}