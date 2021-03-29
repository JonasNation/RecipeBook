package com.example.android.androidsqlconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Connection;

public class MainActivity2 extends AppCompatActivity  {
    Connection connect;
    String ConnectionResult;
    TextView records;
 //   Boolean isSuccess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Toolbar to navigate in between the two screens
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        records = (TextView) findViewById(R.id.textViewRecords);
        // intent receives data passed from main activity and displays it in a text view records
        Intent intent = getIntent();
        String recipes = intent.getStringExtra("message key");
        records.setText(recipes);
    }
/*
    public void getRecipe(View view) {
        DisplayTask displayTask = new DisplayTask();
        displayTask.execute();
       String recipes;
       try {
            ConnectionHelper2 connectionHelper2 = new ConnectionHelper2();
             connect = connectionHelper2.connectionClass();
            if (connect == null) {
                String query = "SELECT * FROM dbo.recipe_table";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    recipes = rs.getString("recipeId");
                    records.setText(recipes);
                    isSuccess = true;
                }
            }else {
                isSuccess = false;
            }
        }catch (SQLException | java.sql.SQLException sqlException) {
            Log.e("Error: ", sqlException.getMessage());
            Toast.makeText(this, "Displaying Records", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates the toolbar so it will be available to navigate to and from both screens
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_2, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // menu item to navigate to screen one
    public void onOptionMakeNewRecipe(MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
    }
/*
    @SuppressLint("StaticFieldLeak")
    private class DisplayTask extends AsyncTask<String, String, String> {

        String z = "";
        Boolean isSuccess = false;
        String recipes;
        TextView records;

        DisplayTask() {}

        @Override
        protected String doInBackground(String... strings) {
            try {
                ConnectionHelper2 connectionHelper2 = new ConnectionHelper2();
                connect = connectionHelper2.connectionClass();
                if (connect == null) {
                    String query = "SELECT * FROM dbo.recipe_table Where recipeId = 1;";
                    Statement st = connect.createStatement();
                    ResultSet rs = st.executeQuery(query);

                    while (rs.next()) {
                        records = (TextView) findViewById(R.id.textViewRecords);
                        recipes = rs.getString(1);
                        records.setText(recipes);
                        z = "Query successful!";
                        isSuccess = true;
                    }
                }else {
                    z = "Invalid Query";
                    isSuccess = false;
                }
                publishProgress();
            }catch (java.sql.SQLException sqlException) {
                ConnectionResult = "Check connection";
                Log.e("SQL Error 1: ", sqlException.getMessage());
            }

            return "Your Recipes";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity2.this, "Getting records", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(MainActivity2.this, s, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                records = (TextView) findViewById(R.id.textViewRecords);
                records.setText(recipes);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }
    }*/
}