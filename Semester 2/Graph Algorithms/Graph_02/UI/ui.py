from graph.directedgraph import *


class UI:
    def __init__(self):
        """
        Initializes a graph and a copy whose initial value is None
        """
        self.__graph = DirectedGraph()
        self.__copy_of_current_graph = None

    @staticmethod
    def print_menu():
        print("1. Options for graph creation")
        print("2. Options for adding/modifying/deleting elements of a graph, or a graph")
        print("3. Options for listing graph information")
        print("4. Options for file work (save/load graph data)")
        print("0. Exit")

    @staticmethod
    def print_menu_for_creating_graphs():
        print("1. Create an empty graph")
        print("2. Create a graph with n vertices")
        print("3. Create a graph with n vertices and m edges")
        print("4. Create a copy of the current graph")
        print("5. Load the copy of the current session graph")
        print("0. Exit")

    def load_copied_graph(self):
        self.__graph = self.__copy_of_current_graph

    @staticmethod
    def print_menu_for_adding_and_deleting():
        print("1. Add a new vertex")
        print("2. Add a new edge")
        print("3. Remove a vertex")
        print("4. Remove an edge")
        print("5. Change the cost of an edge")
        print("6. Check if a vertex already exists")
        print("7. Check if an edge already exists")
        print("0. Exit")

    @staticmethod
    def print_menu_for_listing():
        print("1. Print the cost of an edge")
        print("2. Print the in degree (number of inbound neighbours) of a vertex")
        print("3. Print the out degree (number of outbound neighbours) of a vertex")
        print("4. Print the number of vertices")
        print("5. Print the number of edges")
        print("6. List all the edges")
        print("7. List all the vertices")
        print("8. List the outbound neighbours of a vertex")
        print("9. List the inbound neighbours or a vertex")
        print("10. Print the last saved copy of a graph")
        print("11. BFS algorithm")
        print("0. Exit")

    @staticmethod
    def print_menu_for_file_work():
        print("1. Load graph data information from a file")
        print("2. Save the current graph into a file")
        print("0. Exit")

    def empty_graph(self):
        '''
        Creates an empty graph, no vertices, no edges
        :return:
        '''
        self.__graph = DirectedGraph()
        print("Done!")

    def create_graph_with_n_vertices(self):
        """
        Creates a graph wiht n vertices and no edges
        :return:
        """
        number_of_vertices = int(input("How many vertices do you need: "))
        if number_of_vertices > 0:
            try:
                self.__graph = DirectedGraph(number_of_vertices)
                print("Done!")
            except Exception as e:
                print(e)
        else:
            print("Invalid number of vertices.")

    def create_graph_with_n_vertices_m_edges(self):
        """
        Creates a graph with n vertices and m edges
        :return:
        """
        number_of_vertices = int(input("How many vertices do you need: "))
        number_of_edges = int(input("How many edges do you need: "))
        if number_of_vertices > 0 and number_of_edges >= 0 and number_of_edges <= number_of_vertices * (number_of_vertices-1):
            try:
                self.__graph = DirectedGraph(number_of_vertices, number_of_edges)
                print("Done!")
            except Exception as e:
                print(e)
                self.__graph = DirectedGraph()
        else:
            print("Invalid number of vertices and/or edges.")

    def add_vertex(self):
        new_vertex = int(input("Type the vertex you wish to add: "))
        try:
            self.__graph.add_vertex(new_vertex)
            print("Done!")
        except Exception as e:
            print(e)

    def add_edge(self):
        first_vertex = int(input("Type the first vertex of the edge: "))
        second_vertex = int(input("Type the second vertex of the edge: "))
        new_edge_cost = int(input("Type the cost of the edge: "))
        try:
            self.__graph.add_edge(first_vertex, second_vertex, new_edge_cost)
            print("Done!")
        except Exception as e:
            print(e)

    def remove_vertex(self):
        vertex_to_be_deleted = int(input("Type the vertex you wish to remove: "))
        try:
            self.__graph.remove_vertex(vertex_to_be_deleted)
            print("Done!")
        except Exception as e:
            print(e)

    def remove_edge(self):
        first_vertex = int(input("Type the first vertex of the edge: "))
        second_vertex = int(input("Type the second vertex of the edge: "))
        try:
            self.__graph.remove_edge(first_vertex, second_vertex)
            print("Done!")
        except Exception as e:
            print(e)

    def change_cost_of_edge(self):
        first_vertex = int(input("Type the first vertex of the edge: "))
        second_vertex = int(input("Type the second vertex of the edge: "))
        new_cost = int(input("Type the cost of the edge: "))
        try:
            self.__graph.set_cost_of_edge(first_vertex, second_vertex, new_cost)
            print("Done!")
        except Exception as e:
            print(e)

    def print_cost_of_edge(self):
        first_vertex = int(input("Type the first vertex of the edge: "))
        second_vertex = int(input("Type the second vertex of the edge: "))
        try:
            print("The cost of the given edge is ", self.__graph.get_cost_of_edge(first_vertex, second_vertex))
        except Exception as e:
            print(e)

    def in_degree(self):
        vertex_to_find_inbound_neighbours_of = int(input("Type the vertex for which you wish to find the in degree: "))
        try:
            print(self.__graph.inbound_degree_of_given_vertex(vertex_to_find_inbound_neighbours_of))
        except Exception as e:
            print(e)

    def out_degree(self):
        vertex_to_find_outbound_neighbours_of = int(input("Type the vertex for which you wish to find the out degree: "))
        try:
            print(self.__graph.outbound_degree_of_given_vertex(vertex_to_find_outbound_neighbours_of))
        except Exception as e:
            print(e)

    def count_number_of_vertices(self):
        print("There are a total of ", self.__graph.count_number_of_vertices(), " vertices.")

    def count_number_of_edges(self):
        print("There are a total of ", self.__graph.count_number_of_edges(), " edges.")

    def is_vertex(self):
        vertex = int(input("Type the vertex you wish to check: "))
        try:
            if self.__graph.is_vertex(vertex):
                print("The given vertex belongs to the graph.")
            else:
                print("The given vertex does not belong to the graph.")
        except Exception as e:
            print(e)

    def is_edge(self):
        first_vertex = int(input("Type the first vertex of the edge: "))
        second_vertex = int(input("Type the second vertex of the edge: "))
        try:
            if self.__graph.is_edge(first_vertex, second_vertex):
                print("The edge does exist.")
            else:
                print("The edge doesn't exist.")
        except Exception as e:
            print(e)

    def print_vertex_list(self):
        for vertex in self.__graph.vertices_iterator():
            print(vertex, end =" ")
        print()

    def print_outbound_neighbours(self):
        vertex_to_find_neighbours_of = int(input("Type the vertex you wish to find neighbours for: "))
        try:
            given_vertex_neighbours_exist = False
            for vertex in self.__graph.outbound_vertices_iterator(vertex_to_find_neighbours_of):
                print(vertex, end = " ")
                given_vertex_neighbours_exist = True
            if not given_vertex_neighbours_exist:
                print("Vertex ", vertex_to_find_neighbours_of, " has no neighbours.")
            else:
                print()
        except Exception as e:
            print(e)

    def print_inbound_neighbours(self):
        vertex_to_find_inbound_neighbours_of = int(input("Type the vertex you wish to find inbound neighbours for: "))
        try:
            vertex_inbound_neighbours_exist = False
            for vertex in self.__graph.inbound_vertices_iterator(vertex_to_find_inbound_neighbours_of):
                print(vertex, end = " ")
                vertex_inbound_neighbours_exist = True
            if not vertex_inbound_neighbours_exist:
                print("Vertex ", vertex_to_find_inbound_neighbours_of, " has no inbound neighbours.")
            else:
                print()
        except Exception as e:
            print(e)

    def print_edges(self):
        edges_exist = False
        for first_vertex_second_vertex_and_cost in self.__graph.edges_iterator():
            print("Vertices", first_vertex_second_vertex_and_cost[0], first_vertex_second_vertex_and_cost[1], "and cost", first_vertex_second_vertex_and_cost[2])
            edges_exist = True
        if not edges_exist:
            print("No edges in the graph.")

    def copy_current_graph(self):
        self.__copy_of_current_graph = self.__graph.make_copy_of_current_graph()

    def print_vertices_and_edges_copied_graph(self):
        print("The vertices are:")
        for vertex in self.__copy_of_current_graph.vertices_iterator():
            print(vertex, end =" ")
        print("\nThe edges are:")
        edges_exist = False
        for first_vertex_second_vertex_and_cost in self.__copy_of_current_graph.edges_iterator():
            print("Vertices", first_vertex_second_vertex_and_cost[0], first_vertex_second_vertex_and_cost[1],
                  "and cost", first_vertex_second_vertex_and_cost[2])
            edges_exist = True
        if not edges_exist:
            print("No edges in the graph.")

    def bfs_algorithm(self):
        start_vertex = -10000000
        end_vertex = -100000000
        while not self.__graph.is_vertex(start_vertex):
            start_vertex = int(input("Starting vertex: "))
        while not self.__graph.is_vertex(end_vertex):
            end_vertex = int(input("Ending vertex: "))
        bfs = self.__graph.is_reachable(start_vertex, end_vertex)
        if bfs:
            print(end_vertex, "is reachable from", start_vertex)
            print("The length is:", len(bfs)-1)
            print(bfs)
        else:
            print(end_vertex, "not reachable from", start_vertex)

    def read_from_file(self):
        file_path = input("Type the file from which you wish to read: ")
        reading_type = input("Type of reading normal/with isolated nodes (1/2)?")
        try:
            self.__graph = read_from_file(file_path, reading_type)
            print("Done!")
        except Exception as e:
            print(e)

    def write_in_file(self):
        file_path = input("Type the file you wish to write to: ")
        writing_type = input("Type of writing list of adjacency normal/with isolated nodes (1/2)?")
        try:
            write_in_file(file_path, self.__graph, writing_type)
            print("Done!")
        except Exception as e:
            print(e)

    def start(self):
        commands_list_for_creating = {"1": self.empty_graph, "2": self.create_graph_with_n_vertices,
                                      "3": self.create_graph_with_n_vertices_m_edges, "4": self.copy_current_graph,
                                      "5": self.load_copied_graph}

        commands_list_for_adding_modifying_deleting = {"1": self.add_vertex, "2": self.add_edge,
                                                       "3": self.remove_vertex, "4": self.remove_edge,
                                                       "5": self.change_cost_of_edge, "6": self.is_vertex,
                                                       "7": self.is_edge}

        commands_list_for_listing = {"1": self.print_cost_of_edge, "2": self.in_degree, "3": self.out_degree,
                                     "4": self.count_number_of_vertices, "5": self.count_number_of_edges,
                                     "6": self.print_edges, "7": self.print_vertex_list, "8": self.print_outbound_neighbours,
                                     "9": self.print_inbound_neighbours, "10": self.print_vertices_and_edges_copied_graph,
                                     "11": self.bfs_algorithm}

        commands_list_for_file_work = {"1": self.read_from_file, "2": self.write_in_file}

        print("\n ☆ Welcome to Graph Simulator! ☆ \n")

        while True:
            self.print_menu()

            menu_choice = input(">> ")
            if menu_choice == "1":
                self.print_menu_for_creating_graphs()
                chosen_command = input(">> ")
                if chosen_command in commands_list_for_creating:
                    commands_list_for_creating[chosen_command]()
                elif chosen_command == "0":
                    break
                else:
                    print("Invalid choice")
            elif menu_choice == "2":
                self.print_menu_for_adding_and_deleting()
                chosen_command = input(">> ")
                if chosen_command in commands_list_for_adding_modifying_deleting:
                    commands_list_for_adding_modifying_deleting[chosen_command]()
                elif chosen_command == "0":
                    break
                else:
                    print("Invalid choice")
            elif menu_choice == "3":
                self.print_menu_for_listing()
                chosen_command = input(">> ")
                if chosen_command in commands_list_for_listing:
                    commands_list_for_listing[chosen_command]()
                elif chosen_command == "0":
                    break
                else:
                    print("Invalid choice")
            elif menu_choice == "4":
                self.print_menu_for_file_work()
                chosen_command = input(">> ")
                if chosen_command in commands_list_for_file_work:
                    commands_list_for_file_work[chosen_command]()
                elif chosen_command == "0":
                    break
                else:
                    print("Invalid choice")
            elif menu_choice == "0":
                break
            else:
                print("Invalid choice.")