package com.example.tiktok.information;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiktok.MainActivity;
import com.example.tiktok.R;

import java.util.Calendar;

public class InformationActivity extends AppCompatActivity {

    private String Name;
    private String Id;
    private String Text;
    private String Sex;
    private String nowSex;
    private String Date;
    private String Rotation;
    private String School;

    private TextView tvName;
    private TextView tvId;
    private TextView tvText;
    private TextView tvSex;
    private TextView tvDate;
    private TextView tvRotation;
    private TextView tvSchool;

    private final String SP_HEART = "sp_mine";
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        sp = this.getSharedPreferences(SP_HEART, Context.MODE_PRIVATE);
        editor = sp.edit();

        ConstraintLayout changeName = findViewById(R.id.change_name);
        ConstraintLayout changeId = findViewById(R.id.change_id);
        ConstraintLayout changeText = findViewById(R.id.change_text);
        ConstraintLayout changeSex = findViewById(R.id.change_sex);
        ConstraintLayout changeDate = findViewById(R.id.change_date);
        ConstraintLayout changeRotation = findViewById(R.id.change_rotation);
        ConstraintLayout changeSchool = findViewById(R.id.change_school);

        tvName = findViewById(R.id.change_nametv);
        tvId = findViewById(R.id.change_idtv);
        tvText = findViewById(R.id.change_texttv);
        tvSex = findViewById(R.id.change_sextv);
        tvDate = findViewById(R.id.change_datetv);
        tvRotation = findViewById(R.id.change_rotationtv);
        tvSchool = findViewById(R.id.change_schooltv);

        ImageView imgBack = findViewById(R.id.img_back);

        fresh(this.getWindow().getDecorView());

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changename(v);
            }
        });
        changeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeid(v);
            }
        });
        changeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changetext(v);
            }
        });
        changeSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changesex(v);
            }
        });
        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedate(v);
            }
        });
        changeRotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changerotation(v);
            }
        });
        changeSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeschool(v);
            }
        });
    }

    private void changeschool(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入");
        View view = getLayoutInflater().inflate(R.layout.inputlayout,null);
        final EditText etInput = (EditText) view.findViewById(R.id.editText);
        etInput.setText(tvSchool.getText());
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                School = etInput.getText().toString();
                editor.putString("School",School);
                editor.apply();
                fresh(v);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void changerotation(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入");
        View view = getLayoutInflater().inflate(R.layout.inputlayout,null);
        final EditText etInput = (EditText) view.findViewById(R.id.editText);
        etInput.setText(tvRotation.getText());
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Rotation = etInput.getText().toString();
                editor.putString("Rotation",Rotation);
                editor.apply();
                fresh(v);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void changedate(View v) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this,DatePickerDialog.THEME_HOLO_DARK,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        String text = year + "-" + (month+1) + "-" + dayOfMonth;
                        editor.putInt("Year",year);
                        editor.putString("Date",text);
                        editor.apply();
                        fresh(v);
                    }
                }
                ,calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void changesex(View v) {
        final String[] item = new String[]{"男", "女"};
        android.app.AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("请选择")//默认为0表示选中第一个项目
                .setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("infor", "onClick: "+item[which]);
                        nowSex = item[which];
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Sex = nowSex;
                        editor.putString("Sex",Sex);
                        editor.apply();
                        fresh(v);
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        alertDialog.show();
    }

    private void changetext(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入");
        View view = getLayoutInflater().inflate(R.layout.inputlayout,null);
        final EditText etInput = (EditText) view.findViewById(R.id.editText);
        etInput.setText(tvText.getText());
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Text = etInput.getText().toString();
                editor.putString("Text",Text);
                editor.apply();
                fresh(v);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void changeid(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入");
        View view = getLayoutInflater().inflate(R.layout.inputlayout,null);
        final EditText etInput = (EditText) view.findViewById(R.id.editText);
        etInput.setText(tvId.getText());
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Id = etInput.getText().toString();
                editor.putString("Id",Id);
                editor.apply();
                fresh(v);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void changename(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入");
        View view = getLayoutInflater().inflate(R.layout.inputlayout,null);
        final EditText etInput = (EditText) view.findViewById(R.id.editText);
        etInput.setText(tvName.getText());
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Name = etInput.getText().toString();
                editor.putString("Name",Name);
                editor.apply();
                fresh(v);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }



    private void fresh(View v) {
        tvName.setText(sp.getString("Name",""));
        tvId.setText(sp.getString("Id",""));
        tvText.setText(sp.getString("Text",""));
        tvSex.setText(sp.getString("Sex",""));
        tvDate.setText(sp.getString("Date",""));
        tvRotation.setText(sp.getString("Rotation",""));
        tvSchool.setText(sp.getString("School",""));
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
