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

import by.bsu.dektiarev.myapplication.asynctasks.AlbumGetter;
import by.bsu.dektiarev.myapplication.imageloader.AppController;
import de.umass.lastfm.Album;
import de.umass.lastfm.ImageSize;

/**
 * Created by USER on 20.12.2015.
 */
public class AlbumsListAdapter extends ArrayAdapter<String> {

    Context context;
    private final String[] values;

    public AlbumsListAdapter(Context context, String[] values) {
        super(context, R.layout.list_view_album, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context myContext = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view_album, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textViewAlbumItem);
        NetworkImageView imageView = (NetworkImageView) rowView.findViewById(R.id.imageViewAlbumItem);

        String value = values[position];

        AlbumGetter albumGetter = (AlbumGetter) new AlbumGetter().execute(value);
        Album album = null;
        try {
            album = albumGetter.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(album != null) {
            String imageUrl = album.getImageURL(ImageSize.EXTRALARGE);

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
