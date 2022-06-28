package at.technikum.tourplanner.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AsyncService<R> extends Service<R> {

    private Supplier<R> supplier;

    public void setSupplier(Supplier<R> supplier) {
        this.supplier = supplier;
    }

    public void subscribe(Consumer<R> onSuccess, Consumer<Throwable> onError) {
        valueProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue && null != onSuccess) {
                onSuccess.accept(newValue);
            }
        });
        exceptionProperty().addListener((observable, oldValue, throwable) -> {
            if (null != throwable && null != onError) {
                onError.accept(throwable);
            }
        });
    }

    @Override
    protected Task<R> createTask() {
        return new Task<>() {
            @Override
            protected R call() {
                if (null != supplier) {
                    return supplier.get();
                }
                return null;
            }
        };
    }
}