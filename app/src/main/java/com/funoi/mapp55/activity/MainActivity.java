package com.funoi.mapp55.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.funoi.mapp55.R;
import com.funoi.mapp55.service.SubmitService;
import com.funoi.mapp55.vo.Student;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // edu
        setSpinner();

        Button submit = findViewById(R.id.sumit);
        submit.setOnClickListener(v -> {
            new Thread(() -> {

                // name
                String name = ((EditText) findViewById(R.id.name)).getText().toString();
                // sex
                RadioGroup rg = findViewById(R.id.sex);
                String sex = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                // love
                StringBuilder sb = new StringBuilder();
                CheckBox a = findViewById(R.id.a);
                CheckBox c = findViewById(R.id.c);
                CheckBox g = findViewById(R.id.g);
                List<CheckBox> checkBoxes = new ArrayList<>();
                checkBoxes.add(a);
                checkBoxes.add(c);
                checkBoxes.add(g);
                for (CheckBox x : checkBoxes) {
                    if (x.isChecked()) {
                        sb.append(x.getText().toString()).append(" ");
                    }
                }
                String love = sb.toString().trim();
                // edu
                String edu = ((Spinner) findViewById(R.id.edu)).getSelectedItem().toString();
                // intro
                String intro = Objects.requireNonNull(((TextInputEditText) findViewById(R.id.intro)).getText()).toString();


                String url = "http://172.28.99.151:8080/ms1/addStu";
                Student student = new Student(name, sex, love, edu, intro);

                Gson gson = new Gson();
                String json = gson.toJson(student);

                SubmitService submitService = new SubmitService();
                try {
                    String result = submitService.submitDataByPost(url, json);
                    new Handler(Looper.getMainLooper()).post(() ->
                            Snackbar.make(findViewById(R.id.sumit), result, Snackbar.LENGTH_LONG).show()
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });
    }

    public void setSpinner() {
        Spinner edu = findViewById(R.id.edu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.edu_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        edu.setAdapter(adapter);
    }
}