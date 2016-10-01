package com.example.chrisbennett.mylistview;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class SummaryView extends AppCompatActivity {

    ReviewDBHelper mDbHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_view);


        mDbHelper = new ReviewDBHelper(this);
        db = mDbHelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM "  + ReviewSchema.Review.TABLE_NAME,null);
        ReviewCursorAdapter adapter = new ReviewCursorAdapter(this,c);

        ListView listview = (ListView) findViewById(R.id.listView);

        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //create intent
                Intent intent = new Intent(view.getContext(), DetailView.class);

                //pack in info
                intent.putExtra("position",position);

                //start activity
                startActivity(intent);
            }


        });
    }

    protected void filter(View v) {

        EditText flrTerm = (EditText) findViewById(R.id.txtFilter);
        String str = flrTerm.getText().toString();

        Cursor c = db.rawQuery("SELECT * FROM "  + ReviewSchema.Review.TABLE_NAME + " WHERE " + ReviewSchema.Review.COLUMN_NAME_REVIEW + " LIKE " + "'%" + str + "%'",null);
        ReviewCursorAdapter adapter = new ReviewCursorAdapter(this,c);

        ListView listview = (ListView) findViewById(R.id.listView);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditText flrTerm = (EditText) findViewById(R.id.txtFilter);
                String str = flrTerm.getText().toString();
                //create intent
                Intent intent = new Intent(view.getContext(), DetailView.class);

                //pack in info
                intent.putExtra("position",position);
                intent.putExtra("sql","SELECT * FROM "  + ReviewSchema.Review.TABLE_NAME + " WHERE " + ReviewSchema.Review.COLUMN_NAME_REVIEW + " LIKE " + "'%" + str + "%'");

                //start activity
                startActivity(intent);
            }


        });



    }
}
