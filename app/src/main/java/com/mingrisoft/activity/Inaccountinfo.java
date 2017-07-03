package com.mingrisoft.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mingrisoft.dao.InaccountDAO;
import com.mingrisoft.model.Tb_inaccount;

public class Inaccountinfo extends Activity {
    private static final String FLAG = "id";
    private ListView lvinfo;
    private String strType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inaccountinfo);
        lvinfo = (ListView) findViewById(R.id.lvinaccountinfo);

        showInfo(R.id.btnininfo);

        lvinfo.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strInfo = String.valueOf(((TextView) view).getText());
                String strid = strInfo.substring(0, strInfo.indexOf('|'));
                Intent intent = new Intent(Inaccountinfo.this, InfoManage.class);
                intent.putExtra(FLAG, new String[] { strid, strType });
                startActivity(intent);
            }
        });
    }

    private void showInfo(int intType) {
        String[] strInfos = null;
        ArrayAdapter<String> arrayAdapter = null;
        strType = "btnininfo";
        InaccountDAO inaccountinfo = new InaccountDAO(Inaccountinfo.this);

        List<Tb_inaccount> listinfos = inaccountinfo.getScrollData(0, (int) inaccountinfo.getCount());
        strInfos = new String[listinfos.size()];
        int m = 0;
        for (Tb_inaccount tb_inaccount : listinfos) {

            strInfos[m] = tb_inaccount.getid() + "|" + tb_inaccount.getType() + " " + String.valueOf(tb_inaccount.getMoney()) + "元     "
                    + tb_inaccount.getTime();
            m++;
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strInfos);
        lvinfo.setAdapter(arrayAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showInfo(R.id.btnininfo);
    }
}
