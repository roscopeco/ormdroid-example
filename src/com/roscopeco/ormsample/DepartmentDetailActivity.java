package com.roscopeco.ormsample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormsample.model.Department;
import com.roscopeco.ormsample.model.Person;

import static com.roscopeco.ormdroid.Query.eql;

public class DepartmentDetailActivity extends Activity {
  private TextView mName;
  private ListView mPeople;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.department_detail);

    mName = (TextView)findViewById(R.id.detail_department_name);
    mPeople = (ListView)findViewById(R.id.detail_department_people);
    
    
    int id = getIntent().getIntExtra("DEPARTMENT_ID", -1);
    Department department = Entity.query(Department.class).where(eql("id", id)).execute();
    
    mName.setText(department.name);
    
    ArrayAdapter<Person> peopleAdapter = 
        new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1,
            department.people());
    mPeople.setAdapter(peopleAdapter);
  }
}
