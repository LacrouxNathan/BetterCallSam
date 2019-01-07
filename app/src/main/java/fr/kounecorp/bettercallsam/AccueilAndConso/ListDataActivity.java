package fr.kounecorp.bettercallsam.AccueilAndConso;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import fr.kounecorp.bettercallsam.R;

public class ListDataActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_alcool);
        mListView = (ListView) findViewById(R.id.listViewAlcool);
        mDatabaseHelper = new DatabaseHelper(this);
        final Intent intent = getIntent();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myintent = new Intent(view.getContext(),PageAlcool.class);
                myintent.putExtra("position",position);
                if (intent.getExtras().getString("ID") != null) {
                    myintent.putExtra("ID", intent.getExtras().getString("ID"));
                }
                startActivity(myintent);
            }
        });

        populateListView();
    }

    private void populateListView(){
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(1) + "                          " + data.getString(2) +"°");


        }
        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listData);
        mListView.setAdapter(adapter);
    }
}
