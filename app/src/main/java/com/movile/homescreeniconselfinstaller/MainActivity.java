package com.movile.homescreeniconselfinstaller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SharedPreferences appPreferences;

    private boolean isBrowserRedirect = false;
    private boolean clickedBrowserRedirect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        getExtras(getIntent() == null ? null : getIntent().getExtras());

        setButtonClick((Button) findViewById(R.id.bAdd_Shortcut));
    }

    private void getExtras(@Nullable Bundle extras) {
        if (extras != null) {
            Log.d(TAG, "savedInstanceState is not null");
            if (extras.containsKey("clickedBrowserRedirect")) {
                Log.d(TAG, "savedInstanceState contains 'clickedBrowserRedirect' key");
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
//                startActivity(browserIntent);
            } else {
                Log.d(TAG, "savedInstanceState does not contain 'clickedBrowserRedirect' key");
                boolean test = extras.getBoolean("clickedBrowserRedirect", false);
                Log.d(TAG, "test: " + String.valueOf(test));
            }
        } else {
            Log.d(TAG, "savedInstanceState is null");
        }
    }

    private void setButtonClick(Button addButton) {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShortcutIcon();
            }
        });
    }

    private void addShortcutIcon() {
        Bundle extras = new Bundle();
        extras.putBoolean("clickedBrowserRedirect", true);

        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.putExtra("clickedBrowserRedirect", true);
        shortcutIntent.putExtras(extras);

        createHomescreenicon(shortcutIntent);
    }

    private void createHomescreenicon(Intent shortcutIntent) {
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
    }

}
