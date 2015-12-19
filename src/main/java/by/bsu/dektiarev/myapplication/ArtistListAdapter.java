package by.bsu.dektiarev.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import by.bsu.dektiarev.myapplication.asynctasks.ArtistGetter;
import by.bsu.dektiarev.myapplication.imageloader.DownloadImageTask;
import by.bsu.dektiarev.myapplication.imageloader.ImageLoader;
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view_artist, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textViewItem);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewItem);

        String value = values[position];

        ArtistGetter artistGetter = (ArtistGetter) new ArtistGetter().execute(value);
        Artist artist = null;
        try {
            artist = artistGetter.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        if(artist != null) {
            String imageUrl = artist.getImageURL(ImageSize.HUGE);

            /*ImageLoader imageLoader = new ImageLoader(context);
            imageLoader.DisplayImage(imageUrl, R.drawable.loader, imageView);*/

            DownloadImageTask downloadImageTask = (DownloadImageTask) new DownloadImageTask().execute(imageUrl);
            try {
                Bitmap bitmap = downloadImageTask.get();
                imageView.setImageBitmap(bitmap);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        textView.setText(value);

        return rowView;
    }
}
