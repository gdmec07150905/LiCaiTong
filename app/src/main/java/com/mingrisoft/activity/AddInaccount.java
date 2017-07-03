package com.mingrisoft.activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mingrisoft.dao.InaccountDAO;
import com.mingrisoft.model.Tb_inaccount;

public class AddInaccount extends Activity {
    private static final int DATE_DIALOG_ID = 0;
    private EditText txtInMoney, txtInTime, txtInHandler, txtInMark;
    private Spinner spInType;
    private Button btnInSaveButton;
    private Button btnInCancelButton;

    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinaccount);
        txtInMoney = (EditText) findViewById(R.id.txtInMoney);
        txtInTime = (EditText) findViewById(R.id.txtInTime);
        txtInHandler = (EditText) findViewById(R.id.txtInHandler);
        txtInMark = (EditText) findViewById(R.id.txtInMark);
        spInType = (Spinner) findViewById(R.id.spInType);
        btnInSaveButton = (Button) findViewById(R.id.btnInSave);
        btnInCancelButton = (Button) findViewById(R.id.btnInCancel);

        txtInTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        btnInSaveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String strInMoney = txtInMoney.getText().toString();
                if (!strInMoney.isEmpty()) {
                    InaccountDAO inaccountDAO = new InaccountDAO(AddInaccount.this);
                    Tb_inaccount tb_inaccount = new Tb_inaccount(inaccountDAO.getMaxId() + 1, Double.parseDouble(strInMoney), txtInTime.getText()
                            .toString(), spInType.getSelectedItem().toString(), txtInHandler.getText().toString(), txtInMark.getText().toString());
                    inaccountDAO.add(tb_inaccount);

                    Toast.makeText(AddInaccount.this, "〖新增收入〗数据添加成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddInaccount.this, "请输入收入金额！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnInCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                txtInMoney.setText("");
                txtInMoney.setHint("0.00");
                txtInTime.setText("");
                txtInTime.setHint("2011-01-01");
                txtInHandler.setText("");
                txtInMark.setText("");
                spInType.setSelection(0);
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
        txtInTime.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
    }
}
