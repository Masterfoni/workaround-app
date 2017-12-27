package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.database.DataSetObserver;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.tads.pdm.faljval.workaround.auth.FirebaseAuthListener;
import br.edu.ifpe.tads.pdm.faljval.workaround.auth.UserAuth;
import br.edu.ifpe.tads.pdm.faljval.workaround.helpers.FirebaseHelper;
import br.edu.ifpe.tads.pdm.faljval.workaround.helpers.WorkerAdapterHelper;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.User;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Worker;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseHelper helper;
    private FirebaseAuth mAuth;
    private FirebaseAuthListener authListener;
    private DatabaseReference databaseRef;

    private DrawerLayout menuDrawer;
    private ActionBarDrawerToggle menuToggle;
    private ListView listaWorkers;

    private SwipeRefreshLayout homeSwipeRefreshLayout;

    private WorkerAdapterHelper adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuthListener(this);

        listaWorkers = findViewById(R.id.lv_workers);

        databaseRef = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(databaseRef);

        adapter = new WorkerAdapterHelper(this, helper.retrieveWorkers());
        listaWorkers.setAdapter(adapter);

        homeSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_swipe_refresh);
        homeSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                homeSwipeRefreshLayout.setRefreshing(false);
            }
        });

        menuDrawer = findViewById(R.id.homepage);
        menuToggle = new ActionBarDrawerToggle(this, menuDrawer, R.string.menu_abrir, R.string.menu_fechar);
        menuDrawer.addDrawerListener(menuToggle);
        menuToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_cliente);
        navigationView.setNavigationItemSelectedListener(this);

        setupUserInfo();
    }

    public void setupUserInfo() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children)
                {
                    User user = child.getValue(User.class);

                    if(user.getEmail().equals(UserAuth.getInstance().getUser().getEmail()))
                    {
                        TextView tvNome = findViewById(R.id.nome_cliente_perfil);
                        tvNome.setText(user.getNome());

                        TextView tvAtividade = findViewById(R.id.email_cliente_perfil);
                        tvAtividade.setText(user.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.nav_sair)
        {
            BtnSignOutClick(findViewById(R.id.homepage));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.homepage);
        drawer.closeDrawer(GravityCompat.START);

        return true;
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
