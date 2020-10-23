package com.example.p427;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Person> persons;
    ConstraintLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        container = findViewById(R.id.container);
    }
    class PersonAdapter extends BaseAdapter{
        ArrayList<Person> datas;

        public PersonAdapter(ArrayList<Person> datas){
            this.datas = datas;
        }
        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
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
            TextView tx_name = view.findViewById(R.id.tx_name);
            TextView tx_phone = view.findViewById(R.id.tx_phone);
            Person p = datas.get(position);
            im.setImageResource(p.getId());
            tx_name.setText(p.getName());
            tx_phone.setText(p.getPhone());

            return view;
        }
    }
    public void setList(ArrayList<Person> persons){
        PersonAdapter personAdapter = new PersonAdapter(persons);
        listView.setAdapter(personAdapter);
    }

    public void getData(){
        persons = new ArrayList<>();
        persons.add(new Person(R.drawable.t1,"Lee malsook","010-1111-2222"));
        persons.add(new Person(R.drawable.t2,"Kim malsook","010-1111-2222"));
        persons.add(new Person(R.drawable.t3,"Lim malsook","010-1111-2222"));
        persons.add(new Person(R.drawable.t4,"Oh malsook","010-1111-2222"));
        persons.add(new Person(R.drawable.t5,"Hong malsook","010-1111-2222"));
        persons.add(new Person(R.drawable.t6,"Moon malsook","010-1111-2222"));
        persons.add(new Person(R.drawable.t7,"Ko malsook","010-1111-2222"));
        persons.add(new Person(R.drawable.t8,"Jeong malsook","010-1111-2222"));
        persons.add(new Person(R.drawable.t9,"Jang malsook","010-1111-2222"));
        setList(persons);
    }

    public void ckbt(View v){
        getData();
    }
}