package net.foxandr.sport.universiade.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.threetenabp.AndroidThreeTen;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.api.UniversiadeApi;
import net.foxandr.sport.universiade.api.UniversiadeService;
import net.foxandr.sport.universiade.ui.news.model.NewsDTO;
import net.foxandr.sport.universiade.utils.ImageDownloader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailsActivity extends AppCompatActivity {

    String locale;
    NewsDTO newsDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        getSupportActionBar().setTitle(getResources().getString(R.string.tab_news));
        Bundle arguments = getIntent().getExtras();
        locale = arguments.getString("locale");
        newsDTO = (NewsDTO) arguments.getSerializable("newsDTO");
        setNewsDetails();
        AndroidThreeTen.init(this);
    }

    private void setNewsDetails() {
        TextView newsTitleView = findViewById(R.id.news_title_detailed__name);
        TextView newsTextView = findViewById(R.id.news_detailed_text);
        ImageView imageView = findViewById(R.id.news_detailed_image);

        newsTitleView.setText(newsDTO.getNewsTEntities().get(0).getTitle());
        newsTextView.setText(newsDTO.getNewsTEntities().get(0).getText());

        String uuid = newsDTO.getImagesEntity().getUuid();
        ImageDownloader.getImageByUuid(imageView, uuid);
    }
}