package com.example.cinemago.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemago.R;
import com.example.cinemago.adapters.TimesVerticalAdapter;
import com.example.cinemago.models.Movie;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class MovieDetailsActivity extends AppCompatActivity {

    TextView tvtitle;
    TextView tvdesription;
    ImageView imageView;
    RecyclerView recyclerView;
    MaterialButton selectseatbutton;
    String cinemaid = "";
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvtitle = findViewById(R.id.tvtitle);
        tvdesription = findViewById(R.id.description);
        imageView = findViewById(R.id.image);
        selectseatbutton = findViewById(R.id.selectseat);
        recyclerView = findViewById(R.id.recyclerView);

        movie = (Movie) getIntent().getSerializableExtra("data");
        cinemaid =  getIntent().getStringExtra("cinemaid");

        tvtitle.setText(movie.getName());
        tvdesription.setText(movie.getDescription());
        Picasso.get().load(movie.getImage()).into(imageView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TimesVerticalAdapter(this, movie.getShowtimes(), movie));

        Log.e("MovieData", movie.getName());
        Log.e("MovieData", String.valueOf(movie.getShowtimes().size()));

        findViewById(R.id.selectseat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MovieDetailsActivity.this, SeatsActivity.class)
                        .putExtra("movieid", movie.getId())
                        .putExtra("ticketprice", movie.getTicketprice())
                        .putExtra("data", (Serializable) movie)
                        .putExtra("cinemaid", cinemaid));
            }
        });

    }
}