package com.vuadvocate.advocode2015;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity {

    private final int blue = 0Xff5b88f0;
    private final int gray = 0XFF63706c;

    private Button mButtonMed;
    private Button mButtonAbout;
    private Button mButtonRes;
    private Button mButtonFav;

    private ListView listView;
    private ListView listView2;

    private MedicationFragment mMedicationFragment;
    private ResourceFragment mResourceFragment;
    private AboutUsFragment mAboutUsFragment;
    private FavoriteFragment mFavoriteFragment;
    private ResourceFragment2 mResourceFragment2;
    private InfoFragment mInfoFragment;
    private SearchMedFragment msearchMedFragment;
    private SearchResFragment msearchResFragment;

    private String[] clinicNamesArray;
    private String selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonMed = (Button) findViewById(R.id.btn_Med);
        mButtonAbout = (Button) findViewById(R.id.btn_About);
        mButtonRes = (Button) findViewById(R.id.btn_Res);
        mButtonFav = (Button) findViewById(R.id.btn_Favorites);

        Runnable setUpFrags = new Runnable() {
            public void run() {
                mMedicationFragment = new MedicationFragment();
                mResourceFragment = new ResourceFragment();
                mAboutUsFragment = new AboutUsFragment();
                mFavoriteFragment = new FavoriteFragment();


                openRes(null);
            }
        };

        new Thread(setUpFrags).start();
    }

    public void openRes(View v) {
        Runnable res = new Runnable() {
            public void run() {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.topSection, mResourceFragment);
                transaction.commit();

                runOnUiThread(new Runnable() {
                    public void run() {
                        resSelected();
                        getCategories();
                    }
                });
            }
        };
        new Thread(res).start();
    }

    public void openMed(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.topSection, mMedicationFragment);
        transaction.commit();
        medSelected();
    }

    public void openAbout(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.topSection, mAboutUsFragment);
        transaction.commit();
        aboutSelected();
    }

    public void openFavorites(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.topSection, mFavoriteFragment);
        transaction.commit();
        favoritesSelected();
    }

    private void medSelected() {
        mButtonMed.setBackgroundColor(blue);
        mButtonRes.setBackgroundColor(gray);
        mButtonAbout.setBackgroundColor(gray);
        mButtonFav.setBackgroundColor(gray);
    }

    private void resSelected() {
        mButtonMed.setBackgroundColor(gray);
        mButtonRes.setBackgroundColor(blue);
        mButtonAbout.setBackgroundColor(gray);
        mButtonFav.setBackgroundColor(gray);
    }

    private void aboutSelected() {
        mButtonMed.setBackgroundColor(gray);
        mButtonRes.setBackgroundColor(gray);
        mButtonAbout.setBackgroundColor(blue);
        mButtonFav.setBackgroundColor(gray);
    }

    private void favoritesSelected() {
        mButtonMed.setBackgroundColor(gray);
        mButtonRes.setBackgroundColor(gray);
        mButtonAbout.setBackgroundColor(gray);
        mButtonFav.setBackgroundColor(blue);
    }

    public void mapIt(View v) {
        mInfoFragment.mapIt();
    }

    public void sendIt(View v) {
        mInfoFragment.sendIt();
    }

    public void saveIt(View v) {
        mInfoFragment.saveIt();
    }

    public void rateIt(View v) {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            this.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {

        }
    }

    public void goResAdvanced(View v) {
        msearchResFragment = new SearchResFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.topSection, msearchResFragment);
        transaction.commit();
    }

    public void goMedAdvanced(View v) {
        msearchMedFragment = new SearchMedFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.topSection, msearchMedFragment);
        transaction.commit();
    }

    private void getCategories() {
        String[] cats = {"Adult Daycare", "Child/Domestic Violence", "Counseling, Behavior, Drug & Alcohol Recovery",
                "Dental Service", "Disability Services", "Family Services", "Food Resources"
                , "Government/Legal/Policies", "Hospice Care", "Housing and Utilities", "Immigrant Resources",
                "Job and Homeless Resources", "Medical Care Clinic", "Mental Health Clinic", "Other", "Support groups"};

        clinicNamesArray = cats;
        registerClickCallback();
    }

    public void getList() {

        //Search by category
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ResourceInfo");
        query.whereEqualTo("serviceType", selection);
        query.addAscendingOrder("serviceType");

        List<ParseObject> scoreList = null;
        try {
            scoreList = query.find();
        } catch (Exception e) {
        }

        int listSize = scoreList.size();
        clinicNamesArray = new String[listSize];

        for (int i = 0; i < listSize; i++) {
            ParseObject temp = scoreList.get(i);
            clinicNamesArray[i] = temp.getString("clinicalResourceName");
        }

    }

    private void registerClickCallback() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.da_item, clinicNamesArray);
        listView = (ListView) findViewById(R.id.listViewRes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> paret, View ViewClicked, int position, long id) {
                TextView textView = (TextView) ViewClicked;
                selection = textView.getText().toString();

                mResourceFragment2 = new ResourceFragment2();
                Runnable res2 = new Runnable() {
                    public void run() {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.topSection, mResourceFragment2);
                        transaction.commit();

                        runOnUiThread(new Runnable() {
                            public void run() {
                                getList();
                                registerClickCallback2();
                            }
                        });
                    }
                };

                new Thread(res2).start();
            }
        });

    }

    private void registerClickCallback2() {

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.itemitem, clinicNamesArray);
        listView2 = (ListView) findViewById(R.id.listViewRes2);
        listView2.setAdapter(adapter2);

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> paret, View ViewClicked, int position, long id) {
                TextView textView = (TextView) ViewClicked;
                selection = textView.getText().toString();

                mInfoFragment = new InfoFragment();
                mInfoFragment.setArguments(getQueryInfo());

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.topSection, mInfoFragment);
                transaction.commit();
            }
        });
    }

    private Bundle getQueryInfo() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ResourceInfo");
        query.whereEqualTo("clinicalResourceName", selection);
        ParseObject mobject = null;
        try {
            mobject = query.getFirst();
        } catch (Exception e) {
        }

        String address, phoneNumber, website, busRoute, email, category;

        if (!mobject.has("address")) {
            address = "Not Available";
        } else {
            address = mobject.getString("address");
        }

        if (!mobject.has("phoneNum1")) {
            phoneNumber = "Not Available";
        } else {
            phoneNumber = mobject.getString("phoneNum1");
        }

        if (!mobject.has("siteOnBusRoute")) {
            busRoute = "Bus Not Available";
        } else {
            busRoute = mobject.getString("busRoute");
        }

        if (!mobject.has("website")) {
            website = "Website Not Available";
        } else {
            website = mobject.getString("website");
        }

        if (!mobject.has("serviceType")) {
            category = "Not Available";
        } else {
            category = mobject.getString("serviceType");
        }

        if (!mobject.has("email")) {
            email = "Email Not Available";
        } else {
            email = mobject.getString("email");
        }

        String address2 = " ";
        if (mobject.has("city")) {
            address2 += mobject.getString("city");
        }
        if (mobject.has("state")) {
            address2 = address2 + ", " + mobject.getString("state");
        }
        if (mobject.has("zipCode")) {
            address2 = address2 + ", " + mobject.getString("zipCode");
        }
        if (address2 == "") {
            address2 = "Not Available";
        }

        String Mon = "Mon";
        String Tue = "Tue";
        String Wed = "Wed";
        String Thu = "Thu";
        String Fri = "Fri";
        String Sat = "Sat";
        String Sun = "Sun";

        if (mobject.has("monday")) {
            String MonOpen = mobject.getString("monday");
            if (MonOpen == "Closed") {
                Mon += "Closed";
            } else {
                Mon += mobject.getString("mondayOpenTime");
                Mon = Mon + " " + mobject.getString("mondayCloseTime");
            }
        }


        if (mobject.has("tuesday")) {
            String MonOpen = mobject.getString("tuesday");
            if (MonOpen == "Closed") {
                Tue += "Closed";
            } else {
                Tue += mobject.getString("tuesdayOpenTime");
                Tue = Tue + " " + mobject.getString("tuesdayCloseTime");
            }
        }

        if (mobject.has("wednesday")) {
            String MonOpen = mobject.getString("wednesdayday");
            if (MonOpen == "Closed") {
                Wed += "Closed";
            } else {
                Wed += mobject.getString("wednesdaydayOpenTime");
                Wed = Wed + " " + mobject.getString("wednesdaydayCloseTime");
            }
        }

        if (mobject.has("thursday")) {
            String MonOpen = mobject.getString("thursday");
            if (MonOpen == "Closed") {
                Thu += "Closed";
            } else {
                Thu += mobject.getString("thursdayOpenTime");
                Thu = Thu + " " + mobject.getString("thursdayCloseTime");
            }
        }

        if (mobject.has("friday")) {
            String MonOpen = mobject.getString("friday");
            if (MonOpen == "Closed") {
                Fri += "Closed";
            } else {
                Fri += mobject.getString("fridayOpenTime");
                Fri = Fri + " " + mobject.getString("fridayCloseTime");
            }
        }

        if (mobject.has("saturday")) {
            String MonOpen = mobject.getString("saturday");
            if (MonOpen == "Closed") {
                Sat += "Closed";
            } else {
                Sat += mobject.getString("saturdayOpenTime");
                Sat = Sat + " " + mobject.getString("saturdayCloseTime");
            }
        }

        if (mobject.has("sunday")) {
            String MonOpen = mobject.getString("sunday");
            if (MonOpen == "Closed") {
                Sun += "Closed";
            } else {
                Sun += mobject.getString("sundayOpenTime");
                Sun = Sun + " " + mobject.getString("sundayCloseTime");
            }
        }

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("feedbackObj");
        query1.whereEqualTo("Name", selection);
        ParseObject mobject2 = null;
        double rating = 0.0;
        try {
            mobject2 = query1.getFirst();

            rating = mobject2.getDouble("Rating");

        } catch (Exception e) {
        }


        Bundle ret = new Bundle();
        ret.putString("name", selection);
        ret.putString("address", address);
        ret.putString("address2", address2);
        ret.putString("phoneNumber", phoneNumber);
        ret.putString("website", website);
        ret.putString("busRoute", busRoute);
        ret.putString("email", email);
        ret.putString("category", category);
        ret.putString("Mon", Mon);
        ret.putString("Tue", Tue);
        ret.putString("Wed", Wed);
        ret.putString("Thu", Thu);
        ret.putString("Fri", Fri);
        ret.putString("Sat", Sat);
        ret.putString("Sun", Sun);
        ret.putDouble("Rating", rating);

        return ret;
    }

    public void submitIt(View v) {
        mInfoFragment.submitIt();
    }
/*
    public void doThisResSearch() {
        EditText key1 = (EditText) findViewById(R.id.Reskeyword1);
        String keyFirst = key1.getText().toString();

        EditText key2 = (EditText) findViewById(R.id.Reskeyword2);
        String keySecond = key2.getText().toString();

        EditText key3 = (EditText) findViewById(R.id.Reskeyword3);
        String keyThird = key2.getText().toString();

        EditText rate = (EditText) findViewById(R.id.ResratingInput);
        double minRating = Double.parseDouble(rate.getText().toString());

        EditText pName = (EditText) findViewById(R.id.ReskeywordSearch);
        String partialName = pName.getText().toString();

        boolean isChecked = ((CheckBox) findViewById(R.id.RescheckBox)).isChecked();

        String[] queries = {keyFirst, keySecond, keyThird, partialName};

        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("ResourceInfo");
        query1.whereContainedIn("clinicalResourceName", Arrays.asList(queries));

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ResourceInfo");
        query2.whereContainedIn("serviceType", Arrays.asList(queries));

        ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ResourceInfo");
        query3.whereContainedIn("serviceTypeOtherInfo", Arrays.asList(queries));

        ParseQuery<ParseObject> query4 = ParseQuery.getQuery("ResourceInfo");
        query4.whereContainedIn("address", Arrays.asList(queries));

        ParseQuery<ParseObject> query5 = ParseQuery.getQuery("ResourceInfo");
        query5.whereContainedIn("city", Arrays.asList(queries));

        ParseQuery<ParseObject> query6 = ParseQuery.getQuery("ResourceInfo");
        query6.whereContainedIn("servicesProvided", Arrays.asList(queries));

        ParseQuery<ParseObject> query7 = ParseQuery.getQuery("ResourceInfo");
        query7.whereContainedIn("otherTags", Arrays.asList(queries));

        ParseQuery<ParseObject> query8 = ParseQuery.getQuery("ResourceInfo");
        query8.whereContainedIn("otherNotes", Arrays.asList(queries));

        ParseQuery<ParseObject> query9;

        ArrayList<ParseQuery> queriesList = new ArrayList<ParseQuery>();
        queriesList.add(query1);
        queriesList.add(query2);
        queriesList.add(query3);
        queriesList.add(query4);
        queriesList.add(query5);
        queriesList.add(query6);
        queriesList.add(query7);
        queriesList.add(query8);

        ParseQuery mainQuery = ParseQuery.or(queriesList);

        List<ParseObject> scoreList = null;
        try {
            scoreList = mainQuery.find();
        } catch (Exception e) {
        }

        int listSize = scoreList.size();
        String[] QueriedArray = new String[listSize];

        for (int i = 0; i < listSize; i++) {
            ParseObject temp = scoreList.get(i);
            QueriedArray[i] = temp.getString("clinicalResourceName");

        }

    }
*/
}
