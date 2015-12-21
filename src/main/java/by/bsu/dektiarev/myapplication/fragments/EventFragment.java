package by.bsu.dektiarev.myapplication.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import by.bsu.dektiarev.myapplication.R;
import by.bsu.dektiarev.myapplication.asynctasks.ArtistGetter;
import by.bsu.dektiarev.myapplication.asynctasks.EventsGetter;
import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "artist";

    // TODO: Rename and change types of parameters
    private String artistName;


    public EventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment EventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventFragment newInstance(String param1) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            artistName = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listViewEvents);

        List<Map<String, String>> eventsInfo = new ArrayList<>();

        ArtistGetter artistGetter = (ArtistGetter) new ArtistGetter().execute(artistName);
        Artist artist = null;
        try {
            artist = artistGetter.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(artist != null) {
            EventsGetter eventsGetter = (EventsGetter) new EventsGetter().execute(artist.getName());
            try {
                PaginatedResult<Event> events = eventsGetter.get();
                if(events != null) {
                    List<Event> eventList = (List<Event>) events.getPageResults();

                    for(Event event : eventList) {
                        Map<String, String> value = new HashMap<>(2);
                        value.put("title", event.getTitle());
                        value.put("date", String.valueOf(event.getStartDate()));
                        eventsInfo.add(value);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(this.getContext(), eventsInfo,
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
