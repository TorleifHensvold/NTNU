#!/usr/bin/python3

from sys import stdin
from itertools import repeat


def heapAdd(a, e):
	#Add element e to the heap, and sort accordingly
	a.append(e)
#	print(a) #for debugging
	percolateUp(a, len(a)-1)

def percolateUp(a, i):
	#If the element at i is smaller than the parent, switch them.
	if i == 1:
		return
	if a[i][0] < a[i//2][0]:
		a[i], a[i//2] = a[i//2], a[i]
		percolateUp(a, i//2)
	return

def percolateDown(a, i):
	#If the element is larger than either child, switch with the child that is smaller.
	smallest = i
	left, right = 2*i, 2*i+1
	if left > len(a)-1:
#		print("left DNE")
		return
	if right > len(a)-1:
#		print("Right DNE")
		smallest = left
	else:
		if a[left][0] <= a[right][0]:
			smallest = left
		else:
			smallest = right
	#if smallest != i:
	if a[i][0] > a[smallest][0]:
		a[i], a[smallest] = a[smallest], a[i]
		percolateDown(a, smallest)

def extractMin(a):
	#extract and return the minimum value tuple, then re-sort the heap with minHeapify
#	if len(a) == 1:
#		return
	value = a[1]
	a[1], a[-1] = a[-1], a[1]
	a.pop()
	percolateDown(a, 1)
#	print(a)
	return value

def merge(decks):
	# SKRIV DIN KODE HER
	length = len(decks)-1
	output = ""
	while length > 0:
		output += extractMin(decks)[1]
		length -= 1
	return output


def main():
	# Read input.
	decks = [(None,)]
	for line in testCase:
		(index, csv) = line.strip().split(':')
		deck = list(zip(map(int, csv.split(',')), repeat(index)))
		for item in deck:
			heapAdd(decks,item)
		#decks.append(deck)
	# Merge the decks and print result.
	#print(decks)
	#print(decks[0][0][0])
	print(merge(decks))
	#print(decks)

#testCase = "i:1,3,5,8\nn:2\nt:4,7\na:6\nv:9"
testCase = ["i:1,3,5,8","n:2","t:4,7","a:6","v:9"]
testCase2 = ["i:1,3,5,8","n:2","t:4,7","a:6","v:9","b:10,12,14,16","k:11,13,15,17"]

main()
