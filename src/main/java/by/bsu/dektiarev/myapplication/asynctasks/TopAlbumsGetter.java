package by.bsu.dektiarev.myapplication.asynctasks;

import android.os.AsyncTask;

import java.util.List;

import de.umass.lastfm.Album;
import de.umass.lastfm.Artist;

/**
 * Created by USER on 19.12.2015.
 */
public class TopAlbumsGetter extends AsyncTask<String, Void, List<Album>> {

    private Exception exception;

    @Override
    protected List<Album> doInBackground(String... params) {
        try {
            List<Album> searchRes = (List<Album>) Artist.getTopAlbums(params[0], "a578c2ba64a884d469f7478117dfaae6");

            if(searchRes != null) {
                return searchRes;
            }
            else return null;
        }
        catch(Exception ex) {
            this.exception = ex;
            return null;
        }
    }

    protected void onPostExecute(List<Album> albums) {
        if(this.exception != null) {
            exception.printStackTrace();
            albums = null;
        }
    }
}
