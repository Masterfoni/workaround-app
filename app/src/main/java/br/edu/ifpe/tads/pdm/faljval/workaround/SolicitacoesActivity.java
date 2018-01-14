package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifpe.tads.pdm.faljval.workaround.helpers.FirebaseHelper;
import br.edu.ifpe.tads.pdm.faljval.workaround.helpers.SolicitacaoAdapterHelper;

public class SolicitacoesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseHelper helper;
    private DatabaseReference databaseRef;

    private DrawerLayout menuDrawer;
    private ActionBarDrawerToggle menuToggle;
    private ListView listaSolicitacoes;

    private SwipeRefreshLayout solSwipeRefreshLayout;

    private SolicitacaoAdapterHelper adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitacoes);

        listaSolicitacoes = findViewById(R.id.lv_solicitacoes);

        databaseRef = FirebaseDatabase.getInstance().getReference();
        helper = new FirebaseHelper(databaseRef);

        adapter = new SolicitacaoAdapterHelper(this, helper.retrieveSolicitations(getIntent().getStringExtra("USER_EMAIL"), true));
        listaSolicitacoes.setAdapter(adapter);

        solSwipeRefreshLayout = findViewById(R.id.solicitacoes_swipe_refresh);
        solSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                solSwipeRefreshLayout.setRefreshing(false);
            }
        });

        menuDrawer = findViewById(R.id.homepage);
        menuToggle = new ActionBarDrawerToggle(this, menuDrawer, R.string.menu_abrir, R.string.menu_fechar);
        menuDrawer.addDrawerListener(menuToggle);
        menuToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_cliente);
        navigationView.setNavigationItemSelectedListener(this);
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
        else if(item.getItemId() == R.id.nav_perfil)
        {
            finish();
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
}
