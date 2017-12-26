package br.edu.ifpe.tads.pdm.faljval.workaround.helpers;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.faljval.workaround.R;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Worker;

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved = null;
    ArrayList<Worker> workers = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public Boolean save(Worker worker)
    {
        if(worker == null)
        {
            saved = false;
        }
        else
        {
            try
            {
                db.child("users").push().setValue(worker);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }

        return saved;
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        workers.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Worker worker = ds.getValue(Worker.class);

            if(worker.getWorker() && worker.getDisponivel())
            {
                workers.add(worker);
            }
        }
    }

    public ArrayList<Worker> retrieve() {

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        return workers;
    }
}
