package com.vuadvocate.advocode2015;

import com.parse.Parse;
import android.app.Application;

public class AdvoCode2015 extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "RATgcQpj491NrNWY52F8qIxzXgrn8p93xm5vw6M6",
                "AeCQ1nQ7MVw0VB2PSf3K630LUGTYRfVVmnHeqpCf");
    }
}