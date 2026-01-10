package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import exceptions.InvalidValuesException;
import jsonParser.annotations.JsonIgnore;
import ui.Manager;
import utils.FileUtils;
import utils.IDInitializer;
import utils.ThreadManager;

public class ProductLine implements Runnable {

    private static int nextId = 1;
    private int id;
    private int priority;
    private String name;
    private String status;
    @JsonIgnore
    private List<Task> completed;
    @JsonIgnore
    private List<Task> inprogress;
    @JsonIgnore
    private List<Task> inline;

    static {
        nextId = IDInitializer.getProductlinesGlobalID();
    }

    public ProductLine() {}

    public ProductLine(String name, String status, int priority) throws InvalidValuesException {
        if(priority < 1 || priority > 10) {
            throw new InvalidValuesException("Invalid values in class ProductLine!");
        }

        this.id = nextId++;
        this.priority = priority;
        this.name = name;
        this.status = status;
        this.completed = new ArrayList<>();
        this.inprogress = new ArrayList<>();
        this.inline = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!inline.isEmpty()) {
            Manager.isEdited = true;

            inprogress.add(inline.removeFirst());

            Task runningTask = getFirstAvailableInprogressTask();

            if (runningTask == null) {
                continue;
            }

            while (canProceedWith(runningTask) && !isTaskFinished(runningTask)) {
                Factory.makeProduct(runningTask.getProduct());
                runningTask.increaseReady();

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    FileUtils.log(e);
                }
            }

            if (runningTask.getCompletionPercentage() == 1) {
                completed.add(runningTask);
                inprogress.remove(runningTask);
            }
        }
        ThreadManager.assign();
    }

    public void cancelTask(Task task) {
        int index = inprogress.indexOf(task);
        if (index != -1) {
            inprogress.remove(index);
        } else {
            index = inline.indexOf(task);
            if (index != -1) {
                inline.remove(index);
            } else {
                throw new NoSuchElementException("Task not found in the specified product line.");
            }
        }
    }

    //GETTERS
    public int getLineId() {
        return this.id;
    }
    public static int getNextId() {
        return nextId;
    }

    public int getPriority() {
        return this.priority;
    }

    public String getLineName() {
        return this.name;
    }

    public String getLineStatus() {
        return this.status;
    }

    public List<Task> getCompletedTasks() {
        return this.completed;
    }

    public List<Task> getInprogressTasks() {
        return this.inprogress;
    }

    /**
     * both inprogress and inline tasks are returned
 *
     */
    public List<Task> get0PCTasks() {
        List<Task> tasks = new ArrayList<>();
        for (Task t : inprogress) {
            if (t.getCompletionPercentage() == 0) {
                tasks.add(t);
            }
        }
        for (Task t : inline) {
            tasks.add(t);
        }
        return tasks;
    }

    public List<Task> getInline() {
        return this.inline;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Task> getCompleted() {
        return this.completed;
    }

    public List<Task> getInprogress() {
        return this.inprogress;
    }


    public void addTask(Task task) {
        this.inline.add(task);
    }

    public void setInline(List<Task> tasks) {
        this.inline = new ArrayList<>(tasks);
    }

    public void setInprogress(List<Task> tasks) {
        this.inprogress = new ArrayList<>(tasks);
    }

    public void setCompleted(List<Task> tasks) {
        this.completed = new ArrayList<>(tasks);
    }

    public void setPriority(int priority) throws InvalidValuesException {
        if(priority < 1 || priority > 10) {
            throw new InvalidValuesException("Invalid values!");
        }
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void removeCompletedTask(int id) throws NoSuchElementException {
        this.completed.remove(getCompletedTask(id));
    }

    private boolean isTaskFinished(Task t) {
        return t.getReady() == t.getRequiredQuantity();
    }

    private boolean canProceedWith(Task t) {
        boolean test = true;

        HashMap<Item, Integer> requiredItems = t.getProduct().getRequiredItems();
        for (Map.Entry<Item, Integer> e : requiredItems.entrySet()) {
            Item i = e.getKey();
            int v = e.getValue();

            if (v > i.getQuantityAvailable()) {
                test = false;
                break;
            }
        }

        return test;
    }

    private Task getFirstAvailableInprogressTask() {
        for (Task t : inprogress) {
            if (canProceedWith(t)) {
                return t;
            }
        }

        return null;
    }

    private Task getCompletedTask(int taskId) throws NoSuchElementException {
        for (Task t : completed) {
            if (t.getId() == taskId) {
                return t;
            }
        }

        throw new NoSuchElementException();
    }

    public List<Task> getBothInPInL() {
        List <Task> filteredList = new ArrayList<>();
        filteredList.addAll(inprogress);
        filteredList.addAll(inline);
        return filteredList;
    }

    public double getCompletionRate() {
        double num = inprogress.size() + inline.size() + completed.size();
        double sum = completed.size();
        for(Task t : inprogress) {
            sum += t.getCompletionPercentage();
        }
        double rate = sum / num;
        return rate;
    }

    public boolean hasProduct(String filterValue) {
        for (Task t : inline) {
            if (t.getProduct().getName().equals(filterValue)) {
                return true;
            }
        }
        for (Task t : inprogress) {
            if (t.getProduct().getName().equals(filterValue)) {
                return true;
            }
        }
        for (Task t : completed) {
            if (t.getProduct().getName().equals(filterValue)) {
                return true;
            }
        }
        return false;
    }
}
