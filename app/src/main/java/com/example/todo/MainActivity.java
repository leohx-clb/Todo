package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.todo.dao.TodoDAO;
import com.example.todo.pojos.Todo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  final String TAG ="ToDoList";
    private static final String KEY_TODOLIST ="todoList";

    //declaration
    private TodoDAO todoDAO;
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

                startActivity(intent);
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

        todoDAO = new TodoDAO(context);

        if (savedInstanceState != null){
            todoList = savedInstanceState.getString(KEY_TODOLIST);
            tvTodoList.setText(todoList);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG,"onSaveInstanceState() called");
        // Key is string
        outState.putString(KEY_TODOLIST, todoList);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TodoAsyncTask todoAsyncTask = new TodoAsyncTask();
        todoAsyncTask.execute();

    }

    public class TodoAsyncTask extends AsyncTask<String, String, List<Todo>> {

        @Override
        protected List<Todo> doInBackground(String... strings) {
            List<Todo> responseTodo = new ArrayList<>();
            try {
                responseTodo = todoDAO.list();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return responseTodo;
        }

        @Override
        protected void onPostExecute(List<Todo> todos) {
            String newTodos = "";
            for (Todo todo : todos){
                newTodos += "["+todo.getUrgency()+"]" + " " + todo.getName() + "\n";
                Log.d("todos",todo.getName());
            }
            tvTodoList.setText(newTodos);
        }
    }
}