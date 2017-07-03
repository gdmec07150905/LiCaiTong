package com.mingrisoft.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mingrisoft.model.Tb_pwd;

public class PwdDAO {
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    public PwdDAO(Context context) {

        helper = new DBOpenHelper(context);
    }

    public void add(Tb_pwd tb_pwd) {
        db = helper.getWritableDatabase();
        // 执行添加密码操作
        db.execSQL("insert into tb_pwd (password) values (?)", new Object[] { tb_pwd.getPassword() });
    }

    /**
     * 设置密码信息
     */
    public void update(Tb_pwd tb_pwd) {
        db = helper.getWritableDatabase();
        // 执行修改密码操作
        db.execSQL("update tb_pwd set password = ?", new Object[] { tb_pwd.getPassword() });
    }


     //查找密码信息

    public Tb_pwd find() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select password from tb_pwd", null);
        if (cursor.moveToNext()) {
            return new Tb_pwd(cursor.getString(cursor.getColumnIndex("password")));
        }
        return null;
    }

    public long getCount() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(password) from tb_pwd", null);// 获取密码信息的记录数
        if (cursor.moveToNext()) {
            return cursor.getLong(0);
        }
        return 0;
    }
}
