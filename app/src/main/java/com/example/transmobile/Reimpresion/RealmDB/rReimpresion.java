package com.example.transmobile.Reimpresion.RealmDB;

import com.example.transmobile.Reimpresion.Modelos.mReimpresion;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class rReimpresion {

    private final static int calculateIndex() {
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(mReimpresion.class).max("id_mReimpresion");
        int nextId;
        if (currentIdNum == null) {
            nextId = 0;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }

    public final static void addReimpresion(final mReimpresion Reimpresion) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int index = rReimpresion.calculateIndex();
                mReimpresion realmReimpresion = realm.createObject(mReimpresion.class, index);
                realmReimpresion.setId_patio(Reimpresion.getId_patio());
                realmReimpresion.setPatio(Reimpresion.getPatio());
                realmReimpresion.setId_cliente(Reimpresion.getId_cliente());
                realmReimpresion.setCliente(Reimpresion.getCliente());
                realmReimpresion.setChasis(Reimpresion.getChasis());
                realmReimpresion.setUsado(Reimpresion.getUsado());
                realmReimpresion.setPlaca(Reimpresion.getPlaca());
                realmReimpresion.setUsuario(Reimpresion.getUsuario());
                realmReimpresion.setFecha_Imprecion(Reimpresion.getFecha_Imprecion());

            }
        });
    }

    public final static void addReimpresions(final List<mReimpresion> ListReimpresions) {
        for (final mReimpresion Reimpresion : ListReimpresions) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    int index = rReimpresion.calculateIndex();
                    mReimpresion realmReimpresion = realm.createObject(mReimpresion.class, index);
                    realmReimpresion.setId_patio(Reimpresion.getId_patio());
                    realmReimpresion.setPatio(Reimpresion.getPatio());
                    realmReimpresion.setId_cliente(Reimpresion.getId_cliente());
                    realmReimpresion.setCliente(Reimpresion.getCliente());
                    realmReimpresion.setChasis(Reimpresion.getChasis());
                    realmReimpresion.setUsado(Reimpresion.getUsado());
                    realmReimpresion.setPlaca(Reimpresion.getPlaca());
                    realmReimpresion.setUsuario(Reimpresion.getUsuario());
                    realmReimpresion.setFecha_Imprecion(Reimpresion.getFecha_Imprecion());
                }
            });
        }
    }

    public final static List<mReimpresion> getAllReimpresion() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<mReimpresion> Reimpresions = realm.where(mReimpresion.class).findAll();
        // for(mReimpresion Reimpresion: Reimpresions){}
        return Reimpresions;
    }


    public final static mReimpresion getReimpresion(final mReimpresion Reimpresion) {
        Realm realm = Realm.getDefaultInstance();
        mReimpresion client = realm.where(mReimpresion.class).equalTo("id_clinete", Reimpresion.getId_mReimpresion()).findFirst();
        return client;
    }

    public final static void updateReimpresion(final mReimpresion Reimpresion) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        mReimpresion realmReimpresion = realm.where(mReimpresion.class).equalTo("id_Reimpresion", Reimpresion.getId_mReimpresion()).findFirst();

        realmReimpresion.setId_patio(Reimpresion.getId_patio());
        realmReimpresion.setPatio(Reimpresion.getPatio());
        realmReimpresion.setId_cliente(Reimpresion.getId_cliente());
        realmReimpresion.setCliente(Reimpresion.getCliente());
        realmReimpresion.setChasis(Reimpresion.getChasis());
        realmReimpresion.setUsado(Reimpresion.getUsado());
        realmReimpresion.setPlaca(Reimpresion.getPlaca());
        realmReimpresion.setUsuario(Reimpresion.getUsuario());
        realmReimpresion.setFecha_Imprecion(Reimpresion.getFecha_Imprecion());
        realm.insertOrUpdate(realmReimpresion);
        realm.commitTransaction();
        //if(client != null){}
    }


    public final static void updateReimpresions(final List<mReimpresion> ListReimpresions) {
        for (final mReimpresion Reimpresion : ListReimpresions) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            mReimpresion realmReimpresion = realm.where(mReimpresion.class).equalTo("id_Reimpresion", Reimpresion.getId_mReimpresion()).findFirst();

            realmReimpresion.setId_patio(Reimpresion.getId_patio());
            realmReimpresion.setPatio(Reimpresion.getPatio());
            realmReimpresion.setId_cliente(Reimpresion.getId_cliente());
            realmReimpresion.setCliente(Reimpresion.getCliente());
            realmReimpresion.setChasis(Reimpresion.getChasis());
            realmReimpresion.setUsado(Reimpresion.getUsado());
            realmReimpresion.setPlaca(Reimpresion.getPlaca());
            realmReimpresion.setUsuario(Reimpresion.getUsuario());
            realmReimpresion.setFecha_Imprecion(Reimpresion.getFecha_Imprecion());

            realm.insertOrUpdate(realmReimpresion);
            //if(client != null){}
        }
    }


    public final static boolean deleteReimpresion(final mReimpresion Reimpresion) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            mReimpresion rows = realm.where(mReimpresion.class).equalTo("id_Reimpresion", Reimpresion.getId_mReimpresion()).findFirst();
            rows.deleteFromRealm();
            realm.commitTransaction();
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public final static boolean deleteReimpresionByChais(final String chasis ) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            List<mReimpresion> _Reimprecion =  realm.where(mReimpresion.class).equalTo("chasis", chasis).findAll();
            for (mReimpresion x:_Reimprecion) {
                x.deleteFromRealm();
                realm.commitTransaction();
            }
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public final static boolean deleteAllReimpresion() {
        try {
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            realm.delete(mReimpresion.class);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
