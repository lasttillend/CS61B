package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.MyTrieSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private KDTree kdTree;
    private Map<Point, Long> idMap;
    private MyTrieSet trieSet;
    private Map<String, List<Node>> cleanedNameToNodes;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        List<Point> pts = new LinkedList<>();
        idMap = new HashMap<>();

        trieSet = new MyTrieSet();
        cleanedNameToNodes = new HashMap<>();
        List<Node> nodesList;

        for (Node node : nodes) {
            if (node.name() != null) {
                String cleanedName = cleanString(node.name());
                trieSet.add(cleanedName);
                if (!cleanedNameToNodes.containsKey(cleanedName)) {
                    cleanedNameToNodes.put(cleanedName, new LinkedList<>());
                }
                nodesList = cleanedNameToNodes.get(cleanedName);
                nodesList.add(node);
                cleanedNameToNodes.put(cleanedName, nodesList);
            }
            if (!neighbors(node.id()).isEmpty()) {
                Point pt = new Point(node.lon(), node.lat());
                pts.add(pt);
                idMap.put(pt, node.id());
            }
        }
        kdTree = new KDTree(pts);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point closestPoint = kdTree.nearest(lon, lat);
        return idMap.get(closestPoint);
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String cleanedQuery = cleanString(prefix);
        List<String> matchedNames = trieSet.keysWithPrefix(cleanedQuery);
        Set<String> locationsSet = new HashSet<>();

        for (String name : matchedNames) {
            for (Node node : cleanedNameToNodes.get(name)) {
                locationsSet.add(node.name());
            }
        }
        return new LinkedList<>(locationsSet);
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanedLocationName = cleanString(locationName);
        List<Map<String, Object>> locationsMap = new LinkedList<>();
        if (cleanedNameToNodes.containsKey(cleanedLocationName)) {
            for (Node node : cleanedNameToNodes.get(cleanedLocationName)) {
                Map<String, Object> locationInfo = new HashMap<>();
                locationInfo.put("lat", node.lat());
                locationInfo.put("lon", node.lon());
                locationInfo.put("name", node.name());
                locationInfo.put("id", node.id());
                locationsMap.add(locationInfo);
            }
        }
        return locationsMap;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
