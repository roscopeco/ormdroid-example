package com.roscopeco.ormsample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormsample.model.Person;

import static com.roscopeco.ormdroid.Query.eql;

public class PersonDetailActivity extends Activity {
  private TextView mName;
  private TextView mPhone;
  private TextView mDepartment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.person_detail);
    
    mName = (TextView)findViewById(R.id.detail_person_name);
    mPhone = (TextView)findViewById(R.id.detail_person_phone);
    mDepartment = (TextView)findViewById(R.id.detail_person_department);
    
    int id = getIntent().getIntExtra("PERSON_ID", -1);
    Person person = Entity.query(Person.class).where(eql("id", id)).execute();      
      
    mName.setText(person.name);
    mPhone.setText(person.telephone);
    
    if (person.department != null) {
      mDepartment.setText(person.department.name);
    } else {
      mDepartment.setText("<none>");
    }
  }
}
