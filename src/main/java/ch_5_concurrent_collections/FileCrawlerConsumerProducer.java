package ch_5_concurrent_collections;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/*
Producer and consumer pattern in a desktop search application
 */
public class FileCrawlerConsumerProducer implements Runnable {

    private final BlockingQueue<File> fileQueue;
    private final File root;

    public FileCrawlerConsumerProducer(BlockingQueue<File> fileQueue, File root) {
        this.fileQueue = fileQueue;
        this.root = root;
    }

    private boolean alreadyIndexed(File f) {
        return false;
    }

    // The crawler is the producer
    @Override
    public void run() {
        try {
            crawl(root);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void crawl(File root) throws InterruptedException {
        File[] entries = root.listFiles();
        if (entries != null) {
            for (File entry: entries) {
                if (entry.isDirectory()) {
                    crawl(entry);
                } else if (!alreadyIndexed(entry)) {
                    fileQueue.put(entry);
                }
            }
        }
    }

    // This is the consumer in the pattern; it consumes the files and indexes them
    static class Indexer implements Runnable {
        private final BlockingQueue<File> queue;

        public Indexer(BlockingQueue<File> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    indexFile(queue.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void indexFile(File file) {
            System.out.println("Indexing file: " + file.getName());
        }

    }

    private static final int BOUND = 10;
    private static final int N_CONSUMERS = Runtime.getRuntime().availableProcessors();

    public static void startIndexing(File[] roots) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<>(BOUND);

        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };

        // Producers
        for (File root: roots) {
            new Thread(new FileCrawlerConsumerProducer(queue, root)).start();
        }

        // Consumers
        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new Indexer(queue)).start();
        }

    }

    public static void main(String[] args) {
        // This will index the current directory
        startIndexing(new File[]{new File(".")});
    }

}
