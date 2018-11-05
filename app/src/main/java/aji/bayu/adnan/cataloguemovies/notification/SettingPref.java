package aji.bayu.adnan.cataloguemovies.notification;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import aji.bayu.adnan.cataloguemovies.R;
import aji.bayu.adnan.cataloguemovies.adapter.ResultsItem;

/**
 * Created by Jihad044 on 26/10/2018.
 */

public class SettingPref extends AppCompatPref {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        private RequestQueue mRequestQueue;
        List<ResultsItem> mNotifList;
        MovieDailyReceiver mMovieDailyReceiver = new MovieDailyReceiver();
        MovieUpcomingReceiver mMovieUpcomingReceiver = new MovieUpcomingReceiver();
        SwitchPreference mSwitchReminder;
        SwitchPreference mSwitchToday;

        public class GetMovieTask extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... strings) {
                getData(strings[0]);
                return null;
            }
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String key = preference.getKey();
            boolean value = (boolean) newValue;
            if (key.equals(getString(R.string.key_today_reminder))) {
                if (value) {
                    mMovieDailyReceiver.setAlarm(getActivity());
                } else {
                    mMovieDailyReceiver.cancelAlarm(getActivity());
                }
            } else {
                if (value) {
                    setReleaseAlarm();
                } else {
                    mMovieUpcomingReceiver.cancelAlarm(getActivity());
                }
            }
            return true;
        }

        private void setReleaseAlarm() {
            MainPreferenceFragment.GetMovieTask getDataAsync = new MainPreferenceFragment.GetMovieTask();
            getDataAsync.execute("https://api.themoviedb.org/3/movie/upcoming?api_key=f04bce2a28b277c0c4ee02124610fef5&language=en-US");
        }

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);
            mNotifList = new ArrayList<>();
            mRequestQueue = Volley.newRequestQueue(getActivity());
            mSwitchReminder = (SwitchPreference) findPreference(getString(R.string.key_today_reminder));
            mSwitchReminder.setOnPreferenceChangeListener(this);
            mSwitchToday = (SwitchPreference) findPreference(getString(R.string.key_release_reminder));
            mSwitchToday.setOnPreferenceChangeListener(this);
            Preference myPref = findPreference(getString(R.string.key_lang));
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                    return true;
                }
            });
        }

        public void getData(String url) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date();
            final String today = dateFormat.format(date);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            ResultsItem movieItem = new ResultsItem();
                            movieItem.setTitle(data.getString("title"));
                            movieItem.setReleaseDate(data.getString("release_date"));
                            movieItem.setTitle(data.getString("title"));
                            movieItem.setOverview(data.getString("overview"));
                            movieItem.setPosterPath(data.getString("poster_path"));
                            if (data.getString("release_date").equals(today)) {
                                mNotifList.add(movieItem);
                            }
                        }
                        mMovieUpcomingReceiver.setAlarm(getActivity(), mNotifList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mRequestQueue.add(request);
        }
    }
}
