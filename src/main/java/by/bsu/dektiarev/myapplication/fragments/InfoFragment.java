package by.bsu.dektiarev.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import by.bsu.dektiarev.myapplication.R;
import by.bsu.dektiarev.myapplication.asynctasks.ArtistGetter;
import de.umass.lastfm.Artist;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "artist";

    // TODO: Rename and change types of parameters
    private String artistName;


    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1) {
        InfoFragment fragment = new InfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        TextView textView = (TextView) view.findViewById(R.id.infoTextView);

        ArtistGetter artistGetter = (ArtistGetter) new ArtistGetter().execute(artistName);
        Artist artist = null;
        try {
            artist = artistGetter.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(artist != null) {
            String info = artist.getWikiText();
            textView.setText(info);
        }

        return view;
    }

}
