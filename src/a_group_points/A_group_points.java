package a_group_points;

import java.util.ArrayList;
import java.util.Comparator;

public class A_group_points {

    public static void main(String[] args) {
        int R = 5;
        int[] ini_pts_x = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayList<arrive_point> points = new ArrayList<arrive_point>();
        for (int x : ini_pts_x) {
            points.add(new arrive_point(x));
        }
        arrive_points arrive_points = new arrive_points(points, R);
        int findMinRecPoints = arrive_points.findMinRecPoints();
        System.out.println("Min Num Of Pts = " + findMinRecPoints);
    }
}

class arrive_point {

    private int x;
    private ArrayList listOfPointsByX;

    @Override
    public String toString() {
        return "arrive_point {x=" + x + ", " + listOfPointsByX.toString() + "}";
    }

    public arrive_point(int x) {
        this.x = x;
        listOfPointsByX = new ArrayList();
    }

    public int getX() {
        return x;
    }

    public void addToListOfPointsByX(arrive_point point) {
        listOfPointsByX.add(point.getX());
    }

    public ArrayList getListOfPts() {
        return listOfPointsByX;
    }
}

class arrive_points {

    private ArrayList<arrive_point> arrive_points;
    private int R;
    private int finNumPts;

    public arrive_points(ArrayList<arrive_point> arrive_points, int R) {
        this.arrive_points = arrive_points;
        sortArrivePointsByX();
        this.R = R;
        System.out.println("R=" + R);
    }

    public int findMinRecPoints() {
        if (doAllPointsTooFar()) {
            return arrive_points.size();
        }
        int iter = 1;
        finalProcessPoints(iter);
        return finNumPts;
    }

    private void finalProcessPoints(int iter) {
        finNumPts += 1;
        for (arrive_point arrive_point : arrive_points) {
            formThisPointList(arrive_point);
        }
        sortArrivePointsByListsLengthDesc();
        System.out.println("Start iteration: " + iter);
        for (arrive_point arrive_point1 : arrive_points) {
            System.out.println(arrive_point1);
        }
        arrive_point ptWithLongestList = arrive_points.get(0);

        for (int i = 0; i < arrive_points.size(); i++) {
            if (ptWithLongestList.getListOfPts().contains(arrive_points.get(i).getX())) {
                arrive_points.remove(i);
                i--;
            }
        }

        if (arrive_points.size() > 0) {
            System.out.println("");
            finalProcessPoints(iter + 1);
        }
    }

    private void sortArrivePointsByListsLengthDesc() {
        arrive_points.sort(new Comparator<arrive_point>() {
            @Override
            public int compare(arrive_point o1, arrive_point o2) {
                return o2.getListOfPts().size() - o1.getListOfPts().size();
            }
        });
    }

    private boolean doAllPointsTooFar() {
        for (int i = 0; i < arrive_points.size() - 1; i++) {
            if (arrive_points.get(i + 1).getX() - arrive_points.get(i).getX() <= R) {
                return false;
            }
        }
        return true;
    }

    private void sortArrivePointsByX() {
        arrive_points.sort((arrive_point p1, arrive_point p2) -> p1.getX() - p2.getX());
    }

    private void formThisPointList(arrive_point point) {
        point.getListOfPts().clear();
        for (int i = 0; i < arrive_points.size(); i++) {
            if (Math.abs(point.getX() - arrive_points.get(i).getX()) <= R) {
                point.addToListOfPointsByX(arrive_points.get(i));
            }
        }
    }

}
