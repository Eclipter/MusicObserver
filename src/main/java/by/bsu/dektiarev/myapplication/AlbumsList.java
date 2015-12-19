package by.bsu.dektiarev.myapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.LinkedHashSet;
import java.util.Set;

public class AlbumsList extends AppCompatActivity {

    ListView albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums_list);

        albumList = (ListView) findViewById(R.id.listArtistViewItem);

        Set<String> artists = new LinkedHashSet<>();

        ContentResolver contentResolver = this.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String sortOrder = MediaStore.Audio.Media.ALBUM + " ASC";
        Cursor cursor = contentResolver.query(uri, null, null, null, sortOrder);

        if(cursor != null && cursor.moveToFirst()) {

            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            do {
                String data = cursor.getString(artistColumn);
                artists.add(data);
            } while(cursor.moveToNext());

            cursor.close();
        }

        String[] values = new String[artists.size()];
        artists.toArray(values);
        ArtistListAdapter adapter = new ArtistListAdapter(this.getApplicationContext(), values);
        albumList.setAdapter(adapter);

        albumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AlbumsList.this, ArtistTabbedBrowser.class);
                startActivity(intent);
            }
        });
    }
}
