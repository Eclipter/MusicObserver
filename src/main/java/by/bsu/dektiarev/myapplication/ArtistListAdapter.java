package by.bsu.dektiarev.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.concurrent.ExecutionException;

import by.bsu.dektiarev.myapplication.asynctasks.ArtistGetter;
import by.bsu.dektiarev.myapplication.imageloader.AppController;
import de.umass.lastfm.Artist;
import de.umass.lastfm.ImageSize;

/**
 * Created by USER on 07.12.2015.
 */
public class ArtistListAdapter extends ArrayAdapter<String> {

    Context context;
    private final String[] values;

    public ArtistListAdapter(Context context, String[] values) {
        super(context, R.layout.list_view_artist, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context myContext = parent.getContext();
        context = myContext;

        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view_artist, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textViewArtistItem);
        NetworkImageView imageView = (NetworkImageView) rowView.findViewById(R.id.imageViewArtistItem);

        String value = values[position];

        ArtistGetter artistGetter = (ArtistGetter) new ArtistGetter().execute(value);
        Artist artist = null;
        try {
            artist = artistGetter.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(artist != null) {
            String imageUrl = artist.getImageURL(ImageSize.EXTRALARGE);

            imageView.setMinimumHeight(400);
            imageView.setMinimumWidth(400);

            /*ImageLoader imageLoader = new ImageLoader(myContext);
            imageLoader.DisplayImage(imageUrl, R.drawable.loader, imageView);*/

            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
            imageView.setImageUrl(imageUrl, imageLoader);

        }

        textView.setText(value);

        return rowView;
    }
}
