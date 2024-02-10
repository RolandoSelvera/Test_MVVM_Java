package com.rolandoselvera.testmvvmjava.views.activities.resultsscreen;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.rolandoselvera.testmvvmjava.R;
import com.rolandoselvera.testmvvmjava.data.models.SanitAbastecimiento;
import com.rolandoselvera.testmvvmjava.databinding.ActivityResultsBinding;
import com.rolandoselvera.testmvvmjava.viewmodels.resultsviewmodel.ResultsViewModel;
import com.rolandoselvera.testmvvmjava.viewmodels.resultsviewmodel.ResultsViewModelFactory;
import com.rolandoselvera.testmvvmjava.views.activities.base.BaseActivity;
import com.rolandoselvera.testmvvmjava.views.adapters.ProductsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends BaseActivity<ActivityResultsBinding> implements ProductsListAdapter.OnItemSelectionChangedListener {

    private ProductsListAdapter adapter;

    private ResultsViewModelFactory factory;
    private ResultsViewModel viewModel;

    private SanitAbastecimiento mProducts;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_results;
    }

    @Override
    protected void initializeView() {
        setToolbarTitle(getString(R.string.catalog));
    }

    @Override
    protected void initComponents() {
        showProgress();
        viewModel.loadProducts();
    }

    @Override
    protected void initViewModel() {
        factory = new ResultsViewModelFactory(this.getApplicationContext());
        viewModel = new ViewModelProvider(this, factory).get(ResultsViewModel.class);

        viewModel.getProductsLiveData().observe(this, productsList -> {
            if (productsList.size() > 0) {
                binding.titleStatus.setVisibility(View.GONE);
                setupRecyclerAdapter(productsList);
            }
        });

        viewModel.hasUpdated().observe(this, response -> {
            if (response) {
                toast(getString(R.string.update_successful));
            } else {
                showAlert(getString(R.string.update_unsuccessful), "Intente de nuevo más tarde", this::onBackPressed);
            }
        });

        viewModel.loader().observe(this, isLoading -> {
            if (!isLoading) hideProgress();
        });
    }

    private void setupRecyclerAdapter(List<SanitAbastecimiento> productsList) {
        binding.recyclerView.setVisibility(View.VISIBLE);
        adapter = new ProductsListAdapter(this, products -> {
            mProducts = products;
            adapter.notifyDataSetChanged();
        });
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(new ArrayList<>(productsList));
        adapter.setOnItemSelectionChangedListener(this);
    }

    @Override
    public void onItemSelectionChanged(SanitAbastecimiento product, boolean isSelected) {
        if (isSelected) {
            viewModel.updateProductStatus(product.getIDAbastecimiento(), product.getEstatusAbastecimiento());
        }
    }
}