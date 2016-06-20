package thientt.app.android.tonghophai.pojo;

import retrofit.Call;
import retrofit.http.GET;

public interface ThienTTRetrofit {

//    @FormUrlEncoded
//    @POST("/service/event")
//    Call<String> createGamePlay(@Field("gameplay") String gameplay);

    @GET("/service/linkbongda/all")
    Call<String> getLinkServer();

//    @FormUrlEncoded
//    @POST("/service/like")
//    Call<String> likeEvent(@Field("id") int id, @Field("like") int like);


}

