package com.dront.miniproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SeekBar;


public class MainActivity extends Activity {

    private LinearLayout purpleLayout;
    private LinearLayout greenLayout;
    private LinearLayout blueLayout;
    private LinearLayout orangeLayout;

    private final static int purpleColor = 0xffaa66cc;
    private final static int greenColor = 0xff99cc00;
    private final static int blueColor = 0xff0099cc;
    private final static int orangeColor = 0xffff8800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getInterfaceResources();

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            private final static int purpleMask = 0x010101;
            private final static int greenMask = 0x010100;
            private final static int blueMask = 0x010000;
            private final static int orangeMask = 0x0101;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                int curPurpleColor = purpleColor - progress * purpleMask;
                int curGreenColor = greenColor - progress * greenMask;
                int curBlueColor = blueColor + progress * blueMask;
                int curOrangeColor = orangeColor + progress * orangeMask;

                purpleLayout.setBackgroundColor(curPurpleColor);
                greenLayout.setBackgroundColor(curGreenColor);
                blueLayout.setBackgroundColor(curBlueColor);
                orangeLayout.setBackgroundColor(curOrangeColor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_info:
                showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getInterfaceResources(){
        purpleLayout = (LinearLayout) findViewById(R.id.rect_purple);
        greenLayout = (LinearLayout) findViewById(R.id.rect_green);
        blueLayout = (LinearLayout) findViewById(R.id.rect_blue);
        orangeLayout = (LinearLayout) findViewById(R.id.rect_orange);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title))
               .setMessage(getString(R.string.dialog_text))
               .setPositiveButton(getString(R.string.dialog_positive), new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       String address = getString(R.string.moma_address);
                       Uri siteUri = Uri.parse(address);
                       Intent browserIntent = new Intent(Intent.ACTION_VIEW, siteUri);

                       String chooserText = getString(R.string.chooser_dialog_text);
                       Intent chooserIntent = Intent.createChooser(browserIntent, chooserText);

                       if (browserIntent.resolveActivity(getPackageManager()) != null) {
                           startActivity(chooserIntent);
                       }
                   }
               })
               .setNegativeButton(getString(R.string.dialog_negative), new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.dismiss();
                   }
               })
               .show();
    }


}
