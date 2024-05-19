package com.example.cinemago.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemago.R;
import com.example.cinemago.adapters.MoviesAdapter;
import com.example.cinemago.models.Cinema;
import com.example.cinemago.models.Movie;
import com.example.cinemago.utils.SingletonClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Movie> dataList = new ArrayList<>();
    private MoviesAdapter adapter;
    private DatabaseReference databaseReference;
    TextView addresstv, contacttv, cinemaNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        toolbar.setBackInvokedCallbackEnabled(true);
        setSupportActionBar(toolbar);
        cinemaNameTextView = findViewById(R.id.tvtitle);
        addresstv = findViewById(R.id.address);
        contacttv = findViewById(R.id.contact);

        Cinema data = (Cinema) getIntent().getSerializableExtra("data");
        cinemaNameTextView.setText(data.getName());
        addresstv.setText(data.getAddress());
        contacttv.setText(data.getContact());

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.addreview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoviesActivity.this, AddReviewActivity.class).putExtra("cinemaid", data.getId()));
            }
        });


            findViewById(R.id.add).setVisibility(View.VISIBLE);
            findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MoviesActivity.this, AddMovieActivity.class).putExtra("cinemaid", data.getId()));
                }
            });


        databaseReference = FirebaseDatabase.getInstance().getReference("cinemas").child(data.getId()).child("movies");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MoviesAdapter(this, dataList, data.getId());
        recyclerView.setAdapter(adapter);

        fetchData();

    }

    private void fetchData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.getKey();
                    Movie data = snapshot.getValue(Movie.class);
                    dataList.add(data);
                }
               adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MoviesActivity.this, "Failed to load notifications.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}