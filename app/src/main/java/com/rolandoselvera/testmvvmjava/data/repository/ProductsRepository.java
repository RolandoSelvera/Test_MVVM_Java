package com.rolandoselvera.testmvvmjava.data.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rolandoselvera.testmvvmjava.core.base.App;
import com.rolandoselvera.testmvvmjava.data.local.db.DatabaseHelper;
import com.rolandoselvera.testmvvmjava.data.models.SanitAbastecimiento;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class ProductsRepository {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public ProductsRepository(Context context) {
        dbHelper = App.getInstance().getDatabaseHelper();
        open();
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public Single<Boolean> insertProductsToDB(List<SanitAbastecimiento> productList) {
        return Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            try {
                database.beginTransaction();

                for (SanitAbastecimiento product : productList) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.SUPPLYING_TYPE, product.getTipoAbastecimiento());
                    values.put(DatabaseHelper.USER_CREATION, product.getUsuarioCreacion());
                    values.put(DatabaseHelper.USER_MODIFICATION, product.getUsuarioModificacion());
                    values.put(DatabaseHelper.USER_DELETION, product.getUsuarioEliminacion());
                    values.put(DatabaseHelper.DATE_CREATION, product.getFechaCreacion());
                    values.put(DatabaseHelper.DATE_MODIFICATION, product.getFechaModificacion());
                    values.put(DatabaseHelper.DATE_DELETION, product.getFechaEliminacion());
                    values.put(DatabaseHelper.SUPPLYING_STATUS, product.getEstatusAbastecimiento());
                    values.put(DatabaseHelper.IMG_PATH, product.getCurrentImagePath());

                    long rowId = database.insert(DatabaseHelper.TABLE_PRODUCTS, null, values);
                    if (rowId == -1) {
                        emitter.onSuccess(false);
                        return;
                    }
                }

                database.setTransactionSuccessful();
                emitter.onSuccess(true);

            } catch (Exception e) {
                emitter.onError(e);

            } finally {
                database.endTransaction();
            }
        }).subscribeOn(Schedulers.io());
    }


    public Observable<List<SanitAbastecimiento>> getProducts() {
        return Observable.fromCallable(this::getProductsFromDB)
                .subscribeOn(Schedulers.io());
    }


    @SuppressLint("Range")
    private List<SanitAbastecimiento> getProductsFromDB() {
        List<SanitAbastecimiento> productsList = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_PRODUCTS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                SanitAbastecimiento product = new SanitAbastecimiento();
                product.setIDAbastecimiento(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.SUPPLYING_ID)));
                product.setTipoAbastecimiento(cursor.getString(cursor.getColumnIndex(DatabaseHelper.SUPPLYING_TYPE)));
                product.setUsuarioCreacion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_CREATION)));
                product.setUsuarioModificacion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_MODIFICATION)));
                product.setUsuarioEliminacion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.USER_DELETION)));
                product.setFechaCreacion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATE_CREATION)));
                product.setFechaModificacion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATE_MODIFICATION)));
                product.setFechaEliminacion(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATE_DELETION)));
                product.setEstatusAbastecimiento(cursor.getString(cursor.getColumnIndex(DatabaseHelper.SUPPLYING_STATUS)));
                product.setCurrentImagePath(cursor.getString(cursor.getColumnIndex(DatabaseHelper.IMG_PATH)));

                productsList.add(product);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return productsList;
    }

    public Single<Boolean> deleteAllProducts() {
        return Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            try {
                int rowsDeleted = database.delete(DatabaseHelper.TABLE_PRODUCTS, null, null);
                emitter.onSuccess(rowsDeleted > 0);
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io());
    }

    public Single<Boolean> updateProductStatus(long productId, String newStatus) {
        return Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            try {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.SUPPLYING_STATUS, newStatus);

                int rowsUpdated = database.update(
                        DatabaseHelper.TABLE_PRODUCTS,
                        values,
                        DatabaseHelper.SUPPLYING_ID + " = ?",
                        new String[]{String.valueOf(productId)}
                );

                emitter.onSuccess(rowsUpdated > 0);
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io());
    }

    public Single<Boolean> updateImagePath(long productId, String imagePath) {
        return Single.create((SingleOnSubscribe<Boolean>) emitter -> {
            try {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.IMG_PATH, imagePath);

                int rowsUpdated = database.update(
                        DatabaseHelper.TABLE_PRODUCTS,
                        values,
                        DatabaseHelper.SUPPLYING_ID + " = ?",
                        new String[]{String.valueOf(productId)}
                );

                emitter.onSuccess(rowsUpdated > 0);
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io());
    }

}
