package com.example.brwtalk;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Logic
{
    String user;
    String message;
    String datum;
    List<Message> messages = new ArrayList<>();

    public void writeOnDatabase(String user, String message, String currentDate, long id, final FirebaseFirestore db, final String collectionName)
    {
        Message message1 = new Message(user, message, currentDate, id);

        Map<String, Object> h = new HashMap<>();
        h.put("Msg", message1.getMessage());
        h.put("Date", message1.getDate());
        h.put("User", message1.getUser());
        h.put("Id", message1.getId());

//        h.put("Nachricht2", message1);

        db.collection(collectionName).add(h).addOnSuccessListener(new OnSuccessListener<DocumentReference>()
        {
            @Override
            public void onSuccess(DocumentReference documentReference)
            {
                readFromDatabase(db, collectionName);
                Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Log.w("", "Error adding document", e);
            }
        });
        ;
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
                    messages.clear();

                    for (QueryDocumentSnapshot document : task.getResult())
                    {


                        String message = (String) document.get("Msg");
                        String d = (String) document.get("Date");
                        String user = (String) document.get("User");
                        long id = (long) document.get("Id");
                        Message m = new Message(user, message, d, id);

                        messages.add(m);
                    }
                } else
                {
                    Log.w("firestoreDemo.get", "Error getting documents.", task.getException());
                }


            }
        });
        messages.sort(new Comparator<Message>()
        {
            @Override
            public int compare(Message message, Message t1)
            {
               return (int) (t1.getId()-message.getId());
            }
        });
        return messages;

    }


}

