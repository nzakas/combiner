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

import jargs.gnu.CmdLineParser;
import java.io.*;
import java.nio.charset.Charset;

/*
 * This file heavily inspired and based on YUI Compressor.
 * http://github.com/yui/yuicompressor
 */

public class Combiner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //default settings
        boolean verbose = false;
        boolean separator = false;
        boolean eliminateUnused = false;
        
        //boolean keepAll = true;
        String charset = null;
        String outputFilename = null;
        Writer out = null;
        
        //initialize command line parser
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option verboseOpt = parser.addBooleanOption('v', "verbose");
        CmdLineParser.Option helpOpt = parser.addBooleanOption('h', "help");
        CmdLineParser.Option charsetOpt = parser.addStringOption("charset");
        CmdLineParser.Option outputFilenameOpt = parser.addStringOption('o', "output");
        CmdLineParser.Option separatorOpt = parser.addBooleanOption('s', "separator");
        CmdLineParser.Option eliminateOpt = parser.addBooleanOption('e', "eliminate");
        
        try {
            
            //parse the arguments
            parser.parse(args);

            //figure out if the help option has been executed
            Boolean help = (Boolean) parser.getOptionValue(helpOpt);
            if (help != null && help.booleanValue()) {
                usage();
                System.exit(0);
            } 
            
            //determine boolean options
            verbose = parser.getOptionValue(verboseOpt) != null;
            separator = parser.getOptionValue(separatorOpt) != null;
            eliminateUnused = parser.getOptionValue(eliminateOpt) != null;
            
            //check for charset
            charset = (String) parser.getOptionValue(charsetOpt);
            if (charset == null || !Charset.isSupported(charset)) {
                charset = System.getProperty("file.encoding");
                if (charset == null) {
                    charset = "UTF-8";
                }
                if (verbose) {
                    System.err.println("[INFO] Using charset " + charset);
                }
            }

            //get the file arguments
            String[] fileArgs = parser.getRemainingArgs();
            
            //need to have at least one file
            if (fileArgs.length == 0){
                System.err.println("No files specified.");
                System.exit(1);
            }

            //get output filename
            outputFilename = (String) parser.getOptionValue(outputFilenameOpt);
            
            if (outputFilename == null) {
                out = new OutputStreamWriter(System.out, charset);
            } else {
                if (verbose){
                    System.err.println("[INFO] Output file is '" + (new File(outputFilename)).getAbsolutePath() + "'");
                }
                out = new OutputStreamWriter(new FileOutputStream(outputFilename), charset);
            }            

            FileCombiner combiner = new FileCombiner();
            combiner.combine(out, fileArgs, charset, verbose, separator, eliminateUnused);

    
        } catch (CmdLineParser.OptionException e) {
            usage();
            System.exit(1);            
        } catch (Exception e) { 
            System.err.println(e.getMessage());
            System.exit(1);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }            
        }
        
    }

    /**
     * Outputs help information to the console.
     */
    private static void usage() {
        System.out.println(
                "\nUsage: java -jar combiner-x.y.z.jar [options] [input files]\n\n"

                        + "Global Options\n"
                        + "  -h, --help                Displays this information\n"
                        + "  --charset <charset>       Read the input file using <charset>\n"
                        + "  -v, --verbose             Display informational messages and warnings\n"
                        + "  -s, --separator           Output a separator between combined files\n"
                        + "  -e, --eliminate           Eliminates any files that aren't explicitly required.\n"
                        + "  -o <file>                 Place the output into <file>. Defaults to stdout.");
    }
}
