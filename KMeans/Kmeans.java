import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Kmeans{
    ArrayList<int[]> oldClusterCentroid;
    ArrayList<int[]> clusterCentroid;
    int[][] allPoints;

    public void initialiseCluster(int noClusters){
        for(int i = 0; i < noClusters; i++){
            int xCoord = ThreadLocalRandom.current().nextInt(0,11);
            int yCoord = ThreadLocalRandom.current().nextInt(0,11);
            clusterCentroid.add(new int[]{xCoord, yCoord});
        }
    }

    public int calculateEuclidean(int[] point, int[] target){
        int xDiff = point[0]-target[0];
        int yDiff = point[1]-target[1];
        return (int)Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public HashMap<Integer, List<int[]>> clusterToCentroidMap(){
        if(clusterCentroid == null || clusterCentroid.size() == 0 || allPoints == null){
            throw new RuntimeException("Not all points have been initialised.");
        }

        HashMap<Integer, List<int[]>> clusterCentroidMap = new HashMap<>();

        for(int[] point: allPoints){
            int minEcDist = calculateEuclidean(point, clusterCentroid.get(0));
            int index = 0;

            for(int i = 1; i < clusterCentroid.size(); i++){
                int currEcDist = calculateEuclidean(point, clusterCentroid.get(i));

                if(currEcDist > minEcDist){
                    currEcDist = minEcDist;
                    index = i;
                }
            }

            clusterCentroidMap.getOrDefault(index, new ArrayList<int[]>());
            clusterCentroidMap.get(index).add(point);
        }

        return clusterCentroidMap;
    }

    public void copyClusterCentroid(){
        oldClusterCentroid = clusterCentroid;
    }

    public void initNewCentroids(HashMap<Integer, List<int[]>> clusterCentroidMap){
        Set<Integer> ctc = clusterCentroidMap.keySet();
        ArrayList<int[]> newClusterCentroid = new ArrayList<int[]>();

        for(int clusterIndex: ctc){
            List<int[]> cluster = clusterCentroidMap.get(clusterIndex);
            int totX = 0;
            int totY = 0;

            for(int[] point: cluster){
                totX += point[0];
                totY += point[1];
            }

            int avgX = totX/ctc.size();
            int avgY = totY/ctc.size();

            newClusterCentroid.add(avgX, avgY);
        }

        clusterCentroid = newClusterCentroid;
    }

    public boolean isSameCentroid(){
        if(oldClusterCentroid.size() != clusterCentroid.size()){
            return false;
        }

        for(int[] centroid: oldClusterCentroid){
            boolean isExist = false;

            for(int[] compCentroid: clusterCentroid){
                if(centroi[0] == compCentroid[0] && centroid[1] == compCentroid[1]){
                    isExist = true;
                }
            }

            if(isExist == false){
                return false;
            }
        }

        return true;
    }


    public static void main(String[] args){
        Kmeans kInst = new Kmeans();
        kInst.allPoints = new int[][]{{1,2},{2,3},{3,4},{5,6},{7,8},{9,10}};
        
        kInst.initialiseCluser(3);

        while(!kInst.isSameCentroid()){
            HashMap<Integer, List<int[]>> clusterToCentroid = KInst.clusterCentroidMap();
            kInst.copyClusterCentroid();
            kInst.initNewCentroids(clusterToCentroid);
        }
        
    }
}