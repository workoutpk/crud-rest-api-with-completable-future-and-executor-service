# Crud-api-with-completable-future-and-executor-service
Simple Crud API With Completable Future(CompletableFuture) and Executor Service(ExecutorService )

# Here are some guidelines for deciding when to use @Async:
```
1. Use @Async for long-running or blocking methods.
2. Do not use @Async for short or non-blocking methods.
3. Consider using @Async for methods that are called frequently.
4. Use @Async in conjunction with a thread pool to manage the number of concurrent threads.
```

CompletableFuture is a powerful tool for asynchronous programming in Java. It can be used to run tasks concurrently, chain tasks together, and handle errors.

# Here are some situations when you might want to use CompletableFuture:
```
1. When you need to run multiple tasks concurrently. CompletableFuture makes it easy to run multiple tasks at the same time, without blocking the main thread. This can improve the performance and scalability of your application.
2. When you need to chain tasks together. CompletableFuture provides a variety of methods for chaining tasks together. This can be useful for tasks that need to be executed in a specific order, or for tasks that need to be executed in parallel.
3. When you need to handle errors. CompletableFuture provides a variety of methods for handling errors. This can help you to ensure that your application continues to run even if an error occurs.
4. Here are some examples of when you might want to use CompletableFuture:

5. When you are making multiple network calls. CompletableFuture can be used to make multiple network calls concurrently. This can improve the performance of your application by reducing the total amount of time it takes to make all of the calls.
6. When you are processing a large file. CompletableFuture can be used to process a large file in parallel. This can improve the performance of your application by reducing the total amount of time it takes to process the file.
7. When you are running a long-running task. CompletableFuture can be used to run a long-running task in a separate thread. This can improve the user experience by preventing the main thread from blocking.
```