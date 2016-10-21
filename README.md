# shenandoah-perf
repository containing performance testing of shenandoah.
 The CountDownLatch project demonstrates the latch does not timout. The jmh test demonstrates the timeouts by signalling "(*interrupt*)" for benchmark tests that needed an InterruptException. InterruptExceptions occur when the number of threads is above 2.
 
