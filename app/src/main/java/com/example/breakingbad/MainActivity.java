package com.example.breakingbad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CharacterAPITask.CharacterListener, CharacterAdapter.CharacterAdapterOnClickHandle {

    private ArrayList<Character> characters = new ArrayList<>();
    private RecyclerView charactersRecyclerView;
    private CharacterAdapter characterAdapter;
    private final String TAG = getClass().getSimpleName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "onCreate aangeroepen");
        charactersRecyclerView = (RecyclerView) findViewById(R.id.characters_recycler_view);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            charactersRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
        }
        else{
            charactersRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        }

        characterAdapter = new CharacterAdapter(characters, this);
        charactersRecyclerView.setAdapter(characterAdapter);

        String[] params = {
                "https://www.breakingbadapi.com/api/characters"
        };

        new CharacterAPITask(this).execute(params);
    }


    public void onClick(int position) {
        Log.d("MainActivity", "onClick aangeroepen");
        String name = characters.get(position).getName();
        String nick = characters.get(position).getNick();
        String img = characters.get(position).getImgUrl();
        String status = characters.get(position).getStatus();
        String birth = characters.get(position).getBirth();
        ArrayList<String> occu = characters.get(position).getOccupation();
        ArrayList<Integer> appear = characters.get(position).getAppearance();
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(this, destinationClass);
        intentToStartDetailActivity.putExtra("NAME", name);
        intentToStartDetailActivity.putExtra("NICK", nick);
        intentToStartDetailActivity.putExtra("IMAGE", img);
        intentToStartDetailActivity.putExtra("STATUS", status);
        intentToStartDetailActivity.putExtra("BIRTH", birth);
        intentToStartDetailActivity.putExtra("OCCUPATION", occu);
        intentToStartDetailActivity.putExtra("APPEARANCES", appear);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public void onCharactersAvailable(List<Character> characters) {
        Log.d(TAG, "We have " +characters.size() +" items");
        this.characters.clear();
        this.characters.addAll(characters);
        this.characterAdapter.notifyDataSetChanged();
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        CharSequence text = "Aantal items: " + characterAdapter.getItemCount();
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "De zoekbar wordt aangeroepen");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                characterAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }



}