package com.example.transmobile.Entradas.RealmDB;

import com.example.transmobile.Entradas.Modelos.mColor;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class rColor {

    private final static int calculateIndex() {
        Realm realm = Realm.getDefaultInstance();
        Number currentIdNum = realm.where(mColor.class).max("id_mColor");
        int nextId;
        if (currentIdNum == null) {
            nextId = 0;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }

    public final static void addColor(final mColor color) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int index = rColor.calculateIndex();
                mColor realmcolor = realm.createObject(mColor.class, index);
                realmcolor.setId_color(color.getId_color());
                realmcolor.setNombreColor(color.getNombreColor());

            }
        });
    }

    public final static void addColores(final List<mColor> ListColores) {
        for (final mColor color : ListColores) {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    int index = rColor.calculateIndex();
                    mColor realmcolor = realm.createObject(mColor.class, index);
                    realmcolor.setId_color(color.getId_color());
                    realmcolor.setNombreColor(color.getNombreColor());
                }
            });
        }
    }

    public final static List<mColor> getAllColor() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<mColor> colores = realm.where(mColor.class).findAll();
        return colores;
    }


    public final static mColor getcolor(final mColor color) {
        Realm realm = Realm.getDefaultInstance();
        mColor _color = realm.where(mColor.class).equalTo("id_color", color.getId_color()).findFirst();
        //if(cliente != null){}
        return _color;
    }

    public final static mColor getcolorByid(final String color) {
        Realm realm = Realm.getDefaultInstance();
        mColor _color = realm.where(mColor.class).equalTo("id_color", color).findFirst();
        //if(cliente != null){}
        return _color;
    }

    public final static void updateColor(final mColor color) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        mColor realmcolor = realm.where(mColor.class).equalTo("id_color", color.getId_color()).findFirst();

        realmcolor.setId_color(color.getId_color());
        realmcolor.setNombreColor(color.getNombreColor());

        realm.insertOrUpdate(realmcolor);
        realm.commitTransaction();
    }


    public final static void updatesColores(final List<mColor> ListColores) {
        for (final mColor color : ListColores) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();

            mColor realmcolor = realm.where(mColor.class).equalTo("id_color", color.getId_color()).findFirst();

            realmcolor.setId_color(color.getId_color());
            realmcolor.setNombreColor(color.getNombreColor());

            realm.insertOrUpdate(realmcolor);
            realm.commitTransaction();
        }
    }


    public final static Boolean deleteColor(final mColor color) {
        try {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            mColor realmcolor = realm.where(mColor.class).equalTo("id_color", color.getId_color()).findFirst();
            realmcolor.deleteFromRealm();
            realm.commitTransaction();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public final static Boolean deleteAllColor() {
        try {
            Realm realm = Realm.getDefaultInstance();

            realm.beginTransaction();
            realm.delete(mColor.class);
            realm.commitTransaction();
            realm.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


}
