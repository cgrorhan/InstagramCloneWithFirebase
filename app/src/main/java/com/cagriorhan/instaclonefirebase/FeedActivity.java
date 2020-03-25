package com.cagriorhan.instaclonefirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<String> userMailFromFB;
    ArrayList<String> userCommentFromFB;
    ArrayList<String> userImageFromFB;
    FeedRecylerAdapter feedRecylerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        userMailFromFB=new ArrayList<>();
        userCommentFromFB=new ArrayList<>();
        userImageFromFB=new ArrayList<>();

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        getDataFromFirestore();

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedRecylerAdapter= new FeedRecylerAdapter(userMailFromFB,userCommentFromFB,userImageFromFB);
        recyclerView.setAdapter(feedRecylerAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_post){
            Intent intentToAddpost = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intentToAddpost);
            finish();
        }else if(item.getItemId()==R.id.sign_out){
            firebaseAuth.signOut();
            Intent intentToSingup = new Intent(FeedActivity.this,MainActivity.class);
            startActivity(intentToSingup);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    public void getDataFromFirestore(){
        CollectionReference collectionReference=firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Toast.makeText(FeedActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }

                if(queryDocumentSnapshots !=null){
                    for (DocumentSnapshot snapshot :queryDocumentSnapshots.getDocuments()){
                        Map<String,Object> data=snapshot.getData();

                        String comment=(String) data.get("comment");
                        String email=(String) data.get("usermail");
                        String downloadurl=(String) data.get("downloadurl");

                        userCommentFromFB.add(comment);
                        userMailFromFB.add(email);
                        userImageFromFB.add(downloadurl);
                        feedRecylerAdapter.notifyDataSetChanged();

                    }
                }
            }
        });

    }
}















