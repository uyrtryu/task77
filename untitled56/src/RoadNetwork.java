    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.HashSet;
    import java.util.List;
    import java.util.Set;

    public class RoadNetwork {
        private int numOfCities;
        private List<List<Integer>> adjacencyMatrix;

        public RoadNetwork(int numOfCities) {
            this.numOfCities = numOfCities;
            adjacencyMatrix = new ArrayList<>(numOfCities);
            for (int i = 0; i < numOfCities; i++) {
                adjacencyMatrix.add(new ArrayList<>(Collections.nCopies(numOfCities, 0)));
            }
        }

        public void add(int cityA, int cityB) {
            adjacencyMatrix.get(cityA).set(cityB, 1);
            adjacencyMatrix.get(cityB).set(cityA, 1);
        }

        public boolean canBlockCities(int numOfBlockedRoads, int sourceCity, int targetCity) {
            return canBlockCitiesRecursive(numOfBlockedRoads, 0, sourceCity, targetCity);
        }

        private boolean canBlockCitiesRecursive(int remainingBlockedRoads, int currentRoad, int sourceCity, int targetCity) {
            if (remainingBlockedRoads == 0) {
                Set<Integer> visited = new HashSet<>();
                dfs(sourceCity, visited);
                return !visited.contains(targetCity);
            }
            if(remainingBlockedRoads > 0 && currentRoad >= numOfCities - 2){
                return true;
            }

            for (int i = currentRoad; i < numOfCities; i++) {
                for (int j = i + 1; j < numOfCities; j++) {
                    if (adjacencyMatrix.get(i).get(j) == 1) {
                        adjacencyMatrix.get(i).set(j, 0);
                        adjacencyMatrix.get(j).set(i, 0);

                        boolean pathUnreachable = canBlockCitiesRecursive(remainingBlockedRoads - 1, i + 1, sourceCity, targetCity);

                        adjacencyMatrix.get(i).set(j, 1);
                        adjacencyMatrix.get(j).set(i, 1);

                        if (pathUnreachable) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        private void dfs(int currentCity, Set<Integer> visited) {
            visited.add(currentCity);
            for (int neighborCity = 0; neighborCity < numOfCities; neighborCity++) {
                if (adjacencyMatrix.get(currentCity).get(neighborCity) == 1 && !visited.contains(neighborCity)) {
                    dfs(neighborCity, visited);
                }
            }
        }

        public static void main(String[] args) {
            int numOfCities = 6;
            RoadNetwork roadNetwork = new RoadNetwork(numOfCities);

            roadNetwork.add(0, 1);
            roadNetwork.add(1, 2);
            roadNetwork.add(2, 3);
            roadNetwork.add(3, 4);
            roadNetwork.add(4, 5);
            roadNetwork.add(1, 5);
            roadNetwork.add(0, 4);

            int numOfBlockedRoads = 0   ;
            int sourceCity = 0;
            int targetCity = 2;

            boolean canBlockCities = roadNetwork.canBlockCities(numOfBlockedRoads, sourceCity, targetCity);

            System.out.println("Can block cities: " + canBlockCities);
        }
    }
