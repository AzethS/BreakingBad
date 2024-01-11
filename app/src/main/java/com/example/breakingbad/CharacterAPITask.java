package com.example.breakingbad;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CharacterAPITask extends AsyncTask<String, Void, List<Character>> {

    private final String TAG = getClass().getSimpleName();
    private final String JSON_CHARACTER_NAME = "name";
    private final String JSON_CHARACTER_NICKNAME = "nickname";
    private final String JSON_CHARACTER_IMAGE = "img";
    private final String JSON_CHARACTER_STATUS = "status";
    private final String JSON_CHARACTER_BIRTHDAY = "birthday";
    private final String JSON_CHARACTER_OCCUPATION = "occupation";
    private final String JSON_CHARACTER_APPEARANCE = "appearance";

    private CharacterListener listener = null;

    public CharacterAPITask(CharacterListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Character> doInBackground(String... params) {

        // URL maken of ophalen of samenstellen
        String characterUrlString = params[0];
        HttpURLConnection urlConnection = null;

        URL url = null;
        try{
            url = new URL(characterUrlString);

           urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    String response = scanner.next();
                    Log.d(TAG, "response: " + response);

                    ArrayList<Character> resultList = convertJsonToArrayList(response);
                    return resultList;

                } else {
                    return null;
                }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != urlConnection) {
                urlConnection.disconnect();
            }
    }
        // request bouwen en versturen
        // response afwachten en bewerken
        // ArrayList maken.

        return null;
    }

    @Override
    protected void onPostExecute(List<Character> characters) {
        super.onPostExecute(characters);
        Log.d(TAG, " in onPostExecute: " + characters.size() + " items.");

        // Lijst van characters naar adapter brengen
        listener.onCharactersAvailable(characters);


    }
    // Hulpmethode
    private ArrayList<Character> convertJsonToArrayList(String response){
        ArrayList<Character> resultList = new ArrayList<>();

        //Omzetting van String naar ArrayList

        try {
            JSONArray characters = new JSONArray(response);

            for (int i = 0; i<characters.length(); i++){
                ArrayList<String> occupations = new ArrayList<>();
                ArrayList<Integer> appearances = new ArrayList<>();
                JSONObject actor = characters.getJSONObject(i);
                String name = actor.getString(JSON_CHARACTER_NAME);
                String nickname = actor.getString(JSON_CHARACTER_NICKNAME);
                String image = actor.getString(JSON_CHARACTER_IMAGE);
                String status = actor.getString(JSON_CHARACTER_STATUS);
                String birth = actor.getString(JSON_CHARACTER_BIRTHDAY);
                JSONArray occupationA = actor.getJSONArray(JSON_CHARACTER_OCCUPATION);
                JSONArray appearanceA = actor.getJSONArray(JSON_CHARACTER_APPEARANCE);

                // Loop for occupation arrays
                for(int p=0; p<occupationA.length();p++){
                    String occupation = (String) occupationA.get(p);
                    occupations.add(occupation);
                }
                // Loop for appearance arrays
                for(int z=0; z<appearanceA.length();z++){
                    int appear = (Integer) appearanceA.get(z);
                    appearances.add(appear);
                }
                // Adding the character
                resultList.add(new Character(name, image, nickname, status, birth, occupations, appearances));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "returning " + resultList.size()+ " items.");
        return resultList;
    }

    public interface CharacterListener{
        public void onCharactersAvailable(List<Character> characters);
    }
}
