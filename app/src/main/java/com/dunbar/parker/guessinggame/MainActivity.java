package com.dunbar.parker.guessinggame;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
//    DownloadContent downloadContent;

    TestAsync test;
    HashMap<String, String> imagesAndPictures = new HashMap<>();
    HashMap<Integer, String> numbersAndTitles  = new HashMap<>();
    ArrayList<Integer> answers = new ArrayList<>();
    Random random = new Random(10);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Document doc;

        test = new TestAsync();
        test.execute("https://www.pcauthority.com.au/news/top-10-computer-games-of-all-time-170181");

        int x = 0;
        for (Map.Entry<String,String> entry : imagesAndPictures.entrySet()) {
            String key = entry.getKey();
            numbersAndTitles.put(x++, key);
        }

        shitGameLogic();


//        try {
//            URL url = new URL("https://www.pcauthority.com.au/news/top-10-computer-games-of-all-time-170181");
//            downloadContent = new DownloadContent();
//            downloadContent.execute(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
    }

    public void shitGameLogic() {
        ArrayList<Integer> randomList = randomNumberGenerator();
        Button b1 = findViewById(R.id.ans1);
        Button b2 = findViewById(R.id.ans2);
        Button b3 = findViewById(R.id.ans3);
        Button b4 = findViewById(R.id.ans4);
        b1.setText(numbersAndTitles.get(randomList.get(0)));
        b1.setText(numbersAndTitles.get(randomList.get(1)));
        b1.setText(numbersAndTitles.get(randomList.get(2)));
        b1.setText(numbersAndTitles.get(randomList.get(3)));

        answers.add(randomList.get(0));

    }

    public ArrayList<Integer> randomNumberGenerator(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<11; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        return list;
    }


    private class TestAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            Element element = null;
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
                    imagesAndPictures.put(list.get(i).text().substring(3).trim(), images.get(i).substring(73, images.get(i).length() - 84));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return element.text();
        }
    }


//    private String downloadUrl(URL url) throws IOException {
//        InputStream stream = null;
//        HttpURLConnection connection = null;
//        String result = null;
//        try {
//            connection = (HttpsURLConnection) url.openConnection();
//            connection.setReadTimeout(3000);
//            connection.setConnectTimeout(30000);
//            connection.setRequestMethod("GET");
//            connection.setDoInput(true);
//            connection.connect();
//            int responseCode = connection.getResponseCode();
//            if (responseCode != HttpsURLConnection.HTTP_OK) {
//                throw new IOException("HTTP error code: " + responseCode);
//            }
//            // Retrieve the response body as an InputStream.
//            stream = connection.getInputStream();
//            if (stream != null) {
//                // Converts Stream to String with max length of 500.
////                result = readStream(stream, 100000);
//            }
//        }catch (Exception e) {
//            System.out.println(e.toString());
//        } finally {
//            // Close Stream and disconnect HTTPS connection.
//            if (stream != null) {
//                stream.close();
//            }
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//        return result;
//    }


//    public String readStream(InputStream stream, int maxReadSize) throws IOException, UnsupportedEncodingException {
//        Reader reader = null;
//        reader = new InputStreamReader(stream, "UTF-8");
//        char[] rawBuffer = new char[maxReadSize];
//        int readSize;
//        StringBuffer buffer = new StringBuffer();
//        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
//            if (readSize > maxReadSize) {
//                readSize = maxReadSize;
//            }
//            buffer.append(rawBuffer, 0, readSize);
//            maxReadSize -= readSize;
//        }
//        return buffer.toString();
//    }


//    private class DownloadContent extends AsyncTask<URL, Void, String> {
//        @Override
//        protected String doInBackground(URL... urls) {
//            String result = "";
//            try {
//                result = downloadUrl(urls[0]);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String results) {
//            Log.d("LENGTH", "" + results.length());
//            Log.d("TEST", results);
//            parseResults(results);
//        }
//    }

}
