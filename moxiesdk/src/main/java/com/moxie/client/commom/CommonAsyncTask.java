package com.moxie.client.commom;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

/* compiled from: TbsSdkJava */
public abstract class CommonAsyncTask<Params, Progress, Result> {
    private static final BlockingQueue<Runnable> d = new LinkedBlockingQueue(10);
    private static final BlockingQueue<Runnable> e = new LinkedBlockingQueue();
    private static final BlockingQueue<Runnable> f = new LinkedBlockingQueue();

    private static final ThreadFactory c = new ThreadFactory() {
        private final AtomicInteger a = new AtomicInteger(1);

        public final Thread newThread(Runnable runnable) {
            return new Thread(runnable, "CommonAsyncTask #" + this.a.getAndIncrement());
        }
    };
    public static final Executor a =
            new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, d, c, new DiscardOldestPolicy());
    public static final Executor b = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, e, c);
    private static final ThreadFactory g = new ThreadFactory() {
        private final AtomicInteger a = new AtomicInteger(1);

        public final Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "ImageLoader #" + this.a.getAndIncrement());
            thread.setPriority(4);
            return thread;
        }
    };
    private static final ThreadPoolExecutor h = new ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, f, g);
    private static final InternalHandler handler = new InternalHandler();
    private static volatile Executor j = b;
    private final WorkerRunnable<Params, Result> k = new WorkerRunnable<Params, Result>() {
        final /* synthetic */ CommonAsyncTask a;

        {
            this.a = CommonAsyncTask.this;
        }

        public Result call() throws Exception {
            this.a.o.set(true);
            Process.setThreadPriority(10);
            return postResult(doInBackground(b));
        }
    };
    private final FutureTask<Result> l = new FutureTask<Result>(this.k) {
        final /* synthetic */ CommonAsyncTask a = CommonAsyncTask.this;

        protected void done() {
            try {
                CommonAsyncTask.postResultIfNotInvoked(this.a, get());
            } catch (InterruptedException e) {
                Log.w("CommonAsyncTask", e);
            } catch (ExecutionException e2) {
                throw new RuntimeException("An error occured while executing doInBackground()", e2.getCause());
            } catch (CancellationException e3) {
                CommonAsyncTask.postResultIfNotInvoked(this.a, null);
            }
        }
    };
    private volatile Status m = Status.PENDING;
    private final AtomicBoolean n = new AtomicBoolean();
    private final AtomicBoolean o = new AtomicBoolean();

    /* compiled from: TbsSdkJava */
    private static abstract class WorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] b;

        private WorkerRunnable() {
        }
    }

    /* compiled from: TbsSdkJava */
    private static class AsyncTaskResult<Data> {
        final CommonAsyncTask a;
        final Data[] b;

        AsyncTaskResult(CommonAsyncTask commonAsyncTask, Data... dataArr) {
            this.a = commonAsyncTask;
            this.b = dataArr;
        }
    }

    /* compiled from: TbsSdkJava */
    private static class InternalHandler extends Handler {
        public InternalHandler() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message message) {
            AsyncTaskResult asyncTaskResult = (AsyncTaskResult) message.obj;
            switch (message.what) {
                case 1:
                    CommonAsyncTask.finish(asyncTaskResult.a, asyncTaskResult.b[0]);
                    return;
                case 2:
                    CommonAsyncTask.a();
                    return;
                default:
                    return;
            }
        }
    }

    /* compiled from: TbsSdkJava */
    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    protected abstract Result doInBackground(Params... paramsArr);

    private Result postResult(Result result) {
        handler.obtainMessage(1, new AsyncTaskResult<Result>(this, result)).sendToTarget();
        return result;
    }

    protected void onPostExecute(Result result) {
    }

    protected static void a() {
    }

    public final boolean b() {
        return this.n.get();
    }

    public final boolean c() {
        this.n.set(true);
        return this.l.cancel(true);
    }

    public final CommonAsyncTask<Params, Progress, Result> execute(Params... paramsArr) {
        Executor executor = j;
        if (this.m != Status.PENDING) {
            switch (this.m) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case FINISHED:
                    throw new IllegalStateException(
                            "Cannot execute task: the task has already been executed (a task can be executed only "
                                    + "once)");
            }
        }
        this.m = Status.RUNNING;
        this.k.b = paramsArr;
        executor.execute(this.l);
        return this;
    }

    static /* synthetic */ void postResultIfNotInvoked(CommonAsyncTask commonAsyncTask, Object obj) {
        if (!commonAsyncTask.o.get()) {
            commonAsyncTask.postResult(obj);
        }
    }

    static /* synthetic */ void finish(CommonAsyncTask commonAsyncTask, Object obj) {
        if (!commonAsyncTask.n.get()) {
            commonAsyncTask.onPostExecute(obj);
        }
        commonAsyncTask.m = Status.FINISHED;
    }
}