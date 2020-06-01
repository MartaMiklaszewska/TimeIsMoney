package com.example.timeismoney;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.Timestamp;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, WorkRecyclerAdapter.WorkListener {






    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    WorkRecyclerAdapter workRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

    }

    private void showAlertDialog() {

        View mView = getLayoutInflater()
                .inflate(R.layout.dialog_add_note, null);
        final EditText descryption = (EditText) mView.findViewById(R.id.descryptionEditText);
        final EditText minutes = (EditText) mView.findViewById(R.id.minutesEditText);

        new AlertDialog.Builder(this)
                .setTitle("Add your work")
                .setView(mView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: " + descryption.getText() + minutes.getText());
                        addWork(descryption.getText().toString(), Integer.parseInt(minutes.getText().toString()));
                    }
                })

                .setNegativeButton("Cancel", null)
                .show();
    }

    private void addWork(String text, int minutes) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Work work = new Work(text, minutes,false, new Timestamp(new Date()), userId);
        FirebaseFirestore.getInstance()
                .collection("work")
                .add(work)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Note added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_logout:
                AuthUI.getInstance().signOut(this);
                return true;
            case R.id.action_profile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                return true;
            case R.id.action_timer:
               startActivity(new Intent(MainActivity.this, StatsActivity.class));
               return true;
            case R.id.action_calendar:
                setContentView(R.layout.calculator);
                return true;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
        if (workRecyclerAdapter != null) {
            workRecyclerAdapter.stopListening();
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if(firebaseAuth.getCurrentUser() == null)  {
            startLoginActivity();
            return;
        }
        initRecyclerView(firebaseAuth.getCurrentUser());

    }
    private void initRecyclerView(FirebaseUser user) {
        Query query = FirebaseFirestore.getInstance()
                .collection("work")
                .whereEqualTo("userId", user.getUid())
                .orderBy("completed", Query.Direction.ASCENDING)
                .orderBy("createdDate", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Work> options = new FirestoreRecyclerOptions.Builder<Work>()
                .setQuery(query, Work.class)
                .build();
        workRecyclerAdapter = new WorkRecyclerAdapter(options, this);
        recyclerView.setAdapter(workRecyclerAdapter);
        workRecyclerAdapter.startListening();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction == ItemTouchHelper.LEFT) {
                Toast.makeText(MainActivity.this,"Deleting", Toast.LENGTH_SHORT).show();
                WorkRecyclerAdapter.WorkViewHolder workViewHolder = (WorkRecyclerAdapter.WorkViewHolder) viewHolder;
                workViewHolder.deleteItem();
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark))
                    .addActionIcon(R.drawable.ic_delete_sweep_black_24dp)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    @Override
    public void handleCheck(boolean isChecked, DocumentSnapshot snapshot) {
        Log.d(TAG, "handleCheckChanged " + isChecked);
        snapshot.getReference().update("completed", isChecked);
    }

    @Override
    public void handleEditNote(final DocumentSnapshot snapshot) {
        final Work work = snapshot.toObject(Work.class);
        View mView = getLayoutInflater()
                .inflate(R.layout.dialog_add_note, null);
        final EditText description = (EditText) mView.findViewById(R.id.descryptionEditText);
        final EditText minutes = (EditText) mView.findViewById(R.id.minutesEditText);
        description.setText(work.getText().toString());
        description.setSelection(work.getText().length());
        /*EditText editText = new EditText(this);*/
        new AlertDialog.Builder(this)
                .setTitle("Edit")
                .setView(mView)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newText = description.getText().toString();
                        int min = Integer.parseInt(minutes.getText().toString());
                        work.setText(newText);
                        work.setMinutes(min);
                        snapshot.getReference().set(work);


                        /*(description.getText().toString(), Integer.parseInt(minutes.getText().toString()));*/

                    }
                })
                .setNegativeButton("Exit", null)
                .show();

    }

    @Override
    public void handleDeleteNote(DocumentSnapshot snapshot) {
        final DocumentReference documentReference = snapshot.getReference();
        final Work work = snapshot.toObject(Work.class);
        documentReference.delete()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: Item deleted");
                }
            });
        Snackbar.make(recyclerView, "Item deleted", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        documentReference.set(work);
                    }
                })
                .show();
    }


}

