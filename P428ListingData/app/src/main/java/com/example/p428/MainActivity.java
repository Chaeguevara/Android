package com.example.p428;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Person> people;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        container = findViewById(R.id.container);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater layoutInflater = getLayoutInflater();
                View dview = layoutInflater.inflate(R.layout.dialog,
                        (ViewGroup) findViewById(R.id.dlayout)
                        );
                ImageView dimg = dview.findViewById(R.id.imageView2);
                dimg.setImageResource(people.get(position).getImg());
                builder.setView(dview);

                builder.setTitle("Hi");
                builder.setMessage("Name:"+ people.get(position).getName());
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }

                );
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
        });
    }
    class PersonAdapter extends BaseAdapter{
        ArrayList<Person> data;

        public PersonAdapter(ArrayList<Person> data){
            this.data = data;
        }

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
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(
                    R.layout.person,
                    container,
                    true
            );
            ImageView im = view.findViewById(R.id.imageView);
            TextView tx_id =view.findViewById(R.id.tx_id);
            TextView tx_name =view.findViewById(R.id.tx_name);
            TextView tx_age =view.findViewById(R.id.tx_age);
            Person p =data.get(position);

            im.setImageResource(p.getImg());
            tx_id.setText(p.getId());
            tx_name.setText(p.getName());
            tx_age.setText(p.getAge()+"");

            return view;
        }
    }

    public void setList(ArrayList<Person> people){
        PersonAdapter personAdapter = new PersonAdapter(people);
        listView.setAdapter(personAdapter);
    }

    public void getData(){
        people = new ArrayList<>();
        people.add(new Person(R.drawable.p1,"id01","Lee",27));
        people.add(new Person(R.drawable.p2,"id02","Kim",22));
        people.add(new Person(R.drawable.p3,"id03","Lim",31));
        people.add(new Person(R.drawable.p4,"id04","Park",44));
        people.add(new Person(R.drawable.p5,"id05","Choi",42));
        people.add(new Person(R.drawable.p6,"id06","Hong",56));
        people.add(new Person(R.drawable.p7,"id07","Chae",65));
        people.add(new Person(R.drawable.p8,"id08","Han",45));
        people.add(new Person(R.drawable.p9,"id09","Jeong",32));
        setList(people);

    }


    public void ckbt(View v){
        getData();
    }
}