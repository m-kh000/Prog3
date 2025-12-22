package core;
import java.util.ArrayList;
import java.util.Collection;
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

    public ProductLine() {}

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


    public Task[] previewCompletedTasks() {
        return completed.toArray(new Task[completed.size()]);
    }
    public Task[] previewInprogressTasks() {
        return inprogress.toArray(new Task[inprogress.size()]);
    }
    public Task[] preview0PCInprogressTasks() {
        List<Task> inprogress0PC = new ArrayList<>();
        for(Task t : inprogress){
            if(t.getCompletionPercentage() == 0.0){
                inprogress0PC.add(t);
            }
        }
        return inprogress0PC.toArray(new Task[inprogress0PC.size()]);
    }
    public Task[] previewInlineTasks() {
        return inline.toArray(new Task[inline.size()]);
    }
    public Task[] previewCanceledTasks() {
        return canceled.toArray(new Task[canceled.size()]);
    }

    // public boolean madeProduct() {
   
    // }

    public void cancelTask(Task task) {
        int index = inline.indexOf(task);
        if (index == -1) {
            index = inprogress.indexOf(task);
        } else {
            canceled.add(inline.remove(index));
        }

        if (index == -1) {
            throw new NoSuchElementException("No such task or the task is already completed or canceled."); 
        } else {
            canceled.add(inprogress.remove(index));
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
    public List<Task> get0PCInprogressTasks() {
        return this.inprogress;
    }
    public List<Task> getInlineTasks() {
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

    public ArrayList<Task> getCompleted() {
        return new ArrayList<>(completed);
    }

    public ArrayList<Task> getInprogress() {
        return new ArrayList<>(inprogress);
    }

    public ArrayList<Task> getCanceled() {
        return new ArrayList<>(canceled);
    }

    public void addTask(Task task) {
        this.inline.add(task);
    }

    public void setInline(List<Task> tasks) {
        this.inline = tasks;
    } 
    public void setInprogress(List<Task> tasks) {
        this.inprogress = tasks;
    } 
    public void setCompleted(List<Task> tasks) {
        this.completed = tasks;
    } 
    public void setCanceled(List<Task> tasks) {
        this.canceled = tasks;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    Collection<Task> get0PCInprogress() {
        throw new UnsupportedOperationException("Not supported yet.");
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

            if (v < i.getQuantityAvailable()) {
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
}