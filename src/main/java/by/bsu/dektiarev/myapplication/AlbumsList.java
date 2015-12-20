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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AlbumsList extends AppCompatActivity {

    ListView albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums_list);

        albumList = (ListView) findViewById(R.id.listAlbumViewItem);

        Map<String, String> albumInfo = new LinkedHashMap<>();

        ContentResolver contentResolver = this.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String sortOrder = MediaStore.Audio.Media.ALBUM + " ASC";
        Cursor cursor = contentResolver.query(uri, null, null, null, sortOrder);

        if(cursor != null && cursor.moveToFirst()) {

            int albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            do {
                String album = cursor.getString(albumColumn);
                String artist = cursor.getString(artistColumn);
                albumInfo.put(album, artist);
            } while(cursor.moveToNext());

            cursor.close();
        }

        //String[] values = new String[albumInfo.size()];
        String[] albums = new String[albumInfo.size()];
        albumInfo.keySet().toArray(albums);
        final String[] artists = new String[albumInfo.size()];
        albumInfo.values().toArray(artists);
        AlbumsListAdapter adapter = new AlbumsListAdapter(this.getApplicationContext(), albums);
        albumList.setAdapter(adapter);

        albumList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AlbumsList.this, ArtistTabbedBrowser.class);
                Bundle bundle = new Bundle();
                bundle.putString("artist", artists[position]);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
