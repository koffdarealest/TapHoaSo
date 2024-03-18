package wrapper;

import model.Transaction;

import java.util.concurrent.CompletableFuture;

public class TransactionWrapper {
    private final Transaction transaction;
    private final CompletableFuture<Boolean> future = new CompletableFuture<>();

    public TransactionWrapper(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public CompletableFuture<Boolean> getFuture() {
        return future;
    }
}
