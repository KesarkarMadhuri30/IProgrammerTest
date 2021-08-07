package com.developer.testapplication.Adapter;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.testapplication.Database.DatabaseClient;
import com.developer.testapplication.Model.PhotoListModel;
import com.developer.testapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.photoViewHolder> {
    Activity context;
    ArrayList<PhotoListModel> mPhotoDataList;
    private boolean isCompare;

    public PhotoAdapter(Activity context, ArrayList<PhotoListModel> mPhotoDataList,boolean isCompare) {
        this.context = context;
        this.mPhotoDataList = mPhotoDataList;
        this.isCompare = isCompare;
    }

    @NonNull
    @Override
    public photoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(context).inflate(R.layout.photo_listitem, parent, false);

        return new photoViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull photoViewHolder holder, int i) {

   holder.photo_title.setText(""+mPhotoDataList.get(i).getTitle());
    holder.photo_url.setText("URL : "+mPhotoDataList.get(i).getUrl());
        holder.photo_id.setText("ID :"+mPhotoDataList.get(i).getAlbumId());
//        Glide.with(context)
//                .load(mPhotoDataList.get(i).getUrl())
//                .into(holder.photo_img);
        Picasso.get().load(mPhotoDataList.get(i).getUrl()).into(holder.photo_img);

        if (!isCompare) {
            holder.photo_remove.setVisibility(View.VISIBLE);
            holder.photo_compare.setVisibility(View.GONE);
        } else {

            class GetTasks extends AsyncTask<Void, Void, List<PhotoListModel>> {

                @Override
                protected List<PhotoListModel> doInBackground(Void... voids) {
                    List<PhotoListModel> taskList = DatabaseClient
                            .getInstance(context)
                            .getAppDatabase()
                            .comparePhotoDao()
                            .getParticularData(mPhotoDataList.get(i).getId());
                    return taskList;
                }

                @Override
                protected void onPostExecute(List<PhotoListModel> tasks) {
                    super.onPostExecute(tasks);
                    Log.d("$$$",""+tasks.size());
                    if (tasks.size() > 0) {
                        holder.photo_remove.setVisibility(View.VISIBLE);
                        holder.photo_compare.setVisibility(View.GONE);
                    } else {
                        holder.photo_compare.setVisibility(View.VISIBLE);
                        holder.photo_remove.setVisibility(View.GONE);
                    }
                }
            }

            GetTasks gt = new GetTasks();
            gt.execute();
        }

        holder.photo_compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                class SaveTask extends AsyncTask<Void, Void, Void> {


                    @Override
                    protected Void doInBackground(Void... voids) {

                        //adding to database
                        DatabaseClient.getInstance(context).getAppDatabase()
                                .comparePhotoDao()
                                .addPhotoData(mPhotoDataList.get(i));
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Save into compare list", Toast.LENGTH_LONG).show();
                    }
                }
                SaveTask st = new SaveTask();
                st.execute();
            }
        });

        holder.photo_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class DeleteTask extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        DatabaseClient.getInstance(context).getAppDatabase()
                                .comparePhotoDao()
                                .deleteBymatchId(mPhotoDataList.get(i).getId());
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        Toast.makeText(context, "Deleted from compare list", Toast.LENGTH_LONG).show();
                        if (isCompare) {
                            notifyDataSetChanged();
                        } else {
                            mPhotoDataList.remove(i);
                            notifyDataSetChanged();
                        }


                    }
                }

                DeleteTask dt = new DeleteTask();
                dt.execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhotoDataList.size();
    }

    public class photoViewHolder extends RecyclerView.ViewHolder {
        TextView photo_title,photo_url,photo_id;
        ImageView photo_img;
        TextView photo_compare,photo_remove;
        public photoViewHolder(@NonNull View itemView) {
            super(itemView);
            photo_title = itemView.findViewById(R.id.photo_title);
            photo_url = itemView.findViewById(R.id.photo_url);
            photo_id = itemView.findViewById(R.id.photo_id);
            photo_img = itemView.findViewById(R.id.photo_img);

            photo_compare = itemView.findViewById(R.id.photo_compare);
            photo_remove = itemView.findViewById(R.id.photo_remove);
        }
    }
}
