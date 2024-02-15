package com.nafsoft.bulkemailvalidation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_CSV_FILE_REQUEST = 1;
    private ArrayList<EmailItem> emailItemList;
    private EmailAdapter emailAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button pickFileButton = findViewById(R.id.pickFileButton);
        recyclerView = findViewById(R.id.recyclerView);

        pickFileButton.setOnClickListener(view -> pickCsvFile());
        emailItemList = new ArrayList<>();
        emailAdapter = new EmailAdapter(emailItemList);
        recyclerView.setAdapter(emailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void pickCsvFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/csv");
        startActivityForResult(intent, PICK_CSV_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_CSV_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri csvUri = data.getData();
                if (csvUri != null) {
                    readCsvFile(csvUri);
                }
            }
        }
    }

    private void readCsvFile(Uri csvUri) {
        try {
            CSVReader csvReader = new CSVReader(new InputStreamReader(getContentResolver().openInputStream(csvUri)));

            List<String[]> csvData = csvReader.readAll();
            csvReader.close();

            emailItemList.clear();

            for (String[] row : csvData) {
                for (String value : row) {
                    String status = isValidEmail(value) ? "Valid" : "Invalid";
                    emailItemList.add(new EmailItem(value, status));
                }
            }

            if (!emailItemList.isEmpty()) {
                showRecyclerView();
                emailAdapter.notifyDataSetChanged();
            } else {
                hideRecyclerView();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void hideRecyclerView() {

        recyclerView.setVisibility(View.GONE);
    }
}