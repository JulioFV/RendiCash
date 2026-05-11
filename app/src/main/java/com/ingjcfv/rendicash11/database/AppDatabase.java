package com.ingjcfv.rendicash11.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ingjcfv.rendicash11.dao.DaoMovimiento;
import com.ingjcfv.rendicash11.dao.DaoProyecto;
import com.ingjcfv.rendicash11.dao.DaoUsuario;
import com.ingjcfv.rendicash11.modelo.Movimiento;
import com.ingjcfv.rendicash11.modelo.Proyecto;
import com.ingjcfv.rendicash11.modelo.Usuario;
import com.ingjcfv.rendicash11.utils.DatabaseExecutor;

@Database(
        entities = {
        Usuario.class,
        Proyecto.class,
        Movimiento.class
    },
    version = 1,
    exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public static volatile AppDatabase INSTANCE;
    public abstract DaoUsuario daoUsuario();
    public abstract DaoProyecto daoProyecto();
    public abstract DaoMovimiento daoMovimiento();

    public static AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"rendicash").
                            addCallback(roomCallback).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
    private static final Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            DatabaseExecutor.EXECUTOR.execute(() ->{
                if(INSTANCE != null){
                    Usuario invitado = new Usuario("Invitado", "123","invitado@rendicash");
                    INSTANCE.daoUsuario().crearUsuario(invitado);
                }
            });
        }
    };
}
