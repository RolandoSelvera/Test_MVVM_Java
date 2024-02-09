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

public class ResultsActivity extends BaseActivity<ActivityResultsBinding> {

    private ProductsListAdapter adapter;

    private ResultsViewModelFactory factory;
    private ResultsViewModel viewModel;


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

        viewModel.loader().observe(this, isLoading -> {
            if (!isLoading) hideProgress();
        });
    }

    private void setupRecyclerAdapter(List<SanitAbastecimiento> productsList) {
        binding.recyclerView.setVisibility(View.VISIBLE);
        adapter = new ProductsListAdapter(this, products -> {
            toast(products.getTipoAbastecimiento());
            adapter.notifyDataSetChanged();
        });
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(new ArrayList<>(productsList));
    }
}