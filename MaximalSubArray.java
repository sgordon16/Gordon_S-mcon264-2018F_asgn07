import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class MaximalSubArray {

	public static void main(String[] args) {
		int[] arr;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter length of array");
		int length = sc.nextInt();
		arr = new int[length];
		for(int i = 0; i < arr.length; i++) {
			arr[i] = ThreadLocalRandom.current().nextInt(-100, 101);
		}
		System.out.println("Brute force algorithim\n");
		long startTime = System.nanoTime();
		int [] maximalSubArrayBruteForce = findMaximalSubArrayBruteForce(arr);
		System.out.println("Running Time: " + (System.nanoTime() - startTime));
		System.out.println(IntStream.of(maximalSubArrayBruteForce).sum());
		if(maximalSubArrayBruteForce.length <= 20) {
			for(int i : maximalSubArrayBruteForce)
				System.out.print(i + ", ");
		}
		System.out.println();
		System.out.println("Divide and conquer algorithim\n");
		startTime = System.nanoTime();
		int [] maximalSubArrayDivideConquer = findMaximalSubArrayDivideConquer(arr);
		System.out.println("Running Time: " + (System.nanoTime() - startTime));
		System.out.println(IntStream.of(maximalSubArrayDivideConquer).sum());
		if(maximalSubArrayDivideConquer.length <= 20) {
			for(int i : maximalSubArrayDivideConquer)
				System.out.print(i + ", ");
		}	
	}
	public static int[] findMaximalSubArrayBruteForce(int[] arr) {
		int maxSum = 0;
		int sum = 0;
		int head = 0;
		int tail = 0;
		for(int x = 0; x < arr.length; x++) {
			sum = 0;
			for(int y = x; y < arr.length; y++) {
				sum+= arr[y];
				if(sum > maxSum) {
					maxSum = sum;
					head = x;
					tail = y;
				}
			}
		}
		return Arrays.copyOfRange(arr, head, tail + 1);
	}
	public static int[] findMaximalSubArrayDivideConquer(int[] arr) {
		if(arr.length == 1)
			return arr;
		int maxSum = 0;
		int sum = 0;
		int mid = (arr.length -1) / 2;
		int head = 0;
		int tail = 0;
		int[] middle;
		int[] right;
		int[] left;
		if(arr.length == 2) {
			middle = arr;
			right = Arrays.copyOfRange(arr, 0, 1);
			left = Arrays.copyOfRange(arr, 1, 2);
		}
		else {
			head = mid;
			for(int i = mid; i >= 0 ; i--) {
				sum+= arr[i];
				if(sum > maxSum) {
					maxSum = sum;
					head = i;
				}
			}
			sum = 0;
			maxSum = 0;
			tail = mid + 1;
			for(int i = mid + 1; i < arr.length; i++) {
				sum+= arr[i];
				if(sum > maxSum) {
					maxSum = sum;
					tail = i;
				}
			}
			middle = Arrays.copyOfRange(arr, head, tail + 1);
			right = findMaximalSubArrayDivideConquer(Arrays.copyOfRange(arr, 0, mid));
			left = findMaximalSubArrayDivideConquer(Arrays.copyOfRange(arr, mid, arr.length -1));
		}
		int sumRight = IntStream.of(right).sum();
		int sumLeft = IntStream.of(left).sum();
		int sumMiddle = IntStream.of(middle).sum();
		
		return sumRight > sumLeft && sumRight > sumMiddle ? right : sumLeft > sumRight && sumLeft > sumMiddle ? left : middle; 
	}

}
