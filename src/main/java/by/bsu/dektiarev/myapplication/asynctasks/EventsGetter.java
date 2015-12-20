package by.bsu.dektiarev.myapplication.asynctasks;

import android.os.AsyncTask;

import java.util.List;

import de.umass.lastfm.Artist;
import de.umass.lastfm.Event;
import de.umass.lastfm.PaginatedResult;

/**
 * Created by USER on 20.12.2015.
 */
public class EventsGetter extends AsyncTask<String, Void, PaginatedResult<Event>> {

    private Exception exception;

    @Override
    protected PaginatedResult<Event> doInBackground(String... params) {
        try {
            Artist artist =  Artist.getInfo(params[0], "a578c2ba64a884d469f7478117dfaae6");
            PaginatedResult<Event> events = Artist.getEvents(artist.getMbid(), "a578c2ba64a884d469f7478117dfaae6");


            if(!events.isEmpty()) {
                return events;
            }
            else return null;
        }
        catch(Exception ex) {
            this.exception = ex;
            return null;
        }
    }

    protected void onPostExecute(PaginatedResult<Event> events) {
        if(this.exception != null) {
            exception.printStackTrace();
            events = null;
        }
    }
}
