package com.example.smanoj.androideatit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.smanoj.androideatit.Interface.ItemClickListener;
import com.example.smanoj.androideatit.Model.Food;
import com.example.smanoj.androideatit.ViewHolder.foodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {




    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    FirebaseDatabase database;
    DatabaseReference foodList;

    String CategoryId="";
    FirebaseRecyclerAdapter<Food,foodViewHolder> adapter;



    //Search Fuctionalities
    FirebaseRecyclerAdapter<Food,foodViewHolder> searchadapter;
    List<String> suggestList= new ArrayList<>();
    MaterialSearchBar materialSearchBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);


        //Firebase

        database=FirebaseDatabase.getInstance();
        foodList=database.getReference("Foods");


        recyclerView=(RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        //get intent here
        if(getIntent() !=null)
            CategoryId=getIntent().getStringExtra("CategoryId");
        if(!CategoryId.isEmpty() && CategoryId !=null)
        {
            loadListFood(CategoryId);
        }

        //Search

        materialSearchBar= (MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Enter your food");
        //materialSearchBar.setSpeechMode(false);
        loadSuggest();


        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                List<String> suggest =new ArrayList<String>();

                for(String search:suggestList)
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                     startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void startSearch(CharSequence text) {

        searchadapter= new FirebaseRecyclerAdapter<Food, foodViewHolder>(

                Food.class,
                R.layout.food_item,
                foodViewHolder.class,
                foodList.orderByChild("Name").equalTo(text.toString())
        ) {

            @Override
            protected void populateViewHolder(foodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);

                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start new activity
                        Intent foodDetail =new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",searchadapter.getRef(position).getKey());
                        startActivity(foodDetail);

                    }
                });


            }
        };

        recyclerView.setAdapter(searchadapter);
    }

    private void loadSuggest() {
        foodList.orderByChild("MenuId").equalTo(CategoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Food item=postSnapshot.getValue(Food.class);
                            suggestList.add(item.getName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void loadListFood(String categoryId) {

        adapter= new FirebaseRecyclerAdapter<Food, foodViewHolder>(Food.class,
                R.layout.food_item
                ,foodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId)

                ) {
            @Override
            protected void populateViewHolder(foodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.food_image);

                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                      //Start new activity
                        Intent foodDetail =new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetail);

                    }
                });
            }
        };

       //set Adapter
        Log.d("TAG",""+adapter.getItemCount());
        recyclerView.setAdapter(adapter);


    }


}
