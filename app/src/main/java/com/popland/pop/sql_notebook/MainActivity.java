package com.popland.pop.sql_notebook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class
MainActivity extends AppCompatActivity {
    Button btnTaoBang, btnHuyDB, btnThem, btnXoaDL, btnXem, btnCapNhat;
    EditText edtViDu, edtTu, edtNghia;
    String ms = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Activity2.class);
                startActivity(i);
            }
        });
        btnTaoBang = (Button) findViewById(R.id.BTNtaobang);
        btnHuyDB = (Button) findViewById(R.id.BTNhuydb);
        btnThem = (Button) findViewById(R.id.BTNthem);
        btnXoaDL = (Button) findViewById(R.id.BTNxoadl);
        btnXem = (Button) findViewById(R.id.BTNxem);
        btnCapNhat = (Button) findViewById(R.id.BTNcapnhat);
        edtViDu = (EditText) findViewById(R.id.EDTvidu);
        edtTu = (EditText) findViewById(R.id.EDTtu);
        edtNghia = (EditText) findViewById(R.id.EDTnghia);
        btnTaoBang.setOnClickListener(new MyEvent());
        btnHuyDB.setOnClickListener(new MyEvent());
        btnThem.setOnClickListener(new MyEvent());
        btnXoaDL.setOnClickListener(new MyEvent());
        btnCapNhat.setOnClickListener(new MyEvent());
        btnXem.setOnClickListener(new MyEvent());
    }
    SQLiteDatabase database = null;
    public void taoDB(){
        database = openOrCreateDatabase("personalDict.db",MODE_PRIVATE,null);
    }
    public void taoBang(){
        String taobangVoca = "CREATE TABLE Voca (TU text primary key,NGHIA text,VIDU text)";
        database.execSQL(taobangVoca);
    }
    public void tao(){
        taoDB();//phải tạo database trước bảng
        taoBang();
        btnTaoBang.setEnabled(false);
        btnHuyDB.setEnabled(true);
        btnXem.setEnabled(true);
        btnCapNhat.setEnabled(true);
        btnThem.setEnabled(true);
        btnXoaDL.setEnabled(true);
    }
    public void huyDB(){
        if(deleteDatabase("personalDict.db"))
            ms = "DB deleted true";
        else
            ms = "false to delete";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
        btnTaoBang.setEnabled(true);
        btnHuyDB.setEnabled(false);
        btnXem.setEnabled(false);
        btnCapNhat.setEnabled(false);
        btnThem.setEnabled(false);
        btnXoaDL.setEnabled(false);
    }

    public void them(){
        ContentValues v = new ContentValues();
        v.put("TU",edtTu.getText().toString());
        v.put("NGHIA",edtNghia.getText().toString());
        v.put("VIDU",edtViDu.getText().toString());
        if(database.insert("Voca",null,v)==-1)// hàm trả về int -1/1, thêm thành công hay không
            ms = "fail to insert -1";
        else
            ms = "insert works # -1";
        Toast.makeText(this, ms, Toast.LENGTH_LONG).show();
        edtTu.setText("");
        edtNghia.setText("");
        edtViDu.setText("");
    }
    public void xoaData(){//chỉ xóa dữ liệu trên bảng
        String tu = edtTu.getText().toString();
        if(database.delete("Voca", "TU=?", new String[]{tu})==0)// trả về số lượng dòng bị xóa
            ms = "0 rows deleted";
        else
            ms = "rows deleted";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
    }
    public void capnhat(){//dữ liệu phải tồn tại nó mới dc cập nhật
        String tu = edtTu.getText().toString();
        String new_nghia = edtNghia.getText().toString();
        ContentValues v = new ContentValues();
        v.put("NGHIA",new_nghia);
        if(database.update("Voca", v, "TU=?", new String[]{tu})== 0)//trả về số dòng dc cập nhật
            ms = "0 rows updated";
        else
            ms = " row updated";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
    }
    public void truyvanXem(){
        String data = "";
        Cursor c = database.query(true,"Voca",null,null,null,null,null,null,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            data += c.getString(0)+"  "+c.getString(1)+"  "+c.getString(2);//thứ tự cột dc tính từ 0
            data += "\n";
            c.moveToNext();
        }
        Toast.makeText(this,data,Toast.LENGTH_LONG).show();
        c.close();
    }
    class MyEvent implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            switch(arg0.getId()){
                case R.id.BTNtaobang:
                    tao(); break;
                case R.id.BTNthem:
                    them();
                    break;
                case R.id.BTNxoadl:
                    xoaData(); break;
                case R.id.BTNcapnhat:
                    capnhat(); break;
                case R.id.BTNhuydb:
                    huyDB(); break;
                case R.id.BTNxem:
                    truyvanXem();
                    break;
            }
        }
    }
    }







