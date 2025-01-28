    import java.util.ArrayList;
    import java.util.List;

    class Graph {
        private List<List<Integer>> edges;
        private List<Integer> nodes;

        Graph(int nodeCount) {
            this.edges = new ArrayList<>(nodeCount);
            this.nodes = new ArrayList<>();

            for (int i = 0; i < nodeCount; i++) {
                this.edges.add(new ArrayList<>());
                this.nodes.add(i);
            }
            java.util.Collections.shuffle(nodes);

        }

        void addEdge(int nodeA, int nodeB) {
            this.edges.get(nodeA).add(nodeB);
        }

        List<Integer> neighboursOf(int node) {
            return this.edges.get(node);
        }

        List<Integer> getNodes() {
            return nodes;
        }

        List<List<Integer>> getEdges() {
            return this.edges;
        }

        int size() {
            return this.edges.size();
        }

        Boolean hasEdge(int firstNode, int secondNode) {
            return edges.get(firstNode).contains(secondNode);
        }
    }
