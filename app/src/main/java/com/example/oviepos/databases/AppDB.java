package com.example.oviepos.databases;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.oviepos.databases.helpers.DataConverter;
import com.example.oviepos.databases.helpers.DateConverter;
import com.example.oviepos.databases.interfaces.InterfaceCart;
import com.example.oviepos.databases.interfaces.InterfaceProductCategory;
import com.example.oviepos.databases.interfaces.InterfaceProducts;
import com.example.oviepos.databases.interfaces.InterfaceTransactions;
import com.example.oviepos.databases.models.responses.Cart;
import com.example.oviepos.databases.models.responses.Products;
import com.example.oviepos.databases.models.responses.ProductsCategory;
import com.example.oviepos.databases.models.responses.Transactions;
import com.example.oviepos.utils.Constants;

@TypeConverters({DateConverter.class, DataConverter.class})
@Database(
        entities = {
                ProductsCategory.class,
                Products.class,
                Transactions.class,
                Cart.class
        },
        version = Constants.versionDb,
        exportSchema = Constants.exportSchema
)
public abstract class AppDB extends RoomDatabase {
    public abstract InterfaceProductCategory productCategory();
    public abstract InterfaceProducts products();
    public abstract InterfaceCart cart();
    public abstract InterfaceTransactions transactions();

    private static volatile AppDB INSTANCE;

    public static AppDB getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDB.class,
                            Constants.nameDb
                    )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
