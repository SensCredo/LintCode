package sortNum;

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

	//LintCode����ѵ��(Google)
	//1-��
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
		//����ɫ��λ�ַ���ȣ�����ɫ����Ϊ������ֵ
		if((color.charAt(0))==(color.charAt(1)))
			return color;
		//�洢���ԭ��ֵ�ϴ����ɫֵ
		int colorValue_L=colorValue;
		//�洢���ԭ��ֵ��С����ɫֵ
		int colorValue_S=colorValue;
		//Ѱ����ӽ����ַ���ͬ�Ľϴ����ɫֵ
		while(color.charAt(0)!=color.charAt(1)){
			colorValue_L=colorValue_L+1;
			color=toHexString(colorValue_L);
		}
		color=toHexString(colorValue_S);
		//Ѱ����ӽ����ַ���ͬ�Ľ�С����ɫֵ
		while(color.charAt(0)!=color.charAt(1)){
			colorValue_S=colorValue_S-1;
			color=toHexString(colorValue_S);
		}
		//Ѱ�ҽϴ�ֵ���Сֵ֮����ӽ���ֵ
		color=Math.pow(colorValue-colorValue_L, 2)<Math.pow(colorValue-colorValue_S, 2) ? toHexString(colorValue_L):toHexString(colorValue_S);
		return color;
	}
	//��16��������תΪ�ַ�������һλ����ǰ�油0
	public String toHexString(int colorValue) {
		String color=Integer.toHexString(colorValue);
		if (color.length()==1)
			color='0'+color;
		return color;
	}
	
	//3.��ת��Ϸ
	public List<String> generatePossibleNextMoves(String s) {
		//��������ʽƥ��++
		String moveReg="[+][+]";
		Pattern movePat=Pattern.compile(moveReg);
		Matcher moveMat=movePat.matcher(s);
		int start=0;
		List<String> NextMoves=new ArrayList<String>();
		//�ҳ�ƥ�������--�����滻����list,���Ӵ˴�ƥ��ĵ�һ��+�ź�������м���
		while(moveMat.find(start)){
			start=moveMat.end()-1;
			NextMoves.add(s.substring(0, start-1)+"--"+s.substring(start+1));
		}
		return NextMoves;
    }
	
	//4.��Ч���ʴʹ㳡
	public boolean validWordSquare(String[] words) {
		for (int i = 0; i < words.length; i++) {
			char[] temp=new char[words[i].length()];
			for (int j = 0; j < words[i].length(); j++) {
				//��������������쳣ʱ��˵���õ��������޷�������Ч���ʹ㳡��������쳣������false
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
		//�綨�����ַ���Χ
		final byte START_CHAR='0';
		final byte FINAL_CHAR='9';
		//�жϽ�λ��
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
				//��λ�������ӷ��н�λ��ֻ��Ϊ1��
				localSum=(char) (localSum+1);
			if(localSum>FINAL_CHAR){
				//����Ҫ��λ����localSum������λ�ַ�
				localSum=(char) (localSum-10);
				carry=true;
			}
			else
				carry=false;
			sum[longSize-(shortSize-i)]=localSum;
		}
		//λ����ͬ���
		if(shortSize==longSize){
			if(carry)
				result="1"+new String(sum);
			else
				result=new String(sum);
		}
		//λ����ͬ���
		else{
				for (int i = longSize-shortSize-1; i >=0; i--) {
					if(carry){
						char localSum=(char) (longNum[i]+1);
						if(localSum>FINAL_CHAR){
							//����Ҫ��λ����localSum������λ�ַ�
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
	
	//7.դ��Ⱦɫ
    public int numWays(int n, int k) {
    	int[] ways=new int[n];
    	ways[0]=k;
    	if(n>1)
    		ways[1]=k*k;
    	if(n<=2)
    		return ways[n-1];
    	else{
    		//���ö�̬�滮�������Ƴ�n������ʱ��Ⱦɫ������
    		for (int i = 2; i < n; i++) {
				ways[i]=ways[i-1]*(k-1)+ways[i-2]*(k-1);
			}
    	}
        return ways[n-1];
    }
    
    //8.��Ч����������
    public boolean isValidParentheses(String s) {
    	Stack<Character> stack = new Stack<Character>();
    	for (int i = 0; i < s.length(); i++) {
    		char temp=s.charAt(i);
    		//��Ϊ�����ţ�ѹ��ջ��
			if(temp=='(' || temp=='[' || temp=='{')
				stack.push(temp);
			//��Ϊ�����ţ�����ջ�����ţ��ж��Ƿ�ƥ��
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
    
    //9.�ϲ�����
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
    	//��intervals��start��С�������򣨴�С����
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
    	//�������ұ߽�
    	int leftBorder=intervals.get(0).start;
    	int rightBorder=intervals.get(0).end;
    	for (int i = 1; i < intervals.size(); i++) {
    		Interval in=intervals.get(i);
    		//�����������˴����ұ߽磬��Ϊ�µ����䷶Χ
    		if(in.start>rightBorder){
    			newInts.add(new Interval(leftBorder, rightBorder));
    			leftBorder=in.start;
    			rightBorder=in.end;
    		}
    		//�����������С�ڵ����ұ߽磬�����ص���Χ�����кϲ�
    		else{
    			if(in.end>rightBorder)
    				rightBorder=in.end;
    		}
		}
    	newInts.add(new Interval(leftBorder, rightBorder));
        return newInts;
    }
    
    //10.��һ
    public int[] plusOne(int[] digits) {
		int result[]=new int[digits.length+1];
		//�����ж��Ƿ��λ
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
				//����ĩλ��1
				if(i == digits.length-1){
					if(digits[i]!=9)
						result[i+1]=digits[i]+1;
					else{
						result[i+1]=0;
						carry=true;
					}
				}
				//����λ�ڲ���λ����±��ֲ���
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
    
    //2-�е�
	//1.Beautiful Arrangement
    public int countArrangement(int N) {
    	//�����
    	int caseNum=0;
    	//����used��¼����ʹ�����������false��ʼ��䣨false��ʾδʹ�ã�true��ʾ��ʹ�ã�
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
		//�Է��ϵ�������в��ң�����λ���ҵ��������ֺ�ͨ���ݹ�ķ�ʽ������һλ���ֵĲ��ң����ݷ���
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
		//historyNode��¼��ʷ���ʹ��Ľڵ㣨������ÿ���ڵ��ǰһ��Ϊ�丸�ڵ㣩
		LinkedList<TreeNode> historyNode=new LinkedList<TreeNode>();
		historyNode.add(root);
		//���Ҷ�������Сֵ
		TreeNode currentNode=historyNode.getLast();
		while(currentNode.left!=null){
			historyNode.add(currentNode.left);
			currentNode=historyNode.getLast();
		}
		//���β��ҵ�kС��Ԫ��
		TreeNode smallerNode=null;
		while(k>0){
			smallerNode = findSmaller(historyNode);
			k--;
		}
		return smallerNode.val;
    }
	private TreeNode findSmaller(LinkedList<TreeNode> historyNode) {
		TreeNode smallerNode=historyNode.getLast();
		//����ǰ�Ľ�С�ڵ�����������ʣһ�ڵ�����⣩
		if(historyNode.size()>1)
			historyNode.removeLast();
		if(smallerNode.right==null)
			return smallerNode;
		//���ҽڵ��µ���Сֵ���������������ѯ���Ľڵ㣩
		TreeNode currentNode=smallerNode.right;
		historyNode.add(currentNode);
		while(currentNode.left!=null){
			historyNode.add(currentNode.left);
			currentNode=historyNode.getLast();
		}
		return smallerNode;
	}
	
	//3.Longest Absolute File Path�������ƣ�
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
		//ÿ�β��ҽ�����һ�����ҵ����ļ��г��ȼ������ļ����ȣ����ļ��д����ļ�·���ϣ�
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
				//��������һ��������ʽ
				String deeperRegex="\\n";
				for (int i = 0; i < regex.length()/2-1; i++) {
					//deeperRegex=deeperRegex+"[\\t[ ]+]";
					deeperRegex=deeperRegex+"\\t";
				}
				deeperRegex=deeperRegex+"\\w";
				//���õݹ�Ը���һ��·�����Ƚ��в���
				String[] deeperPaths=path.split(deeperRegex);
				searchPathLength(deeperPaths,deeperRegex,length);
			}
		}
	}
	
	//ex.��ٷ���
	public long houseRobber(int[] A) {
		if(A.length==0)
			return 0;
        // ��¼�ϴ�����ȡ�Ľ���ʼʱΪA[0]��
		long money_last=A[0];
		if(A.length==1)
			return money_last;
		//��¼��������ȡ�Ľ���ʼʱΪA[0]��A[1]�еĽϴ�ֵ��
		long money=Math.max(A[0], A[1]);
		if(A.length==2)
			return money;
		for (int i = 2; i < A.length; i++) {
			//���ö�̬�滮�������ΪMAX(�������ݽ��+���ϴ����ֵ���ϴ����ֵ)
			long temp=money;
			money=Math.max(money_last+A[i], money);
			money_last=temp;
		}
        return money;
    }
	
	//4.ը��Ϯ��
	public int maxKilledEnemies(char[][] grid) {
		int height=grid.length;
		if(height==0)
			return 0;
		int width=grid[0].length;
		//��¼������������
		int maxNum=0;
		//���浱ǰ�и�Ԫ�����������
		int[] temp=new int[width];
		//����ÿ�и�Ԫ�������е�����
		int[] rowTemp=new int[width];
		//��¼ÿ���Ƿ��п�λ
		boolean[] hasEmpty=new boolean[width];
		for (int i = 0; i < height; i++) {
			int lineKilled=0;
			for (int j = 0; j < width; j++) {
				//��¼ÿ�е�һ����λ��������ǽ�����¼���������ʼ��Ϊ-1
				int lastEmpty=-1;
				//����λ��Ϊ0
				if(grid[i][j]=='0'){
					//����һ����λδ��¼������㱾�����������������ֱ��ʹ��lineKilled��ֵ��
					if(lastEmpty==-1){
						lastEmpty=j;
						lineKilled=0;
						//����
						for (int k = j-1; k >= 0; k--) {
							if(grid[i][k]=='E')
								lineKilled++;
							else if(grid[i][k]=='W')
								break;	
						}
						//����
						for (int k = j+1; k < width; k++) {
							if(grid[i][k]=='E')
								lineKilled++;
							else if(grid[i][k]=='W')
								break;	
						}
					}
					hasEmpty[j]=true;
					//temp[j]��¼�����Լ����и�λ�õ������������ֵ������E����ʱ�ļ�����
					temp[j]=Math.max(lineKilled+rowTemp[j],temp[j]);
					//��¼���ֵ
					maxNum=Math.max(maxNum, temp[j]);
				}
				//����λ��ΪE
				else if(grid[i][j]=='E'){
					rowTemp[j]=rowTemp[j]+1;
					if(hasEmpty[j])
						temp[j]++;
						maxNum=Math.max(maxNum, temp[j]);
				}
				//����λ��ΪW
				else{
					//��λ�����ڼ������
					temp[j]=0;
					rowTemp[j]=0;
					//���ÿ�λ����
					lastEmpty=-1;
					//��λ��Ϊfalse
					hasEmpty[j]=false;
				}
			}
		}
		return maxNum;
    }
	
	//5.��������
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
		//���û��ݷ���ͨ���ݹ�ķ�ʽ���ҿ������
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

	//6.x��n����
	 public double myPow(double x, int n) {
		 Math.pow(x, n);
		 int powNum=Math.abs(n);
		 if(n==0)
			 return 1;
		 //nΪ��ֵ�Ҿ���ֵȡֵ����2147483647����int��Χʱ�����ݹ���Ƚ�������Χ�������������
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
	//���÷��η���ʹʱ�临�ӶȽ���O(logn)
	private double calcuPow(double x, int powNum) {
		if(powNum==1)
			return x;
		else if(powNum%2==0){
			double temp=calcuPow(x,powNum/2);
			return temp*temp;
		}
		//powNumΪ����ʱ����(powNum-1)/2��ƽ��ֵ��powNum/2������ʱ��Ϊ����ȡ����
		else{
			double temp=calcuPow(x,powNum/2);
			return temp*temp*x;
		}
	}

	//�����������л��ͷ����л�
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
	//���л�
	public String serialize(TreeNode root) {
		String data=new String();
		if(root!=null){
			data=addNode(root,data);
		}
		return data;
    }
	private String addNode(TreeNode node, String data) {
		//ʹ��,�ָ������ڵ㣬�սڵ�#��ʾ
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

	//�����л�
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

	@Test
	public void myTest(){
		
	}
	
}
