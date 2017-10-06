package com.popland.pop.sql_notebook;

import android.content.ContentValues;
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
Activity2 extends AppCompatActivity {
String ms = "";
    Button btnTaoDB,btnHuyDB,btnTaobangL,btnTaobangSV,btnThemL,btnThemSV
            ,btnXoaL,btnXoaSV,btnCapnhatL,btnCapnhatSV,btnXem;
    EditText edtXem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btnTaoDB = (Button)findViewById(R.id.BTNtaoDB);
        btnHuyDB = (Button)findViewById(R.id.BTNhuyDB);
        btnTaobangL = (Button)findViewById(R.id.BTNtaobangL);
        btnTaobangSV= (Button)findViewById(R.id.BTNtaobangSV);
        btnThemL = (Button)findViewById(R.id.BTNthemL);
        btnThemSV = (Button)findViewById(R.id.BTNthemS);
        btnXoaL = (Button)findViewById(R.id.BTNxoaL);
        btnXoaSV = (Button)findViewById(R.id.BTNxoaS);
        btnCapnhatL = (Button)findViewById(R.id.BTNcapnhatL);
        btnCapnhatSV = (Button)findViewById(R.id.BTNcapnhatS);
        btnXem = (Button)findViewById(R.id.BTNxem);
        edtXem = (EditText)findViewById(R.id.EDTxem);

        btnTaoDB.setOnClickListener(new MyEvent());
        btnHuyDB.setOnClickListener(new MyEvent());
        btnTaobangL.setOnClickListener(new MyEvent());
        btnTaobangSV.setOnClickListener(new MyEvent());
        btnThemL.setOnClickListener(new MyEvent());
        btnThemSV.setOnClickListener(new MyEvent());
        btnXoaL.setOnClickListener(new MyEvent());
        btnXoaSV.setOnClickListener(new MyEvent());
        btnCapnhatL.setOnClickListener(new MyEvent());
        btnCapnhatSV.setOnClickListener(new MyEvent());
        btnXem.setOnClickListener(new MyEvent());
    }
    SQLiteDatabase database = null;
    public void taoDB(){
        database = openOrCreateDatabase("Quanli.db",MODE_PRIVATE,null);
        ms = "database created";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
    }
    public void huyDB(){
        if(deleteDatabase("Quanli.db")== true)
            ms = "database deleted";
        else
            ms = "database not deleted";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
    }
    public void taoBangLop(){
        String bangLop = "CREATE TABLE tbLop (malop Text primary key, tenlop Text, siso integer)";
        database.execSQL(bangLop);
        ms = "tbLop created";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
    }
    public void taoBangSV(){
        String bangSV = "CREATE TABLE tbSV (masv Text primary key, tensv Text,";
               bangSV += "malop Text NOT NULL CONSTRAINT malop REFERENCES tbLop(malop) ON DELETE CASCADE)";
        database.execSQL(bangSV);
        ms = "tbSV created";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
    }
    public void themL(){
        ContentValues v = new ContentValues();
        v.put("malop","ML001");
        v.put("tenlop","12A12");
        v.put("siso","45");
        if(database.insert("tbLop",null,v)==-1)
            ms = "row not inserted";
        else
            ms = "row inserted";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
    }
    public void themSV(){
        ContentValues v = new ContentValues();
        v.put("masv", "SV001");
        v.put("tensv","Thich Hoc");
        v.put("malop", "ML002");
        if(database.insert("tbSV",null,v)==-1)
            ms  = "row not inserted";
        else
            ms = "row inserted";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
    }
    public void xoaLop(){
        if(database.delete("tbLop", null, null)==0)
            ms = "table Lop not deleted";
        else
            ms = "table Lop deleted";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
    }
    public void xoaSV(){
        if(database.delete("tbSV", "masv=?", new String[]{"SV001"})== 0)
            ms = " row not deleted";
        else
            ms = "row deleted";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
    }
    public void capnhatL(){
        ContentValues v = new ContentValues();
        v.put("siso","60");
        if(database.update("tbLop", v, "malop=?", new String[]{"ML001"})==0)
            ms = "row not updated";
        else
            ms = "row updated";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
    }
    public void capnhatSV(){
        ContentValues v = new ContentValues();
        v.put("malop","ML003");
        if(database.update("tbSV", v, "masv=?", new String[]{"SV001"})==0)
            ms = "row not updated";
        else
            ms = "row updated";
        Toast.makeText(this,ms,Toast.LENGTH_LONG).show();
    }
    public void xem(){
        String tenbang = edtXem.getText().toString();
        Cursor c = database.query(true,tenbang,null,null,null,null,null,null,null);
        c.moveToFirst();
        String data = "";
        while(c.isAfterLast()== false){
            data = c.getString(0)+"  "+c.getString(1)+"  "+c.getString(2);
            data += "\n";
            c.moveToNext();
        }
        Toast.makeText(this,data,Toast.LENGTH_LONG).show();
        c.close();
    }
    class MyEvent implements View.OnClickListener{
        @Override
        public void onClick(View arg0) {
            switch(arg0.getId()){
                case R.id.BTNtaoDB:
                    taoDB(); break;
                case R.id.BTNhuyDB:
                    huyDB(); break;
                case R.id.BTNtaobangL:
                    taoBangLop(); break;
                case R.id.BTNtaobangSV:
                    taoBangSV(); break;
                case R.id.BTNthemL:
                    themL(); break;
                case R.id.BTNthemS:
                    themSV(); break;
                case R.id.BTNxoaL:
                    xoaLop(); break;
                case R.id.BTNxoaS:
                    xoaSV(); break;
                case R.id.BTNcapnhatL:
                    capnhatL(); break;
                case R.id.BTNcapnhatS:
                    capnhatSV(); break;
                case R.id.BTNxem:
                    xem(); break;
            }
        }
    }




}
