package com.example.brwtalk;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity
{
    public FirebaseFirestore db;
    private FirebaseAuth mAuth;
    TextView v;
    Button b;
    public ListView lv;
    FirebaseUser user;
    String collectionName = "BRWTalk";
    Logic l;
    List<Message> messageList = new ArrayList<>();
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        v = (TextView) findViewById(R.id.textView);
        b = findViewById(R.id.buttonSend);
        lv = findViewById(R.id.listView);
        l = new Logic();
        if (user != null)
        {
            messageList = l.readFromDatabase(db, collectionName);
        }
        adapter = new Adapter(this, android.R.layout.simple_list_item_1, messageList);
        try
        {
            Thread.sleep(3000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        lv.setAdapter(adapter);


//        loginAuthADD("laurenz260805@gmail.com", "laurenz123");

    }

    public void loginAuthADD(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(">>>>>>>>>>>>>>>>>>>>>>>>>><", "createUserWithEmail:success");
                    user = mAuth.getCurrentUser();
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + user.getEmail());
//                    updateUI(user);
                } else
                {
                    // If sign in fails, display a message to the user.
//                    Log.w(">>>>>>>>>>>>>>>>>>>>>><<<<<<", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                    updateUI(null);
                }

                // ...
            }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + user.getEmail());
//        if (currentUser.equals(null))
//        {
//
//        }

//        updateUI(currentUser);
    }

    public void registrieren(String email, String password)
    {
        loginAuthADD(email, password);
    }

    public void anmelden(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithEmail:success");
                    user = mAuth.getCurrentUser();
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + user.getEmail());
//                    updateUI(user);
                } else
                {
                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                    updateUI(null);
                }


                // ...
            }
        });
    }

    public void actionButtonSend(View v)
    {
        String text = this.v.getText().toString();
        if (text.contains("/register"))
        {
            try
            {

                String[] splitetButtonText = text.split(" ");
                registrieren(splitetButtonText[1], splitetButtonText[2]);
            }
            catch (Exception x)
            {
                Toast.makeText(MainActivity.this, "check your input.", Toast.LENGTH_SHORT).show();
            }


        } else if (text.contains("/login"))
        {
            try
            {
                String[] splitetButtonText = text.split(" ");
                anmelden(splitetButtonText[1], splitetButtonText[2]);
            }
            catch (Exception x)
            {
                Toast.makeText(MainActivity.this, "check your input.", Toast.LENGTH_SHORT).show();
            }


        }

        if (user != null && !(text.equals("")))
        {
            Date d = new Date();
            String d1 = d.toString();
            long id = System.currentTimeMillis();
            l.writeOnDatabase(user.getEmail(), text, d1, id, db, collectionName);
            messageList.clear();
            messageList = l.readFromDatabase(db, collectionName);
            lv.setAdapter(adapter);

        }
        this.v.setText("");
    }


}
