package com.vanlam.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PostListener {
    private RecyclerView recyclerViewPost;
    private PostAdapter adapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postList = new ArrayList<>();
        recyclerViewPost = findViewById(R.id.rcv_posts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new PostAdapter(postList, this);

        recyclerViewPost.setLayoutManager(linearLayoutManager);
        recyclerViewPost.setAdapter(adapter);

        new ReadRSS().execute("https://danviet.vn/rss/home.rss");
    }

    @Override
    public void onClick(Post post, int position) {
        Intent intent = new Intent(MainActivity.this, OverviewActivity.class);
        intent.putExtra("postData", post);
        startActivity(intent);
    }

    @SuppressLint("StaticFieldLeak")
    class ReadRSS extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader input = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader reader = new BufferedReader(input);
                String line = "";

                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDomParser domParser = new XMLDomParser();
            Document document = domParser.getDocument(s);
            NodeList itemNodeList = document.getElementsByTagName("item");

            for (int i = 0; i < itemNodeList.getLength(); i++) {
                Element element = (Element) itemNodeList.item(i);

                String title = element.getElementsByTagName("title").item(0).getTextContent();
                String descriptionText = element.getElementsByTagName("description").item(0).getTextContent();
                String imageLink = getImageLink(descriptionText);
                String description = getDescriptionContent(descriptionText);
                String link = element.getElementsByTagName("link").item(0).getTextContent();
                String pubDateString = element.getElementsByTagName("pubDate").item(0).getTextContent();
                String pubDate = parseDate(pubDateString);

                Post post = new Post(title, description, link, imageLink, pubDateString);
                postList.add(post);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private String getImageLink(String input) {
        String srcImage = "";
        int srcStartIndex = input.indexOf("src=\"");
        if (srcStartIndex != -1) {
            srcStartIndex += 5;
            int srcEndIndex = input.indexOf("\"", srcStartIndex);
            if (srcEndIndex != -1) {
                srcImage += input.substring(srcStartIndex, srcEndIndex);
            }
        }
        return srcImage;
    }

    private String getDescriptionContent(String input) {
        String desc = "";
        int startIndex = input.indexOf("</a>");
        if (startIndex != -1) {
            int endIndex = startIndex + 5;
            desc += input.substring(endIndex);
        }
        return desc;
    }

    private String parseDate(String input) {
        String formattedDate = "";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = inputFormat.parse(input);

            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
}