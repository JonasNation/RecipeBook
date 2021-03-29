package com.example.android.androidsqlconnect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Author
 * Jonas Nation
 * Rasmussen University
 * COP4677C Section 01 Andriod Web and Data Integration Winter Quarter 2021
 * Module 5 Course Project Final Submission
 * Instructor Frances ljeoma
 * The purpose of this app is to take recipes and store it in a public cloud
 * and use Screen Reader functionality
 * I first created my database and table in Azure
 * Then I will connect this app with the SQL database in Azure
 * Insert into the table and display the data in a textview on a second screen
 */

public class MainActivity extends AppCompatActivity {
    EditText prep, cook, serving, ingred, method, create;
    Connection connect;
    String recipes, z, query, query2;
    ProgressBar progressBar;
    Boolean isSuccess = false;
    Button button;
    Statement st, statement;
    ResultSet rs, q2;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar to navigate in between the two screens
        Toolbar my_toolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(my_toolbar);

        // These are the edit text that will take input from user to be stored in SQL database
        prep = findViewById(R.id.preperation);
        prep.requestFocus();
        prep.setError("FIELD CANNOT BE EMPTY");
        cook = findViewById(R.id.cook);
        cook.requestFocus();
        cook.setError("FIELD CANNOT BE EMPTY");
        serving = findViewById(R.id.nutrition);
        serving.requestFocus();
        serving.setError("FIELD CANNOT BE EMPTY");
        ingred = findViewById(R.id.ingredients);
        ingred.requestFocus();
        ingred.setError("FIELD CANNOT BE EMPTY");
        method = findViewById(R.id.methods);
        method.requestFocus();
        method.setError("FIELD CANNOT BE EMPTY");
        create = findViewById(R.id.createdBY);
        create.requestFocus();
        create.setError("FIELD CANNOT BE EMPTY");
        progressBar = findViewById(R.id.progressBar);
        button = (Button) findViewById(R.id.submitButton);

        // Submit button will send and retrieve data to and from the database
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // executes the AsyncTask
                SQLInsertTask sqlInsertTask = new SQLInsertTask();
                sqlInsertTask.execute();

                // sends data from select query to main activity 2 text view
                Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                intent.putExtra("message key", recipes);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates the toolbar so it will be available to navigate to and from both screens
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Lifecycle", "Activity start");
    }
/*
    // Submit button will send and retrieve data to and from the database
    public void getRecordFromSQL(View view) {

        // executes the AsyncTask
      SQLInsertTask sqlInsertTask = new SQLInsertTask();
      sqlInsertTask.execute();

        // sends data from select query to main activity 2 text view
        Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
        intent.putExtra("message key", recipes);
        startActivity(intent);
    }*/

    // menu item to navigate to screen two
    public void onOptionGetRecipe(MenuItem item) {
        startActivity(new Intent(this, MainActivity2.class));
    }

    @SuppressLint("StaticFieldLeak")
    private class SQLInsertTask extends AsyncTask<String, String, String> {

        SQLInsertTask() {}
        // doInBackground will run the query on a separate thread in the background
        // to keep main UI free
        @Override
        protected String doInBackground(String... strings) {
            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connect = connectionHelper.connectionclass();
                if (connect != null) {
                    query = "insert into dbo.recipe_table (preparation_time,cooking_time,nutrition_per_serving,ingredients,methods,created_by)" +
                            " values ('"+prep.getText().toString()+"','"+cook.getText().toString()+"','"+serving.getText().toString()+"','"+ingred.getText().toString()+"','"+method.getText().toString()+"','"+create.getText().toString()+"')";
                    st = connect.createStatement();
                    rs = st.executeQuery(query);

                    query2 = "select * from dbo.recipe_table";
                    statement = connect.createStatement();
                    q2 = statement.executeQuery(query2);

                    while (q2.next()) {
                        // data from column index 1 in database table is assign to variable
                        // the variable is passed to screen two text view via intent message key
                        recipes = rs.getString(1);

                        Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                        intent.putExtra("message key", recipes);
                        startActivity(intent);

                        z = "Query successful!";
                        isSuccess = true;
                    }
                }
                // publish update on the main UI thread
                publishProgress();
            }catch (SQLException | java.sql.SQLException sqlException) {
                Log.e("SQL Error 1: ", sqlException.getMessage());
            }

            return z;
        }
        // show start of AsyncTask execution
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Insert in progress!", Toast.LENGTH_SHORT).show();
        }
        // is executed on the main UI thread after run is complete
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            progressBar.setProgress(0);
            progressBar.setVisibility(View.INVISIBLE);
        }
        // delivers main UI thread updates
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressBar.getProgress();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Lifecycle", "Resume activity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Lifecycle", "Activity paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Lifecycle", "Activity stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Lifecycle", "Activity destroyed");
    }
}

