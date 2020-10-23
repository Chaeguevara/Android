package com.example.p533;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.json.*;

public class MainActivity extends AppCompatActivity {
    TextView tx_url;
    HttpAsync httpAsync;

    RecyclerView recyclerView;
    ArrayList<Movie> movies;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_url = findViewById(R.id.tx_url);
        recyclerView = findViewById(R.id.recyclerView);
        movies = new ArrayList<>();
    }

    public void ck(View v){
        String url = tx_url.getText().toString();
        httpAsync = new HttpAsync();
        httpAsync.execute(url);
        String result = null;
        try {
             result = httpAsync.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,"result:"+result, Toast.LENGTH_LONG).show();
        
        getMovieData(result);


    }


    class MovieAdapter extends BaseAdapter{
        ArrayList<Movie> data;

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(
                    R.layout.activity_show_movie,
                    container,
                    true
            );
            ImageView im = view.findViewById(R.id.imageView);
            TextView tx_name = view.findViewById(R.id.tx_name);
            TextView tx_rank = view.findViewById(R.id.tx_rank);
            TextView tx_audiCnt = view.findViewById(R.id.tx_audiCnt);
            Movie m = data.get(position);
//            im.setImageResource();
            tx_name.setText(m.getMovieNm());
            tx_rank.setText(m.getRank());
            tx_audiCnt.setText(m.getAudiCnt());


            return view;
        }
    } //End classview

    public void setList(ArrayList<Movie> movies){

    }

    private void getMovieData(String result) {
        String jsonString = result;
        JSONArray movieData = null;
        try {
            JSONObject movieList = new JSONObject(jsonString);
            movieList = movieList.getJSONObject("boxOfficeResult");
            movieData = movieList.getJSONArray("dailyBoxOfficeList");
            for(int i=0; i< movieData.length(); i++){
                JSONObject movieJson = movieData.getJSONObject(i);
                Movie movie = new Movie();

                movie.setRnum(movieJson.getString("rnum"));
                movie.setRank(movieJson.getString("rank"));
                movie.setRankInten(movieJson.getString("rankInten"));
                movie.setRankOldAndNew(movieJson.getString("rankOldAndNew"));
                movie.setMovieCd(movieJson.getString("movieCd"));
                movie.setMovieNm(movieJson.getString("movieNm"));
                movie.setOpenDt(movieJson.getString("openDt"));
                movie.setSalesShare(movieJson.getString("salesShare"));
                movie.setSalesInten(movieJson.getString("salesInten"));
                movie.setSalesChange(movieJson.getString("salesChange"));
                movie.setSalesAcc(movieJson.getString("salesAcc"));
                movie.setAudiCnt(movieJson.getString("audiCnt"));
                movie.setAudiInten(movieJson.getString("audiInten"));
                movie.setAudiChange(movieJson.getString("audiChange"));
                movie.setAudiAcc(movieJson.getString("audiAcc"));
                movie.setScrnCnt(movieJson.getString("scrnCnt"));
                movie.setShowCnt(movieJson.getString("showCnt"));

                Log.d("rnum","[rnum] "+movie.getMovieNm());
                movies.add(movie);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("BoxofficeType","[BoxofficeType] "+movieData);
        setList(movies);
    }


    class HttpAsync extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0].toString();
            String result = HttpConnect.getString(url);
            Log.d("Back_result","[Back_result] "+result);
            return result;
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

}