package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.edu.ifpe.tads.pdm.faljval.workaround.auth.FirebaseAuthListener;
import br.edu.ifpe.tads.pdm.faljval.workaround.auth.UserAuth;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Worker;

public class HomeWorkerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuthListener authListener;
    private DrawerLayout menuDrawer;
    private ActionBarDrawerToggle menuToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_worker);

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);

        menuDrawer = findViewById(R.id.homeworkerpage);
        menuToggle = new ActionBarDrawerToggle(this, menuDrawer, R.string.menu_abrir, R.string.menu_fechar);
        menuDrawer.addDrawerListener(menuToggle);
        menuToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_worker);
        navigationView.setNavigationItemSelectedListener(this);

        setupUserInfo();
    }

    public void processarSwitch(View view) {
        Switch dispoSwitch = (Switch) findViewById(R.id.switch_disponibilidade);

        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("disponivel")
                .setValue(dispoSwitch.isChecked());

    }

    public void setupUserInfo() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children)
                {
                    Worker worker = child.getValue(Worker.class);

                    if(worker.getEmail().equals(UserAuth.getInstance().getUser().getEmail()))
                    {
                        TextView tvNome = findViewById(R.id.nome_worker_perfil);
                        tvNome.setText(worker.getNome());

                        TextView tvAtividade = findViewById(R.id.atividade_worker_perfil);
                        tvAtividade.setText(worker.getAtividade());

                        Switch switchDisponivel = findViewById(R.id.switch_disponibilidade);
                        switchDisponivel.setChecked(worker.getDisponivel());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void BtnSignOutClick(View view) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            mAuth.signOut();
        } else {
            Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_sair) {
            Toast.makeText(this, "EITA", Toast.LENGTH_SHORT);
            BtnSignOutClick(findViewById(R.id.homeworkerpage));
        }
        else
        {
            Toast.makeText(this, "UOUPA", Toast.LENGTH_SHORT);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.homeworkerpage);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (menuDrawer.isDrawerOpen(GravityCompat.START)) {
            menuDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }
}
