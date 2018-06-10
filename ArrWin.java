package sortNum;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

//LintCode�������ڵ����ֵ
public class ArrWin {
	    public static ArrayList<Integer> maxSlidingWindow(int[] nums, int k) {
	    	ArrayList<Integer> result=new ArrayList<Integer>();
	    	if(k>=nums.length){
	    		Arrays.sort(nums);
	    		if(nums.length!=0)
	    		result.add(nums[nums.length-1]);
	    	}
	    	else{
		    		int[] temp=new int[k];
					for(int i=0;i<=nums.length-k;i++){
						temp=Arrays.copyOfRange(nums, i, i+k);
						Arrays.sort(temp);
						result.add(temp[k-1]);
					}
	    		}
	    	return result;
	    }
	    //�Ż�����㷨
	    public static ArrayList<Integer> maxSlidingWindow2(int[] nums, int k) {
	    	ArrayList<Integer> result=new ArrayList<Integer>();
	    	if(k>=nums.length){
	    		Arrays.sort(nums);
	    		if(nums.length!=0)
	    		result.add(nums[nums.length-1]);
	    	}
	    	else{
		    		int[] temp=Arrays.copyOfRange(nums, 0, k);
		    		int MaxNum=0;
		    		int MaxIndex=0;
		    		for (int i = 0; i < temp.length; i++) {
						if(temp[i]>=MaxNum){						
							MaxNum=temp[i];
							MaxIndex=i;
						}
					}
		    		result.add(MaxNum);
					for(int j=1;j<=nums.length-k;j++){	
						if(j<=MaxIndex){
							//����������һ��ֵ����Ŀǰ�����е����ֵ������Ŀǰ��MaxNum��MaxIndex
							if(nums[j+k-1]>=MaxNum){
								MaxNum=nums[j+k-1];
								MaxIndex=j+k-1;
								result.add(MaxNum);
							}
							else 
								result.add(MaxNum);
						}
						else{
							//��δ�ҵ�����ֵ��ͬʱ֮ǰ���ֵ���ڴ����⣬��Ŀǰ����������Ѱ��
							temp=Arrays.copyOfRange(nums, j, j+k);
							MaxNum=0;
							for (int i = 0; i < temp.length; i++) {
								if(temp[i]>=MaxNum){						
									MaxNum=temp[i];
									MaxIndex=i;
								}												
							}
							MaxIndex=j+MaxIndex;
							result.add(MaxNum);
						}
					}
	    		}
	    		return result;
	    	}
	    
	    
	    public static void main(String[] args) {
	    	int nums[]={1,2,7,7,2,10,3,4,5};
	    	int k=2;
	    	ArrayList<Integer> arr=maxSlidingWindow2(nums,k);
	    	System.out.println(arr);
		}
}
