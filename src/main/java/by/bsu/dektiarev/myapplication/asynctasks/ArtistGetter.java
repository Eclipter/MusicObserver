package by.bsu.dektiarev.myapplication.asynctasks;

import android.os.AsyncTask;

import java.util.List;

import de.umass.lastfm.Artist;

/**
 * Created by USER on 19.12.2015.
 */
public class ArtistGetter extends AsyncTask<String, Void, Artist> {

    private Exception exception;

    @Override
    protected Artist doInBackground(String... params) {
        try {
            List<Artist> searchRes = (List<Artist>) Artist.search(params[0], "a578c2ba64a884d469f7478117dfaae6");

            if(searchRes.size() != 0) {
                Artist artist = searchRes.get(0);
                Artist detailedArtist = Artist.getInfo(artist.getMbid(), "a578c2ba64a884d469f7478117dfaae6");
                return detailedArtist;
            }
            else return null;
        }
        catch(Exception ex) {
            this.exception = ex;
            return null;
        }
    }

    protected void onPostExecute(Artist artist) {
        if(this.exception != null) {
            exception.printStackTrace();
            artist = null;
        }
    }
}
