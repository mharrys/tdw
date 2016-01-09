package se.mharrys.tdw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_activity);

        ArrayList<String> items = new ArrayList<>();
        ArrayAdapter<String> adapterValues = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                items
        );

        ListView articlesView = (ListView) findViewById(R.id.articlesView);
        articlesView.setAdapter(adapterValues);
    }
}
