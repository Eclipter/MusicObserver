package by.bsu.dektiarev.myapplication.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import by.bsu.dektiarev.myapplication.R;
import by.bsu.dektiarev.myapplication.asynctasks.TopAlbumsGetter;
import by.bsu.dektiarev.myapplication.imageloader.ImageLoader;
import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;
import de.umass.lastfm.ImageSize;

public class AlbumsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_albums, container, false);

        Bundle args = getArguments();
        String artistName = args.getString("artist");

        List<Map<String, String>> albums = new ArrayList<>();

        TopAlbumsGetter topAlbumsGetter = (TopAlbumsGetter) new TopAlbumsGetter().execute(artistName);
        List<Album> albumList = null;
        try {
            albumList = topAlbumsGetter.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        assert albumList != null;
        for(Album album : albumList) {
            Map<String, String> datum = new HashMap<>(2);
            datum.put("title", album.getName());
            Date releaseDate = album.getReleaseDate();
            if(releaseDate != null) {
                datum.put("date", releaseDate.toString());
            }
            else {
                datum.put("date", "Unknown");
            }
            albums.add(datum);
        }

        ListView listView = (ListView) view.findViewById(R.id.listViewOne);


        SimpleAdapter adapter = new SimpleAdapter(this.getContext(), albums,
                android.R.layout.simple_list_item_2, new String[] {"title", "date"},
                new int[] {android.R.id.text1,
                        android.R.id.text2}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                TextView textView1 = (TextView) view.findViewById(android.R.id.text2);
                textView1.setTextColor(Color.BLACK);
                textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                return view;
            }
        };

        listView.setAdapter(adapter);

        return view;
    }
}
