
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


public class LintCode {

	//LintCode阶梯训练(Google)
	//1-简单
	//1.Toeplitz Matrix
	public boolean isToeplitzMatrix(int[][] matrix) {
        // Write your code here
		int temp=matrix[0][0];
        int RowSize=matrix.length;
        int ColumnsSize=matrix[0].length;
        int checkSize=0;
        if (RowSize<=ColumnsSize)
            checkSize=RowSize;
        else
            checkSize=ColumnsSize;
        for (int i=0;i<checkSize ;i++ ) {
            if (matrix[i][i]!=temp) 
                return false;
        }
        return true;
    }
	
	//2.Similar RGB Color
	public String similarRGB(String color) {
		int valueA=Integer.parseInt(color.substring(1,3),16);
		int valueB=Integer.parseInt(color.substring(3,5),16);
		int valueC=Integer.parseInt(color.substring(5,7),16);
		String colorA=FindMin(valueA);
		String colorB=FindMin(valueB);
		String colorC=FindMin(valueC);
		color='#'+colorA+colorB+colorC;
		return color;
	   }
	public String FindMin(int colorValue) {
		String color=toHexString(colorValue);
		//若颜色两位字符相等，则颜色本身为最相似值
		if((color.charAt(0))==(color.charAt(1)))
			return color;
		//存储相比原数值较大的颜色值
		int colorValue_L=colorValue;
		//存储相比原数值较小的颜色值
		int colorValue_S=colorValue;
		//寻找最接近的字符相同的较大的颜色值
		while(color.charAt(0)!=color.charAt(1)){
			colorValue_L=colorValue_L+1;
			color=toHexString(colorValue_L);
		}
		color=toHexString(colorValue_S);
		//寻找最接近的字符相同的较小的颜色值
		while(color.charAt(0)!=color.charAt(1)){
			colorValue_S=colorValue_S-1;
			color=toHexString(colorValue_S);
		}
		//寻找较大值与较小值之间更接近的值
		color=Math.pow(colorValue-colorValue_L, 2)<Math.pow(colorValue-colorValue_S, 2) ? toHexString(colorValue_L):toHexString(colorValue_S);
		return color;
	}
	//将16进制数字转为字符，并对一位数字前面补0
	public String toHexString(int colorValue) {
		String color=Integer.toHexString(colorValue);
		if (color.length()==1)
			color='0'+color;
		return color;
	}
	
	//3.翻转游戏
	public List<String> generatePossibleNextMoves(String s) {
		//定义正则式匹配++
		String moveReg="[+][+]";
		Pattern movePat=Pattern.compile(moveReg);
		Matcher moveMat=movePat.matcher(s);
		int start=0;
		List<String> NextMoves=new ArrayList<String>();
		//找出匹配项后以--进行替换存入list,并从此次匹配的第一个+号后继续进行检索
		while(moveMat.find(start)){
			start=moveMat.end()-1;
			NextMoves.add(s.substring(0, start-1)+"--"+s.substring(start+1));
		}
		return NextMoves;
    }
	
	//4.有效单词词广场
	public boolean validWordSquare(String[] words) {
		for (int i = 0; i < words.length; i++) {
			char[] temp=new char[words[i].length()];
			for (int j = 0; j < words[i].length(); j++) {
				//当出现数组过界异常时即说明该单词序列无法构成有效单词广场，捕获该异常并返回false
				try {
					temp[j]=words[j].charAt(i);
				} catch (Exception e) {
					return false;
				}	
			}
			String newWord=new String(temp);
			if(!newWord.equals(words[i]))
				return false;
		}
		return true;
    }
	
	//5.Add Strings
	public String addStrings(String num1, String num2) {
		int size1=num1.length();
		int size2=num2.length();
		//界定数字字符范围
		final byte START_CHAR='0';
		final byte FINAL_CHAR='9';
		//判断进位符
		boolean carry=false;
		int shortSize;
		int longSize;
		char[] shortNum;
		char[] longNum;
		String result = null;
		if(size1<size2){
			shortSize=size1;
			longSize=size2;
			shortNum=num1.toCharArray();
			longNum=num2.toCharArray();
		}
		else{
			shortSize=size2;
			longSize=size1;
			shortNum=num2.toCharArray();
			longNum=num1.toCharArray();
		}
		char[] sum=new char[longSize];
		for (int i = shortSize-1; i>=0; i--) {
			char localSum=(char)(shortNum[i]+longNum[longSize-(shortSize-i)]-START_CHAR);
			if(carry)
				//进位操作（加法中进位数只会为1）
				localSum=(char) (localSum+1);
			if(localSum>FINAL_CHAR){
				//若需要进位，则localSum保留个位字符
				localSum=(char) (localSum-10);
				carry=true;
			}
			else
				carry=false;
			sum[longSize-(shortSize-i)]=localSum;
		}
		//位数相同情况
		if(shortSize==longSize){
			if(carry)
				result="1"+new String(sum);
			else
				result=new String(sum);
		}
		//位数不同情况
		else{
				for (int i = longSize-shortSize-1; i >=0; i--) {
					if(carry){
						char localSum=(char) (longNum[i]+1);
						if(localSum>FINAL_CHAR){
							//若需要进位，则localSum保留个位字符
							localSum=(char) (localSum-10);
							carry=true;
						}
						else
							carry=false;
						sum[i]=localSum;
					}	
					else
						sum[i]=longNum[i];
				}
				if(carry)
					result="1"+new String(sum);
				else
					result=new String(sum);
			}
		return result;
    }
	
	//7.栅栏染色
    public int numWays(int n, int k) {
    	int[] ways=new int[n];
    	ways[0]=k;
    	if(n>1)
    		ways[1]=k*k;
    	if(n<=2)
    		return ways[n-1];
    	else{
    		//采用动态规划法，递推出n根柱子时的染色方案数
    		for (int i = 2; i < n; i++) {
				ways[i]=ways[i-1]*(k-1)+ways[i-2]*(k-1);
			}
    	}
        return ways[n-1];
    }
    
    //8.有效的括号序列
    public boolean isValidParentheses(String s) {
    	Stack<Character> stack = new Stack<Character>();
    	for (int i = 0; i < s.length(); i++) {
    		char temp=s.charAt(i);
    		//若为左括号，压入栈中
			if(temp=='(' || temp=='[' || temp=='{')
				stack.push(temp);
			//若为右括号，弹出栈顶括号，判断是否匹配
			else{
				if(stack.empty())
		    		return false;
				switch(stack.pop()){
				case '(':
					if(temp!=')')
						return false;
					break;
				case '[':
					if(temp!=']')
						return false;
					break;
				case '{':
					if(temp!='}')
						return false;
					break;
				}
			}
		}
    	if(stack.empty())
    		return true;
    	else
    		return false;
    }
    
    //9.合并区间
    private class Interval {
    	int start, end;
    	Interval(int start, int end) {
    		this.start = start;
    	    this.end = end;
    	    }
		@Override
		public String toString() {
			return "Interval [start=" + start + ", end=" + end + "]";
		}
    	
    	}
    public List<Interval> merge(List<Interval> intervals) {
    	if(intervals.size()==0 || intervals.size()==1)
    		return intervals;
    	List<Interval> newInts=new ArrayList<Interval>();
    	//对intervals按start大小进行排序（从小到大）
    	Collections.sort(intervals,new Comparator<Interval>() {
			@Override
			public int compare(Interval a, Interval b) {
				if(a.start<b.start)
					return -1;
				else if(a.start>b.start)
					return 1;
				else
					return 0;
			}
		});
    	//存贮左右边界
    	int leftBorder=intervals.get(0).start;
    	int rightBorder=intervals.get(0).end;
    	for (int i = 1; i < intervals.size(); i++) {
    		Interval in=intervals.get(i);
    		//若新区间的左端大于右边界，则为新的区间范围
    		if(in.start>rightBorder){
    			newInts.add(new Interval(leftBorder, rightBorder));
    			leftBorder=in.start;
    			rightBorder=in.end;
    		}
    		//若新区间左端小于等于右边界，则有重叠范围，进行合并
    		else{
    			if(in.end>rightBorder)
    				rightBorder=in.end;
    		}
		}
    	newInts.add(new Interval(leftBorder, rightBorder));
        return newInts;
    }
    
    //10.加一
    public int[] plusOne(int[] digits) {
		int result[]=new int[digits.length+1];
		//用于判断是否进位
		boolean carry=false;
		for (int i = digits.length-1; i >= 0; i--) {
			if(carry){
				if(digits[i]!=9){
					result[i+1]=digits[i]+1;
					carry=false;
				}
				else{
					result[i+1]=0;
					//carry=true;
				}
			}
			else{
				//对于末位加1
				if(i == digits.length-1){
					if(digits[i]!=9)
						result[i+1]=digits[i]+1;
					else{
						result[i+1]=0;
						carry=true;
					}
				}
				//其它位在不进位情况下保持不变
				else
					result[i+1]=digits[i];
			}	
		}
		if(carry){
			result[0]=1;
			return result;
		}
    	return Arrays.copyOfRange(result, 1, result.length);
    }
    
    //2-中等
	//1.Beautiful Arrangement
    public int countArrangement(int N) {
    	//情况数
    	int caseNum=0;
    	//数组used记录数字使用情况，并以false初始填充（false表示未使用，true表示已使用）
    	boolean[] used=new boolean[N];
    	Arrays.fill(used, false);
    	caseNum=countNum(N,1,used,caseNum);
        return caseNum;
    }
	private int countNum(int n, int pos, boolean[] used, int caseNum) {
		// TODO Auto-generated method stub
		if(pos>n){
			caseNum++;
			return caseNum;
		}
		//对符合的情况进行查找，当该位查找到合适数字后通过递归的方式进行下一位数字的查找（回溯法）
		for (int i = 1; i <= n; i++) {
			if(!used[i-1] && (i%pos==0 || pos%i ==0)){
				used[i-1]=true;
				caseNum=countNum(n, pos+1, used, caseNum);
				used[i-1]=false;
			}		
		} 
		return caseNum;
	}

	//2.Kth Smallest Element in a BST
	private class TreeNode{
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
			this.val = val;
			this.left = this.right = null;
			}
		@Override
		public String toString() {
			return "TreeNode [val=" + val + ", left=" + left + ", right="
					+ right + "]";
		}
	}
	public int kthSmallest(TreeNode root, int k) {
		//historyNode记录历史访问过的节点（并保持每个节点的前一个为其父节点）
		LinkedList<TreeNode> historyNode=new LinkedList<TreeNode>();
		historyNode.add(root);
		//查找二叉树最小值
		TreeNode currentNode=historyNode.getLast();
		while(currentNode.left!=null){
			historyNode.add(currentNode.left);
			currentNode=historyNode.getLast();
		}
		//依次查找第k小的元素
		TreeNode smallerNode=null;
		while(k>0){
			smallerNode = findSmaller(historyNode);
			k--;
		}
		return smallerNode.val;
    }
	private TreeNode findSmaller(LinkedList<TreeNode> historyNode) {
		TreeNode smallerNode=historyNode.getLast();
		//将当前的较小节点舍弃（除仅剩一节点情况外）
		if(historyNode.size()>1)
			historyNode.removeLast();
		if(smallerNode.right==null)
			return smallerNode;
		//对右节点下的最小值进行搜索（保存查询过的节点）
		TreeNode currentNode=smallerNode.right;
		historyNode.add(currentNode);
		while(currentNode.left!=null){
			historyNode.add(currentNode.left);
			currentNode=historyNode.getLast();
		}
		return smallerNode;
	}
	
	//3.Longest Absolute File Path（待完善）
	private List<Integer> lengths=new ArrayList<Integer>();
	
	public int lengthLongestPath(String input) {
		input=input.replace("    ","\t");
		System.out.println(input.length());
		String regex="\\n\\t\\w";
		String[] paths=input.split(regex);
		searchPathLength(paths,regex,0);
		if(lengths.size()!=0)
			return Collections.max(lengths);
		else
			return 0;
    }

	private void searchPathLength(String[] paths, String regex, int length) {
		//每次查找仅将第一个查找到的文件夹长度计入总文件长度（该文件夹处于文件路径上）
		boolean change=false;
		for (String path : paths) {
			if(!path.contains("\t")){
				if(path.contains("."))
					lengths.add(length+path.length()+2);
				else if(!change){
					if(regex.length()==6)
						length=length+path.length();
					else
						length=length+path.length()+2;
					change=true;
				}	
			}
			else{
				//创建更深一级正则表达式
				String deeperRegex="\\n";
				for (int i = 0; i < regex.length()/2-1; i++) {
					//deeperRegex=deeperRegex+"[\\t[ ]+]";
					deeperRegex=deeperRegex+"\\t";
				}
				deeperRegex=deeperRegex+"\\w";
				//利用递归对更深一层路径长度进行查找
				String[] deeperPaths=path.split(deeperRegex);
				searchPathLength(deeperPaths,deeperRegex,length);
			}
		}
	}
	
	//ex.打劫房屋
	public long houseRobber(int[] A) {
		if(A.length==0)
			return 0;
        // 记录上次所获取的金额（初始时为A[0]）
		long money_last=A[0];
		if(A.length==1)
			return money_last;
		//记录本次所获取的金额（初始时为A[0]及A[1]中的较大值）
		long money=Math.max(A[0], A[1]);
		if(A.length==2)
			return money;
		for (int i = 2; i < A.length; i++) {
			//利用动态规划，最大金额为MAX(新增房屋金额+上上次最大值，上次最大值)
			long temp=money;
			money=Math.max(money_last+A[i], money);
			money_last=temp;
		}
        return money;
    }
	
	//4.炸弹袭击
	public int maxKilledEnemies(char[][] grid) {
		int height=grid.length;
		if(height==0)
			return 0;
		int width=grid[0].length;
		//记录最大消灭敌人数
		int maxNum=0;
		//缓存当前行各元素消灭敌人数
		int[] temp=new int[width];
		//缓存每列各元素消灭列敌人数
		int[] rowTemp=new int[width];
		//记录每行是否有空位
		boolean[] hasEmpty=new boolean[width];
		for (int i = 0; i < height; i++) {
			int lineKilled=0;
			for (int j = 0; j < width; j++) {
				//记录每行第一个空位（若碰到墙则重新计数），初始化为-1
				int lastEmpty=-1;
				//当该位置为0
				if(grid[i][j]=='0'){
					//若第一个空位未记录，则计算本行消灭敌人数（否则直接使用lineKilled数值）
					if(lastEmpty==-1){
						lastEmpty=j;
						lineKilled=0;
						//向左
						for (int k = j-1; k >= 0; k--) {
							if(grid[i][k]=='E')
								lineKilled++;
							else if(grid[i][k]=='W')
								break;	
						}
						//向右
						for (int k = j+1; k < width; k++) {
							if(grid[i][k]=='E')
								lineKilled++;
							else if(grid[i][k]=='W')
								break;	
						}
					}
					hasEmpty[j]=true;
					//temp[j]记录该行以及上行该位置的消灭数中最大值（用于E增加时的计数）
					temp[j]=Math.max(lineKilled+rowTemp[j],temp[j]);
					//记录最大值
					maxNum=Math.max(maxNum, temp[j]);
				}
				//当该位置为E
				else if(grid[i][j]=='E'){
					rowTemp[j]=rowTemp[j]+1;
					if(hasEmpty[j])
						temp[j]++;
						maxNum=Math.max(maxNum, temp[j]);
				}
				//当该位置为W
				else{
					//该位置所在计数清空
					temp[j]=0;
					rowTemp[j]=0;
					//重置空位计数
					lastEmpty=-1;
					//空位置为false
					hasEmpty[j]=false;
				}
			}
		}
		return maxNum;
    }
	
	//5.生成括号
	public List<String> generateParenthesis(int n) {
		List<String> ans=new ArrayList<String>();
		if(n==0){
			return ans;
		}
		int leftRest=n;
		int rightRest=n;
		backtrackSearch(leftRest,rightRest,ans, "");
		return ans;
    }

	private void backtrackSearch(int leftRest,
			int rightRest, List<String> ans ,String parenthesis) {
		//采用回溯法，通过递归的方式查找可能组合
		if(leftRest>rightRest)
			return;
		if(leftRest==0 && rightRest==0){
			ans.add(parenthesis);
			return;
		}
		if(leftRest>0)
			backtrackSearch(leftRest-1,rightRest,ans,parenthesis+"(");
		if(rightRest>0)
			backtrackSearch(leftRest,rightRest-1,ans,parenthesis+")");
	}
	/*
	private void helper(int l, int r, String s, List<String> res) {
        if (r < l) 
        	return;
        if (l == 0 && r == 0) {
            res.add(s);
        }
        if (l > 0) 
        	helper(l - 1, r, s + "(", res);
        if (r > 0) 
        	helper(l, r - 1, s + ")", res);
    }
    */

	//6.x的n次幂
	 public double myPow(double x, int n) {
		 int powNum=Math.abs(n);
		 if(n==0)
			 return 1;
		 //n为负值且绝对值取值超过2147483647（即int范围时），递归深度将超出范围，按情况给出答案
		 if(n<=-2147483648){
			 if(Math.abs(x)==1)
				 return 1.0;
			 else
				 return 0.0;
		 }
		 double ans=calcuPow(x,powNum);
		 if(n>0)
			 return ans;
		 else
			 return 1.0/ans;
	    }
	//采用分治法，使时间复杂度降至O(logn)
	private double calcuPow(double x, int powNum) {
		if(powNum==1)
			return x;
		else if(powNum%2==0){
			double temp=calcuPow(x,powNum/2);
			return temp*temp;
		}
		//powNum为奇数时，求(powNum-1)/2的平方值（powNum/2除不尽时即为向下取整）
		else{
			double temp=calcuPow(x,powNum/2);
			return temp*temp*x;
		}
	}

	//二叉树的序列化和反序列化
	/**
	 * Definition of TreeNode:
	 * public class TreeNode {
	 *     public int val;
	 *     public TreeNode left, right;
	 *     public TreeNode(int val) {
	 *         this.val = val;
	 *         this.left = this.right = null;
	 *     }
	 * }
	 */
	//序列化
	public String serialize(TreeNode root) {
		String data=new String();
		if(root!=null){
			data=addNode(root,data);
		}
		return data;
    }
	private String addNode(TreeNode node, String data) {
		//使用,分隔各个节点，空节点#表示
		data=data+node.val+",";
		if(node.left!=null)
			data=addNode(node.left,data);
		else
			data=data+"#,";
		if(node.right!=null)
			data=addNode(node.right,data);
		else
			data=data+"#,";
		return data;
	}

	//反序列化
	private int index;
	public TreeNode deserialize(String data) {
		if(data!=null && data.length()>0){
			String[] nodes=data.split(",");
			TreeNode root=new TreeNode(Integer.parseInt(nodes[0]));
			index=1;
			readTree(nodes,root);
			return root;
		}
		return null;
    }
	private void readTree(String[] nodes, TreeNode node) {
		if(index>=nodes.length)
			return;
		if(!nodes[index].equals("#")){
			node.left=new TreeNode(Integer.parseInt(nodes[index]));
			index++;
			readTree(nodes,node.left);
		}
		else
			index++;
		if(!nodes[index].equals("#")){
			node.right=new TreeNode(Integer.parseInt(nodes[index]));
			index++;
			readTree(nodes,node.right);
		}
		else
			index++;
	}
	//3-困难
	//1.岛屿的个数II
	/**
	 * Definition for a point.
	 */
	class Point {
		int x;
		int y;

		Point() {
			x = 0;
			y = 0;
		}

		Point(int a, int b) {
			x = a;
			y = b;
		}
	}
	
	public List<Integer> numIslands2(int n, int m, Point[] operators) {
		//建立并查表，存储root节点
		int[] unionFind=new int[n*m];
		Arrays.fill(unionFind, -1);
		//存储岛屿区域信息
		Map<Integer,List<Point>> areas=new HashMap<Integer,List<Point>>();
		List<Integer> isNum=new ArrayList<Integer>();
		if(operators==null || operators.length==0)
			return isNum;
		for (Point island : operators) {
			//位置信息（按行从左向右依次为0，1，2，...）
			int location=island.x*m+island.y;
			if(unionFind[location]==-1){
				//查找四周临近节点的root
				List<Integer> adjUnion=searchAround(m,unionFind,location);
				//若附近无岛屿，则建立新区域
				if(adjUnion.size()==0){
					List<Point> islands = new ArrayList<Point>();
					islands.add(island);
					areas.put(location, islands);
					unionFind[location]=location;
				}
				//若附近仅有一岛屿区域，则加入该区域
				else if(adjUnion.size()==1){
					int root= adjUnion.get(0);
					unionFind[location]=root;
					List<Point> islands = areas.get(root);
					islands.add(island);
					unionFind[location]=root;
				}
				//若附近有多个岛屿区域，合并所有区域后加入其中
				else{
					int root=unionCombine(adjUnion,areas,unionFind,m);
					unionFind[location]=root;
					List<Point> islands = areas.get(root);
					islands.add(island);
					unionFind[location]=root;
				}
			}
			isNum.add(areas.size());
		}
		return isNum;
	}

	private List<Integer> searchAround(int m, int[] unionFind, int location) {
		//使用set防止写入重复元素
		Set<Integer> adjUnion=new TreeSet<Integer>();
		int temp;
		//按上下左右顺序检索,若不为-1则存入adjUnion
		if(location-m>=0 && (temp=unionFind[location-m])!=-1)
			adjUnion.add(temp);		
		if(location+m<unionFind.length && (temp=unionFind[location+m])!=-1)
			adjUnion.add(temp);
		if(location%m!=0 && (temp=unionFind[location-1])!=-1)
			adjUnion.add(temp);
		if((location+1)%m!=0 && (temp=unionFind[location+1])!=-1)
			adjUnion.add(temp);
		return new ArrayList<Integer>(adjUnion);
	}
	private int unionCombine(List<Integer> adjUnion,
			Map<Integer, List<Point>> areas, int[] unionFind, int m) {
		int maxArea=-1;
		int size=0;
		//寻找最大的岛屿区域
		for (int i = 0; i < adjUnion.size(); i++) {
			int area=adjUnion.get(i);
			List<Point> islands = areas.get(area);
			if(islands.size()>size){
				maxArea=area;
				size=islands.size();
			}
		}
		//合并集合（合并areas并更新并查表）
		List<Point> MaxIslands = areas.get(maxArea);
		for (int i = 0; i < adjUnion.size(); i++) {
			int area=adjUnion.get(i);
			if(area!=maxArea){
				List<Point> islands = areas.get(area);
				MaxIslands.addAll(islands);
				areas.remove(area);
				for (Point point : islands) {
					unionFind[point.x*m+point.y]=maxArea;
				}
			}
		}
		return maxArea;
	}
	@Test
	public void myTest(){
		
	}
	
}
