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


public class MainFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = MainFragment.class.getCanonicalName();
    private OnButtonClickListener mClickListener;


    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId())
        {
            case R.id.newGame:
                fragment = new TicTacToeFragment();
                replaceFragment(fragment);
                break;

            case R.id.resumeGame:

                fragment = new TicTacToeFragment();
                replaceFragment(fragment);
                break;
            case R.id.scoreBoard:
                fragment = new ScoreboardFragment();
                replaceFragment(fragment);
                break;

            default:
                break;
        }
    }

    public interface OnButtonClickListener {
        public void onButtonClick(Bundle bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Button newgame = rootView.findViewById(R.id.newGame);
        Button resumegame = rootView.findViewById(R.id.resumeGame);
        Button scoreBoard = rootView.findViewById(R.id.scoreBoard);
        SharedPreferences preferences = getActivity().getSharedPreferences(MainActivity.TIES, Activity.MODE_PRIVATE);

        int ties = preferences.getInt(MainActivity.TIES, 0);



        preferences = getActivity().getSharedPreferences(MainActivity.PASS, Activity.MODE_PRIVATE);

        String pass = preferences.getString(MainActivity.PASS, "");
        if (pass == "") {
            resumegame.setEnabled(false);
        }
        else {
            newgame.setEnabled(false);
            resumegame.setEnabled(true);
        }

        newgame.setOnClickListener((View.OnClickListener) this);
        resumegame.setOnClickListener((View.OnClickListener) this);
        scoreBoard.setOnClickListener((View.OnClickListener) this);

        return rootView;
    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
