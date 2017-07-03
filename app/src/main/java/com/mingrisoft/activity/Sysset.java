package com.mingrisoft.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mingrisoft.dao.PwdDAO;
import com.mingrisoft.model.Tb_pwd;

public class Sysset extends Activity {
    private EditText txtpwd;
    private Button btnSet, btnsetCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sysset);

        txtpwd = (EditText) findViewById(R.id.txtPwd);
        btnSet = (Button) findViewById(R.id.btnSet);
        btnsetCancel = (Button) findViewById(R.id.btnsetCancel);

        btnSet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                PwdDAO pwdDAO = new PwdDAO(Sysset.this);
                Tb_pwd tb_pwd = new Tb_pwd(txtpwd.getText().toString());
                if (pwdDAO.getCount() == 0) {
                    pwdDAO.add(tb_pwd);
                } else {
                    pwdDAO.update(tb_pwd);
                }

                Toast.makeText(Sysset.this, "〖密码〗设置成功！", Toast.LENGTH_SHORT).show();
            }
        });

        btnsetCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                txtpwd.setText("");
                txtpwd.setHint("请输入密码");
            }
        });
    }
}
