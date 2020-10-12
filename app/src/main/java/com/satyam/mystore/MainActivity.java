package com.satyam.mystore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    SliderLayout sliderLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter viewpagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewpagerAdapter);

        nav = (NavigationView) findViewById(R.id.navimenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        sliderLayout = findViewById(R.id.imgslide);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sliderLayout = findViewById(R.id.imgslide);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setScrollTimeInSec(1);

        setSlideViews();
        //method declare


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.my_mall:
                        temp = new MymallFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                        case R.id.my_rewards:
                        temp = new MyrewardFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.my_cart:
                        temp = new MycartFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.my_wishlist:
                        temp = new MywishFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.my_account:
                        temp = new MyaccountFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.my_orders:
                        temp = new MyorderFragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }


        });}

    private void setSlideViews() {

        for(int i = 0; i<= 3; i++)
        {
            DefaultSliderView sliderView = new DefaultSliderView(this);

            switch (i)
            {
                case 0 :
                    //sliderView.setImageUrl("https://www.google.com/search?q=avenger+image&rlz=1C1CHBD_enIN910IN910&sxsrf=ALeKk01i9N2quxvu23GG37MnT4rPJTRqHA:1597428478817&tbm=isch&source=iu&ictx=1&fir=kYgamFxWVWKDJM%252Cfk0SI7ZTDFnXZM%252C_&vet=1&usg=AI4_-kTZAUGpx_P8tHcUHPcytc58c1Xlnw&sa=X&ved=2ahUKEwi6vaLopJvrAhWnzjgGHftpDm0Q9QEwAXoECAoQMA#imgrc=kYgamFxWVWKDJM");
                    sliderView.setImageDrawable(R.drawable.b1);
                    sliderView.setDescription("Picture one");
                    break;
                case  1:
                    //sliderView.setImageUrl("https://www.google.com/search?q=avenger+image&rlz=1C1CHBD_enIN910IN910&sxsrf=ALeKk01i9N2quxvu23GG37MnT4rPJTRqHA:1597428478817&tbm=isch&source=iu&ictx=1&fir=kYgamFxWVWKDJM%252Cfk0SI7ZTDFnXZM%252C_&vet=1&usg=AI4_-kTZAUGpx_P8tHcUHPcytc58c1Xlnw&sa=X&ved=2ahUKEwi6vaLopJvrAhWnzjgGHftpDm0Q9QEwAXoECAoQMA#imgrc=kYgamFxWVWKDJM");
                    sliderView.setImageDrawable(R.drawable.b2);
                    sliderView.setDescription("Picture two");
                    break;
                case 2 :
                    //sliderView.setImageUrl("https://www.google.com/search?q=avenger+image&rlz=1C1CHBD_enIN910IN910&sxsrf=ALeKk01i9N2quxvu23GG37MnT4rPJTRqHA:1597428478817&tbm=isch&source=iu&ictx=1&fir=4ag0qmuSJ_aYZM%252CuNnjwsqE53p35M%252C_&vet=1&usg=AI4_-kT7VV-28AqNUnHVACMklRdNUwhYIQ&sa=X&ved=2ahUKEwi6vaLopJvrAhWnzjgGHftpDm0Q9QEwCHoECAoQPg#imgrc=4ag0qmuSJ_aYZM");
                    sliderView.setImageDrawable(R.drawable.b3);
                    sliderView.setDescription("Picture three");
                    break;
                case 3 :
                    //sliderView.setImageUrl("https://www.google.com/search?q=avenger+image&rlz=1C1CHBD_enIN910IN910&sxsrf=ALeKk01i9N2quxvu23GG37MnT4rPJTRqHA:1597428478817&tbm=isch&source=iu&ictx=1&fir=_pxieWX57voW5M%252CA2SJK-2nitXsPM%252C_&vet=1&usg=AI4_-kRe4XWl_IA_FeA5uOR_hrZnvi7cgg&sa=X&ved=2ahUKEwi6vaLopJvrAhWnzjgGHftpDm0Q9QEwDHoECAoQRg#imgrc=_pxieWX57voW5M");
                    sliderView.setImageDrawable(R.drawable.b4);
                    sliderView.setDescription("Picture four");
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

           // sliderView.setDescription("setDescription"+(i+1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(MainActivity.this,"touch",Toast.LENGTH_SHORT).show();
                }
            });
            sliderLayout.addSliderView(sliderView);




        }

    }

    // for toolbar menu
    public boolean onCreateOptionsMenu (Menu menu){

            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
    }
