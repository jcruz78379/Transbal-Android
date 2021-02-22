package com.example.transmobile.Entradas.RealmDB;

import com.example.transmobile.Entradas.Modelos.mCliente;
import com.example.transmobile.Entradas.Modelos.mModelo;
import com.example.transmobile.Entradas.Modelos.mUsuario;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class rUsuario {

    private final static int calculateIndex() {
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(mUsuario.class).max("id_mUsuario");
        int nextId;
        if (currentIdNum == null) {
            nextId = 0;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }

    public final static void addUsuario(final mUsuario Usuario) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int index = rUsuario.calculateIndex();
                mUsuario realmUsuario = realm.createObject(mUsuario.class, index);
                realmUsuario.setId_usuario(Usuario.getId_usuario());
                realmUsuario.setUsername(Usuario.getUsername());
                realmUsuario.setPass(Usuario.getPass());

            }
        });
    }

    public final static void addUsuarioes(final List<mUsuario> ListUsuarioes) {
        for (final mUsuario Usuario : ListUsuarioes) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    int index = rUsuario.calculateIndex();
                    mUsuario realmUsuario = realm.createObject(mUsuario.class, index);
                    realmUsuario.setId_usuario(Usuario.getId_usuario());
                    realmUsuario.setUsername(Usuario.getUsername());
                    realmUsuario.setPass(Usuario.getPass());
                }
            });
        }
    }

    public final static List<mUsuario> getAllUsuario() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<mUsuario> Usuarioes = realm.where(mUsuario.class).findAll();
        return Usuarioes;
    }


    public final static mUsuario getUsuario(final mUsuario Usuario) {
        Realm realm = Realm.getDefaultInstance();
        mUsuario _Usuario = realm.where(mUsuario.class).equalTo("id_Usuario", Usuario.getId_usuario()).findFirst();
        //if(cliente != null){}
        return _Usuario;
    }

    public final static void updateUsuario(final mUsuario Usuario) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        mUsuario realmUsuario = realm.where(mUsuario.class).equalTo("id_Usuario", Usuario.getId_usuario()).findFirst();

        realmUsuario.setId_usuario(Usuario.getId_usuario());
        realmUsuario.setUsername(Usuario.getUsername());
        realmUsuario.setPass(Usuario.getPass());

        realm.insertOrUpdate(realmUsuario);
        realm.commitTransaction();
    }


    public final static void updatesUsuarioes(final List<mUsuario> ListUsuarioes) {
        for (final mUsuario Usuario : ListUsuarioes) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            mUsuario realmUsuario = realm.where(mUsuario.class).equalTo("id_Usuario", Usuario.getId_usuario()).findFirst();

            realmUsuario.setId_usuario(Usuario.getId_usuario());
            realmUsuario.setUsername(Usuario.getUsername());
            realmUsuario.setPass(Usuario.getPass());
            realm.insertOrUpdate(realmUsuario);
            realm.commitTransaction();
        }
    }


    public final static Boolean deleteUsuario(final mUsuario Usuario) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            mUsuario realmUsuario = realm.where(mUsuario.class).equalTo("id_Usuario", Usuario.getId_usuario()).findFirst();
            realmUsuario.deleteFromRealm();
            realm.commitTransaction();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public final static Boolean deleteAllUsuario() {
        try {
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            realm.delete(mUsuario.class);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


}
