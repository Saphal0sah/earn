package com.saphals2.whatsappstatussaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.saphals2.whatsappstatussaver.databinding.ItemLayoutBinding;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class whatsappAdapter extends RecyclerView.Adapter<whatsappAdapter.ViewHolder> {

    private ArrayList<status_model> list;
    private Context context;
    private LayoutInflater inflater;
    private String savefilepath = util.RootDirectoryWhatsapp+"/";

    public whatsappAdapter(ArrayList<status_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      if (inflater == null){
          inflater = LayoutInflater.from(parent.getContext());
      }
      return new ViewHolder(DataBindingUtil.inflate(inflater,
              R.layout.item_layout , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        status_model item = list.get(position);
        if (item.getUri().toString().endsWith(".mp4"))
            holder.binding.playBtn.setVisibility(View.VISIBLE);
        else   holder.binding.playBtn.setVisibility(View.GONE);

        Glide.with(context).load(item.getPath()).into(holder.binding.storyImg);

        holder.binding.downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               util.createfilefolder();
               final String path = item.getPath();
               final File file = new File(path);
               File destfile = new File(savefilepath);

                try {
                    FileUtils.copyFileToDirectory(file  , destfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Saved to :"+savefilepath, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        ItemLayoutBinding binding;

        public ViewHolder(ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
