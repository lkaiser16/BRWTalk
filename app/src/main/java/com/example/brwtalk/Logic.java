package com.example.brwtalk;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Logic
{
    public void writeOnDatabase(FirebaseUser user, String message, FirebaseFirestore db, String collectionName)
    {

        HashMap<String, Object> h = new HashMap<>();
        h.put("User", user);
        h.put("Message", message);

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
}
