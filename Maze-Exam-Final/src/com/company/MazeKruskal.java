package com.company;

/**
 * Mazekruskal klassen extender Maze klassen.
 * Her har proevet at starte paa at implementere logikken med DisjointSet, men er ikke nået videre.
 */
public class MazeKruskal extends Maze {
    // https://en.wikipedia.org/wiki/Disjoint-set_data_structure
    public class DisjointSet {
        private int[] set;
        private int[] sizes;
        private int size;

        public DisjointSet(int size) {
            this.set = new int[size];
            for (int i =0; i < size; i ++) {this.set[i] = i;}
            this.sizes = new int[size];
            for (int i = 0; i< size; i++){this.sizes[i] = 1;}
            this.size = size;
        }
        public int find (int item){
            int root = item;
            // find the root
            while(set[root]!= root){
                root = set[root];
            }
            // shorten paths
            int current = item;
            while (set[current] != root){
                set[current] = root;
            }

            return root;
        }
        public int join(int item1, int item2){
            int group1 = find(item1);
            int group2 = find(item2);
            --size;
            if (sizes[group1] > sizes[group2]){
                set[group2] = group1;
                sizes[group1] += sizes[group2];
                return group1;
            }
            else{
                set[group1] = group2;
                sizes[group2] += sizes[group1];
                return group2;
            }


        }
    }

    /**
     *
     */
    public void generateKruskalsMaze(){
        //Makes grid with walls

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        /**
         https://en.wikipedia.org/wiki/Kruskal%27s_algorithm

         create a graph F (a set of trees), where each vertex in the graph is a separate tree
         create a set S containing all the edges in the graph

         while S is nonempty and F is not yet spanning

         remove an edge with minimum weight from S
         if the removed edge connects two different trees then add it to the forest F,
         combining two trees into a single tree

         At the termination of the algorithm, the forest forms a minimum spanning forest
         of the graph. If the graph is connected, the forest has a single component and
         forms a minimum spanning tree

         grid of cells = vertex

         walls = edges
         */


    }
}
