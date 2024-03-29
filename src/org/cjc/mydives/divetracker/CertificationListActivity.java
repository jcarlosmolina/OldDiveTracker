package org.cjc.mydives.divetracker;

import org.cjc.mydives.divetracker.db.CertificationConstants;
import org.cjc.mydives.divetracker.db.CertificationDbAdapter;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * @author JuanCarlos
 *
 */
public class CertificationListActivity extends ListActivity {

		private CertificationDbAdapter dbAdapter;
		private static final int ACTIVITY_CREATE = 0;
		private static final int ACTIVITY_EDIT = 1;
		private static final int DELETE_ID = Menu.FIRST + 1;
		private Cursor cursor;
		
		/** Called when the activity is first created */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.certification_list);
			this.getListView().setDividerHeight(2);
			
			dbAdapter = new CertificationDbAdapter(this);
			dbAdapter.open();
			
			fillData();
			registerForContextMenu(getListView());
		}
		
	    // Life cycle method callback implementation. Called when the activity is about to be destroyed
	    @Override
	    protected void onDestroy() {
	    	super.onDestroy();
	    	if (dbAdapter != null) {
	    		dbAdapter.close();
	    	}
	    }		
		
	    // Creation of the ListView context menu
	    @Override
	    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    	super.onCreateContextMenu(menu, v, menuInfo);
	    	menu.add(0, DELETE_ID, 0, R.string.certification_list_menu_delete);
	    }
	    
	    // Creation of items via clicking the Add button
	    public void onClick_button_add(){
	    	Intent createCertificationIntent = new Intent(this, CertificationDetailsActivity.class);
	    	startActivityForResult(createCertificationIntent, ACTIVITY_CREATE);
	    }
	    
	    // Edition of items via clicking on them (on the ListView)
	    
	    @Override
	    public void onListItemClick(ListView l, View v, int position, long id) {
	    	super.onListItemClick(l, v, position, id);
	    	Intent editIntent = new Intent(this, CertificationDetailsActivity.class);
	    	// Pass the Certification id to the activity
	    	editIntent.putExtra(CertificationConstants.FIELD_ROWID, id);
	    	// Activity will return a result if called with startActivityForResult
	    	startActivityForResult(editIntent, ACTIVITY_EDIT);
	    }
	    
	    // Process the result of calling the second (create or edit) activity
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	super.onActivityResult(requestCode, resultCode, data);
	    	fillData();
	    }
	    
	    
	    // Deletion of items via the ListView context menu
	    @Override
	    public boolean onContextItemSelected(MenuItem item) {
	    	switch(item.getItemId()) {
	    	case DELETE_ID:
	    		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
	    		dbAdapter.delete(menuInfo.id);
	    		fillData();
	    		return true;
	    	}
	    	return super.onContextItemSelected(item);
	    }
	    
		// Helper method to populate the ListView
		private void fillData(){
			cursor = dbAdapter.fetchAll();
			startManagingCursor(cursor);
			
			String[] from = new String[]{CertificationConstants.FIELD_TYPE, CertificationConstants.FIELD_ORGANIZATION, CertificationConstants.FIELD_DATE};
			int[] to = new int[]{R.id.certification_row_type, R.id.certification_row_organization, R.id.certification_row_date};
			
	    	// Create an array adapter and set it to display each row using the certification_row.xml layout
	    	SimpleCursorAdapter certificationCursorAdapter = new SimpleCursorAdapter(this, R.layout.certification_row, cursor, from, to);
	    	setListAdapter(certificationCursorAdapter);
		}
}
