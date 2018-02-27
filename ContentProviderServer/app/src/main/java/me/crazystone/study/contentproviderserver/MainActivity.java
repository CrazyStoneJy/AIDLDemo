package me.crazystone.study.contentproviderserver;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button txt_query_user, txt_insert_user, txt_query_job, txt_insert_job;
    // content 协议
    // me.crazystone.study.contentproviderserver authority
    // user 表名
    Uri uri_user = Uri.parse("content://me.crazystone.study.contentproviderserver/user");
    Uri uri_job = Uri.parse("content://me.crazystone.study.contentproviderserver/job");
    TextView txt_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_query_user = findViewById(R.id.txt_query_user);
        txt_insert_user = findViewById(R.id.txt_insert_user);
        txt_query_job = findViewById(R.id.txt_query_job);
        txt_insert_job = findViewById(R.id.txt_insert_job);
        txt_content = findViewById(R.id.txt_content);

        ContentResolver resolver = getContentResolver();
        txt_query_job.setOnClickListener(v -> {
            Cursor cursor = resolver.query(uri_job, new String[]{"job"}, null, null, null);
            if (cursor != null) {
                StringBuilder sb = new StringBuilder();
                while (cursor.moveToNext()) {
                    sb.append(cursor.getString(0)).append(",");
                }
                txt_content.setText(sb);
                cursor.close();
            }
        });
        txt_insert_job.setOnClickListener(v -> {
            ContentValues values = new ContentValues();
            values.put("job", "java");
            resolver.insert(uri_job, values);
        });
        txt_query_user.setOnClickListener(v -> {
            Cursor cursor = resolver.query(uri_user, new String[]{"name"}, null, null, null);
            if (cursor != null) {
                StringBuilder sb = new StringBuilder();
                while (cursor.moveToNext()) {
                    sb.append(cursor.getString(0)).append(",");
                }
                txt_content.setText(sb);
                cursor.close();
            }
        });
        txt_insert_user.setOnClickListener(v -> {
            ContentValues values = new ContentValues();
            values.put("name", "lisi");
            resolver.insert(uri_user, values);
        });


    }
}
