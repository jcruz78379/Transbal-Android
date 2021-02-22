package com.example.transmobile.Entradas.RealmDB;

import com.example.transmobile.Entradas.Modelos.mCliente;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class rCliente {
    private final static int calculateIndex() {
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(mCliente.class).max("id_mcliente");
        int nextId;
        if (currentIdNum == null) {
            nextId = 0;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }

    public final static void addCliente(final mCliente cliente) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int index = rCliente.calculateIndex();
                mCliente realmcliente = realm.createObject(mCliente.class, index);
                realmcliente.setId_cliente(cliente.getId_cliente());
                realmcliente.setNombreClinete(cliente.getNombreClinete());
            }
        });
    }

    public final static void addClientes(final List<mCliente> ListClientes) {
        for (final mCliente cliente : ListClientes) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    int index = rCliente.calculateIndex();
                    mCliente realmcliente = realm.createObject(mCliente.class, index);
                    realmcliente.setId_cliente(cliente.getId_cliente());
                    realmcliente.setNombreClinete(cliente.getNombreClinete());
                }
            });
        }
    }

    public final static List<mCliente> getAllCliente() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<mCliente> clientes = realm.where(mCliente.class).findAll();
        // for(mCliente cliente: clientes){}
        return clientes;
    }


    public final static mCliente getCliente(final mCliente cliente) {
        Realm realm = Realm.getDefaultInstance();
        mCliente client = realm.where(mCliente.class).equalTo("id_clienjte", cliente.getId_cliente()).findFirst();
        return client;
    }

    public final static mCliente getClienteById(final String cliente) {
        Realm realm = Realm.getDefaultInstance();
        mCliente client = realm.where(mCliente.class).equalTo("id_cliente", cliente).findFirst();
        return client;
    }

    public final static void updateCliente(final mCliente cliente) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        mCliente rows = realm.where(mCliente.class).equalTo("id_cliente", cliente.getId_cliente()).findFirst();

        rows.setId_cliente(cliente.getId_cliente());
        rows.setNombreClinete(cliente.getNombreClinete());

        realm.insertOrUpdate(rows);
        realm.commitTransaction();
        //if(client != null){}
    }


    public final static void updateClientes(final List<mCliente> ListClientes) {
        for (final mCliente cliente : ListClientes) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            mCliente rows = realm.where(mCliente.class).equalTo("id_cliente", cliente.getId_cliente()).findFirst();

            rows.setId_cliente(cliente.getId_cliente());
            rows.setNombreClinete(cliente.getNombreClinete());

            realm.insertOrUpdate(rows);
            realm.commitTransaction();
            //if(client != null){}
        }
    }


    public final static boolean deleteCliente(final mCliente cliente) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            mCliente rows = realm.where(mCliente.class).equalTo("id_cliente", cliente.getId_cliente()).findFirst();
            rows.deleteFromRealm();
            realm.commitTransaction();
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public final static boolean deleteAllCliente() {
        try {
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            realm.delete(mCliente.class);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


}
