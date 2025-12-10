package core;
import java.util.ArrayList;
import java.util.List;

public class ProductLine implements Runnable {
    private static int nextId = 1;
    private int id;
    private String name;
    private String status;
    private List<Task> completed;
    private List<Task> inprogress;
    private List<Task> inline;
    private List<Task> canceled;

    public ProductLine(String name, String status) {
        this.id = nextId++;
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
            if (inprogress.isEmpty()) {
                inprogress.add(inline.removeFirst());
            }

            while (true/* canMakeAnotherProduct && still didn't make all the required quantity */) {
                /* consume the needed quantity from the items in the factory */
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // TODO: log the exception to Exceptions.txt file
                }
            }

            // if (!inline.isEmpty()) {
            //     inprogress.add(inline.removeFirst());
            // }
        }
    }

    public Task[] previewCompletedTasks() {
        return completed.toArray(new Task[completed.size()]);
    }
    public Task[] previewInprogressTasks() {
        return inprogress.toArray(new Task[inprogress.size()]);
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
    
    }

    //GETTERS

    public int getLineId() {
        return this.id;
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
    public List<Task> getInlineTasks() {
        return this.inline;
    }
    public List<Task> getCanceledTasks() {
        return this.canceled;
    }
}