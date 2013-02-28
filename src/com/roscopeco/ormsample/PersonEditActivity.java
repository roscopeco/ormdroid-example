package com.roscopeco.ormsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormsample.model.Department;
import com.roscopeco.ormsample.model.Person;

import static com.roscopeco.ormdroid.Query.eql;

public class PersonEditActivity extends Activity {
  private Person mPerson;
  
  private EditText mName;
  private EditText mPhone;
  private Spinner mDepartment;
  private Button mSave;
  
  private ArrayAdapter<Department> mDepartmentAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.person_edit);
    
    mName = (EditText)findViewById(R.id.edit_person_name);
    mPhone = (EditText)findViewById(R.id.edit_person_phone);
    mDepartment = (Spinner)findViewById(R.id.edit_person_department);
    mSave = (Button)findViewById(R.id.edit_person_save);
    
    int id = getIntent().getIntExtra("PERSON_ID", -1);
    if (id != -1) {
      mPerson = Entity.query(Person.class).where(eql("id", id)).execute();      
    } else {
      mPerson = new Person("","");
    }
    
    mName.setText(mPerson.name);
    mPhone.setText(mPerson.telephone);
        
    mDepartmentAdapter = new ArrayAdapter<Department>(this, android.R.layout.simple_spinner_item,
        Entity.query(Department.class).executeMulti());
    mDepartmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mDepartment.setAdapter(mDepartmentAdapter);
    
    if (mPerson.department != null) {      
      mDepartment.setSelection(mDepartmentAdapter.getPosition(mPerson.department));
    }
    
    mSave.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mPerson.name = mName.getText().toString();
        mPerson.telephone = mPhone.getText().toString();
        mPerson.department = (Department)mDepartment.getSelectedItem();
        mPerson.save();
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
