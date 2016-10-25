#include <string.h>
#include <sys/time.h>
#define MAX_STREAM_LENGTH 1000
#define MAX_NEIGHBORS 9

int visited[MAX_STREAM_LENGTH];
int noOfNodes = MAX_STREAM_LENGTH;
int counter = 0;
int edges[MAX_STREAM_LENGTH][MAX_NEIGHBORS];
int endNode;
int startNode;

void bruteForce(int currentNode, int i);
int hasPath(int i, int j);
void print_array(int *array, int cnt, int b);
int tester(int argc, char *argv[]);

int find(int argc, char *argv[])
{
    /* TODO remove time */
    /* for getting the time */
    struct timeval start, end;
    gettimeofday(&start, NULL);

    /* read from file into array */

    /* proof if there is exactly one argument */
    if(argc != 2)
    {
        printf("you need to enter an file!\n");
        return EXIT_FAILURE;
    }

    /* open and read from File */
    FILE *fp = fopen(argv[1], "r");
    if(fp == NULL)
    {
        printf("enter a valid path!\n");
        return EXIT_FAILURE;
    }

    char buffer[MAX_STREAM_LENGTH];

    /* count lines of file */
    noOfNodes = 0;
    while (!feof(fp))
    {
        fgets(buffer, MAX_STREAM_LENGTH, fp);
        ++noOfNodes;
    }

    noOfNodes--;
    rewind(fp);
    //printf("\ncounter: %d\n", noOfNodes);

    fscanf(fp, "%d %d\n", &startNode, &endNode);
    //printf("start node: %d\n", startNode);
    //printf("end node: %d\n", endNode);

    char *ptr = buffer;
    char **p = &ptr;
    int row = -1;
    int value = -1;
    char *buf;
    int edge_counter[noOfNodes];

    /* set all vars to 0 */
    for(int i = 0; i < noOfNodes; i++)
    {
        edge_counter[i] = 0;
        visited[i] = 0;
    }

    /* ---- TODO remove Array output ----*/
    //print_array(edge_counter, noOfNodes, 0);
    //print_array(visited, noOfNodes, 0);


    int c = 0;
    while(!feof(fp))
    {
        c++;
        fgets(buffer, MAX_STREAM_LENGTH, fp);

        if(buffer != NULL)
        {
            ptr = buffer;

            while((buf = strsep(p, " ")) != NULL)
            {
                if(row < 0)
                {
                    row = atoi(buf);
                }
                else
                {
                    value = atoi(buf);
                    edges[row][edge_counter[row]++] = value;
                    edges[value][edge_counter[value]++] = row;

                }

            }
            /* reset values */
            row = -1;
            value = -1;
            free(buf);
        }
        /* ---- TODO remove Array output ----*/
        //print_array(edge_counter, noOfNodes, 0);
    }

    for(int i = 0; i < c; i++)
        edges[i][edge_counter[i]] = -1;

    /* get an overview of the array-values
     ---- TODO remove Array output ----*/
    print_array(edge_counter, noOfNodes, 1);

    fclose(fp);

    /* just bruteforcing */

    // count all cases

    visited[startNode] = 1;
    bruteForce(startNode, 1);

    // generate output

    printf("%d", counter);

    /* TODO remove time */
    gettimeofday(&end, NULL);
    printf("\nneeded time over all: %li\n", ((end.tv_usec) - (start.tv_usec))/1000);

    return EXIT_SUCCESS;
}


/** \brief just bruteforced visiting neighbor nodes
 *
 * \param currentNode int  number of the current node
 * \param i int  is the depth, needed for aborting
 * \return void
 *
 */
void bruteForce(int currentNode, int i)
{

    int outer_counter = 0;
    int node;
outer:
    while(edges[currentNode][outer_counter] != -1)
    {
        node =edges[currentNode][outer_counter++];
        if (visited[node] == 1)
        {
            continue;
        }
        if (node == endNode)
        {
            continue;
        }
        if(i == noOfNodes - 2)
        {
            if(hasPath(node, endNode))
            {
                counter++;
                continue;
            }
            continue;
        }

        visited[node] = 1;

        int inner_counter = 0;
        int n;
inner:
        while(edges[currentNode][inner_counter] != -1)
        {
            n = edges[currentNode][inner_counter++];
            if (visited[n] == 1 || n == node) continue;
            int openConnections = 0;

            int sub_counter = 0;
            int neighbor;
            while(edges[n][sub_counter] != -1)
            {
                neighbor = edges[n][sub_counter++];
                if (neighbor == node)
                    goto inner;
                openConnections += 1-visited[neighbor];
            }
            if (openConnections <= (n == endNode ? 0 : 1))
            {
                visited[node] = 0;
                goto outer;
            }
        }

        bruteForce(node, i + 1);
        visited[node] = 0;
    }
}

/** \brief returns if there is a path between node i and node j
 *
 * \param i int number from first node
 * \param j int number from second node
 * \return int 1, if there is a path, 0, if there is no path
 *
 */
int hasPath(int i, int j)
{
    for(int k = 0; k < noOfNodes ; k++)
    {

        if(edges[i][k] == j)
            return 1;
        if(edges[i][k] == -1)
            return 0;
    }
    return 0;
}


/** \brief printing out the array to generate a debugging output
 *          TODO remove this block
 *
 * \param array[] int* points to the array
 * \param cnt int number ol elements
 * \param b int indicates if (b == 1) array is a matrix or (if !b) an array
 * \return void
 *
 */
void print_array(int *array, int cnt, int b)
{
    if(!b)
    {
        printf("\narray: \n");
        for(int i = 0; i < cnt; i++)
        {
            printf(" %d", *(array+i));
        }
    }
    else if (b)
    {
        printf("\n array:\n");
        for(int i = 0; i < cnt; i++)
        {
            for(int j = 0; j < MAX_NEIGHBORS; j++)
            {
                printf(" %d", edges[i][j]);
            }
            printf("\n");
        }
    }
    printf("\n");
}

/** \brief is for testing purpose only
 *
 * \param argc int  needed argument
 * \param argv[] char*  needed argument
 * \return int  returns the number of hamilton path's
 *
 */

int tester(int argc, char *argv[])
{
   main(argc, argv);
   int r = counter;
   counter = 0;
   return r;
}

