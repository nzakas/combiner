/*
 * Copyright (c) 2009 Nicholas C. Zakas. All rights reserved.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.nczonline.web.combiner;

import java.util.Comparator;

public class SourceFileComparator implements Comparator {

    public int compare(Object o, Object o1) {
        SourceFile file1 = (SourceFile) o;
        SourceFile file2 = (SourceFile) o1;

        if (file1.hasDependency(file2.getName())){
            return 1;
        } else if (file2.hasDependency(file1.getName())){
            return -1;
        } else {
            SourceFile[] deps1 = file1.getDependencies();
            SourceFile[] deps2 = file2.getDependencies();
            return deps1.length - deps2.length;
        }
    }
}