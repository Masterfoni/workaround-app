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
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.EnumStatusServico;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Service;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Worker;

public class FirebaseHelper {
    DatabaseReference db;
    Boolean saved = null;
    ArrayList<Worker> workers = new ArrayList<>();
    ArrayList<Service> services = new ArrayList<>();
    public static final ArrayList<String> keysServices = new ArrayList<>();

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

    private void fetchDataServicesTo(String param, boolean acceptedOnly, DataSnapshot dataSnapshot)
    {
        services.clear();
        keysServices.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Service service = ds.getValue(Service.class);

            if(service.getWorker().equals(param))
            {
                if(acceptedOnly && service.getStatus() == EnumStatusServico.ACCEPTED)
                {
                    services.add(service);
                    keysServices.add(ds.getKey());
                }
                else
                {
                    services.add(service);
                    keysServices.add(ds.getKey());
                }
            }
        }
    }

    private void fetchDataSolicitationsTo(String clienteEmail, boolean acceptedOnly, DataSnapshot dataSnapshot)
    {
        services.clear();
        keysServices.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Service service = ds.getValue(Service.class);

            if(service.getCliente().equals(clienteEmail))
            {
                if(acceptedOnly && service.getStatus() == EnumStatusServico.ACCEPTED)
                {
                    services.add(service);
                    keysServices.add(ds.getKey());
                }
                else
                {
                    services.add(service);
                    keysServices.add(ds.getKey());
                }
            }
        }
    }

    public ArrayList<Worker> retrieveWorkers () {
        adicionarlistener("", false) ;
        return workers;
    }

    public ArrayList<Service> retrieveServices(String param, boolean acceptedOnly) {
        adicionarlistener(param, acceptedOnly);
        return services;
    }

    public ArrayList<Service> retrieveSolicitations(final String clientEmail, final boolean acceptedOnly)
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("services"))
                    fetchDataSolicitationsTo(clientEmail, acceptedOnly, dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("services")) {
                    fetchDataServicesTo(clientEmail, acceptedOnly, dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        return services;
    }

    private void adicionarlistener(final String param, final boolean acceptedOnly) {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("users"))
                {
                    fetchDataWorkerOn(dataSnapshot);
                }
                else if(dataSnapshot.getKey().equals("services"))
                {
                    fetchDataServicesTo(param, acceptedOnly, dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("users"))
                {
                    fetchDataWorkerOn(dataSnapshot);
                }
                else if(dataSnapshot.getKey().equals("services"))
                {
                    fetchDataServicesTo(param, acceptedOnly, dataSnapshot);
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
