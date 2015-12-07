package by.bsu.dektiarev.myapplication;

import android.content.Context;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlbumsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_albums, container, false);

        List<String> albumNames = new ArrayList<>();
        albumNames.add("Showbiz");
        albumNames.add("Origin Of Symmetry");
        albumNames.add("Absolution");
        albumNames.add("Black Holes And Revelations");
        albumNames.add("The Resistance");
        albumNames.add("The 2nd Law");
        albumNames.add("Drones");

        List<Map<String, String>> albums = new ArrayList<>();
        for (String item : albumNames) {
            Map<String, String> datum = new HashMap<>(2);
            datum.put("title", item);
            datum.put("date", "rand");
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
                textView1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                return view;
            }
        };

        listView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
}
