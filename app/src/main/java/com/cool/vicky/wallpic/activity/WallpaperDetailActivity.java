package com.cool.vicky.wallpic.activity;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cool.vicky.wallpic.R;
import com.cool.vicky.wallpic.pojo.Wallpaper;
import com.cool.vicky.wallpic.util.Constants;
import com.cool.vicky.wallpic.util.RetrofitApi;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.dd.CircularProgressButton;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.skyfishjy.library.RippleBackground;

import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WallpaperDetailActivity extends AppCompatActivity {

    private String TAG = WallpaperDetailActivity.class.getSimpleName();
    private final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 100;
    private String hashId;
    private ImageView imageView;
    private Bitmap bitmap;
    private TextView wallpaperTitle;
    private SetWallpaperTask setWallpaperTask;
    private CircularProgressButton circularProgressButton;
    private CircularProgressButton circularProgressButton2;
    private RippleBackground rippleBackground;
    private Button retryButton;
    private LinearLayout errorLayout;
    private Boolean isWallpaperSet = false;
    private Boolean isWallpaperDownloaded = false;
    private ImageView pixabayLink;
    private FloatingActionButton fab;
    private FABProgressCircle fabProgressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_detail);
        Intent intent = getIntent();
        hashId = intent.getStringExtra(Constants.ID);
        initUIComponents();
        makeNetworkRequest();
    }

    private void initUIComponents() {
        imageView = (ImageView) findViewById(R.id.wallpaper);
        pixabayLink = (ImageView) findViewById(R.id.pixabay_link);
        wallpaperTitle = (TextView) findViewById(R.id.wallpaper_title);
        rippleBackground = (RippleBackground) findViewById(R.id.ripple);
        rippleBackground.startRippleAnimation();
        circularProgressButton = (CircularProgressButton) findViewById(R.id.btnWithText);
        circularProgressButton2 = (CircularProgressButton) findViewById(R.id.btnWithImage);
        retryButton = (Button) findViewById(R.id.retry_button);
        errorLayout = (LinearLayout) findViewById(R.id.error_layout);

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorLayout.setVisibility(View.GONE);
                rippleBackground.setVisibility(View.VISIBLE);
                rippleBackground.startRippleAnimation();
                makeNetworkRequest();
            }
        });

        pixabayLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pixabay.com/goto/" + hashId));
                startActivity(browserIntent);
            }
        });

        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null) {
                    if (isWallpaperSet)
                        ;//do nothing
                    else {
                        setWallpaperTask = new SetWallpaperTask();
                        setWallpaperTask.execute();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Wait for image to be loaded.", Toast.LENGTH_SHORT).show();

            }
        });

        circularProgressButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap != null) {
                    if (isWallpaperDownloaded)
                        ;//do nothing
                    else {
                        if (ContextCompat.checkSelfPermission(WallpaperDetailActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(WallpaperDetailActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        } else {
                            DownloadTask downloadTask = new DownloadTask();
                            downloadTask.execute();
                        }
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Wait for image to be loaded.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void makeNetworkRequest() {

        RetrofitApi.ApiInterface apiInterface = RetrofitApi.getApiInterfaceInstance();
        Call<Wallpaper> wallpaperCall = apiInterface.wallpaper("2209479-4cbc19d132c80918a5167e2d9", "high_resolution", hashId);
        wallpaperCall.enqueue(new Callback<Wallpaper>() {
                                  @Override
                                  public void onResponse(Call<Wallpaper> call, Response<Wallpaper> response) {
                                      if (response.isSuccessful()) {
                                          Wallpaper wallpaper = response.body();
                                          wallpaperTitle.setText(WordUtils.capitalize(wallpaper.getHits().get(0).getUser()));
                                          Glide.with(getApplicationContext())
                                                  .load(wallpaper.getHits().get(0).getFullHDURL())
                                                  .asBitmap()
                                                  .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                  .into(new BitmapImageViewTarget(imageView) {
                                                      @Override
                                                      protected void setResource(Bitmap resource) {
                                                          super.setResource(resource);
                                                          bitmap = resource;
                                                          rippleBackground.stopRippleAnimation();
                                                          rippleBackground.setVisibility(View.GONE);
                                                      }

                                                      @Override
                                                      public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                                          rippleBackground.stopRippleAnimation();
                                                          rippleBackground.setVisibility(View.GONE);
                                                          errorLayout.setVisibility(View.VISIBLE);
                                                      }
                                                  });
                                      } else {
                                          rippleBackground.stopRippleAnimation();
                                          rippleBackground.setVisibility(View.GONE);
                                          errorLayout.setVisibility(View.VISIBLE);
                                      }
                                  }

                                  @Override
                                  public void onFailure(Call<Wallpaper> call, Throwable t) {
                                      rippleBackground.stopRippleAnimation();
                                      rippleBackground.setVisibility(View.GONE);
                                      errorLayout.setVisibility(View.VISIBLE);
                                  }
                              }

        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    private class SetWallpaperTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            circularProgressButton.setIndeterminateProgressMode(true); // turn on indeterminate progress
            circularProgressButton.setProgress(50);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(WallpaperDetailActivity.this);
            wallpaperManager.suggestDesiredDimensions(width,height);
            try {
                wallpaperManager.setBitmap(bitmap);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isWallpaperSet = true;
            circularProgressButton.setProgress(100);
            // Toast.makeText(WallpaperDetailActivity.this, "Wallpaper set.", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            circularProgressButton2.setIndeterminateProgressMode(true); // turn on indeterminate progress
            circularProgressButton2.setProgress(50);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            String folderPath = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES) + "/WallPic";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                File wallpaperDirectory = new File(folderPath);
                wallpaperDirectory.mkdirs();
            }
            File image = null;
            try {
                image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".png",         /* suffix */
                        folder      /* directory */
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

            String mCurrentPhotoPath = image.getAbsolutePath();

            FileOutputStream out = null;
            try {
                out = new FileOutputStream(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File(mCurrentPhotoPath);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            WallpaperDetailActivity.this.sendBroadcast(mediaScanIntent);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isWallpaperDownloaded = true;
            circularProgressButton2.setProgress(100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    DownloadTask downloadTask = new DownloadTask();
                    downloadTask.execute();

                } else {
                    Toast.makeText(WallpaperDetailActivity.this, "The app does not have the permission to download the wallpaper.", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

}

