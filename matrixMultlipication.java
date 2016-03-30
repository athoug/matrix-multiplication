package matrixMultlipication;

/*
 * Algorithm Project
 * Matrix Multiplication
 * 
 *  Name: Athoug Alsoughayer
 *  GWID: G38361988
 * 
 */


// for user input
import java.util.Scanner;

public class matrixMultlipication {
	
	public int[][] mult(int[][] A, int[][] B){
		
		int n = A.length;
		int[][] C = new int[n][n]; // declaring the new array outcome of the multiplication
		
		// base case
		if(n == 1){
			
			C[0][0] = A[0][0] * B[0][0];
			
		} else {
			
			int[][] A11 = new int[n/2][n/2];
			int[][] A12 = new int[n/2][n/2];
			int[][] A21 = new int[n/2][n/2];
			int[][] A22 = new int[n/2][n/2];
			int[][] B11 = new int[n/2][n/2];
			int[][] B12 = new int[n/2][n/2];
			int[][] B21 = new int[n/2][n/2];
			int[][] B22 = new int[n/2][n/2];
			
			
			// dividing the matrix into 4 halves 
			// splitting the first matrix
			split(A, A11, 0, 0);
			split(A, A12, 0, n/2);
			split(A, A21, n/2, 0);
			split(A, A22, n/2, n/2);
			
			// splitting the second matrix
			split(B, B11, 0, 0);
			split(B, B12, 0, n/2);
			split(B, B21, n/2, 0);
			split(B, B22, n/2, n/2);
			
			// applying Strassen's algorithm of dividing the multiplication into their complex form
			// P1 =(A11 + A22)*(B11 + B22), P2 = (A21 + A22)*B11, P3 = A11*(B12 - B22), P4 = A22*(B21 - B11) 
			// P5 = (A11 + A12)*B22, P6 = (A21 - A11)*(B11 + B12), P7= (A12 - A22)*(B21 + B22)
			
			int[][] P1 =mult(add(A11, A22), add(B11, B22));
			int[][] P2 =mult(add(A21, A22) ,B11);
			int[][] P3 =mult(A11, sub(B12, B22));
			int[][] P4 =mult(A22, sub(B21, B11));
			int[][] P5 =mult(add(A11, A12), B22);
			int[][] P6 =mult(sub(A21, A11) ,add(B11, B12));
			int[][] P7 =mult(sub(A12, A22), add(B21, B22));
			
			// once we did the multiplication we then combine them using the addition that will make up 
			//the third matrix such that C11 = P1 + P4 - P5 + P7, C12 = P3 + P5, C21 = P2 + P4, C22 = P1 - P2 + P3 + P6
			
			int[][] C11 = add(sub(add(P1, P4), P5), P7);
			int[][] C12 = add(P3, P5);
			int[][] C21 = add(P2, P4);
			int[][] C22 = add(sub(add(P1, P3), P2), P6);
			
			// the final step of divide and concur which is merging the results back together
			merg(C11, C, 0, 0);
			merg(C12, C, 0, n/2);
			merg(C21, C, n/2, 0);
			merg(C22, C, n/2, n/2);
			
		}	
		
		//returning the result of the multiplication
		return C;
		
	}

	// function to subtract two matrices
	public int[][] sub(int[][] A, int[][] B){
		int n = A.length;
		int[][] C = new int[n][n];
				
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				C[i][j] = A[i][j] - B[i][j];
			}
		}
				
		return C;
	}
			
	// function to add two matrices
	public int[][] add(int[][] A, int[][] B){
		int n = A.length;
		int[][] C = new int[n][n];
				
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				C[i][j] = A[i][j] + B[i][j];
			}
		}
				
		return C;
	}
	
	
	// splitting original matrix into leaf matrices
	public void split(int[][] P, int[][] C, int iB, int jB){
		for(int i1 = 0, i2 =iB; i1 < C.length; i1++, i2++){
			for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++){
				C[i1][j1] = P[i2][j2];
			}
		}	
	}
	
	// merging the matrices together
	public void merg(int[][] C, int[][] P, int iB, int jB){
		for(int i1=0, i2=iB; i1<C.length; i1++, i2++){
			for(int j1=0, j2=jB; j1<C.length; j1++, j2++){
				P[i2][j2] = C[i1][j1];
			}
		}
	}

	// main function for implementing the algorithm
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Strassen's Matrix Multiplication Algorithm");
		System.out.println();
		
		
		// make an object of the multiplication class
		matrixMultlipication m = new matrixMultlipication();
		
		System.out.println("Enter the order of matrix (N): ");
		int n = scan.nextInt();
		
		// take a 2D matrix
		System.out.println("Randomly generated first matrix:\n");
		int[][] A = new int[n][n];
		
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				A[i][j] = (int)(Math.random()*100);
				System.out.print(A[i][j] + " ");
			}
			
			System.out.println();
		}
		
		System.out.println("Randomly generated second matrix:\n");
		int[][] B = new int[n][n];
		
		for(int i=0; i<n; i++){
			for(int j=0; j<n;j++){
				B[i][j] = (int)(Math.random()*100);
				System.out.print(B[i][j] + " ");
			}
			
			System.out.println();
		}
		
		// calculating matrix multiplication using brute force (naive) approach 
		int[][] bruteForce = new int[n][n];
		
		// set starting time
		long traditionalStartTime = System.nanoTime();
		
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				for(int k=0; k<n; k++){
					bruteForce[i][j] += A[i][k] * B[k][j];
				}	
			}
		}
		
		// set end time
		long traditionalendTime = System.nanoTime();
		
		// calculate the duration of multiplication
		long traditionalduration = (traditionalendTime - traditionalStartTime)/ 1000000;
		
		// calculating matrix multiplication using Strassen's Algorithm 
		
		// set starting time
		long StartTime = System.nanoTime();
		
		// Start multiplying 
		int[][] C = m.mult(A, B);
		
		// set end time
		long endTime = System.nanoTime();
		
		// calculate the duration of multiplication
		long duration = (endTime - StartTime)/ 1000000;
		
		
		System.out.println("\nThe multiplication result of A * B is: ");
		for(int i=0; i < n; i++){
			for(int j=0; j < n; j++){
				System.out.print(C[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.printf("It took %d miliseconds to calculate using Brute force approcah\nIt took %d miliseconds to calculate using Strassen's Algorithm", traditionalduration, duration);
		System.out.println();		
	}

}
