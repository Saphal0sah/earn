package com.saphals2.whatsappstatussaver;

import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphals2.whatsappstatussaver.databinding.FragmentImageFragmentBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class image_fragment extends Fragment {

    private FragmentImageFragmentBinding binding;
    private ArrayList<status_model>list;
    private whatsappAdapter  adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_fragment , container,false);
        list  = new ArrayList<>();

        getdata();

        binding.refresh.setOnRefreshListener(() -> {
            list =new ArrayList<>();
            getdata();
            binding.refresh.setRefreshing(false);

        });
        return binding.getRoot();
    }
    private void getdata() {
        status_model model;

        String targetpath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/WhatsApp/Media/.statuses";
        File targetDirectory = new File(targetpath);
        File[] allFiles = targetDirectory.listFiles();



        String targetpathBusiness = Environment.getExternalStorageDirectory().getAbsolutePath()+
                "/WhatsApp Business/Media/.statuses";
        File targetDirectoryBusiness = new File(targetpathBusiness);
        File[] allFilesBusiness = targetDirectoryBusiness.listFiles();

        Arrays.sort(allFiles, ((o1, o2)->{
            if (o1.lastModified()> o2.lastModified()) return -1;
            else if (o1.lastModified()< o2.lastModified()) return +1;
            else return 0;
        }));
        for (int i=0; i< allFiles.length; i++){
            File file = allFiles[i];
            if (Uri.fromFile(file).toString().endsWith(".png")||
                    Uri.fromFile(file).toString().endsWith(".jpg")){
                model = new status_model("whatsmansa"+i,
                        Uri.fromFile(file),
                        allFiles[i].getAbsolutePath(),
                        file.getName());
                list.add(model);
            }
        }

        Arrays.sort(allFilesBusiness, ((o1, o2)->{
            if (o1.lastModified()> o2.lastModified()) return -1;
            else if (o1.lastModified()< o2.lastModified()) return +1;
            else return 0;
        }));
        for (int i=0; i< allFilesBusiness.length; i++){
            File file = allFilesBusiness[i];
            if (Uri.fromFile(file).toString().endsWith(".png")||
                    Uri.fromFile(file).toString().endsWith(".jpg")){
                model = new status_model("whatsmansa Businesss"+i,
                        Uri.fromFile(file),
                        allFilesBusiness[i].getAbsolutePath(),
                        file.getName());
                list.add(model);
            }
        }
        adapter= new whatsappAdapter(list, getActivity());
        binding.whatsappRecycler.setAdapter(adapter);
    }
}