# LintCode
LintCode中已刷题目的代码总结

## 1、阶梯训练（Google）
### 描述<br>
LintCode阶梯训练Google篇，包含Easy和Medium难度所有必做高频题及Hard难度部分必做高频题。<br>
各题详细描述请参见https://www.lintcode.com/ladder/18/

### 代码<br>
见LintCode.java

## 2、滑动窗口的最大值

### 描述<br>
给出一个可能包含重复的整数数组，和一个大小为 k 的滑动窗口, 从左到右在数组中滑动这个窗口，找到数组中每个窗口内的最大值。<br>
难度：困难<br>
### 样例<br>
给出数组 [1,2,7,7,8], 滑动窗口大小为 k = 3. 返回 [7,7,8].<br>
解释：<br>
最开始，窗口的状态如下：<br>
[|1, 2 ,7| ,7 , 8], 最大值为 7;<br>
然后窗口向右移动一位：<br>
[1, |2, 7, 7|, 8], 最大值为 7;<br>
最后窗口再向右移动一位：<br>
[1, 2, |7, 7, 8|], 最大值为 8.<br>
### 挑战<br>
O(n)时间，O(k)的额外空间<br>

### 代码<br>
见ArrWin.java

## 3、The Skyline Problem

### 描述<br>
水平面上有 N 座大楼，每座大楼都是矩阵的形状，可以用一个三元组表示 (start, end, height)，分别代表其在x轴上的起点，终点和高度。大楼之间从远处看可能会重叠，求出 N 座大楼的外轮廓线。<br>
外轮廓线的表示方法为若干三元组，每个三元组包含三个数字 (start, end, height)，代表这段轮廓的起始位置，终止位置和高度。
请注意合并同样高度的相邻轮廓，不同的轮廓线在x轴上不能有重叠。<br>
![ ](https://lintcode-media.s3.amazonaws.com/problem/jiuzhang3.jpg)<br>
难度：超难<br>
### 样例<br>
给出三座大楼：<br>
[<br>
  [1, 3, 3],<br>
  [2, 4, 4],<br>
  [5, 6, 1]<br>
]<br>
外轮廓线为：<br>
[<br>
  [1, 2, 3],<br>
  [2, 4, 4],<br>
  [5, 6, 1]<br>
]<br>

### 代码<br>
见OutlineOpt.java
