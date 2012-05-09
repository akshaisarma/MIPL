#include <stdio.h>
#include <time.h>
#include <stdlib.h>

int main(int argc, char ** argv) {
	if (argc != 2 && argc != 3) {
		printf("%s [linenum] [1:M]\n", argv[0]);
		return -1;
	}

	srand(time(NULL));

	int i = atoi(argv[1]);

	if (argc == 2) {
		while (i-- > 0) {
			int c;
			do {
				c = (int) (rand() * 180.0 / RAND_MAX - 60);
			} while (c == 50);
			printf("%d %d %d\n", c, (rand() >= RAND_MAX/2), (rand() >= RAND_MAX/2));
		}
	}
	else if(argc == 3) {
		while (i-- > 0) {
			printf("50 0.5 0.5\n");
		}
	}

	return 0;
}
