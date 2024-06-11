package ru.psuti.fileservice.service.impl;


import lombok.extern.log4j.Log4j2;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;


@Log4j2
public class DocumentChecker {

    protected static void checkAndCorrectDocument(String tempFile) throws IOException {
        try (InputStream is = new FileInputStream(tempFile)) {
            XWPFDocument document = new XWPFDocument(is);

            checkAndCorrectHeaders(document);
            checkAndCorrectBasicText(document);
            checkAndCorrectCode(document);
            checkAndCorrectPicture(document);
            checkAndCorrectPageMargins(document);
            checkAndCorrectLists(document);
            checkAndCorrectTables(document);

//            String correctedFilePath = tempFile.replace(".docx", "_corrected.docx");
            try (FileOutputStream os = new FileOutputStream(tempFile)) {
                document.write(os);
            }

//            return tempFile;
        }
    }

    private static void checkAndCorrectHeaders(XWPFDocument document) {
        try {
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String text = paragraph.getText();

                if (isHeader(paragraph)) {
                    int level = text.split("\\.").length;


                    paragraph.setAlignment(ParagraphAlignment.CENTER);
                    paragraph.setSpacingBetween(1.5);
                    paragraph.setSpacingAfter(0);
                    paragraph.setIndentationFirstLine(0);

                    for (XWPFRun run : paragraph.getRuns()) {
                        run.setFontFamily("Times New Roman");

                        switch (level) {
                            case 1:
                                run.setBold(true);
                                run.setFontSize(16);
                                break;
                            case 2:
                                run.setBold(true);
                                run.setFontSize(14);
                                break;
                            case 3:
                                run.setBold(false);
                                run.setFontSize(14);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Не удалось проверить/исправить заголовки: {}", e.getMessage());
        }

    }

    private static void checkAndCorrectBasicText(XWPFDocument document) {
        try {
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                if (!isHeader(paragraph)) {
                    paragraph.setAlignment(ParagraphAlignment.BOTH);
                    paragraph.setIndentationFirstLine(709);
                    paragraph.setSpacingBetween(1.5);

                    for (XWPFRun run : paragraph.getRuns()) {
                        run.setFontFamily("Times New Roman");
                        run.setFontSize(14);
                        run.setBold(false);
                        run.setItalic(false);
                    }
                }

            }
        } catch (Exception e) {
            log.error("Не удалось проверить/исправить текст: {}", e.getMessage());
        }
    }

    private static void checkAndCorrectPageMargins(XWPFDocument document) {
        try {
            CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
            CTPageMar pageMar = sectPr.addNewPgMar();
            pageMar.setLeft(BigInteger.valueOf(1700));
            pageMar.setRight(BigInteger.valueOf(850));
            pageMar.setTop(BigInteger.valueOf(1132));
            pageMar.setBottom(BigInteger.valueOf(1132));

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                paragraph.setSpacingAfter(0);
                paragraph.setSpacingBefore(0);
                paragraph.setSpacingAfterLines(0);
                paragraph.setSpacingBeforeLines(0);
            }
        } catch (Exception e) {
            log.error("Не удалось проверить/исправить поля страниц: {}", e.getMessage());
        }
    }

    private static void checkAndCorrectPicture(XWPFDocument document) {
        try {
            boolean applyToNextParagraph = false;
            boolean ensureEmptyParagraph = false;

            for (int i = 0; i < document.getBodyElements().size(); i++) {
                IBodyElement element = document.getBodyElements().get(i);

                if (element instanceof XWPFParagraph paragraph) {

                    if (ensureEmptyParagraph) {
                        try {
                            if (!paragraph.getText().isEmpty()) {
                                XWPFParagraph emptyParagraph = createEmptyParagraph(document);

                                document.setParagraph(emptyParagraph, i);

                            }
                        } catch (Exception e) {
                            System.out.println("Error occurred: " + e.getMessage());
                        }

                        ensureEmptyParagraph = false;
                    }

                    if (applyToNextParagraph) {
                        if (paragraph.getText().startsWith("Рис")) {
                            paragraph.setSpacingBetween(1.5);
                            paragraph.setAlignment(ParagraphAlignment.CENTER);
                            paragraph.setIndentationFirstLine(0);
                            for (XWPFRun run : paragraph.getRuns()) {
                                run.setFontFamily("Times New Roman");
                                run.setFontSize(14);
                                run.setBold(false);
                                run.setItalic(false);
                            }
                        } else {
                            XWPFParagraph newParagraph = document.createParagraph();
                            newParagraph.setAlignment(ParagraphAlignment.CENTER);
                            newParagraph.setSpacingBetween(1.5);
                            newParagraph.setIndentationFirstLine(0);
                            newParagraph.setSpacingAfter(0);
                            XWPFRun newRun = newParagraph.createRun();
                            newRun.setText("Рис. %.% – название рисунка");
                            newRun.setFontFamily("Times New Roman");
                            newRun.setTextHighlightColor("lightGray");
                            newRun.setFontSize(14);
                            newRun.setBold(false);
                            newRun.setItalic(false);


                            document.setParagraph(newParagraph, i);
                        }

                        applyToNextParagraph = false;
                        ensureEmptyParagraph = true;
                    }


                    for (XWPFRun run : paragraph.getRuns()) {
                        if (!run.getEmbeddedPictures().isEmpty()) {
                            if (!isSpacingBetween1_5(paragraph) || !isAlignmentCenter(paragraph) || isIndentation1_25(paragraph)) {
                                paragraph.setSpacingBetween(1.5);
                                paragraph.setAlignment(ParagraphAlignment.CENTER);
                                paragraph.setIndentationFirstLine(0);


                                break;
                            }
                            applyToNextParagraph = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Не удалось проверить/исправить рисунки: {}", e.getMessage());
        }
    }

    private static void checkAndCorrectTables(XWPFDocument document) {
        try {
            for (int i = 0; i < document.getBodyElements().size(); i++) {
                if (i + 1 < document.getBodyElements().size()) {
                    IBodyElement elementTable = document.getBodyElements().get(i);
                    IBodyElement elementEmptyParagraphAfterTable = document.getBodyElements().get(i + 1);

                    if (elementTable instanceof XWPFTable table) {
                        if (isFormula(table)) continue;

                        if (elementTable.getElementType() == BodyElementType.TABLE) {
                            for (XWPFTableRow row : table.getRows()) {
                                for (XWPFTableCell cell : row.getTableCells()) {
                                    for (XWPFParagraph paragraphCell : cell.getParagraphs()) {
                                        for (XWPFRun run : paragraphCell.getRuns()) {
                                            run.setFontFamily("Times New Roman");
                                            run.setBold(false);
                                            run.setItalic(false);
                                        }
                                    }
                                }
                            }
                        }
                        if (elementEmptyParagraphAfterTable instanceof  XWPFParagraph paragraph) {
                            if (!paragraph.getText().isEmpty()) {
                                XWPFParagraph emptyParagraph = createEmptyParagraph(document);

                                document.setParagraph(emptyParagraph, i);
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            log.error("Не удалось проверить/исправить таблицы: {}", e.getMessage());
        }
    }


    private static void checkAndCorrectCode(XWPFDocument document) {
        try {
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                if (isCode(paragraph)) {
                    if (!isAlignmentLeft(paragraph) || !isSpacingBetween1_0(paragraph) || !isIndentationNone(paragraph)) {
                        paragraph.setSpacingBetween(1.0);
                        paragraph.setAlignment(ParagraphAlignment.LEFT);
                        paragraph.setIndentationFirstLine(0);
                    }

                    for (XWPFRun run : paragraph.getRuns()) {
                        run.setFontFamily("Courier New");
                        run.setFontSize(11);
                        run.setTextHighlightColor("none");
                    }
                }
            }
        } catch (Exception e) {
            log.error("Не удалось проверить/исправить код: {}", e.getMessage());
        }
    }


    private static void checkAndCorrectLists(XWPFDocument document) {
        try {
            for (XWPFParagraph paragraph : document.getParagraphs()) {

                if (isList(paragraph)) {
                    paragraph.setAlignment(ParagraphAlignment.BOTH);
                    paragraph.setSpacingBetween(1.5);
                    paragraph.setIndentationFirstLine(709);
                    paragraph.setSpacingAfter(0);
                    paragraph.setIndentationLeft(0);
                    paragraph.setIndentationLeftChars(0);
                    paragraph.setIndentationRight(0);
                    paragraph.setIndentationRightChars(0);
                    paragraph.setIndentFromRight(0);

                    for (XWPFRun run : paragraph.getRuns()) {
                        run.setFontFamily("Times New Roman");
                        run.setFontSize(14);
                        run.setBold(false);
                        run.setItalic(false);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Не удалось проверить/исправить списки: {}", e.getMessage());
        }
    }

    private static XWPFParagraph createEmptyParagraph(XWPFDocument document) {
        try {
            XWPFParagraph emptyParagraph = document.createParagraph();
            emptyParagraph.setAlignment(ParagraphAlignment.BOTH);
            emptyParagraph.setSpacingBetween(1.5);
            emptyParagraph.setIndentationFirstLine(709);
            emptyParagraph.setSpacingAfter(0);
            XWPFRun run = emptyParagraph.createRun();
            run.setText(" ");
            run.setFontSize(14);
            run.setFontFamily("Times New Roman");

            return emptyParagraph;
        } catch (Exception e) {
            log.error("Не удалось создать пустой параграф: {}", e.getMessage());
        }

        return null;
    }

    private static boolean isHeader(XWPFParagraph paragraph) {
        String text = paragraph.getText();

        return text.matches("^\\d+(\\.\\d+)*\\s+.*");

    }



    private static boolean isCode(XWPFParagraph paragraph) {
        boolean hasCode = false;

        for (XWPFRun run : paragraph.getRuns()) {
            if (run.getTextHightlightColor() == STHighlightColor.RED) {
                hasCode = true;
                break;
            }
        }

        return hasCode;
    }


    private static boolean isFormula(XWPFTable table) {
        return table.getWidthType().name().equals("PCT");
    }


    private static boolean isList(XWPFParagraph paragraph) {
        return paragraph.getNumIlvl() != null;
    }

    private static boolean isSpacingBetween1_5(XWPFParagraph paragraph) {
        return paragraph.getSpacingBetween() == 1.5;
    }

    private static boolean isSpacingBetween1_0(XWPFParagraph paragraph) {
        return paragraph.getSpacingBetween() == 1.0;
    }


    private static boolean isIndentation1_25(XWPFParagraph paragraph) {
        return paragraph.getIndentationFirstLine() == 720;
    }

    private static boolean isIndentationNone(XWPFParagraph paragraph) {
        return paragraph.getIndentationFirstLine() == 0;
    }

    private static boolean isAlignmentCenter(XWPFParagraph paragraph) {
        return paragraph.getAlignment() == ParagraphAlignment.CENTER;
    }

    private static boolean isAlignmentLeft(XWPFParagraph paragraph) {
        return paragraph.getAlignment() == ParagraphAlignment.LEFT;
    }
}