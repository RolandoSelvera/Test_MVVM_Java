package com.rolandoselvera.testmvvmjava.views.activities.downloadscreen;

import androidx.lifecycle.ViewModelProvider;

import com.rolandoselvera.testmvvmjava.R;
import com.rolandoselvera.testmvvmjava.data.models.SanitAbastecimiento;
import com.rolandoselvera.testmvvmjava.databinding.ActivityDownloadBinding;
import com.rolandoselvera.testmvvmjava.viewmodels.downloadviewmodel.DownloadViewModel;
import com.rolandoselvera.testmvvmjava.viewmodels.downloadviewmodel.DownloadViewModelFactory;
import com.rolandoselvera.testmvvmjava.views.activities.base.BaseActivity;

import java.util.List;

public class DownloadActivity extends BaseActivity<ActivityDownloadBinding> {

    private DownloadViewModelFactory factory;
    private DownloadViewModel viewModel;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_download;
    }

    @Override
    protected void initializeView() {
        setToolbarTitle(getString(R.string.download));
    }

    @Override
    protected void initComponents() {
        showProgress();
        setupViewModels();
    }

    @Override
    protected void initViewModel() {
        factory = new DownloadViewModelFactory(this);
        viewModel = new ViewModelProvider(this, factory).get(DownloadViewModel.class);

        viewModel.getSupplying().observe(this, response -> {
            if (response != null) {
                switch (response.getStatus()) {
                    case SUCCESS:
                        if (response.getSanitAbastecimiento() != null) {
                            showAlert(getString(R.string.download_successful), response.getMessage(), () -> {
                                insertProducts(response.getSanitAbastecimiento());
                                onBackPressed();
                            });
                        }
                        break;
                    case FAILURE:
                    case ERROR:
                        showAlert(getString(R.string.download_unsuccessful), response.getMessage(), this::onBackPressed);
                        break;
                }
            }
        });

        viewModel.hasDeleted().observe(this, response -> {
            if (response) {
                toast(getString(R.string.deleted_successful));
            }
        });

        viewModel.hasInserted().observe(this, response -> {
            if (response) {
                toast(getString(R.string.save_successful));
            }
        });

        viewModel.loader().observe(this, isLoading -> {
            if (!isLoading) hideProgress();
        });
    }

    private void setupViewModels() {
        viewModel.deleteAllProducts();
        viewModel.getProducts();
    }

    private void insertProducts(List<SanitAbastecimiento> sanitAbastecimientoList) {
        viewModel.insertProduct(sanitAbastecimientoList);
    }
}