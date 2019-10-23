import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static double nearestPoint(Point start, Point now, ArrayList<Point> points) {
        System.out.print("[" + now.getX() + "," + now.getY() + "] -> ");
        if (points.size() == 1) {
            System.out.println("[" + start.getX() + "," + start.getY() + "] ");
            return now.distance(start);
        } else {
            points.remove(now);
            double mini = Integer.MAX_VALUE;
            int i = 0;
            for (Point p : points) {
                if (now.distance(p) < mini) {
                    mini = now.distance(p);
                    i = points.indexOf(p);
                }
            }
            return mini + nearestPoint(start, points.get(i), points);
        }
    }

    static ArrayList<Point> way;
    static double mini = Integer.MAX_VALUE;

    private static void listPermutations(ArrayList<Point> points, int fixed) {
        double sum = 0;
        if (fixed >= points.size() - 1) {
            sum = 0;
            for (int j = 0; j < points.size() - 1; j++) {
                sum += points.get(j).distance(points.get(j + 1));
            }
            sum += points.get(0).distance(points.get(points.size()-1));
            if (sum  < mini) {
                mini = sum;
                way=new ArrayList<>(points);
            }
            return;
        }
        for (int i = fixed; i < points.size(); i++) {
            Point t = points.get(fixed);
            points.set(fixed, points.get(i));
            points.set(i, t);
            listPermutations(points, fixed + 1);
            t = points.get(fixed);
            points.set(fixed, points.get(i));
            points.set(i, t);
        }
    }

    private static void randomPointGenerator(int n) throws IOException {
        String fileName = "./1.txt";
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        writer.println(n);
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            writer.println(Integer.toString(rand.nextInt(100)) + " " + Integer.toString(rand.nextInt(100)));
        }
        writer.close();
    }

    private static void exhaustiveSearch(ArrayList<Point> points) {
        listPermutations(points, 0);
        for (int k = 0; k < way.size(); k++) {
            System.out.print("[" + way.get(k).getX() + ", " + way.get(k).getY() + "] ->");
        }
        System.out.println("[" + way.get(0).getX() + ", " + way.get(0).getY() + "]");
        System.out.println(mini);

    }
    public static void main(String[] args) throws Exception {
        Instant start = Instant.now();
//        randomPointGenerator(20);
        File file = new File("E.txt");
        Scanner sc = new Scanner(file);
        int n = sc.nextInt();
        ArrayList<Point> points = new ArrayList();
        for (int i = 0; i < n; i++) points.add(new Point(sc.nextInt(), sc.nextInt()));
        //System.out.println(nearestPoint(points.get(0),points.get(0),points));
        exhaustiveSearch(points);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(timeElapsed);

    }
}
