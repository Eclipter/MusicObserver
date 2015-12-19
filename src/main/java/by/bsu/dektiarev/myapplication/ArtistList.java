package by.bsu.dektiarev.myapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.LinkedHashSet;
import java.util.Set;

public class ArtistList extends AppCompatActivity {

    ListView artistList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);

        artistList = (ListView) findViewById(R.id.listArtistViewItem);

        Set<String> artists = new LinkedHashSet<>();

        ContentResolver contentResolver = this.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String sortOrder = MediaStore.Audio.Media.ARTIST + " ASC";
        Cursor cursor = contentResolver.query(uri, null, null, null, sortOrder);

        if(cursor != null && cursor.moveToFirst()) {

            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            do {
                String data = cursor.getString(artistColumn);
                artists.add(data);
            } while(cursor.moveToNext());

            cursor.close();
        }

        final String[] values = new String[artists.size()];
        artists.toArray(values);
        ArtistListAdapter adapter = new ArtistListAdapter(this.getApplicationContext(), values);
        artistList.setAdapter(adapter);

        artistList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ArtistList.this, ArtistTabbedBrowser.class);
                Bundle bundle = new Bundle();
                bundle.putString("artist", values[position]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
