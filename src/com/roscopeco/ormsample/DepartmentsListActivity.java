package com.roscopeco.ormsample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormsample.model.Department;

public class DepartmentsListActivity extends ListActivity {
  private ArrayAdapter<Department> mAdapter;
  
  private Department getSelectedDepartment(int position) {
    return mAdapter.getItem(position);
  }

  private void refresh() {
    mAdapter.clear();    
    for (Department d : Entity.query(Department.class).executeMulti()) {
      mAdapter.add(d);
    }
    mAdapter.notifyDataSetChanged();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);    
    mAdapter = new ArrayAdapter<Department>(this, android.R.layout.simple_list_item_1);
    setListAdapter(mAdapter);    
    registerForContextMenu(getListView());
  }
  
  @Override
  protected void onStart() {
    super.onStart();
    refresh();    
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }
  
  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    getMenuInflater().inflate(R.menu.listview_context, menu);    
  }

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    Intent i = new Intent(this, DepartmentDetailActivity.class);
    i.putExtra("DEPARTMENT_ID", getSelectedDepartment(position).id);
    startActivity(i);
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent i = null;
    
    switch (item.getItemId()) {
    case R.id.menu_people:
      i = new Intent(this, PeopleListActivity.class);
      break;
    case R.id.menu_add:
      i = new Intent(this, DepartmentEditActivity.class);
      break;
    }
    
    if (i != null) {
      startActivity(i);
      return true;
    } else {
      return super.onOptionsItemSelected(item);      
    }
  }

  @Override
  public boolean onContextItemSelected(MenuItem item) {
    Intent i = null;
    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    
    switch (item.getItemId()) {
    case R.id.context_menu_edit:
      i = new Intent(this, DepartmentEditActivity.class);
      i.putExtra("DEPARTMENT_ID", getSelectedDepartment(info.position).id);
      startActivity(i);
      return true;
    case R.id.context_menu_delete:
      Department d = getSelectedDepartment(info.position);
      d.delete();
      refresh();
      return true;
    }
    
    return super.onContextItemSelected(item);
  }

}
