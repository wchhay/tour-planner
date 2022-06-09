package at.technikum.tourplanner.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Supplier;

public class AsyncService<R> extends Service<R> {
    private Supplier<R> supplier;

    public AsyncService() {
    }

    public AsyncService(Supplier<R> supplier) {
        this.supplier = supplier;
    }

    public void setSupplier(Supplier<R> supplier) {
        this.supplier = supplier;
    }

    @Override
    protected Task<R> createTask() {
        return new Task<>() {
            @Override
            protected R call() throws Exception {
                if (null != supplier) {
                    return supplier.get();
                }
                return null;
            }
        };
    }
}