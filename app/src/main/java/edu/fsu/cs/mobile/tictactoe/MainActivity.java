package edu.fsu.cs.mobile.tictactoe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainFragment.OnButtonClickListener {

    public static final String xWINS = "xwins";
    public static final String oWINS = "owins";
    public static final String TIES = "ties";
    private MainFragment mFirstFragment;
    public static final String PASS = "pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences(MainActivity.xWINS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(MainActivity.xWINS, 0);
        editor.commit();


        preferences = getSharedPreferences(MainActivity.oWINS, Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putInt(MainActivity.oWINS, 0);
        editor.commit();

        preferences = getSharedPreferences(MainActivity.TIES, Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putInt(MainActivity.TIES, 0);
        editor.commit();

        mFirstFragment = new MainFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mFirstFragment)
                .addToBackStack(MainFragment.TAG)
                .commit();



    }


    @Override
    public void onButtonClick(Bundle bundle) {
        TicTacToeFragment fragment = new TicTacToeFragment();
        fragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.hide(mFirstFragment);
        trans.add(R.id.fragment_container, fragment);
        trans.addToBackStack(MainFragment.TAG);
        trans.commit();

    }

}