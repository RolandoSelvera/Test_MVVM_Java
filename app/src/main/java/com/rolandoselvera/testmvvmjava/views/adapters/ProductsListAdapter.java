package com.rolandoselvera.testmvvmjava.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rolandoselvera.testmvvmjava.R;
import com.rolandoselvera.testmvvmjava.data.models.SanitAbastecimiento;
import com.rolandoselvera.testmvvmjava.databinding.ItemProductBinding;

public class ProductsListAdapter extends ListAdapter<SanitAbastecimiento, ProductsListAdapter.ProductViewHolder> {

    private final Context context;
    private final OnItemClickedListener onItemClickedListener;

    private OnItemSelectionChangedListener onItemSelectionChangedListener;

    public void setOnItemSelectionChangedListener(OnItemSelectionChangedListener listener) {
        this.onItemSelectionChangedListener = listener;
    }


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

    class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ItemProductBinding binding;

        public ProductViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SanitAbastecimiento sanitAbastecimiento) {
            binding.txtProduct.setText(sanitAbastecimiento.getTipoAbastecimiento());

            if (sanitAbastecimiento.getEstatusAbastecimiento() != null) {
                switch (sanitAbastecimiento.getEstatusAbastecimiento().toLowerCase()) {
                    case "si":
                        binding.radioBtnYes.setChecked(true);
                        sanitAbastecimiento.setSelected(true);
                        break;

                    case "no":
                        binding.radioBtnNo.setChecked(true);
                        sanitAbastecimiento.setSelected(true);
                        break;

                    case "n/a":
                        binding.radioBtnNA.setChecked(true);
                        sanitAbastecimiento.setSelected(true);
                        break;
                }
            }

            binding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                boolean isChecked = checkedId == R.id.radioBtnYes;
                boolean isCheckedNo = checkedId == R.id.radioBtnNo;
                boolean isCheckedNA = checkedId == R.id.radioBtnNA;

                if (isChecked) {
                    sanitAbastecimiento.setSelected(true);
                    sanitAbastecimiento.setEstatusAbastecimiento(binding.radioBtnYes.getText().toString());
                }

                if (isCheckedNo) {
                    sanitAbastecimiento.setSelected(true);
                    sanitAbastecimiento.setEstatusAbastecimiento(binding.radioBtnNo.getText().toString());
                }

                if (isCheckedNA) {
                    sanitAbastecimiento.setSelected(true);
                    sanitAbastecimiento.setEstatusAbastecimiento(binding.radioBtnNA.getText().toString());
                }

                if (onItemSelectionChangedListener != null) {
                    onItemSelectionChangedListener.onItemSelectionChanged(sanitAbastecimiento, isChecked || isCheckedNo || isCheckedNA);
                }
            });
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

    public interface OnItemSelectionChangedListener {
        void onItemSelectionChanged(SanitAbastecimiento sanitAbastecimiento, boolean isSelected);
    }
}
