package com.asad.mediastorefilereadwrite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final int WRITE_REQUEST_CODE = 101;
    private static final int PICK_TEXT_FILE = 102;
    private String []arrInFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //createFile();
        readFile();
    }

    // read existing text file
    private void readFile() {
        // when you create document, you need to add Intent.ACTION_CREATE_DOCUMENT

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        startActivityForResult(intent, PICK_TEXT_FILE);
    }

    // create text file
    private void createFile() {
        // when you create document, you need to add Intent.ACTION_CREATE_DOCUMENT
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);

        // filter to only show openable items.
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Create a file with the requested Mime type
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "SampleFile.txt");

        startActivityForResult(intent, WRITE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WRITE_REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    if (data != null
                            && data.getData() != null) {
                        //writeInFile(data.getData(), "bison, is, bision");
                        readFromFile(data.getData());
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    break;
            }
        }if (requestCode == PICK_TEXT_FILE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    if (data != null
                            && data.getData() != null) {
                        //writeInFile(data.getData(), "bison, is, bision");
                        readFromFile(data.getData());
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    break;
            }
        }
    }

    private void writeInFile(@NonNull Uri uri, @NonNull String text) {
        OutputStream outputStream;
        try {
            outputStream = getContentResolver().openOutputStream(uri);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
            bw.write(text);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void readFromFile(@NonNull Uri uri) {
        InputStream inputStream;
        try {
            inputStream = getContentResolver().openInputStream(uri);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                String line = "";
                int i =-1;
                while((line=br.readLine())!=null)
                {
                    i++;

                    ///////// For the purpose of showing that how many values are comma separated in file that is got read
                    arrInFile = line.split(",");
                    int a = arrInFile.length;
                    String strA = Integer.toString(a);

                    Toast toast5 = Toast.makeText(getApplicationContext(),"Length Of String A is "+strA, Toast.LENGTH_LONG);
                    toast5.setMargin(50, 50);
                    toast5.show();

                    ///////// For the purpose of showing that how many values are comma separated in file that is got read

                    br.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
    }
}
