package com.local.carl.mybacklog;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.local.carl.mybacklog.db.BacklogDb;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    //TODO:  Make items editable on long press
    //Todo: Mark items as finished.
    //Todo:

    String currentCategory;
    BacklogDb db;
    Button addItem;
    RecyclerView itemList;
    RVAdapter rvAdapter;
    List<Item> viewList;
    NavigationView navView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    int mSelectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new BacklogDb(this);

        viewList = new ArrayList<>();

        viewList = db.getTopTenPriority();


        itemList = (RecyclerView) findViewById(R.id.item_list);
       // initializeData();
        initializeAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        itemList.setLayoutManager(llm);
        itemList.setOnCreateContextMenuListener(this);
        registerForContextMenu(itemList);



//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        setupViewPager(viewPager);
//
//        tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);



        navView = (NavigationView) findViewById(R.id.nav_view);

        Menu m = navView.getMenu();
        SubMenu topChannelMenu = m.addSubMenu("Categories");
        List<String> categoryList = db.getAllCategories();
        topChannelMenu.add("None");
        for (int i = 0; i < categoryList.size(); i++){
            topChannelMenu.add(categoryList.get(i));
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        addItem = (Button) findViewById(R.id.add_item_button);

        addItem.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddItemActivity.class);
                startActivityForResult(intent, 3);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), AddItemActivity.class);
                view.getContext().startActivity(intent);

            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UpcomingFragment(), "Upcoming");
        adapter.addFragment(new FinishedFragment(), "Finished");
        viewPager.setAdapter(adapter);
    }

    private void initializeAdapter() {
        rvAdapter = new RVAdapter(this.getApplicationContext(), viewList);
        itemList.setAdapter(rvAdapter);

    }

    private void initializeData() {

        Item it = new Item();
        it.setName("Test Name");
        it.setOwn(true);
        it.setCategoryName("Category1");
        it.setDesc("My Description of what this is");
        it.setPriority(7);
        db.insertItem(it);
        viewList.add(it);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        updateList();
    }

    private void updateList() {
        if (currentCategory == null){
            viewList = db.getTopTenPriority();
        } else{
            viewList = db.getAllFromCategory(currentCategory);
        }
        rvAdapter.setItems(viewList);
        rvAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.item_list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(viewList.get(info.position).getName());
            String[] menuItems = getResources().getStringArray(R.array.contextArray);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mSelectedId=menuItem.getItemId();
        String itemName = menuItem.getTitle().toString();

        itemSelection(mSelectedId, itemName);
        return true;
    }

    private void itemSelection(int mSelectedId, String itemName) {

        currentCategory = itemName;

        if (currentCategory.equals("None"))
        {
            currentCategory = null;
        }
        updateList();
        drawerLayout.closeDrawer(GravityCompat.START);

    }

}
