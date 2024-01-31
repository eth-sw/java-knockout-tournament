/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.aber.cs21120.knockout.tests;

import uk.ac.aber.cs21120.knockout.interfaces.IMatchTree;
import uk.ac.aber.cs21120.knockout.interfaces.ITreeNode;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Binary tree printer
 *
 * @author MightyPork
 */
public class TreePrinter
{

    /**
     * Print a tree
     *
     * @param tree the tree
     */
    public static void print(IMatchTree tree)
    {
        List<List<String>> lines = new ArrayList<List<String>>();
        PrintStream p = null;
        try {
            p = new PrintStream(System.out, true, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        PrintStream p = System.out;

        List<ITreeNode> level = new ArrayList<>();
        List<ITreeNode> next = new ArrayList<>();

        level.add(tree.getRoot());
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (ITreeNode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    // oh for an Elvis operator in Java
                    String aa;
                    if(n.getTeam() == null) {
                        aa="(not played)";
                    } else {
                        aa = n.getTeam().getName();
                        aa += ": " + n.getScore();
                    }
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null) nn++;
                    if (n.getRight() != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<ITreeNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    p.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            p.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            p.print(j % 2 == 0 ? " " : "─");
                        }
                        p.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            p.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                p.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    p.print(" ");
                }
                p.print(f);
                for (int k = 0; k < gap2; k++) {
                    p.print(" ");
                }
            }
            p.println();

            perpiece /= 2;
        }
    }
}
