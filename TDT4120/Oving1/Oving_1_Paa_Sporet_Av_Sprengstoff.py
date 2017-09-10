from sys import stdin


class Record:
	value = None
	next = None

	def __init__(self, value):
		self.value = value
		self.next = None


def search(record):
	if record.next == None:
		return record.value		#If the record is the last one, there can be no higher numbers further along.
	return max(record.value, search(record.next))	#We compare the record we're at with the record following
													#and return the highest of these two. Thus we're always
													#sending the highest number up through the chain until
													#we return to the first call, which returns the highest
													#number observed.


def main():
	# reading from stdin and creating a linked list
	first = None
	last = None
	for line in stdin:
		penultimate = last
		last = Record(int(line))
		if first is None:
			first = last
		else:
			penultimate.next = last

	# searching and printing out the result
	print(search(first))


if __name__ == "__main__":
	main()