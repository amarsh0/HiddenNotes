package edu.fsu.cs.mobile.tictactoe;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private boolean OpenedNote;
    private long Time;
    private String FileName;
    private Note LoadedNote = null;

    private EditText NoteTitle;
    private EditText NoteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        NoteTitle = findViewById(R.id.note_subject);
        NoteContent = findViewById(R.id.note_et_content);


        FileName = getIntent().getStringExtra(NoteMainActivity.FILENAME);
        if (FileName != null && !FileName.isEmpty() && FileName.endsWith(NoteMainActivity.EXTENSION)) {
            LoadedNote = NoteMainActivity.getNote(getApplicationContext(), FileName);
            if (LoadedNote != null) {

                NoteTitle.setText(LoadedNote.getTitle());
                NoteContent.setText(LoadedNote.getContent());
                Time = LoadedNote.getDateTime();
                OpenedNote = true;
            }
        } else {
            Time = System.currentTimeMillis();
            OpenedNote = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (OpenedNote) {
            getMenuInflater().inflate(R.menu.menu_note_view, menu);

        } else {
            getMenuInflater().inflate(R.menu.menu_note_add, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.action_delete:

                AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure you want to Delete?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NoteMainActivity.deleteFile(getApplicationContext(), FileName);
                                finish();
                            }
                        })
                        .setNegativeButton("NO", null);
                dialogDelete.show();
                break;

            case R.id.action_cancel:
                UpdateAndSaveNote();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        UpdateAndSaveNote();
        finish();
    }




    private void UpdateAndSaveNote() {


        String subject = NoteTitle.getText().toString();
        String body = NoteContent.getText().toString();


        if (subject.isEmpty() || body.isEmpty()) {
            Toast.makeText(NoteActivity.this, "please fill both Subject and Body"
                    , Toast.LENGTH_SHORT).show();
            return;
        }


        if (LoadedNote != null) {
            Time = LoadedNote.getDateTime();

        } else {
            Time = System.currentTimeMillis();
        }
        if (isNoteChanged() && LoadedNote != null) {
            Time = System.currentTimeMillis();
            NoteMainActivity.deleteFile(getApplicationContext(), FileName);
        }

        NoteMainActivity.saveNote(this, new Note(Time, subject, body));

        finish();
    }
    private boolean isNoteChanged() {
        if (OpenedNote) {
            return LoadedNote != null && (!NoteTitle.getText().toString().equalsIgnoreCase(LoadedNote.getTitle())
                    || !NoteContent.getText().toString().equalsIgnoreCase(LoadedNote.getContent()));
        } else {
            return !NoteTitle.getText().toString().isEmpty() || !NoteContent.getText().toString().isEmpty();
        }
    }

}