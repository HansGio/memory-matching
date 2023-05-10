package com.example.memorymatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.memorymatch.databinding.ItemImageBinding;

public class ImageAdapter extends BaseAdapter {

    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 16;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            ItemImageBinding binding = ItemImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

            holder = new ViewHolder(binding);
            holder.view.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.binding.getRoot().setImageResource(R.drawable.baseline_question_mark_24);

        return holder.view;
    }

    private class ViewHolder {
        private View view;
        private ItemImageBinding binding;

        public ViewHolder(ItemImageBinding binding) {
            this.view = binding.getRoot();
            this.binding = binding;
        }
    }
}
