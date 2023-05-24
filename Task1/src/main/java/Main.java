import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < 100; i++) {
            sizeToFreq.put(i, 1);
        }

        Runnable logic = () -> {
            rCount();
        };
        for (int i = 0; i < 1000; i++) {
            threadPool.execute(logic);
        }


        threadPool.shutdown();
        showList(sizeToFreq);


    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static Integer rCount() {
        int count = 1;
        int frequency = 0;
        String path = generateRoute("RLRFR", 100);
        for (int i = 0; i < path.length(); i++) {
            if (path.charAt(i) == 'R') {
                count++;
            }
        }
        frequency = 100 % count;
        synchronized (sizeToFreq) {
            sizeToFreq.put(frequency, count);
        }

        return count;
    }

    public static void showList(Map<Integer, Integer> map) {
        Map.Entry<Integer, Integer> maxEntry =
                Collections.max(map.entrySet(), Map.Entry.comparingByValue());

        System.out.println("Самое частое количество повторений " + maxEntry.getKey() + " (встретилось " + maxEntry.getValue() + ")");
        System.out.println("Другие размеры:");
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((a, b) -> b - a))
                .skip(1)
                .forEach(e -> System.out.println(e.getKey() + " - " + e.getValue()));

    }


}
