/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author USER PC
 */
public class LinkedList {

    Node head;

    static class Node {

        Punto data;
        Node next;

        // Constructor
        Node(Punto d) {
            data = d;
            next = null;
        }
    }

    public LinkedList insert(LinkedList list, Punto data) {
        // Crea nuevo nodo
        Node new_node = new Node(data);

        //Crea head si esta vacia la lista
        if (list.head == null) {
            list.head = new_node;
        } else {
            //Inserta nuevo nodo
            Node last = list.head;
            while (last.next != null) {
                last = last.next;
            }

            last.next = new_node;
        }

        // Return the list by head
        return list;
    }

    //Revisa si un nodo ya esta en la linked list
    public boolean exist(Node head, Punto x) {

        if (head == null) {
            return false;
        }

        if (head.data == x) {
            return true;
        }

        // Recur for remaining list
        return exist(head.next, x);
    }

    public void sortList() {
        //Organiza la lista
        Node current = head, index = null;
        Node temp;

        if (head == null) {
            return;
        } else {
            while (current.next.next != null) {
                
                index = current.next;

                while (index.next.next != null) {
                    if (current.data.getx() > index.data.getx()) {
                        temp = current;
                        current.data = index.data;
                        index = temp;
                    } else if (current.data.getx() == index.data.getx()) {
                        if (current.data.gety() > index.data.gety()) {
                            temp = current;
                            current.data = index.data;
                            index = temp;
                        }

                        index = index.next;
                    }
                    current = current.next;
                }
            }
        }
    }

}
