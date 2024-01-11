package com.example.breakingbad;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> implements Filterable {

    private final String TAG = getClass().getSimpleName();
    private List<Character> charactersList;
    private CharacterAdapterOnClickHandle mClickHandler;
    private List<Character> charactersListAll;


    public interface CharacterAdapterOnClickHandle {
        void onClick(int position);
    }

    public CharacterAdapter(ArrayList<Character> charactersList, CharacterAdapterOnClickHandle mClickHandler) {
        Log.d(TAG, "Constructor aangeroepen");
        this.charactersList = charactersList;
        this.mClickHandler = mClickHandler;
        charactersListAll = new ArrayList<>(charactersList);
    }


    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreate aangeroepen");

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.character_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new CharacterViewHolder(view, mClickHandler);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount aangeroepen. "+ charactersList.size() + " items.");
        return charactersList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder aangeroepen.");
    Character character = charactersList.get(position);

    holder.mCharacterName.setText(character.getName());
    holder.mCharacterNick.setText("Nickname: "+ character.getNick());
    holder.mCharacterStatus.setText("Status: "+ character.getStatus());
    Picasso
            .get()
            .load(character.getImgUrl())
            .resize(300, 300)
            .into(holder.mCharacterImage);

    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        public TextView mCharacterName;
        public ImageView mCharacterImage;
        public TextView mCharacterNick;
        public TextView mCharacterStatus;


        CharacterAdapterOnClickHandle characterAdapterOnClickHandle;
        public CharacterViewHolder(@NonNull View itemView, CharacterAdapterOnClickHandle characterAdapterOnClickHandle) {

            super(itemView);
            Log.d(TAG, "Constructor aangeroepen.");
            itemView.setOnClickListener(this);
            this.characterAdapterOnClickHandle = characterAdapterOnClickHandle;
            mCharacterImage = (ImageView) itemView.findViewById(R.id.character_list_item_image);
            mCharacterName = (TextView) itemView.findViewById(R.id.character_list_item_name);
            mCharacterNick = (TextView) itemView.findViewById(R.id.character_list_item_nick);
            mCharacterStatus = (TextView) itemView.findViewById(R.id.character_list_item_status);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            Log.d(TAG, "onClick aangeroepen.");
        characterAdapterOnClickHandle.onClick(getAdapterPosition());
        }
    }
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        // run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charS) {
        Log.d(TAG, "Filter is performed in the background.");
            List<Character> filteredList = new ArrayList<>();
            if (charS.toString().isEmpty()) {
                filteredList.addAll(charactersListAll);

            } else{
                String filterPattern = charS.toString().toLowerCase().trim();
                for (Character charac : charactersListAll){
                    if(charac.getName().toLowerCase().contains(filterPattern)
                            || charac.getNick().toLowerCase().contains(filterPattern)){
                        filteredList.add(charac);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            Log.d("filtering", "Amount of results: " + filteredList.size());

            return filterResults;
        }
        // runs on a ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            Log.d(TAG, "Filter results are being published.");
            charactersList.clear();
            charactersList.addAll((Collection<? extends Character>) filterResults.values);
            notifyDataSetChanged();

        }
    };

}
