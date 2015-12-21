package by.bsu.dektiarev.myapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedHashSet;
import java.util.Set;

public class ArtistList extends AppCompatActivity {

    ListView artistList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);

        toolbar = (Toolbar) findViewById(R.id.artistListToolBar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_art_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_art_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_main_page) {
                    Intent intent = new Intent(ArtistList.this, MainActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_artists) {

                } else if (id == R.id.nav_albums) {
                    Intent intent = new Intent(ArtistList.this, AlbumsList.class);
                    startActivity(intent);
                } else if (id == R.id.nav_share) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, "Hi! Check out the app 'Music Observer' on Google Play!" +
                            " It's awesome!");
                    try {
                        startActivity(Intent.createChooser(intent, "Promote this app..."));
                    }
                    catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "No mechanisms for sharing.", Toast.LENGTH_SHORT).show();
                    }
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_art_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        getSupportActionBar().setTitle("List of artists");

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_art_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
