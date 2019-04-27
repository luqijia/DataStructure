package com.link;

import java.util.HashMap;

/**
 * 链表ADT
 * 
 * @author wangtao
 * @param <T>
 */
public class LinkADT<T> {

	/**
	 * 单链表节点
	 * 
	 * @author wangtao
	 * @param <T>
	 */
	private static class SingleNode<T> {
		public SingleNode<T> next;
		public T data;

		public SingleNode(T data) {
			this.data = data;
		}

		public T getNextNodeData() {
			return next != null ? next.data : null;
		}
	}

	/**
	 * 寻找链表的中间节点
	 * 
	 * @param node
	 * @return
	 */
	public static SingleNode findMiddle(SingleNode headNode) {
		if (headNode == null || headNode.next == null) {
			return headNode;
		}

		SingleNode pNode = headNode;
		SingleNode qNode = headNode.next;

		while (qNode != null && qNode.next != null) {
			pNode = pNode.next;
			qNode = qNode.next.next;
		}

		return pNode;
	}

	/**
	 * 单链表反转
	 */
	private static void verifyFindeMiddle() {
		SingleNode<Integer> s1 = new SingleNode<>(1);
		SingleNode<Integer> s2 = new SingleNode<>(2);
		SingleNode<Integer> s3 = new SingleNode<>(3);
		SingleNode<Integer> s4 = new SingleNode<>(4);
		SingleNode<Integer> s5 = new SingleNode<>(5);

		s1.next = s2;
		s2.next = s3;
		s3.next = s4;
		s4.next = null;

		printLink(findMiddle(s1));
	}

	/**
	 * 删除倒数第n个节点 version1
	 * 
	 * @param node
	 * @param n
	 * @return
	 */
	public static SingleNode removeV1(SingleNode headNode, int n) {
		if (headNode == null) {
			return null;
		}

		SingleNode pNode = headNode;
		SingleNode qNode = headNode;

		int i = 0;
		while (i < n || n <= 0) {
			pNode = pNode.next;

			if (pNode == null) {   //判断是否删除的是第一个节点
				headNode = headNode.next;
				return headNode;
			}

			i++;
		}

		while (pNode.next != null) {
			pNode = pNode.next;
			qNode = qNode.next;
		}

		qNode.next = qNode.next.next;
		return headNode;
	}

	/**
	 * 单链表反转
	 */
	private static void verifyRemove() {
		SingleNode<Integer> s1 = new SingleNode<>(1);
		SingleNode<Integer> s2 = new SingleNode<>(2);
		SingleNode<Integer> s3 = new SingleNode<>(3);
		SingleNode<Integer> s4 = new SingleNode<>(4);
		SingleNode<Integer> s5 = new SingleNode<>(5);

		s1.next = s2;
		s2.next = s3;
		s3.next = s4;
		s4.next = s5;
		s5.next = null;

		printLink(removeV1(s1, 6));
	}

	/**
	 * 单链表反转 version1
	 * 
	 * @param node 当前节点
	 * @param pre  前一个节点
	 * @return
	 */
	public static SingleNode reverseV1(SingleNode node, SingleNode pre) {

		if (node == null) {
			return node;
		} else {
			// 反转后的头结点
			SingleNode headNode;

			// next为空，说明是尾节点
			if (node.next == null) {
				// 修改next引用
				node.next = pre;
				// 指定反转后的头节点
				headNode = node;
			} else {
				// 非尾节点，继续递归
				headNode = reverseV1(node.next, node);
				//
				node.next = pre;
			}

			return headNode;
		}
	}

	/**
	 * 单链表反转 version2
	 * 
	 * @param node
	 * @return
	 */
	public static SingleNode reverseV2(SingleNode node) {
		if (node == null || node.next == null) {
			return node;
		} else {
			SingleNode headNode = reverseV2(node.next);
			node.next.next = node;
			node.next = null;
			return headNode;
		}
	}

	/**
	 * 判断是否有环 快慢指针法
	 * 
	 * @param node
	 * @return
	 */
	public static boolean hasLoopV1(SingleNode headNode) {

		if (headNode == null) {
			return false;
		}

		SingleNode p = headNode;
		SingleNode q = headNode.next;

		// 快指针未能遍历完所有节点
		while (q != null && q.next != null) {
			p = p.next; // 遍历一个节点
			q = q.next.next; // 遍历两个个节点

			// 已到链表末尾
			if (q == null) {
				return false;
			} else if (p == q) {
				// 快慢指针相遇，存在环
				return true;
			}
		}

		return false;
	}

	private static HashMap<SingleNode, Integer> nodeMap = new HashMap<>();

	/**
	 * 判断是否有环 足迹法
	 * 
	 * @param node
	 * @return
	 */
	public static boolean hasLoopV2(SingleNode node, int index) {
		if (node == null || node.next == null) {
			return false;
		}

		if (nodeMap.containsKey(node)) {
			return true;
		} else {
			nodeMap.put(node, index);
			return hasLoopV2(node.next, ++index);
		}
	}

	/**
	 * 有序链表合并，非递归
	 * 
	 * @param nodeA
	 * @param nodeB
	 * @return
	 */
	public static SingleNode<Integer> mergeV1(SingleNode<Integer> nodeA, SingleNode<Integer> nodeB) {
		if (nodeA == null) {
			return nodeB;
		} else if (nodeB == null) {
			return nodeA;
		}

		// 初始化nodeC
		SingleNode<Integer> nodeC = new SingleNode<Integer>(null);
		// 定义当前节点
		SingleNode<Integer> currentNode = nodeC;

		// 遍历A和B，知道末尾
		while (nodeA != null || nodeB != null) {
			SingleNode<Integer> nextNode = new SingleNode<Integer>(null);
			// 找出较小的节点
			if (compareNode(nodeA, nodeB) <= 0) {
				nextNode.data = nodeA.data;
				nodeA = nodeA.next;
			} else {
				nextNode.data = nodeB.data;
				nodeB = nodeB.next;
			}

			// 添加较小的节点
			currentNode.next = nextNode;
			currentNode = currentNode.next;
		}

		// 去掉没有用的头结点
		nodeC = nodeC.next;

		return nodeC;
	}

	/**
	 * 有序链表合并，递归
	 * 
	 * @param nodeA
	 * @param nodeB
	 * @return
	 */
	private static SingleNode<Integer> mergeV2(SingleNode<Integer> nodeA, SingleNode<Integer> nodeB) {
		SingleNode<Integer> result;
		if (nodeA == null) {
			return nodeB;
		} else if (nodeB == null) {
			return nodeA;
		}

		// 找出较小的节点
		if (compareNode(nodeA, nodeB) <= 0) {
			result = nodeA;
			nodeA = nodeA.next;
		} else {
			result = nodeB;
			nodeB = nodeB.next;
		}
		result.next = mergeV2(nodeA, nodeB);
		return result;
	}

	/**
	 * 单链表反转
	 */
	private static void verifyMerge() {
		SingleNode<Integer> s1 = new SingleNode<>(1);
		SingleNode<Integer> s2 = new SingleNode<>(2);
		SingleNode<Integer> s3 = new SingleNode<>(9);
		s1.next = s2;
		s2.next = s3;

		SingleNode<Integer> n1 = new SingleNode<>(3);
		SingleNode<Integer> n2 = new SingleNode<>(4);
		SingleNode<Integer> n3 = new SingleNode<>(7);
		SingleNode<Integer> n4 = new SingleNode<>(8);
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;

		// printLink(mergeV1(s1, n1));
		printLink(mergeV2(s1, n1));
	}

	private static int compareNode(SingleNode<Integer> node1, SingleNode<Integer> node2) {
		if (node1 == null) {
			return 1;
		} else if (node2 == null) {
			return -1;
		}

		if (node1.data == null) {
			return -1;
		} else if (node2.data == null) {
			return 1;
		}

		return node1.data.compareTo(node2.data);
	}

	/**
	 * 单链表反转
	 */
	private static void verifyLoop() {
		SingleNode<Integer> s1 = new SingleNode<>(1);
		SingleNode<Integer> s2 = new SingleNode<>(2);
		SingleNode<Integer> s3 = new SingleNode<>(3);
		SingleNode<Integer> s4 = new SingleNode<>(3);
		SingleNode<Integer> s5 = new SingleNode<>(3);
		SingleNode<Integer> s6 = new SingleNode<>(3);
		s1.next = s2;
		s2.next = s3;
		s3.next = s4;
		s4.next = s5;
		s5.next = s6;
		s6.next = null;

		System.out.println(hasLoopV1(s1));
	}

	/**
	 * 打印链表信息
	 * 
	 * @param node
	 */
	public static void printLink(SingleNode node) {
		System.out.println("current node data:" + node.data + ", next node data:" + node.getNextNodeData());

		if (node.next != null) {
			printLink(node.next);
		} else {
			System.out.println("-------------");
		}
	}

	/**
	 * 单链表反转
	 */
	private static void verifySingleNodeReverse() {
		SingleNode<Integer> s1 = new SingleNode<>(1);
		SingleNode<Integer> s2 = new SingleNode<>(2);
		SingleNode<Integer> s3 = new SingleNode<>(3);
		SingleNode<Integer> s4 = new SingleNode<>(4);
		s1.next = s2;
		s2.next = s3;
		s3.next = s4;

		printLink(s1);
		// SingleNode<Integer> firstNode = reverseV1(s1, null);
		SingleNode<Integer> firstNode = reverseV2(s1);
		printLink(firstNode);
	}

	public static void main(String[] args) {
         verifySingleNodeReverse();
		// verifyLoop();
		verifyMerge();
		// verifyRemove();
		// verifyFindeMiddle();
	}
}