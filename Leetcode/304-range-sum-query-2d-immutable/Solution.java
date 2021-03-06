/*
Given a 2D matrix matrix, find the sum of the elements inside the rectangle 
defined by its upper left corner (row1, col1) and lower right corner (row2, col2).

Range Sum Query 2D
The above rectangle (with the red border) is defined by (row1, col1) = (2, 1) 
and (row2, col2) = (4, 3), which contains sum = 8.

Example:
Given matrix = [
  [3, 0, 1, 4, 2],
  [5, 6, 3, 2, 1],
  [1, 2, 0, 1, 5],
  [4, 1, 0, 1, 7],
  [1, 0, 3, 0, 5]
]

sumRegion(2, 1, 4, 3) -> 8
sumRegion(1, 1, 2, 2) -> 11
sumRegion(1, 2, 2, 4) -> 12
Note:
You may assume that the matrix does not change.
There are many calls to sumRegion function.
You may assume that row1 ≤ row2 and col1 ≤ col2.
*/

/*
Explanation Picture:
dp[i+1][j+1] represents the sum of area from matrix[0][0] to matrix[i][j]

https://discuss.leetcode.com/topic/29536/clean-c-solution-and-explaination-o-mn-space-with-o-1-time

+-----+-+-------+     +--------+-----+     +-----+---------+     +-----+--------+
|     | |       |     |        |     |     |     |         |     |     |        |
|     | |       |     |        |     |     |     |         |     |     |        |
+-----+-+       |     +--------+     |     |     |         |     +-----+        |
|     | |       |  =  |              |  +  |     |         |  -  |              |
+-----+-+       |     |              |     +-----+         |     |              |
|               |     |              |     |               |     |              |
|               |     |              |     |               |     |              |
+---------------+     +--------------+     +---------------+     +--------------+

dp[i][j]   =          dp[i-1][j]    +     dp[i][j-1]    -   dp[i-1][j-1]   +  
                      matrix[i-1][j-1]

+---------------+   +--------------+   +---------------+   +--------------+   +--------------+
|               |   |         |    |   |   |           |   |         |    |   |   |          |
|   (r1,c1)     |   |         |    |   |   |           |   |         |    |   |   |          |
|   +------+    |   |         |    |   |   |           |   +---------+    |   +---+          |
|   |      |    | = |         |    | - |   |           | - |      (r1,c2) | + |   (r1,c1)    |
|   |      |    |   |         |    |   |   |           |   |              |   |              |
|   +------+    |   +---------+    |   +---+           |   |              |   |              |
|        (r2,c2)|   |       (r2,c2)|   |   (r2,c1)     |   |              |   |              |
+---------------+   +--------------+   +---------------+   +--------------+   +--------------+    

                    dp[r2+1][c2+1] -    dp[r2+1][c1] -     dp[r1][c2+1]  +     dp[r1][c1]; 
*/    

import java.util.*;

// dp O(mn) time 
// Query: O(1)
public class NumMatrix {
    int[][] cumSum;

    public NumMatrix(int[][] matrix) {
        int r = matrix.length;
        if(r > 0){
            int c = matrix[0].length;
            
            cumSum = new int[r][c];
            // cumSum[i][j] = sum of the element (matrix[0][0] to matrix[i-1][j-1])
            cumSum[0][0] = matrix[0][0];
            // first column
            for(int i=1; i<r; i++){    
                cumSum[i][0] = cumSum[i-1][0] + matrix[i][0];
            }
            // first row
            for(int j=1; j<c; j++){
                cumSum[0][j] = cumSum[0][j-1] + matrix[0][j];
            }
            
            for(int i=1; i<r; i++){
                for(int j=1; j<c; j++){
                    cumSum[i][j] = cumSum[i][j-1] + cumSum[i-1][j] - cumSum[i-1][j-1] + matrix[i][j];        
                }
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return cumSum[row2][col2] - 
                (col1 > 0 ? cumSum[row2][col1-1] : 0) - 
                (row1 > 0 ? cumSum[row1-1][col2] : 0) + 
                ((row1 > 0 && col1 > 0) ? cumSum[row1-1][col1-1] : 0);  
    } 

    public static void main(String[] args){
        int[][] nums =  {
          {3, 0, 1, 4, 2},
          {5, 6, 3, 2, 1},
          {1, 2, 0, 1, 5},
          {4, 1, 0, 1, 7},
          {1, 0, 3, 0, 5}
        };

        NumMatrix numMatrix = new NumMatrix(nums);
        System.out.println(numMatrix.sumRegion(2, 1, 4, 3));
        System.out.println(numMatrix.sumRegion(1, 1, 2, 2));
        System.out.println(numMatrix.sumRegion(1, 2, 2, 4));
    }
}


// Your NumMatrix object will be instantiated and called as such:
// NumMatrix numMatrix = new NumMatrix(matrix);
// numMatrix.sumRegion(0, 1, 2, 3);
// numMatrix.sumRegion(1, 2, 3, 4);
