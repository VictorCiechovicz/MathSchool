package com.example.mathschool.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.example.mathschool.API.SignInResponse;
import com.example.mathschool.R;
import com.example.mathschool.utils.ConfigBD;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class Home extends AppCompatActivity {

DrawerLayout drawerLayout;
NavigationView navigationView;
Toolbar toolbar;
ActionBarDrawerToggle actionBarDrawerToggle;

    private FirebaseAuth mAuth;

    SignInResponse signInResponse;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        mAuth = ConfigBD.FirebaseAuth();

        drawerLayout =  findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.drawer_navigation);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.opt_drawer_home:
                        Log.i("MENU_DRAWER_TAG", "Menu home selecionado");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.opt_drawer_profile:
                        Log.i("MENU_DRAWER_TAG", "Menu profile selecionado");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.opt_drawer_roadmap:
                        Log.i("MENU_DRAWER_TAG", "Menu roadmap selecionado");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.opt_drawer_settings:
                        Log.i("MENU_DRAWER_TAG", "Menu settings selecionado");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.opt_drawer_logout:
                        Log.i("MENU_DRAWER_TAG", "Logout");
                        try{
                            mAuth.signOut();
                            finish();
                            startActivity(new Intent(Home.this,SignIn.class));
                        }catch ( Exception e){
                            e.printStackTrace();
                        }

                }
                return true;
            }
        });



        Intent intent = getIntent();
        if(intent.getExtras() != null){
            signInResponse = (SignInResponse) intent.getSerializableExtra("data");

            Log.e("TAG","=========>" +signInResponse.getEmail());
        }


    }

}