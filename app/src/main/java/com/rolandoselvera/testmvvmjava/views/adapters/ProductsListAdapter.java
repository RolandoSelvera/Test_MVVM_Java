package com.rolandoselvera.testmvvmjava.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rolandoselvera.testmvvmjava.data.models.SanitAbastecimiento;
import com.rolandoselvera.testmvvmjava.databinding.ItemProductBinding;

public class ProductsListAdapter extends ListAdapter<SanitAbastecimiento, ProductsListAdapter.ProductViewHolder> {

    private final Context context;
    private final OnItemClickedListener onItemClickedListener;

    public ProductsListAdapter(Context context, OnItemClickedListener onItemClickedListener) {
        super(DiffCallback);
        this.context = context;
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        SanitAbastecimiento current = getItem(position);

        holder.itemView.setOnClickListener(view -> onItemClickedListener.onItemClicked(current));

        holder.bind(current);
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ItemProductBinding binding;

        public ProductViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SanitAbastecimiento sanitAbastecimiento) {
            binding.txtProduct.setText(sanitAbastecimiento.getTipoAbastecimiento());
        }
    }

    private static final DiffUtil.ItemCallback<SanitAbastecimiento> DiffCallback = new DiffUtil.ItemCallback<SanitAbastecimiento>() {
        @Override
        public boolean areItemsTheSame(@NonNull SanitAbastecimiento oldItem, @NonNull SanitAbastecimiento newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull SanitAbastecimiento oldItem, @NonNull SanitAbastecimiento newItem) {
            return oldItem.getIDAbastecimiento() == newItem.getIDAbastecimiento();
        }
    };

    public interface OnItemClickedListener {
        void onItemClicked(SanitAbastecimiento sanitAbastecimiento);
    }
}

