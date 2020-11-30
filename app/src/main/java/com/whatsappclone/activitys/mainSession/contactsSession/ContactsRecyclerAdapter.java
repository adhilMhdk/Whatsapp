package com.whatsappclone.activitys.mainSession.contactsSession;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.whatsappclone.R;
import com.whatsappclone.modelClass.ContactsModel;
import com.whatsappclone.serverHelpers.Server;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.ViewHolder> {

    ArrayList<ContactsModel> models;
    OnClickListener onClickListener;

    public ContactsRecyclerAdapter(ArrayList<ContactsModel> models, OnClickListener onClickListener) {
        this.models = models;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.profileName.setText(models.get(position).getName());
        if (models.get(position).getImage()!=null){
            Picasso.get().load(models.get(position).getImage()).into(holder.profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView profileName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            profileName = itemView.findViewById(R.id.profile_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.recyclerItemClick(getAdapterPosition());
                }
            });

        }

    }
    public interface OnClickListener{
        void recyclerItemClick(int AdapterPosition);
    }



}
