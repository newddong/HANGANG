package leekiyoung.newmap;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class map extends AppCompatActivity{


    MapsActivity mapsActivity;
    DrawerLayout drawerLayout;
    ActionBar actionBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);


        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        mapsActivity = new MapsActivity();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.mapfragment, mapsActivity);
        fragmentTransaction.commit();


        actionBar = getSupportActionBar();
        drawerLayout = (DrawerLayout) findViewById(R.id.draw_layout) ;
        actionBar.setHomeAsUpIndicator(R.mipmap.baseline_dehaze_white_18dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            if(!drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.openDrawer(GravityCompat.START);
            }else{
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButton1Clicked(View v){
        mapsActivity.addMarker();
    }


}
