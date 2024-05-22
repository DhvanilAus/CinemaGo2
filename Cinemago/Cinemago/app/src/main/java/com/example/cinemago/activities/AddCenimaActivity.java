package com.example.cinemago.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinemago.R;
import com.example.cinemago.models.Cinema;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCenimaActivity extends AppCompatActivity {

    EditText name, description, address, lat, lng, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cenima);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        address = findViewById(R.id.address);
        lat = findViewById(R.id.lat);
        lng = findViewById(R.id.lng);
        contact = findViewById(R.id.contact);

        findViewById(R.id.addbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") ||
                        name.getText().toString().equals("") ||
                        description.getText().toString().equals("") ||
                        address.getText().toString().equals("") ||
                        lat.getText().toString().equals("") ||
                        lng.getText().toString().equals("") ||
                        contact.getText().toString().equals("")
                )
                {
                    Toast.makeText(AddCenimaActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    saveData();
                }
            }
        });

    }

    private void saveData() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Cinema Data...");
        progressDialog.show();

        Cinema cinema = new Cinema();
        cinema.setName(name.getText().toString());
        cinema.setDescription(description.getText().toString());
        cinema.setAddress(address.getText().toString());
        cinema.setLat(lat.getText().toString());
        cinema.setLng(lng.getText().toString());
        cinema.setContact(contact.getText().toString());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("cinemas");
        String id = reference.push().getKey();
        cinema.setId(id);
        reference.child(id).setValue(cinema).addOnCompleteListener(task1 -> {
            progressDialog.dismiss();
            if (task1.isSuccessful()) {
                finish();
            } else {
                Toast.makeText(AddCenimaActivity.this, "Failed to add. Try again", Toast.LENGTH_LONG).show();
            }
        });
    }

}