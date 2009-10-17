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


import java.io.*;
import java.util.ArrayList;

public class SourceFile {

    private File file = null;
    private ArrayList dependencies = null;
    private String contents = null;
    
    /**
     * Creates a new SourceFile based on a file.
     * @param file
     */
    public SourceFile(File file){
        this.file = file;
        dependencies = new ArrayList();
    }

    public File getFile() {
        return file;
    }
    
    public String getName(){
        return file.getAbsolutePath();
    }
    
    public String getDirectory(){
        String path = file.getAbsolutePath();
        return path.substring(0, path.lastIndexOf(File.separator)+1);
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }  
    
    public void addDependency(SourceFile dependency){
        dependencies.add(dependency);
    }
    
    public SourceFile[] getDependencies() {
        SourceFile[] deps = new SourceFile[dependencies.size()];
        dependencies.toArray(deps);
        return deps;
    }
    
    public int getDependencyCount(){
        return dependencies.size();
    }
    
    public boolean hasDependencies(){
        return !dependencies.isEmpty();                
    }
    
    public boolean hasDependency(String filename){
        boolean found = false;
        for (int i=0; i < dependencies.size() && !found; i++){
            SourceFile temp = (SourceFile) dependencies.get(i);
            found = temp.getName().equals(filename);
        }
        return found;        
    }
    
    public boolean hasDependency(File file){
        return hasDependency(file.getAbsolutePath());       
    }
    
    public boolean hasDependency(SourceFile file){
        return hasDependency(file.getName());
    }
    
}
