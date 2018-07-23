package edu.fsu.cs.mobile.tictactoe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class TicTacToeFragment extends Fragment implements View.OnClickListener {


    private Button[][] buttons = new Button[3][3];
    private boolean Xturn = true;
    private int roundCount;
    private int Xplayer;
    private int Oplayer;
    private int ties;
    private TextView playersturn;

    String[][] input = new String[3][3];
    public static  String Password ;
    public static  String passAttempt=  "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle state) {

        SharedPreferences preferences = getActivity().getSharedPreferences(MainActivity.xWINS, Activity.MODE_PRIVATE);
        Xplayer = preferences.getInt(MainActivity.xWINS,0);
        preferences = getActivity().getSharedPreferences(MainActivity.oWINS, Activity.MODE_PRIVATE);
        Oplayer = preferences.getInt(MainActivity.oWINS,0);
        preferences = getActivity().getSharedPreferences(MainActivity.TIES, Activity.MODE_PRIVATE);
        ties = preferences.getInt(MainActivity.TIES,0);

        preferences = getActivity().getSharedPreferences(MainActivity.PASS, Activity.MODE_PRIVATE);
        Password = preferences.getString(MainActivity.PASS,"");

        View rootView = inflater.inflate(R.layout.fragment_tic_tac_toe, container, false);



        playersturn = rootView.findViewById(R.id.turn);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = rootView.getResources().getIdentifier(buttonID, "id", getActivity().getPackageName());
                buttons[i][j] = rootView.findViewById(resID);
                buttons[i][j].setOnClickListener(this);


            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                input[i][j] = "not";
            }
        }
        roundCount = 0;

        return rootView;
    }


    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        roundCount++;

        if (Xturn) {
            ((Button) v).setText("X");
            playersturn.setText("Current Player: O");
        } else {
            ((Button) v).setText("O");
            playersturn.setText("Current Player: X");
        }


        save();
        if (check()) {
            if (Xturn) {
                player1Wins();
                replaceFragment();


            } else {
                player2Wins();
                replaceFragment();
            }
        } else if (roundCount == 9) {
            ties++;

            SharedPreferences preferences = getActivity().getSharedPreferences(MainActivity.TIES, Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(MainActivity.TIES, ties);
            editor.commit();

            if (Password != "" && Password.equals(passAttempt)) {
                Toast.makeText(getActivity(),"you have got the password", Toast.LENGTH_SHORT).show();
            }


            if(Password == "")
            {
                Password = passAttempt;
                preferences = getActivity().getSharedPreferences(MainActivity.PASS, Activity.MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString(MainActivity.PASS, Password);
                editor.commit();
            }


            replaceFragment();
        } else {
            Xturn = !Xturn;
        }


    }


    public void replaceFragment() {
        MainFragment someFragment = new MainFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private boolean check() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }
    public void save()
    {
        String[][] field = new String[3][3];


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();

            }
        }


        if (!field[0][0].equals("") && input[0][0].equals("not")) {
            passAttempt = passAttempt + "1";
                input[0][0] = "yes";

        }

        if ( !field[0][1].equals("")&& input[0][1].equals("not")) {
            passAttempt = passAttempt + "2";
            input[0][1]= "yes";

        }

        if (!field[0][2].equals("")&& input[0][2].equals("not")) {
            passAttempt = passAttempt + "3";
            input[0][2]= "yes";

        }

        if (!field[1][0].equals("")&& input[1][0].equals("not")) {
            passAttempt = passAttempt + "4";
            input[1][0]= "yes";
        }

        if ( !field[1][1].equals("")&& input[1][1].equals("not")) {
            passAttempt = passAttempt + "5";
            input[1][1]= "yes";

        }

        if (!field[1][2].equals("")&& input[1][2].equals("not")) {
            passAttempt = passAttempt + "6";
            input[1][2]= "yes";

        }


        if (!field[2][0].equals("")&& input[2][0].equals("not")) {
            passAttempt = passAttempt + "7";
            input[2][0]= "yes";

        }

        if ( !field[2][1].equals("")&& input[2][1].equals("not")) {
            passAttempt = passAttempt + "8";
            input[2][1]= "yes";

        }

        if (!field[2][2].equals("")&& input[2][2].equals("not")) {
            passAttempt = passAttempt + "9";
            input[2][2]= "yes";

        }





    }

    private void player1Wins() {


        Xplayer++;
        ties = 0;
        String yo = Password;
        SharedPreferences preferences = getActivity().getSharedPreferences(MainActivity.xWINS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(MainActivity.xWINS, Xplayer);
        editor.commit();


        preferences = getActivity().getSharedPreferences(MainActivity.TIES, Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putInt(MainActivity.TIES, ties);
        editor.commit();

        if (Password != "" && Password.equals(passAttempt)) {
            Toast.makeText(getContext(),"you have got the password", Toast.LENGTH_SHORT).show();
        }

        if(Password == "")
        {
            Password = passAttempt;
            preferences = getActivity().getSharedPreferences(MainActivity.PASS, Activity.MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString(MainActivity.PASS, Password);
            editor.commit();
        }





    }

    private void player2Wins() {
        Oplayer++;
        ties = 0;
        String yo = Password;
        SharedPreferences preferences = getActivity().getSharedPreferences(MainActivity.oWINS, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(MainActivity.oWINS, Oplayer);
        editor.commit();

        preferences = getActivity().getSharedPreferences(MainActivity.TIES, Activity.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putInt(MainActivity.TIES, ties);
        editor.commit();

        if (!Password .equals("")&& Password.equals(passAttempt)) {
            Toast.makeText(getContext(),"you have got the password", Toast.LENGTH_SHORT).show();
        }


        if(Password == "")
        {
            Password = passAttempt;
            preferences = getActivity().getSharedPreferences(MainActivity.PASS, Activity.MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString(MainActivity.PASS, Password);
            editor.commit();
        }




    }






}
