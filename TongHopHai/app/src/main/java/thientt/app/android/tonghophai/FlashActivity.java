package thientt.app.android.tonghophai;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import thientt.app.android.tonghophai.pojo.Constant;
import thientt.app.android.tonghophai.pojo.ServerLink;

public class FlashActivity extends AppCompatActivity {

    private ThienTTApplication application;
    private ArrayList<ServerLink> serverLinks;
    private ImageView imageView;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            if (bundle != null) {
                int mode = bundle.getInt(Constant.ACTION_SERVICE);
                if (mode == Constant.ACTION_GET_LINK_SERVER) {
                    String data = bundle.getString(Constant.RESULT_HANDLER);
                    if (data != null) {
                        // TLog.d(null, data);
                        try {
                            JSONObject object = new JSONObject(data);
                            if (object.getBoolean("success")) {
                                String game_data = object.getString("message");
                                if (game_data != null)
                                    serverLinks = new Gson().fromJson(game_data, new TypeToken<ArrayList<ServerLink>>() {
                                    }.getType());
                                if (serverLinks != null) {
                                    showDataToScreen(serverLinks);
                                }
                            } else {
                                Toast.makeText(FlashActivity.this, "Something Wrong, try again", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            return true;
        }
    });

    private void showDataToScreen(ArrayList<ServerLink> serverLinks) {
        application.saveServerLinks(serverLinks);
        // Glide.with(this).load(gamePlay.getBg_link()).centerCrop().into(imageView);

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(FlashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        application = (ThienTTApplication) getApplication();
        imageView = (ImageView) findViewById(R.id.image);
        //createKey();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkInternet(null);
                    }
                });
            }
        }.start();
    }

    public void checkInternet(View view) {

        Intent intent = new Intent(FlashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

//        if (Constant.isNetworkAvailable(this)) {
//            application.getFullLinkServer(handler);
//        } else {
//            Constant.showSnackBar(findViewById(R.id.image), getString(R.string.lost_internet));
//            findViewById(R.id.buton).setVisibility(View.VISIBLE);
//        }
    }
}
