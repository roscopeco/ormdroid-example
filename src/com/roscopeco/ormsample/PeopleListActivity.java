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
import com.roscopeco.ormsample.model.Person;

public class PeopleListActivity extends ListActivity {
  private ArrayAdapter<Person> mAdapter;
  
  private Person getSelectedPerson(int position) {
    return mAdapter.getItem(position);
  }

  private void refresh() {
    mAdapter.clear();    
    for (Person p : Entity.query(Person.class).executeMulti()) {
      mAdapter.add(p);
    }
    mAdapter.notifyDataSetChanged();
  }
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1);
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
    super.onCreateOptionsMenu(menu);
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
    Intent i = new Intent(this, PersonDetailActivity.class);
    i.putExtra("PERSON_ID", getSelectedPerson(position).id);
    startActivity(i);
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent i = null;
    
    switch (item.getItemId()) {
    case R.id.menu_departments:
      i = new Intent(this, DepartmentsListActivity.class);
      break;
    case R.id.menu_add:
      i = new Intent(this, PersonEditActivity.class);
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
      i = new Intent(this, PersonEditActivity.class);      
      i.putExtra("PERSON_ID", getSelectedPerson(info.position).id);      
      startActivity(i);
      return true;
    case R.id.context_menu_delete:
      Person p = getSelectedPerson(info.position);
      p.delete();
      refresh();
      return true;
    }
    
    return super.onContextItemSelected(item);
  }
}
