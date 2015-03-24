package com.vuadvocate.advocode2015;

import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.telephony.SmsManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.text.ParseException;

public class InfoFragment extends Fragment {


    private String name;
    private String address;
    private String address2;
    private String phoneNumber;
    private String category;
    private String website;
    private String busRoute;
    private String email;
    private String Mon;
    private String Tues;
    private String Wed;
    private String Thur;
    private String Fri;
    private String Sat;
    private String Sun;
    private Double mRating;

    private TextView mName;
    private TextView mAddress;
    private TextView mAddress2;
    private TextView mPhoneNumber;
    private TextView mWebsite;
    private TextView mBusroute;
    private TextView mEmail;
    private TextView mcategory;
    private EditText mEditText;
    private RatingBar bar;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_info, container, false);
        mName = (TextView) v.findViewById(R.id.name);
        mAddress = (TextView) v.findViewById(R.id.Address1);
        mAddress2 = (TextView) v.findViewById(R.id.Address2);
        mEmail = (TextView) v.findViewById (R.id.emailaddress);
        mPhoneNumber = (TextView) v.findViewById(R.id.phonenumber);
        mBusroute = (TextView) v.findViewById(R.id.bus);
        mWebsite = (TextView) v.findViewById(R.id.website);
        mcategory = (TextView) v.findViewById(R.id.category);

        mEditText = (EditText) v.findViewById(R.id.textNumber);
        Bundle bundle = getArguments();
        double rating;
        name = bundle.getString("name");
        address = bundle.getString("address");
        address2 = bundle.getString("address2");
        phoneNumber = bundle.getString("phoneNumber");
        website = bundle.getString("website");
        busRoute = bundle.getString("busRoute");
        email = bundle.getString("email");
        mRating = bundle.getDouble("Rating");
        category = bundle.getString("category");

        Mon = bundle.getString("Mon");
        Tues = bundle.getString("Tue");
        Wed = bundle.getString("Wed");
        Thur = bundle.getString("Thu");
        Fri = bundle.getString("Fri");
        Sat = bundle.getString("Sat");
        Sun = bundle.getString("Sun");

        mName.setText(name);
        mAddress.setText(address);
        mAddress2.setText(address2);
        mEmail.setText(email);
        mPhoneNumber.setText(phoneNumber);
        mBusroute.setText(busRoute);
        mWebsite.setText(website);
        mcategory.setText(category);

        Spinner hours= (Spinner) v.findViewById(R.id.hoursSpinner);
        String[] days= {Mon, Tues, Wed, Thur, Fri, Sat, Sun};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, days);
        hours.setAdapter(adapter);

        // Inflate the layout for this fragment
        rating = mRating;
        bar = (RatingBar) v.findViewById(R.id.ratingbar);
        Button mButton = (Button) v.findViewById(R.id.btn_Submit);
        bar.setRating((float) rating);
        return v;
    }

    public void mapIt() {

        String formattedAddress = address + address2;
        formattedAddress = formattedAddress.replace(' ', '+');
        Intent geoIntent = new Intent(
                android.content.Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=" + formattedAddress));
        startActivity(geoIntent);
    }

    public void sendIt() {
        String smsMessage = "" + R.string.textpart1 + R.string.textpart2 + name + R.string.textpart3 + phoneNumber + R.string.textpart4;
        phoneNumber = mEditText.getText().toString();
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, smsMessage, null, null);
    }

    public void saveIt() {

    }

    public void submitIt() {
        Log.d("string", "subtmititstarted");
        double num = bar.getRating();
        Log.d("string", "" + bar.getRating());
        ParseObject Rater = new ParseObject("feedbackObj");
        Rater.put("Name", name); //GETTING NAME FROM SANJID
        //Rater.put("Number", 0);   //FOR TESTING PURPOSES
        if(!Rater.has("Rating")){
            Log.d("ohmygod", "PLEASE WORK");
            Rater.put("Rating", 0);
            Rater.put("Number",0);
            }
        if (Rater.has("Number")) {
            Log.d("string", "submittititit");
            int temp = Rater.getInt("Number");
            temp++;
            double rated = num + mRating;
            Rater.put("Rating",rated);
            double avg = rated/temp;
            ParseObject point = ParseObject.createWithoutData("Point", "dlkj83d");
            point.increment("Number");
        }
        Rater.saveInBackground();



    }


}
