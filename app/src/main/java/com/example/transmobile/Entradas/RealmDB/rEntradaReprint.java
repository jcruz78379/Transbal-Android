package com.example.transmobile.Entradas.RealmDB;

import com.example.transmobile.Entradas.Modelos.mCliente;
import com.example.transmobile.Entradas.Modelos.mEntrada;
import com.example.transmobile.Entradas.Modelos.mEntradaReprint;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class rEntradaReprint {

    private final static int calculateIndex() {
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(mEntradaReprint.class).max("id_mEntradaReprint");
        int nextId;
        if (currentIdNum == null) {
            nextId = 0;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }

    public final static void addEntradaReprint(final mEntradaReprint EntradaReprint) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int index = rEntradaReprint.calculateIndex();
                mEntradaReprint realmEntradaReprint = realm.createObject(mEntradaReprint.class, index);
                realmEntradaReprint.setId_clinete(EntradaReprint.getId_clinete());
                realmEntradaReprint.setChasis(EntradaReprint.getChasis());
                realmEntradaReprint.setFecha(EntradaReprint.getFecha());
            }
        });
    }

    public final static void addEntradaReprints(final List<mEntradaReprint> ListEntradaReprints) {
        for (final mEntradaReprint EntradaReprint : ListEntradaReprints) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    int index = rEntradaReprint.calculateIndex();
                    mEntradaReprint realmEntradaReprint = realm.createObject(mEntradaReprint.class, index);
                    realmEntradaReprint.setId_clinete(EntradaReprint.getId_clinete());
                    realmEntradaReprint.setChasis(EntradaReprint.getChasis());
                    realmEntradaReprint.setFecha(EntradaReprint.getFecha());
                }
            });
        }
    }

    public final static List<mEntradaReprint> getAllEntradaReprint() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<mEntradaReprint> EntradaReprintes = realm.where(mEntradaReprint.class).findAll();
        return EntradaReprintes;
    }


    public final static mEntradaReprint getEntradaReprint(final mEntradaReprint EntradaReprint) {
        Realm realm = Realm.getDefaultInstance();
        mEntradaReprint _EntradaReprint = realm.where(mEntradaReprint.class).equalTo("id_mEntradaReprint", EntradaReprint.getId_mEntradaReprint()).findFirst();
        //if(cliente != null){}
        return _EntradaReprint;
    }

    public final static void updateEntradaReprint(final mEntradaReprint EntradaReprint) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        mEntradaReprint realmEntradaReprint = realm.where(mEntradaReprint.class).equalTo("id_mEntradaReprint", EntradaReprint.getId_mEntradaReprint()).findFirst();

        realmEntradaReprint.setId_clinete(EntradaReprint.getId_clinete());
        realmEntradaReprint.setChasis(EntradaReprint.getChasis());
        realmEntradaReprint.setFecha(EntradaReprint.getFecha());

        realm.insertOrUpdate(realmEntradaReprint);
        realm.commitTransaction();
    }


    public final static void updatesEntradaReprintes(final List<mEntradaReprint> ListEntradaReprintes) {
        for (final mEntradaReprint EntradaReprint : ListEntradaReprintes) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            mEntradaReprint realmEntradaReprint = realm.where(mEntradaReprint.class).equalTo("id_mEntradaReprint", EntradaReprint.getId_mEntradaReprint()).findFirst();

            realmEntradaReprint.setId_clinete(EntradaReprint.getId_clinete());
            realmEntradaReprint.setChasis(EntradaReprint.getChasis());
            realmEntradaReprint.setFecha(EntradaReprint.getFecha());

            realm.insertOrUpdate(realmEntradaReprint);
            realm.commitTransaction();
        }
    }


    public final static Boolean deleteEntradaReprint(final mEntradaReprint EntradaReprint) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            mEntradaReprint _EntradaReprint = realm.where(mEntradaReprint.class).equalTo("id_mEntradaReprint", EntradaReprint.getId_mEntradaReprint()).findFirst();
            _EntradaReprint.deleteFromRealm();
            realm.commitTransaction();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public final static Boolean deleteAllEntradaReprint() {
        try {
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            realm.delete(mEntradaReprint.class);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


}
