package com.example.yohan.readhub1;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecycleViewAdapter.onItemClicked{

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<Model> list,list1;
    private RecycleViewAdapter adapter;
    ProgressDialog progressDialog;


  //  public String render;

    private String baseURL = "https://readhub.lk/wp-json/wp/v2/";

    public static final String RENDER_CONTENT = "render";
    public static final String title = "render";

    private DatabaseReference mDatabase;
   // private  mAuth;
  ///  Model model1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout = findViewById(R.id.myswipe);



        linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<Model>();
        mDatabase = FirebaseDatabase.getInstance().getReference("Articles");



        if(haveNetwork(getApplicationContext())){

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Recent Post");
            progressDialog.setMessage("Loading");
            progressDialog.show();

            new GetJson().execute();

            list.clear();
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    progressDialog.dismiss();

                    list.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()){


                        Model model = data.getValue(Model.class);
                        list.add(model);
                        //   adapter.notifyDataSetChanged();


                    }
                    adapter = new RecycleViewAdapter(list,getApplicationContext());
                    // adapter.notifyDataSetChanged();
                    //list.clear();

                    recyclerView.setAdapter(adapter);
                    adapter.SetOnItemClickListener(MainActivity.this);




                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
           // mDatabase.keepSynced(true);
            Toast.makeText(MainActivity.this,"done",Toast.LENGTH_LONG).show();

        }else {

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Recent Post");
            progressDialog.setMessage("Loading");
            progressDialog.show();


            new GetJson().execute();

            list.clear();
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    progressDialog.dismiss();

                    list.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()){


                        Model model = data.getValue(Model.class);
                        list.add(model);
                        //   adapter.notifyDataSetChanged();


                    }
                    adapter = new RecycleViewAdapter(list,getApplicationContext());
                    // adapter.notifyDataSetChanged();
                    //list.clear();

                    recyclerView.setAdapter(adapter);
                    adapter.SetOnItemClickListener(MainActivity.this);




                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
            mDatabase.keepSynced(true);
            Toast.makeText(MainActivity.this," offline done",Toast.LENGTH_LONG).show();
        }








    }



    public class GetJson extends AsyncTask<Void,Void,Void>{



        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }


        @Override
        protected Void doInBackground(Void... voids) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            RetrofitArryApi retrofitArryApi = retrofit.create(RetrofitArryApi.class);

            Call<List<WPPost>> call = retrofitArryApi.getPostInfo();

            call.enqueue(new Callback<List<WPPost>>() {
                @Override
                public void onResponse(Call<List<WPPost>> call, Response<List<WPPost>> response) {



                   // progressDialog.dismiss();
                    mDatabase.removeValue();
                //    Toast.makeText(MainActivity.this,"removed",Toast.LENGTH_LONG).show();


                    for(int i = 0; i<response.body().size(); i++){

                        String temdetails = response.body().get(i).getDate().toString();
                        String titile = response.body().get(i).getTitle().getRendered().toString();
                        titile = titile.replace("&#8211;","");
                        String render = response.body().get(i).getContent().getRendered();
                        String profileUrl = response.body().get(i).getLinks().getAuthor().get(0).getHref();

//                        list.add(new Model( Model.IMAGE_TYPE,titile,
//                                temdetails,
//                                response.body().get(i).getLinks().getWpFeaturedmedia().get(0).getHref(),render,profileUrl));
                        model1 model = new model1(Model.IMAGE_TYPE,titile,
                                temdetails,
                                response.body().get(i).getBetterFeaturedImage().getMediaDetails().getSizes().getThumbnail().getSourceUrl(),render,profileUrl);

                        mDatabase.push().setValue(model);


                    }



                  //  adapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<List<WPPost>> call, Throwable t) {

                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

//    public void getRetrofit(){
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseURL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        RetrofitArryApi retrofitArryApi = retrofit.create(RetrofitArryApi.class);
//
//        Call<List<WPPost>> call = retrofitArryApi.getPostInfo();
//
//        call.enqueue(new Callback<List<WPPost>>() {
//            @Override
//            public void onResponse(Call<List<WPPost>> call, Response<List<WPPost>> response) {
//                Toast.makeText(MainActivity.this,"done",Toast.LENGTH_LONG).show();
//
//                progressBar.setVisibility(View.GONE);
//
//                for(int i = 0; i<response.body().size(); i++){
//
//                    String temdetails = response.body().get(i).getDate();
//                    String titile = response.body().get(i).getTitle().getRendered().toString();
//                    titile = titile.replace("&#8211;","");
//                    String render = response.body().get(i).getContent().getRendered();
//                    String profileUrl = response.body().get(i).getLinks().getAuthor().get(0).getHref();
//
//                    list.add(new Model( Model.IMAGE_TYPE,titile,
//                            temdetails,
//                            response.body().get(i).getJetpackFeaturedMediaUrl(),render,profileUrl));
//                }
//
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onFailure(Call<List<WPPost>> call, Throwable t) {
//
//            }
//        });
//
//
//    }


    @Override
    public void OnItemClick(int index) {

        Intent i = new Intent(this,WPPostDeatils.class);
        Model model = list.get(index);
        i.putExtra(RENDER_CONTENT,model.render);
       // i.putExtra(title,model.title);
        startActivity(i);

    }

    private boolean haveNetwork(Context context){
        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        // NetworkInfo [] networkInfos = connectivityManager.getAllNetworkInfo();
        if (connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }

}
