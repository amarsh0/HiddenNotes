package edu.fsu.cs.mobile.tictactoe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ScoreboardFragment extends Fragment {
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        textViewPlayer1 = rootView.findViewById(R.id.xwin);
        textViewPlayer2 = rootView.findViewById(R.id.oWins);
        SharedPreferences preferences = getActivity().getSharedPreferences(MainActivity.xWINS, Activity.MODE_PRIVATE);
        int xwin = preferences.getInt(MainActivity.xWINS,0);
        preferences = getActivity().getSharedPreferences(MainActivity.oWINS, Activity.MODE_PRIVATE);

        int owin = preferences.getInt(MainActivity.oWINS,0);

        textViewPlayer1.setText("X Wins: " + Integer.toString(xwin));
        textViewPlayer2.setText("O Wins: " + Integer.toString(owin));
        return rootView;
    }



}
