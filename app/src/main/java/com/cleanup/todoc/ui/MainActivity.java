package com.cleanup.todoc.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cleanup.todoc.AppST;
import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener {
   //обьявления метода масива с типом данных проэкт, масив направляет на к методу в проэкте который создает масив с проэктами
    private final Project[] allProjects = Project.getAllProjects();

    @NonNull
    private  List<Task> tasksList = new ArrayList<>();

    TasksAdapter adapter = new TasksAdapter(tasksList, this);


    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;


    @Nullable
    public AlertDialog dialog = null;

    @Nullable
    private EditText dialogEditText = null;

    @Nullable
    private Spinner dialogSpinner = null;

    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView listTasks;

    // Suppress warning is safe because variable is initialized in onCreate
    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;

    private MainViewModel mvm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);

        listTasks = findViewById(R.id.list_tasks);
        lblNoTasks = findViewById(R.id.lbl_no_task);

        listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
listTasks.setAdapter(adapter);
       mvm = ViewModelProviders.of(this).get(MainViewModel.class);
       mvm.getLVD().observe(this, new Observer<List<Task>>() {
           @Override
           public void onChanged(List<Task> tasks) {

tasksList = tasks;
           updateTasks();
           }
       });

        findViewById(R.id.fab_add_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTaskDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            sortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            sortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            sortMethod = SortMethod.RECENT_FIRST;
        }

        updateTasks();

        return super.onOptionsItemSelected(item);
    }
//метод по удаленью таска из адаптера, происходит через интерфейс который имлементирует мейн и который был обьявлен в адаптере
    @Override
    public void onDeleteTask(Task task) {

mvm.delete(task);
        updateTasks();
    }

    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {


                Task task = new Task();
                task.projectId =  taskProject.getId();
                task.name = taskName;
                task.creationTimestamp = new Date().getTime();
mvm.insert(task);


updateTasks();
                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else{
                dialogInterface.dismiss();
            }
        }
        // If dialog is aloready closed
        else {
            dialogInterface.dismiss();
        }
    }

    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();

        dialog.show();

        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner();
    }

    private void addTask(@NonNull Task task) {
        tasksList.add(task);
        updateTasks();
    }
//метод по апдейту тасков, если список пуст, то ставит ту картинку, если список содержит элементы то мы видем ресайклер
    private void updateTasks() {
        //поставить картинку тасков нету
        if (tasksList.size() == 0) {
            lblNoTasks.setVisibility(View.VISIBLE);
            listTasks.setVisibility(View.GONE);
        }

        //поставить ресайклер
        else {
            lblNoTasks.setVisibility(View.GONE);
            listTasks.setVisibility(View.VISIBLE);
            //сортировка тасков в ресайклкре, с помошью енама и методов из самого клсса таск
            switch (sortMethod) {
                case ALPHABETICAL:
                    Collections.sort(tasksList, new Task.TaskAZComparator());
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(tasksList, new Task.TaskZAComparator());
                    break;
                case RECENT_FIRST:
                    Collections.sort(tasksList, new Task.TaskRecentComparator());
                    break;
                case OLD_FIRST:
                    Collections.sort(tasksList, new Task.TaskOldComparator());
                    break;

            }
            //вызов у адаптера метода notifyDataSetChanged();
            adapter.updateTasks(tasksList);
        }
    }

    /**
     * Returns the dialog allowing the user to create a new task.
     */
    //создание диалога добавки таска
    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogEditText = null;
                dialogSpinner = null;
                dialog = null;
            }
        });

        dialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        onPositiveButtonClick(dialog);
                    }
                });
            }
        });

        return dialog;
    }
//адаптер для спинера который использует масив из класса прожект обльявленый в самом вкрху мейна
    private void populateDialogSpinner() {
        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

    //енам для сортировки
    private enum SortMethod {

        ALPHABETICAL,

        ALPHABETICAL_INVERTED,

        RECENT_FIRST,

        OLD_FIRST,

        NONE
    }
}
