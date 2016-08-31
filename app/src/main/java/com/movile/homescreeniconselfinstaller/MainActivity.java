package com.movile.homescreeniconselfinstaller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static android.content.Intent.ACTION_MAIN;
import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.EXTRA_SHORTCUT_ICON_RESOURCE;
import static android.content.Intent.EXTRA_SHORTCUT_INTENT;
import static android.content.Intent.EXTRA_SHORTCUT_NAME;
import static android.content.Intent.ShortcutIconResource.fromContext;
import static android.net.Uri.parse;
import static com.movile.homescreeniconselfinstaller.R.id.add_shortcut_button;
import static com.movile.homescreeniconselfinstaller.R.id.remove_shortcut_button;
import static com.movile.homescreeniconselfinstaller.R.layout.main_activity;
import static com.movile.homescreeniconselfinstaller.R.mipmap.ic_launcher;

public class MainActivity extends AppCompatActivity {

    private static final String REDIRECT_URL = "http://www.google.com";

    private static final String EXTRA_CLICKED_BROWSER_REDIRECT = "clickedBrowserRedirect";

    private static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final String ACTION_UNINSTALL_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(main_activity);

        if (getIntent() != null) getExtras(getIntent().getExtras());

        setAddButtonClick((Button) findViewById(add_shortcut_button));
        setRemoveButtonClick((Button) findViewById(remove_shortcut_button));
    }

    private void setAddButtonClick(Button addButton) {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShortcutIcon();
            }
        });
    }

    private void setRemoveButtonClick(Button addButton) {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeShortcutIcon();
            }
        });
    }

    private void getExtras(@Nullable Bundle extras) {
        if (extras != null && extras.containsKey(EXTRA_CLICKED_BROWSER_REDIRECT)) {
            goToLink();
        }
    }

    private void goToLink() {
        Intent browserIntent = new Intent(ACTION_VIEW, parse(REDIRECT_URL));
        startActivity(browserIntent);
        finish();
    }

    private void addShortcutIcon() {
        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
        shortcutIntent.setAction(ACTION_MAIN);

        Bundle extras = new Bundle();
        extras.putBoolean(EXTRA_CLICKED_BROWSER_REDIRECT, true);
        shortcutIntent.putExtras(extras);

        createHomescreenIcon(shortcutIntent);
    }

    private void createHomescreenIcon(Intent shortcutIntent) {
        Intent intent = new Intent();
        intent.setAction(ACTION_INSTALL_SHORTCUT);

        intent.putExtra(EXTRA_CLICKED_BROWSER_REDIRECT, true);
        intent.putExtra(EXTRA_SHORTCUT_NAME, "Foo Bar Shortcut");
        intent.putExtra(EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(EXTRA_SHORTCUT_ICON_RESOURCE, fromContext(getApplicationContext(), ic_launcher));

        getApplicationContext().sendBroadcast(intent);
    }

    private void removeShortcutIcon() {
        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
        shortcutIntent.setAction(ACTION_MAIN);

        killHomescreenIcon(shortcutIntent);
    }

    private void killHomescreenIcon(Intent shortcutIntent) {
        Intent intent = new Intent();
        intent.setAction(ACTION_UNINSTALL_SHORTCUT);

        intent.putExtra(EXTRA_SHORTCUT_NAME, "Foo Bar Shortcut");
        intent.putExtra(EXTRA_SHORTCUT_INTENT, shortcutIntent);

        getApplicationContext().sendBroadcast(intent);
    }

}
