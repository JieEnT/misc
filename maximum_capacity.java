import java.util.*;

class Node {
    String coord;
    int freq;

    public Node(String coord, int freq) {
        this.coord = coord;
        this.freq = freq;
    }
}

class Test {

    public static int max_capacity(int w, int b, int s, int maxPeople) {
        HashMap<String, Node> hMap = new HashMap<>();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < b; j++) {
                int currW = i % s;
                int currB = j % s;
                String currLoc = currW + "_" + currB;

                if (hMap.get(currLoc) == null) {
                    hMap.put(currLoc, new Node(currLoc, 1));
                } else {
                    hMap.get(currLoc).freq += 1;
                }
            }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>((n1, n2) -> n2.freq - n1.freq);
        for (Node n : hMap.values()) {
            pq.add(n);
        }

        int total = 0;

        while (maxPeople > 0) {
            Node considered = pq.poll();
            total += considered.freq;
            maxPeople--;
        }

        return total;
    }

    public static void main(String[] args) {
        System.out.println(max_capacity(3, 3, 2, 3));
    }

}
