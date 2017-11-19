from sys import stdin, stderr


#import numpy as n


# Capacities is the original capacity-matrix, which contains n x n elemensts (where n is the amount of nodes)
# start_room is an array with the indexes to the nodes which corresponds to the start rooms.
# Exits is an array with the indexes to the nodes which corresponds to the exits.

def isolated_paths_count(capacities, start_rooms, exits):
	# You can use the method find_flow_path to simplify the problem
	# SKRIV DIN KODE HER
#	print("ISO!")
#	numberOfPaths = 0
#	flow_matrix = [[0 for number in line] for line in capacities]

	capacity_matrix, starts, stops = add_supers(capacities)
#	print(n.matrix(capacity_matrix))
	node_capacities = [float("inf") if i == 0 or i == len(capacity_matrix)-1 else 1 for i in range(len(capacity_matrix))]
#	print(node_capacities)
	split_capacities = split_nodes2(capacity_matrix, node_capacities)
#	split_capacities = split_nodes(capacity_matrix, node_capacities)
	flow_matrix = [[0 for number in line] for line in split_capacities]

	paths, number_of_paths = check_paths(0, len(split_capacities)-1, flow_matrix, split_capacities)
#	print(paths)

#	for i in range(len(flow_matrix)):
#		print(flow_matrix[i])

	# test1 = split_nodes(capacity_matrix, node_capacities)
	# test2 = split_nodes2(capacity_matrix, node_capacities)
	# ln = len(test1)
	# for i in range(ln):
	# 	for j in range(ln):
	# 		if test1[i][j] != test2[i][j]:
	# 			print("FALSE")

	# for i in range(len(start_rooms)):
	# 	print("i: {0}".format(i))
	# 	for j in range(len(exits)):
	# 		paths.append(find_flow_path(start_rooms[i], exits[j], flow_matrix, split_capacities))
	# 		print(paths)
	# update_flow(flow_matrix, paths[1])
	# print(n.matrix(flow_matrix))


	return number_of_paths


def check_paths(start, stop, flow_matrix, capacity_matrix):
	number_of_paths = 0
	path = []
	paths = []
	while path is not None:
		path = find_flow_path(start, stop, flow_matrix, capacity_matrix)
		if path is None:
			break
		update_flow(flow_matrix, path)
		paths.append(path)
		number_of_paths += 1
	return paths, number_of_paths




def update_flow(flow_matrix, path):
	for i in range(len(path)-1):
		flow_matrix[path[i]][path[i+1]] += 1
		flow_matrix[path[i+1]][path[i]] -= 1

def reverse_flow(flow_matrix, path):
	for i in range(len(path)-1):
		flow_matrix[path[i]][path[i+1]] -= 1

def add_supers(capacities):
	modified_start_rooms = [i + 1 for i in start_rooms]
	modified_exits = [i + 1 for i in exits]
	super_source = [float("inf") if i in modified_start_rooms else 0 for i in range(len(capacities) + 2)]
	super_sink = [0 for i in range(len(capacities) + 2)]
	capacity_matrix = [[number for number in line] for line in capacities]
	capacity_matrix.insert(0, super_source)
	capacity_matrix.append(super_sink)
	for i in range(len(capacities)):
		capacity_matrix[i + 1].insert(0, 0)
		if i in exits:
			capacity_matrix[i + 1].append(float("inf"))
		else:
			capacity_matrix[i + 1].append(0)
	return capacity_matrix, modified_start_rooms, modified_exits



def split_nodes2(capacity_matrix, node_capacities):
#	print("Split2!")
	length = len(capacity_matrix)
	matrise = [[0 for j in range(2*length)] for i in range(length*2)]
#	print(n.matrix(capacity_matrix))
#	print(n.matrix(matrise))
	for i in range(length*2):
		for j in range(length*2):
			if not i % 2:	# If i is divisible by 2 (doing this on every other row of the matrix)
				if i == j:	# We set the capacity of the edge from i_in to i_out, where i_out is i_in + 1
					matrise[i][j+1] = node_capacities[i//2]
			else:
				if not j % 2:
					matrise[i][j] = capacity_matrix[i//2][j//2]
#	for i in range(len(matrise)):
#		print(matrise[i])
	return matrise



def split_nodes(capacity_matrix, node_capacities):
	struktin = []
	for i in range(len(capacity_matrix) * 2):

		#		for l in range(len(struktin)):
		#			print(struktin[l])
		#		print()

		struktin.append([])

		for j in range(len(capacity_matrix[i // 2]) * 2):
			if i == 0:  # We need to give a capacity to edge 0-1
				struktin[i].append(node_capacities[i]) if j == 1 else struktin[i].append(0)

			elif not i % 2: # for all the other in the adjacancy-matrix, we must make sure it sets a capacity of 1 from
							# the in-node to the out-node
				struktin[i].append(node_capacities[i//2]) if j == i + 1 else struktin[i].append(0)

			elif i % 2: # For all odd numbered nodes in struktin we must have the outbound edges from the nodes in
						# the capacity matrix
				if i == 1:  # For all the outbound edges from SuperSource to Sources, we must have inf capacity
					struktin[i].append(0) if j % 2 else struktin[i].append(capacity_matrix[i // 2][j // 2])

				else:
					struktin[i].append(0) if j % 2 else struktin[i].append(capacity_matrix[i // 2][j // 2])
	struktin[-2][-1] = float("inf")
#	for i in range(len(struktin)):
#		print(struktin[i])
	return struktin




# Finds a path from the source_node to the drain_node
# with available capacity in a flow-network with flow F and capacity C.
# Returns an array where the first element is the index to omne of the start nodes,
# last element is the index to one of the exits and the elements between
# are the indexes of the nodes along the path, in the correct order.
# Example: A path from the start node 4 to node 3, node 9, node 7 and finally
# to the exit 12 will be represented as [4,3,9,7,12].


def find_flow_path(source, drain, F, C):
	n = len(F)
	discovered = [False] * n
	parent = [None] * len(F)
	queue = [source]
	while queue:
#		print(queue)
		node = queue.pop(0)
		if node == drain:
			# The drain is found, create an array of passed nodes.
			path = []
			i = node
			while True:
				path.append(i)
				if i == source:
					break
				i = parent[i]
			path.reverse()
			return path
		for neighbour in range(n):
			if not discovered[neighbour] and F[node][neighbour] < C[node][neighbour]:
				queue.append(neighbour)
				discovered[neighbour] = True
				parent[neighbour] = node
	return None

# try:
# 	stdin = open("input_2.txt")
# except Exception as e:
# 	print(e)


noder, _, _ = [int(x) for x in stdin.readline().split()]
start_rooms = [int(x) for x in stdin.readline().split()]
exits = [int(x) for x in stdin.readline().split()]
adjacency_matrix = []
for linje in stdin:
	naborad = [int(nabo) for nabo in linje.split()]
	adjacency_matrix.append(naborad)
print(isolated_paths_count(adjacency_matrix, start_rooms, exits))