package com.roscopeco.ormsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormsample.model.Department;

import static com.roscopeco.ormdroid.Query.eql;

public class DepartmentEditActivity extends Activity {
  private EditText mName;
  private Button mSave;
  private Department mDepartment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.department_edit);
    
    mName = (EditText)findViewById(R.id.edit_department_name);
    mSave = (Button)findViewById(R.id.edit_department_save);

    int id = getIntent().getIntExtra("DEPARTMENT_ID", -1);
    if (id != -1) {
      mDepartment = Entity.query(Department.class).where(eql("id", id)).execute();      
    } else {
      mDepartment = new Department("");
    }
    
    mName.setText(mDepartment.name);

    mSave.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mDepartment.name = mName.getText().toString();
        mDepartment.save();
        finish();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }
}
