package com.example.transmobile.Entradas.RealmDB;
import com.example.transmobile.Entradas.Modelos.mEntrada;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class rEntrada {

    private final static int calculateIndex() {
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(mEntrada.class).max("id_mEntrada");
        int nextId;
        if (currentIdNum == null) {
            nextId = 0;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }

    public final static boolean addEntrada(final mEntrada Entrada) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    int index = rEntrada.calculateIndex();
                    mEntrada realmEntrada = realm.createObject(mEntrada.class, index);
                    realmEntrada.setId_clinete(Entrada.getId_clinete());
                    realmEntrada.setChasis(Entrada.getChasis());
                    realmEntrada.setLicenciaConductor(Entrada.getLicenciaConductor());
                    realmEntrada.setCedulaConductor(Entrada.getCedulaConductor());
                    realmEntrada.setNombreCondutor(Entrada.getNombreCondutor());
                    realmEntrada.setId_usuario(Entrada.getId_usuario());
                    realmEntrada.setFecha(Entrada.getFecha());
                    realmEntrada.setObrevaciones(Entrada.getObrevaciones());
                    realmEntrada.setId_patio(Entrada.getId_patio());
                    realmEntrada.setGasolina(Entrada.getGasolina());
                    realmEntrada.setKilometraje(Entrada.getKilometraje());
                    realmEntrada.setId_modelo(Entrada.getId_modelo());
                    realmEntrada.setId_color(Entrada.getId_color());
                }
            });
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    public final static void addEntradas(final List<mEntrada> ListEntradas) {
        for (final mEntrada Entrada : ListEntradas) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    int index = rEntrada.calculateIndex();
                    mEntrada realmEntrada = realm.createObject(mEntrada.class, index);
                    realmEntrada.setId_clinete(Entrada.getId_clinete());
                    realmEntrada.setChasis(Entrada.getChasis());
                    realmEntrada.setLicenciaConductor(Entrada.getLicenciaConductor());
                    realmEntrada.setCedulaConductor(Entrada.getCedulaConductor());
                    realmEntrada.setNombreCondutor(Entrada.getNombreCondutor());
                    realmEntrada.setId_usuario(Entrada.getId_usuario());
                    realmEntrada.setFecha(Entrada.getFecha());
                    realmEntrada.setObrevaciones(Entrada.getObrevaciones());
                    realmEntrada.setId_patio(Entrada.getId_patio());
                    realmEntrada.setGasolina(Entrada.getGasolina());
                    realmEntrada.setKilometraje(Entrada.getKilometraje());
                    realmEntrada.setId_modelo(Entrada.getId_modelo());
                    realmEntrada.setId_color(Entrada.getId_color());
                }
            });
        }
    }

    public final static List<mEntrada> getAllEntrada() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<mEntrada> Entradaes = realm.where(mEntrada.class).findAll();
        return Entradaes;
    }


    public final static mEntrada getEntada(final mEntrada Entrada) {
        Realm realm = Realm.getDefaultInstance();
        mEntrada _Entrada = realm.where(mEntrada.class).equalTo("id_mEntrada", Entrada.getId_mEntrada()).findFirst();
        return _Entrada;
    }

    public final static void updateEntrada(final mEntrada Entrada) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        mEntrada _Entrada = realm.where(mEntrada.class).equalTo("id_Entrada", Entrada.getId_mEntrada()).findFirst();

        _Entrada.setId_clinete(Entrada.getId_clinete());
        _Entrada.setChasis(Entrada.getChasis());
        _Entrada.setLicenciaConductor(Entrada.getLicenciaConductor());
        _Entrada.setCedulaConductor(Entrada.getCedulaConductor());
        _Entrada.setNombreCondutor(Entrada.getNombreCondutor());
        _Entrada.setId_usuario(Entrada.getId_usuario());
        _Entrada.setFecha(Entrada.getFecha());
        _Entrada.setObrevaciones(Entrada.getObrevaciones());
        _Entrada.setId_patio(Entrada.getId_patio());
        _Entrada.setGasolina(Entrada.getGasolina());
        _Entrada.setKilometraje(Entrada.getKilometraje());
        _Entrada.setId_modelo(Entrada.getId_modelo());
        _Entrada.setId_color(Entrada.getId_color());

        realm.insertOrUpdate(_Entrada);
        realm.commitTransaction();
    }


    public final static void updatesEntradaes(final List<mEntrada> ListEntradaes) {
        for (final mEntrada Entrada : ListEntradaes) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            mEntrada _Entrada = realm.where(mEntrada.class).equalTo("id_Entrada", Entrada.getId_mEntrada()).findFirst();

            _Entrada.setId_clinete(Entrada.getId_clinete());
            _Entrada.setChasis(Entrada.getChasis());
            _Entrada.setLicenciaConductor(Entrada.getLicenciaConductor());
            _Entrada.setCedulaConductor(Entrada.getCedulaConductor());
            _Entrada.setNombreCondutor(Entrada.getNombreCondutor());
            _Entrada.setId_usuario(Entrada.getId_usuario());
            _Entrada.setFecha(Entrada.getFecha());
            _Entrada.setObrevaciones(Entrada.getObrevaciones());
            _Entrada.setId_patio(Entrada.getId_patio());
            _Entrada.setGasolina(Entrada.getGasolina());
            _Entrada.setKilometraje(Entrada.getKilometraje());
            _Entrada.setId_modelo(Entrada.getId_modelo());
            _Entrada.setId_color(Entrada.getId_color());

            realm.insertOrUpdate(_Entrada);
            realm.commitTransaction();
        }
    }


    public final static Boolean deleteEntrada(final mEntrada Entrada) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            mEntrada _Entrada = realm.where(mEntrada.class).equalTo("id_Entrada", Entrada.getId_mEntrada()).findFirst();
            _Entrada.deleteFromRealm();
            realm.commitTransaction();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public final static Boolean deleteEntradaByChasis(final String chasis) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            List<mEntrada> _Entrada =  realm.where(mEntrada.class).equalTo("chasis", chasis).findAll();
            for (mEntrada x:_Entrada) {
                x.deleteFromRealm();
                realm.commitTransaction();
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }



    public final static Boolean deleteAllEntrada() {
        try {
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            realm.delete(mEntrada.class);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


}
