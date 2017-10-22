from sys import stdin

from collections import deque

class Node:
	def __init__(self):
		self.child = []
		self.ratatosk = False
		self.next_child = 0
		self.color = "W"
		self.nodeLevel = None


def dfs(root):
	# SKRIV DIN KODE HER
	rekkefølge = deque()
	ratatoskLevel = None
	if root.ratatosk == True:	# If we find Ratatosk in the root node we find him at level 0, and return that
		return 0
	for i in range(len(root.child)):	# We set the node level of all the children of root to level 1
		root.child[i].nodeLevel = 1		# and append them to the Deque
		rekkefølge.append(root.child[i])
	while len(rekkefølge) != 0:			# While we still have nodes left to visit
		currentnode = rekkefølge.pop()	# We set the currentnode to the last discovered node
		#print(currentnode.nodeLevel)
		if currentnode.ratatosk == True:	# If we find Ratatosk, we record what depth we found him on
			if ratatoskLevel == None:		# If the depth was None, we set the depth of the current node
				ratatoskLevel = currentnode.nodeLevel
			elif ratatoskLevel > currentnode.nodeLevel:	# If the depth was larger than the current node, we set it
				ratatoskLevel = currentnode.nodeLevel	# to the depth of the current node
		for i in range(len(currentnode.child)):			# We keep adding the children of the current node to the stack
			if currentnode.child[i].nodeLevel == None:						# If we don't have a level for the child
				currentnode.child[i].nodeLevel = currentnode.nodeLevel + 1	# we set the level to +1 from current node
			elif currentnode.child[i].nodeLevel > currentnode.nodeLevel + 1:	# If the child node has a level greater
				currentnode.child[i].nodeLevel = currentnode.nodeLevel + 1	# than current node +1 we set it to current
																			# node +1.
			rekkefølge.append(currentnode.child[i])
	return ratatoskLevel	# If we do not find ratatosk in any of the nodes we visit we return None, but if we found
							# him we return the lowest number level we found him on.




def bfs(root):
	# SKRIV DIN KODE HER
	rekkefølge = deque()
	if root.ratatosk == True:	# If ratatosk is in the root node, the depth is 0
		return 0
	for i in range(len(root.child)):	# The children of the root node has level 1
		root.child[i].nodeLevel = 1
		rekkefølge.append(root.child[i])
	while len(rekkefølge) != 0:			# While we still have nodes left to visit we do this:
		currentnode = rekkefølge.popleft()	# We set the current node to the first discovered amongst those left to visit
		#print(currentnode.nodeLevel)
		if currentnode.ratatosk == True:	# If we find Ratatosk here we return the level of the Node as we've set it
			return currentnode.nodeLevel
		for i in range(len(currentnode.child)):	# If we didn't find Ratatosk, we set the nodelevel of the children of
												# the current node to the depth of the current node + 1
			currentnode.child[i].nodeLevel = currentnode.nodeLevel + 1
			rekkefølge.append(currentnode.child[i])	# And we add the children of the current node to the back of the
													# list of nodes to visit
	return None		# if we didn't find Ratatosk in any of the nodes we could visit we return None

#try:
#	stdin = open("input.txt")
#except Exception as e:
#	print(e)

function = stdin.readline().strip()
number_of_nodes = int(stdin.readline())
nodes = []
for i in range(number_of_nodes):
	nodes.append(Node())
	#print(nodes[i].child for i in range(len(nodes)))
start_node = nodes[int(stdin.readline())]
ratatosk_node = nodes[int(stdin.readline())]
ratatosk_node.ratatosk = True
for line in stdin:
	number = line.split()
	temp_node = nodes[int(number.pop(0))]
	#print(temp_node)
	for child_number in number:
		temp_node.child.append(nodes[int(child_number)])

if function == 'dfs':
	print(dfs(start_node))
elif function == 'bfs':
	print(bfs(start_node))
elif function == 'velg':
	# SKRIV DIN KODE HER
	print(bfs(start_node))


