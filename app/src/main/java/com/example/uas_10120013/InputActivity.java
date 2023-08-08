package com.example.uas_10120013;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

// NIM : 10120013
// NAMA : Jordan Januar Ilhami G
// KELAS : IF 1
public class InputActivity extends AppCompatActivity {

    EditText edtTitle, edtCategory, edtNote;
    TextView textAdd;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        edtTitle = findViewById(R.id.edt_title);
        edtNote = findViewById(R.id.edt_note);
        edtCategory = findViewById(R.id.edt_category);
        textAdd = findViewById(R.id.text_add);

        Button btn = findViewById(R.id.btn_save);

        DAONotes dao = new DAONotes();

        intent = getIntent();

        Notes emp_edit = (Notes) getIntent().getSerializableExtra("EDIT");

        if (emp_edit != null) {
            btn.setText("UPDATE");
            edtTitle.setText(emp_edit.getTitle());
            edtCategory.setText(emp_edit.getCategory());
            edtNote.setText(emp_edit.getNote());
            textAdd.setText("EDIT NOTE");
        } else {
            btn.setText("SUBMIT");
        }

        btn.setOnClickListener(v ->
        {
            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            Notes emp = new Notes(edtTitle.getText().toString(), edtNote.getText().toString(), formattedDate, edtCategory.getText().toString());
            if (emp_edit == null) {
                dao.add(emp).addOnSuccessListener(suc ->
                {
                    Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(er ->
                {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            } else {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("title", edtTitle.getText().toString());
                hashMap.put("note", edtNote.getText().toString());
                hashMap.put("date", formattedDate);
                hashMap.put("category", edtCategory.getText().toString());
                dao.update(emp_edit.getKey(), hashMap).addOnSuccessListener(suc ->
                {
                    Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(er ->
                {
                    Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}