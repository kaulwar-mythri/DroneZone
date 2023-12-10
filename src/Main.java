import java.util.*;
class Point {
    int x, y, drone;


    public Point(int x, int y, int drone) {
        this.x = x;
        this.y = y;
        this.drone = drone;
    }
}
public class Main {
    static List<Point> path = new ArrayList<>();

    public static boolean isValid(List<List<Integer>> G, int i, int j, int m, int n) {
        return i >= 0 && j >= 0 && i < m && j < n;
    }

    public static Point findTarget(List<List<Integer>> G, int X) {
        int[][] dirsFor1 = {{0, 1}, {1, 0}};
        int[][] dirsFor2 = {{0, 1}, {-1, 0}};
        int[][] dirsFor3 = {{0, 1}, {-1, 0}};
        int[][] dirsFor4 = {{0, -1}, {-1, 0}};

        int m = G.size(), n = G.get(0).size();

        Queue<Point> q = new LinkedList<>();

        //Initializing drones at the four corners
        q.offer(new Point(0, 0, 1));
        q.offer(new Point(0, n-1, 2));
        q.offer(new Point(m-1, 0, 3));
        q.offer(new Point(m-1, n-1, 4));

        boolean[][] vis = new boolean[m][n];

        while (!q.isEmpty()) {
            Point cur = q.poll();
            path.add(cur);

            // Check if the drone found the target
            if (G.get(cur.x).get(cur.y) == X) {
                return cur;
            }

            // Move drones towards the center
            if(cur.drone == 1) {
                moveToNext(G, q, cur, vis, dirsFor1, m, n);
            } else if(cur.drone == 2) {
                moveToNext(G, q, cur, vis, dirsFor2, m, n);
            } else if(cur.drone == 3) {
                moveToNext(G, q, cur, vis, dirsFor3, m, n);
            } else {
                moveToNext(G, q, cur, vis, dirsFor4, m, n);
            }
        }
        return null;
    }

    public static void moveToNext(List<List<Integer>> G, Queue<Point> q, Point cur, boolean[][] vis, int[][] dirs, int m, int n) {
        for(int i=0; i<dirs.length; i++) {
            int nxtX = cur.x + dirs[i][0], nxtY = cur.y + dirs[i][1];
            if(isValid(G, nxtX, nxtY, m, n) && !vis[nxtX][nxtY]) {
                q.offer(new Point(nxtX, nxtY, cur.drone));
                vis[nxtX][nxtY] = true;
            }
        }
    }

    public static void main(String[] args) {
        //creating grid
        List<List<Integer>> G = new ArrayList<>();
        G.add(List.of(1, 2, 3, 4));
        G.add(List.of(5, 6, 7, 8));
        G.add(List.of(9, 10, 11, 12));
        G.add(List.of(13, 14, 15, 16));

        int X = 10;

        Point ans = findTarget(G, X);

        System.out.println("Grid: " + G);

        System.out.println("Target X: " + X);

        System.out.println("The drone to reach the target first is: " + ans.drone);
        System.out.println("The path taken by the drone is: ");

        for(Point p: path) {
            if(p.drone == ans.drone) {
                System.out.println("{x, y} :" + "{" + p.x + ", " + p.y + "}");
            }
        }
    }
}