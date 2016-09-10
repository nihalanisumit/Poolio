package androarmy.poolio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    String password;
    String mobile;
    String first_name,last_name,gender,email,vehicle_name,vehicle_number,driving_license;
    SharedPreferences mSharedPreferences;
    TextView usernameheaderTV;
    TextView emailheaderTV;

    public final String PROFILE_URL ="http://www.poolio.in/pooqwerty123lio/profile.php";//Sumit's pc
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        mobile =intent.getStringExtra("mobile");
        password= intent.getStringExtra("pass");
        SharedPreferences mSharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        String mob = mSharedPreferences.getString("mobile", "null");
        fetchDetails(mob);
        SharedPreferences session = getSharedPreferences("session", MODE_PRIVATE);
        SharedPreferences.Editor editor=session.edit();
        editor.putString("mobile", mobile);
        editor.putString("password", password);
        editor.apply();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        if(!InternetConnectionClass.isConnected(getApplicationContext())){
            Toast.makeText(Home.this, "Please connect to the internet!", Toast.LENGTH_LONG).show();
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //navigationView.getMenu().performIdentifierAction(R.id.flContent, 0);
        //navigationView.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.containerView, new TabFragment()).commit();//change
        toolbar.setTitle("Rides");

        View header=navigationView.getHeaderView(0);
/*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        usernameheaderTV = (TextView)header.findViewById(R.id.txt_user);
        emailheaderTV = (TextView)header.findViewById(R.id.txt_email);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this).setIcon(R.drawable.dialog_alert_icon).setTitle("Exit")
                    .setMessage("Are you sure?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }).setNegativeButton("no", null).show();
        }
        overridePendingTransition(R.anim.previous_slide_in, R.anim.previous_slide_out);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        if(!InternetConnectionClass.isConnected(getApplicationContext())){
            Toast.makeText(Home.this, "Please connect to the internet!", Toast.LENGTH_LONG).show();
            return false;
        }
        switch (id){
            case R.id.nav_find:
                fragmentClass= TabFragment.class;
                toolbar.setTitle("Find a ride");
                break;
            case R.id.nav_myrides:
                fragmentClass= myRides.class;
                toolbar.setTitle("My Rides");
                break;
            case R.id.nav_profile:
                fragmentClass=profile.class;
                toolbar.setTitle("Profile");
                break;


            case R.id.nav_share:
                Toast.makeText(getApplicationContext(),"Share",Toast.LENGTH_SHORT).show();
                toolbar.setTitle("Share");
                break;
            case R.id.nav_contact:
                Toast.makeText(getApplicationContext(),"contact us",Toast.LENGTH_SHORT).show();
                toolbar.setTitle("Contact Us");
                break;
            case R.id.nav_about:
                Toast.makeText(getApplicationContext(),"about us",Toast.LENGTH_SHORT).show();
                toolbar.setTitle("About Us");
                break;
            case R.id.sign_out:
                signout();
                break;
        }

        try {

            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }if (fragment!=null){

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.containerView, fragment).commit();

        }
        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void signout(){
        SharedPreferences session= getSharedPreferences("session",MODE_PRIVATE);
        session.edit().clear().commit();
        Intent in= new Intent(this,MainActivity.class);
        startActivity(in);
        overridePendingTransition(R.anim.previous_slide_in, R.anim.previous_slide_out);
    }


    private void fetchDetails(final String mobile){
        class fetchDetailsClass extends AsyncTask<String,Void,String> {
            //ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
              //  loading = ProgressDialog.show(getApplicationContext(),"Profile","Please wait while we connect to our server",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray("result");
                    JSONObject c = result.getJSONObject(0);

                    first_name=c.getString("first_name");
                    last_name=c.getString("last_name");
                    gender=c.getString("gender");
                    email=c.getString("email");
                    vehicle_name=c.getString("vehicle_name");
                    vehicle_number=c.getString("vehicle_number");
                    driving_license=c.getString("driving_license");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mSharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("name",first_name+" "+last_name);
                editor.putString("gender",gender);
                editor.putString("email",email);
                editor.putString("vehicle_name",vehicle_name);
                editor.putString("vehicle_number",vehicle_number);
                editor.putString("driving_license",driving_license);
                editor.commit();

                usernameheaderTV.setText(first_name+" "+last_name);
                emailheaderTV.setText(email);


            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("mobile",params[0]);

                RegisterUserClass ruc = new RegisterUserClass();
                String result = ruc.sendPostRequest(PROFILE_URL,data);
                return result;
            }
        }
        fetchDetailsClass fdc = new fetchDetailsClass();
        fdc.execute(mobile);
    }

}
