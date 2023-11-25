import copy
from random import randrange
from copy import deepcopy
from graph.exceptions import VertexError, EdgeError
from graph.undirectedgraph import UndirectedGraph
#from queue import Queue


class DirectedGraph:
    def __init__(self, number_of_vertices = 0, number_of_edges = 0):
        """
        Initializes the graph structure, depending on the choice of the user (Empty, with nr. of vertices, with nr. of
        vertices and edges
        :param number_of_vertices: integer, base value = 0
        :param number_of_edges: integer, base value = 0
        """
        self._vertices = set()
        self._outbound_neighbours = dict()
        self._inbound_neighbours = dict()
        self._cost = dict()

        for vertex in range(number_of_vertices):
            self.add_vertex(vertex)

        for edge in range(number_of_edges):
            first_vertex = randrange(number_of_vertices)
            second_vertex = randrange(number_of_vertices)
            while self.is_edge(first_vertex, second_vertex):
                first_vertex = randrange(number_of_vertices)
                second_vertex = randrange(number_of_vertices)
            self.add_edge(first_vertex, second_vertex, randrange(1000000))

    def floyd_warshall(self, start, end):
        #add to be able to work with negative costs/wrong  negative
        # infinity will depict the lack of a lowest-cost path between two vertices
        infinity = 9999999
        number_of_vertices = self.count_number_of_vertices()
        # lowest_walk[][] will be the matrix containing the previously passed vertices in the case a lowest-cost path
        # exists between two vertices
        previous_vertex_matrix = [[-1 for first_vertex in range(number_of_vertices)] for second_vertex in range(number_of_vertices)]
        # dist_matrix[][] will contain the lowest cost walk between two vertices (one represented by line index, the
        # other by the column index.
        dist_matrix = [[infinity for first_vertex in range(number_of_vertices)] for second_vertex in range(number_of_vertices)]
        for i in range(number_of_vertices):
            for j in range(number_of_vertices):
                if (i, j) in self._cost:
                    dist_matrix[i][j] = self._cost[(i, j)]
                    previous_vertex_matrix[i][j] = i
                if i == j:
                    dist_matrix[i][j] = 0

        print("Following matrix shows the shortest distances between every pair of vertices\n")
        print("\nIteration: ", 0)
        for i in range(number_of_vertices):
            for j in range(number_of_vertices):
                if dist_matrix[i][j] == infinity:
                    print("INF", end=" ")
                else:
                    print(dist_matrix[i][j], end=" ")
            print(" ")

        print("\nThe previous vertices matrix is:")
        for i in range(number_of_vertices):
            for j in range(number_of_vertices):
                print(previous_vertex_matrix[i][j], end=" ")
            print(" ")

        # following is an algorithm to print the contents of the matrix is an easy to read way. the lack of a
        # lowest cost path is marked by "INF"
        for k in range(number_of_vertices):
            # each vertex will be seen as an intermediate vertex in the path between any two vertices (represented by k)
            for i in range(number_of_vertices):
                # Pick all vertices as destination for the
                # above picked source
                for j in range(number_of_vertices):
                    # if k is on the path between i and j, and the cost would be lower than the already existing one
                    # the value of dist_matrix[i][j] will be updated to the new cost
                    previous = copy.deepcopy(dist_matrix[i][j])
                    if dist_matrix[i][k] + dist_matrix[k][j] >= 0:
                        dist_matrix[i][j] = min(dist_matrix[i][j], dist_matrix[i][k] + dist_matrix[k][j])
                    # if the value of the lowest cost walk was changed, then the previous vertex in the path will be
                    # changed to k
                    if previous != dist_matrix[i][j] and dist_matrix[i][k] + dist_matrix[k][j] >= 0:
                        previous_vertex_matrix[i][j] = k

            print("\nIteration: ", k+1)
            for i in range(number_of_vertices):
                for j in range(number_of_vertices):
                    if dist_matrix[i][j] == infinity:
                        print("INF", end=" ")
                    else:
                        print(dist_matrix[i][j], end=" ")
                print(" ")

            # algorithm to print the matrix of all previous vertices in the lowest cost walks for all 2 vertices
            print("\nThe previous vertices matrix is:")
            for i in range(number_of_vertices):
                for j in range(number_of_vertices):
                    print(previous_vertex_matrix[i][j], end=" ")
                print(" ")

        # return the distance matrix and previous vertex matrix
        return dist_matrix, previous_vertex_matrix

    def is_reachable(self, destination_vertex, start_vertex):
        """
        performs a backwards breadth-first search to check if the start vertex is reachable from the destination vertex
        and returns the path is true
        :param destination_vertex: integer
        :param start_vertex: integer
        :return:
        """
        # Mark all the vertices as not visited
        visited = [None] * (self.count_number_of_vertices())

        # Create a queue for BFS
        queue = []

        # start
        queue.append(start_vertex)

        found = False

        while queue:
            # Dequeue a vertex from queue
            current_vertex = queue.pop(0)

            # If this adjacent node is the destination node,
            # then return true
            if current_vertex == destination_vertex:
                found = True
                break

            #  Else, continue to do BFS
            for vertex in self._inbound_neighbours[current_vertex]:
                if visited[vertex] is None:
                    queue.append(vertex)
                    visited[vertex] = current_vertex
        if not found:
            return False

        path = []
        current_vertex = destination_vertex
        while current_vertex is not None and current_vertex != start_vertex:
            path.append(current_vertex)
            current_vertex = visited[current_vertex]
        if len(path) > 0:
            path.append(start_vertex)
        return path

    def is_vertex(self, vertex):
        """
        Returns True if vertex belongs to the graph.
        :param vertex: integer
        :return:
        """
        if vertex in self._vertices:
            return True
        return False

    def is_edge(self, first_vertex, second_vertex):
        """
        Returns True if the edge from first_vertex to second_vertex belongs to the graph.
        :param first_vertex: integer
        :param second_vertex: integer
        :return:
        """
        if first_vertex in self._outbound_neighbours and second_vertex in self._outbound_neighbours[first_vertex]:
            return True
        return False

    def inbound_degree_of_given_vertex(self, vertex):
        """
        Returns the number of edges with the endpoint in the given vertex.
        :param vertex: integer
        :return: the number of the inbound neighbours of the vertex, if it exists
        """
        if vertex not in self._inbound_neighbours:
            raise VertexError("The specified vertex does not exist.")
        return len(self._inbound_neighbours[vertex])

    def outbound_degree_of_given_vertex(self, vertex):
        """
        Returns the number of edges with the start point in the given vertex.
        :param vertex:
        :return: the number of the outbound neighbours of the given vertex, if it exists
        """
        if vertex not in self._outbound_neighbours:
            raise VertexError("The specified vertex does not exist.")
        return len(self._outbound_neighbours[vertex])

    def get_cost_of_edge(self, first_vertex, second_vertex):
        """
        Returns the cost of an edge if it exists.
        :param first_vertex: integer
        :param second_vertex: integer
        :return: the cost of the edge, if it exists
        """
        if (first_vertex, second_vertex) not in self._cost:
            raise EdgeError("The specified edge does not exist.")
        return self._cost[(first_vertex, second_vertex)]

    def set_cost_of_edge(self, first_vertex, second_vertex, new_cost):
        """
        Sets the cost of an edge in the graph if it exists.
        :param first_vertex: integet
        :param second_vertex: integer
        :param new_cost: integer
        :return:
        """
        if (first_vertex, second_vertex) not in self._cost:
            raise EdgeError("The specified edge does not exist.")
        self._cost[(first_vertex, second_vertex)] = new_cost

    def count_number_of_vertices(self):
        """
        Returns the number of vertices in the graph.
        :return: number of vertices
        """
        return len(self._vertices)

    def count_number_of_edges(self):
        """
        Returns the number of edges in the graph.
        :return: the number of edges, which is the same as the elements of the cost array
        """
        return len(self._cost)

    def add_vertex(self, new_vertex):
        """
        Adds a new vertex to the graph.
        :param new_vertex: integer
        :return:
        """
        if self.is_vertex(new_vertex):
            raise VertexError("Cannot add a vertex which already exists.")
        self._vertices.add(new_vertex)
        self._outbound_neighbours[new_vertex] = set()
        self._inbound_neighbours[new_vertex] = set()

    def add_vertex_isolate(self, new_vertex):
        """
        Adds a new vertex to the graph.
        :param new_vertex: integer
        :return:
        """
        if self.is_vertex(new_vertex):
            raise VertexError("Cannot add a vertex which already exists.")
        self._vertices.add(new_vertex)
        self._outbound_neighbours[new_vertex] = set()
        self._inbound_neighbours[new_vertex] = set()

    def add_edge(self, first_vertex, second_vertex, new_edge_cost = 0):
        """
        Adds a new edge to the graph.
        :param first_vertex: integer
        :param second_vertex: integer
        :param new_edge_cost: integer
        :return:
        """
        if self.is_edge(first_vertex, second_vertex):
            raise EdgeError("The specified edge already exists")
        if not self.is_vertex(first_vertex) or not self.is_vertex(second_vertex):
            raise EdgeError("The vertices on the edge do not exist.")
        self._outbound_neighbours[first_vertex].add(second_vertex)
        self._inbound_neighbours[second_vertex].add(first_vertex)
        self._cost[(first_vertex, second_vertex)] = new_edge_cost

    def add_edge_isolate(self, first_vertex, second_vertex, new_edge_cost = 0):
        """
        Adds a new edge to the graph.
        :param first_vertex: integer
        :param second_vertex: integer
        :param new_edge_cost: integer
        :return:
        """
        if self.is_edge(first_vertex, second_vertex):
            raise EdgeError("The specified edge already exists")
        self._outbound_neighbours[first_vertex].add(second_vertex)
        self._inbound_neighbours[second_vertex].add(first_vertex)
        self._cost[(first_vertex, second_vertex)] = new_edge_cost

    def remove_edge(self, first_vertex, second_vertex):
        """
        Removes an edge from the graph.
        :param first_vertex: integer
        :param second_vertex: integer
        :return:
        """
        if not self.is_edge(first_vertex, second_vertex):
            raise EdgeError("The specified edge does not exist.")
        del self._cost[(first_vertex, second_vertex)]
        self._outbound_neighbours[first_vertex].remove(second_vertex)
        self._inbound_neighbours[second_vertex].remove(first_vertex)

    def remove_vertex(self, vertex_to_delete):
        """
        Removes a vertex from the graph.
        :param vertex_to_delete: integer
        :return:
        """

        if not self.is_vertex(vertex_to_delete):
            raise VertexError("Cannot remove a vertex which doesn't exist.")
        elements_to_remove = []
        for outbound_vertex in self._outbound_neighbours[vertex_to_delete]:
            elements_to_remove.append(outbound_vertex)
        for vertex in elements_to_remove:
            self.remove_edge(vertex_to_delete, vertex)
        elements_to_remove.clear()
        for inbound_vertex in self._inbound_neighbours[vertex_to_delete]:
            elements_to_remove.append(inbound_vertex)
        for vertex in elements_to_remove:
            self.remove_edge(vertex, vertex_to_delete)
        del self._outbound_neighbours[vertex_to_delete]
        del self._inbound_neighbours[vertex_to_delete]
        #try to adapt the program to not have to work with a vertices dictionary!!!
        self._vertices.remove(vertex_to_delete)

        # if not self.is_vertex(vertex_to_delete):
        #     raise VertexError("Cannot remove a vertex which doesn't exist.")
        # for vertex in self._outbound_neighbours[vertex_to_delete]:
        #     print("vertex:", vertex)
        #     print("out:", vertex_to_delete, vertex)
        #     self.remove_edge(vertex_to_delete, vertex)
        #     if self.is_edge(vertex, vertex_to_delete):
        #         print("in:", vertex, vertex_to_delete)
        #         self.remove_edge(vertex, vertex_to_delete)
        #         self._inbound_neighbours[vertex_to_delete].remove(vertex)
        #         self._outbound_neighbours[vertex].remove(vertex_to_delete)
        #     self._outbound_neighbours[vertex_to_delete].remove(vertex)
        #     self._inbound_neighbours[vertex].remove(vertex_to_delete)
        #     self._vertices.remove(vertex_to_delete)

    def vertices_iterator(self):
        """
        Returns the elements from the vertices set.
        :return: an iterable structure containing all the vertices
        """
        for vertex in self._vertices:
            yield vertex

    def outbound_vertices_iterator(self, vertex):
        """
        Returns the elements of the set of (outbound) neighbours of a vertex.
        :param vertex: integer
        :return: an iterable structure containing all the outbound neighbours of the given vertex
        """
        if not self.is_vertex(vertex):
            raise VertexError("Invalid vertex.")
        for neighbour in self._outbound_neighbours[vertex]:
            yield neighbour

    def inbound_vertices_iterator(self, vertex):
        """
        Returns the elements of the set of (inbound) neighbours of a vertex.
        :param vertex: integer
        :return: an iterable structure containing all the neighbours of the given vertex
        """
        if not self.is_vertex(vertex):
            raise VertexError("Invalid vertex.")
        for neighbour in self._inbound_neighbours[vertex]:
            yield neighbour

    def edges_iterator(self):
        """
        Returns the elements of each edge in the edge dictionary.
        :return: an iterable structure containing all the vertices and the cost of an edge
        """
        for key, value in self._cost.items():
            yield key[0], key[1], value

    def make_copy_of_current_graph(self):
        """
        Returns a copy of the current graph.
        :return: a deep copy of the currently running graph object
        """
        return deepcopy(self)


def read_from_file(file_path, reading_type):
    """
    Reads a new graph from a text file
    :param file_path: string
    :param reading_type: integer
    :return:
    """
    file = open(file_path, "r")
    if reading_type == "1":
        number_of_vertices, number_of_edges = map(int, file.readline().split())
        initializing_graph = DirectedGraph(number_of_vertices)
        for edge in range(number_of_edges):
            first_vertex, second_vertex, edge_cost = map(int, file.readline().split())
            initializing_graph.add_edge(first_vertex, second_vertex, edge_cost)
        file.close()
        return initializing_graph
    elif reading_type == "2":
        initializing_graph = DirectedGraph()
        line = file.readline().strip()
        line = line.split()
        while len(line) > 0:
            if len(line) == 1:
                first_vertex = int(line[0])
                initializing_graph.add_vertex_isolate(first_vertex)
            else:
                first_vertex = int(line[0])
                second_vertex = int(line[1])
                edge_cost = int(line[2])
                if not initializing_graph.is_vertex(first_vertex):
                    initializing_graph.add_vertex_isolate(first_vertex)
                if not initializing_graph.is_vertex(second_vertex):
                    initializing_graph.add_vertex_isolate(second_vertex)
                initializing_graph.add_edge_isolate(first_vertex, second_vertex, edge_cost)
            line = file.readline().strip()
            line = line.split()
        file.close()
        return initializing_graph


def write_in_file(file_path, graph_to_be_saved_in_file, writing_type):
    """
    Saves the current graph to a text file
    :param file_path: string
    :param writing_type: integer
    :param graph_to_be_saved_in_file: graph object
    :return:
    """
    file = open(file_path, "w")
    if graph_to_be_saved_in_file.count_number_of_edges() == 0 and graph_to_be_saved_in_file.count_number_of_vertices() == 0:
        file.write("Error!" + '\n')
    elif writing_type == "1":
        file.write(str(graph_to_be_saved_in_file.count_number_of_vertices()) + " " + str(graph_to_be_saved_in_file.count_number_of_edges()) + '\n')
        for vertex in graph_to_be_saved_in_file.vertices_iterator():
            for neighbour_vertex in graph_to_be_saved_in_file.outbound_vertices_iterator(vertex):
                file.write(str(vertex) + " " + str(neighbour_vertex) + " " + str(graph_to_be_saved_in_file.get_cost_of_edge(vertex, neighbour_vertex)) + '\n')
        file.close()
    elif writing_type == "2":
        for vertex in graph_to_be_saved_in_file.vertices_iterator():
            generator_to_check_outbound = graph_to_be_saved_in_file.outbound_vertices_iterator(vertex)
            length_to_check_outbound = len(list(generator_to_check_outbound))
            generator_to_check_inbound = graph_to_be_saved_in_file.inbound_vertices_iterator(vertex)
            length_to_check_inbound = len(list(generator_to_check_inbound))
            if length_to_check_outbound == 0 and length_to_check_inbound == 0:
                file.write(str(vertex) + '\n')
            else:
                for neighbour_vertex in graph_to_be_saved_in_file.outbound_vertices_iterator(vertex):
                    file.write(str(vertex) + " " + str(neighbour_vertex) + " " + str(graph_to_be_saved_in_file.get_cost_of_edge(vertex, neighbour_vertex)) + '\n')
        file.close()


def generate_random_graph(number_of_vertices, number_of_edges):
    """
    Creates a new, random, graph, with no vertices, nor edges specified
    :param number_of_vertices:
    :param number_of_edges:
    :return:
    """
    random_generated_graph = DirectedGraph()
    for vertex in range(number_of_vertices):
        random_generated_graph.add_vertex(vertex)
    for edge in range(number_of_edges):
        first_vertex = randrange(number_of_vertices)
        second_vertex = randrange(number_of_vertices)
        while random_generated_graph.is_edge(first_vertex, second_vertex):
            first_vertex = randrange(number_of_vertices)
            second_vertex = randrange(number_of_vertices)
        random_generated_graph.add_edge(first_vertex, second_vertex, randrange(1000000))
