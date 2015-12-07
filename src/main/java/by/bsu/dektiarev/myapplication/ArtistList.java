package by.bsu.dektiarev.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ArtistList extends AppCompatActivity {

    ListView artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);

        artistList = (ListView) findViewById(R.id.listViewItem);

        String[] values = {"Muse", "Linkin Park", "Green Day", "Scorpions", "Katy Perry"};
        AdapterItem adapter = new AdapterItem (this.getApplicationContext(), values);
        artistList.setAdapter(adapter);

        artistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ArtistList.this, ArtistTabbedBrowser.class);
                startActivity(intent);
            }
        });
    }
}
