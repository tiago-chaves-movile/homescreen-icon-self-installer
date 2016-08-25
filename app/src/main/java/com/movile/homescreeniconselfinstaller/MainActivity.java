package com.movile.homescreeniconselfinstaller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String REDIRECT_URL = "http://www.google.com";
    private static final String CLICKED_BROWSER_REDIRECT = "clickedBrowserRedirect";
    private static final String INSTALL_SHORTCUT_ACTION = "com.android.launcher.action.INSTALL_SHORTCUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (getIntent() != null) getExtras(getIntent().getExtras());

        setAddButtonClick((Button) findViewById(R.id.add_shortcut_button));
    }

    private void setAddButtonClick(Button addButton) {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShortcutIcon();
            }
        });
    }

    private void getExtras(@Nullable Bundle extras) {
        if (extras != null && extras.containsKey(CLICKED_BROWSER_REDIRECT)) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(REDIRECT_URL));
            startActivity(browserIntent);
        }
    }

    private void addShortcutIcon() {
        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);

        Bundle extras = new Bundle();
        extras.putBoolean(CLICKED_BROWSER_REDIRECT, true);
        shortcutIntent.putExtras(extras);

        createHomescreenIcon(shortcutIntent);
    }

    private void createHomescreenIcon(Intent shortcutIntent) {
        Intent addIntent = new Intent();
        addIntent.setAction(INSTALL_SHORTCUT_ACTION);

        addIntent.putExtra(CLICKED_BROWSER_REDIRECT, true);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Google");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher));

        getApplicationContext().sendBroadcast(addIntent);
    }

}
