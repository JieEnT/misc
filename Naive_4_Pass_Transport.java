import java.util.*;

class Test {

    // Grid to check if location has been visited
    static boolean[][] hasVisited = new boolean[10][10];
    // A temp array passed within the findPaths function to keep potential paths
    static ArrayList<Integer> temp = new ArrayList<>();
    static int[][] directions = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
    static List<Integer> forward;
    static List<Integer> backward;

    public static void main(String[] args) {
        ArrayList<Integer> test = new ArrayList<>();
        test.add(92);
        test.add(95);
        test.add(88);
        test.add(79);

        List<Integer> output = transport(test);
        for (int no : output) {
            System.out.print(no + " ");
        }
    }

    public static List<Integer> transport(List<Integer> pos) {
        oneWayTransport(pos, true);
        hasVisited = new boolean[10][10];
        Collections.reverse(pos);
        oneWayTransport(pos, false);

        if (forward == null && backward == null) {
            return null;
        } else if (forward == null) {
            Collections.reverse(backward);
            return backward;
        } else if (backward == null) {
            return forward;
        } else {
            if (forward.size() < backward.size()) {
                return forward;
            } else {
                Collections.reverse(backward);
                return backward;
            }
        }
    }

    public static List<Integer> oneWayTransport(List<Integer> pos, boolean isForward) {
        int[][] points = new int[4][2];

        for (int i = 0; i < pos.size(); i++) {
            points[i][0] = pos.get(i) / 10;
            points[i][1] = pos.get(i) % 10;
        }

        int[] point1 = points[0];
        int[] point2 = points[1];
        int[] point3 = points[2];
        int[] point4 = points[3];

        int[][] store = new int[10][10];
        store[point1[0]][point1[1]] = 0;
        hasVisited[point1[0]][point1[1]] = true;
        bfs(point1[0], point1[1], store, hasVisited);
        // printGrid(store);

        ArrayList<List<Integer>> paths = new ArrayList<>();
        findPaths(point2[0], point2[1], point1[0], point1[1], store, paths, temp);
        // printPaths(paths);

        // now for second coordinate
        for (List<Integer> block : paths) {
            hasVisited = new boolean[10][10];
            int[][] storeTwo = new int[10][10];
            blockOff(block, hasVisited);
            store[point2[0]][point2[1]] = 0;
            bfs(point2[0], point2[1], storeTwo, hasVisited);
            // printGrid(storeTwo);

            ArrayList<List<Integer>> paths2 = new ArrayList<>();
            findPaths(point3[0], point3[1], point2[0], point2[1], storeTwo, paths2, temp);
            // printPaths(paths2);

            for (List<Integer> blocktwo : paths2) {
                hasVisited = new boolean[10][10];
                int[][] storeThree = new int[10][10];
                blockOff(block, hasVisited);
                blockOff(blocktwo, hasVisited);
                store[point2[0]][point2[1]] = 0;
                bfs(point3[0], point3[1], storeThree, hasVisited);
                // printGrid(storeThree);

                ArrayList<List<Integer>> paths3 = new ArrayList<>();
                findPaths(point4[0], point4[1], point3[0], point3[1], storeThree, paths3, temp);
                for (List<Integer> finalPath : paths3) {
                    ArrayList<Integer> contenderPath = new ArrayList<>();

                    for (int i = block.size() - 1; i > 0; i--) {
                        contenderPath.add(block.get(i));
                    }
                    for (int i = blocktwo.size() - 1; i > 0; i--) {
                        contenderPath.add(blocktwo.get(i));
                    }
                    for (int i = finalPath.size() - 1; i >= 0; i--) {
                        contenderPath.add(finalPath.get(i));
                    }

                    if (isForward) {
                        if (forward == null) {
                            forward = contenderPath;
                        } else {
                            if (forward.size() > contenderPath.size()) {
                                forward = contenderPath;
                            }
                        }
                    } else {
                        if (backward == null) {
                            backward = contenderPath;
                        } else {
                            if (backward.size() > contenderPath.size()) {
                                backward = contenderPath;
                            }
                        }
                    }

                }

                unBlock(blocktwo, hasVisited);
            }

            unBlock(block, hasVisited);
        }

        return null;
    }

    public static void blockOff(List<Integer> toblock, boolean[][] hasVisited) {
        for (int no : toblock) {
            hasVisited[no / 10][no % 10] = true;
        }
        System.out.println();
    }

    public static void unBlock(List<Integer> toblock, boolean[][] hasVisited) {
        for (int no : toblock) {
            hasVisited[no / 10][no % 10] = false;
        }
    }

    public static void bfs(int x, int y, int[][] store, boolean[][] hasVisited) {
        int counter = 1;

        ArrayList<int[]> points = new ArrayList<>();
        points.add(new int[] { x, y });
        while (points.size() > 0) {
            ArrayList<int[]> nextPoints = new ArrayList<>();
            for (int[] point : points) {
                for (int[] dir : directions) {
                    int nextx = point[0] + dir[0];
                    int nexty = point[1] + dir[1];

                    if (nextx >= 0 && nexty >= 0 && nextx < store[0].length && nexty < store[1].length
                            && hasVisited[nextx][nexty] == false) {
                        store[nextx][nexty] = counter;
                        hasVisited[nextx][nexty] = true;
                        nextPoints.add(new int[] { nextx, nexty });
                    }
                }
            }
            counter++;
            points = nextPoints;
        }
    }

    // Find the possible paths between two points recursively
    // endx and endy supposed to be the starting
    public static void findPaths(int currx, int curry, int endx, int endy, int[][] store,
            ArrayList<List<Integer>> toret, ArrayList<Integer> temp) {

        if (currx == endx && curry == endy) {
            temp.add(endx * 10 + endy);
            toret.add(new ArrayList<>(temp));
            temp.remove(temp.size() - 1);
        }

        // We will travel in the reverse fashion from highest to lowest point
        for (int[] dir : directions) {
            int newx = currx + dir[0];
            int newy = curry + dir[1];
            if (newx < 0 || newy < 0 || newx >= store[0].length || newy >= store.length) {
                continue;
            }
            if (store[newx][newy] == store[currx][curry] - 1) {
                temp.add(currx * 10 + curry);
                findPaths(newx, newy, endx, endy, store, toret, temp);
                temp.remove(temp.size() - 1);
            }
        }
    }

    // Helper methods

    public static void printGrid(int[][] store) {
        for (int i = 0; i < store.length; i++) {
            for (int j = 0; j < store[0].length; j++) {
                System.out.print(store[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printBoolGrid(boolean[][] hasVisited) {
        for (int i = 0; i < hasVisited.length; i++) {
            for (int j = 0; j < hasVisited[0].length; j++) {
                if (hasVisited[i][j] == true) {
                    System.out.print(1 + " ");
                } else {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
    }

    public static void printPaths(ArrayList<List<Integer>> paths) {
        for (List<Integer> path : paths) {
            for (int no : path) {
                System.out.print(no + " ");
            }
            System.out.println();
        }
    }

    public static void printPath(List<Integer> path) {
        for (int no : path) {
            System.out.print(no + " ");
        }
        System.out.println();
    }
}