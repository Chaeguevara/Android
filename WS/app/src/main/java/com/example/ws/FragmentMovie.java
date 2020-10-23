package com.example.ws;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class FragmentMovie extends Fragment {
    ArrayList<Movie> movies;
    HttpAsync httpAsync;

    public FragmentMovie(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup) inflater.inflate(
                R.layout.fragment_movie,container,false
        );


//        getData();


        return viewGroup;
    }


    private void getData() {
        //get data from URL
        movies = new ArrayList<>();
        String url = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=20201001";
        httpAsync = new HttpAsync();
        httpAsync.execute(url);
        String result = null;
        try {
            result = httpAsync.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        };

        //parse JSON
        JSONArray movieData = null;
        try {
            JSONObject movieList = new JSONObject(result);
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
//        setList(movies);

    }
}