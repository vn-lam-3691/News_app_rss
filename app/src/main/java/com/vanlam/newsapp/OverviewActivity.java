package com.vanlam.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OverviewActivity extends AppCompatActivity {
    private RoundedImageView imagePost;
    private TextView titlePost, pubDate, description;
    private MaterialButton btnDetail;
    private ImageView imgBack;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        imagePost = findViewById(R.id.image_post);
        titlePost = findViewById(R.id.title_post);
        pubDate = findViewById(R.id.tv_pubDate);
        description = findViewById(R.id.tv_description);
        btnDetail = findViewById(R.id.btnDetail);
        imgBack = findViewById(R.id.img_back);

        Intent intent = getIntent();
        post = (Post) intent.getSerializableExtra("postData");
        String title = post.getTitle();
        String desc = post.getDescription();
        String url = post.getLink();
        String publish = post.getPubDate();

        Picasso.get().load(post.getImageUrl()).into(imagePost);
        titlePost.setText(title);
        description.setText(desc);
        pubDate.setText(publish);

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OverviewActivity.this, DetailActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }
}