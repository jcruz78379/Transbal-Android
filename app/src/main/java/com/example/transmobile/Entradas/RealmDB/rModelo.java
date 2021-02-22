package com.example.transmobile.Entradas.RealmDB;

import com.example.transmobile.Entradas.Modelos.mCliente;
import com.example.transmobile.Entradas.Modelos.mEntradaReprint;
import com.example.transmobile.Entradas.Modelos.mModelo;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class rModelo {
    public final static int calculateIndex() {
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(mModelo.class).max("id_mModelo");
        int nextId;
        if (currentIdNum == null) {
            nextId = 0;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }

    public final static void addModelo(final mModelo Modelo) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int index = rModelo.calculateIndex();
                mModelo realmModelo = realm.createObject(mModelo.class, index);
                realmModelo.setId_modelo(Modelo.getId_modelo());
                realmModelo.setNombreModelo(Modelo.getNombreModelo());
                realmModelo.setId_color(Modelo.getId_color());
                realmModelo.setId_cliente(Modelo.getId_cliente());

            }
        });
    }

    public final static void addModelos(final List<mModelo> ListModeloes) {
        for (final mModelo Modelo : ListModeloes) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    int index = rModelo.calculateIndex();
                    mModelo realmModelo = realm.createObject(mModelo.class, index);
                    realmModelo.setId_modelo(Modelo.getId_modelo());
                    realmModelo.setNombreModelo(Modelo.getNombreModelo());
                    realmModelo.setId_color(Modelo.getId_color());
                    realmModelo.setId_cliente(Modelo.getId_cliente());
                }
            });
        }
    }

    public final static List<mModelo> getAllModelo() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<mModelo> Modelos = realm.where(mModelo.class).findAll();
        return Modelos;
    }


    public final static mModelo getModelo(final mModelo Modelo) {
        Realm realm = Realm.getDefaultInstance();
        mModelo _Modelo = realm.where(mModelo.class).equalTo("id_modelo", Modelo.getId_modelo()).findFirst();
        return _Modelo;
    }
    public final static mModelo getModeloById(final String Modelo) {
        Realm realm = Realm.getDefaultInstance();
        mModelo _Modelo = realm.where(mModelo.class).equalTo("id_modelo", Modelo).findFirst();
        return _Modelo;
    }
    public final static List<mModelo> getModeloByCliente(final String id_cliente) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<mModelo> Modelos = realm.where(mModelo.class).equalTo("id_cliente", id_cliente).findAll();
        return Modelos;
    }

    public final static void updateModelo(final mModelo Modelo) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        mModelo realmModelo = realm.where(mModelo.class).equalTo("id_modelo", Modelo.getId_modelo()).findFirst();

        realmModelo.setId_modelo(Modelo.getId_modelo());
        realmModelo.setNombreModelo(Modelo.getNombreModelo());
        realmModelo.setId_color(Modelo.getId_color());
        realmModelo.setId_cliente(Modelo.getId_cliente());

        realm.insertOrUpdate(realmModelo);
        realm.commitTransaction();
    }


    public final static void updatesModeloes(final List<mModelo> ListModeloes) {
        for (final mModelo Modelo : ListModeloes) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            mModelo realmModelo = realm.where(mModelo.class).equalTo("id_modelo", Modelo.getId_modelo()).findFirst();

            realmModelo.setId_modelo(Modelo.getId_modelo());
            realmModelo.setNombreModelo(Modelo.getNombreModelo());
            realmModelo.setId_color(Modelo.getId_color());
            realmModelo.setId_cliente(Modelo.getId_cliente());

            realm.insertOrUpdate(realmModelo);
            realm.commitTransaction();
        }
    }


    public final static Boolean deleteModelo(final mModelo Modelo) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            mModelo _Modelo = realm.where(mModelo.class).equalTo("id_modelo", Modelo.getId_modelo()).findFirst();
            _Modelo.deleteFromRealm();
            realm.commitTransaction();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public final static Boolean deleteAllModelo() {
        try {
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            realm.delete(mModelo.class);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


}
