package se.mharrys.tdw.utils;

/**
 * The responsibility of this class is to respond to task completed asynchronous.
 */
public interface OnTaskComplete<T> {

    /**
     * Called when task is completed.
     *
     * @param result the result from running the task, allowed to be null
     * @param e the exception thrown (if any) during execution
     */
    void onTaskCompleted(T result, Exception e);
}
