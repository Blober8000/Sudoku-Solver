# Sudoku-Solver
Solves *most* sudoku boards

To use the solver there are 2 options. 
1. Either you start from scratch with an empty board, inserting each number individually by inputing the coordinates and the number, eg. to put a 1 on row 1 collumn 1 you'd imput A 1 1.

```
    A B C D E F G H I
  1 1 * * * * * * * *
  2 * * * * * * * * *
  3 * * * * * * * * *
  4 * * * * * * * * *
  5 * * * * * * * * *
  6 * * * * * * * * *
  7 * * * * * * * * *
  8 * * * * * * * * *
  9 * * * * * * * * *
```

3. Or you input the full board at once by inserting the board in the arguments, each number is separated by a space and empty cells must be a 0, the order is as follows on the image. Then you have the option to edit it in the same way you'd edit the board when you start from scratch. eg. java Sudoku 5 3 0 6 0 0 0 9 8 0 7 0 1 9 5 0 0 0 0 0 0 0 0 0 0 6 0 8 0 0 4 0 0 7 0 0 0 6 0 8 0 3 0 2 0 0 0 3 0 0 1 0 0 6 0 6 0 0 0 0 0 0 0 0 0 0 4 1 9 0 8 0 2 8 0 0 0 5 0 7 9
 
  A B C D E F G H I
1 5 3 * * 7 * * * *
2 6 * * 1 9 5 * * *
3 * 9 8 * * * * 6 *
4 8 * * * 6 * * * 3
5 4 * * 8 * 3 * * 1
6 7 * * * 2 * * * 6
7 * 6 * * * * 2 8 *
8 * * * 4 1 9 * * 5
9 * * * * 8 * * 7 9

Typing End will stop the editing and will start the solver
It'll stop when it's finished or if it's doing too many loops never ending it'll stop solving and allow the user to check each cell individually to see what's happening.
