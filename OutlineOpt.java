package sortNum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.Test;

public class OutlineOpt {
	private int start;
	private int end;
	private int height;
	private List<List<Integer>> result;
	private int[][] partRange; 	//partRange用于保存一段连续建筑中已被占用区域的情况
	private int blockindex;
	public  List<List<Integer>> buildingOutline(int[][] buildings) {
		result = new ArrayList<List<Integer>>();
		if(buildings.length==0){
			return result;
		}
		Arrays.sort(buildings, new Comparator<int[]>() {
			@Override
			public int compare(int[] a, int[] b) {
				if(a[0]>b[0])
					return 1;
				else if(a[0]<b[0])
					return -1;
				else{
					if(a[1]>b[1])
						return 1;
					else if(a[1]<b[1])
						return -1;
					else
						return 0;
				}		
			}	
		});
		//当前段建筑轮廓的起点建筑索引
		int startIndex=0;
		//下段建筑轮廓的起点建筑索引
		int endIndex=0;
		while(true){
			start = buildings[startIndex][0];
			end = buildings[startIndex][1];
			height = buildings[startIndex][2];
			//一段轮廓的起点
			//int partStart=start;
			//一段轮廓的终点
			int partEnd=end;
			//遍历buildings找出一段相邻建筑
			for(int i=startIndex+1;i<buildings.length;i++){
				if(buildings[i][0]<=partEnd){
					if(buildings[i][1]>partEnd){
						partEnd=buildings[i][1];
					}
					if(i==buildings.length-1)
						endIndex=i+1;	//防止遗漏最后的建筑
				}
				else{
					endIndex=i;
					break;
				}
			}
			//当前索引已至最终建筑，对结果进行排序并退出循环
			if(startIndex==endIndex){
				Collections.sort(result,new Comparator<List<Integer>>() {
					@Override
					public int compare(List<Integer> a, List<Integer> b) {
						if(a.get(0)>b.get(0))
							return 1;
						else if(a.get(0)<b.get(0))
							return -1;
						else
							return 0;	
					}
				});
				if((result.size()==0)||(result.get(result.size()-1).get(1))<end)
					addResult();
				break;
			}
			int[][] partBuildings=Arrays.copyOfRange(buildings, startIndex, endIndex);
			//按高度从高到低重新给partBuildings排序
			Arrays.sort(partBuildings, new Comparator<int[]>() {
				@Override
				public int compare(int[] a, int[] b) {
					if(a[2]>b[2])
						return -1;
					else if(a[2]<b[2])
						return 1;
					else{
						if(a[0]>b[0])
							return 1;
						else if(a[0]<b[0])
							return -1;
						else
							return 0;
					}		
				}	
			});
			start=partBuildings[0][0];
			end=partBuildings[0][1];
			height=partBuildings[0][2];
			//重置partRange
			//partRange.clear();
			if(partBuildings.length<=500)
				partRange=new int[partBuildings.length*2][2];
			else
				partRange=new int[partBuildings.length/2][2];
			for(int i=1;i<=partBuildings.length;i++){
				int startBlock=-1;
				int endBlock=-1;
				if((i!=partBuildings.length)&&(partBuildings[i][2]==height)&&(partBuildings[i][0]<=end)){
					//若两段建筑高度相等，且相互有重合，则合并
					end=partBuildings[i][1]>end ? partBuildings[i][1] : end;
					continue;
				}
				else{
					if(partRange.length==0){
						addResult();
						//写入已占用区域
						addBlock();
					}
					else{
						for(int j=0;j<partRange.length;j++){
							//新建筑起点小于某已占用区域的终点时，记录起点所在的区块（仅记录一次）
							if((startBlock<0)&&(start<(partRange[j][1]))){
								startBlock=j; 
							}
							//新建筑终点小于某已占用区域的起点时，记录终点所在的区块
							if(((partRange[j][0])==0)||(end<(partRange[j][0]))){
								if(partRange[j][0]==0)
									endBlock=-1;
								else
									endBlock=j;
								break;
							}
						}
						//当前建筑与已占用区域无重合，直接写入结果
						if(startBlock==endBlock){
							addResult();
							addBlock();
						}
						//当前建筑与已占用区域有重合，将未重合部分写入结果
						else{
							//endBlock为-1说明该建筑范围已达到最右方区块
							if(endBlock==-1)
								endBlock=blockindex;
							int startBackup=start;
							int endBackup=end;
							for(int k=startBlock;k<=endBlock;k++){
								//首区
								if(k==startBlock){
									if(startBackup<(partRange[k][0])){
										end=(partRange[k][0]);
										addResult();
										addBlock();
									}
								}
								//尾区
								if(k==endBlock){
									if(endBackup>(partRange[k-1][1])){
										start=(partRange[k-1][1]);
										end=endBackup;
										addResult();
										addBlock();
									}
								}
								//穿越区域
								if((k!=startBlock)&&(k!=endBlock)){
									start=partRange[k-1][1];
									end=partRange[k][0];
									addResult();
									addBlock();
								}
							}
						}
					}
				}
				if(i!=partBuildings.length){
					//对partRange维护（从左到右排序后合并相邻区域）
					Arrays.sort(partRange,new Comparator<int[]>() {
						@Override
						public int compare(int[] a, int[] b) {
							if((b[0]!=0)&&(a[0]>b[0]))
								return 1;
							else if((a[0]!=0)&&(a[0]<b[0]))
								return -1;
							else
								return 0;	
						}
					});
					for(int m=0; (m<partRange.length-1)&&(partRange[m][0]!=0);m++){
						if((partRange[m][1])==((partRange[m+1][0]))){
							start=(partRange[m][0]);
							end=(partRange[m+1][1]);
							remRepeat(partRange,m);
							m--;	//将合并后区间与下个区间继续比较
						}
					}
					//更新start、end、height
					start=partBuildings[i][0];
					end=partBuildings[i][1];
					height=partBuildings[i][2];
					}
				}
			if(endIndex==buildings.length)
				endIndex--;
			startIndex=endIndex;
		}
		return result;
	}
	//去除两个相邻区间并将合并后的新区间放入partRange
	private void remRepeat(int[][] partRange, int index) {
		addBlock(index);
		blockindex--;
		int[][] arrTemp=new int[partRange.length-index-1][3];
		System.arraycopy(partRange, index+2, arrTemp, 0, partRange.length-index-2);
		System.arraycopy(arrTemp, 0, partRange, index+1, arrTemp.length);
	}
	private void addResult(){
		List<Integer> Outline=new ArrayList<Integer>();
		Outline.add(start);
		Outline.add(end);
		Outline.add(height);
		result.add(Outline);
	}
	private void addBlock(int index){
		partRange[index][0]=start;
		partRange[index][1]=end;
	}
	private void addBlock(){
		if((partRange[0][0]==0)&&(partRange[0][1]==0))
			blockindex = 0;
		partRange[blockindex][0]=start;
		partRange[blockindex][1]=end;
		blockindex++;
	}
	@Test
	public void test() {
		int[][] buildings={{1,10,3},{2,5,8},{7,8,9}};
		//int[][] buildings={{3,7,78},{4,5,313},{5,8,401},{6,10,242},{7,8,600},{8,12,466},{9,14,528},{10,13,370},{11,13,642},{12,15,895},{13,16,733},{14,17,360},{15,16,272},{16,21,22},{17,21,605},{18,19,767},{19,22,901},{20,24,942},{21,25,416},{22,27,704},{23,25,497},{24,27,967},{25,30,459},{26,27,414},{27,28,208},{28,29,327},{29,31,773},{30,34,94},{31,35,409},{32,36,156},{33,35,195},{34,37,666},{35,39,156},{36,37,538},{37,38,777},{38,42,186},{39,41,108},{40,41,998},{41,45,660},{42,46,922},{43,47,978},{44,48,927},{45,48,583},{46,49,802},{47,49,210},{48,52,514},{49,51,580},{50,51,479},{51,56,857},{52,57,242},{53,58,753},{54,56,418},{55,56,440},{56,61,25},{57,62,529},{58,60,249},{59,64,619},{60,65,507},{61,66,682},{62,64,152},{63,66,45},{64,68,867},{65,68,383},{66,70,34},{67,69,678},{68,71,176},{69,73,230},{70,73,292},{71,73,211},{72,74,293},{73,74,27},{74,78,287},{75,77,478},{76,79,145},{77,79,178},{78,82,731},{79,80,702},{80,81,696},{81,85,614},{82,87,887},{83,88,71},{84,86,194},{85,87,244},{86,89,414},{87,89,244},{88,90,828}};
		List<List<Integer>> buildingOutline = buildingOutline(buildings);
		System.out.println(buildingOutline);
	}
}
