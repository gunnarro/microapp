package com.gunnarro.sportsteam.utility;

import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//import org.apache.poi.xwpf.usermodel.XWPFTable;
//import org.apache.poi.xwpf.usermodel.XWPFTableCell;
//import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DocxTextReplacer {

//    private static final String PLACEHOLDER_MARK = "[";
//    private String placeHolder;
//    private String replacement;
//    private Map<String, String> placeHoldersMap;
//    private XWPFDocument document;
//
//    public DocxTextReplacer(Map<String, String> placeHoldersMap) {
//        this.placeHoldersMap = placeHoldersMap;
//    }
//
//    public DocxTextReplacer(String fileName, String placeHolder, String replacement) throws Exception {
//        InputStream in = new FileInputStream(new File(fileName));
//        this.document = new XWPFDocument(in);
//        this.placeHolder = placeHolder;
//        this.replacement = replacement;
//    }
//
//    public XWPFDocument replace() throws Exception {
//        List<XWPFParagraph> paragraphs = this.document.getParagraphs();
//        for (XWPFParagraph paragraph : paragraphs) {
//            replace(paragraph);
//        }
//
//        for (XWPFTable xwpfTable : this.document.getTables()) {
//            for (XWPFTableRow row : xwpfTable.getRows()) {
//                for (XWPFTableCell cell : row.getTableCells()) {
//                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
//                        replace(paragraph);
//                    }
//                }
//            }
//        }
//         printDocx();
//        // document.write(new FileOutputStream("tempate-replaced-test.docx"));
//        return this.document;
//    }
//
//    private void replace(XWPFParagraph paragraph) {
//        if (hasPlaceholder(paragraph.getText())) {
//            System.out.println("TextReplacer.Replace: " + paragraph.getText());
//            String replacedText = StringUtils.replace(paragraph.getText(), placeHolder, replacement);
//            removeAllRuns(paragraph);
//            insertReplacementRuns(paragraph, replacedText);
//            System.out.println("TextReplacer.Replace replaced text: " + paragraph.getText());
//        }
//    }
//
//    private void insertReplacementRuns(XWPFParagraph paragraph, String replacedText) {
//        String[] replacementTextSplitOnCarriageReturn = StringUtils.split(replacedText, "\n");
//        for (int j = 0; j < replacementTextSplitOnCarriageReturn.length; j++) {
//            String part = replacementTextSplitOnCarriageReturn[j];
//            XWPFRun newRun = paragraph.insertNewRun(j);
//            newRun.setText(part, 0);
//            if (j + 1 < replacementTextSplitOnCarriageReturn.length) {
//                newRun.addCarriageReturn();
//            }
//        }
//    }
//
//    private void removeAllRuns(XWPFParagraph paragraph) {
//        int size = paragraph.getRuns().size();
//        for (int i = 0; i < size; i++) {
//            paragraph.removeRun(0);
//        }
//    }
//
//    /**
//     * Check if the text contains placeholders mark.
//     * @param text
//     * @return
//     */
//    private boolean hasPlaceholder(String text) {
//        return text.indexOf(PLACEHOLDER_MARK) != -1;
//    }
//
//    private void printPlaceholders(String text) {
//        String regexPattern="\\[(.+?)]";
//        Pattern pattern = Pattern.compile(regexPattern);
//        Matcher matcher = pattern.matcher(text);
//        while (matcher.find()){
//            System.out.println("placehoder: " + matcher.group());
//        }
//    }
//    
//    private void createTable() {
//        // create table
//        XWPFTable table = this.document.createTable();
//        // create first row
//        XWPFTableRow tableRowOne = table.getRow(0);
//        tableRowOne.getCell(0).setText("col one, row one");
//        tableRowOne.addNewTableCell().setText("col two, row one");
//        tableRowOne.addNewTableCell().setText("col three, row one");
//        // create second row
//        XWPFTableRow tableRowTwo = table.createRow();
//        tableRowTwo.getCell(0).setText("col one, row two");
//        tableRowTwo.getCell(1).setText("col two, row two");
//        tableRowTwo.getCell(2).setText("col three, row two");
//        // create third row
//        XWPFTableRow tableRowThree = table.createRow();
//        tableRowThree.getCell(0).setText("col one, row three");
//        tableRowThree.getCell(1).setText("col two, row three");
//        tableRowThree.getCell(2).setText("col three, row three");
//
//        printDocx();
//    }
//
//    public void printDocx() {
//        System.out.println("-------------------- Print updated docx -------------------------");
//        XWPFWordExtractor we = new XWPFWordExtractor(this.document);
//        System.out.println(we.getText());
//        printPlaceholders(we.getText());
//
//        // for (XWPFTable table : this.document.getTables()) {
//        // for (XWPFTableRow row : table.getRows()) {
//        // for (XWPFTableCell cell = row.getTableCells()) {
//        // // cell.getParagraphs();
//        // System.out.println("");
//        // }
//        // }
//        // }
//    }
}
