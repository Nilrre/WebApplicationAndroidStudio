package com.dunbar.parker.guessinggame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    TestAsync test;
    HashMap<String, String> imagesAndPictures = new HashMap<>();
    HashMap<Integer, String> numbersAndTitles = new HashMap<>();
    ArrayList<Integer> answers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Document doc;

        test = new TestAsync();
        try {
            imagesAndPictures = test.execute("https://www.pcauthority.com.au/news/top-10-computer-games-of-all-time-170181").get();
        } catch (InterruptedException | ExecutionException ex) {
        }

        int x = 0;
        for (Map.Entry<String, String> entry : imagesAndPictures.entrySet()) {
            String key = entry.getKey();
            numbersAndTitles.put(x++, key);
        }

        shitGameLogic();

    }


    public void shitGameLogic() {
        ArrayList<Integer> randomList = randomNumberGenerator();

        Button b1 = findViewById(R.id.ans1);
        Button b2 = findViewById(R.id.ans2);
        Button b3 = findViewById(R.id.ans3);
        Button b4 = findViewById(R.id.ans4);
        b1.setText(numbersAndTitles.get(randomList.get(0)));
        b2.setText(numbersAndTitles.get(randomList.get(1)));
        b3.setText(numbersAndTitles.get(randomList.get(2)));
        b4.setText(numbersAndTitles.get(randomList.get(3)));
        answers.add(randomList.get(0));
        String url = imagesAndPictures.get(numbersAndTitles.get(randomList.get(0)));


        new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(url);


    }


    public ArrayList<Integer> randomNumberGenerator() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        return list;
    }

    public void correctAnswer(View v) {
        Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
        shitGameLogic();
    }

    public void incorrectAnswer(View view) {
        Toast.makeText(this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
    }

    private class TestAsync extends AsyncTask<String, Void, HashMap<String, String>> {
        @Override
        protected HashMap<String, String> doInBackground(String... strings) {
            Element element = null;
            HashMap<String, String> iAndP = new HashMap<>();
            try {
                Document doc = Jsoup.connect("https://www.pcauthority.com.au/news/top-10-computer-games-of-all-time-170181").get();
                element = doc.select("div#article-body").first();

                List<Element> list = element.select("b");
                List<Element> image_list = element.select("a");
                List<String> images = new ArrayList<String>();
                for (Element e : image_list) {
                    String link = e.html().toString();
                    if (link.contains("img")) {
                        images.add(link);
                    }
                }

                for (int i = 2; i < list.size(); i++) {
                    iAndP.put(list.get(i).text().substring(3).trim(), images.get(i).substring(73, images.get(i).length() - 84));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return iAndP;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

