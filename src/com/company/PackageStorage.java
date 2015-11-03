package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Bartosz on 2014-11-06.
 */
public class PackageStorage {
    private List<Package> packages = new ArrayList<Package>();

//    public PackageStorage(List<Package> packages) {
//        this.packages = packages;
//    }

    public boolean isEmpty() {
        return packages.isEmpty();
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void print() {
        int n = packages.size();
        int j = 0;
//        int k=1;
//        for( int i=1; i<n; i*=2){
//            k*=2;
//        }
//        System.out.println("xxxxxx "+k);
        int k = n;
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (int m = 1; j < n; m *= 2) {
            for (int l = 0; l < m && j < n; l++) {
                for (int i = 0; i < k; i++) System.out.print(" ");
//                System.out.print(packages.get(j).getId());
                System.out.print(packages.get(j).getPriority());
                j++;
            }
            k = k - 2;
            System.out.println(" ");
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        System.out.println("\n\n");
//        for( Package p : packages )
//            System.out.print(p.getId() + " ");
    }

    public void printList() {
        for (Package p : packages)
            System.out.print(" " + p.getId() + ",");
    }

    public Package pop() {
        Package tmp = packages.get(0);
        Collections.swap(packages, 0, packages.size() - 1);
        packages.remove(packages.size() - 1);
        hdown();
        return tmp;
    }

    public void push(Package elem) {
        packages.add(elem);
        hup();
//        print();
    }

    public void remove(int id) {
        Package thePackage = null;
        for (Package p : packages)
            if (p.getId() == id) {
                thePackage = p;
                break;
            }
        if (thePackage != null) {
            if (packages.indexOf(thePackage) != 0)
                packages.remove(packages.indexOf(thePackage));
            else
                pop();
        }

    }

    private void hup() {
        int n = packages.size();
        int i = n - 1;
        while (i > 0) {
            int p = (i - 1) / 2;
            if (packages.get(p).getPriority() >= packages.get(i).getPriority())
                break;
            Collections.swap(packages, i, p);
            i = p;
        }
    }

    private void hdown() {
        int n = packages.size();
        int i = 0;
        int c = 2 * i + 1;

        while (c < n) {
            if (c + 1 < n && packages.get(c + 1).getPriority() > packages.get(c).getPriority()) {
                c++;
            }

            if (packages.get(c).getPriority() <= packages.get(i).getPriority()) {
                break;
            }

            Collections.swap(packages, i, c);
            i = c;
            c = 2 * i + 1;
        }


    }
}
