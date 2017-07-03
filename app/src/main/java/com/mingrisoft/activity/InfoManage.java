package com.mingrisoft.activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mingrisoft.dao.InaccountDAO;
import com.mingrisoft.dao.OutaccountDAO;
import com.mingrisoft.model.Tb_inaccount;
import com.mingrisoft.model.Tb_outaccount;

public class InfoManage extends Activity {
    private static final int DATE_DIALOG_ID = 0;// 创建日期对话框常量
    private TextView tvtitle, textView;// 创建两个TextView对象
    private EditText txtMoney, txtTime, txtHA, txtMark;// 创建4个EditText对象
    private Spinner spType;// 创建Spinner对象
    private Button btnEdit, btnDel;// 创建两个Button对象
    private String[] strInfos;// 定义字符串数组
    private String strid, strType;// 定义两个字符串变量，分别用来记录信息编号和管理类型

    private int mYear;// 年
    private int mMonth;// 月
    private int mDay;// 日

    private OutaccountDAO outaccountDAO = new OutaccountDAO(InfoManage.this);// 创建OutaccountDAO对象
    private InaccountDAO inaccountDAO = new InaccountDAO(InfoManage.this);// 创建InaccountDAO对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomanage);// 设置布局文件
        tvtitle = (TextView) findViewById(R.id.inouttitle);// 获取标题标签对象
        textView = (TextView) findViewById(R.id.tvInOut);// 获取地点/付款方标签对象
        txtMoney = (EditText) findViewById(R.id.txtInOutMoney);// 获取金额文本框
        txtTime = (EditText) findViewById(R.id.txtInOutTime);// 获取时间文本框
        spType = (Spinner) findViewById(R.id.spInOutType);// 获取类别下拉列表
        txtHA = (EditText) findViewById(R.id.txtInOut);// 获取地点/付款方文本框
        txtMark = (EditText) findViewById(R.id.txtInOutMark);// 获取备注文本框
        btnEdit = (Button) findViewById(R.id.btnInOutEdit);// 获取修改按钮
        btnDel = (Button) findViewById(R.id.btnInOutDelete);// 获取删除按钮

        Intent intent = getIntent();// 创建Intent对象
        Bundle bundle = intent.getExtras();// 获取传入的数据，并使用Bundle记录
        strInfos = bundle.getStringArray(Showinfo.FLAG);// 获取Bundle中记录的信息
        strid = strInfos[0];// 记录id
        strType = strInfos[1];// 记录类型
        if (strType.equals("btnoutinfo")) {// 如果类型是btnoutinfo

            tvtitle.setText("支出管理");// 设置标题为“支出管理”
            textView.setText("地  点：");
            // 根据编号查找支出信息，并存储到Tb_outaccount对象中
            Tb_outaccount tb_outaccount = outaccountDAO.find(Integer.parseInt(strid));
            txtMoney.setText(String.valueOf(tb_outaccount.getMoney()));
            txtTime.setText(tb_outaccount.getTime());
            spType.setPrompt(tb_outaccount.getType());
            txtHA.setText(tb_outaccount.getAddress());
            txtMark.setText(tb_outaccount.getMark());
        } else if (strType.equals("btnininfo")) {

            tvtitle.setText("收入管理");
            textView.setText("付款方：");

            Tb_inaccount tb_inaccount = inaccountDAO.find(Integer.parseInt(strid));
            txtMoney.setText(String.valueOf(tb_inaccount.getMoney()));
            txtTime.setText(tb_inaccount.getTime());
            spType.setPrompt(tb_inaccount.getType());
            txtHA.setText(tb_inaccount.getHandler());
            txtMark.setText(tb_inaccount.getMark());
        }

        txtTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        btnEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (strType.equals("btnoutinfo")) {

                    Tb_outaccount tb_outaccount = new Tb_outaccount();
                    tb_outaccount.setid(Integer.parseInt(strid));
                    tb_outaccount.setMoney(Double.parseDouble(txtMoney.getText().toString()));
                    tb_outaccount.setTime(txtTime.getText().toString());
                    tb_outaccount.setType(spType.getSelectedItem().toString());
                    tb_outaccount.setAddress(txtHA.getText().toString());
                    tb_outaccount.setMark(txtMark.getText().toString());
                    outaccountDAO.update(tb_outaccount);
                } else if (strType.equals("btnininfo")) {

                    Tb_inaccount tb_inaccount = new Tb_inaccount();
                    tb_inaccount.setid(Integer.parseInt(strid));
                    tb_inaccount.setMoney(Double.parseDouble(txtMoney.getText().toString()));
                    tb_inaccount.setTime(txtTime.getText().toString());
                    tb_inaccount.setType(spType.getSelectedItem().toString());
                    tb_inaccount.setHandler(txtHA.getText().toString());
                    tb_inaccount.setMark(txtMark.getText().toString());
                    inaccountDAO.update(tb_inaccount);
                }

                Toast.makeText(InfoManage.this, "〖数据〗修改成功！", Toast.LENGTH_SHORT).show();
            }
        });

        btnDel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (strType.equals("btnoutinfo")) {

                    outaccountDAO.detele(Integer.parseInt(strid));
                } else if (strType.equals("btnininfo")) {

                    inaccountDAO.detele(Integer.parseInt(strid));
                }
                Toast.makeText(InfoManage.this, "〖数据〗删除成功！", Toast.LENGTH_SHORT).show();
            }
        });

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    private void updateDisplay() {
        txtTime.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
    }
}
