package com.vuadvocate.advocode2015;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class StartActivity extends ActionBarActivity {

    private ArrayList<String> FinalList;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private final String INFO = "info";
    private final String favcode = "fave";
    private final String reccode = "recent";
    private SharedPreferences prefs;
    private ArrayList<String> Faves;
    private ArrayList<String> Recents;
    HashMap<Integer, String> mMap = new HashMap<Integer, String>();
    private String[] clinicNamesArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setCategories();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences(INFO, MODE_PRIVATE);

        try {
            Faves = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString(favcode,
                    ObjectSerializer.serialize(new ArrayList<String>())));
            Recents = (ArrayList<String>) ObjectSerializer.deserialize(prefs.getString(reccode,
                    ObjectSerializer.serialize(new ArrayList<String>())));


        } catch (IOException e) {
            e.printStackTrace();
        }

        setupList();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.draw_list_item, FinalList));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    private void setupList(){
        FinalList = new ArrayList<String>();
        FinalList.add("--FAVORITES--");
        mMap.put(0,"Favorites");
        int counter = 1;
        int counter2 = 0;
        for(Iterator it = Faves.iterator();it.hasNext() && counter2<=3;) {
            String val = (String) it.next();
            FinalList.add(val);
            mMap.put(counter, val);
            counter++;
            counter2++;
        }
        FinalList.add("--RECENTS--");
        mMap.put(counter,"Recents");
        counter++;
        counter2 = 0;
        for(Iterator it = Recents.iterator();it.hasNext()&& counter2<=3;){
            String val2 = (String)it.next();
            FinalList.add(val2);
            mMap.put(counter,val2);
            counter++;
            counter2++;
        }
        FinalList.add("--OTHER RESOURCES--");
        mMap.put(counter,"Other");
        counter++;
        for(int i = 0;i<clinicNamesArray.length;i++){
            String val3 = clinicNamesArray[i];
            FinalList.add(val3);
            mMap.put(counter,val3);
            counter++;
        }
    }

    private void setCategories() {
        String[] cats = {"Adult Daycare", "Child/Domestic Violence", "Counseling, Behavior, Drug & Alcohol Recovery",
                "Dental Service", "Disability Services", "Family Services", "Food Resources"
                , "Government/Legal/Policies", "Hospice Care", "Housing and Utilities", "Immigrant Resources",
                "Job and Homeless Resources", "Medical Care Clinic", "Mental Health Clinic", "Other", "Support groups"};

        clinicNamesArray = cats;
    }

    private void selectItem(int position) {
        String val = mMap.get(position);
        Intent intent;
        intent = new Intent(this,MainActivity.class);
        Log.d("TAG","The Value is "+val);
        mDrawerLayout.closeDrawers();
        intent.putExtra("VAL",val);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}

