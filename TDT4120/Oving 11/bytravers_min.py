from sys import stdin, maxsize
#import numpy as n

def shortest_path(order_list, adjacency_matrix, cities):
	# SKRIV DIN KODE HER
	d = fl_w(adjacency_matrix)
#	print(n.matrix(d))
	length = 0
	for i in range(len(order_list)-1):
		fra, til = order_list[i], order_list[i+1]
		length += d[fra][til]
#		print(length)
	length += d[order_list[-1]][order_list[0]]
	return length if length != float("inf") else "umulig"

def fl_w(matrix):
	distance = [[number if number != -1 else float("inf") for number in line] for line in matrix]
	# Copying and making sure we set the length between two cities to infinite if we cannot travel between them
	size = len(distance)
	for k in range(size):
		for i in range(size):
			for j in range(size):
				if distance[i][j] > (distance[i][k] + distance[k][j]):
					distance[i][j] = distance[i][k] + distance[k][j]
#					print(n.matrix(distance))
#					print("{0} {1} {2}".format(k,i,j))
#					print(distance[i][j], end=" ")
#					print(distance[i][k], end="+")
#					print(distance[k][j])
#					print("Changed")
	return distance



#try:
#	stdin = open("input.txt")
#except Exception as e:
#	print(e)

testcases = int(stdin.readline())
for test in range(testcases):
	#print(stdin.readline())
	cities = int(stdin.readline())
	order_list = [int(city) for city in stdin.readline().split()]
#	print(order_list)
	adjacency_matrix = []
	for city in range(cities):
		adjacency_matrix.append([int(cost) for cost in stdin.readline().split()])
#		print(n.matrix(adjacency_matrix))
#	print(n.matrix(adjacency_matrix))
	# SKRIV DIN KODE HER
	print(shortest_path(order_list, adjacency_matrix, cities))