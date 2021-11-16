package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.todo.pojos.Todo;

public class MainActivity extends AppCompatActivity {

    private  final String TAG ="ToDoList";
    private static final String KEY_TODOLIST ="todoList";

    //declaration
    private Context context;
    private Button btnAdd;
    private Button btnCancel;
    private TextView tvTodoList;

    private String todoList;

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addTodo:
                //crée un Intent pour ensuite démarrer CheatActivity
                Intent intent = new Intent(context, AddTodoActivity.class);

                startActivityForResult(intent,2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTodoList = findViewById(R.id.tvTodoList);
        context = getApplicationContext();
        if (savedInstanceState != null){
            todoList = savedInstanceState.getString(KEY_TODOLIST);
            tvTodoList.setText(todoList);
        }
    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            if (resultCode == RESULT_OK){
                Todo todo = (Todo) data.getSerializableExtra("todo");
                    todoList = tvTodoList.getText().toString()+"\n"+"["+todo.getUrgency()+"] "+todo.getName();
                    tvTodoList.setText(todoList);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState() called");
        // Key is string
        outState.putString(KEY_TODOLIST, todoList);
    }

}