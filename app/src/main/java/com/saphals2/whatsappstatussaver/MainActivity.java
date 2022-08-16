package com.saphals2.whatsappstatussaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayoutMediator;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.saphals2.whatsappstatussaver.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivity activity;
    private viewpageradapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activity = this;


        checkpermissions();

        initview();

    }

    private void initview() {

        adapter = new viewpageradapter(activity.getSupportFragmentManager(),
                activity.getLifecycle());
        adapter.addfragment(new image_fragment(), "Images");
        adapter.addfragment(new VideoFragment(), "videos");

        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setOffscreenPageLimit(1);

        new TabLayoutMediator(binding.tabLayout, binding.viewpager,
                (tab, position) -> {
                    tab.setText(adapter.fragmenttitlelist.get(position));
                }).attach();
        for (int i = 0; i<binding.tabLayout.getTabCount(); i++){
            TextView tv = (TextView) LayoutInflater.from(activity)
                    .inflate(R.layout.custom_tab, null);
            binding.tabLayout.getTabAt(i).setCustomView(tv);
        }

    }
    class viewpageradapter extends FragmentStateAdapter {

        private final List<Fragment> fragmentlist  = new ArrayList<>();
        private final List<String> fragmenttitlelist = new ArrayList<>();


        public viewpageradapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }
        public void addfragment(Fragment fragment,String title){
            fragmentlist.add(fragment);
            fragmenttitlelist.add(title);

        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentlist.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentlist.size();
        }
    }

    private void checkpermissions(){
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (!multiplePermissionsReport.areAllPermissionsGranted()){
                            checkpermissions();

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }
                }).check();



    }


}