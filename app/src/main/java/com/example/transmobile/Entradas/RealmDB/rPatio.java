package com.example.transmobile.Entradas.RealmDB;

import com.example.transmobile.Entradas.Modelos.mPatio;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class rPatio {


    private final static int calculateIndex() {
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(mPatio.class).max("id_mPatio");
        int nextId;
        if (currentIdNum == null) {
            nextId = 0;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }

    public final static void addPatio(final mPatio Patio) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int index = rPatio.calculateIndex();
                mPatio realmPatio = realm.createObject(mPatio.class, index);
                realmPatio.setId_Patio(Patio.getId_Patio());
                realmPatio.setNombrePatio(Patio.getNombrePatio());
            }
        });
    }

    public final static void addPatios(final List<mPatio> ListPatios) {
        for (final mPatio Patio : ListPatios) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    int index = rPatio.calculateIndex();
                    mPatio realmPatio = realm.createObject(mPatio.class, index);
                    realmPatio.setId_Patio(Patio.getId_Patio());
                    realmPatio.setNombrePatio(Patio.getNombrePatio());
                }
            });
        }
    }

    public final static List<mPatio> getAllPatio() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<mPatio> Patios = realm.where(mPatio.class).findAll();
        // for(mPatio Patio: Patios){}
        return Patios;
    }


    public final static mPatio getPatio(final mPatio Patio) {
        Realm realm = Realm.getDefaultInstance();
        mPatio client = realm.where(mPatio.class).equalTo("id_Patio", Patio.getId_Patio()).findFirst();
        return client;
    }

    public final static void updatePatio(final mPatio Patio) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        mPatio rows = realm.where(mPatio.class).equalTo("id_Patio", Patio.getId_Patio()).findFirst();

        rows.setId_Patio(Patio.getId_Patio());
        rows.setNombrePatio(Patio.getNombrePatio());

        realm.insertOrUpdate(rows);
        realm.commitTransaction();
        //if(client != null){}
    }


    public final static void updatePatios(final List<mPatio> ListPatios) {
        for (final mPatio Patio : ListPatios) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            mPatio rows = realm.where(mPatio.class).equalTo("id_Patio", Patio.getId_Patio()).findFirst();

            rows.setId_Patio(Patio.getId_Patio());
            rows.setNombrePatio(Patio.getNombrePatio());

            realm.insertOrUpdate(rows);
            realm.commitTransaction();
            //if(client != null){}
        }
    }


    public final static boolean deletePatio(final mPatio Patio) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            mPatio rows = realm.where(mPatio.class).equalTo("id_Patio", Patio.getId_Patio()).findFirst();
            rows.deleteFromRealm();
            realm.commitTransaction();
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public final static boolean deleteAllPatio() {
        try {
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            realm.delete(mPatio.class);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


}
