package asynchrony;

import java.util.concurrent.*;
/**
 * This is a straightforward translation of FutureDemo
 * to use java.util.concurrent.FutureTask
 * @author oscar
 *
 */

public class FutureTaskDemo {
	
	public static void main(String args[]) {
		FutureTaskDemo.demo();
	}
	
	public static void demo() {
		FutureTaskDemo server = new FutureTaskDemo();
		System.out.println("Starting FutureDemo.demo()");
		// With some luck, the faster ones may get computed first ...
		// NB: results depend highly on Thread scheduling ...
		startDemoThread(server, 45);
		startDemoThread(server, 35);
		startDemoThread(server, 20);
		startDemoThread(server, 15);
		startDemoThread(server, 5);
	}
	
	protected static void startDemoThread(final FutureTaskDemo server, final int n){
		new Thread() {
			public void run() {
				try {
					System.out.println("CALLING fibonacci("+n+")");
					FutureTask<Integer> future = server.service(n);
					System.out.println("GOT future(fibonacci("+n+"))");
					int val = future.get().intValue();
					System.out.println("GOT fibonacci("+n+") = " + val);
				} catch(InterruptedException e) {
				} catch(ExecutionException e) {
				}
			}
		}.start();
	}
	
	public FutureTask<Integer> service (final int n) {		// unsynchronized
		FutureTask<Integer> future = new FutureTask<Integer>(new Callable<Integer>() {
			public Integer call() { return new Integer(fibonacci(n)); }
		});
		new Thread(future).start(); // or use an Executor
		return future;
	}
	
	public static int fibonacci(int n) {
		if (n<2) return 1;
		else return fibonacci(n-1) + fibonacci(n-2);
	}
}

