package com.movile.homescreeniconselfinstaller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    Context mContext = MainActivity.this;
    SharedPreferences appPreferences;
    boolean isBrowserRedirect = false;
    boolean clickedBrowserRedirect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState != null) {
            Log.d(TAG, "savedInstanceState is not null: " + savedInstanceState.toString());
            if (savedInstanceState.containsKey("clickedBrowserRedirect")) {
                Log.d(TAG, "savedInstanceState contains 'clickedBrowserRedirect' key");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            } else {
                Log.d(TAG, "savedInstanceState does not contain 'clickedBrowserRedirect' key");
            }
        } else {
            Log.d(TAG, "savedInstanceState is null");
        }

        Button add = (Button) findViewById(R.id.bAdd_Shortcut);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShortcutIcon();
            }
        });

//        appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        isBrowserRedirect = appPreferences.getBoolean("isBrowserRedirect", false);
//        if (isBrowserRedirect) {
//            Log.d(TAG, "appPreferences contains 'isBrowserRedirect' key");
//            SharedPreferences.Editor editor = appPreferences.edit();
//            editor.putBoolean("isBrowserRedirect", false);
//            editor.apply();
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
//            startActivity(browserIntent);
//        }
    }

    private void addShortcutIcon() {
        Intent shortcutIntent = new Intent(this, MainActivity.class);
//        shortcutIntent.setComponent(new ComponentName(
//                getApplicationContext(), BrowserRedirectActivity.class));
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.putExtra("clickedBrowserRedirect", true);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "ShortcutName");
        addIntent.putExtra("duplicate", false);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource
                        .fromContext(getApplicationContext(), R.mipmap.ic_launcher));
        addIntent.putExtra("clickedBrowserRedirect", true);

        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
//        SharedPreferences.Editor editor = appPreferences.edit();
//        editor.putBoolean("isBrowserRedirect", true);
//        editor.apply();
    }

}
