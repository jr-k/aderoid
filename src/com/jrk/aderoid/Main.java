package com.jrk.aderoid;

import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Text;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.style.TextAppearanceSpan;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
	private static final String tag = "Main";
	private Button currentDay;
	private ImageView prevDay;
	private ImageView nextDay;
	private Calendar _calendar;
	private int month, year, day;
	private static String[] months = { "Jan", "Fév", "Mar", "Avr", "Mai",
			"Juin", "Juil", "Août", "Sept", "Oct", "Nov", "Déc" };

	private static String[] dayslab = new String[] { "NONE", "Dim", "Lun",
			"Mar", "Mer", "Jeu", "Ven", "Sam" };

	private static int HOURS_PER_DAY = 14;
	private static int d = 0;

	Context mContext = this;
	private ListView calendarView;
	private BaseAdapter adapter;

	static final int DATE_DIALOG_ID = 999;
	private DatePicker dpResult;

	private Spinner classes;
	private Spinner groups;

	private static String groups_selection = "1308";
	private static String classes_selection = "info";

	private int countselector = 0;

	private static String[] creneaux = new String[] { "08", "09", "10", "11",
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21" };
	private static ArrayList<Course> courses = new ArrayList();

	private Hashtable<String, String> classesTab = new Hashtable();
	private Hashtable<String, String> groupsTab = new Hashtable();
	private static HashMap<String, String> settings;
	private static HashMap<String, HashMap<String, String>> loadcourses;

	private static final int SWIPE_MIN_DISTANCE = 70;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			_calendar.set(year, month, day);

			refreshCalendar();

		}
	};

	public int getPositionFromKey(Spinner s, String key) {
		return ((KeyValueAdapter) s.getAdapter()).getPositionFromKey(key);
	}

	public String getSelectedKey(Spinner s) {
		return ((KeyValueAdapter) s.getAdapter()).getKey(s
				.getSelectedItemPosition());
	}

	public static void put(String key, String v) {
		settings.put(key, v);
		WriteSettings();
	}

	public class CustomOnItemSelectedListenerC implements
			OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {

			classes_selection = getSelectedKey(classes);
			Finals.populateGroups(classes_selection, groupsTab);
			groups.setAdapter(new KeyValueAdapter(mContext, groupsTab, 2));
			put("classes", classes_selection);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	public class CustomOnItemSelectedListenerG implements
			OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			countselector++;
			System.out.println("c" + countselector);
			if (countselector <= 2) {
				groups.setSelection(getPositionFromKey(groups,
						settings.get("groups")));
			} else {
				groups_selection = getSelectedKey(groups);
				System.out.println("selection : " + groups_selection);
				put("groups", groups_selection);
				refreshCalendar();
				if (isConnected(mContext)
						&& !loadcourses.containsKey(groups_selection))
					saveCourses();
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	public final static boolean isConnected(Context context) {
		final ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = connectivityManager
				.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ReadSettings();
		ReadCourses();

		
		
		System.out.println(settings);

		_calendar = Calendar.getInstance(Locale.getDefault());

		prevDay = (ImageView) this.findViewById(R.id.prevDay);
		prevDay.setOnClickListener(this);

		currentDay = (Button) this.findViewById(R.id.currentDay);
		currentDay.setText(dayslab[_calendar.get(Calendar.DAY_OF_WEEK)] + ". "
				+ getStringDate());

		nextDay = (ImageView) this.findViewById(R.id.nextDay);
		nextDay.setOnClickListener(this);

		calendarView = (ListView) this.findViewById(R.id.calendar);

		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};

		calendarView.setOnTouchListener(gestureListener);

		dpResult = new DatePicker(this.mContext);

		currentDay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(DATE_DIALOG_ID);
			}
		});

		Finals.populateClasses(classesTab);
		classes = (Spinner) this.findViewById(R.id.classes);
		classes.setOnItemSelectedListener(new CustomOnItemSelectedListenerC());
		classes.setAdapter(new KeyValueAdapter(mContext, classesTab, 1));

		Finals.populateGroups(((KeyValueAdapter) classes.getAdapter())
				.getKey(classes.getSelectedItemPosition()), groupsTab);
		groups = (Spinner) this.findViewById(R.id.groups);
		groups.setOnItemSelectedListener(new CustomOnItemSelectedListenerG());
		groups.setAdapter(new KeyValueAdapter(mContext, groupsTab, 2));

		classes_selection = settings.get("classes");
		groups_selection = settings.get("groups");

		classes.setSelection(getPositionFromKey(classes, classes_selection));
		groups.setSelection(getPositionFromKey(groups, groups_selection));

		
		getCourses(getStringDateUS(), _calendar.get(Calendar.WEEK_OF_YEAR) + "");
		// adapter = new GridCellAdapter(getApplicationContext(), R.id.gridcell,
		// month, year, day);
		adapter = new DayListAdapter(getApplicationContext());
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);

	}

	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					_calendar.add(Calendar.DAY_OF_MONTH, +1);
					refreshCalendar();
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					_calendar.add(Calendar.DAY_OF_MONTH, -1);
					refreshCalendar();
				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}

	}

	public String getStringDate() {

		month = _calendar.get(Calendar.MONTH);
		year = _calendar.get(Calendar.YEAR);
		day = _calendar.get(Calendar.DAY_OF_MONTH);
		return day + " " + months[month] + " " + year;

	}

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

	public static void createCourse(Course course, String str) {
		JSONParser parser = new JSONParser();

		try {
			parser.parse(str);
			Map json = (Map) parser.parse(str);
			Iterator iter = json.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();

				if (entry.getKey().equals("matiere")) {
					course.setMatiere(entry.getValue().toString());
				} else if (entry.getKey().equals("content")) {
					course.setContent(entry.getValue().toString());
				} else if (entry.getKey().equals("hdebut")) {
					course.setHdebut(entry.getValue().toString());
				} else if (entry.getKey().equals("hours")) {
					course.setHours(entry.getValue().toString());
				} else if (entry.getKey().equals("hfin")) {
					course.setHfin(entry.getValue().toString());
				} else if (entry.getKey().equals("week")) {
					course.setWeek(entry.getValue().toString());
				} else if (entry.getKey().equals("color")) {
					course.setColor1(entry.getValue().toString());
				}
			}

		} catch (ParseException pe) {
			System.out.println(pe);
		}

	}

	public static Course getCourseByCreneau(String cr) {
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getCreneau().equals(cr))
				return courses.get(i);
		}
		return null;
	}

	public void saveCourses() {

		System.out.println("all page : "
				+ "http://jps-prestashop.com/prod/ade/api.php?rsc="
				+ groups_selection + "&week=13&locale=1");
		
		
		// TODO Auto-generated method stub
		JHTTP object = new JHTTP();

		String page = (object
				.sendGET("http://jps-prestashop.com/prod/ade/api.php?rsc="
						+ groups_selection + "&week=13&locale=1"));

		object.disconnect();

		if (!page.equals("null")) {

			JSONParser parser = new JSONParser();

			try {
				parser.parse(page);
				Map json = (Map) parser.parse(page);
				Iterator iter = json.entrySet().iterator();
				HashMap<String, String> tmp = new HashMap();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String v = entry.getValue().toString();
					String k = entry.getKey().toString();

					tmp.put(k, v);

					loadcourses.put(groups_selection, tmp);

				}

			} catch (ParseException pe) {
				System.out.println(pe);
			}

			WriteCourses();
			System.out.println("Saving courses...");

		}
	}

	public static Course getWidgetContent(Context c, final String datecourse,
			final String week, String creneauargs) {

		ReadSettings();
		ReadCourses();

		
		if (loadcourses.containsKey(groups_selection)
				&& loadcourses.get(groups_selection).containsKey(datecourse)) {
			System.out.println("getCourses LOCALE");
			HashMap<String, String> today = loadcourses.get(groups_selection);
			JSONArray array = (JSONArray) JSONValue.parse("["
					+ today.get(datecourse) + "]");
			JSONObject base = (JSONObject) array.get(0);

			for (int i = 0; i < creneaux.length; i++) {
				String creneau = creneaux[i];
				Course course = new Course(creneau, datecourse);

				JSONObject hoursc = (JSONObject) base.get(creneau);
				String hourscs = "null";
				try {
					hourscs = hoursc.toString();
				} catch (Exception e) {

				}
				if (!hourscs.equals("null")) {
					createCourse(course, hourscs);

					if (Integer.parseInt(creneauargs) < Integer.parseInt(course
							.getCreneau()))
						return course;
				}
			}

		} else if (loadcourses.containsKey(groups_selection)
				&& !loadcourses.get(groups_selection).containsKey(datecourse)) {
			return null;
		}

		else if (isConnected(c)) {

			JHTTP object = new JHTTP();

			String page = (object
					.sendGET("http://jps-prestashop.com/prod/ade/api.php?rsc="
							+ groups_selection + "&week=" + week
							+ "&locale=0&day=" + datecourse));
			object.disconnect();

			System.out.println(datecourse);

			System.out.println("link: "
					+ "http://jps-prestashop.com/prod/ade/api.php?rsc="
					+ groups_selection + "&week=13&day=" + datecourse);

			System.out.println("getCourses REMOTE");

			if (!page.equals("null")) {

				JSONArray array = (JSONArray) JSONValue.parse("[" + page + "]");
				JSONObject base = (JSONObject) array.get(0);

				for (int i = 0; i < creneaux.length; i++) {
					String creneau = creneaux[i];
					Course course = new Course(creneau, datecourse);
					JSONObject hoursc = (JSONObject) base.get(creneau);
					String hourscs = "null";
					try {
						hourscs = hoursc.toString();
					} catch (Exception e) {

					}
					if (!hourscs.equals("null")) {
						createCourse(course, hourscs);

						if (Integer.parseInt(creneauargs) < Integer
								.parseInt(course.getCreneau()))
							return course;
					}
				}

			}
		} else {
			return null;
		}
		return null;

	}

	public void getCourses(final String datecourse, final String week) {

		courses = new ArrayList();

		if (loadcourses.containsKey(groups_selection)
				&& loadcourses.get(groups_selection).containsKey(datecourse)) {
			HashMap<String, String> today = loadcourses.get(groups_selection);
			JSONArray array = (JSONArray) JSONValue.parse("["
					+ today.get(datecourse) + "]");
			JSONObject base = (JSONObject) array.get(0);

			for (int i = 0; i < creneaux.length; i++) {
				String creneau = creneaux[i];
				Course course = new Course(creneau, datecourse);

				JSONObject hoursc = (JSONObject) base.get(creneau);
				String hourscs = "null";
				try {
					hourscs = hoursc.toString();
				} catch (Exception e) {

				}
				if (!hourscs.equals("null")) {
					createCourse(course, hourscs);
					courses.add(course);
				}
			}

			System.out.println("getCourses LOCALE");
		} else if (loadcourses.containsKey(groups_selection)
				&& !loadcourses.get(groups_selection).containsKey(datecourse)) {

		}

		else if (isConnected(mContext)) {

			
			
			if (!loadcourses.containsKey(groups_selection)) {
				saveCourses();
			}

			// TODO Auto-generated method stub
			JHTTP object = new JHTTP();

			String page = (object
					.sendGET("http://jps-prestashop.com/prod/ade/apitest.php?rsc="
							+ groups_selection
							+ "&week="
							+ week
							+ "&locale=0&day=" + datecourse));

			System.out.println(datecourse);

			System.out.println("link: "
					+ "http://jps-prestashop.com/prod/ade/apitest.php?rsc="
					+ groups_selection + "&week=" + week + "&locale=0&day="
					+ datecourse);

			System.out.println("PAGE ==== " + page);

			System.out.println("getCourses REMOTE");
			
			
			object.disconnect();

			if (!page.equals("null")) {

				JSONArray array = (JSONArray) JSONValue.parse("[" + page + "]");
				JSONObject base = (JSONObject) array.get(0);

				for (int i = 0; i < creneaux.length; i++) {
					String creneau = creneaux[i];
					Course course = new Course(creneau, datecourse);
					JSONObject hoursc = (JSONObject) base.get(creneau);
					String hourscs = "null";
					try {
						hourscs = hoursc.toString();
					} catch (Exception e) {

					}
					if (!hourscs.equals("null")) {
						createCourse(course, hourscs);
						courses.add(course);
					}
				}

				

			} else {
				System.out.println("getCourses NULL ");
				Toast.makeText(mContext,
						"Aucun cours ne peut être récupéré pour ce jour",
						Toast.LENGTH_LONG).show();
			}

		} else {
			System.out.println("getCourses NONE");
			Toast.makeText(
					mContext,
					"Aucun cours ne peut être récupéré pour ce jour (Connectez-vous au moins une fois pour récupérer les données)",
					Toast.LENGTH_LONG).show();
		}
	}

	public static int makeRGBA2(String hex) {

		String redhex = hex.substring(0, 2);
		int red = Integer.parseInt(redhex, 16);

		String greenhex = hex.substring(2, 4);
		int green = Integer.parseInt(greenhex, 16);

		String bluehex = hex.substring(4, 6);
		int blue = Integer.parseInt(bluehex, 16);

		int alpha = 255;

		return ((blue & 0xFF) + ((green & 0xFF) << 8) + ((red & 0xFF) << 16))
				+ ((alpha & 0xFF) << 24);

	}

	@Override
	public void onClick(View v) {
		if (v == prevDay) {
			_calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		if (v == nextDay) {
			_calendar.add(Calendar.DAY_OF_MONTH, +1);
		}

		refreshCalendar();
	}

	public void refreshCalendar() {
		String labeldate = getStringDate();
		getCourses(getStringDateUS(), _calendar.get(Calendar.WEEK_OF_YEAR) + "");

		// adapter = new GridCellAdapter(getApplicationContext(), R.id.gridcell,
		// month, year, day);
		adapter = new DayListAdapter(getApplicationContext());
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
		currentDay.setText(dayslab[_calendar.get(Calendar.DAY_OF_WEEK)] + ". "
				+ labeldate);
		dpResult.init(year, month, day, null);
	}

	public class DayListAdapter extends BaseAdapter implements ListAdapter {

		private static final String tag = "DayListAdapter";
		private final Context _context;
		private final List<String> list;

		// Days in Current Month
		public DayListAdapter(Context context) {
			super();
			this._context = context;
			this.list = new ArrayList<String>();

		}

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
			View listItem = (View) inflater.inflate(R.layout.list_item, arg2,
					false);
			TextView hourTV = (TextView) listItem.findViewById(R.id.hourTV);
			hourTV.setTextColor(Color.WHITE);

			arg2.setBackgroundColor(makeRGBA2("222222"));

			final LinearLayout eventsLL = (LinearLayout) listItem
					.findViewById(R.id.eventsLL);

			String pref = String.valueOf((position + 8) - d);

			if ((position + 8) - d < 10)
				pref = "0" + pref;

			hourTV.setText(pref + "h00");

			Course course = getCourseByCreneau(pref);

			if (course != null) {

				LinearLayout mylayout = new LinearLayout(mContext);

				mylayout.setOrientation(LinearLayout.VERTICAL);

				TextView A = new TextView(mContext);
				A.setText(course.getMatiere());
				A.setTextColor(Color.BLACK);

				A.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
				A.setPadding(20, 5, 20, 0);
				A.setTextSize(12F);

				TextView B = new TextView(mContext);
				B.setText(course.getContent());
				B.setTextColor(Color.DKGRAY);
				B.setTextSize(12F);

				B.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
				B.setPadding(20, 0, 20, 0);

				TextView C = new TextView(mContext);
				C.setText(course.getHdebut() + " - " + course.getHfin());
				C.setTextColor(Color.DKGRAY);
				C.setTextSize(10F);

				C.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
				C.setPadding(20, 0, 20, 5);

				mylayout.addView(A);
				mylayout.addView(B);
				mylayout.addView(C);

				GradientDrawable gd = new GradientDrawable(
						GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
								makeRGBA2(course.getColor1()),
								makeRGBA2(course.getColor1()) });
				gd.setCornerRadius(5f);

				eventsLL.setBackgroundDrawable(gd);

				eventsLL.addView(mylayout, 0);
			}

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

	public boolean onCreateOptionsMenu(Menu menu) {

		final MenuItem widget = menu.add("Rafraichir Widget");
		widget.setIcon(R.drawable.zzajoutelist);
		widget.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem arg0) {

				put("classes", classes_selection);
				put("groups", groups_selection);

				return false;
			}
		});

		final MenuItem addlist = menu.add("Rafraichir Ade");
		addlist.setIcon(R.drawable.zzajoutelist);
		addlist.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem arg0) {

				if (isConnected(mContext)) {
					saveCourses();
					getCourses(getStringDateUS(),
							_calendar.get(Calendar.WEEK_OF_YEAR) + "");
				} else {
					Toast.makeText(mContext,
							"Veuillez vous connecter à Internet",
							Toast.LENGTH_LONG).show();
				}
				return false;
			}
		});

		MenuItem apropos = menu.add("Contact");
		apropos.setIcon(R.drawable.zcontact);
		apropos.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem arg0) {
				AlertDialog.Builder adb_suppr = new AlertDialog.Builder(
						Main.this);
				adb_suppr.setTitle("Contact");
				adb_suppr.setMessage("jessym.reziga@gmail.com");
				adb_suppr.setCancelable(true);
				adb_suppr.setPositiveButton("OK", null);
				adb_suppr.show();
				return false;
			}
		});

		MenuItem quitter = menu.add("Quitter");
		quitter.setIcon(R.drawable.quitter);
		quitter.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem arg0) {
				finish();
				return false;
			}
		});

		return true;
	}

	public static void ReadSettings() {
		ObjectInputStream ois = null;
		try {
			File file = new File(Environment.getExternalStorageDirectory()
					+ "/aderoid.sys");
			// file.delete();
			if (!file.exists()) {
				settings = new HashMap();
				put("classes", classes_selection);
				put("groups", groups_selection);
				WriteSettings();
			} else {
				ois = new ObjectInputStream(new FileInputStream(file));
				settings = (HashMap) ois.readObject();
			}
			ois.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void WriteSettings() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(
					new FileOutputStream(new File(Environment
							.getExternalStorageDirectory() + "/aderoid.sys")));
			oos.writeObject(settings);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void ReadCourses() {
		ObjectInputStream ois = null;
		try {
			File file = new File(Environment.getExternalStorageDirectory()
					+ "/aderoidc.sys");
			// file.delete();
			if (!file.exists()) {
				loadcourses = new HashMap();
				WriteCourses();
			} else {
				ois = new ObjectInputStream(new FileInputStream(file));
				loadcourses = (HashMap) ois.readObject();
			}
			ois.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static void WriteCourses() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(
					new File(Environment.getExternalStorageDirectory()
							+ "/aderoidc.sys")));
			oos.writeObject(loadcourses);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}