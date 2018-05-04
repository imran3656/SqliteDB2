package com.example.root.sqlitedb2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etSearch,etName,etEmail,etMobile;
    Button btnSave,btnDelete,btnUpdate,btnSelect,btnSelectAll;
    String search,name,email,mobile;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteDatabase=openOrCreateDatabase("Emp",Context.MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS con(ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(255),EMAIL VARCHAR(255),MOBILE VARCHAR(255));");

        etSearch=(EditText)findViewById(R.id.etSearch);
        etName=(EditText)findViewById(R.id.etName);
        etEmail=(EditText)findViewById(R.id.etMail);
        etMobile=(EditText)findViewById(R.id.etMobile);

        btnSave=(Button)findViewById(R.id.btnSave);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnSelect=(Button)findViewById(R.id.btnSelect);
        btnSelectAll=(Button)findViewById(R.id.btnSelectAll);
        btnUpdate=(Button)findViewById(R.id.btnUpdate);

        btnSave.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSelectAll.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnSave)
        {
            name=etName.getText().toString().trim();
            email=etEmail.getText().toString().trim();
            mobile=etMobile.getText().toString().trim();

            if(name.equals("")||email.equals("")||mobile.equals(""))
            {
                Toast.makeText(this,"all fields are compulsory",Toast.LENGTH_LONG).show();
            }
            else
            {
                sqLiteDatabase.execSQL("INSERT INTO con(name,email,mobile)VALUES('"+name+"','"+email+"','"+mobile+"')");
                Toast.makeText(this,"Record Saved....",Toast.LENGTH_LONG).show();

            }
        }
        else if(v.getId()==R.id.btnSelectAll)
        {
            Cursor c=sqLiteDatabase.rawQuery("SELECT *FROM con",null);
            if(c.getCount()==0)
            {
                Toast.makeText(this,"database is empty",Toast.LENGTH_LONG).show();
                return;
            }

            StringBuffer buffer=new StringBuffer();
            while (c.moveToFirst())
            {
                buffer.append("Emp Name:"+c.getString(1)+"\n");
                buffer.append("Emp Mail:"+c.getString(2)+"\n");
                buffer.append("Emp Mobile:"+c.getString(3)+"\n");
            }
            Toast.makeText(this,buffer.toString(),Toast.LENGTH_LONG).show();
        }
        else if(v.getId()==R.id.btnSelect)
        {
            search=etSearch.getText().toString().trim();
            if(search.equals(""))
            {
                Toast.makeText(this,"enter name",Toast.LENGTH_LONG).show();
                return;
            }
            Cursor cursor=sqLiteDatabase.rawQuery("SELECT *FROM con WHERE NAME='"+search+"'",null);
            if(cursor.moveToFirst())
            {
                etName.setText(cursor.getString(1));
                etEmail.setText(cursor.getString(2));
                etMobile.setText(cursor.getString(3));
            }
            else
            {
                Toast.makeText(this,"Recoreds not found..",Toast.LENGTH_LONG).show();
            }
        }
        else if(v.getId()==R.id.btnUpdate)
        {
            search=etSearch.getText().toString().trim();
            name=etName.getText().toString().trim();
            email=etEmail.getText().toString().trim();
            mobile=etMobile.getText().toString().trim();

            Cursor cursorupdate=sqLiteDatabase.rawQuery("SELECT *FROM con WHERE NAME='"+search+"'",null);
            if(cursorupdate.moveToFirst())
            {
                if(name.equals("")||email.equals("")||mobile.equals(""))
                {
                    Toast.makeText(this,"all fields are compulsory",Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    sqLiteDatabase.execSQL("UPDATE FROM con SET NAME='"+name+"','"+email+"','"+mobile+"'");
                    Toast.makeText(this,"Recoreds are updated",Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(this,"record  not found",Toast.LENGTH_LONG).show();
            }
        }
        else if(v.getId()==R.id.btnDelete)
        {
            search=etSearch.getText().toString().trim();

            Cursor cursordel=sqLiteDatabase.rawQuery("DELETE *FROM con WHERE NAME='"+name+"'",null);

            if (cursordel.moveToFirst())
            {
                sqLiteDatabase.execSQL("DELETE FROM con WHERE NAME='"+search+"'");
                Toast.makeText(this,"record deleted",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,"Recored not deleted..",Toast.LENGTH_LONG).show();
            }
        }
    }
}
