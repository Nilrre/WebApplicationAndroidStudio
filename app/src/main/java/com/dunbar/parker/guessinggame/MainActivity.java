package com.dunbar.parker.guessinggame;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
//    DownloadContent downloadContent;

    TestAsync test;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Document doc;

        test = new TestAsync();
        test.execute("https://www.pcauthority.com.au/news/top-10-computer-games-of-all-time-170181");





//        try {
//            URL url = new URL("https://www.pcauthority.com.au/news/top-10-computer-games-of-all-time-170181");
//            downloadContent = new DownloadContent();
//            downloadContent.execute(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
    }

        private class TestAsync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            Element element = null;
            try {
                Document doc = Jsoup.connect("https://www.pcauthority.com.au/news/top-10-computer-games-of-all-time-170181").get();
                element = doc.select("div#article-body").first();
                Log.d("TEST", element.text());

                List<Element> list = element.select("b");
                for(Element e : list) {
                    Log.d("TAGS",e.text());
                }

                List<Element> image_list = element.select("a");
                List<String> images = new ArrayList<String>();
                for(Element e : image_list) {
                    String link = e.html().toString();
                    if(link.contains("img")) {
                        images.add(link);
                    }
                }
                for(String e : images) {
                    Log.d("TAGS",e);
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
