package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifpe.tads.pdm.faljval.workaround.auth.FirebaseAuthListener;
import br.edu.ifpe.tads.pdm.faljval.workaround.auth.UserAuth;

public class HomeWorkerActivity extends AppCompatActivity {
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

        NavigationView navigationView = findViewById(R.id.nav_view_worker);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_sair)
                {
                    BtnSignOutClick(findViewById(R.id.homeworkerpage));
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.homeworkerpage);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });

//        TextView tvNome = findViewById(R.id.tv_nome);
//        TextView tvEmail = findViewById(R.id.tv_email);
//
//        tvNome.setText(UserAuth.getInstance().getUser().getNome());
//        tvEmail.setText(UserAuth.getInstance().getUser().getEmail());
    }

    public void BtnSignOutClick(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            mAuth.signOut();
        } else {
            Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT);
        }
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
