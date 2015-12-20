package by.bsu.dektiarev.myapplication;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import by.bsu.dektiarev.myapplication.asynctasks.ArtistGetter;
import by.bsu.dektiarev.myapplication.fragments.AlbumsFragment;
import by.bsu.dektiarev.myapplication.fragments.EventFragment;
import by.bsu.dektiarev.myapplication.fragments.InfoFragment;
import by.bsu.dektiarev.myapplication.fragments.TestFragment;
import by.bsu.dektiarev.myapplication.imageloader.AppController;
import de.umass.lastfm.Artist;
import de.umass.lastfm.ImageSize;

public class ArtistTabbedBrowser extends AppCompatActivity {


    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private NetworkImageView artistImage;

    Artist artist;
    String artistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_tabbed_browser);

        toolbar = (Toolbar) findViewById(R.id.newtoolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        artistImage = (NetworkImageView) findViewById(R.id.artistImage);

        Bundle bundle = getIntent().getExtras();
        artistName = bundle.getString("artist");


        final ArtistGetter artistGetter = (ArtistGetter) new ArtistGetter().execute(artistName);
        artist = null;
        try {
            artist = artistGetter.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(artist != null) {

            String imageUrl = artist.getImageURL(ImageSize.EXTRALARGE);

            artistImage.setMinimumHeight(400);
            artistImage.setMinimumWidth(400);

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            artistImage.setImageUrl(imageUrl, imageLoader);

            artistName =  artist.getName();
            getSupportActionBar().setTitle(artistName);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putString("artist", artistName);

        InfoFragment infoFragment = InfoFragment.newInstance(artistName);

        AlbumsFragment albumsFragment = new AlbumsFragment();
        albumsFragment.setArguments(bundle);

        EventFragment eventFragment = EventFragment.newInstance(artist.getMbid());

        adapter.addFrag(infoFragment, "INFO");
        adapter.addFrag(albumsFragment, "ALBUMS");
        adapter.addFrag(eventFragment, "EVENTS");
        adapter.addFrag(new TestFragment(), "SIMILAR");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
