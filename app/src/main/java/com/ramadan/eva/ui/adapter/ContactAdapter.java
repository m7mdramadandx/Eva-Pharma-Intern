package com.ramadan.eva.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ramadan.eva.R;
import com.ramadan.eva.database.Contact;
import com.ramadan.eva.databinding.ContactItemBinding;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<Contact> contactArrayList;

    public ContactAdapter(List<Contact> contactArrayList) {
        this.contactArrayList = contactArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.contact_item, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = contactArrayList.get(position);
        holder.contactItemBinding.setContactItem(contact);
//        holder.img.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.zoom_in));
    }

    @Override
    public int getItemCount() {
        return Math.max(contactArrayList.size(), 0);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        ContactItemBinding contactItemBinding;

        ViewHolder(ContactItemBinding contactItemBinding) {
            super(contactItemBinding.getRoot());
            this.contactItemBinding = contactItemBinding;
        }
    }
}

