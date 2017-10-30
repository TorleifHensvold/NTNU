from sys import stdin
import random
from collections import deque

Inf = float(1e3000)


def mst(nm):
	# SKRIV DIN KODE HER
	spenntre = prims(nm)	# We get a minimum spanning tree back, and can traverse it to find the longest length
#	print(spenntre)
#	dictlength = spenntre.__len__()
#	print(dictlength)
#	dictindexL = dictlength-1
#	print(dictindexL)
#	print(len(spenntre))
#	startnode = random.randint(0, len(spenntre)-1)
#	print("Tree Starting Node: ", end='')
#	print(startnode)
	longest = Inf*-1	# We set the longest to an approximately infinitely negative number
						# We start traversing and recording the longest found length larger than "longest"
#	traversal = deque()
#	traversal.append(e for e in spenntre.get(startnode))
#	print(spenntre.get(startnode))
#	print(traversal)
	for key in spenntre:
#		print("key: " + str(key))
		for i in range(len(spenntre[key])):
#			print("Spenntre[" + str(key) + "][" + str(i) + "] = " + str(spenntre[key][i][1]))
			if spenntre[key][i][1] > longest:
				longest = spenntre[key][i][1]
#				print("Longest: " + str(longest))
	return longest


def prims(nm):	# implementing a way to make a minimum spanning tree and returning it
	discovered_nodes = {}	# We initialize a {\LIST} dictionary containing the nodes with key = node number
	startnode = random.randint(0, len(nm)-1)	# We start at a random node
	shortest = Inf
	nodeshort = None
#	print("startnode: ", end='')
#	print(startnode)
	for i in range(len(nm[startnode])):		# For all the edges from startnode we check which is the lesser length
#		print("startnode[i]: ", end='')
#		print(nm[startnode][i])
		if nm[startnode][i] < shortest:		# If the cost of the edge is smaller we remember the cost and node
			shortest = nm[startnode][i]		# it leads to
			nodeshort = i
#			print("nodeshort: ", end='')
#			print(nodeshort)
	discovered_nodes[startnode] = [(nodeshort, shortest)]	# We add the shortest path from the node we know to all the
															# nodes it has access to
	discovered_nodes[nodeshort] = [(startnode, shortest)]	# We add the node we discovered and its edge as well
#	print(discovered_nodes)
	while len(nm) > len(discovered_nodes):	# While we still have nodes to add, we add them, including their edge weight
		nodeshort = None
		shortest = Inf
		keyshort = None
		for key in discovered_nodes:	# For each key in discovered_nodes we do
#			print("key: ", end="")		# Print what key we have
			stringkey = str(key)
#			print(stringkey)
			for i in range(len(nm[key])):	# For the indexes of the neighbour matrix of node "key" we
				# i is index for nm[key]
				if nm[key][i] < shortest and i not in discovered_nodes.keys():
					# If a edge weight is less than the shortest currently discovered edge and the node it leads
					# to is not currently in discovered keys we remember the shortest edge and its node.
					nodeshort, shortest, keyshort = i, nm[key][i], key
					# We also remember what key/what node we found the shortest edge and its node from
#					print("nodeshort, shortest, key: " + str(nodeshort) + " " + str(shortest) + " " + stringkey)
		discovered_nodes[nodeshort] = [(keyshort, shortest)]
		discovered_nodes[keyshort].append((nodeshort, shortest))
#		print(discovered_nodes)
	return discovered_nodes





#try:
#	stdin = open("input.txt")
#except Exception as e:
#	print(e)

lines = []
for string in stdin:
	lines.append(string)
n = len(lines)
neighbour_matrix = [None] * n
node = 0
for line in lines:
	neighbour_matrix[node] = [Inf] * n
	for k in line.split():
		data = k.split(':')
		neighbour = int(data[0])
		weight = int(data[1])
		neighbour_matrix[node][neighbour] = weight
	node += 1
#print(neighbour_matrix)
print(mst(neighbour_matrix))

#prims(neighbour_matrix)