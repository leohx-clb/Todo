package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.pojos.Todo;

public class AddTodoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //declaration
    private Context context;
    private Button btnAdd;
    private Button btnCancel;
    private TextView tvTodo;
    private Spinner spinner;

    //items list
    String[] urgency = {"Low Urgency", "Moderately Urgency", "Hight Urgency"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        context = getApplicationContext();

        //spinner
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,urgency);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        //button
        btnAdd = findViewById(R.id.buttonAdd);
        btnCancel = findViewById(R.id.buttonCancel);
        tvTodo = findViewById(R.id.etTodo);
        spinner = findViewById(R.id.spinner);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo todo = new Todo(tvTodo.getText().toString(),spinner.getSelectedItem().toString());
                Intent resultIntent = new Intent();
                resultIntent.putExtra("todo", todo);
                setResult(RESULT_OK, resultIntent);
                //affiche les valeur de la todo
                Toast.makeText(context,todo.getName()+todo.getUrgency(),Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                setResult(RESULT_CANCELED, resultIntent);
                finish();
                Toast.makeText(context,"check cancel",Toast.LENGTH_LONG).show();
            }
        });
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),urgency[position] , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Toast.makeText(getApplicationContext(),urgency[1] , Toast.LENGTH_LONG).show();
    }

}