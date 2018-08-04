package edu.fsu.cs.mobile.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Note implements Serializable {

    private long DateTime;
    private String Subject;
    private String Body;

    Note(long dateInMillis, String title, String content) {
        DateTime = dateInMillis;
        Subject = title;
        Body = content;
    }

    public void setDateTime(long dateTime) {
        DateTime = dateTime;
    }

    public void setTitle(String title) {
        Subject = title;
    }

    public void setContent(String content) {
        Body = content;
    }

    public long getDateTime() {
        return DateTime;
    }


    public String getDateTimeFormatted(Context context) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MMM d, yyyy hh:mm aaa"
                , context.getResources().getConfiguration().locale);
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(new Date(DateTime));
    }

    public String getTitle() {
        return Subject;
    }

    public String getContent() {
        return Body;
    }

    public static class NoteWrap extends ArrayAdapter<Note> {

        static final int WRAP = 40;
        NoteWrap(Context context, int resource, List<Note> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.view_note_item, null);
            }

            Note note = getItem(position);

            if(note != null) {
                TextView title = (TextView) convertView.findViewById(R.id.list_note_title);
                TextView date = (TextView) convertView.findViewById(R.id.list_note_date);
                TextView content = convertView.findViewById(R.id.list_note_content_preview);

                title.setText(note.getTitle());
                date.setText(note.getDateTimeFormatted(getContext()));

                int wrap = WRAP;
                int lineBreakIndex = note.getContent().indexOf('\n');

                if(lineBreakIndex < WRAP || note.getContent().length() > WRAP ) {
                    if(lineBreakIndex < WRAP) {
                        wrap = lineBreakIndex;
                    }
                    if(wrap > 0) {
                        content.setText(note.getContent().substring(0, wrap));
                    } else {
                        content.setText(note.getContent());
                    }
                } else {
                    content.setText(note.getContent());
                }
            }

            return convertView;
        }

    }
}