package com.example.p245;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ProgressDialog progressDialog;
    LinearLayout container;
    LayoutInflater inflater;
    String id;
    String pwd;
    String mail;

    ScrollView scrollView;
    ImageView imageView;
    BitmapDrawable bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);
        inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public void bt(View v){
        if(v.getId() == R.id.button){
            container.removeAllViews();
            inflater.inflate(R.layout.login,container,true);
            progressBar = container.findViewById(R.id.progressBar);
            progressBar.setProgress(100,true);
        }else if(v.getId() == R.id.button2){
            container.removeAllViews();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Register");
            builder.setMessage("Do you want to Register?");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    inflater.inflate(R.layout.update,container,true);
                    TextView title = container.findViewById(R.id.textView);
                    title.setText("Register");
                    Button button = container.findViewById(R.id.button5);
                    button.setText("Register");
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog= builder.create();
            dialog.show();

        }else if(v.getId() == R.id.button3){
            container.removeAllViews();
            inflater.inflate(R.layout.update,container,true);
            TextView title = container.findViewById(R.id.textView);
            title.setText("Read");
            Button button = container.findViewById(R.id.button5);
            button.setText("Read");
        }else if(v.getId() == R.id.button4){
            container.removeAllViews();
            inflater.inflate(R.layout.list,container,true);

//            scrollView = container.findViewById(R.id.scrollview);
//            imageView = container.findViewById(R.id.imageView);
//            scrollView.setHorizontalScrollBarEnabled(true);
//
//            Resources res = getResources();
//            bitmap = (BitmapDrawable) res.getDrawable(R.drawable.d1);
//            int bitmapWidth = bitmap.getIntrinsicWidth();
//            int bitmapHeight = bitmap.getIntrinsicHeight();
//
//            imageView.setImageDrawable(bitmap);
//            imageView.getLayoutParams().width = bitmapWidth;
//            imageView.getLayoutParams().height = bitmapHeight;

        }
    }
    public void clickRegister(View v){
        Button button = container.findViewById(R.id.button5);
        TextView editName = container.findViewById(R.id.editName);
        TextView editPassword = container.findViewById(R.id.editPassword);
        TextView editEmail = container.findViewById(R.id.editEmail);
        String toastMessage=null;
        if (button.getText().equals("Register")){
            id = editName.getText().toString();
            pwd = editPassword.getText().toString();
            mail = editEmail.getText().toString();
            toastMessage = id +" is registerd \n" +
                    "PWD is" + pwd + "\n"+
                    "Email is " + mail ;
        }else if(button.getText().equals("Read")){
            editName.setText(id);
            editPassword.setText(pwd);
            editEmail.setText(mail);
            toastMessage = id +" is selected \n" +
                    "PWD is" + pwd + "\n"+
                    "Email is " + mail ;
        }
        Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();

    }


}