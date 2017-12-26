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
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Service;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Worker;

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved = null;
    ArrayList<Worker> workers = new ArrayList<>();
    ArrayList<Service> services = new ArrayList<>();

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

    private void fetchDataWorkerOn(DataSnapshot dataSnapshot)
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

    private void fetchDataServicesTo(String param, DataSnapshot dataSnapshot)
    {
        services.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Service service = ds.getValue(Service.class);

            if(service.getWorker().equals(param))
            {
                services.add(service);
            }
        }
    }

    public ArrayList<Worker> retrieveWorkers () {
        adicionarlistener("") ;
        return workers;
    }

    public ArrayList<Service> retrieveServices(String param) {
        adicionarlistener(param) ;
        return services;
    }

    private void adicionarlistener(final String param) {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("users"))
                    fetchDataWorkerOn(dataSnapshot);
                if(dataSnapshot.getKey().equals("services"))
                    fetchDataServicesTo(param, dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("users")) {
                    fetchDataWorkerOn(dataSnapshot);
                }
                if(dataSnapshot.getKey().equals("services")) {
                    fetchDataServicesTo(param, dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }
}
