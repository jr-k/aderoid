package com.jrk.aderoid;

import java.util.Enumeration;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;



public class KeyValueAdapter extends ArrayAdapter<String> 
    implements SpinnerAdapter
{

    private static String TAG = "KeyValueAdapter";

    private final Context _context;
    private final Hashtable<String, String> _data;
    private final String[] _keys;
    private int type;

    public KeyValueAdapter(Context context, Hashtable<String, String> objects, int t)
    {
    	
        super(context, android.R.layout.simple_spinner_item);         
        
        _context = context;
        _data = objects;
        type = t;
        
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  

        //get positions
        int i = 0;
        _keys = new String[_data.size()];

        for (final Enumeration<String> e = _data.keys(); e.hasMoreElements();)
        {
            _keys[i++] = e.nextElement().toString();
        }
    }


    public int getCount()
    {
        return _data.size();
    }


    public int getPositionFromKey(String searchKey)
    {
        for (int i = 0; i < _keys.length; i++)
        {
            if (_keys[i].equals(searchKey))
                return i;
        }
        return -1;
    }


    public String getItem(int position)
    {
        return _data.get(_keys[position]);
    }

    public String getKey(int position)
    {
        return _keys[position].toString();
    }


    public long getItemId(int position)
    {
        /*
        * I happened to be using long keys so I modified this function. you can leave it at:
        *  return position;
        */
        if (position >= _keys.length || position < 0)
        {
            return -1;
        }

        return position; 
    }


    public View getView(int position, View view, ViewGroup parent)
    {

        final TextView text = new TextView(_context);
        text.setTextColor(Color.BLACK);
        
        
        String out = (_data.get(_keys[position]));
        
        if ( out.length() > 6 && type == 2)
        	out = out.substring(0,6)+"...";
        
        text.setText(out);

        return text;
    }

}

