package edu.fsu.cs.mobile.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NoteMainActivity extends AppCompatActivity {


    public static final String FILENAME = "FILENAME";
    public static final String EXTENSION = ".bin";
    private ListView ListNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main);

        ListNotes = findViewById(R.id.main_listview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_create:
                startActivity(new Intent(this, NoteActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void verify()
    {
        ListNotes.setAdapter(null);
        ArrayList<Note> notes = getAllNotes(getApplicationContext());


        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note lhs, Note rhs) {
                if(lhs.getDateTime() < rhs.getDateTime()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        if(notes.size() > 0) {

            final Note.NoteWrap na = new Note.NoteWrap(this, R.layout.view_note_item, notes);
            ListNotes.setAdapter(na);
            ListNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String fileName = ((Note) ListNotes.getItemAtPosition(position)).getDateTime()
                            + EXTENSION;
                    Intent viewNoteIntent = new Intent(getApplicationContext(), NoteActivity.class);
                    viewNoteIntent.putExtra(FILENAME, fileName);
                    startActivity(viewNoteIntent);
                }
            });
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        verify();

    }


    public static void saveNote(Context context, Note note) {

        String fileName = String.valueOf(note.getDateTime()) + EXTENSION;
        FileOutputStream fos;
        ObjectOutputStream oos;

        try {
            fos = context.openFileOutput(fileName, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(note);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    static ArrayList<Note> getAllNotes(Context context) {
        ArrayList<Note> notes = new ArrayList<>();
        File filesDir = context.getFilesDir();
        ArrayList<String> noteFiles = new ArrayList<>();

        for (String file : filesDir.list()) {
            if (file.endsWith(EXTENSION)) {
                noteFiles.add(file);
            }
        }

        FileInputStream fis;
        ObjectInputStream ois;

        for (int i = 0; i < noteFiles.size(); i++) {
            try {
                fis = context.openFileInput(noteFiles.get(i));
                ois = new ObjectInputStream(fis);
                notes.add((Note) ois.readObject());
                fis.close();
                ois.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }

        return notes;
    }


    public static Note getNote(Context context, String fileName) {

        File file = new File(context.getFilesDir(), fileName);
        if (file.exists() && !file.isDirectory()) {

            FileInputStream fis;
            ObjectInputStream ois;

            try {
                fis = context.openFileInput(fileName);
                ois = new ObjectInputStream(fis);
                Note note = (Note) ois.readObject();
                fis.close();
                ois.close();

                return note;

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }
    }

    public static boolean deleteFile(Context context, String fileName) {
        File dirFiles = context.getFilesDir();
        File file = new File(dirFiles, fileName);

        if (file.exists() && !file.isDirectory()) return file.delete();

        return false;
    }
}