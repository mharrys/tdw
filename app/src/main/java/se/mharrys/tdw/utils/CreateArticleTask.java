package se.mharrys.tdw.utils;

import android.os.AsyncTask;

import se.mharrys.tdw.InitializationException;
import se.mharrys.tdw.article.factory.CreateArticleStrategy;

/**
 * The responsibility of this class is to run a article creation strategy on a separate thread.
 */
public class CreateArticleTask<T> extends AsyncTask<Void, Void, T> {
    private CreateArticleStrategy<T> strategy;
    private OnTaskComplete<T> listener;
    private Exception error;

    public CreateArticleTask(CreateArticleStrategy strategy, OnTaskComplete<T> listener) {
        this.strategy = strategy;
        this.listener = listener;
    }

    @Override
    protected T doInBackground(Void... voids) {
        error = null;
        try {
            return strategy.create();
        } catch (InitializationException e) {
            error = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(T items) {
        super.onPostExecute(items);
        listener.onTaskCompleted(items, error);
    }
}
