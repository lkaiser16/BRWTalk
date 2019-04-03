package com.example.brwtalk;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Logic
{
    FirebaseUser user;
    String message;
    Date datum;
    List<Message> messages = new ArrayList<>();

    public void writeOnDatabase(FirebaseUser user, String message, Date currentDate,long id, FirebaseFirestore db, String collectionName)
    {
        Message message1 = new Message(user, message, currentDate,id);
        HashMap<String, Object> h = new HashMap<>();
        h.put("Message", message1);


        db.collection(collectionName).document().set(h).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                Log.d("firestoreDemo.set", "DocumentSnapshot successfully written!");
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.w("firestoreDemo.set", "Error writing document", e);
            }
        });
    }

    public List<Message> readFromDatabase(FirebaseFirestore db, String collectionName)
    {

        db.collection(collectionName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {


            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {

                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        Message loadedMessage = (Message) document.get("Message");
                        messages.add(loadedMessage);

//                    TextView tv = findViewById(R.id.textView);
//                    tv.setText(note.toString());


                    }
                } else
                {
                    Log.w("firestoreDemo.get", "Error getting documents.", task.getException());
                }


            }
        });
        return messages;

    }


}

