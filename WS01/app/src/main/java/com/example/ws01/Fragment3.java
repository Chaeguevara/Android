package com.example.ws01;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;


public class Fragment3 extends Fragment {
    ArrayList<Movie> movies;
    HttpAsync httpAsync;
    Button button;
    CalendarView calendarView;
    LinearLayout container_1;
    ListView listView;

    String dateStr;

    public Fragment3(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup) inflater.inflate(
                R.layout.fragment_3,container,false
        );
        container_1 = viewGroup.findViewById(R.id.container);
        button = viewGroup.findViewById(R.id.button4);
        listView = viewGroup.findViewById(R.id.listView);
        final ViewGroup finalViewGroup = viewGroup;

        //get date from calendar
        calendarView = finalViewGroup.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month +=1;
                String strMonth = null;
                String strYear = year+"";
                String strDay = null;
                if(month<10){
                    strMonth = "0" + month;
                }else{
                    strMonth = month+"";
                }
                if(dayOfMonth<10){
                    strDay = "0" + dayOfMonth;
                }else{
                    strDay = dayOfMonth+"";
                }
                dateStr = strYear+strMonth+strDay;
                Log.d("[Date]",dateStr);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });



        return viewGroup;
    }// end on createview

    public int getFileid(Movie m){
        int id = R.drawable.m2;
        HashMap<String,Integer> movieID = new HashMap<String,Integer>();

        /*put elements*/
        movieID.put("담보", R.drawable.m_dambo);
        movieID.put("삼진그룹 영어토익반", R.drawable.m_samjin);
        movieID.put("소리도 없이", R.drawable.m_soundless);
        movieID.put("테넷", R.drawable.m_tenet);
        movieID.put("에브리타임 아이 다이", R.drawable.m_everydie);
        movieID.put("기기괴괴 성형수", R.drawable.m_weiredplastic);
        movieID.put("돌멩이", R.drawable.m_stone);
        movieID.put("21 브릿지: 테러 셧다운", R.drawable.m_21bridge);
        movieID.put("폰조", R.drawable.m_fonzo);
        movieID.put("그대, 고맙소 : 김호중 생애 첫 팬미팅 무비", R.drawable.m_hojoong);
        movieID.put("그린랜드", R.drawable.m_green);
        movieID.put("언힌지드", R.drawable.m_unhinged);
        movieID.put("국제수사", R.drawable.m_international);
        movieID.put("애프터: 그 후", R.drawable.m_after);

        if(movieID.get(m.getMovieNm())!=null){
            id = movieID.get(m.getMovieNm());
        }

        return id;
    }

    class MovieAdapter extends BaseAdapter{
        ArrayList<Movie> data;

        public MovieAdapter(ArrayList<Movie> data){this.data = data;}

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(
                    R.layout.movie,
                    container_1,
                    true
            );
            ImageView im = view.findViewById(R.id.imageView);
            TextView rank = view.findViewById(R.id.rank);
            TextView salesAcc = view.findViewById(R.id.salesAcc);
            TextView audiAcc = view.findViewById(R.id.audiAcc);
            Movie m = data.get(position);

            im.setImageResource(getFileid(m));
            rank.setText(m.getRank()+". " + m.getMovieNm());
            salesAcc.setText("총수익: " + m.getSalesAcc()+"");
            audiAcc.setText("총 관객수: " + m.getAudiAcc()+"");
            return view;
        }
    }// end Movie adapter

    public void setList(ArrayList<Movie> movie){
        MovieAdapter movieAdapter = new MovieAdapter(movie);
        listView.setAdapter(movieAdapter);

    }

    private void getData() {
        //get data from URL
        movies = new ArrayList<>();
        String url = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=" + dateStr;
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
        setList(movies);

    }
}