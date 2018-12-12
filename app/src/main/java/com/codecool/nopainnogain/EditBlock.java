package com.codecool.nopainnogain;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.codecool.nopainnogain.adapters.EditBlockRecyclerViewAdapter;
import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.util.DragAndDropSwipeHelper;

public class EditBlock extends AppCompatActivity {

    private int REQUEST_CODE_REST_CREATE = 1;
    private int REQUEST_CODE_REST_EDIT = 2;
    private int REQUEST_CODE_EXERCISE_CREATE = 3;

    private TextView textView;
    private RecyclerView recyclerView;
    private EditBlockRecyclerViewAdapter adapter;
    private WorkoutBlock block;

    private Rest currentlyEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_block);

        block = WorkoutBlock.toWorkoutBlockObject(getIntent().getStringExtra("block"));

        textView = findViewById(R.id.editBlockPlaceholder);
        if(block.getComponents().size() == 0) {
            textView.setText("Click the dots in the corner to add exercises or rests");
        }else{
            textView.setVisibility(View.GONE);
        }

        adapter = new EditBlockRecyclerViewAdapter(this,block.getComponents(),this);

        recyclerView = findViewById(R.id.editBlockRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback dragAndDropSwipeHelper = new DragAndDropSwipeHelper(0, ItemTouchHelper.RIGHT,adapter);
        new ItemTouchHelper(dragAndDropSwipeHelper).attachToRecyclerView(recyclerView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_block_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addRest) {
            startAddNewRest();
        }else if(id == R.id.addExercise){
            startAddNewExercise();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_REST_CREATE){
                int newDuration = data.getIntExtra("newDuration",1000);
                System.out.println(newDuration);
                Rest newRest = new Rest(newDuration);
                block.addComponent(newRest);
                adapter.notifyDataSetChanged();
                /*recyclerView.swapAdapter(new EditBlockRecyclerViewAdapter(this,block.getComponents(),this),true);
                recyclerView.scrollBy(0,0);*/
                adapter.addComponentManually(newRest);
                textView.setVisibility(View.GONE);
            }else if(requestCode == REQUEST_CODE_REST_EDIT){
                int newDuration = data.getIntExtra("newDuration",1000);
                System.out.println(newDuration);
                currentlyEdited.setDurationInMilis(newDuration);
                adapter.notifyDataSetChanged();
            }

        }
    }

    public void startAddNewExercise(){
        Intent intent = new Intent(this,AddExercise.class);
        intent.putExtra("exerciseId",1L);
        intent.putExtra("exerciseReps",10);
        startActivityForResult(intent,REQUEST_CODE_EXERCISE_CREATE);
    }

    public void startEditRest(Rest rest) {
        Intent intent = new Intent(this,AddRest.class);
        intent.putExtra("initialDuration",rest.getDurationInMilis());
        currentlyEdited = rest;
        startActivityForResult(intent,REQUEST_CODE_REST_EDIT);
    }

    public void startAddNewRest(){
        Intent intent = new Intent(this,AddRest.class);
        intent.putExtra("initialDuration",1000);
        startActivityForResult(intent,REQUEST_CODE_REST_CREATE);
    }


    @Override
    public void finish() {
        Intent returnIntent = getIntent();
        returnIntent.putExtra("newBlock",WorkoutBlock.toJsonString(block));
        setResult(RESULT_OK,returnIntent);
        super.finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
