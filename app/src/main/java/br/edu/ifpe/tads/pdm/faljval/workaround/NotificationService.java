package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifpe.tads.pdm.faljval.workaround.auth.UserAuth;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Service;

public class NotificationService extends IntentService {


    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        DatabaseReference databaseRef;
        boolean isWorker = intent.getBooleanExtra("WORKER", false);

        if(isWorker) {
            databaseRef = FirebaseDatabase.getInstance().getReference("services");
            databaseRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Service service = dataSnapshot.getValue(Service.class);
                    if (service.getWorker().equals(UserAuth.getInstance().getUser().getEmail())) {
                        showNotification(service);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private void showNotification(Service service) {
        Intent newIntent = new Intent(this, MainActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
                this, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText(service.getCliente() + "solicitou seus serviços");
        builder.setContentIntent(pendingNotificationIntent);
        builder.setAutoCancel(true);
        Notification notification = builder.build();
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
