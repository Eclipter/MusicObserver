package by.bsu.dektiarev.myapplication.asynctasks;

import android.os.AsyncTask;

import java.util.List;

import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;

/**
 * Created by USER on 20.12.2015.
 */
public class AlbumGetter extends AsyncTask<String, Void, Album> {
    private Exception exception;

    @Override
    protected Album doInBackground(String... params) {
        try {
            List<Album> searchRes = (List<Album>) Album.search(params[0], "a578c2ba64a884d469f7478117dfaae6");

            if(searchRes.size() != 0) {
                Album album = searchRes.get(0);
                Album detailedAlbum = Album.getInfo(album.getArtist(), album.getMbid(), "a578c2ba64a884d469f7478117dfaae6");
                return detailedAlbum;
            }
            else return null;
        }
        catch(Exception ex) {
            this.exception = ex;
            return null;
        }
    }

    protected void onPostExecute(Album album) {
        if(this.exception != null) {
            exception.printStackTrace();
            album = null;
        }
    }
}
