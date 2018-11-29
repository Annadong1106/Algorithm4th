/*
主要内容：
1、实现二叉树的先序、中序、后序遍历，包括递归方式和非递归方式
*/

实现二叉树的先序、中序、后序遍历，包括递归方式和非递归方式
注意：
    对于二叉树，有深度遍历和广度遍历，深度遍历有前序、中序以及后序三种遍历方法，广度遍历即我们寻常所说的层次遍历。
一、递归方式
--------------------------------------------------------------------------------
/*先序遍历*/
public static void preOrderRecur(Node head){
    if (head == null) {
        return;
    }
    System.out.print(head.value + "  ");
    preOrderRecur(head.left);
    preOrderRecur(head.right);
}

/*中序遍历*/
public static void inOrderRecur(Node head){
    if (head == null) {
        return;
    }
    inOrderRecur(head.left);
    System.out.print(head.value + "  ");
    inOrderRecur(head.right);
}

/*后序遍历*/
public static void posOrderRecur(Node head) {
    if (head == null) {
        return;
    }
    posOrderRecur(head.left);
    posOrderRecur(head.right);
    System.out.print(head.value + "  ");
}

/*
* 层序遍历
* */
public static void levelTraverse(Node head) {
    System.out.print("level-order : ");
    if (head != null) {
        Queue<Node> queue = new LinkedList<>();
        ((LinkedList<Node>) queue).add(head);

        while (!queue.isEmpty()) {
            head = queue.poll();
            System.out.print(head.value + "  ");

            if (head.left != null) {
                ((LinkedList<Node>) queue).add(head.left);
            }
            if (head.right != null) {
                ((LinkedList<Node>) queue).add(head.right);
            }
        }
    }

    System.out.println();
}
--------------------------------------------------------------------------------

二、非递归方式
--------------------------------------------------------------------------------
/*
* 先序遍历的非递归实现
* 利用到栈，对于当前节点，先压右，在压左
* */
public static void preOrderUnRecur(Node head){
    System.out.print("pre-order : ");
    if(head != null){
        Stack<Node> stack = new Stack<>();                  //创建存放节点的栈
        stack.push(head);                                   //存放根节点

        while (!stack.isEmpty()) {
            head = stack.pop();                             //当前节点出栈
            System.out.print(head.value + "  ");

            if (head.right != null) {                       //右入栈
                stack.push(head.right);
            }
            if (head.left != null) {                        //左入栈
                stack.push(head.left);
            }
        }
    }
    System.out.println();
}

/*
*  后序遍历非递归实现
*  与先序遍历设计方法相似，但是：不同的是，每一次取出数据，不打印，而是存入到另一个栈中
* */
public static void posOrderUnRecur(Node head){
    System.out.print("pos-order : ");

    if (head != null) {
        Stack<Node> s1 = new Stack<>();
        Stack<Node> s2 = new Stack<>();

        s1.push(head);          //根节点入栈
        while (!s1.isEmpty()) {
            head = s1.pop();                //取出当前节点
            s2.push(head);

            if (head.left != null) {
                s1.push(head.left);
            }

            if (head.right != null) {
                s1.push(head.right);
            }
        }
        while (!s2.isEmpty()) {
            System.out.print(s2.pop().value + "  ");
        }
    }
    System.out.println();
}

/*
* 中序遍历非递归实现
* 思想：当前节点不空：
			①压栈    
			②当前节点向左走
*       
		当前节点为空：
			①从栈中取出一个元素，作为当前节点并打印当前节点    
			②当前节点向右走
* */
public static void inOrderUnRecur(Node head){
    System.out.print("in-order : ");
    if (head != null) {
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || head != null) {
            if (head != null) {
                stack.push(head);
                head = head.left;
            } else {
                head = stack.pop();
                System.out.print(head.value + "  ");
                head = head.right;
            }
        }
    }
    System.out.println();
}
--------------------------------------------------------------------------------

二叉树遍历测试：
--------------------------------------------------------------------------------
public static void main(String[] args) {
    Node head = new Node(5);
    head.left = new Node(3);
    head.right = new Node(8);
    head.left.left = new Node(2);
    head.left.right = new Node(4);
    head.left.left.left = new Node(1);
    head.right.left = new Node(7);
    head.right.left.left = new Node(6);
    head.right.right = new Node(10);
    head.right.right.left = new Node(9);
    head.right.right.right = new Node(11);

    // recursive
    System.out.println("==============recursive==============");
    System.out.print("pre-order: ");
    preOrderRecur(head);
    System.out.println();
    System.out.print("in-order: ");
    inOrderRecur(head);
    System.out.println();
    System.out.print("pos-order: ");
    posOrderRecur(head);
    System.out.println();
    levelTraverse(head);

    // unrecursive
    System.out.println("============unrecursive=============");
    preOrderUnRecur(head);
    inOrderUnRecur(head);
    posOrderUnRecur(head);
}
--------------------------------------------------------------------------------











