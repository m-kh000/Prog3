package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import jsonParser.annotations.JsonIgnore;
import utils.FileUtils;
import utils.ThreadManager;

public class ProductLine implements Runnable {

    private static int nextId = 1;
    private int id;
    private int priority;
    private String name;
    private String status;
    private String note;
    @JsonIgnore
    private List<Task> completed;
    @JsonIgnore
    private List<Task> inprogress;
    @JsonIgnore
    private List<Task> inline;
    @JsonIgnore
    private List<Task> canceled;

    public ProductLine() {
    }

    public ProductLine(String name, String status, int priority) {
        this.id = nextId++;
        this.priority = priority;
        this.name = name;
        this.status = status;
        this.completed = new ArrayList<>();
        this.inprogress = new ArrayList<>();
        this.inline = new ArrayList<>();
        this.canceled = new ArrayList<>();
    }

    @Override
    public void run() {
        while (!inline.isEmpty()) {
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

            if (runningTask.getCompletionPercentage() == 100.00) {
                completed.add(runningTask);
                inprogress.remove(runningTask);
            }
        }
        ThreadManager.assign();
    }

    public void cancelTask(Task task) {
        int index = inprogress.indexOf(task);
        if (index != -1) {
            canceled.add(inprogress.remove(index));
        } else {
            index = inline.indexOf(task);
            if (index != -1) {
                canceled.add(inline.remove(index));
            } else {
                throw new NoSuchElementException("Task not found in the product line.");
            }
        }
    }

    //GETTERS
    public int getLineId() {
        return this.id;
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

    public List<Task> getCanceledTasks() {
        return this.canceled;
    }

    public String getNote() {
        return this.note;
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

    public List<Task> getCanceled() {
        return this.canceled;
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

    public void setCanceled(List<Task> tasks) {
        this.canceled = new ArrayList<>(tasks);
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPriority(int priority) {
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

    public double getCompletionRate() {
//TODO return as 0.123253
        return 0.123253;
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
