package thientt.app.android.tonghophai.pojo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import thientt.app.android.tonghophai.R;
import thientt.app.android.tonghophai.ThienTTApplication;

public class ConnectService {
    private Context context;
    private ThienTTRetrofit retrofit;

    public ConnectService(Context context) {
        this.context = context;
        retrofit = ThienTTService.createService(ThienTTRetrofit.class, ((ThienTTApplication) context.getApplicationContext()).getURL());
        if (!Constant.isNetworkAvailable(context))
            Constant.showToast(context, context.getString(R.string.lost_internet));
    }

    public void getLinkServer(Handler handler) {
        final Messenger messenger_create = new Messenger(handler);
        final Bundle bundle = new Bundle();
        bundle.putInt(Constant.ACTION_SERVICE, Constant.ACTION_GET_LINK_SERVER);
        final Message message = Message.obtain();
        retrofit.getLinkServer().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                bundle.putString(Constant.RESULT_HANDLER, response.body());
                message.setData(bundle);
                try {
                    messenger_create.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
               // TLog.e(null, "loi getImageRandom:" + t.toString());
                bundle.putBoolean(Constant.RESULT_ERROR, true);
                bundle.putString(Constant.RESULT_ERROR_CONTENT, t.toString());
                message.setData(bundle);
                try {
                    messenger_create.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
