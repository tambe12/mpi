package ds_4;
import mpi.*;


public class MPI_ASSIGNMENT {
	public static void main(String [] args) {
		MPI.Init(args);
		int rank = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();
		
		int N = 8;
		int [] array = {1,2,3,4,5,6,7,8};
		
		int localSum = 0;
		int elementsPerProcess = N/size;
		
		int start = rank*elementsPerProcess;
		int end = start + elementsPerProcess;
		
		for(int i = start; i< end; i++) {
			localSum += array[i];
		}
		
		System.out.println("Process : "+ rank + " partial sum : "+ localSum);
		
		if(rank != 0) {
			int[]sendBuf = {localSum};
			MPI.COMM_WORLD.Send(sendBuf, 0, 1,MPI.INT, 0,0 );
		}else {
			int totalSum = localSum;
			for(int i= 1; i< size; i++) {
				int[] recvBuf = new int[1];
				MPI.COMM_WORLD.Recv(recvBuf, 0, 1, MPI.INT, i,0);
				totalSum += recvBuf[0];
			}
			System.out.println("Final Total Sum : "+ totalSum);
		}
		
		MPI.Finalize();
	}
}	















//export MPJ_HOME=/mnt/c/Users/dumbr/Downloads/mpj-v0_44/mpj-v0_44
//
//export PATH=$PATH:$MPJ_HOME/bin
//
//ls $MPJ_HOME/bin
//
//$MPJ_HOME/bin/mpjrun.sh -np 4 ds_4.MPI_ASSIGNMENT
//or 
///mnt/c/Users/dumbr/Downloads/mpj-v0_44/mpj-v0_44/bin/mpjrun.sh -np 4 ds_4.MPI_ASSIGNMENT
/// 
/// Make sure you are inside the /bin folder, not inside the src

