/*
Basic plan
①Shuffle the array
②Partition so that,for that j
 ——entry a[j] is in place
 ——no larger entry to the left of j
 ——no smaller entry to the right of j
③Sort each piece recursively
*/

/*
Quicksort partitioning demo
Repeat until i and j pointers cross.
——Scan i from left to right so long as (a[i] < a[lo]).
——Scan j from right to left so long as (a[j] > a[lo]).
——Exchange a[i] with a[j].
When pointers cross.        
——Exchange a[lo] with a[j].
 */
 
 
/*   Java code for partitioning   */
/*- ----------------------------------------------------------------------------------------------------*/
private static int partition(Comparable[] a,int lo,int hi){
  int i = lo,j = hi+1;               //j初始的值为hi + 1,因为后面为--j形式
  Comparable v = a[lo];

while(true){
    while(less(a[++i],v))  if(i == hi) break;    //i == hi是为了防止最左边是最大值的情况而陷入死循坏
    while(less(v,a[--j]))  if(j == lo) break;    //j == lo是为了防止最左边是最小值的情况而陷入死循坏
    if(j <= i)  break;         //pointers cross
    exch(a,i,j);
  }
  exch(a,lo,j);
  return j;
}
/*- -----------------------------------------------------------------------------------------------------*/


/*  Quicksort: Java implementation  */
/*- ----------------------------------------------------------------------------------------------------*/
import java.util.Random;
import java.util.Scanner;

public class Quicksort {
	public static void sort(Comparable[] a){
		StdRandom.shuffle(a);    //为了产生随机的a[lo],即partitioning item
		sort(a,0,a.length - 1);
	}
	
	private static void sort(Comparable[] a,int lo,int hi){
		if(hi <= lo) return;
		
		int j = partition(a,lo,hi);
		
		sort(a,lo,j-1);
		sort(a,j+1,hi);	
	}
	
	private static int partition(Comparable[] a,int lo,int hi){
		int i = lo,j = hi+1;
		
		Comparable v = a[lo];
		
		while(true){
			while(less(a[++i],v))  if(i == hi) break;
			while(less(v,a[--j]))  if(j == lo) break;
			
			if(j <= i)  break;
			exch(a,i,j);
		}
		exch(a,lo,j);
		return j;
	}
	
	private static boolean less(Comparable v,Comparable w){
		return v.compareTo(w) < 0;
	}
	
	private static void exch(Comparable[] a,int i,int j){
		
		Comparable v = a[i];
		a[i] = a[j];
		a[j] = v;
	}
	
	private static void show(Comparable[] a){
		int N = a.length;
		
		for(int i = 0;i < N;i++){
			System.out.print(a[i] + "  ");
		}
		
		System.out.println();
	}
	
	
	public static void main(String[] args){
		String[] s = new String[16];
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Input the array of String");
		int i = 0;
		while(!input.hasNext("exit")){
			s[i++] = input.next();
		}
		
		System.out.println("The array of String is :");
		show(s);
		
		sort(s);
		System.out.println("After sorting :");
		show(s);		
	}
}
/*- ----------------------------------------------------------------------------------------------------*/


/* Shuffling is needed for performance guarantee */
/*- ----------------------------------------------------------------------------------------------------*/
class StdRandom{
	public static void shuffle(Comparable[] a){
		int N = a.length;
		Random random = new Random();
		
		for(int i = 0;i < N;i++){
			int j = random.nextInt(i + 1);     //[0,i]之间的随机数			
			exch(a,i,j);					
		}
	}
	
	private static void exch(Comparable[] a,int i,int j){
		
		Comparable v = a[i];
		a[i] = a[j];
		a[j] = v;
	}
}
/*- ----------------------------------------------------------------------------------------------------*/

/*
 * 在JDK1.8中输入结果为：
 * -----------------------------------------------
 * Input the array of String
   X Q U I C K S O R T E A M P L E exit
   The array of String is :
   X  Q  U  I  C  K  S  O  R  T  E  A  M  P  L  E  
   After sorting :
   A  C  E  E  I  K  L  M  O  P  Q  R  S  T  U  X
   -----------------------------------------------  
 * */
/*- ----------------------------------------------------------------------------------------------------*/

/* Quicksort: practical improvementsⅠ—— Insertion sort small subarrays. */
/*
Insertion sort small subarrays.
􀉾Even quicksort has too much overhead for tiny subarrays.
􀉾Cutoff to insertion sort for ≈ 10 items.
􀉾Note: could delay insertion sort until one pass at end.
*/
/* 修改Quicksort中的sort() */
/*----------------------------------------------------------------------------------*/
private static void sort(Comparable[] a,int lo,int hi){
		if(hi <= lo + CUTOFF - 1){           /* CUTOFF is system-dependent , any key between 5 and 15 is likely work well in most situations */
			Insertion.sort(a,lo,hi);
			return;
		}
		
		int j = partition(a,lo,hi);
		
		sort(a,lo,j-1);
		sort(a,j+1,hi);	
}
/*----------------------------------------------------------------------------------*/

/* 此时需要修改Insertion.sort() */
/*----------------------------------------------------------------------------------*/
class Insertion{
	public static void sort(Comparable[] a,int lo,int hi){
		
		for(int i = lo + 1;i <= hi;i++){
			for(int j = i;j > lo && less(a[j],a[j-1]);j--){
				exch(a,j,j-1);
			}
		}
	}
	
	private static boolean less(Comparable v,Comparable w){
		return v.compareTo(w) < 0;
	}
	
	private static void exch(Comparable[] a,int i,int j){
		
		Comparable v = a[i];
		a[i] = a[j];
		a[j] = v;
	}
}
/*----------------------------------------------------------------------------------*/
/*在JDK1.8中输入结果为：
 * -----------------------------------------------
 * Input the array of String
   X Q U I C K S O R T E A M P L E exit
   The array of String is :
   X  Q  U  I  C  K  S  O  R  T  E  A  M  P  L  E  
   After sorting :
   A  C  E  E  I  K  L  M  O  P  Q  R  S  T  U  X  
   -----------------------------------------------  
 * */



/* Quicksort: practical improvements Ⅱ —— Median of sample*/
/*
Median of sample.
􀉾Best choice of pivot item = median.
􀉾Estimate true median by taking median of sample.
􀉾Median-of-3 (random) items.

Doing so will give a slightly better partition,but at the cost of computing the median
*/
/* 修改Quicksort中的sort() */
/*---------------------------------------------------------------------------------*/
private static void sort(Comparable[] a,int lo,int hi){
	if(hi <= lo)  return;

	int m = medianOf3(lo,lo + (hi - lo)/2,hi);        //每一次sort都要计算medianOf3
	exch(a,m,lo);              //交换a[medianOf3]与a[lo]
	
	int j = partition(a,lo,hi);
	sort(a,lo,j-1);
	sort(a,j+1,hi);		
}
/*---------------------------------------------------------------------------------*/

/* implementation of median */
/*---------------------------------------------------------------------------------*/
private static int medianOf3(int u,int v,int w){
	return (u + v + w)/3;
}
/*---------------------------------------------------------------------------------*/


/* Quicksort: practical improvements Ⅲ —— 3-way partitioning */
/*
Often, purpose of sort is to bring items with equal keys together.
——Sort population by age.
——Remove duplicates from mailing list.
——Sort job applicants by college attended.

Typical characteristics of such applications.
——Huge array.
——Small number of key values.
*/

/* 3-way partitioning */
/*
Goal. Partition array into 3 parts so that:
——Entries between lt and gt equal to partition item v.
——No larger entries to left of lt.
——No smaller entries to right of gt.

Dijkstra 3-way partitioning demo 
①Let v be partitioning item a[lo].
②Scan i from left to right.
– (a[i] < v): exchange a[lt] with a[i]; increment both lt and i
– (a[i] > v): exchange a[gt] with a[i]; decrement gt
– (a[i] == v): increment i
*/

/* 3-way quicksort: Java implementation */
/*---------------------------------------------------------------------------------------*/
private static void sort(Comparable[] a,int lo,int hi){
	if(hi <= lo)  return;     //递归结束标志
 
	int lt = lo,gt = hi;   //lt,gt初始位置分别为lo,hi
	int i = lo;

	Comparable v = a[lo];
	while(i <= gt){
		int cmp = a[i].compareTo(v);  

		if(cmp < 0) exch(a,lt++,i++);    /* (a[i] < v): exchange a[lt] with a[i]; increment both lt and i */
		else if(cmp > 0) exch(a,gt--,i);  /* (a[i] > v): exchange a[gt] with a[i]; decrement gt */
		else i++;		/* (a[i] == v): increment i */	
	}		

	sort(a,lo,lt-1);
	sort(a,gt+1,hi);
}
/*---------------------------------------------------------------------------------------*/




/* Selection */
/*
Goal. Given an array of N items, find a kth smallest item.
Ex. Min (k = 0), max (k = N - 1), median (k = N / 2).

Applications.
——Order statistics.
——Find the "top k."

Partition array so that:
——Entry a[j] is in place.
——No larger entry to the left of j.
——No smaller entry to the right of j.

Repeat in one subarray, depending on j; finished when j equals k.
*/


/* Jave implementation of selection */
/*---------------------------------------------------------------------------------------*/
public static Comparable select(Comparable[] a,int k){
	StdRandom.shuffle(a);

	int lo = 0;
	int hi = a.length - 1;

	while(lo < hi){
		int j = partition(a,lo,hi);

		if(k == j) return a[j];
		else if(k < j) hi = j - 1;
		else lo = j + 1;
	}
	return a[k];
}
/*---------------------------------------------------------------------------------------*/
/*
在JDK1.8中输入结果为： 
- ----------------------------------------------
Input the array of String
X Q U I C K S O R T E A M P L E exit
The array of String is :
X  Q  U  I  C  K  S  O  R  T  E  A  M  P  L  E  
The 4th smallest is I
- -----------------------------------------------
*/
