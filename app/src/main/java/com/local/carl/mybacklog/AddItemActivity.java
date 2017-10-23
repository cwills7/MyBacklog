package com.local.carl.mybacklog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.local.carl.mybacklog.db.BacklogDb;

/**
 * Created by carlr on 10/22/2017.
 */

public class AddItemActivity extends Activity {

    private EditText itemNameInput;
    private EditText itemDescInput;
    private EditText ratingInput;
    private AutoCompleteTextView categoryNameInput;
    private NumberPicker priorityInput;
    private CheckBox ownedInput;
    private Button submitInput;

    BacklogDb db;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.add_item_activity);

        db = new BacklogDb(this);

        itemNameInput = (EditText) findViewById(R.id.edit_item_name);
        itemDescInput = (EditText) findViewById(R.id.edit_item_desc);
        ratingInput = (EditText) findViewById(R.id.rating_input);
        categoryNameInput = (AutoCompleteTextView) findViewById(R.id.category_name);
        priorityInput = (NumberPicker) findViewById(R.id.priority);
        ownedInput = (CheckBox) findViewById(R.id.owned_checkbox);
        submitInput = (Button) findViewById(R.id.submit_button);

        priorityInput.setMinValue(0);
        priorityInput.setMaxValue(10);

        submitInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = new Item();
                item.setName(itemNameInput.getText().toString());
                item.setDesc(itemDescInput.getText().toString());
                item.setRating(Double.parseDouble(ratingInput.getText().toString()));
                //item.setCategoryName(categoryNameInput.get);
                item.setOwn(ownedInput.isChecked());
                db.insertItem(item);
            }
        });



    }
}
